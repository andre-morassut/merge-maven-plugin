package org.zcore.maven;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Merge specified text files into a new file.
 *
 * @author Robert Heine
 * @author André Morassut
 * @author Vincent van ’t Zand
 * @requiresProject true
 */
@Mojo(name = "merge", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class MergeMojo extends AbstractMojo {

	/**
	 * <pre>
	 * Configuration through the POM file:
	 *
	 * &lt;configuration>
	 *   &lt;mergers>
	 *     &lt;merger>
	 *       &lt;sources>
	 *       &lt;target>${build.outputDirectory}/target.txt&lt;/target>
	 *         &lt;source>src/main/config/${property}/application.txt&lt;/source>
	 *         &lt;source>src/main/config/extended/application.txt&lt;/source>
	 *         &lt;source>src/main/config/default/application.txt&lt;/source>
	 *       &lt;/sources>
	 *       &lt;rewriteNewlines>${newline.character}&lt;/rewriteNewlines>
	 *       &lt;newLineBetween>false&lt;/newLineBetween>
	 *     &lt;/merger>
	 *   &lt;/mergers>
	 * &lt;/configuration>
	 * </pre>
	 */
	@Parameter(property = "mergers", required = true)
	private transient Merger[] mergers;

	/**
	 * Opens an OutputStream, based on the supplied file
	 *
	 * @param file {@linkplain File} the output file
	 * @return {@linkplain OutputStream} the output stream to the parameter file
	 * @throws MojoExecutionException if file is a directory, not writable, or other problem
	 */
	protected OutputStream initOutput(final File file) throws MojoExecutionException {
		// stream to return
		final OutputStream stream;
		// plenty of things can go wrong...
		try {
			// directory?
			if (file.isDirectory()) {
				throw new MojoExecutionException("File " + file.getAbsolutePath() + " is directory!");
			}
			// already exists && can't remove it?
			if (file.exists() && !file.delete()) {
				throw new MojoExecutionException("Could not remove file: " + file.getAbsolutePath());
			}
			// get directory above file file
			final File fileDirectory = file.getParentFile();
			// does not exist && create it?
			if (!fileDirectory.exists() && !fileDirectory.mkdirs()) {
				throw new MojoExecutionException("Could not create directory: " + fileDirectory.getAbsolutePath());
			}
			// moar wtf: parent directory is no directory?
			if (!fileDirectory.isDirectory()) {
				throw new MojoExecutionException("Not a directory: " + fileDirectory.getAbsolutePath());
			}
			// file file is for any reason not creatable?
			if (!file.createNewFile()) {
				throw new MojoExecutionException("Could not create file: " + file.getAbsolutePath());
			}
			// finally create some file
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new MojoExecutionException("Could not find file: " + file.getAbsolutePath(), e);
		} catch (IOException e) {
			throw new MojoExecutionException("Could not write to file: " + file.getAbsolutePath(), e);
		}
		// return
		return stream;
	}

	/**
	 * Opens an InputStream, based on the supplied file
	 *
	 * @param file {@linkplain File} the source file
	 * @return {@linkplain InputStream} the input stream of the parameter file
	 * @throws MojoExecutionException If file has a problem
	 */
	protected InputStream initInput(final File file) throws MojoExecutionException {
		InputStream stream;
		try {
			if (file.isDirectory()) {
				throw new MojoExecutionException("File " + file.getAbsolutePath() + " is a directory!");
			}
			if (!file.exists()) {
				throw new MojoExecutionException("File " + file.getAbsolutePath() + " does not exist!");
			}
			stream = new FileInputStream(file);
			// append to outfile here
		} catch (FileNotFoundException e) {
			throw new MojoExecutionException("Could not find file: " + file.getAbsolutePath(), e);
		}
		return stream;
	}

	/**
	 * Factory Execute
	 *
	 * @throws MojoExecutionException if file write operation went wrong
	 * @throws MojoFailureException for plugin failure
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		File[] sources = null;
		ArrayList<InputStream> sourcesIs = null;
		// iterate through all <merge />
		for (Merger merger : mergers) {
			// Initialize input streams
			sources = merger.getSources();
			sourcesIs = new ArrayList<InputStream>(sources.length * 2);
			for (File source : sources) {
				sourcesIs.add(initInput(source)); // add source stream
				if (merger.getNewLineBetween()) {
					sourcesIs.add(new ByteArrayInputStream(System.getProperty("line.separator").getBytes())); // add line separator
				}
			}
			// write to file
			try {
				Files.copy(new SequenceInputStream(Collections.enumeration(sourcesIs)), merger.getTarget().toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new MojoExecutionException("Error during output write.", e);
			}
		}
	}
}
