package de.simonsator.partyandfriends.api.adapter;

import de.simonsator.partyandfriends.api.PAFExtension;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;

public class BukkitBungeeAdapter {
	private static BukkitBungeeAdapter instance;
	private final Plugin PAF_EXTENSION;

	public BukkitBungeeAdapter(PAFExtension pPlugin) {
		PAF_EXTENSION = pPlugin;
		instance = this;
	}

	public BukkitBungeeAdapter(Plugin pPlugin) {
		PAF_EXTENSION = pPlugin;
		instance = this;
	}

	public static BukkitBungeeAdapter getInstance() {
		return instance;
	}

	public void callEvent(Object pEvent) {
		ProxyServer.getInstance().getPluginManager().callEvent((Event) pEvent);
	}

	public ServerSoftware getServerSoftware() {
		return ServerSoftware.BUNGEECORD;
	}
}
