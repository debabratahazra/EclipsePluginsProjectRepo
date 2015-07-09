package com.odcgroup.pageflow.editor.diagram.custom.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.util.AbstractEnumerator;
import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class StateSize  extends AbstractEnumerator {
	
	public static final int STATE_SIZE_SMALL = 0;
	public static final int STATE_SIZE_MEDIUM = 1;
	public static final int STATE_SIZE_BIG = 2;
	
	public static final StateSize STATE_SIZE_SMALL_LITERAL = new StateSize(STATE_SIZE_SMALL, Messages.StateSizeSmall, Messages.StateSizeSmall);
	public static final StateSize STATE_SIZE_MEDIUM_LITERAL = new StateSize(STATE_SIZE_MEDIUM, Messages.StateSizeMedium, Messages.StateSizeMedium);
	public static final StateSize STATE_SIZE_BIG_LITERAL = new StateSize(STATE_SIZE_BIG, Messages.StateSizeBig,  Messages.StateSizeBig);
	
	/**
	 * 
	 */
	private static final StateSize[] VALUES_ARRAY =
        new StateSize[] {
		STATE_SIZE_SMALL_LITERAL,
		STATE_SIZE_MEDIUM_LITERAL, 
		STATE_SIZE_BIG_LITERAL
        };
	
	/**
	 * 
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	/**
	 * @param literal
	 * @return
	 */
	public static StateSize get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	StateSize result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }
	
	
	
	/**
	 * @param name
	 * @return
	 */
	public static StateSize getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	StateSize result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }
	
	/**
	 * @param value
	 * @return
	 */
	public static StateSize get(int value) {
        switch (value) {
        	case STATE_SIZE_SMALL: return STATE_SIZE_SMALL_LITERAL;
        	case STATE_SIZE_MEDIUM: return STATE_SIZE_MEDIUM_LITERAL;
        	case STATE_SIZE_BIG: return STATE_SIZE_BIG_LITERAL;
        }
        return null;	
	}	
	
	/**
	 * @param value
	 * @param name
	 * @param literal
	 */
	protected StateSize(int value, String name, String literal) {
		super(value, name, literal);
	}
	
	/**
	 * @return
	 */
	public static Dimension getPreferredStateSize() {
		String preference = getPreferenceStore().getString(PageflowPreferenceConstants.PREF_STATE_SIZE);
		StateSize state = get(preference);
		if (state.equals(STATE_SIZE_SMALL_LITERAL)){
			return new Dimension(100,60);
		} else if (state.equals(STATE_SIZE_MEDIUM_LITERAL)){
			return new Dimension(150,90);
		} else if (state.equals(STATE_SIZE_BIG_LITERAL)){
			return new Dimension(200,120);
		}
		// default
		return new Dimension(100,60);
	}
	
	/**
	 * @return
	 */
	public static int getPreferredFontSize(){
		String preference = getPreferenceStore().getString(PageflowPreferenceConstants.PREF_STATE_SIZE);		
		StateSize state = get(preference);
		if (state.equals(STATE_SIZE_SMALL_LITERAL)){
			return 9;
		} else if (state.equals(STATE_SIZE_MEDIUM_LITERAL)){
			return 10;
		} else if (state.equals(STATE_SIZE_BIG_LITERAL)){
			return 12;
		}
		return 9;
	}
	
	/**
	 * @generated
	 */
	private static IPreferenceStore getPreferenceStore() {
		return PageflowDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}
