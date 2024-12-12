import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Level4 {
    public static void main(String[] args) throws IOException {
        String name = "level4_2";
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

            for (int i = 0; i < dims.get(1); i+=2) {
                for (int j = 0; j + 2 < dims.get(0); j += 4) {
                    for (int k = 0; k < 3; k++) {
                        matrix[i][j + k] = cur;
                    }
                    cur++;
                }
            }

            for (int j = dims.get(0) - 1; j >= 0; j-=2) {
                if (matrix[0][j] != 0 || matrix[0][j - 1] != 0) {
                    break;
                }
                for (int i = 0; i + 2 < dims.get(1); i += 4) {
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
            System.out.println(cur);
        }
    }
}