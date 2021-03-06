package org.zcore.maven;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Arrays;

/**
 * PoJo holding information for text file merging
 *
 * @author Robert Heine
 * @author Vincent van ’t Zand
 */
public class Merger {

	/**
	 * The target file, where all other files are copied to
	 */
	@Parameter(property = "target", required = true)
	private transient File target;

	/**
	 * Array of possible source files
	 */
	@Parameter(property = "sources", required = true)
	private transient File[] sources;

	/**
	 * If this is set, strip all newlines and rewrite them with specified characters
	 */
	@Parameter(property = "rewriteNewlines", required = false)
	private transient String rewriteNewlines;

	/**
	 * This determines if a new line character is written after each file used in the merge. The
	 * default value is true for backwards compatibility.
	 */
	@Parameter(property = "newLineBetween", required = false, defaultValue = "true")
	private transient Boolean newLineBetween = true;

	/**
	 * Returns the target filename
	 *
	 * @return {@linkplain File} target file
	 */
	public File getTarget() {
		return target;
	}

	/**
	 * Returns the array of source filenames
	 *
	 * @return array of {@linkplain File}
	 */
	public File[] getSources() {
		return sources;
	}

	/**
	 * Returns rewriting newlines
	 *
	 * @return rewriteNewLines attribute
	 */
	public String getRewriteNewlines() {
		return rewriteNewlines;
	}

	/**
	 * Returns if new lines should be added between each of the files to merge
	 *
	 * @return Value of newLineBetween attribute
	 */
	public Boolean getNewLineBetween() {
		return newLineBetween;
	}

	/**
	 * Overriding toString() here for debugging
	 *
	 * @return {@linkplain String} string representation
	 */
	@Override
	public String toString() {
		// buffer to return
		final StringBuilder buffer = new StringBuilder(40);
		// append stuff
		buffer.append("Merger [toString()=").append(super.toString()).append(']');
		buffer.append("[target: ").append(getTarget().toString()).append(']');
		buffer.append("[source: ").append(Arrays.asList(getSources().toString())).append(']');
		if (null != rewriteNewlines) {
			buffer.append("[newline: ").append(rewriteNewlines).append(']');
		}
		buffer.append("[newLineBetween: ").append(getNewLineBetween() ? "yes" : "no").append(']');
		// return
		return buffer.toString();
	}
}
