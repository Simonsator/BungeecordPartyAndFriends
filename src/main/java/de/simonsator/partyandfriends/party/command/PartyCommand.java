package de.simonsator.partyandfriends.party.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.party.subcommand.Chat;
import de.simonsator.partyandfriends.party.subcommand.Info;
import de.simonsator.partyandfriends.party.subcommand.Invite;
import de.simonsator.partyandfriends.party.subcommand.Join;
import de.simonsator.partyandfriends.party.subcommand.Kick;
import de.simonsator.partyandfriends.party.subcommand.Leader;
import de.simonsator.partyandfriends.party.subcommand.Leave;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The /party command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyCommand extends TopCommand<PartySubCommand> {
	/**
	 * A list of the command classes
	 */
	private List<PartySubCommand> subcommand = new ArrayList<PartySubCommand>();

	/**
	 * Initials the object
	 * 
	 * @param pCommandNames
	 *            The alias for the command
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public PartyCommand(String[] pCommandNames) {
		super(pCommandNames, Main.getInstance().getConfig().getString("Permissions.PartyPermission"));
		subcommand.add(
				new Join(Main.getInstance().getConfig().getStringList("CommandNames.Party.Join").toArray(new String[0]),
						0, Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Join")));
		subcommand.add(new Invite(
				Main.getInstance().getConfig().getStringList("CommandNames.Party.Invite").toArray(new String[0]), 1,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Invite")));
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick").equalsIgnoreCase("true")) {
			subcommand.add(new Kick(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Kick").toArray(new String[0]), 6,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Kick")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info").equalsIgnoreCase("true")) {
			subcommand.add(new Info(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Info").toArray(new String[0]), 3,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Info")));
		}
		subcommand.add(new Leave(
				Main.getInstance().getConfig().getStringList("CommandNames.Party.Leave").toArray(new String[0]), 5,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leave")));
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat").equalsIgnoreCase("true")) {
			subcommand.add(new Chat(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Chat").toArray(new String[0]), 4,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Chat")));
		}
		if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader").equalsIgnoreCase("true")) {
			subcommand.add(new Leader(
					Main.getInstance().getConfig().getStringList("CommandNames.Party.Leader").toArray(new String[0]), 7,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leader")));
		}
	}

	public PartySubCommand getCommand(String name) {
		for (PartySubCommand c : subcommand) {
			if (c.isApplicable(name))
				return c;
		}
		return null;
	}

	@Override
	protected void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			PlayerParty party = PartyManager.getParty(pPlayer);
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.General.HelpBegin")));
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Join")));
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Invite")));
			if (party != null) {
				if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Info")
						.equalsIgnoreCase("true")) {
					pPlayer.sendMessage(new TextComponent(
							Main.getInstance().getMessagesYml().getString("Party.CommandUsage.List")));
				}
				if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Chat")
						.equalsIgnoreCase("true")) {
					pPlayer.sendMessage(new TextComponent(
							Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Chat")));
				}
				pPlayer.sendMessage(
						new TextComponent(Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leave")));
				if (party.isLeader(pPlayer)) {
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Kick")
							.equalsIgnoreCase("true")) {
						pPlayer.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Kick")));
					}
					if (!Main.getInstance().getConfig().getString("General.DisableCommand.Party.Leader")
							.equalsIgnoreCase("true")) {
						pPlayer.sendMessage(new TextComponent(
								Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leader")));
					}
				}
			}
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
		Vector<String> a = new Vector<String>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		sc.onCommand(pPlayer, args);
		return;
	}
}
