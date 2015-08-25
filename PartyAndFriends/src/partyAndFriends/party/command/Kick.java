/**
 * The /party kick command
 * 
 * @author Simonsator
 * @version 1.0.0
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
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.KickAlias")));
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
		if (PartyManager.getParty(p) == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cYou §care §cnot §cin §ca §cparty."));
			} else {
				if (Main.main.language.equals("own")) {
					p.sendMessage(
							new TextComponent(Main.main.messagesYml.getString("Party.Command.General.ErrorNoParty")));
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
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.main.language.equals("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", args[0])));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDer §cSpieler " + args[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (!party.getPlayer().contains(pl)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§cThe §cplayer " + args[0] + " §cis §cnot §cin §cthe §cParty."));
			} else {
				if (Main.main.language.equals("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty")
									.replace("[PLAYER]", args[0])));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDer §cSpieler " + args[0]
							+ " §cist §cnicht §cin §cder §cParty."));
				}
			}
			return;
		}
		if (party.removePlayer(pl))

		{
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bYou §bhave §bkicked §bthe §bplayer §6"
						+ pl.getDisplayName() + " §bout §bof §bthe §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfTheParty")
									.replace("[PLAYER]", pl.getDisplayName())));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDu §bhast §bden §bSpieler §6"
							+ pl.getDisplayName() + " §baus §bdeiner §bParty §bgekickt."));
				}
			}
			for (ProxiedPlayer pp : party.getPlayer()) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bThe §bplayer §6" + pl.getDisplayName()
							+ " §bwas §bkicked §bout §bof §bparty §bparty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers")
										.replace("[PLAYER]", pl.getDisplayName())));
					} else {
						pp.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDer §bSpieler §6"
								+ pl.getDisplayName() + " §bwurde §baus §bder §bParty §bgekickt."));
					}
				}
			}
			if (Main.main.language.equalsIgnoreCase("english")) {
				pl.sendMessage(
						new TextComponent(Main.main.partyPrefix + "§bYou §bhave §bbeen §bkicked §bout §bof §bparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					pl.sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
							.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer")));
				} else {
					pl.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§bDu §bwurdest §baus §bder §bParty §bgekickt."));
				}
			}
			return;
		}

		if (Main.main.language.equalsIgnoreCase("english"))

		{
			p.sendMessage(new TextComponent(
					Main.main.partyPrefix + "§cYou §ccouldn´t §ckick §cthe §cplayer §cout §cof §cthe §cparty."));
		} else

		{
			p.sendMessage(new TextComponent(Main.main.partyPrefix
					+ "§cDu §ckonntest §cden §cSpieler §cnicht §caus §cdeiner §cParty §ckicken."));
		}
	}
}
