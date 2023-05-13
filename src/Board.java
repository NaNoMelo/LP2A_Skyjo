import java.io.IOException;

public class Board {
    private int nbPlayers;
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
    private int turn=0;
    private int endGame= -1;

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
        discardPile.add(drawPile.drawCard().setVisible(true));
        endGame = -1;
    }

    public void selectTurn() {
        //look for the player with the lowest score, turn is his index
        int min = players[0].getPoints();
        for (int i = 1; i < nbPlayers; i++) {
            if (players[i].getPoints() > min) {
                min = players[i].getPoints();
                turn = i;
            }
        }
    }

    public int play(boolean fromDiscard,int row, int col, boolean discard) {
        Player player = players[turn];
        Card[][] cards = player.getCards();
        Card card = cards[row][col];
        if (fromDiscard) {
            if (discardPile.isEmpty()){
                System.out.println("Discard pile is empty");
                return 1;
            }
            cards[row][col] = discardPile.poll().setVisible(true);
            discardPile.add(card.setVisible(true));
        } else {
            if (drawPile.isEmpty()){
                System.out.println("Draw pile is empty");
                return 2;
            }
            if (discard) {
                discardPile.add(drawPile.poll().setVisible(true));
                if (cards[row][col].isVisible()) {
                    System.out.println("Card is already visible");
                    return 3;
                }
                cards[row][col].setVisible(true);
            } else {
                cards[row][col] = drawPile.poll().setVisible(true);
                discardPile.add(card.setVisible(true));
            }
        }
        boolean testVisible = true;
        for (int i = 0; i<4;i++){
            for (int j = 0; j<3;j++){
                if (!cards[i][j].isVisible()){
                    testVisible = false;
                }
            }
        }
        if (testVisible) {
            endGame = turn;
        }
        turn = (turn + 1) % nbPlayers;
        return 0;
    }
    //getters
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

    public int isEndGame() {
        return endGame;
    }
    //getters
    public int getTurn() {
        return turn;
    }
    //setters
    public Board setTurn(int turn) {
        this.turn = turn;
        return this;
    }
}
