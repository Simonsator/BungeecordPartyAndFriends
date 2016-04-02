/**
 * The class which will be executed on /party join
 * 
 * @author Simonsator
 * @version 1.0.0
 *
 */
package de.simonsator.partyandfriends.party.command;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.PartyManager;
import de.simonsator.partyandfriends.party.PlayerParty;
import de.simonsator.partyandfriends.utilities.StringToArray;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.JoinAlias")));
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
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §cneed §cto §cgive §ca §cplayer."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) != null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
						+ "§cYou §care §calready §cin §ca §cparty. §cUse §6/party leave §cto §cleave §cthis §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.AlreadyInAPartyError")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§cDu §cbist §cbereits §cin §ceiner §cParty. §cNutze §6/party leave §cum §cdiese §cParty §czu §cverlassen."));
				}
			}
			return;
		}
		ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorPlayerNotOnline")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§cDieser §cSpieler §cist §cnicht §conline."));
				}
			}
			return;
		}
		if (PartyManager.getParty(pl) == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cThis §cplayer §chas §cno §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasNoParty")));
				} else {
					p.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§cDieser §cSpieler §chat §ckeine §cParty."));
				}
			}
			return;
		}
		PlayerParty party = PartyManager.getParty(pl);
		if (party.addPlayer(p)) {
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bThe §bplayer §6" + p.getDisplayName()
							+ " §bjoined §bthe §bparty."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasJoinend")
										.replace("[PLAYER]", p.getDisplayName())));
					} else {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDer §bSpieler §6"
								+ p.getDisplayName() + " §bist §bder §bParty §bbeigetreten."));
					}
				}
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				party.getleader().sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§bThe §bplayer §6" + p.getDisplayName() + " §bjoined §bthe §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
							.getString("Party.Command.Join.PlayerHasJoinend").replace("[PLAYER]", p.getDisplayName())));
				} else {
					party.getleader().sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDer §bSpieler §6"
							+ p.getDisplayName() + " §bist §bder §bParty §bbeigetreten."));
				}
			}
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §can´t §cjoin §cthis §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.ErrorNoInvatation")));
				} else {
					p.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + "§cDu §ckannst §cder §cParty §cnicht §cbeitreten."));
				}
			}
			return;
		}
	}
}
