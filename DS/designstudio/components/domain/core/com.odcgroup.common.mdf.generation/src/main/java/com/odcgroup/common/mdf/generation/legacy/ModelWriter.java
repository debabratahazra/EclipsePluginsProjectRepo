package com.odcgroup.common.mdf.generation.legacy;

import java.io.IOException;
import java.util.Collection;

import com.odcgroup.mdf.MessageSource;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;



/**
 * TODO: DOCUMENT ME!
 *
 * @version $revision$
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public interface ModelWriter extends MessageSource {
    /**
     * Returns a descriptive name for the writer.
     *
     * @return a descriptive name for the writer.
     */
    public String getName();

	/**
	 * Writes the given model to the given root folder.
	 *
	 * @param domain the MDF domain to write
	 * @param root the root folder to write to
	 *
	 * @throws IOException If something went wrong
	 */
	public void write(MdfDomain domain, Collection<MdfDomain> importedDomains, OutputStreamFactory factory)
		throws IOException;
}
