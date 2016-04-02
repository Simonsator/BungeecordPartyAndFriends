/**
 * The /party kick command
 * 
 * @author Simonsator
 * @version 1.0.0
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
 * The /party kick command
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Kick extends SubCommand {
	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public Kick() {
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.KickAlias")));
	}

	/**
	 * Will be executed on /party kick
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param args
	 *            The arguments
	 */
	public void onCommand(ProxiedPlayer p, String[] args) {
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
		if (PartyManager.getParty(p) == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equals("own")) {
					p.sendMessage(
							new TextComponent(Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoParty")));
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
		ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.getInstance().getLanguage().equals("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", args[0])));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDer §cSpieler " + args[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (!party.getPlayer().contains(pl)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.getInstance().getLanguage().equals("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", args[0])));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDer §cSpieler " + args[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (party.removePlayer(pl))

		{
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bYou §bhave §bkicked §bthe §bplayer §6"
						+ pl.getDisplayName() + " §bout §bof §bthe §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Kick.KickedPlayerOutOfTheParty")
									.replace("[PLAYER]", pl.getDisplayName())));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bhast §bden §bSpieler §6"
							+ pl.getDisplayName() + " §baus §bdeiner §bParty §bgekickt."));
				}
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bThe §bplayer §6" + pl.getDisplayName()
							+ " §bwas §bkicked §bout §bof §bparty §bparty."));
				} else {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
								+ Main.getInstance().getMessagesYml().getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers")
										.replace("[PLAYER]", pl.getDisplayName())));
					} else {
						pp.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDer §bSpieler §6"
								+ pl.getDisplayName() + " §bwurde §baus §bder §bParty §bgekickt."));
					}
				}
			}
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				pl.sendMessage(
						new TextComponent(Main.getInstance().getPartyPrefix() + "§bYou §bhave §bbeen §bkicked §bout §bof §bparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					pl.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
							.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer")));
				} else {
					pl.sendMessage(
							new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bwurdest §baus §bder §bParty §bgekickt."));
				}
			}
			return;
		}

		if (Main.getInstance().getLanguage().equalsIgnoreCase("english"))

		{
			p.sendMessage(new TextComponent(
					Main.getInstance().getPartyPrefix() + "§cYou §ccouldn´t §ckick §cthe §cplayer §cout §cof §cthe §cparty."));
		} else

		{
			p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ "§cDu §ckonntest §cden §cSpieler §cnicht §caus §cdeiner §cParty §ckicken."));
		}
	}
}
