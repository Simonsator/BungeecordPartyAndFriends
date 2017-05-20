package de.simonsator.partyandfriends.api.pafplayers;

/**
 * @author simonbrungs
 * @version 1.0.0 19.05.17
 */
public abstract class IDBasedPAFPlayerManager extends PAFPlayerManager {
	public abstract PAFPlayer getPlayer(int pPlayerID);
}
