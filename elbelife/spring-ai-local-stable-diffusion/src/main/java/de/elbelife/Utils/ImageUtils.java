package de.elbelife.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtils {

    public static Path saveBase64Image(String base64, String style, String output) throws IOException {
        byte[] data = Base64.getDecoder().decode(base64);
        Path path = Paths.get(output, style + System.currentTimeMillis() + ".png");
        System.out.println(path.toAbsolutePath());
        Files.createDirectories(path.getParent());
        Files.write(path, data);
        return path;
    }
}
