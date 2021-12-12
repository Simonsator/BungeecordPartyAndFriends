package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.PAFPluginBase;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 on 02.08.16.
 */
public abstract class LanguageConfiguration extends ConfigurationCreator {
	public final Language LANGUAGE;

	@Deprecated
	protected LanguageConfiguration(Language pLanguage, File pFile) {
		super(pFile);
		LANGUAGE = pLanguage;
	}

	protected LanguageConfiguration(Language pLanguage, File pFile, PAFPluginBase pPlugin) {
		this(pLanguage, pFile, (Plugin) pPlugin);
	}

	protected LanguageConfiguration(Language pLanguage, File pFile, PAFPluginBase pPlugin, boolean supportHexColors) {
		super(pFile, pPlugin, supportHexColors);
		LANGUAGE = pLanguage;
	}

	@Deprecated
	protected LanguageConfiguration(Language pLanguage, File pFile, Plugin pPlugin) {
		super(pFile, pPlugin);
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
			for (String message : messageList)
				messages.add(pPrefix + message);
			return messages;
		}
		return pPrefix + entry;
	}
}
