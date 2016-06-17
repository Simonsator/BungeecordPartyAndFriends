package de.simonsator.partyandfriends.pafplayers.mysql;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.utilities.OfflineMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.main.Main.getPlayerManager;

public class PAFPlayerMySQL extends PAFPlayerClass {
	final int ID;

	public PAFPlayerMySQL(int pID) {
		ID = pID;
	}

	@Override
	public String getName() {
		return Main.getInstance().getConnection().getName(ID);
	}

	private int getPlayerID() {
		return ID;
	}

	@Override
	public List<PAFPlayer> getFriends() {
		return idListToPAFPlayerList(getInstance().getConnection().getFriends(ID));
	}

	@Override
	public UUID getUniqueId() {
		return Main.getInstance().getConnection().getUUID(ID);
	}

	@Override
	public String toString() {
		return "{Name:\"" + getName() + "\", DispalyName:\"" + getDisplayName() + "\"}";
	}

	@Override
	public boolean doesExist() {
		return ID > 0;
	}

	@Override
	public int getSettingsWorth(int pSettingsID) {
		return getInstance().getConnection().getSettingsWorth(ID, pSettingsID);
	}

	@Override
	public List<PAFPlayer> getRequests() {
		return idListToPAFPlayerList(getInstance().getConnection().getRequests(ID));
	}

	@Override
	public boolean hasRequestFrom(PAFPlayer pPlayer) {
		return getInstance().getConnection().hasRequestFrom(ID, ((PAFPlayerMySQL) pPlayer).getPlayerID());
	}

	@Override
	public void denyRequest(PAFPlayer pPlayer) {
		getInstance().getConnection().denyRequest(ID, ((PAFPlayerMySQL) pPlayer).getPlayerID());
	}

	@Override
	public boolean isAFriendOf(PAFPlayer pPlayer) {
		return getInstance().getConnection().isAFriendOf(ID, ((PAFPlayerMySQL) pPlayer).getPlayerID());
	}

	private List<PAFPlayer> idListToPAFPlayerList(List<Integer> pList) {
		List<PAFPlayer> list = new ArrayList<>();
		for (int playerID : pList)
			list.add(((PAFPlayerManagerMySQL) getPlayerManager()).getPlayer(playerID));
		return list;
	}

	@Override
	public PAFPlayer getLastPlayerWroteTo() {
		return ((PAFPlayerManagerMySQL) getPlayerManager()).getPlayer(getInstance().getConnection().getLastPlayerWroteTo(ID));
	}

	@Override
	public void sendFriendRequest(PAFPlayer pSender) {
		getInstance().getConnection().sendFriendRequest(((PAFPlayerMySQL) pSender).getPlayerID(), ID);
	}

	@Override
	public void addFriend(PAFPlayer pPlayer) {
		getInstance().getConnection().addFriend(((PAFPlayerMySQL) pPlayer).getPlayerID(), ID);
	}

	@Override
	public void removeFriend(PAFPlayer pPlayer) {
		getInstance().getConnection().deleteFriend(((PAFPlayerMySQL) pPlayer).getPlayerID(), ID);
	}

	@Override
	public void setSetting(int pSettingsID, int pNewWorth) {
		getInstance().getConnection().setSetting(ID, pSettingsID, pNewWorth);
	}

	@Override
	public void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo) {
		getInstance().getConnection().setLastPlayerWroteTo(ID, ((PAFPlayerMySQL) pLastWroteTo).getPlayerID(), 0);
	}

	@Override
	public void addOfflineMessage(OfflineMessage pMessage) {
		getInstance().getConnection().offlineMessage(ID, ((PAFPlayerMySQL) pMessage.SENDER).getPlayerID(), pMessage.MESSAGE);
	}
}
