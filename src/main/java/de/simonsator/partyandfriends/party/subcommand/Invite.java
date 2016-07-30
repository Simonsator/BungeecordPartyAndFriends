package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.PatterCollection.MAX_PLAYERS_IN_PARTY_PATTERN;
import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The /party chat Invite
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Invite extends PartySubCommand {

	public Invite(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
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
		PAFPlayer playerQuery = getPlayerManager().getPlayer(args[0]);
		if (isPlayerOffline(pPlayer, playerQuery))
			return;
		OnlinePAFPlayer toInvite = (OnlinePAFPlayer) playerQuery;
		if (senderEqualsSearched(pPlayer, toInvite))
			return;
		boolean justCreated = false;
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (party == null) {
			party = Main.getPartyManager().createParty(pPlayer);
			justCreated = true;
		}
		if (!isPartyLeader(pPlayer, party))
			return;
		if (!allowsInvitation(pPlayer, toInvite))
			return;
		if (isAlreadyInAParty(pPlayer, toInvite)) {
			if (justCreated)
				Main.getPartyManager().deleteParty(party);
			return;
		}
		if (isAlreadyInvited(pPlayer, toInvite, party))
			return;
		if (!canInvite(pPlayer, party))
			return;
		party.invite(toInvite);
		pPlayer.sendMessage(
				new TextComponent(
						Main.getInstance().getPartyPrefix() + PLAYER_PATTERN
								.matcher(Main.getInstance().getMessagesYml()
										.getString("Party.Command.Invite.InvitedPlayer"))
								.replaceAll(Matcher.quoteReplacement(toInvite.getDisplayName()))));
	}

	private boolean senderEqualsSearched(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pSearched) {
		if (pPlayer.equals(pSearched)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.GivenPlayerEqualsSender")));
			return true;
		}
		return false;
	}

	private boolean isPartyLeader(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (!pParty.isLeader(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
			return false;
		}
		return true;
	}

	private boolean isAlreadyInAParty(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pToInvite) {
		if (Main.getPartyManager().getParty(pToInvite) != null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.AlreadyInAParty")));
			return true;
		}
		return false;
	}

	private boolean isAlreadyInvited(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pToInvite, PlayerParty pParty) {
		if (pParty.isInvited(pToInvite)) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getPartyPrefix() + PLAYER_PATTERN
							.matcher(Main.getInstance().getMessagesYml()
									.getString("Party.Command.Invite.AlreadyInYourParty"))
							.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
			return true;
		}
		return false;
	}

	private boolean canInvite(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (!pPlayer.getPlayer()
				.hasPermission(Main.getInstance().getConfig().getString("Permissions.NoPlayerLimitForParties")))
			if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") > 1)
				if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") < pParty.getAllPlayers().size()
						+ pParty.getInviteListSize() + 1) {
					pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + MAX_PLAYERS_IN_PARTY_PATTERN
							.matcher(Main.getInstance().getMessagesYml()
									.getString("Party.Command.Invite.MaxPlayersInPartyReached"))
							.replaceAll(Matcher.quoteReplacement(
									Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") + ""))));
					return false;
				}
		return true;
	}

	private boolean isPlayerOffline(OnlinePAFPlayer pPlayer, PAFPlayer pSeracherd) {
		if (!pSeracherd.isOnline()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.CanNotInviteThisPlayer")));
			return true;
		}
		return false;
	}

	private boolean allowsInvitation(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pQueryPlayer) {
		if (pQueryPlayer.getSettingsWorth(1) == 1 && !pPlayer.isAFriendOf(pQueryPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.CanNotInviteThisPlayer")));
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
