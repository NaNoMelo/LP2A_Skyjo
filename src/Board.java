import java.io.IOException;

public class Board {
    private int nbPlayers;
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;
    private int turn;

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
    }

    public void selectTurn() {
        int best = 0;
        int bestPlayer = 0;
        for (int p = 0; p < nbPlayers; p++) {
            Player player = players[p];
            Card[][] cards = player.getCards();
            int score = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    score += cards[i][j].isVisible() ? cards[i][j].getScore() : 0;
                }
            }
            if (score > best) {
                best = score;
                bestPlayer = p;
            }
        }
        System.out.println("Player " + bestPlayer + " starts");
        turn = bestPlayer;
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
        turn = (turn + 1) % nbPlayers;
        return 0;
    }

}
