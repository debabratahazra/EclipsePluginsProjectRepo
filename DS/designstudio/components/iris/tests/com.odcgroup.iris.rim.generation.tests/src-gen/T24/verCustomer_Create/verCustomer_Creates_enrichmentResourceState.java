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

public class verCustomer_Creates_enrichmentResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Creates_enrichmentResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Creates_enrichmentResourceState(ResourceFactory factory) {
        super("Enrichment", "verCustomer_Creates_enrichment", createActions(), "/{companyid}/verCustomer_Creates_enrichment()/enrichment", createLinkRelations(), new UriSpecification("verCustomer_Creates_enrichment", "/{companyid}/verCustomer_Creates_enrichment()/enrichment"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Creates_enrichment = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Creates_enrichmentActions = new ArrayList<Action>();
        return verCustomer_Creates_enrichmentActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Creates_enrichmentRelationsStr = "";
        verCustomer_Creates_enrichmentRelationsStr += "http://temenostech.temenos.com/rels/enrichment ";
        String[] verCustomer_Creates_enrichmentRelations = verCustomer_Creates_enrichmentRelationsStr.trim().split(" ");
        return verCustomer_Creates_enrichmentRelations;
    }
    
}
