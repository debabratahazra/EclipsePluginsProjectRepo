package T24.enqCustomerEdge;
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

public class enqCustomerEdgesResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqCustomerEdgesResourceState() {
        this(new ResourceFactory());
    }

    public enqCustomerEdgesResourceState(ResourceFactory factory) {
        super("enqCustomerEdge", "enqCustomerEdges", createActions(), "/{companyid}/enqCustomerEdges()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState senqCustomerEdges = this;
        // create transitions
        ResourceState senqCustomerEdge = factory.getResourceState("T24.enqCustomerEdge.enqCustomerEdge");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{Id}");
            senqCustomerEdges.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(senqCustomerEdge)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("enqCustomerEdge")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqCustomerEdgesActions = new ArrayList<Action>();
        return enqCustomerEdgesActions;
    }

    private static String[] createLinkRelations() {
        String[] enqCustomerEdgesRelations = null;
        return enqCustomerEdgesRelations;
    }
    
}
