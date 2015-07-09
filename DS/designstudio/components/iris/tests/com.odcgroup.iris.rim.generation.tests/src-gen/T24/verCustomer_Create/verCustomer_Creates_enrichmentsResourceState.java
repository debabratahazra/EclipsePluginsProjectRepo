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

public class verCustomer_Creates_enrichmentsResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Creates_enrichmentsResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Creates_enrichmentsResourceState(ResourceFactory factory) {
        super("Enrichment", "verCustomer_Creates_enrichments", createActions(), "/{companyid}/verCustomer_Creates_enrichments()/enrichments", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sverCustomer_Creates_enrichments = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Creates_enrichmentsActions = new ArrayList<Action>();
        return verCustomer_Creates_enrichmentsActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Creates_enrichmentsRelationsStr = "";
        verCustomer_Creates_enrichmentsRelationsStr += "http://temenostech.temenos.com/rels/enrichments ";
        String[] verCustomer_Creates_enrichmentsRelations = verCustomer_Creates_enrichmentsRelationsStr.trim().split(" ");
        return verCustomer_Creates_enrichmentsRelations;
    }
    
}
