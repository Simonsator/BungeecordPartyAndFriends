package de.simonsator.partyandfriends.friends.subcommands;

import java.util.ArrayList;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/***
 * The command list
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class List extends FriendSubCommand {
	public List(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		ArrayList<Integer> friends = Main.getInstance().getConnection()
				.getFriends(Main.getInstance().getConnection().getPlayerID(pPlayer.getName()));
		if (!hasFriends(pPlayer, friends))
			return;
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
				+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.FriendsList")
				+ getFriendsCombined(friends)));
	}

	private String getFriendsCombined(ArrayList<Integer> pFriends) {
		String friendsCombined = "";
		for (int i = 0; i < pFriends.size(); i++) {
			String friendName = Main.getInstance().getConnection().getName(pFriends.get(i));
			ProxiedPlayer friendLoaded = ProxyServer.getInstance().getPlayer(friendName);
			String additive;
			String color;
			if (friendLoaded == null || Main.getInstance().getConnection().getSettingsWorth(pFriends.get(i), 3) == 1) {
				additive = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineTitle");
				color = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineColor");
			} else {
				friendName = friendLoaded.getDisplayName();
				additive = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineTitle");
				color = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineColor");
			}
			String comma = " ";
			if (i > 0) {
				comma = Main.getInstance().getMessagesYml().getString("Friends.Command.List.PlayerSplit");
			}
			friendsCombined += comma + color + friendName + additive;
		}
		return friendsCombined;
	}

	private boolean hasFriends(ProxiedPlayer pPlayer, ArrayList<Integer> pFriends) {
		if (pFriends.isEmpty()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.NoFriendsAdded")));
			return false;
		}
		return true;
	}
}
