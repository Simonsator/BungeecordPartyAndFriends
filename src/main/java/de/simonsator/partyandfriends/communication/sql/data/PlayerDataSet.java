package de.simonsator.partyandfriends.communication.sql.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataSet {
	public final String NAME;
	public final int ID;
	public final UUID UUID;
	public final Long LAST_ONLINE;
	public final Map<Integer, Integer> SETTINGS = new HashMap<>();

	public PlayerDataSet(String name, int id, UUID uuid) {
		NAME = name;
		ID = id;
		UUID = uuid;
		LAST_ONLINE = null;
	}

	public PlayerDataSet(String name, int id, UUID uuid, long pLastOnline) {
		NAME = name;
		ID = id;
		UUID = uuid;
		LAST_ONLINE = pLastOnline;
	}

	public void setSetting(Integer pSettingsId, Integer pSettingValue) {
		SETTINGS.put(pSettingsId, pSettingValue);
	}
}
