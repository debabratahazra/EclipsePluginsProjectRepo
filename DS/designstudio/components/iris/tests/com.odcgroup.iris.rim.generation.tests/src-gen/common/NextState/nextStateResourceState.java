package common.NextState;
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

public class nextStateResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public nextStateResourceState() {
        this(new ResourceFactory());
    }

    public nextStateResourceState(ResourceFactory factory) {
        super("NextState", "nextState", createActions(), "/verCustomer_Inputs/next", createLinkRelations(), new UriSpecification("nextState", "/verCustomer_Inputs/next"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState snextState = this;
        // create transitions


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            snextState.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(new DynamicResourceState("NextState", "dynamic", "locatorNextState", new String[] {"{NextState}"}))
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> nextStateActions = new ArrayList<Action>();
        return nextStateActions;
    }

    private static String[] createLinkRelations() {
        String nextStateRelationsStr = "";
        nextStateRelationsStr += "http://temenostech.temenos.com/rels/next ";
        String[] nextStateRelations = nextStateRelationsStr.trim().split(" ");
        return nextStateRelations;
    }
    
}
