package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.subcommands.*;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

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
				Main.getInstance().getConfig().getString("Commands.Friends.TopCommands.Friend.Permissions"), pPrefix);
		instance = this;
		Configuration config = Main.getInstance().getConfig();
		if (!config.getBoolean("Commands.Friends.SubCommands.List.Disabled"))
			subCommands
					.add(new FriendList(config.getStringList("Commands.Friends.SubCommands.List.Names"),
							0, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.List"), config.getString("Commands.Friends.SubCommands.List.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.MSG.Disabled"))
			subCommands.add(new Message(
					config.getStringList("Commands.Friends.SubCommands.MSG.Names"),
					1, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.MSG"), config.getString("Commands.Friends.SubCommands.MSG.Permission")));
		List<String> acceptCommandNames = config.getStringList("Commands.Friends.SubCommands.Accept.Names");
		subCommands.add(new Accept(acceptCommandNames
				, 3,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Accept"), config.getString("Commands.Friends.SubCommands.Accept.Permission")));
		subCommands.add(
				new Add(config.getStringList("Commands.Friends.SubCommands.Add.Names"),
						2, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.ADD"), acceptCommandNames.get(0), config.getString("Commands.Friends.SubCommands.Add.Permission")));
		subCommands.add(new Deny(
				config.getStringList("Commands.Friends.SubCommands.Deny.Names"), 4,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Deny"), config.getString("Commands.Friends.SubCommands.Deny.Permission")));
		subCommands.add(new Remove(
				config.getStringList("Commands.Friends.SubCommands.Remove.Names"), 5,
				Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Remove"), config.getString("Commands.Friends.SubCommands.Remove.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.Jump.Disabled"))
			subCommands.add(new Jump(
					config.getStringList("Commands.Friends.SubCommands.Jump.Names"), 6,
					Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Jump"), config.getString("Commands.Friends.SubCommands.Jump.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.Settings.Disabled"))
			subCommands.add(new Settings(
					config.getStringList("Commands.Friends.SubCommands.Settings.Names"),
					7, Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Settings"), config.getString("Commands.Friends.SubCommands.Settings.Permission")));
		sort();
	}

	public static Friends getInstance() {
		return instance;
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
					(Main.getInstance().getMessagesYml().getString("Friends.General.HelpBegin")));
			for (FriendSubCommand command : subCommands)
				command.printOutHelp(pPlayer, getName());
			pPlayer.sendMessage(
					(Main.getInstance().getMessagesYml().getString("Friends.General.HelpEnd")));
			return;
		}
		for (FriendSubCommand command : subCommands) {
			if (command.isApplicable(args[0])) {
				if (command.hasPermission(pPlayer))
					command.onCommand(pPlayer, args);
				else
					pPlayer.sendMessage(getPrefix() + Main.getInstance().getMessagesYml().getString("Friends.General.NoPermission"));
				return;
			}
		}
		pPlayer.sendMessage((getPrefix()
				+ Main.getInstance().getMessagesYml().getString("Friends.General.CommandNotFound")));
	}

	@EventHandler
	public void onTabComplete(TabCompleteEvent pEvent) {
		tabComplete(pEvent);
	}

}
