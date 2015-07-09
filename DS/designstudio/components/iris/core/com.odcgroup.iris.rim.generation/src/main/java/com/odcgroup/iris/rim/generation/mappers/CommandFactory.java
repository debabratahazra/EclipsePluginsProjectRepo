package com.odcgroup.iris.rim.generation.mappers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.temenos.interaction.rimdsl.rim.Command;
import com.temenos.interaction.rimdsl.rim.CommandProperty;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.MethodRef;
import com.temenos.interaction.rimdsl.rim.ResourceCommand;
import com.temenos.interaction.rimdsl.rim.RimFactory;

/**
 * Constructs commands and add to the list of commands.
 *
 * @author aphethean
 *
 */
public class CommandFactory {
	
	private Map<String, Command> commandMap = new HashMap<String, Command>();
	private EList<Command> modelReferences;
	
	public CommandFactory(EList<Command> modelReferences) {
		this.modelReferences = modelReferences;
	}
	
	public ResourceCommand createResourceCommand(String commandName, Map<String,String> properties) {
		// a resource command references a command
		ResourceCommand resourceCommand = RimFactory.eINSTANCE.createResourceCommand();
		if (commandMap.get(commandName) == null) {
			Command command = RimFactory.eINSTANCE.createCommand();
			command.setName(commandName);
			// command.getProperties();
			commandMap.put(commandName, command);
			modelReferences.add(command);
		}
		resourceCommand.setCommand(commandMap.get(commandName));
		// add any resource specific command properties
		for (String key : properties.keySet()) {
			CommandProperty commandProperty = RimFactory.eINSTANCE.createCommandProperty();
			commandProperty.setName(key);
			commandProperty.setValue(properties.get(key));
			resourceCommand.getProperties().add(commandProperty);
		}
		return resourceCommand;
	}
	
	// creates the HTTP method to associate to commands
	public MethodRef createMethod(String httpMethod, ResourceCommand command) {
		MethodRef method = RimFactory.eINSTANCE.createMethodRef();
		Event event = RimFactory.eINSTANCE.createEvent();
		event.setName(httpMethod);
		event.setHttpMethod(httpMethod);
		method.setEvent(event);
		method.setCommand(command);
		return method;
	}

}
