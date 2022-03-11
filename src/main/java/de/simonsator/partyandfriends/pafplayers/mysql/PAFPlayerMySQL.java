package de.simonsator.partyandfriends.pafplayers.mysql;

import de.simonsator.partyandfriends.api.PermissionProvider;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.PAFAccountDeleteEvent;
import de.simonsator.partyandfriends.api.events.friends.FriendRemovedEvent;
import de.simonsator.partyandfriends.api.events.friends.FriendRequestAcceptedEvent;
import de.simonsator.partyandfriends.api.events.friends.FriendRequestDeniedEvent;
import de.simonsator.partyandfriends.api.pafplayers.IDBasedPAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.communication.sql.data.PlayerDataSet;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;

import java.sql.Timestamp;
import java.util.*;

public class PAFPlayerMySQL extends PAFPlayerClass implements IDBasedPAFPlayer {
	private static boolean multiCoreEnhancement = false;
	private final Map<Integer, Integer> SETTINGS;
	protected int id;
	private String name;
	private UUID uuid;
	private Long lastOnline;

	public PAFPlayerMySQL(int pID) {
		id = pID;
		SETTINGS = new HashMap<>();
	}

	public PAFPlayerMySQL(PlayerDataSet pData) {
		id = pData.ID;
		name = pData.NAME;
		uuid = pData.UUID;
		lastOnline = pData.LAST_ONLINE;
		SETTINGS = pData.SETTINGS;
	}

	public static void setMultiCoreEnhancement(boolean pUseMultiCoreEnhancment) {
		multiCoreEnhancement = pUseMultiCoreEnhancment;
	}

	@Override
	public String getName() {
		if (name != null)
			return name;
		return name = PAFPlayerManagerMySQL.getConnection().getName(id);
	}

	public int getPlayerID() {
		return id;
	}

	@Override
	public List<PAFPlayer> getFriends() {
		return idListToPAFPlayerList(PAFPlayerManagerMySQL.getConnection().getFriends(id));
	}

	private List<PAFPlayer> playerDataToPAFList(List<PlayerDataSet> playerDataSets) {
		List<PAFPlayer> players = new ArrayList<>();
		for (PlayerDataSet playerDataSet : playerDataSets)
			players.add(PAFPlayerManager.getInstance().getPlayer(playerDataSet));
		return players;
	}

	@Override
	public UUID getUniqueId() {
		if (uuid != null)
			return uuid;
		return uuid = PAFPlayerManagerMySQL.getConnection().getUUID(id);
	}

	@Override
	public String toString() {
		return "{Name:\"" + getName() + "\", DisplayName:\"" + getDisplayName() + "\"}";
	}

	@Override
	public boolean doesExist() {
		return id > 0;
	}

	@Override
	public int getSettingsWorth(int pSettingsID) {
		Integer worth = SETTINGS.get(pSettingsID);
		if (worth != null)
			return worth;
		return PAFPlayerManagerMySQL.getConnection().getSettingsWorth(id, pSettingsID);
	}

	@Override
	public List<PAFPlayer> getRequests() {
		return playerDataToPAFList(PAFPlayerManagerMySQL.getConnection().getRequestsPlayerData(id));
	}

	@Override
	public int getFriendRequestCount() {
		return PAFPlayerManagerMySQL.getConnection().getFriendCount(id);
	}

