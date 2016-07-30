package de.simonsator.partyandfriends.api.pafplayers;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public interface OnlinePAFPlayer extends PAFPlayer {

	void createEntry();

	void connect(ServerInfo pInfo);

	ServerInfo getServer();

	boolean isOnline();

	int changeSettingsWorth(int pSettingsID);

	void sendPacket(Chat chat);

	void updatePlayerName();

	/**
	 * @return Returns the player if he is on this Bungeecord. If he is on
	 * another Bungeecord (RedisBungee) it returns null.
	 */
	ProxiedPlayer getPlayer();
}
