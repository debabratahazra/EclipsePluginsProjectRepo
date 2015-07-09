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

public class verCustomer_Create_ContextEnquiriesResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_ContextEnquiriesResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_ContextEnquiriesResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Create_ContextEnquiries", createActions(), "/{companyid}/verCustomer_Create/ContextEnquiries", createLinkRelations(), new UriSpecification("verCustomer_Create_ContextEnquiries", "/{companyid}/verCustomer_Create/ContextEnquiries"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Create_ContextEnquiries = this;
        // create transitions
        ResourceState senqlistCustomers = factory.getResourceState("T24.enqlistCustomer.enqlistCustomers");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomers)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List live deals")
                    .build());




        ResourceState senqlistCustomersUnauth = factory.getResourceState("T24.enqlistCustomer.enqlistCustomersUnauth");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomersUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List unauthorised deals")
                    .build());




        ResourceState senqlistCustomersHist = factory.getResourceState("T24.enqlistCustomer.enqlistCustomersHist");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomersHist)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List history deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomers)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search live deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomersUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search unauthorised deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistCustomersHist)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search history deals")
                    .build());




        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verCustomer_Create");
            sverCustomer_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("GET")
                    .target(sContextEnquiryList)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Other context Enquiries")
                    .build());
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Create_ContextEnquiriesActions = new ArrayList<Action>();
        return verCustomer_Create_ContextEnquiriesActions;
    }

    private static String[] createLinkRelations() {
        String[] verCustomer_Create_ContextEnquiriesRelations = null;
        return verCustomer_Create_ContextEnquiriesRelations;
    }
    
}
