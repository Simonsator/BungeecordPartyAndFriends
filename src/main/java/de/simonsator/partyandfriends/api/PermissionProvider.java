package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

/**
 * This class provides information about if a player has a permission or not. It is also thought to work as
 * communication between the permission plugin and this plugin for more things then just the permissions.
 * Like in a custom version for a special permission plugin as provider for the display name.
 *
 * @author Simonsator
 * @version 1.0.0 on 02.09.16.
 */
public abstract class PermissionProvider {
	private static PermissionProvider instance;

	/**
	 * Sets the new instance of this class to the instance which is returned if getInstance() is called.
	 */
	public PermissionProvider() {
		instance = this;
	}

	/**
	 * @return Returns the instance of the PermissionProvider. There can always only be one PermissionProvider
	 */
	public static PermissionProvider getInstance() {
		return instance;
	}

	/**
	 * @param pPlayer     The player for whom the permission is asked
	 * @param pPermission The permission which is asked for
	 * @return Returns true if the given player has the given permission and returns false if he has not.
	 */
	public abstract boolean hasPermission(PAFPlayer pPlayer, String pPermission);

}
