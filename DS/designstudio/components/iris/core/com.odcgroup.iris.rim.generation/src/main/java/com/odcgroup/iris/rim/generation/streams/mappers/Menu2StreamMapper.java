package com.odcgroup.iris.rim.generation.streams.mappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.iris.rim.generation.ParameterParser;
import com.odcgroup.iris.rim.generation.ParameterParserResult;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.menu.menu.Translation;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.di.ILangSpecificProvider;
import com.odcgroup.workbench.core.xtext.XtextProxyUtil;
import com.temenos.interaction.rimdsl.rim.Entity;
import com.temenos.interaction.rimdsl.rim.ResourceType;
import com.temenos.interaction.rimdsl.rim.RimFactory;
import com.temenos.interaction.rimdsl.rim.State;

/**
 * TODO: Document me!
 * 
 * @author taubert
 * 
 */
public class Menu2StreamMapper implements StreamMapper<Void> {

	private EObject rootObject;
	private static final Logger logger = LoggerFactory.getLogger(Menu2StreamMapper.class);

	private final ILangSpecificProvider<XtextProxyUtil> _proxyUtilProvider;
	Properties props = new Properties();
	final File fProperties;

	public Menu2StreamMapper(ILangSpecificProvider<XtextProxyUtil> proxyUtilProvider, String genProject) {
		this._proxyUtilProvider = proxyUtilProvider;
		String genProjectLocation = "";
		try {
			genProjectLocation = getProjectLocation(genProject);
		} catch (IllegalStateException ise) {
			logger.error("Failed to find the project location for '" + genProject + "'", ise);
		}

		fProperties = new File(genProjectLocation,
				"src/generated/edge/META-INF/resources/WEB-INF/data/MenuRoot.properties");
		/*
		 * ensure the path exists
		 */
		fProperties.getParentFile().mkdirs();
	}

