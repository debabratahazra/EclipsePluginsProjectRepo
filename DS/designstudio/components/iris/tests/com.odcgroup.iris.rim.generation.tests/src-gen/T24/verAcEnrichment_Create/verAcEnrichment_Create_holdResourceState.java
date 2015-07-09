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

public class verAcEnrichment_Create_holdResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_holdResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_holdResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_hold", createActions(), "/{companyid}/verAcEnrichment_Creates('{id}')/hold", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_hold", "/{companyid}/verAcEnrichment_Creates('{id}')/hold"), factory.getResourceState("Errors.Errors"));
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_hold = this;
        // create transitions
        ResourceState sverAcEnrichment_Create_input = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_input");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Create_hold.addTransition(new Transition.Builder()
                    .method("PUT")
                    .target(sverAcEnrichment_Create_input)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("input deal")
                    .build());




        ResourceState sverAcEnrichment_Create_delete = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_delete");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Create_hold.addTransition(new Transition.Builder()
                    .method("DELETE")
                    .target(sverAcEnrichment_Create_delete)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("delete")
                    .build());




        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_Create_holdActions = new ArrayList<Action>();
        return verAcEnrichment_Create_holdActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_holdRelationsStr = "";
        verAcEnrichment_Create_holdRelationsStr += "http://temenostech.temenos.com/rels/hold ";
        String[] verAcEnrichment_Create_holdRelations = verAcEnrichment_Create_holdRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_holdRelations;
    }
    
}
