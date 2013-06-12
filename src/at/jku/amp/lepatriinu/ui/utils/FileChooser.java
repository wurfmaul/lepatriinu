package at.jku.amp.lepatriinu.ui.utils;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * FileChooser functionality simplified into a slim function.
 * 
 * @author Fabian Jordan (0855941)
 * 
 */
public class FileChooser {

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
}