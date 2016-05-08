package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Will be executed on /r
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Reply extends Command {
	public Reply(String[] aliases) {
		super("r", Main.getInstance().getConfig().getString("Permissions.FriendPermission"), aliases);
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof ProxiedPlayer)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				Main.getInstance().loadConfiguration();
				commandSender.sendMessage(
						new TextComponent(Main.getInstance().getFriendsPrefix() + "Config and MessagesYML reloaded!"));
			} else {
				Main.getInstance().loadConfiguration();
				commandSender.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + "Config reloaded"));
			}
			return;
		}
		ProxiedPlayer pPlayer = (ProxiedPlayer) commandSender;
		if (!Main.getInstance().getFriendsMSGCommand().messageGiven(pPlayer, args, 0))
			return;
		int senderID = Main.getInstance().getConnection().getPlayerID(pPlayer.getName());
		int queryID = Main.getInstance().getConnection().getLastPlayerWroteTo(senderID);
		if (queryID == 0) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.NoOneEverWroteToYou")));
			return;
		}
		String queryName = Main.getInstance().getConnection().getName(queryID);
		Main.getInstance().getFriendsMSGCommand().send(pPlayer, args, senderID, queryID, 0, queryName);
	}

}