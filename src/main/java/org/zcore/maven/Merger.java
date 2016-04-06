package org.zcore.maven;

import java.io.File;
import java.util.Arrays;

/**
 * PoJo holding information for text file merging
 * 
 * @author Robert Heine
 *
 */
public class Merger {

	/**
	 * The target file, where all other files are copied to
	 * 
	 * @parameter
	 * @required
	 */
	private transient File target;

	/**
	 * Array of possible source files
	 * 
	 * @parameter
	 * @required
	 */
	private transient File[] sources;

	/**
	 * If this is set, strip all newlines and rewrite them with specified characters
	 * 
	 * @parameter
	 */
	private transient String rewriteNewlines;

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
		/**
		 * Append rewriting char for newlines
		 * 
		 * @since 2013-02-12
		 */
		if (null != rewriteNewlines) {
			buffer.append("[newline: ").append(rewriteNewlines).append(']');
		}
		// return
		return buffer.toString();
	}
}