	@Override
	public boolean hasRequestFrom(PAFPlayer pPlayer) {
		return PAFPlayerManagerMySQL.getConnection().hasRequestFrom(id, ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	@Override
	public boolean hasPermission(String pPermission) {
		return pPermission == null || pPermission.isEmpty() || PermissionProvider.getInstance().hasPermission(this, pPermission);
	}

	@Override
	public void denyRequest(PAFPlayer pPlayer) {
		if (multiCoreEnhancement)
			BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> denyRequestNoMultiCoreEnhancement(pPlayer));
		else
			denyRequestNoMultiCoreEnhancement(pPlayer);
		BukkitBungeeAdapter.getInstance().callEvent(new FriendRequestDeniedEvent(this, pPlayer));
	}

	public void denyRequestNoMultiCoreEnhancement(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().denyRequest(id, ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	@Override
	public boolean isAFriendOf(PAFPlayer pPlayer) {
		return PAFPlayerManagerMySQL.getConnection().isAFriendOf(id, ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	private List<PAFPlayer> idListToPAFPlayerList(List<Integer> pList) {
		List<PAFPlayer> list = new ArrayList<>();
		for (int playerID : pList)
			list.add(((PAFPlayerManagerMySQL) PAFPlayerManager.getInstance()).getPlayer(playerID));
		return list;
	}

	@Override
	public PAFPlayer getLastPlayerWroteTo() {
		return ((PAFPlayerManagerMySQL) PAFPlayerManager.getInstance()).getPlayer(PAFPlayerManagerMySQL.getConnection().getLastPlayerWroteTo(id));
	}

	@Override
	public void sendFriendRequest(PAFPlayer pPlayer) {
		if (multiCoreEnhancement)
			BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> sendFriendRequestNoMultiCoreEnhancement(pPlayer));
		else
			sendFriendRequestNoMultiCoreEnhancement(pPlayer);
	}

	public void sendFriendRequestNoMultiCoreEnhancement(PAFPlayer pSender) {
		PAFPlayerManagerMySQL.getConnection().sendFriendRequest(((PAFPlayerMySQL) pSender).getPlayerID(), id);
	}

	@Override
	public void addFriend(PAFPlayer pPlayer) {
		BukkitBungeeAdapter.getInstance().callEvent(new FriendRequestAcceptedEvent(this, pPlayer));
		if (multiCoreEnhancement)
			BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> addFriendNoMultiCoreEnhancement(pPlayer));
		else
			addFriendNoMultiCoreEnhancement(pPlayer);
	}

	public void addFriendNoMultiCoreEnhancement(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().addFriend(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), id);
	}

	@Override
	public PAFPlayer getPAFPlayer() {
		return this;
	}

	@Override
	public void removeFriend(PAFPlayer pPlayer) {
		BukkitBungeeAdapter.getInstance().callEvent(new FriendRemovedEvent(this, pPlayer));
		if (multiCoreEnhancement)
			BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> removeFriendNoMultiCoreEnhancement(pPlayer));
		else
			removeFriendNoMultiCoreEnhancement(pPlayer);
	}

	public void removeFriendNoMultiCoreEnhancement(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().deleteFriend(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), id);
	}

	@Override
	public void setSetting(int pSettingsID, int pNewWorth) {
		SETTINGS.put(pSettingsID, pNewWorth);
		PAFPlayerManagerMySQL.getConnection().setSetting(id, pSettingsID, pNewWorth);
	}

	@Override
	public void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo) {
		PAFPlayerManagerMySQL.getConnection().setLastPlayerWroteTo(id, ((PAFPlayerMySQL) pLastWroteTo.getPAFPlayer())
				.getPlayerID());
	}

	@Override
	public long getLastOnline() {
		if (lastOnline != null)
			return lastOnline;
		Timestamp time = PAFPlayerManagerMySQL.getConnection().getLastOnline(id);
		if (time != null)
			return time.getTime();
		return 0;
	}

	@Override
	public boolean deleteAccount() {
		PAFAccountDeleteEvent event = new PAFAccountDeleteEvent(this);
		BukkitBungeeAdapter.getInstance().callEvent(event);
		if (!event.isCancelled()) {
			PAFPlayerManagerMySQL.getConnection().deletePlayerEntry(id);
			return true;
		}
		return false;
	}

	@Override
	public void updateLastOnline() {
		PAFPlayerManagerMySQL.getConnection().updateLastOnline(getPlayerID());
	}
}
