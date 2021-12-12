package de.simonsator.partyandfriends.friends.settings;

import de.simonsator.partyandfriends.api.SimpleSetting;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 28.03.17
 */
public class FriendRequestSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 0;
	public static final int DOES_NOT_RECEIVE_FRIEND_REQUESTS_STATE = 0;
	public static final int DOES_RECEIVE_FRIEND_REQUESTS_STATE = 1;

	public FriendRequestSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == DOES_NOT_RECEIVE_FRIEND_REQUESTS_STATE) {
			identifier = "Friends.Command.Settings.FriendRequestSettingNobody";
		} else {
			identifier = "Friends.Command.Settings.FriendRequestSettingEveryone";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pNewSettingState) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == DOES_NOT_RECEIVE_FRIEND_REQUESTS_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests")));
		} else {

			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests")));
		}
	}
}
