package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.PermissionProvider;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

/**
 * @author Simonsator
 * @version 02.09.16.
 */
public class StandardPermissionProvider extends PermissionProvider {
	public StandardPermissionProvider() {
		super();
	}

	@Override
	public boolean hasPermission(PAFPlayer pPlayer, String pPermission) {
		return !(pPlayer != null && !pPlayer.isOnline()) && ((OnlinePAFPlayer) pPlayer).getPlayer().hasPermission(pPermission);
	}
}
