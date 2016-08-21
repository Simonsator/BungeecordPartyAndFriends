package de.simonsator.partyandfriends.utilities;

import java.io.File;

/**
 * @author Simonsator
 * @version 1.0.0 on 02.08.16.
 */
public abstract class LanguageConfiguration extends ConfigurationCreator {
	public final Language LANGUAGE;

	protected LanguageConfiguration(Language pLanguage, File pFile) {
		super(pFile);
		LANGUAGE = pLanguage;
	}

	public String getString(String pIdentifier) {
		return configuration.getString(pIdentifier);
	}

}
