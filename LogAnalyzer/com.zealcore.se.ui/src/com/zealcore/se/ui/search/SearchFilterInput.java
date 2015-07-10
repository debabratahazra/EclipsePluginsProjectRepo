/**
 * 
 */
package com.zealcore.se.ui.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ISearchAdapter;
import com.zealcore.se.core.NotImplementedException;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.SearchCriteria;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ReflectiveType;
import com.zealcore.se.ui.SeUiPlugin;
import com.zealcore.se.ui.core.TypePackageLabelProvider;
import com.zealcore.se.ui.core.TypePackageTreeContentProvider;
import com.zealcore.se.ui.dialogs.OpenPlotDialog.PlotComposite;
import com.zealcore.se.ui.internal.FilteredElementTreeSelectionDialog;
import com.zealcore.se.ui.internal.assertions.IEditor;
import com.zealcore.se.ui.internal.assertions.IModifyListener;
import com.zealcore.se.ui.internal.assertions.ModifyEvent;
import com.zealcore.se.ui.search.DebuggerSearch.MyComposite;

public class SearchFilterInput implements IEditor {

	private static final char DOT_CHAR = '.';

	private static final char STAR_CHAR = '*';

	private static final String DOT = ".";

	private static final String STAR = "*";

	private static final String QUESTION = "?";

	private static final String PIPE = "|";

	private static final String L_PARA = "(";

	private static final String R_PARA = ")";

	private static final String L_BRACKET = "[";

	private static final String R_BRACKET = "]";

	private static final String L_WING = "{";

	private static final String R_WING = "}";

	private static final String HAT = "^";

	private static final String DOLLAR = "$";

	private static final String PLUS = "+";

	private static final String SMALLER = "<";

	private static final String GREATER = ">";

	private static final String BACKSLASH = "\\";

	private final String[] regExpCharacters = { PIPE, L_PARA, R_PARA,
			L_BRACKET, R_BRACKET, L_WING, R_WING, HAT, DOLLAR, PLUS, SMALLER,
			GREATER, };

	private static final String STRING = "String";

	private static final String THE_NAME_OF_THE_PROCESS = "The name of the process/task";

	private static final String STR_INPUT_ERROR = "There is something wrong with your input";

	private final Set<IModifyListener> modifyListeners = new HashSet<IModifyListener>();

	private static final String SELECT_SEARCHABLE_TYPE = "Select Type";

	private static final int NUM_GRID_COLUMNS = 4;

	private static final int INPUT_MIN_WIDTH = 110;

	private boolean useRegularExpressionSearch;

	public static final IFilter<IType> SEARCHABLE_TYPES = new IFilter<IType>() {
		public boolean filter(final IType type) {
			return type.isSearchable();
		}
	};

	public static final IFilter<IType> PLOTTABLE_TYPES = new IFilter<IType>() {
		public boolean filter(final IType type) {
			return type.isPlottable();
		}
	};

	protected static final String NORMAL_SEARCH_HELP_STRING = "(* = any string, ? = any character, \\ = escape for literals: * ? \\)";

	protected static final String REG_EXP_HELP_STRING = "See \"Reference Information\" in Help for more information.";

	private static final int DIALOG_WIDTH = 500;

	private static final int MAX_DIALOG_HEIGHT = 460;

	private static final int SEARCH_OFFSET_HEIGHT = 230;

	private static final int ASSERTION_OFFSET_HEIGHT = 70;
	
	private static final int PLOT_OFFSET_HEIGHT = 70;

	// protected static final String REG_EXP_HELP_STRING =
	// "(. = single character, * = zero or more characters, + = one or more characters, [ ] = any character in the set, [^] = any character not in the set, | = or, \\ = escape character, { } = tag expression, \\d = any decimal digit, \\D = any non-digit character, \\s = white space character: [ \\t\\n\\x0B\\f\\r], \\S = non-white space character: [^\\s], \\w = any alphanumeric character, \\W = any non-alphanumeric character)";

	private Group searchAdapterGroup;

	private ISearchAdapter adapter;

	private Map<SearchCriteria, SearchString> criteriaInputMap = new HashMap<SearchCriteria, SearchString>();

	private boolean currentState;

	private Composite parent;

	private final Map<ITypePackage, Collection<IType>> typesByPackage = new HashMap<ITypePackage, Collection<IType>>();

	private final int typeCount;

	/*
	 * Contains initial "search settings", e.g. if Plot Task is choosen from
	 * Gantt view
	 */
	private ISearchAdapter initialSearchAdapter;

