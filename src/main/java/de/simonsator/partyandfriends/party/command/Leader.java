/**
 * The class which will be executed on /party leader
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package de.simonsator.partyandfriends.party.command;

import java.util.ArrayList;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.StringToArray;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * The class which will be executed on /party leader
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Leader extends SubCommand {
	/**
	 * Initials the object
	 */
	public Leader() {
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.LeaderAlias")));
	}

	/**
	 * Will be executed on /party leader
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param agrs
	 *            The arguments
	 */
	@Override
	public void onCommand(ProxiedPlayer p, String[] agrs) {
		if (agrs.length == 0) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§7You §7need §7to give §7a §7player."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§7Du §7musst §7einen §7Spieler §7angeben, §7der §7der §7neue §7Leader §7sein §7soll."));
				}
			}
			return;
		}
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(agrs[0]);
		if (player == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorPlayerNotOnline")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDer §cSpieler " + agrs[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §cbare §cnot §cthe §cparty §cleader."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§cDu §cbist §cnicht §cder §cParty §cLeader."));
				}
			}
			return;
		}
		if (player.equals(p)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§7You §7cannot §7make §7yourself §7to §7the §7new §7party §7leader"));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Leader.SenderEqualsGivenPlayer")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§7Du §7kannst §7dich §7nicht §7selber §7zum §7neuen §7Party §7Leiter §7machen"));
				}
			}
			return;
		}
		if (!party.getPlayer().contains(player)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", agrs[0])));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDer §cSpieler " + agrs[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		ArrayList<ProxiedPlayer> liste = party.getPlayer();
		liste.add(p);
		liste.remove(player);
		party.setPlayer(liste);
		party.setLeader(player);
		for (ProxiedPlayer playern : party.getPlayer()) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				playern.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§7The §7new §7party §7leader §7is §6" + player.getDisplayName()));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					playern.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml().getString("Party.Command.Leader.NewLeaderIs")
									.replace("[NEWLEADER]", player.getDisplayName())));
				} else {
					playern.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getDisplayName()));
				}
			}
		}
		if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
			player.sendMessage(new TextComponent(
					Main.getInstance().getPartyPrefix() + "§7The §7new §7party §7leader §7is §6" + player.getDisplayName()));
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				player.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml().getString("Party.Command.Leader.NewLeaderIs")
								.replace("[NEWLEADER]", player.getDisplayName())));
			} else {
				player.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getDisplayName()));
			}
		}
	}
}
