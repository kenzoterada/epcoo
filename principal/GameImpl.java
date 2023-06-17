package principal;

import carta.Card;
import carta.Piece;
import excecao.IllegalMovementException;
import excecao.IncorrectTurnOrderException;
import excecao.InvalidCardException;
import excecao.InvalidPieceException;
import jogador.Player;
import posicao.Color;
import posicao.Position;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class GameImpl implements Game {
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

        this.tabuleiro = new Spot[5][5];
        this.CartaTabuleiro = cartas_embaralhadas[0];
        this.corJogadorTurno = CartaTabuleiro.getColor();
        this.jogadorVermelho = new Player(peca_vermelha, Color.RED, cartas_embaralhadas[1], cartas_embaralhadas[2]);
        this.jogadorAzul = new Player(peca_azul, Color.BLUE, cartas_embaralhadas[3], cartas_embaralhadas[4]);

    }

    private void definirTurno() {
        this.corJogadorTurno = CartaTabuleiro.getColor();
    }

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

    @Override
    public void makeMove(Card card, Position cardMove, Position currentPos)
            throws IncorrectTurnOrderException, IllegalMovementException, InvalidCardException, InvalidPieceException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

    @Override
    public boolean checkVictory(Color color) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkVictory'");
    }

    @Override
    public void printBoard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printBoard'");
    }

}
