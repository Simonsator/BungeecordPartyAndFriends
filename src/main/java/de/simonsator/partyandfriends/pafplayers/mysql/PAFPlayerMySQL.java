package de.simonsator.partyandfriends.pafplayers.mysql;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;

public class PAFPlayerMySQL extends PAFPlayerClass {
	final int ID;

	public PAFPlayerMySQL(int pID) {
		ID = pID;
	}

	@Override
	public String getName() {
		return PAFPlayerManagerMySQL.getConnection().getName(ID);
	}

	private int getPlayerID() {
		return ID;
	}

	@Override
	public List<PAFPlayer> getFriends() {
		return idListToPAFPlayerList(PAFPlayerManagerMySQL.getConnection().getFriends(ID));
	}

	@Override
	public UUID getUniqueId() {
		return PAFPlayerManagerMySQL.getConnection().getUUID(ID);
	}

	@Override
	public String toString() {
		return "{Name:\"" + getName() + "\", DisplayName:\"" + getDisplayName() + "\"}";
	}

	@Override
	public boolean doesExist() {
		return ID > 0;
	}

	@Override
	public int getSettingsWorth(int pSettingsID) {
		return PAFPlayerManagerMySQL.getConnection().getSettingsWorth(ID, pSettingsID);
	}

	@Override
	public List<PAFPlayer> getRequests() {
		return idListToPAFPlayerList(PAFPlayerManagerMySQL.getConnection().getRequests(ID));
	}

	@Override
	public boolean hasRequestFrom(PAFPlayer pPlayer) {
		return PAFPlayerManagerMySQL.getConnection().hasRequestFrom(ID, ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	@Override
	public void denyRequest(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().denyRequest(ID,  ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	@Override
	public boolean isAFriendOf(PAFPlayer pPlayer) {
		return PAFPlayerManagerMySQL.getConnection().isAFriendOf(ID,  ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
	}

	private List<PAFPlayer> idListToPAFPlayerList(List<Integer> pList) {
		List<PAFPlayer> list = new ArrayList<>();
		for (int playerID : pList)
			list.add(((PAFPlayerManagerMySQL) getPlayerManager()).getPlayer(playerID));
		return list;
	}

	@Override
	public PAFPlayer getLastPlayerWroteTo() {
		return ((PAFPlayerManagerMySQL) getPlayerManager()).getPlayer(PAFPlayerManagerMySQL.getConnection().getLastPlayerWroteTo(ID));
	}

	@Override
	public void sendFriendRequest(PAFPlayer pSender) {
		PAFPlayerManagerMySQL.getConnection().sendFriendRequest(((PAFPlayerMySQL) pSender).getPlayerID(), ID);
	}

	@Override
	public void addFriend(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().addFriend( ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), ID);
	}

	@Override
	public PAFPlayer getPAFPlayer() {
		return this;
	}

	@Override
	public void removeFriend(PAFPlayer pPlayer) {
		PAFPlayerManagerMySQL.getConnection().deleteFriend( ((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), ID);
	}

	@Override
	public void setSetting(int pSettingsID, int pNewWorth) {
		PAFPlayerManagerMySQL.getConnection().setSetting(ID, pSettingsID, pNewWorth);
	}

	@Override
	public void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo) {
		PAFPlayerManagerMySQL.getConnection().setLastPlayerWroteTo(ID, ((PAFPlayerMySQL) pLastWroteTo).getPlayerID(), 0);
	}

}
