package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/***
 * The command list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class FriendList extends FriendSubCommand {
	public FriendList(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		java.util.List<PAFPlayer> friends = pPlayer.getFriends();
		if (!hasFriends(pPlayer, friends))
			return;
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
				+ getInstance().getMessagesYml().getString("Friends.Command.List.FriendsList")
				+ getFriendsCombined(friends)));
	}

	private String getFriendsCombined(List<PAFPlayer> pFriends) {
		String friendsCombined = "";
		for (int i = 0; i < pFriends.size(); i++) {
			String additive;
			String color;
			if (!pFriends.get(i).isOnline() || pFriends.get(i).getSettingsWorth(3) == 1) {
				additive = getInstance().getMessagesYml().getString("Friends.Command.List.OfflineTitle");
				color = getInstance().getMessagesYml().getString("Friends.Command.List.OfflineColor");
			} else {
				additive = getInstance().getMessagesYml().getString("Friends.Command.List.OnlineTitle");
				color = getInstance().getMessagesYml().getString("Friends.Command.List.OnlineColor");
			}
			String comma = " ";
			if (i > 0) {
				comma = getInstance().getMessagesYml().getString("Friends.Command.List.PlayerSplit");
			}
			friendsCombined += comma + color + pFriends.get(i).getDisplayName() + additive;
		}
		return friendsCombined;
	}

	private boolean hasFriends(OnlinePAFPlayer pPlayer, List<PAFPlayer> pFriends) {
		if (pFriends.isEmpty()) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.List.NoFriendsAdded")));
			return false;
		}
		return true;
	}
}
