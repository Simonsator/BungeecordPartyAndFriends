package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The /party chat command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Chat extends PartySubCommand {

	public Chat(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT <= pPermissionHeight;
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		Main.getInstance().getPartyChatCommand().execute(pPlayer, args);
	}
}
