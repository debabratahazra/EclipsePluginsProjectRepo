package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.temenos.interaction.core.hypermedia.UriSpecification;
import com.temenos.interaction.core.hypermedia.Action;
import com.temenos.interaction.core.hypermedia.CollectionResourceState;
import com.temenos.interaction.core.hypermedia.ResourceFactory;
import com.temenos.interaction.core.hypermedia.ResourceLocatorProvider;
import com.temenos.interaction.core.hypermedia.ResourceState;
import com.temenos.interaction.core.hypermedia.ResourceStateMachine;
import com.temenos.interaction.core.hypermedia.validation.HypermediaValidator;
import com.temenos.interaction.core.hypermedia.expression.Expression;
import com.temenos.interaction.core.hypermedia.expression.ResourceGETExpression;
import com.temenos.interaction.core.resource.ResourceMetadataManager;

public class T24CommandsBehaviour {
	private ResourceLocatorProvider locatorProvider;

    public static void main(String[] args) {
        T24CommandsBehaviour behaviour = new T24CommandsBehaviour();
        ResourceStateMachine hypermediaEngine = new ResourceStateMachine(behaviour.getRIM(), behaviour.getExceptionResource());
        HypermediaValidator validator = HypermediaValidator.createValidator(hypermediaEngine, new ResourceMetadataManager(hypermediaEngine).getMetadata());
        System.out.println(validator.graph());
    }

    public ResourceState getRIM() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        Properties actionViewProperties;

        ResourceFactory factory = new ResourceFactory();
        factory.setResourceLocatorProvider(locatorProvider);
        ResourceState initial = null;
        // create states
        return initial;
    }

    public ResourceState getExceptionResource() {
        ResourceFactory factory = new ResourceFactory();
        ResourceState exceptionState = null;
        return exceptionState;
    }
    
    public void setResourceLocatorProvider(ResourceLocatorProvider locatorProvider) {
    	this.locatorProvider = locatorProvider;
    }
    
    public ResourceLocatorProvider getResourceLocatorProvider() {
    	return locatorProvider;
    }		    
}
