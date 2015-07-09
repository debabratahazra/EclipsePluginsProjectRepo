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

public class verCustomer_Create_newResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_newResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_newResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Create_new", createActions(), "/{companyid}/verCustomer_Creates()/new", createLinkRelations(), new UriSpecification("verCustomer_Create_new", "/{companyid}/verCustomer_Creates()/new"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Create_new = this;
        // create transitions
        ResourceState sverCustomer_Create_metadata = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_metadata");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Create_new.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("POST")
                    .target(sverCustomer_Create_metadata)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("metadata")
                    .build());
        ResourceState sverCustomer_Create_validate = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_validate");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
            sverCustomer_Create_new.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverCustomer_Create_validate)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("validate deal")
                    .build());




        ResourceState sverCustomer_Create_input = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_input");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
            sverCustomer_Create_new.addTransition(new Transition.Builder()
                    .method("PUT")
                    .target(sverCustomer_Create_input)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("input deal")
                    .build());




        ResourceState sverCustomer_Create_hold = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_hold");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CustomerCode}");
            sverCustomer_Create_new.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverCustomer_Create_hold)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("hold deal")
                    .build());




        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verCustomer_Create");
            sverCustomer_Create_new.addTransition(new Transition.Builder()
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
        List<Action> verCustomer_Create_newActions = new ArrayList<Action>();
        return verCustomer_Create_newActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Create_newRelationsStr = "";
        verCustomer_Create_newRelationsStr += "http://schemas.microsoft.com/ado/2007/08/dataservices/related/verCustomer_Create ";
        verCustomer_Create_newRelationsStr += "http://temenostech.temenos.com/rels/new ";
        String[] verCustomer_Create_newRelations = verCustomer_Create_newRelationsStr.trim().split(" ");
        return verCustomer_Create_newRelations;
    }
    
}
