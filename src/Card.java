public class Card {
    private final UV uv;
    private int score;
    private boolean visible = false;

    public Card(UV uv, int score) {
        this.uv = uv;
        this.score = score;
        this.visible = false;
    }

    //getters
    public UV getUV() {
        return uv;
    }
    public boolean isVisible() {
        return visible;
    }
    public int getScore() {
        return score;
    }

    //setters
    public Card setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

}
