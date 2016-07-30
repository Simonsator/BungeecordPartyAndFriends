package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.LEADER_PATTERN;

/**
 * This class will be executed on /party list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Info extends PartySubCommand {

	public Info(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * This method will be executed on /party list
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		String leader = LEADER_PATTERN
				.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.Info.Leader"))
				.replaceAll(Matcher.quoteReplacement(party.getLeader().getDisplayName()));
		String players = Main.getInstance().getMessagesYml().getString("Party.Command.Info.Players");
		if (!party.getPlayers().isEmpty()) {
			for (OnlinePAFPlayer pp : party.getPlayers()) {
				players = players + pp.getDisplayName()
						+ Main.getInstance().getMessagesYml().getString("Party.Command.Info.PlayersCut");
			}
			players = players.substring(0, players
					.lastIndexOf(Main.getInstance().getMessagesYml().getString("Party.Command.Info.PlayersCut")));
		} else {
			players = players + Main.getInstance().getMessagesYml().getString("Party.Command.Info.Empty");
		}
		pPlayer.sendMessage(
				new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpBegin")));
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + leader));
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + players));
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpEnd")));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT <= pPermissionHeight;
	}
}
