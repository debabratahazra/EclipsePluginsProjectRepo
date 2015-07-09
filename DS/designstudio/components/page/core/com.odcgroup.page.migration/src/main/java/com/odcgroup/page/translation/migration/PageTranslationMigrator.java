package com.odcgroup.page.translation.migration;

import java.util.List;

import org.slf4j.Logger;

import com.odcgroup.page.translation.migration.internal.MessageRepository;
import com.odcgroup.translation.core.migration.ITranslationModelMigrator;
import com.odcgroup.translation.core.migration.TranslationMigrationException;
import com.odcgroup.translation.core.migration.TranslationVO;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 */
public class PageTranslationMigrator implements ITranslationModelMigrator {

	/**/
	private IMessageRepository repository;
	
	/**
	 * @return MessageRepository
	 */
	protected final IMessageRepository getMessageRepository() {
		return repository;
	}
	
	/**
	 * Subclasses may override this method
	 * @param logger
	 * @param ofsProject
	 * @return MessageRepository
	 */
	protected IMessageRepository createMessageRepository(Logger logger, IOfsProject ofsProject) {
		return new MessageRepository(logger, ofsProject);
	}

	@Override
	public void startMigration(Logger logger, IOfsProject ofsProject) {
		if (null == logger || null == ofsProject) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		repository = createMessageRepository(logger, ofsProject);
	}
	

	@Override
	public boolean isKeyAccepted(String key) {
		return getMessageRepository().acceptKey(key);
	}

	@Override
	public boolean process(TranslationVO translation) {
		return getMessageRepository().migrateTranslation(translation);
	}

	@Override
	public List<TranslationVO> endMigration() throws TranslationMigrationException {
		return getMessageRepository().release();
	}

	/**
	 * Constructor
	 */
	public PageTranslationMigrator() {
		super();
	}

}
