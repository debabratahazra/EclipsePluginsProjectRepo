package T24.enqAcctBalToday;
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

public class enqAcctBalTodaysResourceState extends CollectionResourceState implements LazyResourceLoader {
    
    private ResourceFactory factory = null;

    public enqAcctBalTodaysResourceState() {
        this(new ResourceFactory());
    }

    public enqAcctBalTodaysResourceState(ResourceFactory factory) {
        super("enqAcctBalToday", "enqAcctBalTodays", createActions(), "/{companyid}/enqAcctBalTodays()", createLinkRelations(), null, null);
        this.factory = factory;
    }
    
    public boolean initialise() {
        Map<String, String> uriLinkageProperties = new HashMap<String, String>();
        List<Expression> conditionalLinkExpressions = null;
        CollectionResourceState senqAcctBalTodays = this;
        // create transitions
        ResourceState senqAcctBalToday = factory.getResourceState("T24.enqAcctBalToday.enqAcctBalToday");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{AccountNumber}");
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(senqAcctBalToday)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("enqAcctBalToday")
                    .linkId("")
                    .build());



        ResourceState sT24_enqStmtEntToday_enqStmtEntTodays = factory.getResourceState("T24.enqStmtEntToday.enqStmtEntTodays");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sT24_enqStmtEntToday_enqStmtEntTodays)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Statement entries today")
                    .linkId("1")
                    .build());



        ResourceState sT24_enqStmtEntLast_enqStmtEntLasts = factory.getResourceState("T24.enqStmtEntLast.enqStmtEntLasts");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sT24_enqStmtEntLast_enqStmtEntLasts)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Entries Since Last Stmt")
                    .linkId("2")
                    .build());



        ResourceState sT24_enqNostroFwdBal_enqNostroFwdBals = factory.getResourceState("T24.enqNostroFwdBal.enqNostroFwdBals");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("filter", "AccountId eq '{Acc}'");
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sT24_enqNostroFwdBal_enqNostroFwdBals)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Forward projections")
                    .linkId("3")
                    .build());



        ResourceState sT24_enqAcctStmtHist_enqAcctStmtHists = factory.getResourceState("T24.enqAcctStmtHist.enqAcctStmtHists");

        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("filter", "StmtAccountNo eq '{Acc}'");
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(sT24_enqAcctStmtHist_enqAcctStmtHists)
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Historical Statements")
                    .linkId("4")
                    .build());




        // create foreach transition
            conditionalLinkExpressions = null;
            uriLinkageProperties.clear();
        uriLinkageProperties.put("id", "{id}");
            senqAcctBalTodays.addTransition(new Transition.Builder()
                    .flags(Transition.FOR_EACH)
                    .method("GET")
                    .target(new DynamicResourceState("enqAcctBalToday", "dynamic", "t24ResourceLocator", new String[] {"{AccFile}"}))
                    .uriParameters(uriLinkageProperties)
                    .evaluation(conditionalLinkExpressions != null ? new SimpleLogicalExpressionEvaluator(conditionalLinkExpressions) : null)
                    .label("Account details")
                    .linkId("")
                    .build());



        return true;
    }
    
    private static List<Action> createActions() {
        Properties actionViewProperties = null;
        List<Action> enqAcctBalTodaysActions = new ArrayList<Action>();
        return enqAcctBalTodaysActions;
    }

    private static String[] createLinkRelations() {
        String[] enqAcctBalTodaysRelations = null;
        return enqAcctBalTodaysRelations;
    }
    
}
