package de.simonsator.partyandfriends.communication.sql.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author simonbrungs
 * @version 1.0.0 21.12.16
 */
public class LocalPlayerCache extends PlayerCache {
	private final Map<String, Integer> namePlayerID = new HashMap<>();
	private final Map<UUID, Integer> uuidPlayerID = new HashMap<>();
	private final Map<Integer, String> playerIDName = new HashMap<>();
	private final Map<Integer, UUID> playerIDUUID = new HashMap<>();

	@Override
	public void add(String pName, UUID pUUID, int pPlayerID) {
		namePlayerID.put(pName.toLowerCase(), pPlayerID);
		uuidPlayerID.put(pUUID, pPlayerID);
		playerIDName.put(pPlayerID, pName);
		playerIDUUID.put(pPlayerID, pUUID);
	}

	@Override
	public Integer getPlayerID(String pName) {
		return namePlayerID.get(pName.toLowerCase());
	}

	@Override
	public Integer getPlayerID(UUID pUUID) {
		return uuidPlayerID.get(pUUID);
	}

	@Override
	public String getName(int pID) {
		return playerIDName.get(pID);
	}

	@Override
	public UUID getUUID(int pID) {
		return playerIDUUID.get(pID);
	}

	@Override
	public void updateUUID(int pPlayerID, UUID pNewUUID) {
		UUID oldUUID = getUUID(pPlayerID);
		if (oldUUID != null)
			uuidPlayerID.remove(oldUUID);
		uuidPlayerID.put(pNewUUID, pPlayerID);
		playerIDUUID.put(pPlayerID, pNewUUID);
	}

	@Override
	public void updateName(int pPlayerID, String pNewPlayerName) {
		String oldName = getName(pPlayerID);
		if (oldName != null)
			namePlayerID.remove(oldName);
		namePlayerID.put(pNewPlayerName, pPlayerID);
		playerIDName.put(pPlayerID, pNewPlayerName);
	}

	@Override
	public void deletePlayer(int pPlayerID) {
		String playerName = playerIDName.remove(pPlayerID);
		UUID playerUUID = playerIDUUID.remove(pPlayerID);
		if (playerName != null)
			namePlayerID.remove(playerName);
		if (playerUUID != null)
			uuidPlayerID.remove(playerUUID);
	}

}
