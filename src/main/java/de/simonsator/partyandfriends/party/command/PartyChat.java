/**
 * The /p command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;

/**
 * The /p command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyChat extends Command {
	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pCommandNames
	 *            The alias for this command
	 */
	public PartyChat(String[] pCommandNames) {
		super(pCommandNames[0], Main.getInstance().getConfig().getString("Permissions.PartyPermission"), pCommandNames);
	}

	/**
	 * Will be executed on /p command
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pSender
	 *            The command sender
	 * @param args
	 *            The arguments
	 */
	@Override
	public void execute(CommandSender pSender, String[] args) {
		if (!(pSender instanceof ProxiedPlayer)) {
			new TextComponent(Main.getInstance().getPartyPrefix() + "You need to be a player!");
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) pSender;
		PlayerParty party = PartyManager.getParty(player);
		if (!isInParty(player, party))
			return;
		if (!messageGiven(player, args))
			return;
		String text = "";
		for (String arg : args) {
			text += " " + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ContentColor") + arg;
		}
		party.sendMessage(new TextComponent(Main.getInstance().getMessagesYml().getString("Party.Command.Chat.Prefix")
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Chat.PartyChatOutput")
						.replace("[SENDERNAME]", player.getDisplayName()).replace("[MESSAGE_CONTENT]", text)));
	}

	private boolean messageGiven(ProxiedPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.Command.Chat.Prefix")
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ErrorNoMessage")));
			return false;
		}
		return true;
	}

	private boolean isInParty(ProxiedPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
			return false;
		}
		return true;
	}
}
