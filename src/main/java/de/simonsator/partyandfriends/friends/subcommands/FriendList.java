package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/***
 * The command list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class FriendList extends FriendSubCommand {
	private boolean sortElements;
	private final String LAST_ONLINE_COLOR = Main.getInstance().getMessagesYml().getString("Friends.Command.List.TimeColor");
	private SimpleDateFormat dateFormat = new SimpleDateFormat(Main.getInstance().getConfig().getString("General.Time.Format"),
			Locale.forLanguageTag(Main.getInstance().getConfig().getString("General.Time.LanguageTag")));

	public FriendList(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
		dateFormat.setTimeZone(TimeZone.getTimeZone(Main.getInstance().getConfig().getString("General.Time.TimeZone")));
		sortElements = Main.getInstance().getConfig().getBoolean("Commands.Friends.SubCommands.List.SortElements");
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		java.util.List<PAFPlayer> friends = pPlayer.getFriends();
		if (!hasFriends(pPlayer, friends))
			return;
		pPlayer.sendMessage(Main.getInstance().getFriendsPrefix()
				+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.FriendsList")
				+ getFriendsCombined(friends));
	}

	private String getFriendsCombined(List<PAFPlayer> pFriends) {
		StringBuilder friendsCombined = new StringBuilder();
		List<PlayerListElement> playerListElements = toList(pFriends);
		if (sortElements)
			Collections.sort(playerListElements);
		for (int i = 0; i < pFriends.size(); i++) {
			String additive;
			String color;
			if (!pFriends.get(i).isOnline() || pFriends.get(i).getSettingsWorth(3) == 1) {
				additive = PatterCollection.LAST_ONLINE_PATTERN.matcher(
						Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineTitle")).replaceAll(Matcher.quoteReplacement(
						setLastOnlineColor(dateFormat.format(pFriends.get(i).getLastOnline()))));
				color = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OfflineColor");
			} else {
				ServerInfo server = ((OnlinePAFPlayer) pFriends.get(i)).getServer();
				String serverName;
				if (server == null)
					serverName = "?";
				else
					serverName = server.getName();
				additive = PatterCollection.SERVER_ON.matcher(Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineTitle")).
						replaceAll(Matcher.quoteReplacement(serverName));
				color = Main.getInstance().getMessagesYml().getString("Friends.Command.List.OnlineColor");
			}
			if (i > 0)
				friendsCombined.append(Main.getInstance().getMessagesYml().getString("Friends.Command.List.PlayerSplit"));
			friendsCombined.append(color);
			friendsCombined.append(pFriends.get(i).getDisplayName());
			friendsCombined.append(additive);
		}
		return friendsCombined.toString();
	}

	private boolean hasFriends(OnlinePAFPlayer pPlayer, List<PAFPlayer> pFriends) {
		if (pFriends.isEmpty()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.List.NoFriendsAdded")));
			return false;
		}
		return true;
	}

	private String setLastOnlineColor(String pLastOnline) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char args : pLastOnline.toCharArray()) {
			stringBuilder.append(LAST_ONLINE_COLOR);
			stringBuilder.append(args);
		}
		return stringBuilder.toString();
	}

	private List<PlayerListElement> toList(List<PAFPlayer> pPlayers) {
		List<PlayerListElement> playerListElements = new ArrayList<>(pPlayers.size());
		for (PAFPlayer player : pPlayers)
			playerListElements.add(new PlayerListElement(player));
		return playerListElements;
	}

	private class PlayerListElement implements Comparable<PlayerListElement> {
		private final boolean IS_ONLINE;
		private final String DISPLAY_NAME;
		private final Long LAST_ONLINE;

		public PlayerListElement(PAFPlayer pPlayer) {
			boolean isOnline = pPlayer.isOnline() && pPlayer.getSettingsWorth(3) == 0;
			IS_ONLINE = isOnline;
			DISPLAY_NAME = pPlayer.getDisplayName();
			if (!isOnline)
				LAST_ONLINE = pPlayer.getLastOnline();
			else
				LAST_ONLINE = null;
		}

		@Override
		public int compareTo(PlayerListElement pCompare) {
			if (pCompare.isOnline() && this.isOnline())
				return this.getDisplayName().compareTo(pCompare.getDisplayName());
			else if (pCompare.isOnline())
				return 1;
			if (isOnline())
				return -1;
			return LAST_ONLINE.compareTo(pCompare.LAST_ONLINE);
		}

		public String getDisplayName() {
			return DISPLAY_NAME;
		}

		public boolean isOnline() {
			return IS_ONLINE;
		}
	}
}
