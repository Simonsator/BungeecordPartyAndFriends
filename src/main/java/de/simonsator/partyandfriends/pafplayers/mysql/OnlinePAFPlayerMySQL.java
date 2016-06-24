package de.simonsator.partyandfriends.pafplayers.mysql;

import static de.simonsator.partyandfriends.main.Main.getInstance;

import java.util.ArrayList;
import java.util.UUID;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public class OnlinePAFPlayerMySQL extends PAFPlayerMySQL implements OnlinePAFPlayer {
	private final ProxiedPlayer PLAYER;

	public OnlinePAFPlayerMySQL(int pID, ProxiedPlayer pPlayer) {
		super(pID);
		PLAYER = pPlayer;
	}

	@Override
	public void createEntry() {
		getInstance().getConnection().firstJoin(PLAYER);
	}

	@Override
	public String getName() {
		return PLAYER.getName();
	}

	@Override
	public String getDisplayName() {
		return PLAYER.getDisplayName();
	}

	@Override
	public UUID getUniqueId() {
		return PLAYER.getUniqueId();
	}

	@Override
	public void connect(ServerInfo pInfo) {
		PLAYER.connect(pInfo);
	}

	@Override
	public void sendMessage(TextComponent pTextComponent) {
		PLAYER.sendMessage(pTextComponent);
	}

	@Override
	public ServerInfo getServer() {
		return PLAYER.getServer().getInfo();
	}

	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public ProxiedPlayer getPlayer() {
		return PLAYER;
	}

	@Override
	public int changeSettingsWorth(int pSettingsID) {
		return getInstance().getConnection().changeSettingsWorth(PLAYER, pSettingsID);
	}

	@Override
	public void sendPacket(Chat chat) {
		PLAYER.unsafe().sendPacket(chat);
	}

	@Override
	public void updatePlayerName() {
		if (!PLAYER.getName().equals(getInstance().getConnection().getName(ID)))
			getInstance().getConnection().updatePlayerName(ID, PLAYER.getName());
	}

	@Override
	public ArrayList<OfflineMessage> getOfflineMessages() {
		return getInstance().getConnection().getOfflineMessages(PLAYER);
	}
}
