package de.simonsator.partyandfriends.communication.sql.cache;

import java.util.UUID;

public class NoCache extends PlayerCache {
	@Override
	public void add(String pName, UUID pUUID, int pPlayerID) {

	}

	@Override
	public Integer getPlayerID(String pName) {
		return null;
	}

	@Override
	public Integer getPlayerID(UUID pUUID) {
		return null;
	}

	@Override
	public String getName(int pID) {
		return null;
	}

	@Override
	public UUID getUUID(int pID) {
		return null;
	}

	@Override
	public void updateUUID(int pPlayerID, UUID pNewUUID) {

	}

	@Override
	public void updateName(int pPlayerID, String pNewPlayerName) {

	}

	@Override
	public void deletePlayer(int pPlayerID) {

	}
}
