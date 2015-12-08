/***
 * The command settings
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.friends.commands;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

/***
 * The command settings
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Settings {
	/**
	 * The command settings
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player who used this command
	 * @param args
	 *            The arguments which are given by the player
	 */
	public static void settings(ProxiedPlayer player, String[] args) {
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("party")) {
				int worthNow = Main.getInstance().getConnection().setSettings(player, 0);
				if (worthNow == 0) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §7von §ajedem §7Spieler §7in §7eine §7Party §7eingeladen §7werden"));
						}
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7can §7get §7invited §conly §7by §7by your friends §7into §7their §7Party."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §cnur §7noch §7von §7deinen §7Freunden"
									+ " §7in §7eine §7Party §7eingeladen §7werden"));
						}
					}
				}
				return;
			}
			if (args[1].equalsIgnoreCase("Freundschaftsanfragen") || args[1].equalsIgnoreCase("friendrequests")) {
				int worthNow = Main.getInstance().getConnection().setSettings(player, 1);
				if (worthNow == 0) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7are §cnot §7gone §7receive §7friend §7requests §7anymore"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
									+ Main.getInstance().getMessagesYml().getString(
											"Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §ckeine §7Freundschaftsanfragen §7mehr §7erhalten"));
						}
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7are §agone §7receive §7friend §7requests §7from §7everyone"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §7von §ajedem §7Freundschaftsanfragen §7erhalten"));
						}
					}
				}
				return;
			}
			if (args[1].equalsIgnoreCase("offline")) {
				int worthNow = Main.getInstance().getConnection().setSettings(player, 3);
				if (worthNow == 0) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7will §7be §7shown §7as §aonline"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7wirst §7nun §7als §7online §7angezeigt"));
						}
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7will §7be §7shown §7as §coffline"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7wirst §7nun §7als §coffline §7angezeigt"));
						}
					}
				}
				return;
			}
			if (args[1].equalsIgnoreCase("messages")) {
				int worthNow = Main.getInstance().getConnection().setSettings(player, 2);
				if (worthNow == 1) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7are §cnot §7gone §7receive §7messages §7anymore"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(
									new TextComponent(Main.getInstance().getFriendsPrefix() + Main.getInstance()
											.getMessagesYml().getString("Friends.Command.Settings.NowNoMessages")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §ckeine §7Nachrichten §7mehr §7erhalten"));
						}
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7you §7are §agone §7receive §7message §7from §7everyone"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + Main
									.getInstance().getMessagesYml().getString("Friends.Command.Settings.NowMessages")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Du §7kannst §7jetzt §7von §ajedem §7Nachrichten §7erhalten"));
						}
					}
				}
				return;
			}
			if (args[1].equalsIgnoreCase("jump")) {
				int worthNow = Main.getInstance().getConnection().setSettings(player, 4);
				if (worthNow == 0) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7your §7friends §7can §ajump §7to §7you"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYourFriendsCanJump")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Freunde §7können §7jetzt §7zu §7dir §aspringen"));
						}
					}
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
								+ " §7Now §7your §7friends §7can §cnot §7jump §7to §7you"));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							player.sendMessage(new TextComponent(
									Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
											.getString("Friends.Command.Settings.NowYourFriendsCanNotJump")));
						} else {
							player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
									+ " §7Freunde §7können §7jetzt §cnicht §7zu §7dir §7springen"));
						}
					}
				}
				return;
			}
		}
		int[] abgefragteEinstellung = Main.getInstance().getConnection().querySettings(player);
		if (abgefragteEinstellung[0] == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7At §7the moment §7you §7are §cnot §7gone §7receive §7friend §7request"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml().getString(
									"Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests")));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Momentan §7können §7dir §ckeine §7Freundschaftsanfragen §7gesendet §7werden"));
				}
			}

		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7At §7the moment §7you §7are §7gone §7receive §7friend §7requests §7from §aeveryone"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(
							Main.getInstance().getFriendsPrefix() + Main.getInstance().getMessagesYml()
									.getString("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests")));
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Momentan §7können §7dir §7von §ajedem §7Freundschaftsanfragen §7gesendet §7werden"));
				}
			}
		}
		String jsoncode = "";
		String zuschreiben;
		String command = "/friends settings Freundschaftsanfragen";
		if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
			zuschreiben = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
					+ " §7Change §7this §7setting §7with §6/friend §6settings §6friendrequests";
			jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
					+ "'},'hoverEvent':{'action':'show_text','value':'Click here to change this setting.'}}";
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				zuschreiben = Main.getInstance().getMessagesYml()
						.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests");
				jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
						+ "'},'hoverEvent':{'action':'show_text','value':'" + Main.getInstance().getMessagesYml()
								.getString("Friends.Command.Settings.ChangeThisSettingsHover")
						+ "'}}";
			} else {
				zuschreiben = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Freundschaftsanfragen";
				jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
						+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Einstellung zu ändern.'}}";
			}
		}
		player.unsafe().sendPacket(new Chat(jsoncode));
		String zuschreibenNeu = "";
		String jsonCodeNeu = "";
		String commandNeu = "/friends settings party";
		if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
			player.sendMessage(new TextComponent(
					Main.getInstance().getMessagesYml().getString("Friends.Command.Settings.SplitLine")));
		} else {
			player.sendMessage(new TextComponent("§8§m-----------------------------------------------"));
		}
		if (abgefragteEinstellung[1] == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7At §7the moment §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party."));
				zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7Change §7this §7setting §7with §6/friend §6settings §6Party";
				jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
						+ commandNeu
						+ "'},'hoverEvent':{'action':'show_text','value':'Click here to change this setting.'}}";
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ Main.getInstance().getMessagesYml().getString(
									"Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")));
					zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET + Main.getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingWithParty");
					jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
							+ commandNeu + "'},'hoverEvent':{'action':'show_text','value':'" + Main.getInstance()
									.getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingsHover")
							+ "'}}";
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Momentan §7können §7dir §7Party §7Einladungen §7von §ajedem §7gesendet §7werden §7gesendet §7werden"));
					zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Party";
					commandNeu = "/friends settings party";
					jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
							+ commandNeu
							+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Einstellung zu ändern.'}}";
				}
			}
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7At §7the moment §7you §7can §7get §7invited §aonly §7by §7by your friends §7into §7their §7Party."));
				zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
						+ " §7Change §7this §7setting §7with §6/friend §6settings §6Party";
				commandNeu = "/friends settings party";
				jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
						+ commandNeu
						+ "'},'hoverEvent':{'action':'show_text','value':'Click here to change this setting.'}}";
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ Main.getInstance().getMessagesYml().getString(
									"Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")));
					zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET + Main.getInstance()
							.getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingWithParty");
					jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
							+ commandNeu + "'},'hoverEvent':{'action':'show_text','value':'" + Main.getInstance()
									.getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingsHover")
							+ "'}}";
				} else {
					player.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Momentan §7können §7dir §cnur §7Party §7Einladungen §7von §7Freunden §7gesendet §7werden"));
					zuschreibenNeu = Main.getInstance().getFriendsPrefix() + ChatColor.RESET
							+ " §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Party";
					jsonCodeNeu = "{'text':'" + zuschreibenNeu + "', 'clickEvent':{'action':'run_command','value':'"
							+ commandNeu
							+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um die Einstellung zu ändern.'}}";
				}
			}
		}
		player.unsafe().sendPacket(new Chat(jsonCodeNeu));
	}
}
