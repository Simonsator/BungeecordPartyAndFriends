package de.simonsator.partyandfriends.api.pafplayers;

import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.ServerDisplayNameCollection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public interface OnlinePAFPlayer extends PAFPlayer {

	void createEntry();

	void connect(ServerInfo pInfo);

	ServerInfo getServer();

	default String getServerDisplayName() {
		return ServerDisplayNameCollection.getInstance().getServerDisplayName(getServer());
	}

	boolean isOnline();

	int changeSettingsWorth(int pSettingsID);

	@Deprecated
	void sendPacket(Chat chat);

	void sendPacket(TextComponent chat);

	boolean teleportTo(OnlinePAFPlayer pPlayer);

	void update();

	/**
	 * @return Returns the player if he is on this Bungeecord. If he is on
	 * another Bungeecord (RedisBungee) it returns null.
	 */
	ProxiedPlayer getPlayer();

	default PlayerParty getParty() {
		return PartyManager.getInstance().getParty(this);
	}
}
