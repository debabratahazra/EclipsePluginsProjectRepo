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

public class verAcEnrichment_Create_IAuth_seeResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_IAuth_seeResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_IAuth_seeResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_IAuth_see", createActions(), "/{companyid}/verAcEnrichment_Creates_IAuth('{id}')/see", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_IAuth_see", "/{companyid}/verAcEnrichment_Creates_IAuth('{id}')/see"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_IAuth_see = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_Create_IAuth_seeActions = new ArrayList<Action>();
        return verAcEnrichment_Create_IAuth_seeActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_IAuth_seeRelationsStr = "";
        verAcEnrichment_Create_IAuth_seeRelationsStr += "http://temenostech.temenos.com/rels/see ";
        String[] verAcEnrichment_Create_IAuth_seeRelations = verAcEnrichment_Create_IAuth_seeRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_IAuth_seeRelations;
    }
    
}
