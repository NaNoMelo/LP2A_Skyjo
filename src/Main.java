import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //start the game
        Game game = new Game();
        game.start();
        while (!game.isclicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.isclicked = false;
        game.menu();
        while (!game.isclicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        game.isclicked = false;
        game.show_board();
        game.popUp("Each players: select two cards");
        //wait for the players to select their cards
        for(int i=0;i<game.getBoard().getNbPlayers();i++){
            game.getBoard().setTurn(i);
            for(int j=0;j<2;j++){
                while (!game.isclicked) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                game.isclicked = false;
                System.out.println("Player "+i+" selected a card");
                game.refresh();
            }
        }

        game.popUp("You can now start the game");
        //set score for each player
        for(int i=0;i<game.getBoard().getNbPlayers();i++){
            game.getBoard().getPlayers()[i].setPoints();
        }
        game.getBoard().selectTurn();
        game.popUp("Player "+game.getBoard().getTurn()+" starts"+"because he has the lowest score");
        //show which one has to play

        game.popUp("Player "+game.getBoard().getTurn()+" select a card from the draw pile or the discard pile");
        //wait for the player to select a card
        while (!game.isclicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.isclicked = false;





    }
}