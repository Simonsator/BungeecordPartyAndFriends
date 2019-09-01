package de.simonsator.partyandfriends.pafplayers.mysql;

import de.simonsator.partyandfriends.api.PermissionProvider;
import de.simonsator.partyandfriends.api.events.PAFAccountDeleteEvent;
import de.simonsator.partyandfriends.api.pafplayers.IDBasedPAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import net.md_5.bungee.api.ProxyServer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PAFPlayerMySQL extends PAFPlayerClass implements IDBasedPAFPlayer {
	private static boolean multiCoreEnhancement = false;
	protected int id;

	public PAFPlayerMySQL(int pID) {
		id = pID;
	}

	public static void setMultiCoreEnhancement(boolean pUseMultiCoreEnhancment) {
		multiCoreEnhancement = pUseMultiCoreEnhancment;
	}

	@Override
	public String getName() {
		return PAFPlayerManagerMySQL.getConnection().getName(id);
	}

	public int getPlayerID() {
		return id;
	}

	@Override
	public List<PAFPlayer> getFriends() {
		return idListToPAFPlayerList(PAFPlayerManagerMySQL.getConnection().getFriends(id));
	}

	@Override
	public UUID getUniqueId() {
		return PAFPlayerManagerMySQL.getConnection().getUUID(id);
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
		return PAFPlayerManagerMySQL.getConnection().getSettingsWorth(id, pSettingsID);
	}

	@Override
	public List<PAFPlayer> getRequests() {
		return idListToPAFPlayerList(PAFPlayerManagerMySQL.getConnection().getRequests(id));
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
			ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), () -> denyRequestNoMultiCoreEnhancement(pPlayer));
		else
			denyRequestNoMultiCoreEnhancement(pPlayer);
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
			ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), () -> sendFriendRequestNoMultiCoreEnhancement(pPlayer));
		else
			sendFriendRequestNoMultiCoreEnhancement(pPlayer);
	}

	public void sendFriendRequestNoMultiCoreEnhancement(PAFPlayer pSender) {
		PAFPlayerManagerMySQL.getConnection().sendFriendRequest(((PAFPlayerMySQL) pSender).getPlayerID(), id);
	}

	@Override
	public void addFriend(PAFPlayer pPlayer) {
		if (multiCoreEnhancement)
			ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), () -> addFriendNoMultiCoreEnhancement(pPlayer));
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
		if (multiCoreEnhancement)
			ProxyServer.getInstance().getScheduler().runAsync(Main.getInstance(), () -> removeFriendNoMultiCoreEnhancement(pPlayer));
		else
			removeFriendNoMultiCoreEnhancement(pPlayer);
	}

	public void removeFriendNoMultiCoreEnhancement(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().deleteFriend(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), id);
	}

	@Override
	public void setSetting(int pSettingsID, int pNewWorth) {
		PAFPlayerManagerMySQL.getConnection().setSetting(id, pSettingsID, pNewWorth);
	}

	@Override
	public void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo) {
		PAFPlayerManagerMySQL.getConnection().setLastPlayerWroteTo(id, ((PAFPlayerMySQL) pLastWroteTo.getPAFPlayer())
				.getPlayerID(), 0);
	}

	@Override
	public long getLastOnline() {
		Timestamp time = PAFPlayerManagerMySQL.getConnection().getLastOnline(id);
		if (time != null)
			return time.getTime();
		return 0;
	}

	@Override
	public boolean deleteAccount() {
		PAFAccountDeleteEvent event = new PAFAccountDeleteEvent(this);
		ProxyServer.getInstance().getPluginManager().callEvent(event);
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
