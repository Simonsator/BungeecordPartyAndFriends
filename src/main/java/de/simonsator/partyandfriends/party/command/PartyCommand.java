package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.subcommand.*;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;
import de.simonsator.partyandfriends.utilities.LanguageConfiguration;

import java.util.Arrays;
import java.util.Vector;

/**
 * The /party command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyCommand extends TopCommand<PartySubCommand> {
	private static PartyCommand instance;

	/**
	 * @param pCommandNames The alias for the command
	 * @param pPrefix       The prefix for this command (e.g. [Party])
	 */
	public PartyCommand(String[] pCommandNames, String pPrefix) {
		super(pCommandNames, Main.getInstance().getGeneralConfig().getString("Commands.Party.TopCommands.Party.Permissions"), pPrefix);
		instance = this;
		ConfigurationCreator config = Main.getInstance().getGeneralConfig();
		LanguageConfiguration messages = Main.getInstance().getMessages();
		subCommands.add(
				new Join(config.getStringList("Commands.Party.SubCommands.Join.Names"),
						config.getInt("Commands.Party.SubCommands.Join.Priority"), messages.getString("Party.CommandUsage.Join"), config.getString("Commands.Party.SubCommands.Join.Permissions")));
		subCommands.add(new Invite(
				config.getStringList("Commands.Party.SubCommands.Invite.Names"), config.getInt("Commands.Party.SubCommands.Invite.Priority"),
				messages.getString("Party.CommandUsage.Invite"), config.getString("Commands.Party.SubCommands.Invite.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.Deny.Disabled"))
			subCommands.add(new PartyDenySubCommand(
					config.getStringList("Commands.Party.SubCommands.Deny.Names"), config.getInt("Commands.Party.SubCommands.Deny.Priority"),
					messages.getString("Party.CommandUsage.Deny"), config.getString("Commands.Party.SubCommands.Deny.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.Kick.Disabled"))
			subCommands.add(new Kick(
					config.getStringList("Commands.Party.SubCommands.Kick.Names"), config.getInt("Commands.Party.SubCommands.Kick.Priority"),
					messages.getString("Party.CommandUsage.Kick"), config.getString("Commands.Party.SubCommands.Kick.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.Info.Disabled"))
			subCommands.add(new Info(
					config.getStringList("Commands.Party.SubCommands.Info.Names"), config.getInt("Commands.Party.SubCommands.Kick.Priority"),
					messages.getString("Party.CommandUsage.List"), config.getString("Commands.Party.SubCommands.Info.Permissions")));
		subCommands.add(new Leave(
				config.getStringList("Commands.Party.SubCommands.Leave.Names"), config.getInt("Commands.Party.SubCommands.Leave.Priority"),
				messages.getString("Party.CommandUsage.Leave"), config.getString("Commands.Party.SubCommands.Leave.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.Chat.Disabled"))
			subCommands.add(new Chat(
					config.getStringList("Commands.Party.SubCommands.Chat.Names"), config.getInt("Commands.Party.SubCommands.Chat.Priority"),
					messages.getString("Party.CommandUsage.Chat"), config.getString("Commands.Party.SubCommands.Chat.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.Leader.Disabled"))
			subCommands.add(new Leader(
					config.getStringList("Commands.Party.SubCommands.Leader.Names"), config.getInt("Commands.Party.SubCommands.Leader.Priority"),
					messages.getString("Party.CommandUsage.Leader"), config.getString("Commands.Party.SubCommands.Leader.Permissions")));
		if (!config.getBoolean("Commands.Party.SubCommands.InviteSetting.Disabled"))
			subCommands.add(new PartyInviteSettingSubCommand(
					config.getStringList("Commands.Party.SubCommands.InviteSetting.Names"), config.getInt("Commands.Party.SubCommands.InviteSetting.Priority"),
					messages.getString("Party.CommandUsage.InviteSetting"), config.getString("Commands.Party.SubCommands.InviteSetting.Permissions")));
	}

	public static PartyCommand getInstance() {
		return instance;
	}

	private PartySubCommand getCommand(String name) {
		for (PartySubCommand c : subCommands) {
			if (c.isApplicable(name))
				return c;
		}
		return null;
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
			pPlayer.sendMessage(
					Main.getInstance().getMessages().getString("Party.General.HelpBegin"));
			int permissionHeight = PartyAPI.NO_PARTY_PERMISSION_HEIGHT;
			if (party != null)
				if (party.isLeader(pPlayer))
					permissionHeight = PartyAPI.LEADER_PERMISSION_HEIGHT;
				else
					permissionHeight = PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT;
			for (PartySubCommand cmd : subCommands)
				if (cmd.hasAccess(party, permissionHeight) || !Main.getInstance().getGeneralConfig().getBoolean("Commands.Party.General.PrintOnlyExecutableSubCommandsOut"))
					cmd.printOutHelp(pPlayer, getName());
			pPlayer.sendMessage(
					Main.getInstance().getMessages().getString("Party.General.HelpEnd"));
			return;
		}
		PartySubCommand sc = getCommand(args[0]);
		if (sc == null) {
			pPlayer.sendMessage(Main.getInstance().getMessages().get(getPrefix(), "Party.Error.CommandNotFound"));
			return;
		}
		if (!sc.hasPermission(pPlayer)) {
			pPlayer.sendMessage(getPrefix() + Main.getInstance().getMessages().getString("Party.Error.NoPermission"));
			return;
		}
		Vector<String> a = new Vector<>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		sc.onCommand(pPlayer, args);
	}
}
