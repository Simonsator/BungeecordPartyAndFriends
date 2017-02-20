package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;

import java.util.List;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/**
 * Will be executed on /friend msg
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Message extends FriendSubCommand {
	public Message(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		getInstance().getFriendsMSGCommand().send(pPlayer, args, 0);
	}
}
