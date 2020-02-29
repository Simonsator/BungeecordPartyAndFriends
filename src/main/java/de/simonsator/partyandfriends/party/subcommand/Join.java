package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The class which will be executed on /party join
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Join extends PartySubCommand {

	public Join(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	/**
	 * Will be executed on /party join
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		if (isInParty(pPlayer))
			return;
		PAFPlayer pl = PAFPlayerManager.getInstance().getPlayer(args[0]);
		if (!pl.isOnline()) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Join.PlayerHasNoParty"));
			return;
		}
		OnlinePAFPlayer onlinePAFPlayer = (OnlinePAFPlayer) pl;
		PlayerParty party = PartyManager.getInstance().getParty(onlinePAFPlayer);
		if (hasNoParty(pPlayer, party))
			return;
		if (party.addPlayer(pPlayer)) {
			party.sendMessage(

					PREFIX + PLAYER_PATTERN
							.matcher(Main.getInstance().getMessages()
									.getString("Party.Command.Join.PlayerHasJoined"))
							.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName())));
			if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Party.SubCommands.Join.AutoJoinLeaderServer")) {
				ServerInfo leaderServer = party.getLeader().getServer();
				if (!leaderServer.equals(pPlayer.getServer()))
					pPlayer.connect(leaderServer);
			}
		} else
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Join.ErrorNoInvitation"));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.NO_PARTY_PERMISSION_HEIGHT == pPermissionHeight;
	}

	private boolean hasNoParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Join.PlayerHasNoParty"));
			return true;
		}
		return false;
	}

	private boolean isInParty(OnlinePAFPlayer pPlayer) {
		if (PartyManager.getInstance().getParty(pPlayer) != null) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Join.AlreadyInAPartyError"));
			return true;
		}
		return false;
	}
}
