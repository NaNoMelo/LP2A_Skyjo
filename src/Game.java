import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game {
    private Board board;
    private JFrame frame;
    public boolean gameStatus = false;
    public boolean isclicked = false;
    private Card  card;
    private int nbPlayers;
    private JButton[][] buttons; // Declare as a class-level variable
    JTextArea textArea = new JTextArea(2, 1);

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
    public void menu() throws IOException {
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
                if (Integer.parseInt(field.getText()) > 0 && Integer.parseInt(field.getText()) < 9) {
                    System.out.println("Number of players is valid");
                    //create a board
                    try {
                        board = new Board(Integer.parseInt(field.getText()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
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
    public void show_board() {
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
        buttons = new JButton[board.getNbPlayers()][12];
        for (int i = 0; i < board.getNbPlayers(); i++) {
            for (int j = 0; j < 12; j++) {
                i1 = j % 4;
                j1 = Math.abs(j / 4);
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(100, 150)); //adjust the size as needed
                //create two lines for the text


                String cardName = board.getPlayers()[i].getCards()[i1][j1].getUV().getName();
                String score = String.valueOf(board.getPlayers()[i].getCards()[i1][j1].getScore());
                String text = cardName + "ECTS :" + score;
                buttons[i][j].setText("<html><center>" + text.replaceAll("\\n", "<br>") + "</center></html>");
                //set the color of the text
                //if the card is visible, the button is green

                if (board.getPlayers()[i].getCards()[i1][j1].isVisible()) {
                    buttons[i][j].setBackground(Color.WHITE);
                }
                //if the card is not visible, the button is red
                else {
                    //background is the same color as the text
                    buttons[i][j].setBackground(Color.black);
                }
                panels[i].add(buttons[i][j]);

            }
        }


        //create a button exit
        JButton exit = new JButton("exit");
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
                //draw a card
                popUp("Vous avez pioché une carte");
                //ask the player if he wants to play the card
                int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous jouer la carte ?", "Choix", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    //play the card
                    popUp("Vous avez joué la carte");
                } else {
                    //discard the card
                    board.
                }


            }
        });


        //add a button in which the discard pile will be displayed
        JButton discard = new JButton("Défausse");
        discard.setPreferredSize(new Dimension(100, 50));
        frame.add(discard, BorderLayout.CENTER);

        //add event listener for each button
        for (int i = 0; i < board.getNbPlayers(); i++) {
            for (int j = 0; j < 12; j++) {
                int finalI = i;
                int finalJ = j;
                int i2 = j%4;
                int j2 = Math.abs(j/4);
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //check if it's the player's turn
                        if(board.getTurn()==finalI){
                            //found which button was clicked
                            System.out.println("Button clicked: " + buttons[finalI][finalJ].getText());
                            //found which card belongs to the button
                            System.out.println("Card clicked: " + board.getPlayers()[finalI].getCards()[i2][j2].getUV().getName());
                            //set the card to visible
                            board.getPlayers()[finalI].getCards()[i2][j2].setVisible(true);
                            isclicked = true;
                            int i1 = 0;
                            int j1 = 0;
                            for (int i = 0; i < board.getNbPlayers(); i++) {
                                for (int j = 0; j < 12; j++){
                                    i1 = j%4;
                                    j1 = Math.abs(j/4);
                                    if (!board.getPlayers()[i].getCards()[i1][j1].isVisible()){
                                        //set foreground color to transparent
                                        buttons[i][j].setForeground(new Color(0, 0, 0, 0));
                                    } else {
                                        //transparent foreground
                                        buttons[i][j].setForeground(Color.BLACK);
                                    }
                                }
                            }
                        } else {
                            popUp("Ce n'est pas votre tour");
                        }
                        frame.repaint();
                    }
                });
            }
        }
        frame.setVisible(true);
    }
    //refreashe the board
    public void refresh() {
        int i1 = 0;
        int j1 = 0;
        //remove all the text areas

        for (int i = 0; i < board.getNbPlayers(); i++) {
            for (int j = 0; j < 12; j++){
                i1 = j%4;
                j1 = Math.abs(j/4);
                if (board.getPlayers()[i].getCards()[i1][j1].isVisible()) {
                    buttons[i][j].setBackground(Color.WHITE);
                }
                //if the card is not visible, the button is red
                else {
                    buttons[i][j].setBackground(Color.BLACK);
                }
            }
            //set foreground color to transparent
        }
        frame.repaint();
    }
//create a pop up
    public void popUp(String message) {
        JFrame frame = new JFrame("Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLabel label = new JLabel(message);
        JButton button = new JButton("OK");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(label);
        frame.add(button);
        frame.setVisible(true);
    }

    public void ask(String question) {
        JFrame frame = new JFrame(question);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLabel label = new JLabel(question);
        JTextField field = new JTextField(1);
        JButton button = new JButton("OK");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(label);
        frame.add(button);
        frame.setVisible(true);


    }

    //getters and setters
    public boolean isIsclicked() {
        return isclicked;
    }
    public Board getBoard() {
        return board;
    }
}
