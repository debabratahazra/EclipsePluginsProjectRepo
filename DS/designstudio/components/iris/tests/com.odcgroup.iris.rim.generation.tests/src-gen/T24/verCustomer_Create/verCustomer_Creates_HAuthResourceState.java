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

public class verCustomer_Creates_HAuthResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Creates_HAuthResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Creates_HAuthResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Creates_HAuth", createActions(), "/{companyid}/verCustomer_Creates_HAuth()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sverCustomer_Creates_HAuth = this;
        // create transitions
        ResourceState sverCustomer_Create_HAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_HAuth");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
            sverCustomer_Creates_HAuth.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sverCustomer_Create_HAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("verCustomer_Create_HAuth")
                    .linkId("")
                    .build());



        ResourceState sverCustomer_Create_new = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_new");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_autoId"), ResourceGETExpression.Function.OK));
            sverCustomer_Creates_HAuth.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverCustomer_Create_new)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("create new deal")
                    .build());




        ResourceState sverCustomer_Create_populate = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_populate");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Creates_HAuth.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverCustomer_Create_populate)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("populate existing deal")
                    .build());




        ResourceState sverCustomer_Create_restore = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_restore");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
            sverCustomer_Creates_HAuth.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("POST")
                    .target(sverCustomer_Create_restore)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("restore deal")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Creates_HAuthActions = new ArrayList<Action>();
        return verCustomer_Creates_HAuthActions;
    }

    private static String[] createLinkRelations() {
        String[] verCustomer_Creates_HAuthRelations = null;
        return verCustomer_Creates_HAuthRelations;
    }
    
}
