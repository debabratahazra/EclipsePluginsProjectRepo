package com.odcgroup.process.diagram.custom.util;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.util.AbstractEnumerator;
import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.process.diagram.custom.preferences.ProcessPreferenceConstants;
import com.odcgroup.process.diagram.part.Messages;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;

public class TaskSize  extends AbstractEnumerator {
	
	public static final int TASK_SIZE_SMALL = 0;
	public static final int TASK_SIZE_MEDIUM = 1;
	public static final int TASK_SIZE_BIG = 2;
	
	public static final TaskSize TASK_SIZE_SMALL_LITERAL = new TaskSize(TASK_SIZE_SMALL, Messages.StateSizeSmall, Messages.StateSizeSmall);
	public static final TaskSize TASK_SIZE_MEDIUM_LITERAL = new TaskSize(TASK_SIZE_MEDIUM, Messages.StateSizeMedium, Messages.StateSizeMedium);
	public static final TaskSize TASK_SIZE_BIG_LITERAL = new TaskSize(TASK_SIZE_BIG, Messages.StateSizeBig,  Messages.StateSizeBig);
	
	/**
	 * 
	 */
	private static final TaskSize[] VALUES_ARRAY =
        new TaskSize[] {
		TASK_SIZE_SMALL_LITERAL,
		TASK_SIZE_MEDIUM_LITERAL, 
		TASK_SIZE_BIG_LITERAL
        };
	
	/**
	 * 
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	/**
	 * @param literal
	 * @return
	 */
	public static TaskSize get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	TaskSize result = VALUES_ARRAY[i];
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
	public static TaskSize getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	TaskSize result = VALUES_ARRAY[i];
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
	public static TaskSize get(int value) {
        switch (value) {
        	case TASK_SIZE_SMALL: return TASK_SIZE_SMALL_LITERAL;
        	case TASK_SIZE_MEDIUM: return TASK_SIZE_MEDIUM_LITERAL;
        	case TASK_SIZE_BIG: return TASK_SIZE_BIG_LITERAL;
        }
        return null;	
	}	
	
	/**
	 * @param value
	 * @param name
	 * @param literal
	 */
	protected TaskSize(int value, String name, String literal) {
		super(value, name, literal);
	}
	
	/**
	 * @return
	 */
	public static Dimension getPreferredStateSize() {
		String preference = getPreferenceStore().getString(ProcessPreferenceConstants.PREF_TASK_SIZE);
		TaskSize state = get(preference);
		if (state.equals(TASK_SIZE_SMALL_LITERAL)){
			return new Dimension(100,60);
		} else if (state.equals(TASK_SIZE_MEDIUM_LITERAL)){
			return new Dimension(150,90);
		} else if (state.equals(TASK_SIZE_BIG_LITERAL)){
			return new Dimension(200,120);
		}
		// default
		return new Dimension(100,60);
	}
	
	/**
	 * @return
	 */
	public static int getPreferredFontSize(){
		String preference = getPreferenceStore().getString(ProcessPreferenceConstants.PREF_TASK_SIZE);		
		TaskSize state = get(preference);
		if (state.equals(TASK_SIZE_SMALL_LITERAL)){
			return 9;
		} else if (state.equals(TASK_SIZE_MEDIUM_LITERAL)){
			return 10;
		} else if (state.equals(TASK_SIZE_BIG_LITERAL)){
			return 12;
		}
		return 9;
	}
	
	/**
	 * @generated
	 */
	private static IPreferenceStore getPreferenceStore() {
		return ProcessDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}
