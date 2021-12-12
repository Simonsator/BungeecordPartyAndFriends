package de.simonsator.partyandfriends.party.settings;

import de.simonsator.partyandfriends.api.SimpleSetting;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.main.Main;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 28.03.17
 */
public class InviteSetting extends SimpleSetting {
	public static final int SETTINGS_ID = 1;
	public static final int PLAYER_RECEIVES_INVITES_BY_EVERYONE_STATE = 0;
	public static final int PLAYER_RECEIVES_INVITES_BY_FRIENDS_STATE = 1;

	public InviteSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(SETTINGS_ID) == PLAYER_RECEIVES_INVITES_BY_EVERYONE_STATE) {
			identifier = "Friends.Command.Settings.PartyInvitedByEveryone";
		} else {
			identifier = "Friends.Command.Settings.PartyInvitedByFriends";
		}
		return Main.getInstance().getMessages().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(SETTINGS_ID);
		if (worthNow == PLAYER_RECEIVES_INVITES_BY_EVERYONE_STATE) {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone")));
		} else {
			pPlayer.sendMessage((Friends.getInstance().getPrefix() + Main.getInstance()
					.getMessages().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
		}
	}
}
