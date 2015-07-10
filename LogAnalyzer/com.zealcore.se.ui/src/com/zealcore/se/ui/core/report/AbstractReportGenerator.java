package com.zealcore.se.ui.core.report;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.IPath;

/**
 * Abstract class for report generators.
 * <p>
 * Extend this class to be able to generate reports and transform them to a
 * specific file format
 * <p>
 * 
 * @see HtmlReportGenerator
 * @author daze
 * 
 */
public abstract class AbstractReportGenerator extends AbstractReport {

    private Collection<IReportContributor> contributors;

    /**
     * Sets the contributors that will be used to contribute to this report Any
     * report data will be cleared.
     * 
     * @param contributors
     *                The set of contributors that will be asked to contribute
     *                to the report. Can not be <code>null</code> or empty.
     * @see AbstractReportGenerator#clearReportData()
     * @see AbstractReportGenerator#generate()
     */
    void setContributors(final Collection<IReportContributor> contributors) {
        if (contributors == null || contributors.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.contributors = contributors;
        clearReportData();
    }

    /**
     * Gathers the report data from the preset contributors
     * <p>
     * Clears the report data and calls
     * {@link IReportContributor#report(AbstractReport)} on each contributor to
     * gather report contributions
     * <p>
     * 
     * @see AbstractReportGenerator#setContributors(Collection)
     * @see AbstractReportGenerator#clearReportData()
     * @see IReportContributor
     */
    void gatherReportData() {
        if (this.contributors.size() == 0) {
            throw new IllegalStateException();
        }

        clearReportData();
        for (final IReportContributor contributor : this.contributors) {
            contributor.fillReport(this);
        }
    }

    /**
     * Implementor should create and return a file representation of the report.
     * <p>
     * For example the {@link HtmlReportGenerator} returns an html-file
     * 
     * @param path
     *                The path to the file
     * @return A {@link File} object representing the report or
     *         <code>null</code> if the report have not been generated
     * @throws IOException
     * 
     * @see HtmlReportGenerator
     */
    abstract File toFile(final IPath path) throws IOException;

    /**
     * Implementor should generate the report and make it ready for immediate
     * access by for example {@link AbstractReportGenerator#toFile(IPath)}
     * 
     * @see AbstractReportGenerator#toFile(IPath)
     */
    abstract void compileReport();
}
