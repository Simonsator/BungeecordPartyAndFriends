package de.simonsator.partyandfriends.pafplayers.manager;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public abstract class PAFPlayerManager {
	public abstract PAFPlayer getPlayer(String pPlayer);

	public abstract OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer);

	public abstract PAFPlayer getPlayer(UUID pPlayer);
}