	private Label regExpHelpString;

	public SearchFilterInput(final IFilter<IType> filter) {
		final ITypeRegistry typeRegistry = SeCorePlugin.getDefault()
				.getService(ITypeRegistry.class);

		typeCount = populateTypeMap(typeRegistry, filter);
	}

	private int populateTypeMap(final ITypeRegistry typeRegistry,
			final IFilter<IType> filter) {
		int count = 0;
		final ITypePackage[] typePackages = typeRegistry.getTypePackages();
		for (final ITypePackage typePackage : typePackages) {
			final Collection<IType> searchableTypes = new ArrayList<IType>();
			final Collection<IType> types = typePackage.getTypes();
			for (final IType type : types) {
				if (filter.filter(type)) {
					searchableTypes.add(type);
					count++;
				}
			}
			typesByPackage.put(typePackage, searchableTypes);
		}
		return count;
	}

	/**
	 * Creates label/input pairs for searching.
	 */
    void createSearchAdapterContent(final ISearchAdapter adapter, boolean selectType) {

		for (final Control c : getSearchAdapterGroup().getChildren()) {
			c.dispose();
		}

		getSearchAdapterGroup().setText(adapter.getName());

		for (final SearchCriteria criteria : adapter.getCritList()) {
			final SearchString search = new SearchString();
			final Label searchLabel = new Label(getSearchAdapterGroup(),
					SWT.NULL);
			searchLabel.setAlignment(SWT.RIGHT);
			searchLabel.setText(criteria.getName());
			searchLabel.setToolTipText(getToolTip(criteria));

			// Create Text box -1
			final Text textBox1 = new Text(getSearchAdapterGroup(), SWT.BORDER);
			textBox1.addModifyListener(new ModifyListener() {
				public void modifyText(
						final org.eclipse.swt.events.ModifyEvent e) {
					search.setText1(textBox1.getText());
					criteriaInputMap.put(criteria, search);
					notifyModifyListeners();
				}
			});

			String str = new String();

			str += criteria.getOperand1() == null ? "" : criteria.getOperand1()
					.toString();

			textBox1.setText(str);

			GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
			gridData.horizontalAlignment = SWT.LEFT;
			searchLabel.setLayoutData(gridData);
			int HorizontalSpanSpacing = 1;

			String attributeClassName = criteria.getAttributeClassType()
					.toString();
			if (attributeClassName.contains("java.lang.String")
					|| attributeClassName
							.contains("com.zealcore.se.core.model")
					|| attributeClassName.contains("java.lang.Boolean")
					|| attributeClassName.contains("boolean")) {
				HorizontalSpanSpacing = 2;
			} else {
				// Create text box-2
				createTextbox2(criteria, search);
			}
			gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
			gridData.widthHint = SearchFilterInput.INPUT_MIN_WIDTH;
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalSpan = HorizontalSpanSpacing;
			gridData.horizontalAlignment = SWT.FILL;
			textBox1.setLayoutData(gridData);

			// "Not" Check Box creation
			final Button checkBoxMatch = new Button(getSearchAdapterGroup(),
					SWT.CHECK);
			checkBoxMatch.setText("Not");
			gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = SWT.LEFT;
			checkBoxMatch.setLayoutData(gridData);
			checkBoxMatch.setSelection(criteria.getWildcard());

			search.setChecked(checkBoxMatch.getSelection());
			criteriaInputMap.put(criteria, search);

			checkBoxMatch.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					search.setChecked(checkBoxMatch.getSelection());
					criteriaInputMap.put(criteria, search);
				}

				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}

		new Label(getSearchAdapterGroup(), SWT.NULL);

		// Create Regular Expression Label.
		GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
		gridData.horizontalSpan = 3;

