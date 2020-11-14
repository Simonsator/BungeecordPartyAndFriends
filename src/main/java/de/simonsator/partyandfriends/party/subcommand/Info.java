package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.LEADER_PATTERN;

/**
 * This class will be executed on /party list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Info extends PartySubCommand {

	public Info(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	/**
	 * This method will be executed on /party list
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		String leader = LEADER_PATTERN
				.matcher(Main.getInstance().getMessages().getString("Party.Command.Info.Leader"))
				.replaceAll(Matcher.quoteReplacement(party.getLeader().getDisplayName()));
		StringBuilder stringBuilder = new StringBuilder();
		String players;
		stringBuilder.append(Main.getInstance().getMessages().getString("Party.Command.Info.Players"));
		List<OnlinePAFPlayer> partyPlayers = party.getPlayers();
		if (partyPlayers.isEmpty()) {
			stringBuilder.append(Main.getInstance().getMessages().getString("Party.Command.Info.Empty"));
			players = stringBuilder.toString();
		} else {
			for (OnlinePAFPlayer pp : partyPlayers) {
				stringBuilder.append(pp.getDisplayName());
				stringBuilder.append(Main.getInstance().getMessages().getString("Party.Command.Info.PlayersCut"));
			}
			players = stringBuilder.substring(0, stringBuilder
					.lastIndexOf(Main.getInstance().getMessages().getString("Party.Command.Info.PlayersCut")));
		}
		pPlayer.sendMessage(
				Main.getInstance().getMessages().getString("Party.General.HelpBegin"));
		pPlayer.sendMessage(PREFIX + leader);
		pPlayer.sendMessage(PREFIX + players);
		pPlayer.sendMessage(Main.getInstance().getMessages().getString("Party.General.HelpEnd"));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT <= pPermissionHeight;
	}
}
