import java.io.IOException;
public class Board {
    private int nbPlayers;
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
    private int turn = 0;
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
//getter
    public int getNbPlayers() {
        return nbPlayers;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Deck getDrawPile() {
        return drawPile;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }
//setter
    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setDrawPile(Deck drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(Deck discardPile) {
        this.discardPile = discardPile;
    }
    public int getTurn() {
        return turn;
    }
    //print the deck
    public void printDeck() {
        for (int i = 0; i < drawPile.size(); i++) {
            System.out.println(drawPile.get(i).getUV());
        }
    }

}
