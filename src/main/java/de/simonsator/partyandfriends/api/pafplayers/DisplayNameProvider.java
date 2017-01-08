package de.simonsator.partyandfriends.api.pafplayers;

/**
 * @author simonbrungs
 * @version 1.0.0 01.01.17
 */
public interface DisplayNameProvider {
	String getDisplayName(PAFPlayer pPlayer);

	String getDisplayName(OnlinePAFPlayer pPlayer);
}
