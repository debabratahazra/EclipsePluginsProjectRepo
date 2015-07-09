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

public class verCustomer_Create_metadataResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_metadataResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_metadataResourceState(ResourceFactory factory) {
        super("T24FieldMetadata", "verCustomer_Create_metadata", createActions(), "/{companyid}/verCustomer_Creates()/metadata", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sverCustomer_Create_metadata = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Create_metadataActions = new ArrayList<Action>();
        return verCustomer_Create_metadataActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Create_metadataRelationsStr = "";
        verCustomer_Create_metadataRelationsStr += "http://temenostech.temenos.com/rels/metadata ";
        String[] verCustomer_Create_metadataRelations = verCustomer_Create_metadataRelationsStr.trim().split(" ");
        return verCustomer_Create_metadataRelations;
    }
    
}
