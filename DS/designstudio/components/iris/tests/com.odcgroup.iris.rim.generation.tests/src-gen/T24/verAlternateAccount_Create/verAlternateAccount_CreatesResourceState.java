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

public class verAlternateAccount_CreatesResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public verAlternateAccount_CreatesResourceState() {
        this(new ResourceFactory());
    }

    public verAlternateAccount_CreatesResourceState(ResourceFactory factory) {
        super("verAlternateAccount_Create", "verAlternateAccount_Creates", createActions(), "/{companyid}/verAlternateAccount_Creates()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState sverAlternateAccount_Creates = this;
        // create transitions
        ResourceState sverAlternateAccount_Create = factory.getResourceState("T24.verAlternateAccount_Create.verAlternateAccount_Create");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{AlternativeNumber}");
            sverAlternateAccount_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sverAlternateAccount_Create)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("verAlternateAccount_Create")
                    .linkId("")
                    .build());



        ResourceState sverAlternateAccount_Create_see = factory.getResourceState("T24.verAlternateAccount_Create.verAlternateAccount_Create_see");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{AlternativeNumber}");
            sverAlternateAccount_Creates.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sverAlternateAccount_Create_see)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("see record")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> verAlternateAccount_CreatesActions = new ArrayList<Action>();
        return verAlternateAccount_CreatesActions;
    }

    private static String[] createLinkRelations() {
        String[] verAlternateAccount_CreatesRelations = null;
        return verAlternateAccount_CreatesRelations;
    }
    
}
