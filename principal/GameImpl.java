package principal;

import carta.Card;
import carta.Piece;
import excecao.IllegalMovementException;
import excecao.IncorrectTurnOrderException;
import excecao.InvalidCardException;
import excecao.InvalidPieceException;
import posicao.Color;
import posicao.Position;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class GameImpl implements Game {
    private static Card carta_no_tabuleiro;
    private Card CartaTabuleiro;
    private Player jogadorVermelho, jogadorAzul;
    private Spot[][] tabuleiro;
    private Color corJogadorTurno;

    public GameImpl() {
        Card[] cartas = Card.createCards();
        this.tabuleiro = new Spot[5][5];
        this.CartaTabuleiro = cartas[0];
        this.corJogadorTurno = CartaTabuleiro.getColor();
        this.jogadorVermelho = new Player("Vermelho", Color.RED, cartas[1], cartas[2]);
        this.jogadorAzul = new Player("Azul", Color.BLUE, cartas[3], cartas[4]);
    }

    public GameImpl(String peca_vermelha, String peca_azul) {
        Card[] cartas = Card.createCards();
        this.tabuleiro = new Spot[5][5];
        this.CartaTabuleiro = cartas[0];
        this.corJogadorTurno = CartaTabuleiro.getColor();
        this.jogadorVermelho = new Player(peca_vermelha, Color.RED, cartas[1], cartas[2]);
        this.jogadorAzul = new Player(peca_azul, Color.BLUE, cartas[3], cartas[4]);
    }

    public GameImpl(String peca_vermelha, String peca_azul, Card[] novo_deck) {

        List<Card> listaCartas = Arrays.asList(novo_deck);
        Collections.shuffle(listaCartas);
        Card[] cartas_embaralhadas = listaCartas.toArray(new Card[0]);

        carta_no_tabuleiro = cartas_embaralhadas[0];
        this.tabuleiro = new Spot[5][5];
        this.CartaTabuleiro = cartas_embaralhadas[0];
        this.corJogadorTurno = CartaTabuleiro.getColor();
        this.jogadorVermelho = new Player(peca_vermelha, Color.RED, cartas_embaralhadas[1], cartas_embaralhadas[2]);
        this.jogadorAzul = new Player(peca_azul, Color.BLUE, cartas_embaralhadas[3], cartas_embaralhadas[4]);

    }
    // ******************************************************************************

    public static Card getCarta_no_tabulerio() {
        return carta_no_tabuleiro;

    }

    public boolean mestre_vivo(Color cor) {
        Piece verify;
        for (int i = 0; i < this.tabuleiro.length; i++) {
            for (int j = 0; j < this.tabuleiro[i].length; j++) {
                verify = getPiece(new Position(i, j));

                if (verify.isMaster() == true && verify.getColor() == cor) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean possivel_locomover(Card card, Position cardMove) {
        Position[] posicoes = card.getPositions();
        for (Position posicao : posicoes) {
            if (posicao == cardMove) {
                return true;
            }
        }
        return false;
    }

    public boolean possui_carta(Player player, Card card) {
        Card[] cartas = player.getCards();
        for (Card carta : cartas) {
            if (carta == card) {
                return true;
            }
        }
        return false;
    }

    public Piece achar_peca(Position currentPos) {
        Piece peca_descobrir = null;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.tabuleiro[i][j].getPosition() == currentPos) {
                    peca_descobrir = tabuleiro[i][j].getPiece();
                }
            }
        }
        return peca_descobrir;
    }

    public boolean dentro_tabuleiro(Position cardMove) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.tabuleiro[i][j].getPosition() == cardMove) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean possui_peca_cor(Position cardMove, Color cor) {
        if (achar_peca(cardMove).getColor() == cor) {
            return true;
        }
        return false;
    }

    public Player getPlayer_pela_cor(Color cor) {
        if (cor == Color.BLUE) {
            return getBluePlayer();
        } else {
            return getRedPlayer();
        }
    }

    public void change_turn() {
        if (corJogadorTurno == Color.RED) {
            corJogadorTurno = Color.BLUE;
        } else {
            corJogadorTurno = Color.RED;
        }
    }

    public Position nova_posicao(Position cardMove, Position currentPos) {
        int linha = cardMove.getRow() + currentPos.getRow();
        int coluna = cardMove.getCol() + currentPos.getCol();
        Position nova_posicao = new Position(linha, coluna);

        return nova_posicao;
    }

    // ******************************************************************************

    @Override
    public Color getSpotColor(Position position) {
        return this.tabuleiro[position.getRow()][position.getCol()].getColor();
    }

    @Override
    public Piece getPiece(Position position) {
        return this.tabuleiro[position.getRow()][position.getCol()].getPiece();
    }

    @Override
    public Card getTableCard() {
        return this.CartaTabuleiro;
    }

    @Override
    public Player getRedPlayer() {
        return this.jogadorVermelho;
    }

    @Override
    public Player getBluePlayer() {
        return this.jogadorAzul;
    }

    // private void definirTurno() {
    // this.corJogadorTurno = CartaTabuleiro.getColor();
    // }

    @Override
    public void makeMove(Card card, Position cardMove, Position currentPos)
            throws IncorrectTurnOrderException, IllegalMovementException,
            InvalidCardException, InvalidPieceException {

        Color jogador_atual = card.getColor();
        Player jogador = getPlayer_pela_cor(jogador_atual);

        boolean no_tabuleiro = dentro_tabuleiro(cardMove);

        if (this.corJogadorTurno != jogador_atual) {
            throw new IncorrectTurnOrderException("Jogador não está no seu turno!");
        } else if (no_tabuleiro) {
            throw new IllegalMovementException("Movimento para fora do tabuleiro!");
        } else if (possui_peca_cor(cardMove, jogador_atual)) {
            throw new IllegalMovementException("Posição ja possui peça da mesma cor!");
        } else if (!possui_carta(jogadorAzul, card)) {
            throw new InvalidCardException("Jogador não possui carta!");
        } else if (achar_peca(currentPos) == null) {
            throw new InvalidPieceException("Peça não se encontra na posição atual!");
        } else {
            jogador.swapCard(card, carta_no_tabuleiro);

            Position posicao_nova = nova_posicao(cardMove, currentPos);
            tabuleiro[posicao_nova.getRow()][posicao_nova.getCol()].occupySpot(achar_peca(currentPos));
            tabuleiro[currentPos.getRow()][currentPos.getCol()].releaseSpot();
        }
    }

    @Override
    public boolean checkVictory(Color color) {
        if (color == Color.BLUE) {
            if (this.tabuleiro[4][2].getColor() == Color.BLUE ||
                    !mestre_vivo(Color.RED)) {
                return true;
            }

            else if (color == Color.RED) {
                if (this.tabuleiro[0][2].getColor() == Color.RED ||
                        !mestre_vivo(Color.BLUE)) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public void printBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Piece verifica = this.tabuleiro[i][j].getPiece();
                if (verifica == null) {
                    System.out.print(" e "); // empty
                } else {
                    boolean verifica_master = verifica.isMaster();
                    if (verifica_master && (verifica.getColor() == Color.BLUE)) {
                        System.out.print("M.A ");

                    }
                    if (verifica_master && (verifica.getColor() == Color.RED)) {
                        System.out.print("M.V ");

                    }
                    if (!verifica_master && (verifica.getColor() == Color.BLUE)) {
                        System.out.print("A ");

                    }
                    if (!verifica_master && (verifica.getColor() == Color.RED)) {
                        System.out.print("V ");

                    }
                }
            }
            System.out.print("\n");
        }
    }

}
