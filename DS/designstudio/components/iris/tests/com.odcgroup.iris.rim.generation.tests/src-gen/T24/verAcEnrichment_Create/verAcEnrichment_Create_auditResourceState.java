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

public class verAcEnrichment_Create_auditResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_auditResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_auditResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_audit", createActions(), "/{companyid}/verAcEnrichment_Creates('{id}')/review", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_audit", "/{companyid}/verAcEnrichment_Creates('{id}')/review"), factory.getResourceState("Errors.Errors"));
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_audit = this;
        // create transitions
        ResourceState sverAcEnrichment_Create = factory.getResourceState("T24.verAcEnrichment_Create.verAcEnrichment_Create");
        // create regular transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{IdEnri}");
            sverAcEnrichment_Create_audit.addTransition(new Transition.Builder()
                    .method("GET")
                    .target(sverAcEnrichment_Create)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("verAcEnrichment_Create")
                    .build());




        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_Create_auditActions = new ArrayList<Action>();
        return verAcEnrichment_Create_auditActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_auditRelationsStr = "";
        verAcEnrichment_Create_auditRelationsStr += "http://temenostech.temenos.com/rels/review ";
        String[] verAcEnrichment_Create_auditRelations = verAcEnrichment_Create_auditRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_auditRelations;
    }
    
}
