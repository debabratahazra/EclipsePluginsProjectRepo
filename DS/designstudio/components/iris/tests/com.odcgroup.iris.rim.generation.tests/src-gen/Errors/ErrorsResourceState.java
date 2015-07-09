package Errors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.temenos.interaction.core.hypermedia.UriSpecification;
import com.temenos.interaction.core.hypermedia.Action;
import com.temenos.interaction.core.hypermedia.CollectionResourceState;
import com.temenos.interaction.core.hypermedia.DynamicResourceState;        
import com.temenos.interaction.core.hypermedia.LazyResourceLoader;        
import com.temenos.interaction.core.hypermedia.ResourceFactory;
import com.temenos.interaction.core.hypermedia.ResourceState;
import com.temenos.interaction.core.hypermedia.ResourceStateMachine;
import com.temenos.interaction.core.hypermedia.Transition;
import com.temenos.interaction.core.hypermedia.validation.HypermediaValidator;
import com.temenos.interaction.core.hypermedia.expression.Expression;
import com.temenos.interaction.core.hypermedia.expression.ResourceGETExpression;
import com.temenos.interaction.core.hypermedia.expression.SimpleLogicalExpressionEvaluator;
import com.temenos.interaction.core.hypermedia.ResourceLocator;        
import com.temenos.interaction.core.hypermedia.ResourceLocatorProvider;

public class ErrorsResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public ErrorsResourceState() {
        this(new ResourceFactory());
    }

    public ErrorsResourceState(ResourceFactory factory) {
        super("Errors", "Errors", createActions(), "/Errors", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sErrors = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> ErrorsActions = new ArrayList<Action>();
        ErrorsActions.add(new Action("NoopGET", Action.TYPE.VIEW, new Properties()));
        return ErrorsActions;
    }

    private static String[] createLinkRelations() {
        String[] ErrorsRelations = null;
        return ErrorsRelations;
    }
    
}
