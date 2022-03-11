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

	/**
	 * @param pPlayer The name of the player
	 * @return Returns either a {@link PAFPlayer} if the player is offline/does not exist
	 * (check with {@link PAFPlayer#doesExist()}) or an {@link OnlinePAFPlayer} if the player is online
	 */
	public abstract PAFPlayer getPlayer(String pPlayer);

	public abstract OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer);

	/**
	 * @param pPlayer The UUID of the player
	 * @return Returns either a {@link PAFPlayer} if the player is offline/does not exist
	 * (check with {@link PAFPlayer#doesExist()}) or an {@link OnlinePAFPlayer} if the player is online
	 */
	public abstract PAFPlayer getPlayer(UUID pPlayer);

	/**
	 * @param playerDataSet The {@link PlayerDataSet}. Please make sure that the data set is complete
	 * @return The {@link PAFPlayer} linked to this dataset
	 */
	public abstract PAFPlayer getPlayer(PlayerDataSet playerDataSet);
}
