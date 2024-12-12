import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level3 {
    public static void main(String[] args) throws IOException {
        String name = "level3_5";
        String input = "./%s.in".formatted(name);
        String output = "./out/%s.out".formatted(name);

        Files.deleteIfExists(Path.of(output));
        List<String> lines = Files.readAllLines(Path.of(input));
        Files.createFile(Path.of(output));

        lines.removeFirst();

        for (String line : lines) {
            List<Integer> dims = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            int cur = 1;
            int[][] matrix = new int[dims.get(1)][dims.get(0)];
            int count = 0;

            for (int i = 0; i < dims.get(1); i++) {
                for (int j = 0; j + 2 < dims.get(0); j += 3) {
                    for (int k = 0; k < 3; k++) {
                        matrix[i][j + k] = cur;
                    }
                    cur++;
                }
            }

            for (int j = dims.get(0) - 1; j >= 0; j--) {
                if (matrix[0][j] != 0) {
                    break;
                }
                for (int i = 0; i + 2 < dims.get(1); i += 3) {
                    if (matrix[i][j] != 0) {
                        break;
                    }
                    for (int k = 0; k < 3; k++) {
                        matrix[i + k][j] = cur;
                    }
                    cur++;
                }
            }

            for (int[] row : matrix) {
                String res = String.join(" ", Arrays.stream(row).mapToObj(String::valueOf).toList());
                Files.writeString(Path.of(output), res + "\n", StandardOpenOption.APPEND);
            }
            Files.writeString(Path.of(output), "\n", StandardOpenOption.APPEND);
        }
    }
}