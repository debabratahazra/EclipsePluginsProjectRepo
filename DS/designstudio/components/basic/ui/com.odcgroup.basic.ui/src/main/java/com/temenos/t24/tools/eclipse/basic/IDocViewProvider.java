package com.temenos.t24.tools.eclipse.basic;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.part.MultiPageEditorPart;

public interface IDocViewProvider {
   public void enableView(IFolderLayout right, IFolderLayout bottom);
   public void refreshView();
   public void buildView(MultiPageEditorPart editor);
   public void showView(String partName);
}
