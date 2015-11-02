package pt.ist.jpdaplace.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;

public class FileUtil {

    public static void consumeResourceByLines(final String resource, final Consumer<String> consumer) {
        final InputStream inputStream = FileUtil.class.getResourceAsStream(resource);
        try (final Reader reader = new InputStreamReader(inputStream,  "ISO8859-1");
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            for (;;) {
                final String line = bufferedReader.readLine();
                if (line == null)
                    break;
                consumer.accept(line);
            }
        } catch (final IOException ex) {
            throw new Error(ex);
        }
    }

}
