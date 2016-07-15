package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.friends.subcommands.*;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * The /friend command class
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Friends extends TopCommand<FriendSubCommand> {

	/**
	 * Initials the object
	 *
	 * @param pCommandNames The alias for the /friend command
	 */
	public Friends(java.util.List<String> pCommandNames) {
		super(pCommandNames.toArray(new String[0]),
				getInstance().getConfig().getString("Permissions.FriendPermission"));
		if (!getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
			subCommands
					.add(new FriendList(
							(getInstance().getConfig().getStringList("CommandNames.Friends.List")
									.toArray(new String[0])),
							0, getInstance().getMessagesYml().getString("Friends.CommandUsage.List")));
		}
		if (!getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
			subCommands.add(new Message(
					getInstance().getConfig().getStringList("CommandNames.Friends.Message").toArray(new String[0]),
					1, getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
		}
		subCommands.add(
				new Add(getInstance().getConfig().getStringList("CommandNames.Friends.Add").toArray(new String[0]),
						2, getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD")));
		subCommands.add(new Accept(
				getInstance().getConfig().getStringList("CommandNames.Friends.Accept").toArray(new String[0]), 3,
				getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept")));
		subCommands.add(new Deny(
				getInstance().getConfig().getStringList("CommandNames.Friends.Deny").toArray(new String[0]), 4,
				getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny")));
		subCommands.add(new Remove(
				getInstance().getConfig().getStringList("CommandNames.Friends.Remove").toArray(new String[0]), 5,
				getInstance().getMessagesYml().getString("Friends.CommandUsage.Remove")));
		if (!getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
			subCommands.add(new Jump(
					getInstance().getConfig().getStringList("CommandNames.Friends.Jump").toArray(new String[0]), 6,
					getInstance().getMessagesYml().getString("Friends.CommandUsage.Jump")));
		}
		if (!getInstance().getConfig().getString("General.DisableCommand.Friends.Settings")
				.equalsIgnoreCase("true")) {
			subCommands.add(new Settings(
					getInstance().getConfig().getStringList("CommandNames.Friends.Settings")
							.toArray(new String[0]),
					7, getInstance().getMessagesYml().getString("Friends.CommandUsage.Settings")));
		}
		sort();
	}

	/**
	 * Executed on /friend
	 *
	 * @param pPlayer The sender of the command
	 * @param args    The arguments
	 */
	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(
					new TextComponent(getInstance().getMessagesYml().getString("Friends.General.HelpBegin")));
			for (FriendSubCommand command : subCommands)
				pPlayer.sendMessage(new TextComponent(command.HELP));
			pPlayer.sendMessage(
					new TextComponent(getInstance().getMessagesYml().getString("Friends.General.HelpEnd")));
			return;
		}
		for (FriendSubCommand command : subCommands) {
			if (command.isApplicable(args[0])) {
				command.onCommand(pPlayer, args);
				return;
			}
		}
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
				+ getInstance().getMessagesYml().getString("Friends.General.CommandNotFound")));
	}

}
