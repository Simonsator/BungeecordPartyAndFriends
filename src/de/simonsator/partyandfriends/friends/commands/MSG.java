/**
 * Will be executed on /msg
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.friends.Messages;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Will be executed on /msg
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class MSG extends Command {
	/**
	 * Initials the command
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param friendsAliasMsg
	 *            The aliases for the command /msg
	 */
	public MSG(String[] friendsAliasMsg) {
		super("msg", Main.getInstance().getConfig().getString("Permissions.FriendPermission"), friendsAliasMsg);
	}

	/**
	 * Executes the command /msg
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param commandSender
	 *            The sender of the command
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			commandSender.sendMessage(new TextComponent("§8[§5§lFriends§8]" + " You need to be a player!"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) commandSender;
		Messages.send(player, args, 1);
	}
}
