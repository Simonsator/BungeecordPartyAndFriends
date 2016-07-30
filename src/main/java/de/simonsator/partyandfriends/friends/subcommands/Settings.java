package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import static de.simonsator.partyandfriends.main.Main.getInstance;

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
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (changeSetting(pPlayer, args))
			return;
		if (pPlayer.getSettingsWorth(
				0) == 0) {
			pPlayer.sendMessage(
					new TextComponent(getInstance().getFriendsPrefix() + getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests")));
		} else {
			pPlayer.sendMessage(
					new TextComponent(getInstance().getFriendsPrefix() + getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouAreGoneReceiveFriendRequests")));
		}
		pPlayer.sendPacket(new Chat("{\"text\":\""
				+ getInstance().getMessagesYml()
				.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests")
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ getInstance().getFriendsCommand().getName() + " " + " setting" + " friendrequests"
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ getInstance().getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingsHover")
				+ "\"}]}}}"));
		pPlayer.sendMessage(
				new TextComponent(getInstance().getMessagesYml().getString("Friends.Command.Settings.SplitLine")));
		if (pPlayer.getSettingsWorth(1) == 0) {
			pPlayer.sendMessage(new TextComponent(
					getInstance().getFriendsPrefix() + ChatColor.RESET + getInstance().getMessagesYml()
							.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")));
		} else {
			pPlayer.sendMessage(
					new TextComponent(getInstance().getFriendsPrefix() + ChatColor.RESET + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
		}
		pPlayer
				.sendPacket(new Chat("{\"text\":\"" + getInstance().getFriendsPrefix() + ChatColor.RESET
						+ getInstance().getMessagesYml()
						.getString("Friends.Command.Settings.ChangeThisSettingWithParty")
						+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
						+ getInstance().getFriendsCommand().getName() + " " + " setting" + " party"
						+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
						+ "Click here to change this setting." + "\"}]}}}"));
	}

	private boolean changeSetting(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length <= 1)
			return false;
		switch (args[1].toLowerCase()) {
			case "party": {
				int worthNow = pPlayer.changeSettingsWorth(1);
				if (worthNow == 0) {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone")));
				} else {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
				}
				return true;
			}
			case "friendrequests": {
				int worthNow = pPlayer.changeSettingsWorth(0);
				if (worthNow == 0) {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests")));
				} else {

					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests")));
				}
				return true;
			}
			case "offline": {
				int worthNow = pPlayer.changeSettingsWorth(3);
				if (worthNow == 0) {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouWillBeShowAsOnline")));
				} else {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYouWilBeShownAsOffline")));
				}
				return true;
			}
			case "messages": {
				int worthNow = pPlayer.changeSettingsWorth(2);
				if (worthNow == 1) {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
							+ getInstance().getMessagesYml().getString("Friends.Command.Settings.NowNoMessages")));
				} else {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
							+ getInstance().getMessagesYml().getString("Friends.Command.Settings.NowMessages")));
				}
				return true;
			}
			case "jump": {
				int worthNow = pPlayer.changeSettingsWorth(4);
				if (worthNow == 0) {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYourFriendsCanJump")));
				} else {
					pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.NowYourFriendsCanNotJump")));
				}
				return true;
			}
			default:
				return false;
		}
	}
}
