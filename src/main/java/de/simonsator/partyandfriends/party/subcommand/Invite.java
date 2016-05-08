package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            The player
	 * @param args
	 *            Arguments The arguments
	 */
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		ProxiedPlayer toInvite = ProxyServer.getInstance().getPlayer(args[0]);
		if (isPlayerOffline(pPlayer, toInvite))
			return;
		if (senderEqualsSearched(pPlayer, toInvite))
			return;
		PlayerParty party = PartyManager.getParty(pPlayer);
		if (party == null)
			party = PartyManager.createParty(pPlayer);
		if (!isPartyLeader(pPlayer, party))
			return;
		if (!allowsInvitation(pPlayer, toInvite))
			return;
		if (isAlreadyInAParty(pPlayer, toInvite))
			return;
		if (isAlreadyInvited(pPlayer, toInvite, party))
			return;
		if (!canInvite(pPlayer, party))
			return;
		party.invite(toInvite);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Invite.InvitedPlayer").replace("[PLAYER]", toInvite.getDisplayName())));
	}

	private boolean senderEqualsSearched(ProxiedPlayer pPlayer, ProxiedPlayer pSearched) {
		if (pPlayer.equals(pSearched)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.GivenPlayerEqualsSender")));
			return true;
		}
		return false;
	}

	private boolean isPartyLeader(ProxiedPlayer pPlayer, PlayerParty pParty) {
		if (!pParty.isLeader(pPlayer)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
			return false;
		}
		return true;
	}

	private boolean isAlreadyInAParty(ProxiedPlayer pPlayer, ProxiedPlayer pToInvite) {
		if (PartyManager.getParty(pToInvite) != null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.AlreadyInAParty")));
			return true;
		}
		return false;
	}

	private boolean isAlreadyInvited(ProxiedPlayer pPlayer, ProxiedPlayer pToInvite, PlayerParty pParty) {
		if (pParty.isInvited(pToInvite)) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.AlreadyInYourParty")
							.replace("[PLAYER]", pPlayer.getDisplayName())));
			return true;
		}
		return false;
	}

	private boolean canInvite(ProxiedPlayer pPlayer, PlayerParty pParty) {
		if (!pPlayer.hasPermission(Main.getInstance().getConfig().getString("Permissions.NoPlayerLimitForPartys")))
			if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") > 1)
				if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") < pParty.getAllPlayers().size()
						+ pParty.getInviteListSize() + 1) {
					pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.MaxPlayersInPartyReached")
							.replace("[MAXPLAYERSINPARTY]",
									Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") + "")));
					return false;
				}
		return true;
	}

	private boolean isPlayerOffline(ProxiedPlayer pPlayer, ProxiedPlayer pSeracherd) {
		if (pSeracherd == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.CanNotInviteThisPlayer")));
			return true;
		}
		return false;
	}

	private boolean allowsInvitation(ProxiedPlayer pPlayer, ProxiedPlayer pQueryPlayer) {
		int friendID = Main.getInstance().getConnection().getPlayerID(pQueryPlayer);
		if (Main.getInstance().getConnection().getSettingsWorth(friendID, 1) == 1 && !Main.getInstance().getConnection()
				.isAFriendOf(friendID, Main.getInstance().getConnection().getPlayerID(pPlayer))) {
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
