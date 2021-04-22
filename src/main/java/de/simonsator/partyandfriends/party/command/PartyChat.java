package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.api.OnlyTopCommand;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.message.PartyMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;

import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.MESSAGE_CONTENT_PATTERN;
import static de.simonsator.partyandfriends.utilities.PatterCollection.SENDER_NAME_PATTERN;

/**
 * The /p command
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PartyChat extends OnlyTopCommand {
	private static PartyChat instance;

	public PartyChat(String[] pCommandNames, String pPrefix) {
		super(pCommandNames, Main.getInstance().getGeneralConfig().getString("Commands.Party.TopCommands.PartyChat.Permissions"), pPrefix);
		instance = this;
	}

	public static PartyChat getInstance() {
		return instance;
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		send(pPlayer, args);
	}

	public void send(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		if (!messageGiven(pPlayer, args))
			return;
		StringBuilder stringBuilder = new StringBuilder();
		for (String arg : args) {
			stringBuilder.append(" ");
			stringBuilder.append(Main.getInstance().getMessages().getString("Party.Command.Chat.ContentColor"));
			stringBuilder.append(arg);
		}
		String text = stringBuilder.toString();
		PartyMessageEvent partyMessageEvent = new PartyMessageEvent(pPlayer, text, party);
		BukkitBungeeAdapter.getInstance().callEvent(partyMessageEvent);
		if (partyMessageEvent.isCancelled())
			return;
		sendMessage(text, pPlayer.getDisplayName(), party);
	}

	private void sendMessage(String pContent, String pSenderDisplayName, PlayerParty pParty) {
		String message = Main.getInstance().getMessages().getString("Party.Command.Chat.Prefix") + MESSAGE_CONTENT_PATTERN
				.matcher(SENDER_NAME_PATTERN
						.matcher(Main.getInstance().getMessages()
								.getString("Party.Command.Chat.PartyChatOutput"))
						.replaceAll(Matcher.quoteReplacement(pSenderDisplayName)))
				.replaceAll(Matcher.quoteReplacement(pContent));
		for (OnlinePAFPlayer receiver : pParty.getAllPlayers())
			receiver.sendMessage(message);
	}

	private boolean messageGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length == 0) {
			pPlayer.sendMessage(
					Main.getInstance().getMessages().getString("Party.Command.Chat.Prefix")
							+ Main.getInstance().getMessages().getString("Party.Command.Chat.ErrorNoMessage"));
			return false;
		}
		return true;
	}

	private boolean isInParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(PartyCommand.getInstance().getPrefix()
					+ Main.getInstance().getMessages().getString("Party.Command.General.ErrorNoParty"));
			return false;
		}
		return true;
	}
}
