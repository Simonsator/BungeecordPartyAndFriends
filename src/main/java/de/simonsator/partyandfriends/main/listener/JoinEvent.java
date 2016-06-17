package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.utilities.CompilePatter;
import de.simonsator.partyandfriends.utilities.OfflineMessage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;
import static de.simonsator.partyandfriends.main.Main.getPlayerManager;

/**
 * Listener for joins
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class JoinEvent implements Listener {


	/**
	 * Will be execute if somebody logs in into server
	 *
	 * @param pEvent The pEvent
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onPostLogin(final PostLoginEvent pEvent) {
		getInstance().getProxy().getScheduler().runAsync(getInstance(), new Runnable() {
			@Override
			public void run() {
				sbLoggedIn(pEvent);
			}
		});
	}

	private void sbLoggedIn(PostLoginEvent pEvent) {
		OnlinePAFPlayer player = getPlayerManager().getPlayer(pEvent.getPlayer());
		if (!player.doesExist()) {
			player.createEntry();
			return;
		} else
			player.updatePlayerName();
		List<PAFPlayer> friends = player.getFriends();
		List<PAFPlayer> friendRequests = player.getRequests();
		if (friends.isEmpty() && friendRequests.isEmpty())
			return;
		boolean noFriends = friends.isEmpty();
		if (!friendRequests.isEmpty())
			deliverFriendRequests(player, friendRequests);
		if (getInstance().getConfig().getString("General.Disable.OfflineMessages").equalsIgnoreCase("false"))
			deliverOfflineMessages(player);
		if (player.getSettingsWorth(3) == 1)
			noFriends = true;
		if (!noFriends)
			sendNowOnline(player, friends);
	}

	private void deliverFriendRequests(OnlinePAFPlayer pPlayer, List<PAFPlayer> pFriendRequests) {
		String content = "";
		for (PAFPlayer player : pFriendRequests)
			content = content + getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColor")
					+ player.getDisplayName()
					+ getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColorComma")
					+ ",";
		content = content.substring(0, content.length() - 1);
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + CompilePatter.FRIENDREQUESTPATTERN.matcher(getInstance()
				.getMessagesYml().getString("Friends.General.RequestInfoOnJoin")).replaceAll(Matcher.quoteReplacement(content))));
	}

	private void deliverOfflineMessages(OnlinePAFPlayer pPlayer) {
		ArrayList<OfflineMessage> offlineMessages = pPlayer.getOfflineMessages();
		for (OfflineMessage messages : offlineMessages) {
			getInstance().getFriendsMSGCommand().deliverOfflineMessage(messages.MESSAGE, pPlayer,
					messages.SENDER);
		}
	}

	private void sendNowOnline(OnlinePAFPlayer pPlayer, List<PAFPlayer> pFriends) {
		for (PAFPlayer friend : pFriends) {
			friend.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ CompilePatter.PLAYERPATTERN.matcher(getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOnline")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		}
	}
}
