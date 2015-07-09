package T24.enqCustomerInfo;
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

public class enqCustomerInfosResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqCustomerInfosResourceState() {
        this(new ResourceFactory());
    }

    public enqCustomerInfosResourceState(ResourceFactory factory) {
        super("enqCustomerInfo", "enqCustomerInfos", createActions(), "/{companyid}/enqCustomerInfos()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState senqCustomerInfos = this;
        // create transitions
        ResourceState senqCustomerInfo = factory.getResourceState("T24.enqCustomerInfo.enqCustomerInfo");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{CusNo}");
            senqCustomerInfos.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(senqCustomerInfo)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("enqCustomerInfo")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqCustomerInfosActions = new ArrayList<Action>();
        return enqCustomerInfosActions;
    }

    private static String[] createLinkRelations() {
        String[] enqCustomerInfosRelations = null;
        return enqCustomerInfosRelations;
    }
    
}
