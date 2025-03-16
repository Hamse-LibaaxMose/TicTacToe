import java.util.Scanner;

public class TicTacToe {
    private static char[] grid = {
            ' ', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
    };
    private static char currentPlayer = 'X';
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Game started! Player X goes first.");

        while (true) {
            displayGrid();
            System.out.println("Current Player: " + currentPlayer);

            if (currentPlayer == 'X') {
                playerMove();
            } else {
                computerMove();
            }

            if (isWinner(currentPlayer)) {
                displayGrid();
                System.out.println("Player " + currentPlayer + " wins! 🎉");
                break;
            }
            if (isDraw()) {
                displayGrid();
                System.out.println("It's a draw! 😐");
                break;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    // Player's move
    private static void playerMove() {
        int index;
        do {
            System.out.println("Enter index (0-8): ");
            index = scanner.nextInt();
        } while (!isValidMove(index));

        System.out.println("Player X chose index: " + index);
        grid[index] = 'X';
    }

    // AI's move using Minimax with Alpha-Beta Pruning
    private static void computerMove() {
        int bestMoveScore = Integer.MIN_VALUE;
        int bestIndex = -1;

        System.out.println("AI is calculating the best move...");

        for (int i = 0; i < 9; i++) {
            if (grid[i] == ' ') {
                grid[i] = 'O';
                int moveScore = minimax(false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                grid[i] = ' ';

                if (moveScore > bestMoveScore) {
                    bestMoveScore = moveScore;
                    bestIndex = i;
                }
            }
        }

        System.out.println("AI chooses move at index " + bestIndex + " with score: " + bestMoveScore);
        grid[bestIndex] = 'O';
    }

    // Minimax Algorithm with Alpha-Beta Pruning
    private static int minimax(boolean isAITurn, int alpha, int beta) {
        if (isWinner('X')) return -1;
        if (isWinner('O')) return 1;
        if (isDraw()) return 0;

        int bestMoveScore = isAITurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            if (grid[i] == ' ') {
                grid[i] = isAITurn ? 'O' : 'X';
                int moveScore = minimax(!isAITurn, alpha, beta);
                grid[i] = ' ';

                if (isAITurn) {  // AI is maximizing
                    bestMoveScore = Math.max(moveScore, bestMoveScore);
                    alpha = Math.max(alpha, bestMoveScore);
                } else {  // Player X is minimizing
                    bestMoveScore = Math.min(moveScore, bestMoveScore);
                    beta = Math.min(beta, bestMoveScore);
                }

                // **Alpha-Beta Pruning Condition**
                if (beta <= alpha) {
                    break;  // Prune the branch
                }
            }
        }

        return bestMoveScore;
    }

    // Display the board
    private static void displayGrid() {
        System.out.println("\nCurrent Board:");
        System.out.println("-------------");
        for (int i = 0; i < 9; i++) {
            System.out.print("| " + grid[i] + " ");
            if (i % 3 == 2) System.out.println("|\n-------------");
        }
    }

    // Check if a move is valid
    private static boolean isValidMove(int index) {
        return index >= 0 && index < 9 && grid[index] == ' ';
    }

    // Check for a winner
    private static boolean isWinner(char player) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] pattern : winPatterns) {
            if (grid[pattern[0]] == player && grid[pattern[1]] == player && grid[pattern[2]] == player) {
                return true;
            }
        }
        return false;
    }

    // Check for a draw
    private static boolean isDraw() {
        for (char cell : grid) {
            if (cell == ' ') return false;
        }
        return true;
    }
}
