package T24.ContextEnquiry;
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

public class ApplicationJoinsToCurrencyResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public ApplicationJoinsToCurrencyResourceState() {
        this(new ResourceFactory());
    }

    public ApplicationJoinsToCurrencyResourceState(ResourceFactory factory) {
        super("Entity", "ApplicationJoinsToCurrency", createActions(), "/{companyid}/ContextEnquiryCurrency/{entity}", createLinkRelations(), new UriSpecification("ApplicationJoinsToCurrency", "/{companyid}/ContextEnquiryCurrency/{entity}"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sApplicationJoinsToCurrency = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> ApplicationJoinsToCurrencyActions = new ArrayList<Action>();
        actionViewProperties = new Properties();
        actionViewProperties.put("JoinedTo", "CURRENCY");
        ApplicationJoinsToCurrencyActions.add(new Action("T24EntityMetadata", Action.TYPE.VIEW, actionViewProperties));
        return ApplicationJoinsToCurrencyActions;
    }

    private static String[] createLinkRelations() {
        String[] ApplicationJoinsToCurrencyRelations = null;
        return ApplicationJoinsToCurrencyRelations;
    }
    
}
