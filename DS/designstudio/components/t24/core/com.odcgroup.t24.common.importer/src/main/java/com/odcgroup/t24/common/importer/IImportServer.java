package com.odcgroup.t24.common.importer;

import java.util.List;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.server.model.IDSServer;

/**
 * Facade sur le serveur externe d'importation de modèles
 * manage la connection, effectue l'importation concrete
 * @author atripod
 *
 * @param <T>
 */
public interface IImportServer<T> {
	
	/**
	 * @return la liste des models importables
	 */
	List<T> getRegisteredModels();

	void setServer(IDSServer server);

	void setDestinationFolder(IFolder folder);
	
	void setImportFolder(IFolder folder);
	
	void importModels(List<T> models, boolean saveXML /* TODO rename  this variable*/);

}
