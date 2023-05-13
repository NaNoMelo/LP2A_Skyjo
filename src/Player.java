public class Player {
    private Card[][] cards;
    private String name;
    private int points = 0;

    public Player(String name) {
        this.name = name;
        this.cards = new Card[4][3];
    }

    public void drawCards(Deck deck) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                cards[i][j] = deck.drawCard();
            }
        }
    }
    //getter
    //setter
    public int getPoints() {
        return points;
    }
    public void setPoints() {
        //for each visible card, add its score to the player's points
        points = 0;
        for (Card[] card : cards) {
            for (Card value : card) {
                if (value.isVisible()) {
                    points += value.getScore();
                }
            }
        }
    }
    public Card[][] getCards() {
        return cards;
    }
    public String getName() {
        return name;
    }
    //setter
    public void setCards(Card[][] cards) {
        this.cards = cards;
    }
    public void setName(String name) {
        this.name = name;
    }
}
