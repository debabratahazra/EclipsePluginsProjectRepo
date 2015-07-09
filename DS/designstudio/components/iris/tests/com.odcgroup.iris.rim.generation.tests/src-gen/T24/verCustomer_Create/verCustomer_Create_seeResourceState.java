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

public class verCustomer_Create_seeResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verCustomer_Create_seeResourceState() {
        this(new ResourceFactory());
    }

    public verCustomer_Create_seeResourceState(ResourceFactory factory) {
        super("verCustomer_Create", "verCustomer_Create_see", createActions(), "/{companyid}/verCustomer_Creates('{id}')/see", createLinkRelations(), new UriSpecification("verCustomer_Create_see", "/{companyid}/verCustomer_Creates('{id}')/see"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverCustomer_Create_see = this;
        // create transitions
        ResourceState sverCustomer_Create_metadata = factory.getResourceState("T24.verCustomer_Create.verCustomer_Create_metadata");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverCustomer_Create_see.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("POST")
                    .target(sverCustomer_Create_metadata)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("metadata")
                    .build());
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verCustomer_Create_seeActions = new ArrayList<Action>();
        return verCustomer_Create_seeActions;
    }

    private static String[] createLinkRelations() {
        String verCustomer_Create_seeRelationsStr = "";
        verCustomer_Create_seeRelationsStr += "http://temenostech.temenos.com/rels/see ";
        String[] verCustomer_Create_seeRelations = verCustomer_Create_seeRelationsStr.trim().split(" ");
        return verCustomer_Create_seeRelations;
    }
    
}
