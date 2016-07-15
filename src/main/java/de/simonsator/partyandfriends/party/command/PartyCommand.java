package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.party.subcommand.*;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Arrays;
import java.util.Vector;

/**
 * The /party command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyCommand extends TopCommand<PartySubCommand> {

	/**
	 * Initials the object
	 *
	 * @param pCommandNames The alias for the command
	 */
	public PartyCommand(String[] pCommandNames) {
		super(pCommandNames, Main.getInstance().getConfig().getString("Permissions.PartyPermission"));
		subCommands.add(
				new Join(Main.getInstance().getConfig().getStringList("CommandNames.Party.Join").toArray(new String[0]),
						0, Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Join")));
		subCommands.add(new Invite(
				Main.getInstance().getConfig().getStringList("CommandNames.Party.Invite").toArray(new String[0]), 1,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Invite")));
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick").equalsIgnoreCase("true")) {
			subCommands.add(new Kick(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Kick").toArray(new String[0]), 6,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Kick")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
			subCommands.add(new Info(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Info").toArray(new String[0]), 3,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.List")));
		}
		subCommands.add(new Leave(
				Main.getInstance().getConfig().getStringList("CommandNames.Party.Leave").toArray(new String[0]), 5,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leave")));
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
			subCommands.add(new Chat(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Chat").toArray(new String[0]), 4,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Chat")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader").equalsIgnoreCase("true")) {
			subCommands.add(new Leader(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Leader").toArray(new String[0]), 7,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leader")));
		}
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
			PlayerParty party = Main.getPartyManager().getParty(pPlayer);
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpBegin")));
			int permissionHeight = PartyAPI.NO_PARTY_PERMISSION_HEIGHT;
			if (party != null)
				if (party.isLeader(pPlayer))
					permissionHeight = PartyAPI.LEADER_PERMISSION_HEIGHT;
				else
					permissionHeight = PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT;
			for (PartySubCommand cmd : subCommands)
				if (cmd.hasAccess(permissionHeight))
					pPlayer.sendMessage(new TextComponent(cmd.HELP));
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpEnd")));
			return;
		}
		PartySubCommand sc = getCommand(args[0]);
		if (sc == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Error.CommandNotFound")));
			return;
		}
		Vector<String> a = new Vector<>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		sc.onCommand(pPlayer, args);
	}
}
