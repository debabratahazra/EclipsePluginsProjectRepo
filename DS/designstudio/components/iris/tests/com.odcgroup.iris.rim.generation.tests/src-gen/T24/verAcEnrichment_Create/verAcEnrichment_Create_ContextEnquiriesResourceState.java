package T24.verAcEnrichment_Create;
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

public class verAcEnrichment_Create_ContextEnquiriesResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_ContextEnquiriesResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_ContextEnquiriesResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_ContextEnquiries", createActions(), "/{companyid}/verAcEnrichment_Create/ContextEnquiries", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_ContextEnquiries", "/{companyid}/verAcEnrichment_Create/ContextEnquiries"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_ContextEnquiries = this;
        // create transitions
        ResourceState senqlistAcEnrichments = factory.getResourceState("T24.enqlistAcEnrichment.enqlistAcEnrichments");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverAcEnrichment_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAcEnrichments)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List live deals")
                    .build());




        ResourceState senqlistAcEnrichmentsUnauth = factory.getResourceState("T24.enqlistAcEnrichment.enqlistAcEnrichmentsUnauth");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("param", "list");
            sverAcEnrichment_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAcEnrichmentsUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("List unauthorised deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverAcEnrichment_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAcEnrichments)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search live deals")
                    .build());




        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverAcEnrichment_Create_ContextEnquiries.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(senqlistAcEnrichmentsUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Search unauthorised deals")
                    .build());




        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verAcEnrichment_Create");
            sverAcEnrichment_Create_ContextEnquiries.addTransition(new Transition.Builder()
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
        List<Action> verAcEnrichment_Create_ContextEnquiriesActions = new ArrayList<Action>();
        return verAcEnrichment_Create_ContextEnquiriesActions;
    }

    private static String[] createLinkRelations() {
        String[] verAcEnrichment_Create_ContextEnquiriesRelations = null;
        return verAcEnrichment_Create_ContextEnquiriesRelations;
    }
    
}
