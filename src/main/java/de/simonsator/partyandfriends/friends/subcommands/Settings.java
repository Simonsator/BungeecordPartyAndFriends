/***
 * The command settings
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.subcommands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.main.Main;

/***
 * The command settings
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Settings extends FriendSubCommand {
	public Settings(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(ProxiedPlayer pPlayer, String[] args) {
		if (changeSetting(pPlayer, args))
			return;
		if (Main.getInstance().getConnection().getSettingsWorth(Main.getInstance().getConnection().getPlayerID(pPlayer),
				0) == 0) {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests")));
		} else {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests")));
		}
		pPlayer.unsafe().sendPacket(new Chat("{\"text\":\""
				+ Main.getInstance().getMessagesYml()
						.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests")
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Main.getInstance().getFriendsCommand().getName() + " setting" + " friendrequests"
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingsHover")
				+ "\"}]}}}"));
		pPlayer.sendMessage(
				new TextComponent(Main.getInstance().getMessagesYml().getString("Friends.Command.Settings.SplitLine")));
		if (Main.getInstance().getConnection().getSettingsWorth(Main.getInstance().getConnection().getPlayerID(pPlayer),
				1) == 0) {
			pPlayer.sendMessage(new TextComponent(
					Main.getInstance().getFriendsPrefix() + ChatColor.RESET + Main.getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")));
		} else {
			pPlayer.sendMessage(
					new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET + Main.getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
		}
		pPlayer.unsafe()
				.sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ Main.getInstance().getMessagesYml()
								.getString("Friends.Command.Settings.ChangeThisSettingWithParty")
						+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
						+ Main.getInstance().getFriendsCommand().getName() + " setting" + " party"
						+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
						+ "Click here to change this setting." + "\"}]}}}"));
	}

	private boolean changeSetting(ProxiedPlayer pPlayer, String[] args) {
		if (args.length <= 1)
			return false;
		switch (args[1].toLowerCase()) {
		case "party": {
			int worthNow = Main.getInstance().getConnection().changeSettingsWorth(pPlayer, 1);
			if (worthNow == 0) {
				pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
						.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone")));
			} else {
				pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
						.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
			}
			return true;
		}
		case "friendrequests": {
			int worthNow = Main.getInstance().getConnection().changeSettingsWorth(pPlayer, 0);
			if (worthNow == 0) {
				pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
						.getMessagesYml().getString("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests")));
			} else {

				pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
						.getMessagesYml().getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests")));
			}
			return true;
		}
		default:
			return false;
		}
	}
}
