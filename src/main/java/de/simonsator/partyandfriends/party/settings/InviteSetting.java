package de.simonsator.partyandfriends.party.settings;

import de.simonsator.partyandfriends.api.SimpleSetting;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * @author simonbrungs
 * @version 1.0.0 28.03.17
 */
public class InviteSetting extends SimpleSetting {
	public InviteSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	@Override
	protected String getMessage(OnlinePAFPlayer pPlayer) {
		String identifier;
		if (pPlayer.getSettingsWorth(1) == 0) {
			identifier = "Friends.Command.Settings.PartyInvitedByEveryone";
		} else {
			identifier = "Friends.Command.Settings.PartyInvitedByFriends";
		}
		return Main.getInstance().getMessagesYml().getString(identifier);
	}

	@Override
	public void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs) {
		int worthNow = pPlayer.changeSettingsWorth(1);
		if (worthNow == 0) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
					.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone")));
		} else {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
					.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
		}
	}
}
