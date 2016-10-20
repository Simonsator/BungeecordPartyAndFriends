package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.friends.subcommands.*;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * The /friend command class
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Friends extends TopCommand<FriendSubCommand> {
	private static Friends instance;

	/**
	 * Initials the object
	 *
	 * @param pCommandNames The alias for the /friend command
	 */
	public Friends(java.util.List<String> pCommandNames, String pPrefix) {
		super(pCommandNames.toArray(new String[0]),
				Main.getInstance().getConfig().getString("Permissions.FriendPermission"), pPrefix);
		instance = this;
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.List").equalsIgnoreCase("true")) {
			subCommands
					.add(new FriendList(
							(Main.getInstance().getConfig().getStringList("CommandNames.Friends.List")
									.toArray(new String[0])),
							0, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.List")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.MSG").equalsIgnoreCase("true")) {
			subCommands.add(new Message(
					Main.getInstance().getConfig().getStringList("CommandNames.Friends.Message").toArray(new String[0]),
					1, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG")));
		}
		String[] acceptCommandNames = Main.getInstance().getConfig().getStringList("CommandNames.Friends.Accept").toArray(new String[0]);
		subCommands.add(new Accept(acceptCommandNames
				, 3,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept")));
		subCommands.add(
				new Add(Main.getInstance().getConfig().getStringList("CommandNames.Friends.Add").toArray(new String[0]),
						2, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD"), acceptCommandNames[0]));
		subCommands.add(new Deny(
				Main.getInstance().getConfig().getStringList("CommandNames.Friends.Deny").toArray(new String[0]), 4,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny")));
		subCommands.add(new Remove(
				Main.getInstance().getConfig().getStringList("CommandNames.Friends.Remove").toArray(new String[0]), 5,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Remove")));
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Jump").equalsIgnoreCase("true")) {
			subCommands.add(new Jump(
					Main.getInstance().getConfig().getStringList("CommandNames.Friends.Jump").toArray(new String[0]), 6,
					Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Jump")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Friends.Settings")
				.equalsIgnoreCase("true")) {
			subCommands.add(new Settings(
					Main.getInstance().getConfig().getStringList("CommandNames.Friends.Settings")
							.toArray(new String[0]),
					7, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Settings")));
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
					new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.General.HelpBegin")));
			for (FriendSubCommand command : subCommands)
				pPlayer.sendMessage(new TextComponent(command.HELP));
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.General.HelpEnd")));
			return;
		}
		for (FriendSubCommand command : subCommands) {
			if (command.isApplicable(args[0])) {
				command.onCommand(pPlayer, args);
				return;
			}
		}
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
				+ Main.getInstance().getMessagesYml().getString("Friends.General.CommandNotFound")));
	}

	public static Friends getInstance() {
		return instance;
	}
}
