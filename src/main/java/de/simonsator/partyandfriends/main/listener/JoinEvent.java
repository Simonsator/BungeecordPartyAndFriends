package de.simonsator.partyandfriends.main.listener;

import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

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
	 * @param pEvent
	 *            The pEvent
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onPostLogin(final PostLoginEvent pEvent) {
		Main.getInstance().getProxy().getScheduler().runAsync(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				sbLogedIn(pEvent);
			}
		});
	}

	private void sbLogedIn(PostLoginEvent pEvent) {
		int id = Main.getInstance().getConnection().getPlayerID(pEvent.getPlayer());
		if (id == -1) {
			Main.getInstance().getConnection().firstJoin(pEvent.getPlayer());
			return;
		} else if (pEvent.getPlayer().getName() != Main.getInstance().getConnection().getName(id))
			Main.getInstance().getConnection().updatePlayerName(id, pEvent.getPlayer().getName());
		ArrayList<Integer> friends = Main.getInstance().getConnection().getFriends(id);
		ArrayList<Integer> friendRequests = Main.getInstance().getConnection().getRequests(id);
		if (friends.isEmpty() && friendRequests.isEmpty()) {
			return;
		}
		boolean noFriends = friends.isEmpty();
		if (!friendRequests.isEmpty())
			deliverFriendRequests(pEvent, friendRequests);
		if (Main.getInstance().getConnection().getSettingsWorth(id, 3) == 1)
			noFriends = true;
		if (!noFriends)
			sendNowOnline(pEvent.getPlayer(), friends);
	}

	private void deliverFriendRequests(PostLoginEvent pEvent, ArrayList<Integer> pFriendRequests) {
		String content = "";
		for (int n : pFriendRequests) {
			content = content + Main.getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColor")
					+ Main.getInstance().getConnection().getName(n)
					+ Main.getInstance().getMessagesYml().getString("Friends.General.RequestInfoOnJoinColorComma")
					+ ",";
		}
		content = content.substring(0, content.length() - 1);
		pEvent.getPlayer().sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.General.RequestInfoOnJoin").replace("[FRIENDREQUESTS]", content)));
	}

	private void sendNowOnline(ProxiedPlayer pPlayer, ArrayList<Integer> pFriends) {
		for (int friendID : pFriends) {
			String befreundeterSpieler = Main.getInstance().getConnection().getName(friendID);
			ProxiedPlayer friendsLoaded = ProxyServer.getInstance().getPlayer(befreundeterSpieler);
			if (friendsLoaded != null)
				friendsLoaded.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
						+ Main.getInstance().getMessagesYml().getString("Friends.General.PlayerIsNowOnline")
								.replace("[PLAYER]", pPlayer.getDisplayName())));
		}
	}
}
