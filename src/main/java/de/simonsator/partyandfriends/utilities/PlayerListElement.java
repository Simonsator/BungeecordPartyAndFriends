package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * @author Simonsator
 * @version 1.0.0 11.04.17
 */
public class PlayerListElement implements Comparable<PlayerListElement> {
	private final boolean IS_ONLINE;
	private final String DISPLAY_NAME;
	private final Long LAST_ONLINE;
	private final ServerInfo SERVER;

	public PlayerListElement(PAFPlayer pPlayer) {
		boolean isOnline = pPlayer.isOnline() && pPlayer.getSettingsWorth(3) == 0;
		IS_ONLINE = isOnline;
		DISPLAY_NAME = pPlayer.getDisplayName();
		if (!isOnline) {
			LAST_ONLINE = pPlayer.getLastOnline();
			SERVER = null;
		} else {
			LAST_ONLINE = null;
			SERVER = ((OnlinePAFPlayer) pPlayer).getServer();
		}
	}

	@Override
	public int compareTo(PlayerListElement pCompare) {
		if (pCompare.isOnline() && this.isOnline())
			return this.getDisplayName().compareTo(pCompare.getDisplayName());
		if (pCompare.isOnline())
			return 1;
		if (this.isOnline())
			return -1;
		switch (LAST_ONLINE.compareTo(pCompare.LAST_ONLINE)) {
			case 0:
				return 0;
			case 1:
				return -1;
			case -1:
				return 1;
		}
		return 0;
	}

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	public boolean isOnline() {
		return IS_ONLINE;
	}

	public Long getLastOnline() {
		return LAST_ONLINE;
	}

	public ServerInfo getServer() {
		return SERVER;
	}
}