package at.jku.amp.lepatriinu.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.filechooser.FileFilter;

/**
 * File operations simplified into a few slim functions.
 * 
 * @author Fabian Jordan (0855941)
 * 
 */
public class FileUtils {

	/**
	 * Reads a file and returns its content.
	 * 
	 * @param filename
	 *            The file
	 * @return The content of the file.
	 */
	public static String readFile(String filename) {
		Path p = Paths.get(filename);
		try (BufferedReader br = Files.newBufferedReader(p,
				StandardCharsets.UTF_8)) {
			return readFile(br);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String readFile(BufferedReader br) throws IOException {
		StringBuffer result = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			result.append(line);
			result.append('\n');
		}

		return result.toString();
	}

	/**
	 * Returns a FileFilter (used in the DataModels of JLists) for a given extension.
	 */
	public static FileFilter getFileFilter(final String extension) {
		return getFileFilter(new String[] { extension });
	}

	/**
	 * Returns a FileFilter (used in the DataModels of JLists) for multiple extensions.
	 */
	public static FileFilter getFileFilter(final String[] extensions) {
		return new FileFilter() {
			@Override
			public String getDescription() {
				return extensions[0];
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return false;
				String name = f.getName();
				for (String s : extensions) {
					if (name.endsWith("." + s))
						return true;
				}
				return false;
			}
		};
	}

	/**
	 * Chops off the extension of a given filename.
	 */
	public static String killExtension(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}
}