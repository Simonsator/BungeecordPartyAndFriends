package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.subcommand.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.event.EventHandler;

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
	 * Initials the object
	 *
	 * @param pCommandNames The alias for the command
	 */
	public PartyCommand(String[] pCommandNames, String pPrefix) {
		super(pCommandNames, Main.getInstance().getConfig().getString("Commands.Party.TopCommands.Party.Permissions"), pPrefix);
		instance = this;
		subCommands.add(
				new Join(Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Join.Names").toArray(new String[0]),
						0, Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Join")));
		subCommands.add(new Invite(
				Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Invite.Names").toArray(new String[0]), 1,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Invite")));
		if (!Main.getInstance().getConfig().getBoolean("Commands.Party.SubCommands.Kick.Disabled"))
			subCommands.add(new Kick(
					Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Kick.Names").toArray(new String[0]), 6,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Kick")));
		if (!Main.getInstance().getConfig().getBoolean("Commands.Party.SubCommands.Info.Disabled"))
			subCommands.add(new Info(
					Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Info.Names").toArray(new String[0]), 3,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.List")));
		subCommands.add(new Leave(
				Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Leave.Names").toArray(new String[0]), 5,
				Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leave")));
		if (!Main.getInstance().getConfig().getBoolean("Commands.Party.SubCommands.Chat.Disabled"))
			subCommands.add(new Chat(
					Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Chat.Names").toArray(new String[0]), 4,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Chat")));
		if (!Main.getInstance().getConfig().getBoolean("Commands.Party.SubCommands.Leader.Disabled"))
			subCommands.add(new Leader(
					Main.getInstance().getConfig().getStringList("Commands.Party.SubCommands.Leader.Names").toArray(new String[0]), 7,
					Main.getInstance().getMessagesYml().getString("Party.CommandUsage.Leader")));
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
					cmd.printOutHelp(pPlayer, getName());
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
