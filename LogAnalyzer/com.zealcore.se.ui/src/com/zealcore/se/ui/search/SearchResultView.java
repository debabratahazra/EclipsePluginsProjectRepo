package com.zealcore.se.ui.search;

import java.text.DecimalFormat;
import java.util.UUID;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.ISearchResultPage;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPageSite;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.ifw.IFWFacade;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.ifw.SearchQuery;
import com.zealcore.se.core.ifw.SearchQuery.SearchInfo;
import com.zealcore.se.core.model.ITimed;
import com.zealcore.se.ui.IconManager;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.CaseFileManager;
import com.zealcore.se.ui.editors.ILogViewInput;

/**
 * Implements a TimeCluster synchronized searchpage.
 * 
 * @author stch
 * 
 */
public class SearchResultView implements ISearchResultPage {

    private ISearchResult search;

    private Object uiState;

    private IPageSite site;

    private Composite container;
    
    private SashForm sashForm; 
    
    private TreeViewer viewer;

    private String id;

    private Action prevAction;

    private Action nextAction;

    private SearchSelectionDropDownAction searchHistory;

    private ISearchResultViewPart part;

    private ISearchResultListener listener;

    private IChangeListener importListener;

	private TreeViewer statisticViewer;
	
	private static final String PATTERN = "#.#########";
	
    private static final String TOTAL = "Complete Range" ;
    
    private static final String CURRENT = "Current Range" ;
            
    public String getID() {
        return id;
    }

    public String getLabel() {
        if (search == null) {
            return "No search results avaiable.";
        }
        return search.getLabel();
    }

    public Object getUIState() {
        return uiState;
    }

    public void restoreState(final IMemento memento) {
        // Cannot save => cannot restore
    }

    public void saveState(final IMemento memento) {
        // cannot save
    }

    public void setID(final String id) {
        this.id = id;

    }


    public void setInput(final ISearchResult search, final Object uiState) {
        this.search = search;
        this.uiState = uiState;
        viewer.setInput(search);
        if (search != null) {
            SearchQuery.SearchInfo info = ((UISearchQuery) search.getQuery())
            .getSearchInfo();
            if (info == null) {
                nextAction.setEnabled(false);
                searchHistory.setEnabled(false);
                prevAction.setEnabled(false);
            } else {
                if (info.getCurrentHitIndex() == SearchQuery.SEARCH_INFO_START_INDEX) {
                    prevAction.setEnabled(false);
                } 
                if (info.getHitsSize() - (info.getCurrentHitIndex() + 1) <= 0) {
                    nextAction.setEnabled(false);
                } else {
                    nextAction.setEnabled(true);
                }
                searchHistory.setEnabled(true);
            }
            search.addListener(listener);
        }else {
        	statisticViewer.setInput(null);
        }
        part.updateLabel();
    }

    public void setViewPart(final ISearchResultViewPart part) {
        this.part = part;
    }

    public IPageSite getSite() {
        return site;
    }

    public void init(final IPageSite site) {
        this.site = site;
    }

