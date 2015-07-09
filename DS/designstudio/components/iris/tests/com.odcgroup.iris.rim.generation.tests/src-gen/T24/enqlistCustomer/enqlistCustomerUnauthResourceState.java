package T24.enqlistCustomer;
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

public class enqlistCustomerUnauthResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqlistCustomerUnauthResourceState() {
        this(new ResourceFactory());
    }

    public enqlistCustomerUnauthResourceState(ResourceFactory factory) {
        super("enqlistCustomer", "enqlistCustomerUnauth", createActions(), "/{companyid}/enqlistCustomersUnauth('{id}')", createLinkRelations(), new UriSpecification("enqlistCustomerUnauth", "/{companyid}/enqlistCustomersUnauth('{id}')"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState senqlistCustomerUnauth = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqlistCustomerUnauthActions = new ArrayList<Action>();
        enqlistCustomerUnauthActions.add(new Action("GetIauthEntity", Action.TYPE.VIEW, new Properties()));
        return enqlistCustomerUnauthActions;
    }

    private static String[] createLinkRelations() {
        String[] enqlistCustomerUnauthRelations = null;
        return enqlistCustomerUnauthRelations;
    }
    
}
