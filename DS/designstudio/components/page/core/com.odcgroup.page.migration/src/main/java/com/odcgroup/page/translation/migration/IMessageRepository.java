package com.odcgroup.page.translation.migration;

import java.util.List;

import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * @author atr
 */
public interface IMessageRepository {

	/**
	 * @param messageKey
	 *            the message key
	 * @return <code>true</code> if the key can be processed by this repository,
	 *         <code>false</code> otherwise.
	 */
	boolean acceptKey(String messageKey);

	/**
	 * Migrate the translation
	 * 
	 * @param IOfsProject
	 *            ofsProject, URI modelURI the translation to be migrated.
	 * @return <code>true</code> in the migration was successful,
	 *         <code>false</code> otherwise.
	 */
	boolean migrateTranslation(TranslationVO translation);

	/**
	 * Notifies this repository it can release its internal resources and save
	 * all models modified by the migration process. This is also the last
	 * chance for the repository to returns a list of <code>TranslationVO</code>
	 * that cannot be migrated. Indeed, this can happen for some translations
	 * key declared only in models with no definitions in any translations
	 * files.
	 * 
	 * @return a list of additional not processed translations declared in the
	 *         models. For these <code>TranslationVO</code>, both the language
	 *         and the message cannot be determined. Only the <code>key</code>
	 *         and the <code>errorStatus</code> are set.
	 * 
	 * @throws MigrationException
	 */
	List<TranslationVO> release() throws TranslationMigrationException;

}
