package de.simonsator.partyandfriends.api.adapter;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.PAFPluginBase;
import de.simonsator.partyandfriends.api.TopCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class BukkitBungeeAdapter {
	private static BukkitBungeeAdapter instance;
	private final Plugin PAF_EXTENSION;
	private boolean forceUuidSupport = false;

	public BukkitBungeeAdapter(PAFExtension pPlugin) {
		this((PAFPluginBase) pPlugin);
	}

	public BukkitBungeeAdapter(PAFPluginBase pPlugin) {
		PAF_EXTENSION = pPlugin;
		if (instance == null)
			instance = this;
	}

	public static BukkitBungeeAdapter getInstance() {
		return instance;
	}

	public void registerListener(Object pListener, PAFPluginBase pPlugin) {
		ProxyServer.getInstance().getPluginManager().registerListener(pPlugin, (Listener) pListener);
	}

	public void callEvent(Object pEvent) {
		ProxyServer.getInstance().getPluginManager().callEvent((Event) pEvent);
	}

	public void registerCommand(Object pCommand, PAFPluginBase pPlugin) {
		ProxyServer.getInstance().getPluginManager().registerCommand(pPlugin, (Command) pCommand);
	}

	public void registerTopCommand(TopCommand<?> pCommand, PAFPluginBase pPlugin) {
		ProxyServer.getInstance().getPluginManager().registerCommand(pPlugin, pCommand);
	}

	public ServerSoftware getServerSoftware() {
		return ServerSoftware.BUNGEECORD;
	}

	public void runAsync(PAFPluginBase pPlugin, Runnable pRunnable) {
		ProxyServer.getInstance().getScheduler().runAsync(pPlugin, pRunnable);
	}

	public void schedule(PAFPluginBase instance, Runnable pRunnable, long timeInSeconds) {
		ProxyServer.getInstance().getScheduler().schedule(instance, pRunnable, timeInSeconds, TimeUnit.SECONDS);
	}

	public boolean isOnlineMode() {
		return ProxyServer.getInstance().getConfig().isOnlineMode() || forceUuidSupport;
	}

	public void setForceUuidSupport(boolean forceUuidSupport) {
		this.forceUuidSupport = forceUuidSupport;
	}
}
