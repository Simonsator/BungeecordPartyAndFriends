package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.subcommands.*;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

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
	public Friends(List<String> pCommandNames, String pPrefix) {
		super(pCommandNames.toArray(new String[0]),
				Main.getInstance().getGeneralConfig().getString("Commands.Friends.TopCommands.Friend.Permissions"), pPrefix);
		instance = this;
		ConfigurationCreator config = Main.getInstance().getGeneralConfig();
		if (!config.getBoolean("Commands.Friends.SubCommands.List.Disabled"))
			subCommands
					.add(new FriendList(config.getStringList("Commands.Friends.SubCommands.List.Names"),
							config.getInt("Commands.Friends.SubCommands.List.Priority"), Main.getInstance().getMessages().getString("Friends.CommandUsage.List"), config.getString("Commands.Friends.SubCommands.List.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.MSG.Disabled"))
			subCommands.add(new Message(
					config.getStringList("Commands.Friends.SubCommands.MSG.Names"),
					config.getInt("Commands.Friends.SubCommands.MSG.Priority"), Main.getInstance().getMessages().getString("Friends.CommandUsage.MSG"), config.getString("Commands.Friends.SubCommands.MSG.Permission")));
		List<String> acceptCommandNames = config.getStringList("Commands.Friends.SubCommands.Accept.Names");
		subCommands.add(new Accept(acceptCommandNames, config.getInt("Commands.Friends.SubCommands.Accept.Priority"),
				Main.getInstance().getMessages().getString("Friends.CommandUsage.Accept"), config.getString("Commands.Friends.SubCommands.Accept.Permission")));
		subCommands.add(
				new Add(config.getStringList("Commands.Friends.SubCommands.Add.Names"),
						config.getInt("Commands.Friends.SubCommands.Add.Priority"), Main.getInstance().getMessages().getString("Friends.CommandUsage.ADD"), acceptCommandNames.get(0), config.getString("Commands.Friends.SubCommands.Add.Permission")));
		subCommands.add(new Deny(
				config.getStringList("Commands.Friends.SubCommands.Deny.Names"), config.getInt("Commands.Friends.SubCommands.Deny.Priority"),
				Main.getInstance().getMessages().getString("Friends.CommandUsage.Deny"), config.getString("Commands.Friends.SubCommands.Deny.Permission")));
		subCommands.add(new Remove(
				config.getStringList("Commands.Friends.SubCommands.Remove.Names"), config.getInt("Commands.Friends.SubCommands.Remove.Priority"),
				Main.getInstance().getMessages().getString("Friends.CommandUsage.Remove"), config.getString("Commands.Friends.SubCommands.Remove.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.Jump.Disabled"))
			subCommands.add(new Jump(
					config.getStringList("Commands.Friends.SubCommands.Jump.Names"), config.getInt("Commands.Friends.SubCommands.Jump.Priority"),
					Main.getInstance().getMessages().getString("Friends.CommandUsage.Jump"), config.getString("Commands.Friends.SubCommands.Jump.Permission")));
		if (!config.getBoolean("Commands.Friends.SubCommands.Settings.Disabled"))
			subCommands.add(new Settings(
					config.getStringList("Commands.Friends.SubCommands.Settings.Names"),
					config.getInt("Commands.Friends.SubCommands.Jump.Priority"), Main.getInstance().getMessages().getString("Friends.CommandUsage.Settings"), config.getString("Commands.Friends.SubCommands.Settings.Permission")));
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
					(Main.getInstance().getMessages().getString("Friends.General.HelpBegin")));
			for (FriendSubCommand command : subCommands)
				if (command.hasPermission(pPlayer))
					command.printOutHelp(pPlayer, getName());
			pPlayer.sendMessage(
					(Main.getInstance().getMessages().getString("Friends.General.HelpEnd")));
			return;
		}
		for (FriendSubCommand command : subCommands) {
			if (command.isApplicable(args[0])) {
				if (command.hasPermission(pPlayer))
					command.onCommand(pPlayer, args);
				else
					pPlayer.sendMessage(getPrefix() + Main.getInstance().getMessages().getString("Friends.General.NoPermission"));
				return;
			}
		}
		pPlayer.sendMessage(Main.getInstance().getMessages().get(getPrefix(), "Friends.General.CommandNotFound"));
	}
}
