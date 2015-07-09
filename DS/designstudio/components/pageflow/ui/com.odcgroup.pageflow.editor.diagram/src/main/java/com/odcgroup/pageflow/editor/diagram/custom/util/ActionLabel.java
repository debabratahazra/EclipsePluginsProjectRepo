package com.odcgroup.pageflow.editor.diagram.custom.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

import com.odcgroup.pageflow.editor.diagram.part.Messages;

public final class ActionLabel extends AbstractEnumerator {
	
	public static final int ACTION_URI = 0;
	public static final int ACTION_NAME = 1;
	public static final int ACTION_NONE = 2;
	
	public static final ActionLabel ACTION_URI_LITERAL = new ActionLabel(ACTION_URI, Messages.ActionLabelURI, Messages.ActionLabelURI);
	public static final ActionLabel ACTION_NAME_LITERAL = new ActionLabel(ACTION_NAME, Messages.ActionLabelActionName, Messages.ActionLabelActionName);
	public static final ActionLabel ACTION_NONE_LITERAL = new ActionLabel(ACTION_NONE, Messages.ActionLabelNoInfo,  Messages.ActionLabelNoInfo);
	
	/**
	 * 
	 */
	private static final ActionLabel[] VALUES_ARRAY =
        new ActionLabel[] {
		ACTION_URI_LITERAL,
		ACTION_NAME_LITERAL, 
		ACTION_NONE_LITERAL
        };
	
	/**
	 * 
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	/**
	 * @param literal
	 * @return
	 */
	public static ActionLabel get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	ActionLabel result = VALUES_ARRAY[i];
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
	public static ActionLabel getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
        	ActionLabel result = VALUES_ARRAY[i];
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
	public static ActionLabel get(int value) {
        switch (value) {
        	case ACTION_URI: return ACTION_URI_LITERAL;
        	case ACTION_NAME: return ACTION_NAME_LITERAL;
        	case ACTION_NONE: return ACTION_NONE_LITERAL;
        }
        return null;	
	}	
	
	/**
	 * @param value
	 * @param name
	 * @param literal
	 */
	protected ActionLabel(int value, String name, String literal) {
		super(value, name, literal);
	}

}
