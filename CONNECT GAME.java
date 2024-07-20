import java.util.Random;
import java.util.Scanner;

public class ConnectFourAI {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = ' ';
    private static final char PLAYER = 'X';
    private static final char AI = 'O';
    private static char[][] board = new char[ROWS][COLS];
    private static Random random = new Random();

    public static void main(String[] args) {
        initializeBoard();
        boolean playerTurn = true;
        boolean gameWon = false;

        while (true) {
            printBoard();
            int col;
            if (playerTurn) {
                col = getPlayerMove();
            } else {
                col = getAIMove();
            }
            int row = dropPiece(col, playerTurn ? PLAYER : AI);
            
            if (checkWin(row, col)) {
                printBoard();
                System.out.println((playerTurn ? "Player" : "AI") + " wins!");
                gameWon = true;
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("The game is a draw!");
                break;
            }

            playerTurn = !playerTurn;
        }
        
        if (!gameWon) {
            System.out.println("Thanks for playing!");
        }
    }

    private static void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = EMPTY;
            }
        }
    }

    private static void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print("| " + board[row][col] + " ");
            }
            System.out.println("|");
        }
        for (int col = 0; col < COLS; col++) {
            System.out.print("--- ");
        }
        System.out.println();
    }

    private static int getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int col;
        while (true) {
            System.out.print("Player (X), choose a column (0-6): ");
            col = scanner.nextInt();
            if (col >= 0 && col < COLS && board[0][col] == EMPTY) {
                break;
            } else {
                System.out.println("Invalid column. Try again.");
            }
        }
        return col;
    }

    private static int getAIMove() {
        int col;
        while (true) {
            col = random.nextInt(COLS);
            if (board[0][col] == EMPTY) {
                System.out.println("AI (O) chooses column: " + col);
                break;
            }
        }
        return col;
    }

    private static int dropPiece(int col, char player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                board[row][col] = player;
                return row;
            }
        }
        return -1; // This should never happen
    }

    private static boolean checkWin(int row, int col) {
        char player = board[row][col];
        return checkDirection(row, col, 1, 0, player) + checkDirection(row, col, -1, 0, player) > 2 || // Vertical
               checkDirection(row, col, 0, 1, player) + checkDirection(row, col, 0, -1, player) > 2 || // Horizontal
               checkDirection(row, col, 1, 1, player) + checkDirection(row, col, -1, -1, player) > 2 || // Diagonal /
               checkDirection(row, col, 1, -1, player) + checkDirection(row, col, -1, 1, player) > 2;  // Diagonal \
    }

    private static int checkDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 0;
        for (int i = 1; i < 4; i++) {
            int r = row + dRow * i;
            int c = col + dCol * i;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == player) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private static boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }
}
