package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.pafplayers.PAFPlayer;

public class OfflineMessage {
	public final String MESSAGE;
	public final PAFPlayer SENDER;

	public OfflineMessage(String MESSAGE, PAFPlayer SENDER) {
		this.MESSAGE = MESSAGE;
		this.SENDER = SENDER;
	}
}
