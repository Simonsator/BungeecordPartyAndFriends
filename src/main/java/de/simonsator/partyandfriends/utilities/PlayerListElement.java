package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.friends.settings.OfflineSetting;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Simonsator
 * @version 1.0.0 11.04.17
 */
public class PlayerListElement implements Comparable<PlayerListElement> {
	private final boolean IS_ONLINE;
	private final Long LAST_ONLINE;
	private final ServerInfo SERVER;
	private final PAFPlayer PLAYER;
	private final int SORT_TYPE;
	private final OnlinePAFPlayer CALLER;
	private String displayName = null;
	private String name;
	private UUID uuid;

	public PlayerListElement(PAFPlayer pPlayer) {
		this(pPlayer, 0, null);
	}

	private PlayerListElement(PAFPlayer pPlayer, int pSortingType, OnlinePAFPlayer pCaller) {
		PLAYER = pPlayer;
		boolean isOnline = pPlayer.isOnline() && (!Main.getInstance().getGeneralConfig().getBoolean(
				"Commands.Friends.SubCommands.Settings.Settings.Offline.Enabled") ||
				pPlayer.getSettingsWorth(OfflineSetting.SETTINGS_ID) == OfflineSetting.FRIENDS_CAN_SEE_PLAYER_IS_ONLINE_STATE);
		IS_ONLINE = isOnline;
		if (!isOnline) {
			LAST_ONLINE = pPlayer.getLastOnline();
			SERVER = null;
		} else {
			LAST_ONLINE = null;
			SERVER = ((OnlinePAFPlayer) pPlayer).getServer();
		}
		CALLER = pCaller;
		if (pSortingType == 2 && pCaller == null)
			SORT_TYPE = 0;
		else
			SORT_TYPE = pSortingType;
	}

	/**
	 * @param pCaller   The person for whom this list should be created
	 * @param pSortType The sorting type. 0 is by last online, 1 is alphabetically, 2 is reverse alphabetic and 3 is
	 *                  by friendship duration
	 * @return A list of PlayerListElements
	 */
	public static List<PlayerListElement> getFriendsAsPlayerListElement(OnlinePAFPlayer pCaller, int pSortType) {
		List<PAFPlayer> friends = pCaller.getFriends();
		List<PlayerListElement> playerListElements = new ArrayList<>(friends.size());
		for (PAFPlayer player : friends)
			playerListElements.add(new PlayerListElement(player, pSortType, pCaller));
		return playerListElements;
	}

	@Override
	public int compareTo(PlayerListElement pCompare) {
		int sortType = 0;
		if (pCompare.SORT_TYPE == SORT_TYPE)
			sortType = SORT_TYPE;
		switch (sortType) {
			case 1:
				return -pCompare.getName().toLowerCase().compareTo(getName().toLowerCase());
			case 2:
				return pCompare.getName().toLowerCase().compareTo(getName().toLowerCase());
			case 3:
				return 0;
			default:
				return defaultCompareTo(pCompare);
		}
	}

	private int defaultCompareTo(PlayerListElement pCompare) {
		if (pCompare.isOnline() && this.isOnline())
			return this.getName().compareTo(pCompare.getName());
		if (pCompare.isOnline())
			return 1;
		if (this.isOnline())
			return -1;
		switch (LAST_ONLINE.compareTo(pCompare.LAST_ONLINE)) {
			case 1:
				return -1;
			case -1:
				return 1;
			case 0:
			default:
				return 0;
		}
	}

	public String getDisplayName() {
		if (displayName == null)
			return displayName = PLAYER.getDisplayName();
		return displayName;
	}

	public String getName() {
		if (name == null)
			return name = PLAYER.getName();
		return name;
	}

	public UUID getUniqueId() {
		if (uuid == null)
			return uuid = PLAYER.getUniqueId();
		return uuid;
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

	public String getServerDisplayName() {
		return ServerDisplayNameCollection.getInstance().getServerDisplayName(getServer());
	}

	public PAFPlayer getPlayer() {
		return PLAYER;
	}
}