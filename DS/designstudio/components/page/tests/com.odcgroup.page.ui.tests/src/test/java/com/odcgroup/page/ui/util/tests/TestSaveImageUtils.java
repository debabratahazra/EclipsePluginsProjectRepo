package com.odcgroup.page.ui.util.tests;
import org.junit.Test;

/**
 * This is the test class for the SaveConditionsUtils class
 * 
 * @author Alexandre Jaquet
 *
 */
public class TestSaveImageUtils  {

	/** 
	 * Test the save images method.
	 * 
	 *  @throws Exception
	 */
	@Test
	public void testSaveImages() throws Exception  {
		// TODO GHA Reactive me
		/*IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		Bundle b = PageUIPlugin.getDefault().getBundle();
		IProject project  = root.getProject("test");
		IFolder folder = project.getFolder("module");
		URL url = FileLocator.find(b, new Path("src/test/resources/condition.module"), Collections.emptyMap());				
		InputStream input = url.openStream();
		IFile file = folder.getFile("condition.module");
		if (!project.exists()) {
			project.create(null);
		}
		if (!project.isOpen()) {
			project.open(null);
		}
		if (!folder.exists()) { 
		    folder.create(IResource.NONE, true, null);
		}
		if (!file.exists()) {		    
		    file.create(input, IResource.NONE, null);
		}
		IPath path = new Path("w:");
		int imageFormat = SWT.IMAGE_GIF;
		SaveImageUtils.createImages(file, path, imageFormat);*/	
	}

}
