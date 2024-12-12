import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level2 {
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
            int cur = 1;
            int count = 0;
            for(int i = 0; i < dims.get(1); i++) {
                List<String> res = new ArrayList<>();
                for(int j = 0; j < dims.get(0); j++) {
                    res.add(String.valueOf(cur));
                    count++;
                    if (count == 3) {
                        cur++;
                        count = 0;
                    }
                }
                Files.writeString(Path.of(output), String.join(" ", res)+ "\n", StandardOpenOption.APPEND);
            }
            Files.writeString(Path.of(output), "\n", StandardOpenOption.APPEND);
        }
    }
}