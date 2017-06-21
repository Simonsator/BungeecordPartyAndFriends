package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.party.command.PartyChat;

import java.util.List;

/**
 * The /party chat command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Chat extends PartySubCommand {

	public Chat(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT <= pPermissionHeight;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PartyChat.getInstance().send(pPlayer, args);
	}
}
