package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.OnlyTopCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Will be executed on /r
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Reply extends OnlyTopCommand {
	public Reply(String[] aliases, String pPrefix) {
		super(aliases, Main.getInstance().getGeneralConfig().getString("Commands.Friends.TopCommands.Reply.Permission"), pPrefix);
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!MSG.getInstance().messageGiven(pPlayer, args, 0))
			return;
		PAFPlayer queryPlayer = pPlayer.getLastPlayerWroteTo();
		if (!queryPlayer.doesExist()) {
			pPlayer.sendMessage((getPrefix()
					+ Main.getInstance().getMessages().getString("Friends.Command.MSG.NoOneEverWroteToYou")));
			return;
		}
		MSG.getInstance().send(pPlayer, args, queryPlayer, 0);
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return Collections.emptyList();
	}
}