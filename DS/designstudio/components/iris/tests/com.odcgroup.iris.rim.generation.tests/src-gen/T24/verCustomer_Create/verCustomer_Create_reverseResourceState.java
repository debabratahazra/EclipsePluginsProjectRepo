package T24.verCustomer_Create;
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

public class verCustomer_Create_reverseResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_reverseResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_reverseResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Create_reverse", createActions(), "/{companyid}/verCustomer_Creates('{id}')/reverse", createLinkRelations(), new UriSpecification("verCustomer_Create_reverse", "/{companyid}/verCustomer_Creates('{id}')/reverse"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Create_reverse = this;
        // create transitions
        ResourceState sverCustomer_Create_IAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth"), ResourceGETExpression.Function.OK));
            sverCustomer_Create_reverse.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverCustomer_Create_IAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        ResourceState sverCustomer_Create_HAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_HAuth");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_HAuth"), ResourceGETExpression.Function.OK));
            sverCustomer_Create_reverse.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverCustomer_Create_HAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Create_reverseActions = new ArrayList<Action>();
        return verCustomer_Create_reverseActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Create_reverseRelationsStr = "";
        verCustomer_Create_reverseRelationsStr += "http://temenostech.temenos.com/rels/reverse ";
        String[] verCustomer_Create_reverseRelations = verCustomer_Create_reverseRelationsStr.trim().split(" ");
        return verCustomer_Create_reverseRelations;
    }
    
}
