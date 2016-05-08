package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.abstractcommands.LeaderNeededCommand;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The /party kick command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Kick extends LeaderNeededCommand {
	public Kick(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party kick
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
		if (!standartCheck(pPlayer, party, args))
			return;
		ProxiedPlayer toKick = ProxyServer.getInstance().getPlayer(args[0]);
		if (!checkIsInParty(pPlayer, toKick, party, args))
			return;
		party.kickPlayer(toKick);
	}

}
