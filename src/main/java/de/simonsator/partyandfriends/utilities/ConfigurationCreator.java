package de.simonsator.partyandfriends.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author Simonsator
 * @version 1.0.0 on 22.07.16.
 */
public abstract class ConfigurationCreator {
	protected final File FILE;
	protected Configuration configuration = new Configuration();

	protected ConfigurationCreator(File file) {
		this.FILE = file;
	}

	protected void readFile() throws IOException {
		File folder = FILE.getParentFile();
		if (!folder.exists())
			folder.mkdir();
		if (!FILE.exists())
			FILE.createNewFile();
		configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(FILE);
	}

	public abstract void reloadConfiguration() throws IOException;

	public Configuration getCreatedConfiguration() {
		return configuration;
	}

	protected void set(String pKey, Object pText) {
		if (configuration.get(pKey) == null)
			configuration.set(pKey, pText);
	}

	protected void set(String pKey, String... entries) {
		set(pKey, new ArrayList<>(Arrays.asList(entries)));
	}

	protected void saveFile() throws IOException {
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, FILE);
	}

	protected void process(Configuration pMessagesYML) {
		for (String key : pMessagesYML.getKeys()) {
			Object entry = pMessagesYML.get(key);
			if (entry instanceof LinkedHashMap | entry instanceof Configuration)
				process(pMessagesYML.getSection(key));
			else if (entry instanceof String) {
				String stringEntry = (String) entry;
				stringEntry = ChatColor.translateAlternateColorCodes('&', stringEntry);
				stringEntry = fixColors(stringEntry);
				pMessagesYML.set(key, ChatColor.translateAlternateColorCodes('&', stringEntry));
			}
		}
	}

	private String fixColors(String pInput) {
		String[] split = pInput.split(" ");
		StringBuilder composite = new StringBuilder("");
		String colorCode = "";
		for (String input : split) {
			if (!input.startsWith("§"))
				input = colorCode + input;
			int index = input.lastIndexOf('§');
			if (index != -1)
				if (input.length() > index)
					colorCode = "§" + input.charAt(index + 1);
			composite.append(' ').append(input);
		}
		String composited = composite.toString();
		if (composited.length() > 0)
			composited = composited.substring(1);
		if (pInput.endsWith(" "))
			composited += (' ');
		return composited;
	}

}
