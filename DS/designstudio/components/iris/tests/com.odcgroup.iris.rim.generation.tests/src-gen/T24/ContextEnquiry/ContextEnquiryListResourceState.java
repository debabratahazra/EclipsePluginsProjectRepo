package T24.ContextEnquiry;
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

public class ContextEnquiryListResourceState extends ResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public ContextEnquiryListResourceState() {
        this(new ResourceFactory());
    }

    public ContextEnquiryListResourceState(ResourceFactory factory) {
        super("Entity", "ContextEnquiryList", createActions(), "/{companyid}/ContextEnquiryList/{entity}", createLinkRelations(), new UriSpecification("ContextEnquiryList", "/{companyid}/ContextEnquiryList/{entity}"), null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        ResourceState sContextEnquiryList = this;
        // create transitions
        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> ContextEnquiryListActions = new ArrayList<Action>();
        ContextEnquiryListActions.add(new Action("NoopGET", Action.TYPE.VIEW, new Properties()));
        return ContextEnquiryListActions;
    }

    private static String[] createLinkRelations() {
        String ContextEnquiryListRelationsStr = "";
        ContextEnquiryListRelationsStr += "http://www.temenos.com/rels/contextenquiry ";
        String[] ContextEnquiryListRelations = ContextEnquiryListRelationsStr.trim().split(" ");
        return ContextEnquiryListRelations;
    }
    
}
