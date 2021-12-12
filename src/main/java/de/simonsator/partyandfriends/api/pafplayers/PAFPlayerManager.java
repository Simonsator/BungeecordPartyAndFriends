package de.simonsator.partyandfriends.api.pafplayers;

import de.simonsator.partyandfriends.communication.sql.data.PlayerDataSet;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public abstract class PAFPlayerManager {
	private static PAFPlayerManager instance;

	public PAFPlayerManager() {
		instance = this;
	}

	public static PAFPlayerManager getInstance() {
		return instance;
	}

	public abstract PAFPlayer getPlayer(String pPlayer);

	public abstract OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer);

	public abstract PAFPlayer getPlayer(UUID pPlayer);

	/**
	 * @param playerDataSet The PlayerDataSet. Please make sure that the data set is complete
	 * @return The PAFPlayer linked to this dataset
	 */
	public abstract PAFPlayer getPlayer(PlayerDataSet playerDataSet);
}
