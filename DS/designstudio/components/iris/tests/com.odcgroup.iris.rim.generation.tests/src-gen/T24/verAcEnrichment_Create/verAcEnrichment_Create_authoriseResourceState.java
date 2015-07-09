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

public class verAcEnrichment_Create_authoriseResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_authoriseResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_authoriseResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_authorise", createActions(), "/{companyid}/verAcEnrichment_Creates('{id}')/authorise", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_authorise", "/{companyid}/verAcEnrichment_Creates('{id}')/authorise"), factory.getResourceState("Errors.Errors"));
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_authorise = this;
        // create transitions
        ResourceState sverAcEnrichment_Create_IAuth = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_IAuth");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_IAuth"), ResourceGETExpression.Function.OK));
            sverAcEnrichment_Create_authorise.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverAcEnrichment_Create_IAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        ResourceState sverAcEnrichment_Create = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create"), ResourceGETExpression.Function.OK));
            sverAcEnrichment_Create_authorise.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverAcEnrichment_Create)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        ResourceState sContextEnquiryList = factory.getResourceState("T24.ContextEnquiry.ContextEnquiryList");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("entity", "verAcEnrichment_Create");
            sverAcEnrichment_Create_authorise.addTransition(new Transition.Builder()
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
        List<Action> verAcEnrichment_Create_authoriseActions = new ArrayList<Action>();
        return verAcEnrichment_Create_authoriseActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_authoriseRelationsStr = "";
        verAcEnrichment_Create_authoriseRelationsStr += "http://temenostech.temenos.com/rels/authorise ";
        String[] verAcEnrichment_Create_authoriseRelations = verAcEnrichment_Create_authoriseRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_authoriseRelations;
    }
    
}
