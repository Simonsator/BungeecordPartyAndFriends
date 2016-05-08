package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.party.abstractcommands.LeaderNeededCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The class which will be executed on /party leader
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Leader extends LeaderNeededCommand {
	public Leader(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party leader
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            The player
	 * @param args
	 *            The arguments
	 */
	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getParty(pPlayer);
		if (!standartCheck(pPlayer, party, args))
			return;
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
		if (!checkIsInParty(pPlayer, player, party, args))
			return;
		party.addPlayer(pPlayer);
		party.setLeader(player);
		party.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Leader.NewLeaderIs").replace("[NEWLEADER]", player.getDisplayName())));
	}
}