    public void createControl(final Composite parent) {
        container = new Composite(parent, SWT.NULL);
        container.setLayout(new FillLayout());    	
    	sashForm = new SashForm(container, SWT.VERTICAL );    	
        viewer = new TreeViewer(sashForm);
        viewer.setContentProvider(new NamedItemContentProvider());
        viewer.setLabelProvider(new NamedItemLabelProvider());
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(final DoubleClickEvent event) {
                final Object element = ((IStructuredSelection) event
                        .getSelection()).getFirstElement();
                if (element instanceof ITimed) {
                    fireTimeEvent((ITimed) element);

                }
            }
        });
        getSite().setSelectionProvider(viewer);

        // Add "Previous" & "Next" Action in Search View (For Log Analyzer Search only)
        IViewPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
        findView("org.eclipse.search.ui.views.SearchView");

        if (part == null) {
            // Search View not found.
            return;
        }
        IActionBars bars = part.getViewSite().getActionBars();
        IToolBarManager mgr = bars.getToolBarManager();

        fillToolbar(mgr, parent);
        
        createStatisticSashForm();

        importListener = new IChangeListener() {
            public void update(final boolean changed) {
                if (changed) {
                    refresh();
                }
            }
        };
        IFWFacade.addChangeListener(importListener);

        listener = new ISearchResultListener() {

            public void searchResultChanged(final SearchResultEvent e) {
                //Run in UI Thread
                Display.getDefault().asyncExec(new Runnable(){
                    public void run() {
                        setInput(e.getSearchResult(), null); 
                        
                        SearchInfo info = ((UISearchQuery) search.getQuery())
                        .getSearchInfo();
                        if(info.getTotalStatistics().getMean() != 0) {
                        	statisticViewer.setInput(new Object());
                        	sashForm.setWeights(new int[] {parent.getBounds().height - 150 , 150});
                        }else {
                        	statisticViewer.setInput(null);
                        	sashForm.setWeights(new int[] {parent.getBounds().height , 0});
                        }
                    }
                });
            }};
    }

    
    private void createStatisticSashForm() {
		
		Composite comp = new Composite(sashForm, SWT.NONE);
		comp.setLayout(new FillLayout());
		
        Tree tree = new Tree(comp, SWT.BORDER);
        
        statisticViewer = new TreeViewer(tree);

        statisticViewer.setContentProvider(new ITreeContentProvider() {
			
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
			
			public void dispose() {
			}
			
			public boolean hasChildren(Object element) {
				return getChildren(element).length > 0;
			}
			
			public Object getParent(Object element) {
				return null;
			}
			
			public Object[] getElements(Object inputElement) {
				return getStatisticsResults(inputElement);
			}
			
			private Object[] getStatisticsResults(Object inputElement) {
						String[] type = {TOTAL, CURRENT};
						return (String[]) new LargeResultWrapper(type).wrap(false);
			}

			public Object[] getChildren(Object parentElement) {
				return new LargeResultWrapper(getChildren2(parentElement)).wrap(false);
			}

			private Object[] getChildren2(Object parentElement) {
				SearchInfo info = ((UISearchQuery) search.getQuery())
						.getSearchInfo();
				DecimalFormat df = new DecimalFormat(PATTERN);
				if (((String) parentElement).equalsIgnoreCase(CURRENT)) {
					Object type[] = {"Minimum : "
									+ df.format(info.getCurrentStatistics().getMinimum()) + " ns",
							"Mean : " + df.format(info.getCurrentStatistics().getMean()) + " ns",
							"Maximum : " + df.format(info.getCurrentStatistics().getMaximum()) + " ns" };
					return type;
				}
				if (((String) parentElement).equalsIgnoreCase(TOTAL)) {
					Object type[] = { "Minimum : "
									+ df.format(info.getTotalStatistics().getMinimum()) + " ns",
							"Mean : " + df.format(info.getTotalStatistics().getMean()) + " ns",
							"Maximum : " + df.format(info.getTotalStatistics().getMaximum()) + " ns" };
					return type;
				}
				return new Object[0];
			}
		});

        statisticViewer.setLabelProvider(new TreeLabelProvider());
	}

    private void refresh() {
        setInput(null, null);
        sashForm.setWeights(new int[] {sashForm.getParent().getBounds().height , 0});
        nextAction.setEnabled(false);
        prevAction.setEnabled(false);
        searchHistory.setEnabled(false);
    }

    // Call at the time of first Search
    public void resetAction() {
        try {
            // Refresh Pull down menu in Search View.
            new SearchSelectionDropDownAction(this);

            if ((prevAction != null) && (nextAction != null)) {
                prevAction.setEnabled(false);
                if (!nextAction.isEnabled()) {
                    nextAction.setEnabled(true);
                }
                prevAction.setChecked(false);
                nextAction.setChecked(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void fillToolbar(final IToolBarManager mgr,  final Composite composite) {

        prevAction = new Action("Previous Search", SWT.PUSH) {
            @Override
            public void run() {
                executeNext(false, composite);
            }
        };
        prevAction.setImageDescriptor(SeUiPlugin.imageDescriptorFromPlugin(
                SeUiPlugin.PLUGIN_ID, "icons/search/search_next.gif"));
        prevAction.setEnabled(false);

        searchHistory = new SearchSelectionDropDownAction(this);

        nextAction = new Action("Next Search", SWT.PUSH) {
            @Override
            public void run() {
                SearchQuery.SearchInfo info = ((UISearchQuery) search.getQuery()).getSearchInfo();
                if ((info.getCurrentHitIndex() + 1) == info.getHitsSize()) {
                    // Disable Next Button
                    nextAction.setEnabled(false);
                    return;
                }
                executeNext(true, composite);
            }
        };
        nextAction.setImageDescriptor(SeUiPlugin.imageDescriptorFromPlugin(
                SeUiPlugin.PLUGIN_ID, "icons/search/search_prev.gif"));
        nextAction.setEnabled(false);

        mgr.add(prevAction);
        mgr.add(searchHistory);
        mgr.add(nextAction);
    }

    protected void executeNext(final boolean next, final Composite composite) {
        SearchQuery.SearchInfo info = ((UISearchQuery) search.getQuery())
        .getSearchInfo();
        if (next) {
            // Its required. Don't delete this line.
            nextAction.setChecked(false);
            info.setCurrentHitIndex(info.getCurrentHitIndex() + 1);
        } else {
            // Its required. Don't delete this line.
            prevAction.setChecked(false);
            info.setCurrentHitIndex(info.getCurrentHitIndex() - 1);
        }
        new DebuggerSearch().performSearchAction(info);
        updateActions();
    }

    private void updateActions() {
        SearchQuery.SearchInfo info = ((UISearchQuery) search.getQuery())
        .getSearchInfo();
        if ((info.getCurrentHitIndex() + 1) == info.getHitsSize()) {
            // Disable Next Button
            nextAction.setEnabled(false);
        } else {
            nextAction.setEnabled(true);
        }

        if (info.getCurrentHitIndex() == SearchQuery.SEARCH_INFO_START_INDEX) {
            // Disable Previous Button
            prevAction.setEnabled(false);
        } else {
            prevAction.setEnabled(true);
        }
    }

    private void fireTimeEvent(final ITimed element) {
        if (search instanceof SearchResult) {
            final SearchResult result = (SearchResult) search;
            final ISearchQuery q = result.getQuery();
            if (q instanceof UISearchQuery) {
                final UISearchQuery uiSearch = (UISearchQuery) q;
                final Logset log = uiSearch.getLogset();
                fireTimeEvent(log, element);
            }
        }
    }

    private void fireTimeEvent(final Logset log, final ITimed element) {
        Assert.isNotNull(log);
        Assert.isNotNull(element);
        final UUID uid = log.getId();
        Assert.isNotNull(uid);
        final ILogViewInput input = CaseFileManager.getInputByUID(uid
                .toString());
        Assert.isNotNull(input);
        input.getTimeCluster().setCurrentTime(element.getTimeReference());
    }

    public void dispose() {
        IFWFacade.removeChangeListener(importListener);
        viewer.getLabelProvider().dispose();
        viewer.getTree().dispose();
    }

    public Control getControl() {
        return container;
    }

    public void setActionBars(final IActionBars actionBars) {}

    public void setFocus() {}

    public class SearchSelectionDropDownAction extends Action implements IMenuCreator {

        public static final String SEPARATOR = "...";

        private Menu searchMenu;

        private class ShowSearchAction extends Action {
            private int index;

            public ShowSearchAction(final ISearchResult search,
                    final String label) {
                super("", AS_RADIO_BUTTON);
                setText(label);
                setImageDescriptor(search.getImageDescriptor());
                setToolTipText(search.getTooltip() + " " + label);
            }

            public void runWithEvent(final Event event) {
                if (this.isChecked()) {
                    index = Integer.parseInt(this.getId());
                    run();
                }
            }

            @Override
            public void run() {
                SearchQuery.SearchInfo info = ((UISearchQuery) search
                        .getQuery()).getSearchInfo();
                info.setCurrentHitIndex(index);
                new DebuggerSearch().performSearchAction(info);
                updateMenuActions();
            }

            private void updateMenuActions() {
                try {
                    String[] menu = getMenuLabel();
                    if (menu == null || menu.length == 1) {
                        nextAction.setEnabled(false);
                        prevAction.setEnabled(false);
                        return;
                    }
                    updateActions();
                } catch (Throwable e) {}
            }
        }

        /**
         * Constructor
         * 
         * @param searchResultView
         */
        public SearchSelectionDropDownAction(
                final SearchResultView searchResultView) {

            setImageDescriptor(SeUiPlugin.imageDescriptorFromPlugin(
                    SeUiPlugin.PLUGIN_ID, "icons/search/search_history.gif"));
            setToolTipText("Search from selected range");
            setMenuCreator(this);
        }

        public void dispose() {
            if (searchMenu != null) {
                searchMenu.dispose();
            }
        }

        @Override
        public void run() {
            if (getMenuLabel() == null) {
                MessageDialog.openInformation(
                        Display.getCurrent().getShells()[0], " Information ",
                "Search result not found.");
            } else {
                MessageDialog.openInformation(
                        Display.getCurrent().getShells()[0], "Information",
                "Please select search range from pulldown menu.");
            }
        }

        public Menu getMenu(final Control parent) {

            ISearchResult result = SearchResult.getInstance();
            dispose();

            searchMenu = new Menu(parent);
            String[] menuLabel = getMenuLabel();
            if (menuLabel == null) {
                // Search result is zero.
                return null;
            }
            for (int i = 0; i < menuLabel.length; i++) {
                ShowSearchAction action = new ShowSearchAction(result,
                        menuLabel[i]);
                addActionToMenu(searchMenu, action);
                action.setId(String.valueOf(i));
                if (((UISearchQuery) search.getQuery()).getSearchInfo().getCurrentHitIndex() == (i + SearchQuery.SEARCH_INFO_START_INDEX)) {
                    action.setChecked(true);
                }
            }
            return searchMenu;
        }

        private void addActionToMenu(final Menu menu,
                final ShowSearchAction action) {
            ActionContributionItem item = new ActionContributionItem(action);
            item.fill(menu, -1);
        }

        private String[] getMenuLabel() {
            String[] label;
            SearchQuery.SearchInfo info = ((UISearchQuery) search.getQuery())
            .getSearchInfo();
            if (info.getTotalEvents() == 0) {
                return null;
            }

            int first = 1;
            label = new String[info.getHitsSize()];
            for (int i = 0; i < info.getHitsSize(); i++) {
                label[i] = first + SEPARATOR
                + (first + info.getHitInfoAtIndex(i).getHitsCount() - 1);
                first += info.getHitInfoAtIndex(i).getHitsCount();
            }
            return label;
        }

        public Menu getMenu(final Menu parent) {
            return null;
        }
    }
}


class TreeLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		ImageDescriptor desc = IconManager
				.getImageDescriptor(IconManager.BLOB_IMAGE);
		
		return desc.createImage();
	}

	@Override
	public String getText(Object element) {
		return (String) element.toString();
	}
}
