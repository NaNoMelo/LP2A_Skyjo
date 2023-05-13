import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game {
    private Board board;
    private JFrame frame;

    public Game(int nbPlayers) throws IOException {

        try {
            this.board = new Board(nbPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.board = new Board(nbPlayers);
        this.frame = new JFrame("Skyjjo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 1000));
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }



}
