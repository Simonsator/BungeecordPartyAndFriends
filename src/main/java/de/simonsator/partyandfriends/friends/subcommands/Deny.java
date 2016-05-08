/**
 * The command deny
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.RequestReactionsCommands;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The command deny
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Deny extends RequestReactionsCommands {
	public Deny(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		int idSender = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		int idQuery = Main.getInstance().getConnection().getPlayerID(args[1]);
		if (hasNoRequest(pPlayer, args[1], idSender, idQuery))
			return;
		Main.getInstance().getConnection().denyRequest(idSender, idQuery);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.Command.Deny.HasDenied").replace("[PLAYER]", args[1])));
	}

}
