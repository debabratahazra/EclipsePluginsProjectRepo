package samplecode.source;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveFactory implements IPerspectiveFactory {
	
	private static final float LEFT_RATIO = 0.25F;
	
	private static final float RIGHT_RATIO = 0.75F;
	
	private static final float BOTTOM_RATIO = 0.75F;
	
	@Override
	public void createInitialLayout(IPageLayout layout) {
		
		String editorArea = layout.getEditorArea();
		
		// Add View in left side area of the editor space
		IFolderLayout leftLayout = layout.createFolder("leftLayout", IPageLayout.LEFT, LEFT_RATIO, editorArea);
		leftLayout.addView(IPageLayout.ID_PROJECT_EXPLORER);
		leftLayout.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		
		// Add View in right side area of the editor space
		IFolderLayout rightLayout = layout.createFolder("rightLayout", IPageLayout.RIGHT, RIGHT_RATIO, editorArea);
		rightLayout.addView(IPageLayout.ID_OUTLINE);
		rightLayout.addPlaceholder(IPageLayout.ID_TASK_LIST);
		
		// Add View in the bottom area in separate way
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, BOTTOM_RATIO, editorArea);
		
		// Add View shortcut which will appear under "Window -> Show View" Menu
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		
		// Add Fast View in the perspective which will appear in the bottom-left corner icon.
		layout.addFastView(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addFastView(IPageLayout.ID_OUTLINE, RIGHT_RATIO);
	}
}
