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

public class verCustomer_Create_authoriseResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_authoriseResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_authoriseResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Create_authorise", createActions(), "/{companyid}/verCustomer_Creates('{id}')/authorise", createLinkRelations(), new UriSpecification("verCustomer_Create_authorise", "/{companyid}/verCustomer_Creates('{id}')/authorise"), factory.getResourceState("Errors.Errors"));
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Create_authorise = this;
        // create transitions
        ResourceState sverCustomer_Create_IAuth = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_IAuth"), ResourceGETExpression.Function.OK));
            sverCustomer_Create_authorise.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverCustomer_Create_IAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        ResourceState sverCustomer_Create = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verCustomer_Create.verCustomer_Create"), ResourceGETExpression.Function.OK));
            sverCustomer_Create_authorise.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverCustomer_Create)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verCustomer_Create");
            sverCustomer_Create_authorise.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(sContextEnquiryList)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Context Enquiries")
                    .build());




        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Create_authoriseActions = new ArrayList<Action>();
        return verCustomer_Create_authoriseActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Create_authoriseRelationsStr = "";
        verCustomer_Create_authoriseRelationsStr += "http://temenostech.temenos.com/rels/authorise ";
        String[] verCustomer_Create_authoriseRelations = verCustomer_Create_authoriseRelationsStr.trim().split(" ");
        return verCustomer_Create_authoriseRelations;
    }
    
}