		final Button useRegExpCheck = new Button(getSearchAdapterGroup(),
				SWT.CHECK);
		useRegExpCheck.setAlignment(SWT.LEFT);
		useRegExpCheck.setText("Regular expression");
		useRegExpCheck.setLayoutData(gridData);
		useRegExpCheck.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			public void widgetSelected(final SelectionEvent e) {
				setUseRegularExpressionSearch(useRegExpCheck.getSelection());
				if (useRegExpCheck.getSelection()) {
					regExpHelpString.setText(REG_EXP_HELP_STRING);
				} else {
					regExpHelpString.setText(NORMAL_SEARCH_HELP_STRING);
				}
			}
		});

		final Label emptyField2 = new Label(getSearchAdapterGroup(), SWT.NULL);
		emptyField2.setText("");

		regExpHelpString = new Label(getSearchAdapterGroup(), SWT.NULL);
		regExpHelpString.setAlignment(SWT.LEFT);
		GridData gridData1 = new GridData(SWT.DEFAULT, SWT.DEFAULT);
		gridData1.horizontalSpan = 3;
		regExpHelpString.setText(NORMAL_SEARCH_HELP_STRING);
		regExpHelpString.setLayoutData(gridData1);

        if(selectType) {
            createSelectTypeButton();
        }

        getSearchAdapterGroup().layout(true);
        getSearchAdapterGroup().update();

		parent.layout(true);
		boolean assertion = !(parent.getParent().getParent() instanceof MyComposite)
				&& !(parent.getParent().getParent() instanceof PlotComposite);
		
		// Set minimum height of scrolledComposite for selected event type
        if (parent.getParent() instanceof ScrolledComposite) {
            int height = this.getAdapter().getCritList().size() * getTextHeight(emptyField2)  + SEARCH_OFFSET_HEIGHT;
            ScrolledComposite scrolledComposite = ((ScrolledComposite) parent
                    .getParent());
            if (assertion) {
                height += ASSERTION_OFFSET_HEIGHT ;         //Assertion Dialog box only
            }
            
            if(parent.getParent().getParent() instanceof PlotComposite) {
            	height -= PLOT_OFFSET_HEIGHT;         		//Plot dialog box only
            }
            
            scrolledComposite.setMinHeight(height);
            scrolledComposite.getParent().layout(true);
        }
		if (parent.getParent() != null) {
			parent.getParent().layout(true);
		}

		doNotifyLiteners();
		
		if (assertion) {
			parent.getShell().setSize(DIALOG_WIDTH, MAX_DIALOG_HEIGHT);
		}
	}
    
	public int getTextHeight(Label label) {
		int initHeight = 14;
		GC gc = new GC(label);
        FontMetrics fm = gc.getFontMetrics();
        int height = fm.getHeight() + initHeight;
        gc.dispose();
        return height;
	}
    
	private void createSelectTypeButton () {
        final Button selectTypeButton = new Button(getSearchAdapterGroup(),
                SWT.PUSH);
        selectTypeButton.setText(SearchFilterInput.SELECT_SEARCHABLE_TYPE);

        GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT);
        gridData.widthHint = SearchFilterInput.INPUT_MIN_WIDTH;
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalSpan = 5;
        gridData.horizontalAlignment = SWT.FILL;

        selectTypeButton.setLayoutData(gridData);
        selectTypeButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent event) {
                final TypePackageLabelProvider typePackageLabelProvider = new TypePackageLabelProvider();
                final TypePackageTreeContentProvider typePackageTreeContentProvider = new TypePackageTreeContentProvider(
                        typesByPackage);
                final FilteredElementTreeSelectionDialog selectDialog = new FilteredElementTreeSelectionDialog(
                        null, typePackageLabelProvider,
                        typePackageTreeContentProvider);
                selectDialog.setValidator(new ISelectionStatusValidator() {
                    public IStatus validate(final Object[] selection) {
                        final Status error = new Status(IStatus.ERROR,
                                SeUiPlugin.PLUGIN_ID, 0,
                                "Please select a type or press Cancel", null);
                        if (selection == null) {
                            return error;
                        }
                        if (selection.length == 0) {
                            return error;
                        }
                        if (!(selection[0] instanceof IType)) {
                            return error;
                        }
                        final IType type = (IType) selection[0];
                        final Status ok = new Status(IStatus.OK,
                                SeUiPlugin.PLUGIN_ID, 0, type.getName(), null);
                        return ok;
                    }
                });
                selectDialog.setAllowMultiple(false);
                selectDialog.setInput(typesByPackage);
                selectDialog.setComparator(new ViewerComparator());
                selectDialog.setTitle(SearchFilterInput.SELECT_SEARCHABLE_TYPE);
                if (selectDialog.open() == Window.OK) {
                    final IType type = (IType) selectDialog.getFirstResult();
                    SearchAdapter sa = SearchAdapter.createSearchAdapter(type);
                    setAdapter(sa);
                    createSearchAdapterContent(getAdapter(), true);
                }
            }
        });
    }

	/**
	 * Create second text box for Number field.
	 * 
	 * @param criteria
	 * @param search
	 * @param textBox1
	 */
	private void createTextbox2(final SearchCriteria criteria,
			final SearchString search) {

		final Text textBox2 = new Text(getSearchAdapterGroup(), SWT.BORDER);

		textBox2.addModifyListener(new ModifyListener() {
			public void modifyText(final org.eclipse.swt.events.ModifyEvent key) {
				search.setText2(textBox2.getText());
				criteriaInputMap.put(criteria, search);
				notifyModifyListeners();
			}
		});

		String str = new String();

		str += criteria.getOperand2() == null ? "" : criteria.getOperand2()
				.toString();

		textBox2.setText(str);

		GridData gridData1 = new GridData(SWT.DEFAULT, SWT.DEFAULT);
		gridData1.widthHint = SearchFilterInput.INPUT_MIN_WIDTH;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = SWT.FILL;
		textBox2.setLayoutData(gridData1);
	}

	/**
	 * Get tool tip for a search criteria.
	 * 
	 * @param criteria
	 *            the criteria
	 * @return the tool tip
	 */
	private String getToolTip(final SearchCriteria criteria) {
		final String newline = System.getProperty("line.separator");
		String simpleName = criteria.getAttributeClassType().getSimpleName();
		String retValue;
		if ("IArtifact".equals(simpleName)) {
			simpleName = STRING;
			retValue = simpleName + newline + THE_NAME_OF_THE_PROCESS;
		} else if ("GenericTask".equals(simpleName)) {
			simpleName = STRING;
			retValue = simpleName + newline + THE_NAME_OF_THE_PROCESS;
		} else if ("ISequenceMember".equals(simpleName)) {
			simpleName = STRING;
			retValue = simpleName + newline + THE_NAME_OF_THE_PROCESS;
		} else {
			retValue = simpleName + newline + criteria.getDescription();
		}
		return retValue;
	}

	void setSearchAdapterGroup(final Group searchAdapterGroup) {
		this.searchAdapterGroup = searchAdapterGroup;
	}

	Group getSearchAdapterGroup() {
		return searchAdapterGroup;
	}

	public void setAdapter(final ISearchAdapter adapter) {
		this.adapter = adapter;
		criteriaInputMap.clear();
	}

	public ISearchAdapter getAdapter() {
		return adapter;
	}

	public final ISearchAdapter createAdapter() {
		buildAdapter();
		return getAdapter();
	}

	/**
	 * Create Assertion Set selection GUI.
	 * 
	 * @param parent
	 */
	public final Composite createContents(final Composite parent) {
		this.parent = parent;
		

        setSearchAdapterGroup(new Group(parent, SWT.NONE));

        getSearchAdapterGroup().setLayout(
                new GridLayout(SearchFilterInput.NUM_GRID_COLUMNS, false));
        GridData layoutData = new GridData(GridData.FILL, GridData.FILL, true,
                true);
        layoutData.horizontalSpan = 2;
        getSearchAdapterGroup().setLayoutData(layoutData);

        // Set initial selection
        if (typeCount > 0) {
            setAdapter(getInitialSelection());
            createSearchAdapterContent(getAdapter(), true);
        }
        parent.layout(true);
        return parent;
    }

	public void setInitialSearchAdapter(
			final ISearchAdapter initialSearchAdapter) {
		this.initialSearchAdapter = initialSearchAdapter;
	}

	private ISearchAdapter getInitialSelection() {
		if (initialSearchAdapter != null
				&& existType(initialSearchAdapter.getSearchType())) {
			return initialSearchAdapter;
		}
		initialSearchAdapter = SearchAdapter.createSearchAdapter(ReflectiveType
				.valueOf(ILogEvent.class));
		return initialSearchAdapter;
	}

	public IType getSelection() {
		return this.adapter.getSearchType();
	}

	/**
	 * Checks if the given type exists in typepackages
	 * 
	 * @param type
	 * @return
	 */
	private boolean existType(final IType type) {
		final Collection<Collection<IType>> typeLists = typesByPackage.values();
		for (final Collection<IType> types : typeLists) {
			for (Iterator<IType> it = types.iterator(); it.hasNext();) {
				IType type2 = (IType) it.next();
				Class<? extends IType> class1 = type2.getClass();
				if (type.getClass() == class1) {
					return true;
				}
			}
		}
		return false;
	}

	public final Composite createContent(final Composite parent,
			final SearchAdapter adapter) {

        this.parent = parent;

		setAdapter(adapter);
		setSearchAdapterGroup(new Group(parent, SWT.NULL));
		getSearchAdapterGroup().setLayout(
				new GridLayout(SearchFilterInput.NUM_GRID_COLUMNS, false));
		GridData layoutData = new GridData(GridData.FILL, GridData.FILL, true,
                true);
        layoutData.horizontalSpan = 2;
        getSearchAdapterGroup().setLayoutData(layoutData);

        // Set initial selection
        createSearchAdapterContent(adapter, false);
        return parent;
    }
	
	

	/**
	 * Builds the adapter. This implementation uses a SearchHelper
	 * 
	 * @return true if this implementation deemed the build-adapter to be
	 *         successfull.
	 */
	final boolean buildAdapter() {
		final SearchHelper helper = new SearchHelper();
		for (final Entry<SearchCriteria, SearchString> entry : criteriaInputMap
				.entrySet()) {
			SearchString text = entry.getValue();
			final SearchCriteria critera = entry.getKey();
			entry.setValue(text);
			setCriteriaValue(helper, text, critera);
		}
		return true;
	}

	final boolean buildAdapterRegExp() {
		final SearchHelper helper = new SearchHelper();
		for (final Entry<SearchCriteria, SearchString> entry : criteriaInputMap
				.entrySet()) {
			SearchString text = entry.getValue();
			text = makeRegExp(entry.getKey(), text);
			entry.setValue(text);
			final SearchCriteria critera = entry.getKey();
			setCriteriaValue(helper, text, critera);
		}
		return true;
	}

	final boolean deBuildAdapterRegExp() {
		final SearchHelper helper = new SearchHelper();
		for (final Entry<SearchCriteria, SearchString> entry : criteriaInputMap
				.entrySet()) {
			SearchString text = entry.getValue();
			text = unMakeRegExp(entry.getKey(), text);
			entry.setValue(text);
			final SearchCriteria critera = entry.getKey();
			setCriteriaValue(helper, text, critera);
		}
		return true;
	}

	/**
	 * Tries to parse the text parameter and set the criteria value.
	 * 
	 * @param helper
	 *            a helper class to validate and set criteria values
	 * @param text
	 *            the input text from this ui component
	 * @param critera
	 *            the criteria to set values
	 * @return false if failed for some reason, true if successfull
	 */
	boolean setCriteriaValue(final SearchHelper helper,
			final SearchString searchString, final SearchCriteria critera) {

		// The refactorable version uses verifiers, so any text inputed
		// should already be ok - exceptions may be thrown by the helper

		helper.set(critera, searchString);
		critera.setWildcard(searchString.isChecked());
		return true;
	}

	public void addModifyListener(final IModifyListener listener) {
		modifyListeners.add(listener);

		boolean isOk = isCriteriaMapOk();
		String errorMessage = null;
		if (!isOk) {
			errorMessage = SearchFilterInput.STR_INPUT_ERROR;
		}
		final ModifyEvent event = new ModifyEvent(errorMessage, this);
		listener.componentModified(event);
	}

	public void removeModifyListener(final IModifyListener listener) {
		modifyListeners.remove(listener);
	}

	/**
	 * Will notify modify listeners if the state has changed
	 */
	private void notifyModifyListeners() {
		final boolean isOk = isCriteriaMapOk();

		// Check if the current state has changed
		if (currentState != isOk) {
			currentState = isOk;
			// May be ok, errorMessage == null means ModifyEvent is ok
			doNotifyLiteners();
		}
	}

	/**
	 * Will notify liteners of the current state - even if nothing has changed.
	 */
	private void doNotifyLiteners() {
		// If the state changed to NOT Ok - Send a error message
		String errorMessage = null;
		if (!isCriteriaMapOk()) {
			errorMessage = SearchFilterInput.STR_INPUT_ERROR;
		}
		final ModifyEvent event = new ModifyEvent(errorMessage, this);
		for (final IModifyListener listener : modifyListeners) {
			listener.componentModified(event);
		}
	}

	public boolean isOk() {
		return isCriteriaMapOk();
	}

	private boolean isCriteriaMapOk() {
		boolean isOk = true;
		for (final Entry<SearchCriteria, SearchString> entry : criteriaInputMap
				.entrySet()) {
			if (!SearchHelper.isOk(entry.getKey(), makeRegExp(entry.getKey(),
					entry.getValue()))) {
				isOk = false;
			}
		}
		return isOk;
	}

	public void saveChanges() {
		throw new NotImplementedException();
	}

	private void setUseRegularExpressionSearch(
			final boolean useRegularExpressionSearch) {
		this.useRegularExpressionSearch = useRegularExpressionSearch;
	}

	public boolean isUseRegularExpressionSearch() {
		return useRegularExpressionSearch;
	}

	public void copyVals(final SearchFilterInput searchInput) {
		this.criteriaInputMap = new HashMap<SearchCriteria, SearchString>();
		for (final Entry<SearchCriteria, SearchString> entry : searchInput.criteriaInputMap
				.entrySet()) {
			SearchCriteria criteria = entry.getKey();
			SearchString value = entry.getValue();

			this.criteriaInputMap.put(criteria, value);

		}

		List<SearchCriteria> critList = this.adapter.getCritList();
		critList = new ArrayList<SearchCriteria>();
		for (final SearchCriteria sc : searchInput.getAdapter().getCritList()) {

			critList.add(sc);
		}

	}

	private SearchString makeRegExp(final SearchCriteria criteria,
			final SearchString search) {
		final SearchString searchStr = new SearchString();
		String text = search.getText1();
		if (text != null && !text.equals("")) {
			if (criteria != null
					&& SearchHelper.getCriteriaType(criteria) == SearchHelper.Type.ARTIFACT) {
				if (!useRegularExpressionSearch) {
					text = text.replace(DOT, BACKSLASH + DOT);
					text = text.replace(STAR, DOT + STAR);
					text = text.replace(QUESTION, DOT + QUESTION);
					// \ | ( ) [ { ^ $ * + ? . < >
					for (String ch : regExpCharacters) {
						text = text.replace(ch, BACKSLASH + ch);
					}
					// Make the search so it just needs to contain the text.
					if (text.length() > 0) {
						text = DOT + STAR + text + DOT + STAR;
					}
				}
			}
		}
		searchStr.setText1(text);
		searchStr.setText2(search.getText2());
		searchStr.setChecked(search.isChecked());
		return searchStr;
	}

	private SearchString unMakeRegExp(final SearchCriteria criteria,
			final SearchString search) {
		SearchString searchStr = new SearchString();
		String text1 = search.getText1();
		if (criteria != null
				&& SearchHelper.getCriteriaType(criteria) == SearchHelper.Type.ARTIFACT) {
			if (!useRegularExpressionSearch && text1.length() > 3) {
				if (text1.charAt(0) == DOT_CHAR && text1.charAt(1) == STAR_CHAR) {
					text1 = text1.substring(2);
				}
				if (text1.charAt(text1.length() - 2) == DOT_CHAR
						&& text1.charAt(text1.length() - 1) == STAR_CHAR) {
					int endIndex = text1.length() - 2;
					if (endIndex > 0) {
						text1 = text1.substring(0, endIndex);
					}
				}

				text1 = text1.replace(BACKSLASH, "");
				text1 = text1.replace(DOT + STAR, STAR);
				text1 = text1.replace(DOT + QUESTION, QUESTION);
				text1 = text1.replace(PIPE, BACKSLASH + PIPE);
				text1 = text1.replace(BACKSLASH + DOT, DOT);
				// \ | ( ) [ { ^ $ * + ? . < >
				for (String ch : regExpCharacters) {
					text1 = text1.replace(BACKSLASH + ch, ch);
				}
			}
		}
		String text2 = search.getText2();
		if (criteria != null
				&& SearchHelper.getCriteriaType(criteria) == SearchHelper.Type.ARTIFACT) {
			if (!useRegularExpressionSearch && text2.length() > 3) {
				if (text2.charAt(0) == DOT_CHAR && text2.charAt(1) == STAR_CHAR) {
					text2 = text2.substring(2);
				}
				if (text2.charAt(text2.length() - 2) == DOT_CHAR
						&& text2.charAt(text2.length() - 1) == STAR_CHAR) {
					int endIndex = text2.length() - 2;
					if (endIndex > 0) {
						text2 = text2.substring(0, endIndex);
					}
				}

				text2 = text2.replace(BACKSLASH, "");
				text2 = text2.replace(DOT + STAR, STAR);
				text2 = text2.replace(DOT + QUESTION, QUESTION);
				text2 = text2.replace(PIPE, BACKSLASH + PIPE);
				text2 = text2.replace(BACKSLASH + DOT, DOT);
				// \ | ( ) [ { ^ $ * + ? . < >
				for (String ch : regExpCharacters) {
					text2 = text2.replace(BACKSLASH + ch, ch);
				}
			}
		}
		searchStr.setText1(text1);
		searchStr.setText2(text2);
		searchStr.setChecked(search.isChecked());
		return searchStr;
	}
}
