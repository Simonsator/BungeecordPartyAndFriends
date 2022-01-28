package de.simonsator.partyandfriends.api.pafplayers;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.List;
import java.util.UUID;

public interface PAFPlayer {
	String getName();

	/**
	 * @return Returns the display name of a player. The display name is determined by a {@link DisplayNameProvider}.
	 * To register your {@link DisplayNameProvider} use {@link PAFPlayerClass#setDisplayNameProvider(DisplayNameProvider)}
	 */
	String getDisplayName();

	List<PAFPlayer> getFriends();

	UUID getUniqueId();

	/**
	 * Sends a message to a player if he is online
	 *
	 * @param pMessage The message which should be sent. Can either be a string or a list of messages
	 *                 (a random one from the list will be sent)
	 */
	void sendMessage(Object pMessage);

	/**
	 * Sends a random message from the list to the player if he is online
	 *
	 * @param pMessages A list of messages
	 */
	void sendMessage(List<String> pMessages);

	/**
	 * Sends a message to the player if he is online
	 *
	 * @param pTextComponent The message
	 */
	void sendMessage(TextComponent pTextComponent);

	/**
	 * Sends a message to a player if he is online. The message will be broken into two messages if LINE_BREAK is used
	 * in the message
	 *
	 * @param pText The message which should be sent
	 */
	void sendMessage(String pText);

	/**
	 * Please use {@link PAFPlayer#sendPacket(TextComponent)} instead
	 *
	 * @param chat The message which should be sent
	 */
	@Deprecated
	void sendPacket(Chat chat);

	void sendPacket(TextComponent chat);

	/**
	 * @return Returns true if the player exists in the database. Returns false otherwise.
	 */
	boolean doesExist();

	int getSettingsWorth(int pSettingsID);

	List<PAFPlayer> getRequests();

	int getFriendRequestCount();

	boolean hasRequestFrom(PAFPlayer pPlayer);

	boolean hasPermission(String pPermission);

	void denyRequest(PAFPlayer pPlayer);

	/**
	 * @return Returns true if the player is online. If the player is online it is safe to cast an object of this class
	 * to an {@link OnlinePAFPlayer}
	 */
	boolean isOnline();

	boolean isAFriendOf(PAFPlayer pPlayer);

	PAFPlayer getLastPlayerWroteTo();

	void sendFriendRequest(PAFPlayer pSender);

	void addFriend(PAFPlayer pPlayer);

	PAFPlayer getPAFPlayer();

	void removeFriend(PAFPlayer pPlayer);

	void setSetting(int pSettingsID, int pNewWorth);

	void setLastPlayerWroteFrom(PAFPlayer pLastWroteTo);

	long getLastOnline();

	boolean deleteAccount();

	void updateLastOnline();
}
