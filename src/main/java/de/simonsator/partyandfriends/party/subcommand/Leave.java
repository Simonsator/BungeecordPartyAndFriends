package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The class which will be executed on /party leave
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Leave extends PartySubCommand {

	public Leave(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party leave
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		party.leaveParty(pPlayer);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.YouLeftTheParty")));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return pPermissionHeight <= PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT;
	}
}
