/**
 * The /p command
 *
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.PatterCollection.MESSAGE_CONTENT_PATTERN;
import static de.simonsator.partyandfriends.utilities.PatterCollection.SENDER_NAME_PATTERN;

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
	 * @param pCommandNames The alias for this command
	 */
	public PartyChat(String[] pCommandNames) {
		super(pCommandNames[0], Main.getInstance().getConfig().getString("Permissions.PartyPermission"), pCommandNames);
	}

	/**
	 * Will be executed on /p command
	 *
	 * @param pSender The command sender
	 * @param args    The arguments
	 */
	@Override
	public void execute(CommandSender pSender, String[] args) {
		TopCommand.isPlayer(pSender);
		send(getPlayerManager().getPlayer((ProxiedPlayer) pSender), args);
	}

	public void send(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		if (!messageGiven(pPlayer, args))
			return;
		String text = "";
		for (String arg : args) {
			text += " " + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ContentColor") + arg;
		}
		party.sendMessage(new TextComponent(
				Main.getInstance().getMessagesYml().getString("Party.Command.Chat.Prefix") + MESSAGE_CONTENT_PATTERN
						.matcher(SENDER_NAME_PATTERN
								.matcher(Main.getInstance().getMessagesYml()
										.getString("Party.Command.Chat.PartyChatOutput"))
								.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName())))
						.replaceAll(Matcher.quoteReplacement(text))));
	}

	private boolean messageGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getMessagesYml().getString("Party.Command.Chat.Prefix")
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ErrorNoMessage")));
			return false;
		}
		return true;
	}

	private boolean isInParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
			return false;
		}
		return true;
	}
}
