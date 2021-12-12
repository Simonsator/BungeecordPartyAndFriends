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
public class PMSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 2;
	public static final int PLAYER_RECEIVES_PM_STATE = 0;
	public static final int PLAYER_DOES_NOT_RECEIVES_PM_STATE = 1;

	public PMSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == PLAYER_RECEIVES_PM_STATE) {
			identifier = "Friends.Command.Settings.ReceivePM";
		} else {
			identifier = "Friends.Command.Settings.DoNotReceivePM";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == PLAYER_DOES_NOT_RECEIVES_PM_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.Settings.NowNoMessages")));
		} else {
			pPlayer.sendMessage((Friends.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.Settings.NowMessages")));
		}
	}
}
