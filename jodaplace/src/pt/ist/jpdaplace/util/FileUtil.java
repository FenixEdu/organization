package pt.ist.jpdaplace.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileUtil {

    public static String[] readResource(final String resource) {
	final InputStream inputStream = FileUtil.class.getResourceAsStream(resource);
	try {
	    final InputStreamReader fileReader = new InputStreamReader(inputStream, "ISO8859-1");
	    try {
		char[] buffer = new char[4096];
		final StringBuilder fileContents = new StringBuilder();
		for (int n = 0; (n = fileReader.read(buffer)) != -1; fileContents.append(buffer, 0, n))
		    ;
		return fileContents.toString().split("\n");
	    } catch (final IOException ex) {
		throw new Error(ex);
	    } finally {
		try {
		    fileReader.close();
		} catch (final IOException ex) {
		    throw new Error(ex);
		}
	    }
	} catch (final UnsupportedEncodingException ex) {
	    throw new Error(ex);
	}
    }

}
