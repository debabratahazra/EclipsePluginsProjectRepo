package com.odcgroup.page.model.util;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.JAVA_CODE;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;

import ch.vorburger.el.engine.Expression;
import ch.vorburger.el.engine.ExpressionContext;
import ch.vorburger.el.engine.ExpressionParsingException;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.PageModelCore;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.el.engine.DSELContext;
import com.odcgroup.workbench.el.engine.DSExpressionFactory;

/**
 * EL (Workbench -> Vorburger.CH) entry page for DS TAP page designer.
 *
 * DSExpressionWithContextGeneratorTest (in page-model-tests) illustrates how to use this.
 *
 * @author Michael Vorburger
 */
public class ConditionUtils {

	// As the JavaDoc says, initializing a DSExpressionFactory is probably
	// expensive - don't do it for every getConditionAsJavaCode(), but only once.
	// (Having said that, this is still "wrong" of course, ideally we shouldn't
	// use StandaloneSetup at all anymore here, and instead @Inject Guice DI, only.)
	private static final DSExpressionFactory factory = new DSExpressionFactory();
	
	public static String getConditionAsJavaCode(Widget w, boolean udp) throws CoreException {
		String lang = w.getPropertyValue(PropertyTypeConstants.CONDITION_LANG);
		if(lang==null || lang.equals("XSP")) {
			return w.getPropertyValue(JAVA_CODE);
		} else {
			String condition = w.getPropertyValue(JAVA_CODE);
			DSELContext variables = getVariables(w);
			return getConditionAsJavaCode(variables, condition, w.getModel(), udp);
		}
	}
	
	// used above, as well as from ConditionDialogUtils
	public static DSELContext getVariables(Widget w) {
		DSELContext variables = new DSELContext();
		String mainDatasetName = w.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		String mainDatasetVar = mainDatasetName.substring(mainDatasetName.indexOf(':') + 1);
		variables.add(mainDatasetVar, mainDatasetName);
		return variables;
	}
	
	public static String getConditionAsJavaCode(DSELContext variables, String conditionText, Notifier context, boolean udp) throws CoreException {
		ExpressionContext expressionContext = variables.getExpressionContext(context);
		try {
			Expression expression = factory.newExpressionFromString(conditionText, expressionContext);
			PageDSELCompiler compiler = factory.getInjector().getInstance(PageDSELCompiler.class);
			compiler.setUdpStyle(udp);
			
			// instead of ch.vorburger.el.engine.ExpressionImpl.generateJavaCode(Class<?>) we do:
			String javaCode = compiler.compile(expression.getXExpression(), Boolean.class);

			// THIS IS VERY IMPORTANT! @see http://rd.oams.com/browse/DS-5851
			expression.dispose();
			
			javaCode = escapeGenerics(javaCode);
			javaCode = escapeMore(javaCode);
			
			return javaCode;
		} catch (ExpressionParsingException e) {
			throw new CoreException(new Status(IStatus.ERROR, PageModelCore.PLUGIN_ID, "Error parsing condition", e));
		}
	}

	/**
	 * DS-5547 This is a bit of a hack... since Xtext v2.3.1 the conditional
	 * XBase expressions are returned as
	 * "new org.eclipse.xtext.xbase.lib.Functions.Function0<Boolean>() {" and
	 * this is to escape that inside the xsp:logic:
	 */
	private static String escapeGenerics(String javaCode) {
		javaCode = javaCode.replaceAll("<Boolean>", "&lt;Boolean&gt;");
		javaCode = javaCode.replaceAll("<Object>", "&lt;Object&gt;");				
		javaCode = javaCode.replaceAll("<Number>", "&lt;Number&gt;");
		javaCode = javaCode.replaceAll("<Byte>", "&lt;Byte&gt;");
		javaCode = javaCode.replaceAll("<Double>", "&lt;Double&gt;");
		javaCode = javaCode.replaceAll("<Float>", "&lt;Float&gt;");
		javaCode = javaCode.replaceAll("<Integer>", "&lt;Integer&gt;");
		javaCode = javaCode.replaceAll("<Long>", "&lt;Long&gt;");
		javaCode = javaCode.replaceAll("<Short>", "&lt;Short&gt;");
		javaCode = javaCode.replaceAll("<java.util.Date>", "&lt;java.util.Date&gt;");
		javaCode = javaCode.replaceAll("<java.math.BigDecimal>", "&lt;java.math.BigDecimal&gt;");
		javaCode = javaCode.replaceAll("<java.math.BigInteger>", "&lt;java.math.BigInteger&gt;");
		javaCode = javaCode.replaceAll("<Character>", "&lt;Character&gt;");
		return javaCode;
	}

	private static String escapeMore(String javaCode) {
		int pos = javaCode.indexOf("&& ");
		if (pos >= 0) {
			javaCode = javaCode.replaceAll("&& ", "&amp;&amp; ");
		}
		pos = javaCode.indexOf("& ");
		if (pos >= 0) {
			javaCode = javaCode.replaceAll("& ", "&amp; ");
		}
		pos = javaCode.indexOf(" < ");
		if (pos >= 0) {
			javaCode = javaCode.replaceAll(" < ", " &lt; ");
		}
		pos = javaCode.indexOf(" > ");
		if (pos >= 0) {
			javaCode = javaCode.replaceAll(" > ", " &gt; ");
		}
		return javaCode;
	}

}
