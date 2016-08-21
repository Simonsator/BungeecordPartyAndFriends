package de.simonsator.partyandfriends.api.pafplayers;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public abstract class PAFPlayerManager {
	private static PAFPlayerManager instance;

	public PAFPlayerManager() {
		instance = this;
	}

	public static PAFPlayerManager getInstance() {
		return instance;
	}

	public abstract PAFPlayer getPlayer(String pPlayer);

	public abstract OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer);

	public abstract PAFPlayer getPlayer(UUID pPlayer);

}
