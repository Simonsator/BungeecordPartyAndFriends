package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

/**
 * @author Simonsator
 * @version 1.0.0 07.05.17
 */
public interface TextReplacer {
	String onProecess(PAFPlayer pPlayer, String pMessage);
}
