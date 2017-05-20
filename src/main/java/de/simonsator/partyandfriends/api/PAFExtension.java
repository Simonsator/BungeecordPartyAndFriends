package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

/**
 * @author Simonsator
 * @version 1.0.0 12.02.17
 */
public abstract class PAFExtension extends Plugin {
	public abstract void reload();

	public File getConfigFolder() {
		if (Main.getInstance().getConfig().getBoolean("Extensions.UseExtensionFolderAsConfigFolder"))
			return new File(Main.getInstance().getDataFolder(), "extensions/" + getDescription().getName());
		return getDataFolder();
	}
}
