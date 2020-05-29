package de.simonsator.partyandfriends.communication.sql.cache;

import java.util.UUID;

/**
 * @author simonbrungs
 * @version 1.0.0 18.12.16
 */
public abstract class PlayerCache {

	public abstract void add(String pName, UUID pUUID, int pPlayerID);

	public abstract Integer getPlayerID(String pName);

	public abstract Integer getPlayerID(UUID pUUID);

	public abstract String getName(int pID);

	public abstract UUID getUUID(int pID);

	public abstract void updateUUID(int pPlayerID, UUID pNewUUID);

	public abstract void updateName(int pPlayerID, String pNewPlayerName);

	public abstract void deletePlayer(int pPlayerID);
}
