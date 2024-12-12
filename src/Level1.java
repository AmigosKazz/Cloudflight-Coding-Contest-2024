import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Level1 {
    public static void main(String[] args) throws IOException {
        String name = "level2_5";
        String input = "./%s.in".formatted(name);
        String output = "./out/%s.out".formatted(name);

        Files.deleteIfExists(Path.of(output));
        List<String> lines = Files.readAllLines(Path.of(input));
        Files.createFile(Path.of(output));

        lines.removeFirst();

        for (String line : lines) {
            List<Integer> dims = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            Files.writeString(Path.of(output), "%d\n".formatted(dims.get(0) * dims.get(1) / 3), StandardOpenOption.APPEND);
        }
    }
}