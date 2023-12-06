import java.util.List;
import java.util.stream.IntStream;

class Judge {
    private List<List<Board>> board;

    public Judge(List<List<Board>> board) {
        this.board = board;
    }

    public boolean checkWin(Board b) {  // check sequence of 8 ways
        return checkSequence(b, -1, 0) || checkSequence(b, 1, 0) ||
               checkSequence(b, 0, -1) || checkSequence(b, 0, 1) ||
               checkSequence(b, -1, -1) || checkSequence(b, 1, 1) ||
               checkSequence(b, -1, 1) || checkSequence(b, 1, -1);
    }

    @FunctionalInterface
    interface Validator {
        boolean isValid(int x, int y);
    }

    private boolean checkSequence(Board b, int dx, int dy) {
        int x = b.x;
        int y = b.y;
        char state = b.state;
        
        Validator isValid = (xVal, yVal) ->
            xVal >= 0 && xVal < board.size() && yVal >= 0 && yVal < board.get(0).size();

        long cnt = IntStream.iterate(0, i -> i + 1)
                .takeWhile(i -> isValid.isValid(x + dx * i, y + dy * i))
                .filter(i -> board.get(x + dx * i).get(y + dy * i).state == state)
                .count();

        return cnt >= 5;
    }
}
