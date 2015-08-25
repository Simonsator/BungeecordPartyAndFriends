/**
 * The class which will be executed on /party leader
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package partyAndFriends.party.command;

import java.util.List;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;
import partyAndFriends.utilities.StringToArray;

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
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.LeaderAlias")));
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
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§7You §7need §7to give §7a §7player."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ "§7Du §7musst §7einen §7Spieler §7angeben, §7der §7der §7neue §7Leader §7sein §7soll."));
				}
			}
			return;
		}
		ProxiedPlayer player = BungeeCord.getInstance().getPlayer(agrs[0]);
		if (player == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDer §cSpieler " + agrs[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNoParty")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDu §cbist §cin §ckeiner §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §cbare §cnot §cthe §cparty §cleader."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNotPartyLeader")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§cDu §cbist §cnicht §cder §cParty §cLeader."));
				}
			}
			return;
		}
		if (player.equals(p)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix
						+ "§7You §7can §7not §7make §7youself §7to §7the §7new §7party §7leader"));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Leader.SenderEqualsGivenPlayer")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ "§7Du §7kannst §7dich §7nicht §7selber §7zum §7neuen §7Party §7Leiter §7machen"));
				}
			}
			return;
		}
		if (!party.getPlayer().contains(player)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§cThe §cplayer " + agrs[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", agrs[0])));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDer §cSpieler " + agrs[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		List<ProxiedPlayer> liste = party.getPlayer();
		liste.add(p);
		liste.remove(player);
		party.setPlayer(liste);
		party.setLeader(player);
		for (ProxiedPlayer playern : party.getPlayer()) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				playern.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§7The §7new §7party §7leader §7is §6" + player.getDisplayName()));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					playern.sendMessage(new TextComponent(
							Main.main.partyPrefix + Main.main.messagesYml.getString("Party.Command.Leader.NewLeaderIs")
									.replace("[NEWLEADER]", player.getDisplayName())));
				} else {
					playern.sendMessage(new TextComponent(Main.main.partyPrefix
							+ "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getDisplayName()));
				}
			}
		}
		if (Main.main.language.equalsIgnoreCase("english")) {
			player.sendMessage(new TextComponent(
					Main.main.partyPrefix + "§7The §7new §7party §7leader §7is §6" + player.getDisplayName()));
		} else {
			if (Main.main.language.equalsIgnoreCase("own")) {
				player.sendMessage(new TextComponent(
						Main.main.partyPrefix + Main.main.messagesYml.getString("Party.Command.Leader.NewLeaderIs")
								.replace("[NEWLEADER]", player.getDisplayName())));
			} else {
				player.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§7Der §7neue §7Party §7Leiter §7ist §6" + player.getDisplayName()));
			}
		}
	}
}
