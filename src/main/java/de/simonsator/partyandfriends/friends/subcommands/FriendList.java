package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import net.md_5.bungee.api.chat.TextComponent;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/***
 * The command list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class FriendList extends FriendSubCommand {
	private final String LAST_ONLINE_COLOR = Main.getInstance().getMessagesYml().getString("Friends.Command.List.TimeColor");
	private SimpleDateFormat dateFormat = new SimpleDateFormat(Main.getInstance().getConfig().getString("General.Time.Format"),
			Locale.forLanguageTag(Main.getInstance().getConfig().getString("General.Time.LanguageTag")));

	public FriendList(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
		dateFormat.setTimeZone(TimeZone.getTimeZone(Main.getInstance().getConfig().getString("General.Time.TimeZone")));
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		java.util.List<PAFPlayer> friends = pPlayer.getFriends();
		if (!hasFriends(pPlayer, friends))
			return;
		pPlayer.sendMessage(getInstance().getFriendsPrefix()
				+ getInstance().getMessagesYml().getString("Friends.Command.List.FriendsList")
				+ getFriendsCombined(friends));
	}

	private String getFriendsCombined(List<PAFPlayer> pFriends) {
		StringBuilder friendsCombined = new StringBuilder();
		for (int i = 0; i < pFriends.size(); i++) {
			String additive;
			String color;
			if (!pFriends.get(i).isOnline() || pFriends.get(i).getSettingsWorth(3) == 1) {
				additive = PatterCollection.LAST_ONLINE_PATTERN.matcher(
						getInstance().getMessagesYml().getString("Friends.Command.List.OfflineTitle")).replaceAll(Matcher.quoteReplacement(
						setLastOnlineColor(dateFormat.format(pFriends.get(i).getLastOnline()))));
				color = getInstance().getMessagesYml().getString("Friends.Command.List.OfflineColor");
			} else {
				additive = PatterCollection.SERVER_ON.matcher(getInstance().getMessagesYml().getString("Friends.Command.List.OnlineTitle")).
						replaceAll(Matcher.quoteReplacement(((OnlinePAFPlayer) pFriends.get(i)).getServer().getName()));
				color = getInstance().getMessagesYml().getString("Friends.Command.List.OnlineColor");
			}
			if (i > 0)
				friendsCombined.append(getInstance().getMessagesYml().getString("Friends.Command.List.PlayerSplit"));
			friendsCombined.append(color);
			friendsCombined.append(pFriends.get(i).getDisplayName());
			friendsCombined.append(additive);
		}
		return friendsCombined.toString();
	}

	private boolean hasFriends(OnlinePAFPlayer pPlayer, List<PAFPlayer> pFriends) {
		if (pFriends.isEmpty()) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.List.NoFriendsAdded")));
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
}
