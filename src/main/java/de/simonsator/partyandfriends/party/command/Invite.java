/**
 * The /party chat Invite
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
		super(StringToArray.stringToArray(Main.getInstance().getConfig().getString("Aliases.InviteAlias")));
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
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cYou §cneed §cto §cgive §ca §cplayer."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.General.ErrorNoPlayer")));
				} else {
					p.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
				}
			}
			return;
		}
		ProxiedPlayer pl = ProxyServer.getInstance().getPlayer(args[0]);
		if (pl == null)

		{
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThis §cplayer §cis §cnot §conline."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.General.CanNotInviteThisPlayer")));
				} else {
					p.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + "§cDieser §cSpieler §cist §cnicht §conline."));
				}
			}
			return;
		}
		if (pl.equals(p))

		{
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§7You §7are §7not §7allowed §7to §7invite §7yourself."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.GivenPlayerEqualsSender")));
				} else {
					p.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + "§7Du §7darfst §7dich §7nicht §7selber §7einladen."));
				}
			}
			return;
		}
		if (PartyManager.getParty(p) == null) {
			PartyManager.createParty(p);
		}

		PlayerParty party = PartyManager.getParty(p);
		if (!party.isleader(p)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cYou §cbare §cnot §cthe §cparty §cleader."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.General.ErrorNotPartyLeader")));
				} else {
					p.sendMessage(new TextComponent(
							Main.getInstance().getPartyPrefix() + "§cDu §cbist §cnicht §cder §cParty §cLeader."));
				}
			}
			return;
		}
		if (Main.getInstance().getConnection().allowsPartyRequests(args[0]) == false
				&& Main.getInstance().getConnection().isFriendWith(p, pl) == false) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
						+ "§cYou §ccan´t §cinvite §cthis §cplayer §cinto §cyour §cParty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.CanNotInviteThisPlayer")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§cDu §ckannst §cdiesen §cSpieler §cnicht §cin §cdeine §cParty §ceinladen."));
				}
			}
			return;
		}

		if (PartyManager.getParty(pl) != null) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(
						Main.getInstance().getPartyPrefix() + "§cThis §cplayer §cis §calready §cin §ca §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.AlreadyInAParty")));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ "§cDieser §cDer §cSpieler §cist §cbereits §cin §ceiner §cParty."));
				}
			}
			return;
		}
		if (party.containsPlayer(pl)) {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cThe §cplayer §e"
						+ pl.getDisplayName() + "§cis §calready §cinvited §cinto §cyour §cparty."));
			} else {
				if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
							+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.AlreadyInYourParty")
									.replace("[PLAYER]", pl.getDisplayName())));
				} else {
					p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§cDer §cSpieler §e"
							+ pl.getDisplayName() + " §cist §cschon §cin §cdie §cParty §ceingeladen."));
				}
			}
			return;
		}
		if (!p.hasPermission(Main.getInstance().getConfig().getString("Permissions.NoPlayerLimitForPartys"))) {
			if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") > 1) {
				if (Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") < party.getPlayer().size()
						+ party.inviteListSize() + 2) {
					if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
						p.sendMessage(new TextComponent(
								Main.getInstance().getPartyPrefix() + "§cThe §cMax §csize §cof §ca §cparty §cis §c"
										+ Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") + "§c."));
					} else {
						if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
							p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
									.getMessagesYml().getString("Party.Command.Invite.MaxPlayersInPartyReached")
									.replace("[MAXPLAYERSINPARTY]",
											Main.getInstance().getConfig().getInt("General.MaxPlayersInParty") + "")));
						} else {
							p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
									+ "§cDie §cMaximale §cgröße §cfür §ceine §cParty §cist §c"
									+ Main.getInstance().getConfig().getInt("General.MaxPlayersInParty")));
						}
					}
					return;
				}
			}
		}

		party.invite(pl);
		if (Main.getInstance().getLanguage().equalsIgnoreCase("english")) {
			p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§6" + pl.getDisplayName()
					+ " §bwas §binvited §bto §byour §bparty."));
		} else {
			if (Main.getInstance().getLanguage().equalsIgnoreCase("own")) {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
						+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.InvitedPlayer")
								.replace("[PLAYER]", pl.getDisplayName())));
			} else {
				p.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + "§bDu §bhast §6"
						+ pl.getDisplayName() + " §bin §bdeine §bParty §beingeladen."));
			}
		}
	}
}
