package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;


public class EntitySelector extends Composite {
    private static final String DEFAULT_NAME = "";

    private final Button browseButton;
    private final Collection listeners = new ArrayList();
    private final IContentAssistProvider contentAssistProvider;
    private final Text nameText;
    private final String defaultDomain;

    public EntitySelector(Composite parent, WidgetFactory factory,
            IContentAssistProvider contentAssistProvider, String defaultDomain) {
        super(parent, SWT.NONE);
        this.contentAssistProvider = contentAssistProvider;
        this.setBackground(factory.getBackgroundColor());
        this.setForeground(factory.getForegroundColor());
        this.defaultDomain = defaultDomain;

        GridLayout layout = new GridLayout(2, false);
        layout.marginLeft = 1;
        layout.marginBottom = layout.marginHeight = layout.marginRight = layout.marginWidth = 0;
        this.setLayout(layout);

        nameText = factory.createText(this, null);
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        ContentAssistProcessor.install(nameText, contentAssistProvider);

        browseButton = factory.createButton(this, "&Browse...", SWT.NULL);

        registerListeners();
    }

    public void setEnabled(boolean enable) {
        nameText.setEnabled(enable);
        browseButton.setEnabled(enable);
    }

    public void setSelection(MdfEntity entity) {
        if (entity != null) {
       		entity = getResolvedMdfEntity((MdfEntityImpl) entity);
            setSelection(entity.getQualifiedName());
        } else {
            setSelection(DEFAULT_NAME);
        }
    }

    /**
	 * @param entity
	 */
    private MdfEntity getResolvedMdfEntity(MdfEntityImpl entity) {
    	if(entity.eIsProxy()) {
    		String names[] = entity.getName().split("\\.");
        	InternalEObject eobj = (InternalEObject) entity;
        	URI uri = eobj.eProxyURI().trimFragment();
    		if (uri.isPlatformResource()){
    			String path = uri.toPlatformString(true);
    			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
    	    	ResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, file.getProject());
    			Resource res = rs.getResource(uri, true);
    			if (res != null) {
    				MdfDomain domain = (MdfDomain) res.getContents().get(0);
    				if(names != null && names.length > 1)
    					return domain.getEntity(names[1]);
    			}
    		}
    	}
		return entity;
	}

	public void setSelection(String name) {
        nameText.setText((name == null) ? DEFAULT_NAME : name);
    }

    public void setSelection(MdfName name) {
        if (name != null) {
            if (name.getDomain().equals(defaultDomain)) {
                setSelection(name.getLocalName());
            } else {
                setSelection(name.toString());
            }
        } else {
            setSelection(DEFAULT_NAME);
        }
    }

    public MdfName getSelection() {
    	MdfName selection = null;
        String name = nameText.getText().trim();
        if (name.length() > 0) {
        	selection = MdfNameFactory.createMdfNameFromPossibleFQN(name, defaultDomain);
        }
   		return selection;
    }

	/**
	 * @param selection
	 * @return
	 */
	public boolean isSelectionValid() {
		MdfName selection = getSelection();
		if (selection == null) {
			return true;
		}
		
		for (MdfModelElement element : contentAssistProvider.getCandidates()) {
			if (selection.equals(element.getQualifiedName())) {
				return true;
			}
		}
		return false;
	}

	public void addModifyListener(ModifyListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeModifyListener(ModifyListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void notifyModifyListeners(ModifyEvent e) {
        synchronized (listeners) {
            Iterator it = listeners.iterator();

            while (it.hasNext()) {
                ModifyListener listener = (ModifyListener) it.next();
                listener.modifyText(e);
            }
        }
    }

    protected void registerListeners() {
        nameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                Event evt = new Event();
                evt.widget = EntitySelector.this;
                notifyModifyListeners(new ModifyEvent(evt));
            }
        });

        browseButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                onBrowse();
            }
        });
    }

    private void onBrowse() {
        MdfEntitySelectionDialog dialog =
                MdfEntitySelectionDialog.createDialog(getShell(),
                        contentAssistProvider);

        String name = nameText.getText().trim();
        if (name.length() > 0) {
            dialog.setSelection(MdfNameFactory.createMdfNameFromPossibleFQN(name, defaultDomain));
        }

        if (dialog.open() == Window.OK) {
            setSelection((MdfEntity) dialog.getFirstResult());
        }
    }

}
