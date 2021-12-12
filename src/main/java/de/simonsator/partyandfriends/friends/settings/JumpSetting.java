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
public class JumpSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 4;
	public static final int FRIENDS_CAN_JUMP_TO_PLAYER_STATE = 0;
	public static final int FRIENDS_CAN_NOT_JUMP_TO_PLAYER_STATE = 1;

	public JumpSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == FRIENDS_CAN_JUMP_TO_PLAYER_STATE) {
			identifier = "Friends.Command.Settings.CanJump";
		} else {
			identifier = "Friends.Command.Settings.CanNotJump";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == FRIENDS_CAN_JUMP_TO_PLAYER_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYourFriendsCanJump")));
		} else {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYourFriendsCanNotJump")));
		}
	}

}
