package T24.enqlistAcEnrichment;
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

public class enqlistAcEnrichmentsUnauthResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqlistAcEnrichmentsUnauthResourceState() {
        this(new ResourceFactory());
    }

    public enqlistAcEnrichmentsUnauthResourceState(ResourceFactory factory) {
        super("enqlistAcEnrichment", "enqlistAcEnrichmentsUnauth", createActions(), "/{companyid}/enqlistAcEnrichmentsUnauth()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState senqlistAcEnrichmentsUnauth = this;
        // create transitions
        ResourceState senqlistAcEnrichmentUnauth = factory.getResourceState("T24.enqlistAcEnrichment.enqlistAcEnrichmentUnauth");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{PrimaryKey}");
            senqlistAcEnrichmentsUnauth.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(senqlistAcEnrichmentUnauth)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("enqlistAcEnrichmentUnauth")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqlistAcEnrichmentsUnauthActions = new ArrayList<Action>();
        actionViewProperties = new Properties();
        actionViewProperties.put("filter", "{filter}");
        enqlistAcEnrichmentsUnauthActions.add(new Action("GetIauthEntities", Action.TYPE.VIEW, actionViewProperties));
        return enqlistAcEnrichmentsUnauthActions;
    }

    private static String[] createLinkRelations() {
        String[] enqlistAcEnrichmentsUnauthRelations = null;
        return enqlistAcEnrichmentsUnauthRelations;
    }
    
}
