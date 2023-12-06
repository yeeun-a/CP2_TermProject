import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Omok extends JFrame {
    final int SIZE = 20;    // size of Board 
    List<List<Board>> board;
    private Judge judge;
    List<String> messages;
    ImageIcon img = new ImageIcon("img/empty.png");
    ImageIcon white = new ImageIcon("img/white.png");
    ImageIcon black = new ImageIcon("img/black.png");
    ImageIcon turn = black;

    public Omok() {
        Container c = getContentPane();
        c.setLayout(new GridLayout(SIZE, SIZE));
        board = new ArrayList<>();
        judge = new Judge(board);
        messages = readMessagesFromFile("src/messages.txt");
        myActionListener goAction = new myActionListener();

        for (int i = 0; i < SIZE; i++) {
            List<Board> row = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                Board b = new Board(i, j, img);
                row.add(b);
                c.add(b);
                b.addActionListener(goAction);
                b.setBorderPainted(false);
            }
            board.add(row);
        }

        setTitle("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE * 40, SIZE * 40);
        setVisible(true);
    }

    class myActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board clickedBoard = (Board) e.getSource();
            if (turn == white) {
                clickedBoard.setIcon(white);
                clickedBoard.state = 'W';
                turn = black;
            } else {
                clickedBoard.setIcon(black);
                clickedBoard.state = 'B';
                turn = white;
            }
            if (judge.checkWin(clickedBoard)) {
                String gameOverMessage = messages.get(0);
                if (clickedBoard.state == 'B') {  // if black wins
                    String blackWinMessage = messages.get(1);
                    JOptionPane.showMessageDialog(null, blackWinMessage, gameOverMessage, JOptionPane.INFORMATION_MESSAGE);
                } else {    // if white wins
                    String whiteWinMessage = messages.get(2);
                    JOptionPane.showMessageDialog(null, whiteWinMessage, gameOverMessage, JOptionPane.INFORMATION_MESSAGE);
                }
                System.exit(0);
            }
            clickedBoard.removeActionListener(this);
        }
    }

    private List<String> readMessagesFromFile(String filename) {
        List<String> messages = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
