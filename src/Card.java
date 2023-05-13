public class Card {
    private final UV uv;
    private int score = 0;
    private String letter;
    private boolean visible = false;

    public Card(UV uv) {
        this.uv = uv;
        String l[] = {"fx","f","abs","e","d","c","b","a"};
        this.letter = l[(int)(Math.random()*l.length)];
        this.score = uv.getCredits();
        //if the letter is fx score is divided by -4
        if (letter.equals("abs")){
            this.score = this.score*-40/100;
        }
        if (letter.equals("f")){
            this.score = this.score*-20/100;
        }
        if (letter.equals("fx")){
            this.score = 0;
        }
        if (letter.equals("e")){
            this.score = this.score*20/100;
        }
        if (letter.equals("d")){
            this.score = this.score*40/100;
        }
        if (letter.equals("c")){
            this.score = this.score*60/100;
        }
        if (letter.equals("b")){
            this.score = this.score*80/100;
        }
        if (letter.equals("a")){
            this.score = this.score;
        }
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
