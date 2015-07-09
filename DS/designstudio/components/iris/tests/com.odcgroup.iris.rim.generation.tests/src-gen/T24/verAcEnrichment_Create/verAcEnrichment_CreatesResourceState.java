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

public class verAcEnrichment_CreatesResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_CreatesResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_CreatesResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Creates", createActions(), "/{companyid}/verAcEnrichment_Creates()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sverAcEnrichment_Creates = this;
        // create transitions
        ResourceState sverAcEnrichment_Create = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sverAcEnrichment_Create)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("verAcEnrichment_Create")
                    .linkId("")
                    .build());



        ResourceState sverAcEnrichment_Create_see = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_see");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sverAcEnrichment_Create_see)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("see record")
                    .linkId("")
                    .build());



        ResourceState sverAcEnrichment_Create_new = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_new");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        conditionalLinkExpressions = new ArrayList<Expression>();
        conditionalLinkExpressions.add(new ResourceGETExpression(factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_autoId"), ResourceGETExpression.Function.OK));
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverAcEnrichment_Create_new)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("create new deal")
                    .build());




        ResourceState sverAcEnrichment_Create_populate = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_populate");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .method("POST")
                    .target(sverAcEnrichment_Create_populate)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("populate existing deal")
                    .build());




        ResourceState sverAcEnrichment_Create_input = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_input");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("PUT")
                    .target(sverAcEnrichment_Create_input)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("input deal")
                    .linkId("")
                    .build());



        ResourceState sverAcEnrichment_Create_audit = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create_audit");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("POST")
                    .target(sverAcEnrichment_Create_audit)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("audit deal")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_CreatesActions = new ArrayList<Action>();
        return verAcEnrichment_CreatesActions;
    }

    private static String[] createLinkRelations() {
        String[] verAcEnrichment_CreatesRelations = null;
        return verAcEnrichment_CreatesRelations;
    }
    
}
