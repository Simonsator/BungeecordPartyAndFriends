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
public class OfflineSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 3;
	public static final int FRIENDS_CAN_SEE_PLAYER_IS_ONLINE_STATE = 0;
	public static final int FRIENDS_ALWAYS_SEE_PLAYER_AS_OFFLINE_STATE = 1;

	public OfflineSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == FRIENDS_CAN_SEE_PLAYER_IS_ONLINE_STATE) {
			identifier = "Friends.Command.Settings.ShowAsOnline";
		} else {
			identifier = "Friends.Command.Settings.ShowAsOffline";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == FRIENDS_CAN_SEE_PLAYER_IS_ONLINE_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouWillBeShowAsOnline")));
		} else {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouWilBeShownAsOffline")));
			pPlayer.updateLastOnline();
		}
	}
}
