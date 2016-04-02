/***
 * The command jump
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/***
 * The command jump
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Jump {
	/**
	 * The command jump
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The sender
	 * @param args
	 *            The arguments
	 */
	public static void jump(ProxiedPlayer player, String[] args) {
		if (args.length > 1) {
			ProxiedPlayer freund = ProxyServer.getInstance().getPlayer(args[1]);

			if (freund == null) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §7The Player §e" + args[1]
									+ " §7is §7not §7online §7or §7you §7are §7not §7a §7friend §7of §7him"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
										.getString("Friends.General.PlayerIsOffline").replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + " §7Der Spieler §e"
								+ args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
				}
				return;
			}
			if (Main.getInstance().getConnection().isFriendWith(player, freund)) {
				ServerInfo toJoin = freund.getServer().getInfo();
				if (toJoin.equals(player.getServer().getInfo())) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + " §7You §7are §7already §7on §7this §7server"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
											.getMessagesYml().getString("Friends.Command.Jump.AlreadyOnTheServer")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ " §7Du §7bist §7bereits §7auf §7diesem §7Server"));
						}
					}
					return;
				}
				if (Main.getInstance().getConnection().querySettings(player)[4] == 1) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								Main.getInstance().getFriendsPrefix() + " §7You §7cannot §jump to §7this §7person"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main
									.getInstance().getMessagesYml().getString("Friends.Command.Jump.CanNotJump")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ " §7Du §7kannst §7nicht zu §7dieser §7Person springen"));
						}
					}
					return;
				}
				player.connect(toJoin);
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ " §7Now §7you §7are §7on §7the §7same §7server, §7like §7the §7player §e"
							+ freund.getDisplayName()));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.Command.Jump.JoinedTheServer")
										.replace("[PLAYER]", freund.getDisplayName())));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ " §7Du §7bist §7jetzt §7auf §7dem §7gleichen §7Server, §7wie §7der §7Spieler §e"
								+ freund.getDisplayName()));
					}
				}
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + ChatColor.RESET + " §7The Player §e" + args[1]
									+ " §7is §7not §7online §7or §7you §7are §7not §7a §7friend §7of §7him"));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
								+ Main.getInstance().getMessagesYml().getString("Friends.General.NotAFriendOfOrOffline")
										.replace("[PLAYER]", args[1])));
					} else {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + " §7Der Spieler §e"
								+ args[1]
								+ " §7ist §7nicht §7Online §7oder §7du §7bist §7nicht §7mit §7ihm §7befreundet"));
					}
				}
			}
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(
						Main.getInstance().getFriendsPrefix() + " §7You §7need §7to §7give §7a §7friend"));
				player.sendMessage(new TextComponent("§8/§5friend §5jump [name of the §5friend]" + ChatColor.RESET
						+ "§8- §7Jump §7to §7a §7friend"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.General.NoFriendGiven")));
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
							+ Main.getInstance().getMessagesYml().getString("Friends.CommandUsage.Jump")));
				} else {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + " §7Du §7musst §7einen §7Freund §7angeben"));
					player.sendMessage(new TextComponent("§8/§5friend §5jump [Name des Freundes]" + ChatColor.RESET
							+ "§8- §7Zu §7einem §7Freund §7springen"));
				}
			}
		}
	}
}
