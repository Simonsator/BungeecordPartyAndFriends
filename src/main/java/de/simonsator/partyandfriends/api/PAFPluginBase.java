package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class PAFPluginBase extends Plugin {
	private final BukkitBungeeAdapter adapter = new BukkitBungeeAdapter(this);

	public void registerCommand(Object pCommand) {
		BukkitBungeeAdapter.getInstance().registerCommand(pCommand, this);
	}

	public void registerTopCommand(TopCommand<?> pCommand) {
		BukkitBungeeAdapter.getInstance().registerTopCommand(pCommand, this);
	}

	public BukkitBungeeAdapter getAdapter() {
		return adapter;
	}

}
