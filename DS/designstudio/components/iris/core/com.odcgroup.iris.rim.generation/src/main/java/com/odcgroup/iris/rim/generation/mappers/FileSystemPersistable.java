package com.odcgroup.iris.rim.generation.mappers;


/**
 * The <code>FileSystemPersistable</code> interface describes the minimum capabilities of an object that would like to be persisted to the file system
 * by {@link ModelsGenEdgeFileWriter} <i>(or indeed any similar class, should anyone ever create one)</i>.
 *
 * @author Simon Hayes
 */
public interface FileSystemPersistable {
    
    /** @return the pathless name of the file to which this instance is to be persisted */ 
    String getFilename();
    
    /** @return a description (for logging purposes) of what kind of the sort of file that this instance is being persisted to (e.g. "COS properties") */
    String getFileDescription();
    
    /** @return the content that is to be written to {@link #getFilename()} */
    CharSequence getFileContent();
    
}
