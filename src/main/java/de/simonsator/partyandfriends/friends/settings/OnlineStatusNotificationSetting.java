package de.simonsator.partyandfriends.friends.settings;

import de.simonsator.partyandfriends.api.SimpleSetting;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;

import java.util.List;

public class OnlineStatusNotificationSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 101;
	public static final int SHOW_ONLINE_STATUS_CHANGE_NOTIFICATION_STATE = 0;
	public static final int DO_NOT_SHOW_ONLINE_STATUS_CHANGE_NOTIFICATION_STATE = 1;

	public OnlineStatusNotificationSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == SHOW_ONLINE_STATUS_CHANGE_NOTIFICATION_STATE) {
			identifier = "Friends.Command.Settings.ShowOnlineStatusChangeNotification";
		} else {
			identifier = "Friends.Command.Settings.DoNotShowOnlineStatusChangeNotification";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == SHOW_ONLINE_STATUS_CHANGE_NOTIFICATION_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouWillReceiveOnlineStatusNotification")));
		} else {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouWillNotReceiveOnlineStatusNotification")));
		}
	}

}
