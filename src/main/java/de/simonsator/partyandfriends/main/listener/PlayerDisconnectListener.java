package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.api.events.OnlineStatusChangedMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The class with the PlayerDisconnectEvent event.
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PlayerDisconnectListener implements Listener {

	/**
	 * Will be executed on player disconnect
	 *
	 * @param pEvent The disconnect event
	 */
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent pEvent) {
		OnlinePAFPlayer player = getPlayerManager().getPlayer(pEvent.getPlayer());
		PlayerParty party = PartyManager.getInstance().getParty(player);
		if (party != null)
			party.leaveParty(player);
		String message = Main.getInstance().getFriendsPrefix() + PLAYER_PATTERN
				.matcher(Main.getInstance().getMessagesYml()
						.getString("Friends.General.PlayerIsNowOffline"))
				.replaceAll(Matcher.quoteReplacement(player.getDisplayName()));
		OnlineStatusChangedMessageEvent event = new OnlineStatusChangedMessageEvent(player, message, player.getFriends());
		ProxyServer.getInstance().getPluginManager().callEvent(event);
		if (!event.isCancelled())
			for (PAFPlayer friend : event.getFriends())
				friend.sendMessage(new TextComponent(event.getMessage()));
		if (player.getSettingsWorth(3) != 1)
			player.updateLastOnline();
	}
}