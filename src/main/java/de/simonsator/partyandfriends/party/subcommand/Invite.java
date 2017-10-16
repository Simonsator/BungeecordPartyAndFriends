package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.events.command.party.InviteEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.MAX_PLAYERS_IN_PARTY_PATTERN;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The /party chat Invite
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Invite extends PartySubCommand {

	public Invite(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	/**
	 * Will be executed on /party invite
	 *
	 * @param pPlayer The player
	 * @param args    Arguments The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[0]);
		if (isPlayerOffline(pPlayer, playerQuery))
			return;
		OnlinePAFPlayer toInvite = (OnlinePAFPlayer) playerQuery;
		if (senderEqualsSearched(pPlayer, toInvite))
			return;
		boolean justCreated = false;
		PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
		if (party == null) {
			party = PartyManager.getInstance().createParty(pPlayer);
			justCreated = true;
		}
		if (!isPartyLeader(pPlayer, party))
			return;
		if (!allowsInvitation(pPlayer, toInvite))
			return;
		if (isAlreadyInAParty(pPlayer, toInvite)) {
			if (justCreated)
				PartyManager.getInstance().deleteParty(party);
			return;
		}
		if (isAlreadyInvited(pPlayer, toInvite, party))
			return;
		if (!canInvite(pPlayer, party))
			return;
		InviteEvent event = new InviteEvent(pPlayer, toInvite, args, this);
		ProxyServer.getInstance().getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		party.invite(toInvite);
		pPlayer.sendMessage(
				PREFIX + PLAYER_PATTERN
						.matcher(Main.getInstance().getMessages()
								.getString("Party.Command.Invite.InvitedPlayer"))
						.replaceAll(Matcher.quoteReplacement(toInvite.getDisplayName())));
	}

	private boolean senderEqualsSearched(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pSearched) {
		if (pPlayer.equals(pSearched)) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Invite.GivenPlayerEqualsSender"));
			return true;
		}
		return false;
	}

	private boolean isPartyLeader(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (!pParty.isLeader(pPlayer)) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.General.ErrorNotPartyLeader"));
			return false;
		}
		return true;
	}

	private boolean isAlreadyInAParty(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pToInvite) {
		if (PartyManager.getInstance().getParty(pToInvite) != null) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Invite.AlreadyInAParty"));
			return true;
		}
		return false;
	}

	private boolean isAlreadyInvited(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pToInvite, PlayerParty pParty) {
		if (!pParty.isPrivate())
			return false;
		if (pParty.isInvited(pToInvite)) {
			pPlayer.sendMessage(
					PREFIX + PLAYER_PATTERN
							.matcher(Main.getInstance().getMessages()
									.getString("Party.Command.Invite.AlreadyInYourParty"))
							.replaceAll(Matcher.quoteReplacement(pToInvite.getDisplayName())));
			return true;
		}
		return false;
	}

	private boolean canInvite(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (!pPlayer.getPlayer()
				.hasPermission(Main.getInstance().getConfig().getString("Permissions.NoPlayerLimitForParties")))
			if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") > 1 &&
					Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") < pParty.getAllPlayers().size()
							+ pParty.getInviteListSize() + 1) {
				pPlayer.sendMessage(PREFIX + MAX_PLAYERS_IN_PARTY_PATTERN
						.matcher(Main.getInstance().getMessages()
								.getString("Party.Command.Invite.MaxPlayersInPartyReached"))
						.replaceAll(Matcher.quoteReplacement(
								Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") + "")));
				return false;
			}
		return true;
	}

	private boolean isPlayerOffline(OnlinePAFPlayer pPlayer, PAFPlayer pSearched) {
		if (!pSearched.isOnline()) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Invite.CanNotInviteThisPlayer"));
			return true;
		}
		return false;
	}

	private boolean allowsInvitation(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pQueryPlayer) {
		if (pQueryPlayer.getSettingsWorth(1) == 1 && !pPlayer.isAFriendOf(pQueryPlayer)) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Party.Command.Invite.CanNotInviteThisPlayer"))
			;
			return false;
		}
		return true;
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.LEADER_PERMISSION_HEIGHT == pPermissionHeight
				|| PartyAPI.NO_PARTY_PERMISSION_HEIGHT == pPermissionHeight;
	}
}
