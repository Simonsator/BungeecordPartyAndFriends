/**
 * Will be executed on /friend msg
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Will be executed on /friend msg
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Message extends FriendSubCommand {
	public Message(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		Main.getInstance().getFriendsMSGCommand().send(pPlayer, args, 0);
	}
}
