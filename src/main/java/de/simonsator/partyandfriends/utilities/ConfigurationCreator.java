package de.simonsator.partyandfriends.utilities;

import com.google.common.base.Charsets;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 on 22.07.16.
 */
public abstract class ConfigurationCreator {
	protected final File FILE;
	private final Plugin PLUGIN;
	protected Configuration configuration = new Configuration();

	@Deprecated
	protected ConfigurationCreator(File file) {
		this.FILE = file;
		PLUGIN = null;
	}

	protected ConfigurationCreator(File file, Plugin pPlugin) {
		this.FILE = file;
		PLUGIN = pPlugin;
	}

	private void createParentFolder() {
		File parent = FILE.getParentFile();
		if (!parent.exists())
			parent.mkdir();
	}

	protected void readFile() throws IOException {
		File folder = FILE.getParentFile();
		if (!folder.exists())
			folder.mkdir();
		if (!FILE.exists())
			FILE.createNewFile();
		try (InputStreamReader inputStream = new InputStreamReader(new FileInputStream(FILE), Charsets.UTF_8)) {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(inputStream);
		}
	}

	@Deprecated
	public void reloadConfiguration() throws IOException {
		throw new UnsupportedOperationException("This method was not implemented");
	}

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
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE), Charsets.UTF_8)) {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, writer);
		}
	}

	protected void process(Configuration pMessagesYML) {
		for (String key : pMessagesYML.getKeys()) {
			Object entry = pMessagesYML.get(key);
			if (entry instanceof LinkedHashMap | entry instanceof Configuration)
				process(pMessagesYML.getSection(key));
			else if (entry instanceof String) {
				pMessagesYML.set(key, process((String) entry));
			} else if (entry instanceof List) {
				List<String> messages = new ArrayList<>((List<String>) entry);
				for (int i = 0; i < messages.size(); i++)
					messages.set(i, process(messages.get(i)));
				pMessagesYML.set(key, messages);
			}
		}
	}

	private String process(String pMessage) {
		pMessage = ChatColor.translateAlternateColorCodes('&', pMessage);
		return fixColors(pMessage);
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

	protected boolean copyFromJar() throws IOException {
		if (PLUGIN == null)
			throw new UnsupportedOperationException("Deprecated constructor was used to initialise the Object.");
		if (FILE.exists())
			return false;
		createParentFolder();
		InputStream in = PLUGIN.getResourceAsStream(FILE.getName());
		OutputStream out = new FileOutputStream(FILE);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();
		in.close();
		return true;
	}

}
