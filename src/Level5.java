import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Level5 {
    private static int target = 0;
    private static int[][] resultMatrix = null;

    public static void main(String[] args) throws IOException {
        String name = "level5_1";
        String input = "./%s.in".formatted(name);
        String output = "./out/%s.out".formatted(name);

        Files.deleteIfExists(Path.of(output));
        List<String> lines = Files.readAllLines(Path.of(input));
        Files.createFile(Path.of(output));

        lines.removeFirst();

        for (String line : lines) {
            resultMatrix = null;
            List<Integer> dims = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

            target = dims.get(2);

            int resCount = arrange(0, 0, new int[dims.get(1)][dims.get(0)], 0);
            System.out.println("resCount = " + resCount);

            for (int[] row : resultMatrix) {
                String res = String.join("", Arrays.stream(row).mapToObj((x) -> {
                    if (x == 0) {
                        return ".";
                    } else {
                        return "X";
                    }
                }).toList());
                Files.writeString(Path.of(output), res + "\n", StandardOpenOption.APPEND);
            }
            Files.writeString(Path.of(output), "\n", StandardOpenOption.APPEND);
        }
    }

    public static int arrange(int row, int col, int[][] matrix, int cur) {
        if ((resultMatrix != null) || row >= matrix.length || col >= matrix[0].length) {
            return cur;
        }

        boolean isLastCol = col == matrix[0].length - 1;
        int newCol = isLastCol ? 0 : col + 1;
        int newRow = isLastCol ? row + 1 : row;

        if (isValidVerticalDesk(row, col, matrix)) {
            matrix[row][col] = 1;
            matrix[row + 1][col] = 1;
            cur = arrange(newRow, newCol, matrix, cur + 1);

            if (cur != target) {
                matrix[row][col] = 0;
                matrix[row + 1][col] = 0;
            } else {
                if (resultMatrix == null) {
                    resultMatrix = matrix;
                }
            }
        }
        if (isValidHorizontalDesk(row, col, matrix)) {
            matrix[row][col] = 1;
            matrix[row][col + 1] = 1;
            cur = arrange(newRow, newCol, matrix, cur + 1);

            if (cur != target) {
                matrix[row][col] = 0;
                matrix[row][col + 1] = 0;
            } else {
                if (resultMatrix == null) {
                    resultMatrix = matrix;
                }
            }
        }

        if (cur != target) {
            cur = arrange(newRow, newCol, matrix, cur);
        }

        return cur;
    }

    private static boolean isValidHorizontalDesk(int row, int col, int[][] matrix) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length - 1 || matrix[row][col] != 0 || matrix[row][col + 1] != 0) {
            return false;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if(i == 0 && j == 1) {
                    continue;
                }
                if (row + i >= 0 && row + i < matrix.length && col + j >= 0 && col + j < matrix[0].length && matrix[row + i][col + j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isValidVerticalDesk(int row, int col, int[][] matrix) {
        if (row < 0 || row >= matrix.length - 1 || col < 0 || col >= matrix[0].length || matrix[row][col] != 0 || matrix[row + 1][col] != 0) {
            return false;
        }

        for (int i = -1; i <= 2; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if(i == 1 && j == 0) {
                    continue;
                }
                if (row + i >= 0 && row + i < matrix.length && col + j >= 0 && col + j < matrix[0].length && matrix[row + i][col + j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }
}