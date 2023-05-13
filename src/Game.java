import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game {
    private Board board;
    private JFrame frame;
    private Card card;
    public boolean gameStatus = false;
    public boolean isclicked = false;

        // create a startup frame, a button start and a button exit, changing gameStatus to true or false
        public void start() {

            frame = new JFrame("Skyjjo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            JButton start = new JButton("Start");
            JButton exit = new JButton("Exit");

            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameStatus = true;
                    isclicked = true;
                    frame.dispose();
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameStatus = false;
                    isclicked = true;
                    frame.dispose();
                }
            });

            frame.add(start);
            frame.add(exit);
            frame.setVisible(true);
        }

        // create a menu frame, a entering integer field, a validation button and a button exit
        public void menu (){

            frame = new JFrame("Skyjjo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new FlowLayout());
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            JLabel label = new JLabel("Enter the number of players: ");
            JTextField field = new JTextField(10);
            JButton validate = new JButton("Validate");
            JButton exit = new JButton("Exit");

            validate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //get the number of players

                    if (Integer.parseInt(field.getText()) > 0 && Integer.parseInt(field.getText()) < 9) {
                        System.out.println("Number of players is valid");

                        //create a board
                        try {
                            board = new Board(Integer.parseInt(field.getText()));
                            board.printDeck();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        isclicked = true;
                        frame.dispose();
                    } else {
                        System.out.println("Number of players is invalid");
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameStatus = false;
                    isclicked = true;
                    frame.dispose();
                }
            });

            frame.add(label);
            frame.add(field);
            frame.add(validate);
            frame.add(exit);
            frame.setVisible(true);
        }
    public void show_board(Game game) {
        frame = new JFrame("Skyjjo Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //frame is in full screen
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        // background is png adjusted to the size of the frame
        ImageIcon background = new ImageIcon(".\\fond.png");

        //adjust the size of the background
        Image img = background.getImage();
        Image newimg = img.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        background = new ImageIcon(newimg);
        //add the background to the frame
        frame.setContentPane(new JLabel(background));
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //create a panel for each player
        JPanel[] panels = new JPanel[board.getNbPlayers()];
        for (int i = 0; i < board.getNbPlayers(); i++) {
            panels[i] = new JPanel();
            //add the name of the player
            panels[i].add(new JLabel(board.getPlayers()[i].getName()));
            //return to the line
            panels[i].add(new JLabel("  "));
            panels[i].add(new JLabel("  "));
            panels[i].add(new JLabel("  "));

            panels[i].setPreferredSize(new Dimension(300, 300)); //adjust the size as needed
            panels[i].setLayout(new FlowLayout());
            //dispose a grid
            panels[i].setLayout(new GridLayout(4, 3));
            //transparent background
            panels[i].setOpaque(false);
            frame.add(panels[i]);
        }

        //add in each panel as many buttons as there are cards in the hand of the player
        //create a button for each card
        int i1 = 0;
        int j1 = 0;
        JButton[][] buttons = new JButton[board.getNbPlayers()][12];
        for (int i = 0; i < board.getNbPlayers(); i++) {
            for (int j = 0; j < 12; j++) {
                if (i1 == 3) {
                    i1 = 0;
                    j1++;
                }
                if(j1==2&&i1==3){
                    j1=0;
                    i1=0;
                }

                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(100, 150)); //adjust the size as needed
                buttons[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBorderPainted(false);
                //set a label for each card which is the name of the card
                buttons[i][j].add(new JLabel(board.getPlayers()[i].getCards()[i1][i1].getUV().getName()));
                //set label to transparent if the card is not visible
                if (!board.getPlayers()[i].getCards()[i1][i1].isVisible()) {
                    buttons[i][j].setForeground(new Color(0, 0, 0, 0));
                }


                i1++;

                panels[i].add(buttons[i][j]);
            }
        }


        //create a button exit
        JButton exit = new JButton("Draw");
        exit.setPreferredSize(new Dimension(100, 50));

        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BorderLayout());
        exitPanel.setPreferredSize(new Dimension(100, 50)); //adjust the size as needed
        frame.add(exit, BorderLayout.CENTER);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isclicked = true;
                frame.dispose();
            }
        });

        //draw button
        JButton draw = new JButton("Draw");
        draw.setPreferredSize(new Dimension(100, 50));
        frame.add(draw, BorderLayout.CENTER);

        //event listener for the draw button
        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isclicked = true;
                frame.dispose();
            }
        });


        //add a button in which the discard pile will be displayed
        JButton discard = new JButton("Défausse");
        discard.setPreferredSize(new Dimension(100, 50));
        frame.add(discard, BorderLayout.CENTER);

        //add event listener for each button
        for (int i = 0; i < board.getNbPlayers(); i++) {
            for (int j = 0; j < board.getPlayers()[0].getCards().length; j++) {
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //if the card is visible, the player can play it
                        if (board.getPlayers()[finalI].getCards()[finalI][finalJ].isVisible()) {
                            isclicked = true;
                            frame.dispose();
                        }
                    }
                });
        }
        }


        frame.setVisible(true);
    }


}
