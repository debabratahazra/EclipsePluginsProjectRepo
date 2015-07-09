package T24.verAlternateAccount_Create;
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

public class verAlternateAccount_Create_seeResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAlternateAccount_Create_seeResourceState() {
        this(new ResourceFactory());
    }

    public verAlternateAccount_Create_seeResourceState(ResourceFactory factory) {
        super("verAlternateAccount_Create", "verAlternateAccount_Create_see", createActions(), "/{companyid}/verAlternateAccount_Creates('{id}')/see", createLinkRelations(), new UriSpecification("verAlternateAccount_Create_see", "/{companyid}/verAlternateAccount_Creates('{id}')/see"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sverAlternateAccount_Create_see = this;
        // create transitions
        ResourceState sverAlternateAccount_Create_metadata = factory.getResourceState("T24.verAlternateAccount_Create.verAlternateAccount_Create_metadata");




        // create EMBEDDED transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            sverAlternateAccount_Create_see.addTransition(new Transition.Builder()
                    .flags(Transition.EMBEDDED)
                    .method("POST")
                    .target(sverAlternateAccount_Create_metadata)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("metadata")
                    .build());
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAlternateAccount_Create_seeActions = new ArrayList<Action>();
        return verAlternateAccount_Create_seeActions;
    }

    private static String[] createLinkRelations() {
        String verAlternateAccount_Create_seeRelationsStr = "";
        verAlternateAccount_Create_seeRelationsStr += "http://temenostech.temenos.com/rels/see ";
        String[] verAlternateAccount_Create_seeRelations = verAlternateAccount_Create_seeRelationsStr.trim().split(" ");
        return verAlternateAccount_Create_seeRelations;
    }
    
}
