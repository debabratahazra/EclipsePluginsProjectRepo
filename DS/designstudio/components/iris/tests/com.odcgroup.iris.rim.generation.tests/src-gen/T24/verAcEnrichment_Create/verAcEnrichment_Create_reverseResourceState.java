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

public class verAcEnrichment_Create_reverseResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_reverseResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_reverseResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_reverse", createActions(), "/{companyid}/verAcEnrichment_Creates('{id}')/reverse", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_reverse", "/{companyid}/verAcEnrichment_Creates('{id}')/reverse"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_reverse = this;
        // create transitions
        ResourceState sverAcEnrichment_Create_IAuth = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_IAuth");


        // create AUTO transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_IAuth"), ResourceGETExpression.Function.OK));
            sverAcEnrichment_Create_reverse.addTransition(new Transition.Builder()
                    .flags(Transition.AUTO)
                    .target(sverAcEnrichment_Create_IAuth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .build());


        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_Create_reverseActions = new ArrayList<Action>();
        return verAcEnrichment_Create_reverseActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_reverseRelationsStr = "";
        verAcEnrichment_Create_reverseRelationsStr += "http://temenostech.temenos.com/rels/reverse ";
        String[] verAcEnrichment_Create_reverseRelations = verAcEnrichment_Create_reverseRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_reverseRelations;
    }
    
}
