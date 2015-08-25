/**
 * The /party chat Invite
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.party.command;

import java.sql.SQLException;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import partyAndFriends.main.Main;
import partyAndFriends.party.PartyManager;
import partyAndFriends.party.PlayerParty;
import partyAndFriends.utilities.StringToArray;

/**
 * The /party chat Invite
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Invite extends SubCommand {
	/**
	 * Initials the object
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public Invite() {
		super(StringToArray.stringToArray(Main.main.config.getString("Aliases.InviteAlias")));
	}

	/**
	 * Will be executed on /party invite
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param p
	 *            The player
	 * @param args
	 *            Arguments The arguments
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
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		if (pl == null)

		{
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(
							new TextComponent(Main.main.partyPrefix + "§cDieser §cSpieler §cist §cnicht §conline."));
				}
			}
			return;
		}
		if (pl.equals(p))

		{
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.main.partyPrefix + "§7You §7are §7not §7allowed §7to §7invite §7yourself."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender")));
				} else {
					p.sendMessage(new TextComponent(
							Main.main.partyPrefix + "§7Du §7darfst §7dich §7nicht §7selber §7einladen."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			PartyManager.createParty(p);
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
		try {
			if (Main.main.verbindung.erlaubtPartyAnfragen(args[0]) == false
					&& Main.main.verbindung.istBefreundetMit(p, pl) == false) {
				if (Main.main.language.equalsIgnoreCase("english")) {
					p.sendMessage(new TextComponent(
							Main.main.partyPrefix + "§cYou §ccan´t §cinvite §cthis §cplayer §cinto §cyour §cParty."));
				} else {
					if (Main.main.language.equalsIgnoreCase("own")) {
						p.sendMessage(new TextComponent(Main.main.partyPrefix
								+ Main.main.messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer")));
					} else {
						p.sendMessage(new TextComponent(Main.main.partyPrefix
								+ "§cDu §ckannst §cdiesen §cSpieler §cnicht §cin §cdeine §cParty §ceinladen."));
					}
				}
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (PartyManager.getParty(pl) != null) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				new TextComponent(Main.main.partyPrefix + "§cThis §cplayer §cis §calready §cin §ca §cparty.");
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Invite.AlreadyInAParty")));
				} else {
					p.sendMessage(new TextComponent(
							Main.main.partyPrefix + "§cDieser §cDer §cSpieler §cist §cbereits §cin §ceiner §cParty."));
				}
			}
			return;
		}
		if (party.containsPlayer(pl)) {
			if (Main.main.language.equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cThe §cplayer §e" + pl.getDisplayName()
						+ "§cis §calready §cinvited §cinto §cyour §cparty."));
			} else {
				if (Main.main.language.equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.main.partyPrefix
							+ Main.main.messagesYml.getString("Party.Command.Invite.AlreadyInYourParty")
									.replace("[PLAYER]", pl.getDisplayName())));
				} else {
					p.sendMessage(new TextComponent(Main.main.partyPrefix + "§cDer §cSpieler §e" + pl.getDisplayName()
							+ " §cist §cschon §cin §cdie §cParty §ceingeladen."));
				}
			}
			return;
		}
		if (!p.hasPermission(Main.main.config.getString("Permissions.NoPlayerLimitForPartys"))) {
			if (Main.main.config.getInt("General.MaxPlayersInParty") > 1) {
				if (Main.main.config.getInt("General.MaxPlayersInParty") < party.getPlayer().size()
						+ party.inviteListSize() + 2) {
					if (Main.main.language.equalsIgnoreCase("english")) {
						p.sendMessage(
								new TextComponent(Main.main.partyPrefix + "§cThe §cMax §csize §cof §ca §cparty §cis §c"
										+ Main.main.config.getInt("General.MaxPlayersInParty") + "§c."));
					} else {
						if (Main.main.language.equalsIgnoreCase("own")) {
							p.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached")
											.replace("[MAXPLAYERSINPARTY]",
													Main.main.config.getInt("General.MaxPlayersInParty") + "")));
						} else {
							p.sendMessage(new TextComponent(
									Main.main.partyPrefix + "§cDie §cMaximale §cgröße §cfür §ceine §cParty §cist §c"
											+ Main.main.config.getInt("General.MaxPlayersInParty")));
						}
					}
					return;
				}
			}
		}

		party.invite(pl);
		if (Main.main.language.equalsIgnoreCase("english")) {
			p.sendMessage(new TextComponent(
					Main.main.partyPrefix + "§6" + pl.getDisplayName() + " §bwas §binvited §bto §byour §bparty."));
		} else {
			if (Main.main.language.equalsIgnoreCase("own")) {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + Main.main.messagesYml
						.getString("Party.Command.Invite.InvitedPlayer").replace("[PLAYER]", pl.getDisplayName())));
			} else {
				p.sendMessage(new TextComponent(Main.main.partyPrefix + "§bDu §bhast §6" + pl.getDisplayName()
						+ " §bin §bdeine §bParty §beingeladen."));
			}
		}
	}
}
