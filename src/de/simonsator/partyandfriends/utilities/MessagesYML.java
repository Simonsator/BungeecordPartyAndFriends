/**
 * This class loads the Messages.yml
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * This class loads the Messages.yml
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class MessagesYML {
	/**
	 * Adds missing lines in the Messages.yml
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the Messages.yml variable
	 * @throws IOException
	 *             Can throw a {@link SQLException}
	 */
	public static Configuration loadMessages() throws IOException {

		File file = new File(Main.getInstance().getDataFolder().getPath(), "Messages.yml");
		if (!file.exists()) {
			file.createNewFile();
		}
		Configuration messagesYml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		if (messagesYml.getString("General.LanguageName").equals("")) {
			messagesYml.set("General.LanguageName", "Own");
		}
		if (messagesYml.getString("Party.General.PartyPrefix").equals("")) {
			messagesYml.set("Party.General.PartyPrefix", "§7[§5Party§7] ");
		}
		if (messagesYml.getString("Party.General.HelpBegin").equals("")) {
			messagesYml.set("Party.General.HelpBegin",
					"§8§m-------------------" + ChatColor.RESET + "§8[§5§lParty§8]§m-------------------");
		}
		if (messagesYml.getString("Party.General.HelpEnd").equals("")) {
			messagesYml.set("Party.General.HelpEnd", "§8§m---------------------------------------------");
		}
		if (messagesYml.getString("Party.Error.CommandNotFound").equals("")) {
			messagesYml.set("Party.Error.CommandNotFound", "§cThis command doesn´t exist!");
		}
		if (messagesYml.getString("Party.CommandUsage.Join").equals("")) {
			messagesYml.set("Party.CommandUsage.Join", "§8/§5Party " + "join <Name>" + " §8- §7Join §7a §7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Invite").equals("")) {
			messagesYml.set("Party.CommandUsage.Invite",
					"§8/§5Party " + "invite <player>" + " §8- §7Invite §7a §7player §7into §7your §7Party");
		}
		if (messagesYml.getString("Party.CommandUsage.List").equals("")) {
			messagesYml.set("Party.CommandUsage.List",
					"§8/§5Party " + "list" + " §8- §7List §7all §7players §7who §7are §7in §7the §7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Chat").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat",
					"§8/§5Party " + "chat <message>" + " §8- §7Send §7all §7players §7in §7the §7party §7a §7message");
		}
		if (messagesYml.getString("Party.CommandUsage.Leave").equals("")) {
			messagesYml.set("Party.CommandUsage.Leave", "§8/§5Party " + "leave" + " §8- §7Leave the party");
		}
		if (messagesYml.getString("Party.CommandUsage.Kick").equals("")) {
			messagesYml.set("Party.CommandUsage.Kick",
					"§8/§5Party " + "kick <player>" + " §8- §7Kicks §7a §7player §7out §7of §7the §7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Leader").equals("")) {
			messagesYml.set("Party.CommandUsage.Leader", "§8/§5Party " + "leader §5<player>"
					+ " §8- §7Makes §7another §7player §7to §7the §7party §7leader");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoParty", "§5You need to be in a party");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNotPartyLeader", "§cYou §cbare §cnot §cthe §cparty §cleader.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty",
					"§cThe §cplayer [PLAYER] §cis §cnot §cin §cthe §cParty.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoPlayer").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoPlayer", "§cYou §cneed §cto §cgive §ca §cplayer.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline").equals("")) {
			messagesYml.set("Party.Command.General.ErrorPlayerNotOnline", "§cThis §cplayer §cis §cnot §conline.");
		}
		if (messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").equals("")) {
			messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
					"§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players.");
		}
		if (messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty").equals("")) {
			messagesYml.set("Party.Command.General.PlayerHasLeftTheParty",
					"§bThe §bplayer §6[PLAYER] §bhas §bleft §bthe §bparty.");
		}
		if (messagesYml.getString("Party.Command.General.ServerSwitched").equals("")) {
			messagesYml.set("Party.Command.General.ServerSwitched",
					"§bThe §bparty §bhas §bjoined §bthe §bServer §e[SERVER]§b.");
		}
		if (messagesYml.getString("Party.Command.Chat.Prefix").equals("")) {
			messagesYml.set("Party.Command.Chat.Prefix", "§7[§5PartyChat§7] ");
		}
		if (messagesYml.getString("Party.Command.Chat.ContentColor").equals("")) {
			messagesYml.set("Party.Command.Chat.ContentColor", "§7");
		}
		if (messagesYml.getString("Party.Command.Chat.PartyChatOutput").equals("")) {
			messagesYml.set("Party.Command.Chat.PartyChatOutput", "§e[SENDERNAME]§5:[MESSAGE_CONTENT]");
		}
		if (messagesYml.getString("Party.Command.Chat.ErrorNoMessage").equals("")) {
			messagesYml.set("Party.Command.Chat.ErrorNoMessage", "§5You need to give a message");
		}
		if (messagesYml.getString("Party.Command.Info.Leader").equals("")) {
			messagesYml.set("Party.Command.Info.Leader", "§3Leader§7: §5[LEADER]");
		}
		if (messagesYml.getString("Party.Command.Info.Players").equals("")) {
			messagesYml.set("Party.Command.Info.Players", "§8Players§7: §b");
		}
		if (messagesYml.getString("Party.Command.Info.PlayersCut").equals("")) {
			messagesYml.set("Party.Command.Info.PlayersCut", "§7, §b");
		}
		if (messagesYml.getString("Party.Command.Info.Empty").equals("")) {
			messagesYml.set("Party.Command.Info.Empty", "empty");
		}
		if (messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").equals("")) {
			messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
					"§7You §7are §7not §7allowed §7to §7invite §7yourself.");
		}
		if (messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").equals("")) {
			messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
					"§cYou §ccan´t §cinvite §cthis §cplayer §cinto §cyour §cParty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInAParty",
					"§cThis §cplayer §cis §calready §cin §cyour §cparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInAParty",
					"§cThis §cplayer §cis §calready §cin §cyour §cparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
					"§cThe §cplayer §e[PLAYER] §cis §calready §cinvited §cinto §cyour §cparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").equals("")) {
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"§cThe §cMax §csize §cof §ca §cparty §cis §c[MAXPLAYERSINPARTY]");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitedPlayer").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitedPlayer", "§6[PLAYER] §bwas §binvited §bto §byour §bparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
					"§5You §5were §5invited §5to §5the §5party §5of §6[PLAYER]§5!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"§5Join §5the §5party §5by §5using §5the §5command §6/Party §6join §6[PLAYER]!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER", "Click here to join the party");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
					"§5The invitation of the Party from §6[PLAYER] §5is §5timed §5out!");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
					"§5The player§6 [PLAYER] §5has §5not §5accepted §5your §5invitation!");
		}
		if (messagesYml.getString("Party.Command.Join.AlreadyInAPartyError").equals("")) {
			messagesYml.set("Party.Command.Join.AlreadyInAPartyError",
					"§cYou §care §calready §cin §ca §cparty. §cUse §6/party leave §cto §cleave §cthis §cparty.");
		}
		if (messagesYml.getString("Party.Command.Join.PlayerHasNoParty").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasNoParty", "§cThis §cplayer §chas §cno §cparty.");
		}
		if (messagesYml.getString("Party.Command.Join.PlayerHasJoinend").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasJoinend", "§bThe §bplayer §6[PLAYER] §bjoined §bthe §bparty.");
		}
		if (messagesYml.getString("Party.Command.Join.ErrorNoInvatation").equals("")) {
			messagesYml.set("Party.Command.Join.ErrorNoInvatation", "§cYou §7can´t §cjoin §cthis §cparty.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfTheParty").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfTheParty",
					"§bYou §bhave §bkicked §bthe §bplayer §6[PLAYER] §bout §bof §bthe §bparty.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers",
					"§bThe §bplayer §6[PLAYER] §bwas §bkicked §bout §bof §bparty §bparty.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
					"§bYou §bhave §bbeen §bkicked §bout §bof §bparty.");
		}
		if (messagesYml.getString("Party.Command.Leader.SenderEqualsGivenPlayer").equals("")) {
			messagesYml.set("Party.Command.Leader.SenderEqualsGivenPlayer",
					"§7You §7cannot §7make §7yourself  §7to §7the §7new §7party §7leader");
		}
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leader.NewLeaderIs", "§7The §7new §7party §7leader §7is §6[NEWLEADER]");
		}
		if (messagesYml.getString("Party.Command.Leave.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leave.NewLeaderIs",
					"§bThe §bLeader §bhas §bleft §bthe §Party. §bThe §bnew §bLeader §bis §e[NEWLEADER].");
		}
		if (messagesYml.getString("Party.Command.Leave.YouLeftTheParty").equals("")) {
			messagesYml.set("Party.Command.Leave.YouLeftTheParty", "§bYou §bleft §byour §bparty.");
		}
		if (messagesYml.getString("Friends.General.Prefix").equals("")) {
			messagesYml.set("Friends.General.Prefix", "§8[§5§lFriends§8]");
		}
		if (messagesYml.getString("Friends.General.HelpBegin").equals("")) {
			messagesYml.set("Friends.General.HelpBegin",
					"§8§m-------------------" + ChatColor.RESET + "§8[§5§lFriends§8]§m-------------------");
		}
		if (messagesYml.getString("Friends.General.HelpEnd").equals("")) {
			messagesYml.set("Friends.General.HelpEnd", "§8§m-----------------------------------------------");
		}
		if (messagesYml.getString("Friends.General.CommandNotFound").equals("")) {
			messagesYml.set("Friends.General.CommandNotFound", " §7The §7Command §7doesn´t §7exist.");
		}
		if (messagesYml.getString("Friends.General.PlayerIsOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsOffline",
					" §7The Player §e[PLAYER] §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him");
		}
		if (messagesYml.getString("Friends.General.NotAFriendOfOrOffline").equals("")) {
			messagesYml.set("Friends.General.NotAFriendOfOrOffline",
					" §7The Player §e[PLAYER] §7is §7not §7online §7or §7you §7bare §7not §7a §7friend §7of §7him");
		}
		if (messagesYml.getString("Friends.General.NoFriendGiven").equals("")) {
			messagesYml.set("Friends.General.NoFriendGiven", " §7You §7need §7to §7give §7a §7friend");
		}
		if (messagesYml.getString("Friends.General.NoPlayerGiven").equals("")) {
			messagesYml.set("Friends.General.NoPlayerGiven", " §7You §7need §7to §7give §7a §7player");
		}
		if (messagesYml.getString("Friends.General.TooManyArguments").equals("")) {
			messagesYml.set("Friends.General.TooManyArguments", "§7 Too many arguments");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOffline", " §7Your friend §e[PLAYER] §7is §7now §coffline.");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOnline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOnline", " §7The friend §e[PLAYER]§7 is §7now §aonline.");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoin").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoin",
					" §7You §7have §7friend §7requests §7from: [FRIENDREQUESTS]");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColor").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoinColor", "§e");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColorComma").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoinColorComma", "§7");
		}
		if (messagesYml.getString("Friends.General.DoesnotExist").equals("")) {
			messagesYml.set("Friends.General.DoesnotExist", " §7The player §e[PLAYER] §7doesn´t §7exist");
		}
		if (messagesYml.getString("Friends.CommandUsage.List").equals("")) {
			messagesYml.set("Friends.CommandUsage.List", "§8/§5friend list §8- §7Lists §7all §7of §7your §7friends");
		}
		if (messagesYml.getString("Friends.CommandUsage.MSG").equals("")) {
			messagesYml.set("Friends.CommandUsage.MSG", "§8/§5friend §5msg §5[name §5of §5the §5friend] §5[message]"
					+ ChatColor.RESET + " §8- §7send §7a §7friend §7a §7message");
		}
		if (messagesYml.getString("Friends.CommandUsage.ADD").equals("")) {
			messagesYml.set("Friends.CommandUsage.ADD",
					"§8/§5friend §5add §5[name §5of §5the §5player]" + ChatColor.RESET + " §8- §7Add §7a §7friend");
		}
		if (messagesYml.getString("Friends.CommandUsage.Accept").equals("")) {
			messagesYml.set("Friends.CommandUsage.Accept", "§8/§5friend §5accept §5[name §5of §5the §5player]"
					+ ChatColor.RESET + " §8- §7accept §7a §7friend request");
		}
		if (messagesYml.getString("Friends.CommandUsage.Deny").equals("")) {
			messagesYml.set("Friends.CommandUsage.Deny", "§8/§5friend §5deny §5[name §5of §5the §5player]"
					+ ChatColor.RESET + " §8- §7deny §7a §7friend §7request");
		}
		if (messagesYml.getString("Friends.CommandUsage.Remove").equals("")) {
			messagesYml.set("Friends.CommandUsage.Remove", "§8/§5friend §5remove §5[name §5of §5the §5friend]"
					+ ChatColor.RESET + " §8- §7removes §7a §7friend");
		}
		if (messagesYml.getString("Friends.CommandUsage.Settings").equals("")) {
			messagesYml.set("Friends.CommandUsage.Settings", "§8/§5friend §5settings " + ChatColor.RESET
					+ "§8- §7settings §7of §7the §7party- §7and §7friendsystem");
		}
		if (messagesYml.getString("Friends.CommandUsage.Jump").equals("")) {
			messagesYml.set("Friends.CommandUsage.Jump",
					"§8/§5friend §5jump [name of the §5friend]" + ChatColor.RESET + "§8- §7Jump §7to §7a §7friend");
		}
		if (messagesYml.getString("Friends.Command.Accept.NowFriends").equals("")) {
			messagesYml.set("Friends.Command.Accept.NowFriends", " §7You and §e[PLAYER] §7are §7now §7friends");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
					" §7You didn´t receive a §7friend §7request §7from §e[PLAYER]§7.");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualreceiver").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorSenderEqualreceiver",
					" §7You cannot §7write §7to §7yourself.");
		}
		if (messagesYml.getString("Friends.Command.Add.SenderEqualsreceiver").equals("")) {
			messagesYml.set("Friends.Command.Add.SenderEqualsreceiver",
					" §7You §7cannot §7send §7yourself §7a §7friend §7request.");
		}
		if (messagesYml.getString("Friends.Command.Add.FriendRequestFromreceiver").equals("")) {
			messagesYml.set("Friends.Command.Add.FriendRequestFromreceiver",
					" §7The player §e[PLAYER] §7has §7already §7send §7you §7a §7friend §7request.");
		}
		if (messagesYml.getString("Friends.Command.Add.FriendRequestreceived").equals("")) {
			messagesYml.set("Friends.Command.Add.FriendRequestreceived",
					"§7 You have received a friend request from §e[PLAYER]§7.");
		}
		if (messagesYml.getString("Friends.Command.Add.ClickHere").equals("")) {
			messagesYml.set("Friends.Command.Add.ClickHere", "§aClick here to accept the friendship request");
		}
		if (messagesYml.getString("Friends.Command.Add.SendedAFriendRequest").equals("")) {
			messagesYml.set("Friends.Command.Add.SendedAFriendRequest",
					"§7 The player §e[PLAYER]§7 was §7send §7a §7friend §7request");
		}
		if (messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").equals("")) {
			messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
					" §7You §7cannot §7send §7the §7player §e[PLAYER] §7a §7friend §7request");
		}
		if (messagesYml.getString("Friends.Command.Add.HowToAccept").equals("")) {
			messagesYml.set("Friends.Command.Add.HowToAccept",
					" §7Accept the friend request with §6/friend §6accept §6[PLAYER]§7.");
		}
		if (messagesYml.getString("Friends.Command.Add.AlreadyFriends").equals("")) {
			messagesYml.set("Friends.Command.Add.AlreadyFriends", "§7 You and §e[PLAYER] §7are §7already §7friends.");
		}
		if (messagesYml.getString("Friends.Command.Deny.HasDenied").equals("")) {
			messagesYml.set("Friends.Command.Deny.HasDenied", " §7You have denied the friend request of §e[PLAYER].");
		}
		if (messagesYml.getString("Friends.Command.Deny.NoFriendRequest").equals("")) {
			messagesYml.set("Friends.Command.Deny.NoFriendRequest",
					" §7You didn´t receive a §7friend §7request §7from §e[PLAYER]§7.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
					" §7Now §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
					" §7Now §7you §7can §7get §7invited §conly §7by §7by your friends §7into §7their §7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests",
					" §7Now §7you §7are §cnot §7gone §7receive §7friend §7requests §7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests",
					" §7Now §7you §7are §agone §7receive §7friend §7requests §7from §7everyone");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveMessages",
					" §7Now §7you §7are §cnot §7gone §7receive §7messages §7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
					" §7Now §7you §7will §7be §7shown §7as §aonline");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
					" §7Now §7you §7will §7be §7shown §7as §coffline");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowNoMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowNoMessages",
					" §7Now §7you §7are §cnot §7gone §7receive §7messages §7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowMessages",
					" §7Now §7you §7are §agone §7receive §7message §7from §7everyone");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
					" §7Now §7your §7friends §7can §ajump §7to §7you");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
					" §7Now §7your §7friends §7can §cnot §7jump §7to §7you");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests",
					" §7At §7the moment §7you §7are §cnot §7gone §7receive §7friend §7request");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests",
					" §7At §7the moment §7you §7are §7gone §7receive §7friend §7requests §7from §aeveryone");
		}
		if (messagesYml.getString("Friends.Command.Settings.SplitLine").equals("")) {
			messagesYml.set("Friends.Command.Settings.SplitLine",
					"§8§m-----------------------------------------------");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
					" §7At §7the moment §7you §7can §7get §7invited §7by §aevery §7player §7into §7his §7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" §7At §7the moment §7you §7can §7get §7invited §aonly §7by §7by your friends §7into §7their §7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover", "Click here to change this setting.");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
					" §7Change §7this §7setting §7with §6/friend §6settings §6friendrequests");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
					" §7Ändere §7diese §7Einstellung §7mit §6/friend §6settings §6Party");
		}
		if (messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer", " §7You §7bare §7already §7on §7this §7server");
		}
		if (messagesYml.getString("Friends.Command.Jump.JoinedTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.JoinedTheServer",
					" §7Now §7you §7are §7on §7the §7same §7server, §7like §7the §7player §e[PLAYER]");
		}
		if (messagesYml.getString("Friends.Command.Jump.CanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Jump.CanNotJump", " §7You §7cannot §jump to §7this §7person");
		}
		if (messagesYml.getString("Friends.Command.List.NoFriendsAdded").equals("")) {
			messagesYml.set("Friends.Command.List.NoFriendsAdded",
					" §7Till now, §7you don´t §7have §7added §7friends.");
		}
		if (messagesYml.getString("Friends.Command.List.OfflineTitle").equals("")) {
			messagesYml.set("Friends.Command.List.OfflineTitle", "(offline)");
		}
		if (messagesYml.getString("Friends.Command.List.OfflineColor").equals("")) {
			messagesYml.set("Friends.Command.List.OfflineColor", ChatColor.RED + "");
		}
		if (messagesYml.getString("Friends.Command.List.OnlineTitle").equals("")) {
			messagesYml.set("Friends.Command.List.OnlineTitle", "(online)");
		}
		if (messagesYml.getString("Friends.Command.List.OnlineColor").equals("")) {
			messagesYml.set("Friends.Command.List.OnlineColor", ChatColor.GREEN + "");
		}
		if (messagesYml.getString("Friends.Command.List.FriendsList").equals("")) {
			messagesYml.set("Friends.Command.List.FriendsList", " §7This §7are §7your §7friends:");
		}
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").equals("")) {
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " §7You cannot write to this player.");
		}
		if (messagesYml.getString("Friends.Command.MSG.PlayerAndMessageMissing").equals("")) {
			messagesYml.set("Friends.Command.MSG.PlayerAndMessageMissing", " §8- §7send §7a §7friend §7a §7message");
		}
		if (messagesYml.getString("Friends.Command.MSG.ColorOfMessage").equals("")) {
			messagesYml.set("Friends.Command.MSG.ColorOfMessage", " §7");
		}
		if (messagesYml.getString("Friends.Command.MSG.SendedMessage").equals("")) {
			messagesYml.set("Friends.Command.MSG.SendedMessage", " §e[SENDER]§5-> §e[PLAYER]§7:[CONTENT]");
		}
		if (messagesYml.getString("Friends.Command.Remove.Removed").equals("")) {
			messagesYml.set("Friends.Command.Remove.Removed", "§7 You removed the player §e[PLAYER]§7.");
		}
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesYml, file);
		return messagesYml;
	}
}
