package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

/**
 * @author Simonsator
 * @version 1.0.0 12.02.17
 */
public abstract class PAFExtension extends Plugin {
	public void reload() {
		onDisable();
		onEnable();
	}

	public File getConfigFolder() {
		if (Main.getInstance().getConfig().getBoolean("Extensions.UseExtensionFolderAsConfigFolder")) {
			File configFolder = new File(Main.getInstance().getDataFolder(), "extensions/" + getDescription().getName());
			if (!configFolder.exists())
				configFolder.mkdir();
			return configFolder;
		}
		return getDataFolder();
	}

	@Override
	public void onDisable() {
		getProxy().getPluginManager().unregisterListeners(this);
	}

	private void registerAsExtension() {
		Main.getInstance().registerExtension(this);
	}
}
