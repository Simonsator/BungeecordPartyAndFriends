package de.simonsator.partyandfriends.utilities;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Simonsator
 * @version 1.0.0 on 22.07.16.
 */
public abstract class ConfigurationCreator {
	protected Configuration configuration = new Configuration();
	protected final File FILE;

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

}
