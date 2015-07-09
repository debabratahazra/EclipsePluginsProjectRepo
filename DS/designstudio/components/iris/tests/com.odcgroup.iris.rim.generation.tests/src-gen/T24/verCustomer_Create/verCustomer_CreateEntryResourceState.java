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

public class verCustomer_CreateEntryResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_CreateEntryResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_CreateEntryResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_CreateEntry", createActions(), "/{companyid}/verCustomer_CreateEntry", createLinkRelations(), new UriSpecification("verCustomer_CreateEntry", "/{companyid}/verCustomer_CreateEntry"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_CreateEntry = this;
        // create transitions
        ResourceState sverCustomer_Create_new = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_new");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_autoId"), ResourceGETExpression.Function.OK));
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
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
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverCustomer_Create_populate)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("populate existing deal")
                    .build());




        ResourceState sverCustomer_Create_see = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_see");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "@");
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_see)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("See transaction")
                    .build());
        ResourceState sverCustomer_Create_IAuth_see = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth_see");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "@");
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_IAuth_see)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("GetIauthEntity")
                    .build());
        ResourceState sverCustomer_Create_HAuth_see = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_HAuth_see");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "@");
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_HAuth_see)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("GetHauthEntity")
                    .build());
        ResourceState sverCustomer_Create_IAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "@");
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_IAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("GetIauthEntity")
                    .build());
        ResourceState sverCustomer_Create_HAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_HAuth");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "@");
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_HAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("GetHauthEntity")
                    .build());
        ResourceState sverCustomer_Create_ContextEnquiries = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_ContextEnquiries");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_CreateEntry.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sverCustomer_Create_ContextEnquiries)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("All context Enquiries")
                    .build());
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_CreateEntryActions = new ArrayList<Action>();
        return verCustomer_CreateEntryActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_CreateEntryRelationsStr = "";
        verCustomer_CreateEntryRelationsStr += "http://schemas.microsoft.com/ado/2007/08/dataservices/related/verCustomer_Create ";
        verCustomer_CreateEntryRelationsStr += "http://temenostech.temenos.com/rels/contract ";
        String[] verCustomer_CreateEntryRelations = verCustomer_CreateEntryRelationsStr.trim().split(" ");
        return verCustomer_CreateEntryRelations;
    }
    
}
