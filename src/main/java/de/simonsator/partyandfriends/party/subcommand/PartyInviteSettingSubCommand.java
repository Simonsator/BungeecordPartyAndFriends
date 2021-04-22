package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.party.settings.InviteSetting;

import java.util.List;

public class PartyInviteSettingSubCommand extends PartySubCommand {
	private final InviteSetting SETTING;

	public PartyInviteSettingSubCommand(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
		SETTING = new InviteSetting(null, null, 0);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		SETTING.changeSetting(pPlayer, args);
	}
}
