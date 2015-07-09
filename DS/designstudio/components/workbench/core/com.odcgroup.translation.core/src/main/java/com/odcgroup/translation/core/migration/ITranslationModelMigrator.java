package com.odcgroup.translation.core.migration;

import java.util.List;

import org.slf4j.Logger;

import com.odcgroup.workbench.core.IOfsProject;

/**
 * Migrator interface
 * 
 * @author yan
 */
public interface ITranslationModelMigrator {

	/**
	 * Starts the migration. This migration is logged in a separate file. Use
	 * this logger to report problem and processing progress
	 * 
	 * @param migrationLogger the logger
	 * @param ofsProject the OFS project
	 */
	public void startMigration(Logger migrationLogger, IOfsProject ofsProject);

	/**
	 * @param key the message key
	 * @return <code>true</code> if the key can be processed by this migrator,
	 *         <code>false</code> otherwise.
	 */
	public boolean isKeyAccepted(String key);

	/**
	 * Migrate the translation
	 * 
	 * @param IOfsProject ofsProject, URI modelURI
	 *            the translation to be migrated.
	 * @return <code>true</code> in the migration was successful,
	 *         <code>false</code> otherwise.
	 */
	public boolean process(TranslationVO translation);

	/**
	 * Notifies this migrator it can release its internal resource and save all
	 * models modified by process. This is the last chance for the migrator to returns 
	 * a list of <code>TranslationVO</code> that cannot be migrated. Indeed, this can 
	 * happen for some translations key declared only in models with no definitions in any 
	 * translations files. 
	 * 
	 * @return a list of additional not processed translations declared in the models. 
	 * For these <code>TranslationVO</code>, both the language and the message cannot 
	 * be determined. Only the <code>key</code> and the <code>errorStatus</code> are set. 
	 * 
	 * @throws TranslationMigrationException
	 */
	public List<TranslationVO> endMigration() throws TranslationMigrationException;

}
