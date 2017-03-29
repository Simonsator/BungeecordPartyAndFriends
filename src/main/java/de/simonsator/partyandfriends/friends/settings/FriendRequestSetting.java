package de.simonsator.partyandfriends.friends.settings;

import de.simonsator.partyandfriends.api.SimpleSetting;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 28.03.17
 */
public class FriendRequestSetting extends SimpleSetting {
	public FriendRequestSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(0) == 0) {
			identifier = "Friends.Command.Settings.FriendRequestSettingNobody";
		} else {
			identifier = "Friends.Command.Settings.FriendRequestSettingEveryone";
		}
		return Main.getInstance().getMessagesYml().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pNewSettingState) {
		int worthNow = pPlayer.changeSettingsWorth(0);
		if (worthNow == 0) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests")));
		} else {

			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
					.getMessagesYml().getString("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests")));
		}
	}
}
