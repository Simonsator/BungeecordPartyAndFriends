package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.DisplayNameProvider;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author simonbrungs
 * @version 1.0.0 01.01.17
 */
public class StandardDisplayNameProvider implements DisplayNameProvider {
	@Override
	public String getDisplayName(PAFPlayer pPlayer) {
		return pPlayer.getName();
	}

	@Override
	public String getDisplayName(OnlinePAFPlayer pPlayer) {
		ProxiedPlayer player = pPlayer.getPlayer();
		if (player != null)
			return player.getDisplayName();
		return pPlayer.getName();
	}
}
