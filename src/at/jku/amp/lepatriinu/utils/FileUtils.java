package at.jku.amp.lepatriinu.utils;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * FileChooser functionality simplified into a slim function.
 * 
 * @author Fabian Jordan (0855941)
 * 
 */
public class FileUtils {

	/**
	 * Opens a <code>JFileChooser</code> dialog window for the user and returns
	 * the chosen <code>File</code> or <code>null</code>.
	 * 
	 * @param currentDirectoryPath
	 *            the directory the dialog should start off with
	 * @param extension
	 *            the extension for which the dialog should filter (e.g. "wav",
	 *            "txt", ...)
	 * @param extDescription
	 *            some nice description of the file extension that is presented
	 *            to the user
	 * @param parent
	 *            The parenting Swing <code>Component</code> on which the dialog
	 *            will be centered. If <code>null</code> the dialog will be
	 *            centered on the screen.
	 * @return the chosen <code>File</code> or <code>null</code> if the user
	 *         aborted the action
	 */
	public static File chooseFile(String currentDirectoryPath,
			final String extension, final String extDescription,
			Component parent) {
		JFileChooser fj = new JFileChooser(currentDirectoryPath);
		if (extension == null) {
			fj.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			fj.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return extDescription;
				}

				@Override
				public boolean accept(File f) {
					if (f.isDirectory())
						return true;
					String name = f.getName();
					int dot = name.lastIndexOf('.');
					if (dot > 0) {
						return name.substring(dot + 1, name.length())
								.equalsIgnoreCase(extension);
					}
					return false;
				}
			});
		}
		if (fj.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
			return fj.getSelectedFile();
		return null;
	}
	
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

	public static FileFilter getFileFilter(final String extension) {
		return new FileFilter() {
			@Override
			public String getDescription() {
				return extension;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return false;
				String name = f.getName();
				return name.endsWith("." + extension);
			}
		};
	}
}