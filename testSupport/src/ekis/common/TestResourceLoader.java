package ekis.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class TestResourceLoader {

    public static Integer[] loadIntegersFrom(String resourceName) throws URISyntaxException, IOException {
        return readZip(resourceName).map(Integer::valueOf).toArray(Integer[]::new);
    }

    private static Stream<String> readZip(String resourceName) throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
        ZipFile zipFile = new ZipFile(path.toFile());
        System.out.println("Reading ZIP file  -> " + zipFile.getName());
        ZipEntry zipEntry = zipFile.entries().nextElement();
        System.out.println("Reading ZIP entry -> " + zipEntry.getName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry)));
        return reader.lines().parallel().map(String::trim);
    }
}
