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

public class enqlistCustomersUnauthResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqlistCustomersUnauthResourceState() {
        this(new ResourceFactory());
    }

    public enqlistCustomersUnauthResourceState(ResourceFactory factory) {
        super("enqlistCustomer", "enqlistCustomersUnauth", createActions(), "/{companyid}/enqlistCustomersUnauth()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState senqlistCustomersUnauth = this;
        // create transitions
        ResourceState senqlistCustomerUnauth = factory.getResourceState("T24.enqlistCustomer.enqlistCustomerUnauth");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{PrimaryKey}");
            senqlistCustomersUnauth.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(senqlistCustomerUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("enqlistCustomerUnauth")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqlistCustomersUnauthActions = new ArrayList<Action>();
        actionViewProperties = new Properties();
        actionViewProperties.put("filter", "{filter}");
        enqlistCustomersUnauthActions.add(new Action("GetIauthEntities", Action.TYPE.VIEW, actionViewProperties));
        return enqlistCustomersUnauthActions;
    }

    private static String[] createLinkRelations() {
        String[] enqlistCustomersUnauthRelations = null;
        return enqlistCustomersUnauthRelations;
    }
    
}
