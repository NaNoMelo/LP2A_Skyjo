import java.io.IOException;

public class Board {
    private int nbPlayers;
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;

    public Board(int nbPlayers) throws IOException {
        this.drawPile = new Deck();
        try {
            drawPile.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        drawPile.shuffle();
        this.discardPile = new Deck();
        this.nbPlayers = nbPlayers;
        this.players = new Player[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            players[i] = new Player("Player " + i);
            players[i].drawCards(drawPile);
        }
    }

}
