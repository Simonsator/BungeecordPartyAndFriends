package de.simonsator.partyandfriends.utilities;

import net.md_5.bungee.api.config.ServerInfo;

import java.util.HashMap;
import java.util.Map;

public class ServerDisplayNameCollection {
	private static ServerDisplayNameCollection instance;
	private final Map<String, String> serverNames;

	public ServerDisplayNameCollection(ConfigurationCreator pConfig) {
		instance = this;
		if (pConfig.getBoolean("ServerDisplayNames.Use")) {
			serverNames = new HashMap<>();
			for (String key : pConfig.getSectionKeys("ServerDisplayNames.Replace")) {
				serverNames.put(pConfig.getString("ServerDisplayNames.Replace." + key + ".RealName"), pConfig.getString("ServerDisplayNames.Replace." + key + ".ReplacedName"));
			}
		} else {
			serverNames = null;
		}
	}

	public static ServerDisplayNameCollection getInstance() {
		return instance;
	}

	public String getServerDisplayName(ServerInfo pServerInfo) {
		if (pServerInfo == null)
			return "?";
		if (serverNames == null)
			return pServerInfo.getName();
		String serverDisplayName = serverNames.get(pServerInfo.getName());
		if (serverDisplayName == null)
			return pServerInfo.getName();
		return serverDisplayName;
	}
}
