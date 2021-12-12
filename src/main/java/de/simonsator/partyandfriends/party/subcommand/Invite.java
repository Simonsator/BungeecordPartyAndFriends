package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.command.party.InviteEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartyJoinInviteSubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.settings.InviteSetting;

import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The /party chat Invite
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Invite extends PartyJoinInviteSubCommand {

	public Invite(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission, Main.getInstance().getMessages()
				.getString("Party.Command.Invite.MaxPlayersInPartyReached"));
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
		if (!canInvite(pPlayer, party, pPlayer))
			return;
		InviteEvent event = new InviteEvent(pPlayer, toInvite, args, this);
		BukkitBungeeAdapter.getInstance().callEvent(event);
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

	private boolean isPlayerOffline(OnlinePAFPlayer pPlayer, PAFPlayer pSearched) {
		if (!pSearched.isOnline()) {
			pPlayer.sendMessage(PREFIX
					+ Main.getInstance().getMessages().getString("Party.Command.Invite.CanNotInviteThisPlayer"));
			return true;
		}
		return false;
	}

	private boolean allowsInvitation(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pQueryPlayer) {
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Enabled") && pQueryPlayer.getSettingsWorth(InviteSetting.SETTINGS_ID) == InviteSetting.PLAYER_RECEIVES_INVITES_BY_FRIENDS_STATE && !pPlayer.isAFriendOf(pQueryPlayer)) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Party.Command.Invite.CanNotInviteThisPlayer"));
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
