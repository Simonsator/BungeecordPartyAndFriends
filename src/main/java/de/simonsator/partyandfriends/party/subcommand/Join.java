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
 * The class which will be executed on /party join
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Join extends PartySubCommand {
	public Join(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party join
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		if (isInParty(pPlayer))
			return;
		ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(args[0]);
		if (pl == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasNoParty")));
			return;
		}
		PlayerParty party = PartyManager.getParty(pl);
		if (hasNoParty(pPlayer, party))
			return;
		if (party.addPlayer(pPlayer)) {
			party.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasJoinend")
							.replace("[PLAYER]", pPlayer.getDisplayName())));
		} else {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.ErrorNoInvatation")));
			return;
		}
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.NO_PARTY_PERMISSION_HEIGHT == pPermissionHeight;
	}

	public boolean hasNoParty(ProxiedPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasNoParty")));
			return true;
		}
		return false;
	}

	private boolean isInParty(ProxiedPlayer pPlayer) {
		if (PartyManager.getParty(pPlayer) != null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.AlreadyInAPartyError")));
			return true;
		}
		return false;
	}
}
