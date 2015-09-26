/**
 * The class which will be executed on /party join
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package partyAndFriends.party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;
import partyAndFriends.utilities.StringToArray;

/**
 * The class which will be executed on /party join
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
public class Join extends SubCommand {
	/**
	 * Initials the object
	 */
	public Join() {
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.JoinAlias")));
	}

	/**
	 * Will be executed on /party join
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer p, String[] args) {
		partyBeitreten(p, args);
	}

	/**
	 * Join a party
	 * 
	 * @param p
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void partyBeitreten(ProxiedPlayer p, String[] args) {
		if (args.length == 0) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §cneed §cto §cgive §ca §cplayer."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) != null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix
						+ "§cYou §care §calready §cin §ca §cparty. §cUse §6/party leave §cto §cleave §cthis §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Join.AlreadyInAPartyError")));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ "§cDu §cbist §cbereits §cin §ceiner §cParty. §cNutze §6/party leave §cum §cdiese §cParty §czu §cverlassen."));
				}
			}
			return;
		}
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§cDieser §cSpieler §cist §cnicht §conline."));
				}
			}
			return;
		}
		if (PartyManager.getParty(pl) == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cThis §cplayer §chas §cno §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Join.PlayerHasNoParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§cDieser §cSpieler §chat §ckeine §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(pl);
		if (party.addPlayer(p)) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6" + p.getDisplayName()
							+ " §bjoined §bthe §bparty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.Join.PlayerHasJoinend")
										.replace("[PLAYER]", p.getDisplayName())));
					} else {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
								+ p.getDisplayName() + " §bist §bder §bParty §bbeigetreten."));
					}
				}
			}
			if (Main.main.language.equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(
						Main.main.partyPrefix + "§bThe §bplayer §6" + p.getDisplayName() + " §bjoined §bthe §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
							.getString("Party.Command.Join.PlayerHasJoinend").replace("[PLAYER]", p.getDisplayName())));
				} else {
					party.getleader().sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
							+ p.getDisplayName() + " §bist §bder §bParty §bbeigetreten."));
				}
			}
		} else {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §can´t §cjoin §cthis §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Join.ErrorNoInvatation")));
				} else {
					p.sendMessage(new TextComponent(
							Main.main.partyPrefix + "§cDu §ckannst §cder §cParty §cnicht §cbeitreten."));
				}
			}
			return;
		}
	}
}
