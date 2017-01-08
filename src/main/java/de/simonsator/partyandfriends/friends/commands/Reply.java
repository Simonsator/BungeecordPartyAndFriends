package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.api.TopCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.event.EventHandler;

/**
 * Will be executed on /r
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Reply extends TopCommand {
	public Reply(String[] aliases, String pPrefix) {
		super(aliases, Main.getInstance().getConfig().getString("Permissions.FriendPermission"), pPrefix);
	}

	@Override
	protected void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!Main.getInstance().getFriendsMSGCommand().messageGiven(pPlayer, args, 0))
			return;
		PAFPlayer queryPlayer = pPlayer.getLastPlayerWroteTo();
		if (!queryPlayer.doesExist()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.NoOneEverWroteToYou")));
			return;
		}
		Main.getInstance().getFriendsMSGCommand().send(pPlayer, args, queryPlayer, 0);

	}

}