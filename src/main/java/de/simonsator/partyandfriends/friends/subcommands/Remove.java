/**
 * The command remove
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The command remove
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Remove extends FriendSubCommand {
	public Remove(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		int idQuery = Main.getInstance().getConnection().getPlayerID(args[1]);
		int playerID = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		if (!isAFriendOf(pPlayer, args[1], playerID, idQuery))
			return;
		Main.getInstance().getConnection().deleteFriend(playerID, idQuery);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
				.getMessagesYml().getString("Friends.Command.Remove.Removed").replace("[PLAYER]", args[1])));
	}

}
