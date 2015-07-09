package T24.enqlistAlternateAccount;
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

public class enqlistAlternateAccountResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqlistAlternateAccountResourceState() {
        this(new ResourceFactory());
    }

    public enqlistAlternateAccountResourceState(ResourceFactory factory) {
        super("enqlistAlternateAccount", "enqlistAlternateAccount", createActions(), "/{companyid}/enqlistAlternateAccounts('{id}')", createLinkRelations(), new UriSpecification("enqlistAlternateAccount", "/{companyid}/enqlistAlternateAccounts('{id}')"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState senqlistAlternateAccount = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqlistAlternateAccountActions = new ArrayList<Action>();
        enqlistAlternateAccountActions.add(new Action("GETEntity", Action.TYPE.VIEW, new Properties()));
        return enqlistAlternateAccountActions;
    }

    private static String[] createLinkRelations() {
        String[] enqlistAlternateAccountRelations = null;
        return enqlistAlternateAccountRelations;
    }
    
}