	@Override
	public List<Void> map(EObject source, RimWriter destination, String rimName) {
		this.rootObject = source;

		if (fProperties.exists()) {
			FileInputStream input = null;
			try {
				input = new FileInputStream(fProperties);
				props.load(input);
			} catch (Exception e) {
				logger.error("Failed to read '" + fProperties.getAbsolutePath() + "'", e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
		}

		destination.println("domain " + T24ResourceModelsGenerator.getDomain(null) + " {");
		destination.println("use common.CoreCommands.*");
		destination.println("use common.HTTPEvents.*");
		destination.println("use common.ODataCommands.*");
		destination.println("use common.T24Commands.*");
		destination.println("use common.T24Events.*");
		destination.println("");
		destination.println("rim mnu" + rimName + " {");
		destination.println("");
		destination.println("command GETMenu");
		destination.println("");
		destination.println("basepath: \"/{companyid}\"");
		destination.println("");

		MenuItem item = ((MenuRoot) source).getRootItem();
		PropertyName property = new PropertyName(RESOURCE_TYPE.menu + EMUtils.camelCaseName(item.getName()));

		createBranchOrLeaf(null, destination, ((MenuRoot) source).getRootItem(), property);
		props.put(property.Name, property.toString());
		destination.println("}");
		destination.println("}");

		FileOutputStream output = null;
		try {
			output = new FileOutputStream(fProperties);
			props.store(output, "File representing the menut structure.");
		} catch (Exception e) {
			logger.error("Failed to save '" + fProperties.getAbsolutePath() + "'", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}

		}

		return null;
	}

	void createBranchOrLeaf(State dummyLeaf, RimWriter destination, MenuItem item, PropertyName property) {
		if (item.getSubmenus().size() > 0) {
			createBranch(dummyLeaf, destination, item, property);
		} else {
			/*
			 * one single item without sub-items in this menu.
			 */
			String oneBranchRimName = ParameterParser.getResourceName(T24ResourceModelsGenerator.TYPE_MENU(),
					item.getName(), item.getParameters()).getResourceName();

			destination.println("resource " + oneBranchRimName + " {");
			destination.println("type: item");
			destination.println("entity: Menu");
			destination.println("view: GETMenu ");
			destination.ident();
			destination.println("relations [ \"http://www.temenos.com/rels/menu\" ]");

			createLeaf(dummyLeaf, destination, item, property);

			destination.println("}");
		}
	}

	void createBranch(State noopLeaf, RimWriter destination, MenuItem item, PropertyName property) {
		/*
		 * No possible parameter on a branch
		 */
		String oneBranchRimName = ParameterParser.getResourceName(T24ResourceModelsGenerator.TYPE_MENU(),
				item.getName(), null).getResourceName();

		if (property == null) {
			property = new PropertyName(item.getName());
		}
		destination.println("resource " + oneBranchRimName + " {");
		destination.println("type: item");
		destination.println("entity: Menu");
		destination.println("view: GETMenu");
		destination.ident();
		destination.println("relations [ \"http://www.temenos.com/rels/menu\" ]");

		for (MenuItem child : item.getSubmenus()) {
			createLeaf(noopLeaf, destination, child, property);
		}

		destination.println("}");

		for (MenuItem child : item.getSubmenus()) {
			if (child.getSubmenus().size() > 0) {

				property = new PropertyName(child.getName());
				createBranch(noopLeaf, destination, child, property);
				props.put(property.Name, property.toString());

			}
		}
	}

	void createLeaf(State noopLeaf, RimWriter destination, MenuItem item, PropertyName property) {

		String t24Name = "";
		String relation = "GET -> ";
		String packageName = T24ResourceModelsGenerator.getDomain(null); // default
		RESOURCE_TYPE resourceType = RESOURCE_TYPE.unknown;

		if (item.getVersion() != null) {
			EObject eobject = item.getVersion();
			if (eobject.eIsProxy()) {
				// if version is still a proxy after you've tried to
				// getVersion(), then it means that it's an unresolved (broken)
				// cross reference... so:
				t24Name = _proxyUtilProvider.get(item.eResource().getURI()).getProxyCrossRefAsString(item, eobject);
			} else {
				t24Name = ((Version) eobject).getT24Name();
			}
			resourceType = RESOURCE_TYPE.version;
			packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
		} else if (item.getApplication() != null) {
			MdfClass eobject = item.getApplication();
			t24Name = eobject.getName();
			resourceType = RESOURCE_TYPE.version;
			packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
		} else if (item.getCompositeScreen() != null) {
			EObject eobject = item.getCompositeScreen();
			if (eobject.eIsProxy()) {
				t24Name = _proxyUtilProvider.get(item.eResource().getURI()).getProxyCrossRefAsString(item, eobject);
			} else {
				t24Name = ((CompositeScreen) eobject).getName();
			}
			resourceType = RESOURCE_TYPE.composite;
			packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
		} else if (item.getEnquiry() != null) {
			EObject eobject = item.getEnquiry();
			if (eobject.eIsProxy()) {
				t24Name = _proxyUtilProvider.get(item.eResource().getURI()).getProxyCrossRefAsString(item, eobject);
			} else {
				t24Name = ((Enquiry) eobject).getName();
			}
			resourceType = RESOURCE_TYPE.enquiry;
			packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
		} else if (item.getIncludedMenu() != null) {
			MenuRoot menuItem = (MenuRoot) item.getIncludedMenu();
			if (menuItem != null) {
				if (menuItem.eIsProxy()) {
					t24Name = _proxyUtilProvider.get(item.eResource().getURI())
							.getProxyCrossRefAsString(item, menuItem);
					resourceType = RESOURCE_TYPE.menu;
					packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
					relation = "GET +-> ";
				} else {
					if (menuItem.getRootItem() != null) {
						t24Name = menuItem.getRootItem().getName();
						resourceType = RESOURCE_TYPE.menu;
						packageName += "." + resourceType.toString() + EMUtils.camelCaseName(t24Name);
						relation = "GET +-> ";
					}
				}
			}
		} else if (item.getSubmenus() != null && item.getSubmenus().size() > 0) {
			t24Name = item.getName();
			resourceType = RESOURCE_TYPE.menu;
			packageName = "";
			relation = "GET +-> ";
		}

		if (t24Name == null || t24Name.length() == 0) {
			resourceType = RESOURCE_TYPE.unknown;
			t24Name = "InteractionException";
			packageName = "hothouse.Hothouse";
		}

		String parameters = null;
		if (resourceType != RESOURCE_TYPE.menu) {
			parameters = item.getParameters();
			if (parameters != null) {
				String[] aParams = parameters.split(" ");
				if (!parameters.trim().startsWith("S")) {
					if (aParams.length == 2 || parameters.trim().startsWith("A")) {
						relation = "POST -> ";
					}
				}
			}
		}
		ParameterParserResult pResult = ParameterParser.getResourceName(resourceType, t24Name, parameters);
		String resourceName = pResult.getResourceName();
		if (packageName.length() > 0) {
			resourceName = packageName + "." + resourceName;
		}

		RelAndPath ret = resolvePathAndRel(resourceType, resourceName, pResult.getParameters());
		PropertyItem pItem = null;
		if (resourceType == RESOURCE_TYPE.menu) {
			pItem = new PropertyItem(RESOURCE_TYPE.menu + EMUtils.camelCaseName(t24Name), "M");
		} else {
			pItem = new PropertyItem(t24Name, "R");
			pItem.Rel = ret.link;
			pItem.Ref = ret.href;
		}
		/*
		 * Add the labels
		 */
		if (item.getLabels() != null && item.getLabels().size() > 0) {
			for (Translation tr : item.getLabels()) {
				pItem.Descriptions.put(tr.getLanguage(), tr.getMessage());
			}
		} else {
			pItem.Descriptions.put("en", t24Name);
		}

		property.Items.add(pItem);

		// FT.FT_Contract.verFundsTransfer_Edge.verFundsTransfer_Edge_new

		destination.println(relation + "\"" + resourceName + "\" {");
		destination.println("title: \"" + getFirstLabel(item) + "\"");

		if (pResult.getParameters() != null) {
			destination.println(pResult.getParameters()); // *** NEW
		}

		destination.println("}");

	}

	String getFirstLabel(MenuItem item) {
		if (item.getLabels() != null && item.getLabels().size() > 0 && item.getLabels().get(0).getMessage() != null) {
			String title = item.getLabels().get(0).getMessage().replace("\"", "'");
			// DS-7452 : letting \n in the string breaks the rim generation
			// letting \n in the rim breaks the java generation
			// So in the rim -> \\n which will end up in \n in java.
			title = title.replace("\n", "\\\\n");
			title = title.replace("\r", "\\\\r");
			return title;
		} else {
			/*
			 * Should always exists.
			 */
			return item.getName();
		}
	}

	ResourceType createItemResourceType() {
		ResourceType resourceType = RimFactory.eINSTANCE.createResourceType();
		resourceType.setIsItem(true);
		return resourceType;
	}

	Entity createEntity(String entityName) {
		Entity entity = RimFactory.eINSTANCE.createEntity();
		entity.setName(entityName);
		return entity;
	}

	private RelAndPath resolvePathAndRel(RESOURCE_TYPE resourceType, String sOrigName, String parameter) {
		RelAndPath ret = new RelAndPath();
		String sName = sOrigName;
		int pos = sName.lastIndexOf(".");
		if (pos > 0) {
			sName = sName.substring(pos + 1);
		}
		String hRefSuffix = "()";
		ret.link = sName; // Just a default value

		if (parameter != null) {
			parameter = parameter.trim();
			if (parameter.startsWith("parameters [")) {
				parameter = parameter.substring("parameters [".length());
				if (parameter.endsWith("]")) {
					parameter = parameter.substring(0, parameter.length() - 1);
				}
			}
			parameter = parameter.trim();
			parameter = parameter.replace(" = ", "=");
			parameter = parameter.replace("\"", "");

		}

		if (resourceType == RESOURCE_TYPE.version) {
			if (sName.equals("verPwProcess_Auto")) {
				ret.href = "verPwProcess_Autos()/pw";
				ret.link = "http://temenostech.temenos.com/rels/pw";

			} else {
				String suffix = getSuffix(sOrigName);
				if (suffix.equals("_new")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s()/new";
					ret.link = sName + " new";
				} else if (suffix.equals("_input")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')";
					ret.link = sName;
				} else if (suffix.equals("_audit")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')/review";
					ret.link = sName;
				} else if (suffix.equals("_reverse")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')/reverse";
					ret.link = sName;
				} else if (suffix.equals("_delete")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')/delete";
					ret.link = sName;
				} else if (suffix.equals("_validate")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')/validate";
					ret.link = sName;
				} else if (suffix.equals("_hold")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s('{id}')/hold";
					ret.link = sName;
				} else if (suffix.equals("_populate")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s()/populate";
					ret.link = sName;
				} else if (suffix.equals("s")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "s()";
					ret.link = sName;
				} else if (suffix.equals("_metadata")) {
					sName = sName.substring(0, sName.length() - "_metadata".length());
					hRefSuffix = "s()/metadata";
					ret.link = sName;
				} else if (suffix.equals("_see")) {
					sName = sName.substring(0, sName.length() - "_see".length());
					hRefSuffix = "s('{id}')/see";
					ret.link = sName;
				} else if (sName.endsWith("_IAuth")) {
					sName = sName.substring(0, sName.length() - "_IAuth".length()) + "s_IAuth";
					hRefSuffix = "('{id}')";
					ret.link = sName;
				} else if (suffix.equals("Entry")) {
					sName = sName.substring(0, sName.length() - suffix.length());
					hRefSuffix = "Entry"; // so it puts back what we just removed.
					ret.link = sName + " contract";
				} else if (suffix.equals("")) {
					hRefSuffix = "s('{id}')";
					ret.link = sName;
				} else{
					ret.link = sName;
				}

				if (parameter != null) {
					pos = parameter.indexOf("id=");
					if (pos >= 0) {
						parameter = parameter.substring(pos + "id=".length());
						parameter = parameter.trim();
						char closeId = parameter.charAt(0);
						if (closeId == '{') {
							closeId = '}';
						}
						pos = parameter.indexOf(closeId, 1);
						if (pos > 0) {
							parameter = parameter.substring(0, pos + 1);
						}
						parameter = parameter.replace("\"", "");
						hRefSuffix = hRefSuffix.replace("{id}", parameter);
						parameter = null;
					}
				}

				ret.href = sName + hRefSuffix;
			}
		} else if (resourceType == RESOURCE_TYPE.enqlist) {
			ret.href = sName + "()";
		} else if (resourceType == RESOURCE_TYPE.enquiry) {
			ret.href = sName + "()";
		} else if (resourceType == RESOURCE_TYPE.composite) {
			ret.href = sName;
		}
		
		if (parameter != null && parameter.length() > 0) {
			ret.href += "?" + parameter;
		}
		
		return ret;
	}

	private String getSuffix(String sName) {
		if (sName.startsWith("T24.")) {
			sName = sName.substring(4);
		}
		String suffix = null;
		int pos = sName.indexOf(".");
		if (pos > 0) {
			String sPart = sName.substring(0, pos);
			pos = sName.indexOf(sPart, pos);
			if (pos > 0) {
				suffix = sName.substring(pos + sPart.length());
			}
		}
		return suffix;
	}

	class RelAndPath {
		String href = null;
		String link = null;
	}

	class PropertyName {
		final String Name;
		final HashMap<String, String> Descriptions = new HashMap<String, String>();
		final ArrayList<PropertyItem> Items = new ArrayList<PropertyItem>();

		PropertyName(String name) {
			this.Name = name;
		}

		void AddDescription(String language, String description) {
			Descriptions.put(language, description);
		}

		void addItem(PropertyItem item) {
			Items.add(item);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			boolean bFirst = true;
			for (PropertyItem item : Items) {
				if (!bFirst) {
					sb.append("|");
				}
				bFirst = false;
				sb.append(item.toString());
			}
			return sb.toString();
		}
	}

	class PropertyItem {
		final String Type;
		final String Name;
		final HashMap<String, String> Descriptions = new HashMap<String, String>();
		String Rel = null;
		String Ref = "";

		PropertyItem(String name, String type) {
			this.Type = type;
			this.Name = name;
			Ref = name; // default
		}

		void AddDescription(String language, String description) {
			Descriptions.put(language, description);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder(this.Type).append(":").append(Ref);
			if (Rel != null) {
				sb.append(";").append(Rel);
			}
			if (Descriptions.size() > 0) {
				sb.append(";");
				boolean bFirst = true;
				for (String language : Descriptions.keySet()) {
					if (!bFirst) {
						sb.append("^");
					}
					bFirst = false;
					String desc = Descriptions.get(language);
					sb.append(language).append("=").append(desc);
				}
			}
			return sb.toString();
		}
	}

	private IProject getProject(String projectName) {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING)
			return null;
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		if (ws == null)
			return null;
		return ws.getRoot().getProject(projectName);
	}

	protected String getProjectLocation(String projectName) throws IllegalStateException {
		IProject project = getProject(projectName);
		if (project == null)
			throw new IllegalStateException(
					"getProjectLocation() needs to be @Override for the Menu2StreamMapper to work in standalone mode"); // mvorburger
		return project.getLocation().toOSString();
	}

}
