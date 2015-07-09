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

public class verAcEnrichment_Create_deleteResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAcEnrichment_Create_deleteResourceState() {
        this(new ResourceFactory());
    }

    public verAcEnrichment_Create_deleteResourceState(ResourceFactory factory) {
        super("verAcEnrichment_Create", "verAcEnrichment_Create_delete", createActions(), "/{companyid}/verAcEnrichment_Creates('{id}')/delete", createLinkRelations(), new UriSpecification("verAcEnrichment_Create_delete", "/{companyid}/verAcEnrichment_Creates('{id}')/delete"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAcEnrichment_Create_delete = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAcEnrichment_Create_deleteActions = new ArrayList<Action>();
        return verAcEnrichment_Create_deleteActions;
    }

    private static String[] createLinkRelations() {
        String verAcEnrichment_Create_deleteRelationsStr = "";
        verAcEnrichment_Create_deleteRelationsStr += "http://temenostech.temenos.com/rels/delete ";
        String[] verAcEnrichment_Create_deleteRelations = verAcEnrichment_Create_deleteRelationsStr.trim().split(" ");
        return verAcEnrichment_Create_deleteRelations;
    }
    
}
