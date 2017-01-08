package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.events.message.PartyMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.MESSAGE_CONTENT_PATTERN;
import static de.simonsator.partyandfriends.utilities.PatterCollection.SENDER_NAME_PATTERN;

/**
 * The /p command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyChat extends TopCommand {

	/**
	 * Initials the object
	 *
	 * @param pCommandNames The alias for this command
	 */
	public PartyChat(String[] pCommandNames, String pPrefix) {
		super(pCommandNames, Main.getInstance().getConfig().getString("Permissions.PartyPermission"), pPrefix);
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		send(pPlayer, args);
	}

	public void send(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		if (!messageGiven(pPlayer, args))
			return;
		String text = "";
		for (String arg : args)
			text += " " + arg;
		ProxyServer.getInstance().getPluginManager().callEvent(new PartyMessageEvent(pPlayer, text, party));
		text = text.replaceAll(" ", " " + Main.getInstance().getMessagesYml().getString("Party.Command.Chat.ContentColor"));
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
