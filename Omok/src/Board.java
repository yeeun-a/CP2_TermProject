import javax.swing.*;

class Board extends JButton {
    int x;
    int y;
    char state;

    public Board(int x, int y, ImageIcon image) {
        super(image);
        this.x = x;
        this.y = y;
        state = 'N';    // N: None, B: Black, W: White
    }
}
