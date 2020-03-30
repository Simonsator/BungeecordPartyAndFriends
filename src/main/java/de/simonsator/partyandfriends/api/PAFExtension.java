package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.main.Main;

import java.io.File;

/**
 * @author Simonsator
 * @version 1.0.0 12.02.17
 */
public abstract class PAFExtension extends PAFPluginBase {

	public void reload() {
		onDisable();
		onEnable();
	}

	public File getConfigFolder() {
		if (Main.getInstance().getGeneralConfig().getBoolean("Extensions.UseExtensionFolderAsConfigFolder")) {
			File configFolder = new File(Main.getInstance().getDataFolder(), "extensions/");
			if (!configFolder.exists())
				configFolder.mkdir();
			configFolder = new File(configFolder, getDescription().getName());
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

	protected void registerAsExtension() {
		Main.getInstance().registerExtension(this);
	}

	protected void unregisterExtension() {
		Main.getInstance().unregisterExtension(this);
	}

}
