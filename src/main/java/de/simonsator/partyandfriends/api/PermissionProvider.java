package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

/**
 * @author Simonsator
 * @version 1.0.0 on 02.09.16.
 */
public abstract class PermissionProvider {
	private static PermissionProvider instance;

	public PermissionProvider() {
		instance = this;
	}

	public static PermissionProvider getInstance() {
		return instance;
	}

	public abstract boolean hasPermission(PAFPlayer pPlayer, String pPermission);

}
