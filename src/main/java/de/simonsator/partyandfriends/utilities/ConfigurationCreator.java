package de.simonsator.partyandfriends.utilities;

import com.google.common.base.Charsets;
import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.PAFPluginBase;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Simonsator
 * @version 1.0.0 on 22.07.16.
 */
public abstract class ConfigurationCreator {
	protected final File FILE;
	private final Plugin PLUGIN;
	private final boolean SUPPORT_HEX_COLORS;
	private final Pattern HEX_PATTERN = Pattern.compile("&#" + "([A-Fa-f0-9]{6})");
	protected Configuration configuration = new Configuration();
	private boolean fileWasChanged = false;

	@Deprecated
	protected ConfigurationCreator(File file) {
		this(file, null);
	}

	protected ConfigurationCreator(File file, PAFPluginBase pPlugin) {
		this(file, (Plugin) pPlugin);
	}

	protected ConfigurationCreator(File file, PAFPluginBase pPlugin, boolean supportHexColors) {
		this(file, (Plugin) pPlugin, supportHexColors);
	}

	@Deprecated
	protected ConfigurationCreator(File file, Plugin pPlugin) {
		this(file, pPlugin, true);
	}

	private ConfigurationCreator(File file, Plugin pPlugin, boolean supportHexColors) {
		this.FILE = file;
		PLUGIN = pPlugin;
		SUPPORT_HEX_COLORS = supportHexColors;
	}

	protected ConfigurationCreator(File file, PAFExtension pPlugin) {
		this(file, (Plugin) pPlugin);
	}

	private void createParentFolder() {
		File parent = FILE.getParentFile();
		if (!parent.exists())
			parent.mkdir();
	}

	protected void process() {
		process(getCreatedConfiguration());
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
		if (configuration.get(pKey) == null) {
			configuration.set(pKey, pText);
			fileWasChanged = true;
		}
	}

	protected void overwriteKeyTemp(String pKey, Object pText) {
		configuration.set(pKey, pText);
	}

	protected void set(String pKey, String... entries) {
		set(pKey, new ArrayList<>(Arrays.asList(entries)));
	}

	protected void saveFile() throws IOException {
		if (fileWasChanged)
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
		if (SUPPORT_HEX_COLORS)
			return fixHexColors(pMessage);
		return fixColors(pMessage);
	}

	private String fixHexColors(String pMessage) {
		Matcher matcher = HEX_PATTERN.matcher(pMessage);
		StringBuffer buffer = new StringBuffer(pMessage.length() + 4 * 8);
		while (matcher.find()) {
			String group = matcher.group(1);
			String COLOR_CHAR = "§";
			matcher.appendReplacement(buffer, COLOR_CHAR + "x"
					+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
					+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
					+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
			);
		}
		return matcher.appendTail(buffer).toString();
	}

	/**
	 * @param pInput The text message
	 * @return Returns a string which is correctly read by client software without the use of TextComponent.fromLegacyText
	 */
	private String fixColors(String pInput) {
		String[] split = pInput.split(" ");
		StringBuilder composite = new StringBuilder();
		String colorCode = "";
		String formatCode = "";
		Pattern formatPattern = Pattern.compile("(?i)§[K-OR]");
		for (String input : split) {
			if (!input.startsWith("§"))
				input = colorCode + formatCode + input;
			int index;
			String inputClone = input;
			while ((index = inputClone.indexOf('§')) != -1) {
				if (inputClone.length() > index) {
					char colorFormatCharacter = inputClone.charAt(index + 1);
					if (colorFormatCharacter == 'r') {
						colorCode = "";
						formatCode = "";
					} else {
						String temp = "§" + colorFormatCharacter;
						if (formatPattern.matcher(temp).matches()) {
							formatCode = temp;
						} else {
							colorCode = temp;
						}
					}
				}
				inputClone = inputClone.substring(index + 1);
			}
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
		return copyFromJar(FILE.getName());
	}

	protected boolean copyFromJar(String pFileNameInJar) throws IOException {
		if (PLUGIN == null)
			throw new UnsupportedOperationException("Deprecated constructor was used to initialise the Object.");
		if (FILE.exists())
			return false;
		createParentFolder();
		InputStream in = PLUGIN.getResourceAsStream(pFileNameInJar);
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

	public String getString(String pIdentifier) {
		return configuration.getString(pIdentifier);
	}

	public int getInt(String pIdentifier) {
		return configuration.getInt(pIdentifier);
	}

	public long getLong(String pIdentifier) {
		return configuration.getLong(pIdentifier);
	}

	public boolean getBoolean(String pIdentifier) {
		return configuration.getBoolean(pIdentifier);
	}

	public List<String> getStringList(String pIdentifier) {
		return configuration.getStringList(pIdentifier);
	}

	public Object get(String pIdentifier) {
		return configuration.get(pIdentifier);
	}

	public Collection<String> getSectionKeys(String pIdentifier) {
		return getCreatedConfiguration().getSection(pIdentifier).getKeys();
	}
}
