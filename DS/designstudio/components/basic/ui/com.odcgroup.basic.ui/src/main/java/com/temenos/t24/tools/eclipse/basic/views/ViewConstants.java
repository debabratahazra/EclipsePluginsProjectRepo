package com.temenos.t24.tools.eclipse.basic.views;

import com.temenos.t24.tools.eclipse.basic.views.calls.CallsView;
import com.temenos.t24.tools.eclipse.basic.views.inserts.InsertsView;
import com.temenos.t24.tools.eclipse.basic.views.labels.T24LabelsRegionsView;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.MacrosView;
import com.temenos.t24.tools.eclipse.basic.views.tasks.TasksView;
import com.temenos.t24.tools.eclipse.basic.views.todo.TodoView;
import com.temenos.t24.tools.eclipse.basic.views.variables.VariablesView;

public final class ViewConstants {

    /** Array with views IDs */
    public final static String[] T24_VIEW_IDS = { CallsView.VIEW_ID, InsertsView.VIEW_ID, T24LabelsRegionsView.VIEW_ID,
            TodoView.VIEW_ID, MacrosView.VIEW_ID, TasksView.VIEW_ID, VariablesView.VIEW_ID };

    public static enum T24_VIEW_ITEM_CATEGORY {
        CALL_ITEM("CALL_ITEM"), LABEL_ITEM("LABEL_ITEM"), INSERT_ITEM("INSERT_ITEM"), T24REGION_ITEM("T24REGION_ITEM"), LABEL_AND_T24REGION_ITEM(
                "LABEL_AND_T24REGION_ITEM"), TODO_ITEM("TODO_ITEM"), GOSUB_ITEM("GOSUB_ITEM"), MACRO_ITEM("MACRO_ITEM"), SERVER_DIRECTORY_ITEM(
                "SERVER_DIRECTORY_ITEM"), SERVER_FILE_ITEM("SERVER_FILE_ITEM"), FIRST_SERVER_ITEM("FIRST_SERVER_ITEM"), /* Used for one (first) Server View */
        SECOND_SERVER_ITEM("SECOND_SERVER_ITEM"), /* Used if the second Server View is opened */
        MACRO_SYSTEM_ITEM("MACRO_SYSTEM_ITEM"), MACRO_USER_ITEM("MACRO_USER_ITEM"), TEMPLATE_SYSTEM_ITEM("TEMPLATE_SYSTEM_ITEM"), TEMPLATE_USER_ITEM(
                "TEMPLATE_USER_ITEM"), ONLINE_SRC_ITEM("ONLINE_SRC_ITEM"), COMPILE_ERROR_ITEM("COMPILE_ERROR_ITEM"), COMPILE_WARNING_ITEM(
                "COMPILE_WARNING_ITEM"), COMPILE_STANDARD_ITEM("COMPILE_STANDARD_ITEM"), COMPILE_CODEREVIEW_ITEM(
                "COMPILE_CODEREVIEW_ITEM"), NULL_CATEGORY("NULL_CATEGORY"), WARNING_ITEM("WARNING_ITEM"), ERROR_ITEM("WARNING_ITEM"), VARIABLE_ITEM(
                "VARIABLE_ITEM");

        private String category;

        private T24_VIEW_ITEM_CATEGORY(String category) {
            this.category = category;
        }

        public String getCategory() {
            return this.category;
        }

        /**
         * Given a category string finds the corresponding enum. If not found
         * it'll return NULL_CATEGORY.
         */
        public static T24_VIEW_ITEM_CATEGORY find(String category) {
            for (T24_VIEW_ITEM_CATEGORY item : T24_VIEW_ITEM_CATEGORY.values()) {
                if (category.equals(item.getCategory())) {
                    return item;
                }
            }
            return NULL_CATEGORY;
        }
    };

    public static enum T24_POSITION_CATEGORY {
        CALLS_POSITION("POSITION_CATEGORY_CALLS"), INSERTS_POSITION("POSITION_CATEGORY_INSERTS"), LABELS_POSITION(
                "POSITION_CATEGORY_LABELS"), REGIONS_POSITION("POSITION_CATEGORY_REGIONS"), TODOS_POSITION(
                "POSITION_CATEGORY_TODOS"), GOSUBS_POSITION("POSITION_CATEGORY_GOSUBS"), VARIABLES_POSITION(
                "POSITION_CATEGORY_VARIABLES");

        private final String posCatString;

        private T24_POSITION_CATEGORY(String posCatString) {
            this.posCatString = posCatString;
        }

        public String getString() {
            return this.posCatString;
        }
    };

    public final static String DEFAULT_IMAGE_FILE = "/icons/default.gif";
    public final static String LABEL_IMAGE_FILE = "/icons/labelImage.gif";
    public final static String REGION_IMAGE_FILE = "/icons/t24RegionImage.gif";
    public final static String CALL_IMAGE_FILE = "/icons/callImage.gif";
    public final static String INSERT_IMAGE_FILE = "/icons/insertImage.gif";
    public final static String TODO_IMAGE_FILE = "/icons/todoImage.gif";
    public final static String DIRECTORY_IMAGE_FILE = "/icons/directoryImage.gif";
    public final static String FILE_IMAGE_FILE = "/icons/fileImage.gif";
    public final static String MACRO_SYSTEM_IMAGE_FILE = "/icons/macroImage.gif";
    public final static String MACRO_USER_IMAGE_FILE = "/icons/macroUserImage.gif";
    public final static String TEMPLATE_SYSTEM_IMAGE_FILE = "/icons/templateImage.gif";
    public final static String TEMPLATE_USER_IMAGE_FILE = "/icons/templateUserImage.gif";
    public final static String COMPILE_ERROR_IMAGE_FILE = "/icons/errorImage.gif";
    public final static String COMPILE_WARNING_IMAGE_FILE = "/icons/warningImage.gif";
    public final static String COMPILE_STANDARD_IMAGE_FILE = "/icons/infoImage.gif";
    public final static String COMPILE_CODEREVIEW_IMAGE_FILE = "/icons/infoImage.gif";
    public final static String ERROR_IMAGE_FILE = "/icons/errorImage.gif";
    public final static String WARNING_IMAGE_FILE = "/icons/warningImage.gif";
    public final static String VARIABLE_IMAGE_FILE = "/icons/variable.png";
    public final static String COULD_NOT_RETRIEVE_ITEMS = "COULD NOT RETRIEVE ITEMS";
    public final static String DEFAULT_REMOTE_SITE_IMAGE = "/icons/defaultSite.png";
}
