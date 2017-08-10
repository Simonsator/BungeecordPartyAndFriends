package de.simonsator.partyandfriends.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	public Object get(String pPrefix, String pIdentifier) {
		Object entry = configuration.get(pIdentifier);
		if (entry instanceof List) {
			List<String> messageList = (List<String>) entry;
			ArrayList<String> messages = new ArrayList<>(messageList.size());
			for (int i = 0; i < messageList.size(); i++)
				messages.add(pPrefix + messageList.get(i));
			return messages;
		}
		return pPrefix + entry;
	}
}
