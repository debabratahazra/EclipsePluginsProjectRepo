package T24.verAlternateAccount_Create;
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

public class verAlternateAccount_Create_ContextEnquiriesResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAlternateAccount_Create_ContextEnquiriesResourceState() {
        this(new ResourceFactory());
    }

    public verAlternateAccount_Create_ContextEnquiriesResourceState(ResourceFactory factory) {
        super("verAlternateAccount_Create", "verAlternateAccount_Create_ContextEnquiries", createActions(), "/{companyid}/verAlternateAccount_Create/ContextEnquiries", createLinkRelations(), new UriSpecification("verAlternateAccount_Create_ContextEnquiries", "/{companyid}/verAlternateAccount_Create/ContextEnquiries"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAlternateAccount_Create_ContextEnquiries = this;
        // create transitions
        ResourceState senqlistAlternateAccounts = factory.getResourceState("T24.enqlistAlternateAccount.enqlistAlternateAccounts");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverAlternateAccount_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAlternateAccounts)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List live deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverAlternateAccount_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAlternateAccounts)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search live deals")
                    .build());




        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verAlternateAccount_Create");
            sverAlternateAccount_Create_ContextEnquiries.addTransition(new Transition.Builder()
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
        List<Action> verAlternateAccount_Create_ContextEnquiriesActions = new ArrayList<Action>();
        return verAlternateAccount_Create_ContextEnquiriesActions;
    }

    private static String[] createLinkRelations() {
        String[] verAlternateAccount_Create_ContextEnquiriesRelations = null;
        return verAlternateAccount_Create_ContextEnquiriesRelations;
    }
    
}
