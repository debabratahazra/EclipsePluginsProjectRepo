package com.odcgroup.t24.version.editor.ui.providers;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;

import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.impl.RoutineImpl;
/**
 * custom Observable label provider for the Default Tab table viewer.
 * @author snn
 *
 */
public class DefaultTableViewerLabelProvider extends ObservableMapLabelProvider {

   
    public DefaultTableViewerLabelProvider(IObservableMap[] attributeMap) {
	super(attributeMap);
	
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
	//get the labels of the new value and routine column from valueorRoutine.
	if(element instanceof Default && columnIndex >2 ){
	    ValueOrAtRoutine valueOrRoutine=((Default)element).getDefaultNewValueOrAtRoutine();
	    if(valueOrRoutine !=null){
		if(columnIndex ==3){
		    if(valueOrRoutine.getValue()!=null){
		      return valueOrRoutine.getValue();
		    }
		    return StringUtils.EMPTY; 
		}else if(valueOrRoutine.getAtRoutine()!=null){
		         return ((RoutineImpl)valueOrRoutine.getAtRoutine()).getName();
		      }
		   return StringUtils.EMPTY; 
		}
	      }
	return super.getColumnText(element, columnIndex);
    }

}
