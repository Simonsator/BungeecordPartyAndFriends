package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.LeaderNeededCommand;

import java.util.List;

/**
 * The /party kick command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Kick extends LeaderNeededCommand {
	public Kick(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	/**
	 * Will be executed on /party kick
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
		if (!standardCheck(pPlayer, party, args))
			return;
		PAFPlayer toKick = PAFPlayerManager.getInstance().getPlayer(args[0]);
		if (!checkIsInParty(pPlayer, toKick, party, args))
			return;
		party.kickPlayer((OnlinePAFPlayer) toKick);
	}

}
