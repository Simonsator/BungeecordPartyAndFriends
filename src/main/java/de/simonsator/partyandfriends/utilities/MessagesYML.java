package de.simonsator.partyandfriends.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * This class loads the Messages.yml
 * 
 * @author Simonsator
 * @version 1.0.1
 */
public class MessagesYML {
	/**
	 * Adds missing lines in the Messages.yml
	 * 
	 * @author Simonsator
	 * @version 1.0.1
	 * @param pType
	 *            The name of the language
	 * @return Returns the Messages.yml variable
	 * @throws IOException
	 *             Can throw a {@link SQLException}
	 */
	public static Configuration loadMessages(String pType) throws IOException {
		File file = null;
		Configuration messagesYml = null;
		switch (pType.toLowerCase()) {
		case "own":
			file = new File(Main.getInstance().getDataFolder().getPath(), "Messages.yml");
			if (!file.exists()) {
				file.createNewFile();
			}
			messagesYml = loadEnglishMessages(ConfigurationProvider.getProvider(YamlConfiguration.class).load(file));
			break;
		case "english":
			messagesYml = loadEnglishMessages(new Configuration());
			break;
		default:
			messagesYml = loadGermanMessages(new Configuration());
			break;
		}
		messagesYml = loadEnglishMessages(messagesYml);
		if (pType.equalsIgnoreCase("own"))
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesYml, file);
		return replaceColorCodes(messagesYml);
	}

	private static Configuration loadEnglishMessages(Configuration messagesYml) {

		if (messagesYml.getString("Party.Error.CommandNotFound").equals("")) {
			messagesYml.set("Party.Error.CommandNotFound", "&cThis command doesn't exist!");
		}
		if (messagesYml.getString("Party.CommandUsage.Join").equals("")) {
			messagesYml.set("Party.CommandUsage.Join", "&8/&5Party " + "join <Name>" + " &8- &7Join &7a &7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Invite").equals("")) {
			messagesYml.set("Party.CommandUsage.Invite",
					"&8/&5Party " + "invite <player>" + " &8- &7Invite &7a &7player &7into &7your &7Party");
		}
		if (messagesYml.getString("Party.CommandUsage.List").equals("")) {
			messagesYml.set("Party.CommandUsage.List",
					"&8/&5Party " + "list" + " &8- &7List &7all &7players &7who &7are &7in &7the &7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Chat").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party " + "chat <message>" + " &8- &7Send &7all &7players &7in &7the &7party &7a &7message");
		}
		if (messagesYml.getString("Party.CommandUsage.Leave").equals("")) {
			messagesYml.set("Party.CommandUsage.Leave", "&8/&5Party " + "leave" + " &8- &7Leave the party");
		}
		if (messagesYml.getString("Party.CommandUsage.Kick").equals("")) {
			messagesYml.set("Party.CommandUsage.Kick",
					"&8/&5Party " + "kick <player>" + " &8- &7Kicks &7a &7player &7out &7of &7the &7party");
		}
		if (messagesYml.getString("Party.CommandUsage.Leader").equals("")) {
			messagesYml.set("Party.CommandUsage.Leader", "&8/&5Party " + "leader &5<player>"
					+ " &8- &7Makes &7another &7player &7to &7the &7party &7leader");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoParty", "&5You need to be in a party");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNotPartyLeader", "&cYou &care &cnot &cthe &cparty &cleader.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty",
					"&cThe &cplayer [PLAYER] &cis &cnot &cin &cthe &cParty.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoPlayer").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoPlayer", "&cYou &cneed &cto &cgive &ca &cplayer.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline").equals("")) {
			messagesYml.set("Party.Command.General.ErrorPlayerNotOnline", "&cThis &cplayer &cis &cnot &conline.");
		}
		if (messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").equals("")) {
			messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
					"&5The &5party &5was &5dissolved &5because &5of &5too &5less &5players.");
		}
		if (messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty").equals("")) {
			messagesYml.set("Party.Command.General.PlayerHasLeftTheParty",
					"&bThe &bplayer &6[PLAYER] &bhas &bleft &bthe &bparty.");
		}
		if (messagesYml.getString("Party.Command.General.ServerSwitched").equals("")) {
			messagesYml.set("Party.Command.General.ServerSwitched",
					"&bThe &bparty &bhas &bjoined &bthe &bServer &e[SERVER]&b.");
		}
		if (messagesYml.getString("Party.Command.Chat.ErrorNoMessage").equals("")) {
			messagesYml.set("Party.Command.Chat.ErrorNoMessage", "&5You need to give a message");
		}
		if (messagesYml.getString("Party.Command.Info.Empty").equals("")) {
			messagesYml.set("Party.Command.Info.Empty", "empty");
		}
		if (messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").equals("")) {
			messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
					"&7You &7are &7not &7allowed &7to &7invite &7yourself.");
		}
		if (messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").equals("")) {
			messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
					"&cYou &ccan't &cinvite &cthis &cplayer &cinto &cyour &cParty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInAParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInAParty", "&cThis &cplayer &cis &calready &cin &ca &cparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
					"&cThe &cplayer &e[PLAYER] &cis &calready &cinvited &cinto &cyour &cparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").equals("")) {
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&cThe &cMax &csize &cof &ca &cparty &cis &c[MAXPLAYERSINPARTY]");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitedPlayer").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitedPlayer", "&6[PLAYER] &bwas &binvited &bto &byour &bparty.");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
					"&5You &5were &5invited &5to &5the &5party &5of &6[PLAYER]&5!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Join &5the &5party &5by &5using &5the &5command &6/Party &6join &6[PLAYER]!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER", "Click here to join the party");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
					"&5The invitation of the Party from &6[PLAYER] &5is &5timed &5out!");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
					"&5The player&6 [PLAYER] &5has &5not &5accepted &5your &5invitation!");
		}
		if (messagesYml.getString("Party.Command.Join.PlayerHasNoParty").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasNoParty", "&cThis &cplayer &chas &cno &cparty.");
		}
		if (messagesYml.getString("Party.Command.Join.AlreadyInAPartyError").equals("")) {
			messagesYml.set("Party.Command.Join.AlreadyInAPartyError",
					"&cYou &care &calready &cin &ca &cparty. &6use &6/party leave &cto &cleave &this &cParty.");
		}
		if (messagesYml.getString("Party.Command.Join.PlayerHasJoinend").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasJoinend", "&bThe &bplayer &6[PLAYER] &bjoined &bthe &bparty.");
		}
		if (messagesYml.getString("Party.Command.Join.ErrorNoInvatation").equals("")) {
			messagesYml.set("Party.Command.Join.ErrorNoInvatation", "&cYou &7can't &cjoin &cthis &cparty.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfTheParty").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfTheParty",
					"&bThe &bplayer &6[PLAYER] &bwas &bkicked &bout &bof &bparty &bparty.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
					"&bYou &bhave &bbeen &bkicked &bout &bof &bparty.");
		}
		if (messagesYml.getString("Party.Command.Leader.SenderEqualsGivenPlayer").equals("")) {
			messagesYml.set("Party.Command.Leader.SenderEqualsGivenPlayer",
					"&7You &7cannot &7make &7yourself  &7to &7the &7new &7party &7leader");
		}
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leader.NewLeaderIs", "&7The &7new &7party &7leader &7is &6[NEWLEADER]");
		}
		if (messagesYml.getString("Party.Command.Leave.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leave.NewLeaderIs",
					"&bThe &bLeader &bhas &bleft &bthe &bParty. &bThe &bnew &bLeader &bis &e[NEWLEADER].");
		}
		if (messagesYml.getString("Party.Command.Leave.YouLeftTheParty").equals("")) {
			messagesYml.set("Party.Command.Leave.YouLeftTheParty", "&bYou &bleft &byour &bparty.");
		}
		if (messagesYml.getString("Friends.General.Prefix").equals("")) {
			messagesYml.set("Friends.General.Prefix", "&8[&5&lFriends&8]");
		}
		if (messagesYml.getString("Friends.General.CommandNotFound").equals("")) {
			messagesYml.set("Friends.General.CommandNotFound", " &7The &7Command &7doesn't &7exist.");
		}
		if (messagesYml.getString("Friends.General.PlayerIsOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsOffline",
					" &7The Player &e[PLAYER] &7is &7not &7online &7or &7you &7are &7not &7a &7friend &7of &7him");
		}
		if (messagesYml.getString("Friends.General.NotAFriendOfOrOffline").equals("")) {
			messagesYml.set("Friends.General.NotAFriendOfOrOffline",
					" &7The Player &e[PLAYER] &7is &7not &7online &7or &7you &7are &7not &7a &7friend &7of &7him");
		}
		if (messagesYml.getString("Friends.General.NoFriendGiven").equals("")) {
			messagesYml.set("Friends.General.NoFriendGiven", " &7You &7need &7to &7give &7a &7friend");
		}
		if (messagesYml.getString("Friends.General.NoPlayerGiven").equals("")) {
			messagesYml.set("Friends.General.NoPlayerGiven", " &7You &7need &7to &7give &7a &7player");
		}
		if (messagesYml.getString("Friends.General.TooManyArguments").equals("")) {
			messagesYml.set("Friends.General.TooManyArguments", "&7 Too many arguments");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOffline", " &7Your friend &e[PLAYER] &7is &7now &coffline.");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOnline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOnline", " &7The friend &e[PLAYER]&7 is &7now &aonline.");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoin").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoin",
					" &7You &7have &7friend &7requests &7from: [FRIENDREQUESTS]");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColor").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoinColor", "&e");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColorComma").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoinColorComma", "&7");
		}
		if (messagesYml.getString("Friends.General.DoesNotExist").equals("")) {
			messagesYml.set("Friends.General.DoesNotExist", " &7The player &e[PLAYER] &7doesn't &7exist");
		}
		if (messagesYml.getString("Friends.General.GivenPlayerEqualsSender").equals("")) {
			messagesYml.set("Friends.General.GivenPlayerEqualsSender",
					" &7You cannot give you self as player argument.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowAllPlayers").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowAllPlayers", " &aNow you can see all players.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer",
					" &eNow only only friends and people of the server &eteam will be shown.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriends").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriends", " &6Now you can see only Friends.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer",
					" &5Now you can see only players from the server &5team.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowNobody").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowNobody", " &cHide all players.");
		}
		if (messagesYml.getString("Friends.CommandUsage.List").equals("")) {
			messagesYml.set("Friends.CommandUsage.List", "&8/&5friend list &8- &7Lists &7all &7of &7your &7friends");
		}
		if (messagesYml.getString("Friends.CommandUsage.MSG").equals("")) {
			messagesYml.set("Friends.CommandUsage.MSG", "&8/&5friend &5msg &5[name &5of &5the &5friend] &5[message]"
					+ "&r" + " &8- &7send &7a &7friend &7a &7message");
		}
		if (messagesYml.getString("Friends.CommandUsage.ADD").equals("")) {
			messagesYml.set("Friends.CommandUsage.ADD",
					"&8/&5friend &5add &5[name &5of &5the &5player]" + "&r" + " &8- &7Add &7a &7friend");
		}
		if (messagesYml.getString("Friends.CommandUsage.Accept").equals("")) {
			messagesYml.set("Friends.CommandUsage.Accept",
					"&8/&5friend &5accept &5[name &5of &5the &5player]" + "&r" + " &8- &7accept &7a &7friend request");
		}
		if (messagesYml.getString("Friends.CommandUsage.Deny").equals("")) {
			messagesYml.set("Friends.CommandUsage.Deny",
					"&8/&5friend &5deny &5[name &5of &5the &5player]" + "&r" + " &8- &7deny &7a &7friend &7request");
		}
		if (messagesYml.getString("Friends.CommandUsage.Remove").equals("")) {
			messagesYml.set("Friends.CommandUsage.Remove",
					"&8/&5friend &5remove &5[name &5of &5the &5friend]" + "&r" + " &8- &7removes &7a &7friend");
		}
		if (messagesYml.getString("Friends.CommandUsage.Jump").equals("")) {
			messagesYml.set("Friends.CommandUsage.Jump",
					"&8/&5friend &5jump [name of the &5friend]" + "&r" + "&8- &7Jump &7to &7a &7friend");
		}
		if (messagesYml.getString("Friends.CommandUsage.Settings").equals("")) {
			messagesYml.set("Friends.CommandUsage.Settings",
					"&8/&5friend &5settings " + "&r" + "&8- &7Change &7the &7settings");
		}
		if (messagesYml.getString("Friends.Command.Accept.NowFriends").equals("")) {
			messagesYml.set("Friends.Command.Accept.NowFriends", " &7You and &e[PLAYER] &7are &7now &7friends");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
					" &7You didn't receive a &7friend &7request &7from &e[PLAYER]&7.");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualreceiver").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorSenderEqualreceiver",
					" &7You cannot &7write &7to &7yourself.");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorAlreadySend").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorAlreadySend",
					"&7 You already have sent &7the &7player &e[PLAYER] &7a &7friend &7request.");
		}
		if (messagesYml.getString("Friends.Command.Add.SenderEqualsreceiver").equals("")) {
			messagesYml.set("Friends.Command.Add.SenderEqualsreceiver",
					" &7You &7cannot &7send &7yourself &7a &7friend &7request.");
		}
		if (messagesYml.getString("Friends.Command.Add.FriendRequestFromreceiver").equals("")) {
			messagesYml.set("Friends.Command.Add.FriendRequestFromreceiver",
					" &7The player &e[PLAYER] &7has &7already &7send &7you &7a &7friend &7request.");
		}
		if (messagesYml.getString("Friends.Command.Add.FriendRequestreceived").equals("")) {
			messagesYml.set("Friends.Command.Add.FriendRequestreceived",
					"&7 You have received a friend request from &e[PLAYER]&7.");
		}
		if (messagesYml.getString("Friends.Command.Add.ClickHere").equals("")) {
			messagesYml.set("Friends.Command.Add.ClickHere", "&aClick here to accept the friendship request");
		}
		if (messagesYml.getString("Friends.Command.Add.SendedAFriendRequest").equals("")) {
			messagesYml.set("Friends.Command.Add.SendedAFriendRequest",
					"&7 The player &e[PLAYER]&7 was &7send &7a &7friend &7request");
		}
		if (messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").equals("")) {
			messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
					" &7You &7cannot &7send &7the &7player &e[PLAYER] &7a &7friend &7request");
		}
		if (messagesYml.getString("Friends.Command.Add.HowToAccept").equals("")) {
			messagesYml.set("Friends.Command.Add.HowToAccept",
					" &7Accept the friend request with &6/friend &6accept &6[PLAYER]&7.");
		}
		if (messagesYml.getString("Friends.Command.Add.AlreadyFriends").equals("")) {
			messagesYml.set("Friends.Command.Add.AlreadyFriends", "&7 You and &e[PLAYER] &7are &7already &7friends.");
		}
		if (messagesYml.getString("Friends.Command.Deny.HasDenied").equals("")) {
			messagesYml.set("Friends.Command.Deny.HasDenied", " &7You have denied the friend request of &e[PLAYER].");
		}
		if (messagesYml.getString("Friends.Command.Deny.NoFriendRequest").equals("")) {
			messagesYml.set("Friends.Command.Deny.NoFriendRequest",
					" &7You didn't receive a &7friend &7request &7from &e[PLAYER]&7.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
					" &7Now &7you &7can &7get &7invited &7by &aevery &7player &7into &7his &7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
					" &7Now &7you &7can &7get &7invited &conly &7by &7by your friends &7into &7their &7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests",
					" &7Now &7you &7are &cnot &7gone &7receive &7friend &7requests &7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests",
					" &7Now &7you &7are &agone &7receive &7friend &7requests &7from &7everyone");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveMessages",
					" &7Now &7you &7are &cnot &7gone &7receive &7messages &7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
					" &7Now &7you &7will &7be &7shown &7as &aonline");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
					" &7Now &7you &7will &7be &7shown &7as &coffline");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowNoMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowNoMessages",
					" &7Now &7you &7are &cnot &7gone &7receive &7messages &7anymore");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowMessages",
					" &7Now &7you &7are &agone &7receive &7message &7from &7everyone");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
					" &7Now &7your &7friends &7can &ajump &7to &7you");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
					" &7Now &7your &7friends &7can &cnot &7jump &7to &7you");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests",
					" &7At &7the moment &7you &7are &cnot &7gone &7receive &7friend &7request");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests",
					" &7At &7the moment &7you &7are &7gone &7receive &7friend &7requests &7from &aeveryone");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
					" &7At &7the moment &7you &7can &7get &7invited &7by &aevery &7player &7into &7his &7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7At &7the moment &7you &7can &7get &7invited &aonly &7by &7by your friends &7into &7their &7Party.");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover", "Click here to change this setting.");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
					" &7Change &7this &7setting &7with &6/friend &6settings &6friendrequests");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
					" &7Change &7this &7setting &7with &6/friend &6settings &6Party");
		}
		if (messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer", " &7You &7are &7already &7on &7this &7server");
		}
		if (messagesYml.getString("Friends.Command.Jump.JoinedTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.JoinedTheServer",
					" &7Now &7you &7are &7on &7the &7same &7server, &7like &7the &7player &e[PLAYER]");
		}
		if (messagesYml.getString("Friends.Command.Jump.CanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Jump.CanNotJump", " &7You &7cannot &7jump to &7this &7person");
		}
		if (messagesYml.getString("Friends.Command.List.NoFriendsAdded").equals("")) {
			messagesYml.set("Friends.Command.List.NoFriendsAdded",
					" &7Till now, &7you don't &7have &7added &7friends.");
		}
		if (messagesYml.getString("Friends.Command.List.FriendsList").equals("")) {
			messagesYml.set("Friends.Command.List.FriendsList", " &7These &7are &7your &7friends:");
		}
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").equals("")) {
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7You cannot write to this player.");
		}
		if (messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").equals("")) {
			messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou", "&7 No player ever wrote to you.");
		}
		if (messagesYml.getString("Friends.Command.MSG.PlayerAndMessageMissing").equals("")) {
			messagesYml.set("Friends.Command.MSG.PlayerAndMessageMissing", " &7You &7need &7to &7give &7a &7message.");
		}
		if (messagesYml.getString("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin").equals("")) {
			messagesYml.set("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin",
					" &7The &7player &7will &7receive &7the &7message, &7when &7he &7goes &7online.");
		}
		if (messagesYml.getString("Friends.Command.Remove.Removed").equals("")) {
			messagesYml.set("Friends.Command.Remove.Removed", "&7 You removed the friend &e[PLAYER]&7.");
		}
		loadSharedMessages(messagesYml);
		return messagesYml;
	}

	private static Configuration loadGermanMessages(Configuration messagesYml) {
		if (messagesYml.getString("Friends.General.CommandNotFound").equals("")) {
			messagesYml.set("Friends.General.CommandNotFound", " &7Das &7Kommando &7existiert &7nicht.");
		}
		if (messagesYml.getString("Friends.General.PlayerIsOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsOffline",
					" &7Der Spieler &e[PLAYER] &7ist &7nicht &7Online &7oder &7du &7bist &7nicht &7mit &7ihm &7befreundet");
		}
		if (messagesYml.getString("Friends.General.NoPlayerGiven").equals("")) {
			messagesYml.set("Friends.General.NoPlayerGiven", " &7Du musst einen Spieler angeben");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOnline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOnline", " &e[PLAYER] &7ist &7jetzt &aOnline");
		}
		if (messagesYml.getString("Friends.General.RequestInfoOnJoin").equals("")) {
			messagesYml.set("Friends.General.RequestInfoOnJoin",
					" &7Freundschaftsanfragen &7stehen &7von &7den &7folgenden &7Spielern &7aus: [FRIENDREQUESTS]");
		}
		if (messagesYml.getString("Friends.General.PlayerIsNowOffline").equals("")) {
			messagesYml.set("Friends.General.PlayerIsNowOffline", " &7Der Freund &e[PLAYER] &7ist &7nun &cOffline.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowAllPlayers").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowAllPlayers", " &aDir &awerden &ajetzt &aalle &aSpieler &aangezeigt.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer",
					" &eDir &ewerden &ejetzt &enur &enoch &eFreunde &eund &eLeute &evom &eServer &eangezeigt.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriends").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriends",
					" &6Dir &6werden &6jetzt &6nur &6noch &6deine &6Freunde &6angezeigt.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer",
					" &5Dir &5werden &5jetzt &5nur &5noch &5Spieler &5vom &5Server &5Team &5angezeigt.");
		}
		if (messagesYml.getString("Friends.GUI.Hide.ShowNobody").equals("")) {
			messagesYml.set("Friends.GUI.Hide.ShowNobody",
					" &cDir &cwerden &cjetzt &ckeine &cSpieler &cmehr &cangezeigt.");
		}
		if (messagesYml.getString("Friends.CommandUsage.List").equals("")) {
			messagesYml.set("Friends.CommandUsage.List",
					"&8/&5friend list" + "&r" + " &8- &7Listet &7deine &7Freunde &7auf");
		}
		if (messagesYml.getString("Friends.CommandUsage.MSG").equals("")) {
			messagesYml.set("Friends.CommandUsage.MSG", "&8/&5friend &5msg &5[Name &5des &5Freundes] &5[Nachricht]"
					+ "&r" + " &8- &7schickt &7einem &7Freund &7eine &7Private Nachricht");
		}
		if (messagesYml.getString("Friends.CommandUsage.ADD").equals("")) {
			messagesYml.set("Friends.CommandUsage.ADD",
					"&8/&5friend &5add &5[Name &5des &5Spielers]" + "&r" + " &8- &7Fügt &7einen &7Freund &7hinzu");
		}
		if (messagesYml.getString("Friends.CommandUsage.Accept").equals("")) {
			messagesYml.set("Friends.CommandUsage.Accept", "&8/&5friend &5accept &5[Name &5des &5Spielers]" + "&r"
					+ " &8- &7Akzeptiert &7eine &7Freundschaftsanfrage");
		}
		if (messagesYml.getString("Friends.CommandUsage.Deny").equals("")) {
			messagesYml.set("Friends.CommandUsage.Deny", "&8/&5friend &5deny &5[Name &5des &5Spielers]" + "&r"
					+ " &8- &7Lehnt eine &7Freundschaftsanfrage &7ab");
		}
		if (messagesYml.getString("Friends.CommandUsage.Remove").equals("")) {
			messagesYml.set("Friends.CommandUsage.Remove",
					"&8/&5friend &5remove &5[Name &5des &5Spielers]" + "&r" + " &8- &7Entfernt &7einen &7Freund");
		}
		if (messagesYml.getString("Friends.CommandUsage.Jump").equals("")) {
			messagesYml.set("Friends.CommandUsage.Jump",
					"&8/&5friend &5jump [Name des Freundes]" + "&r" + "&8- &7Zu &7einem &7Freund &7springen");
		}
		if (messagesYml.getString("Friends.CommandUsage.Settings").equals("")) {
			messagesYml.set("Friends.CommandUsage.Settings",
					"&8/&5friend &5settings " + "&r" + "&8- &7Ändere die Einstellungen");
		}
		if (messagesYml.getString("Friends.Command.Accept.NowFriends").equals("")) {
			messagesYml.set("Friends.Command.Accept.NowFriends", " &7Du bist jetzt mit &e[PLAYER] &7befreundet");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorAlreadySend").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorAlreadySend",
					" &7Du hast dem Spieler &e[PLAYER] &7schon &7eine &7Freundschaftsanfrage &7gesendet.");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
					"&7 Du hast keine &7Freundschaftsanfrage von &e[PLAYER] &7keine &7erhalten");
		}
		if (messagesYml.getString("Friends.Command.Add.SendedAFriendRequest").equals("")) {
			messagesYml.set("Friends.Command.Add.SendedAFriendRequest",
					"&7 The player &e[PLAYER] &7was &7send &7a &7friend &7request");
		}
		if (messagesYml.getString("Friends.Command.Add.FriendRequestFromreceiver").equals("")) {
			messagesYml.set("Friends.Command.Add.FriendRequestFromreceiver",
					" &7Der Spieler &e[PLAYER] &7hat &7dir &7schon &7eine &7Freundschaftsanfrage &7gesendet.");
		}
		if (messagesYml.getString("Friends.Command.Add.HowToAccept").equals("")) {
			messagesYml.set("Friends.Command.Add.HowToAccept", " &7Nimm sie mit &6/friend accept [PLAYER] &7an");
		}
		if (messagesYml.getString("Friends.Command.Add.ClickHere").equals("")) {
			messagesYml.set("Friends.Command.Add.ClickHere", "&aHier klicken um die Freundschaftsanfrage anzunehmen");
		}
		if (messagesYml.getString("Friends.Command.Add.AlreadyFriends").equals("")) {
			messagesYml.set("Friends.Command.Add.AlreadyFriends", " &7Du bist schon mit &e[PLAYER] &7befreundet");
		}
		if (messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualsReceiver").equals("")) {
			messagesYml.set("Friends.Command.Accept.ErrorSenderEqualsReceiver",
					" &7Du kannst dir nicht selber eine &7Freundschaftsanfrage &7senden");
		}
		if (messagesYml.getString("Friends.General.DoesNotExist").equals("")) {
			messagesYml.set("Friends.General.DoesnotExist", " &7Der Spieler &e[PLAYER] &7exestiert &7nicht");
		}
		if (messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").equals("")) {
			messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
					" &7Du &7kannst &7dem &7Spieler &e[PLAYER] &7keine &7Freundschaftsanfrage &7senden");
		}
		if (messagesYml.getString("Friends.Command.Deny.HasDenied").equals("")) {
			messagesYml.set("Friends.Command.Deny.HasDenied", " &7Du hast die Anfrage von &e[PLAYER] &7abglehnt");
		}
		if (messagesYml.getString("Friends.Command.Jump.CanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Jump.CanNotJump", " &7Du &7kannst &7nicht zu &7dieser &7Person springen");
		}
		if (messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer",
					" &7Du &7bist &7bereits &7auf &7diesem &7Server");
		}
		if (messagesYml.getString("Friends.Command.Jump.JoinedTheServer").equals("")) {
			messagesYml.set("Friends.Command.Jump.JoinedTheServer",
					" &7Du &7bist &7jetzt &7auf &7dem &7gleichen &7Server, &7wie &7der &7Spieler &e[PLAYER]");
		}
		if (messagesYml.getString("Friends.Command.List.FriendsList").equals("")) {
			messagesYml.set("Friends.Command.List.FriendsList", " &7Dies &7sind &7deine &7Freunde:");
		}
		if (messagesYml.getString("Friends.Command.List.NoFriendsAdded").equals("")) {
			messagesYml.set("Friends.Command.List.NoFriendsAdded", " &7Du hast noch keine Freunde &7hinzugefügt.");
		}
		if (messagesYml.getString("Friends.Command.Remove.Removed").equals("")) {
			messagesYml.set("Friends.Command.Remove.Removed", "&7 Du hast den Freund &e[PLAYER] &7entfernt");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests",
					" &7Momentan &7können &7dir &ckeine &7Freundschaftsanfragen &7gesendet &7werden");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &7Party &7Einladungen &7von &ajedem &7gesendet &7werden &7gesendet &7werden");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
					" &7Ändere &7diese &7Einstellung &7mit &6/friend &6settings &6Freundschaftsanfragen");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &cnur &7Party &7Einladungen &7von &7Freunden &7gesendet &7werden");
		}
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.equals("")) {
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &cnur &7Party &7Einladungen &7von &7Freunden &7gesendet &7werden");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
					" &7Ändere &7diese &7Einstellung &7mit &6/friend &6settings &6Party");
		}
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").equals("")) {
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover",
					"Hier klicken um die Einstellung zu ändern.");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Spieler &7in &7eine &7Party &7eingeladen &7werden");
		}

		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
					" &7Du &7kannst &7jetzt &cnur &7noch &7von &7deinen &7Freunden &7in &7eine &7Party &7eingeladen &7werden");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests",
					" &7Du &7kannst &7jetzt &ckeine &7Freundschaftsanfragen &7mehr &7erhalten");
		}

		if (messagesYml.getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Freundschaftsanfragen &7erhalten");
		}

		if (messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
					"Friends.Command.Settings.NowYouWillBeShowAsOnline");
		}

		if (messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
					" &7Du &7wirst &7nun &7als &coffline &7angezeigt");
		}

		if (messagesYml.getString("Friends.Command.Settings.NowNoMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowNoMessages",
					" &7Du &7kannst &7jetzt &ckeine &7Nachrichten &7mehr &7erhalten");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowMessages").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowMessages",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Nachrichten &7erhalten");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
					" &7Freunde &7können &7jetzt &7zu &7dir &aspringen");
		}
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").equals("")) {
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
					" &7Freunde &7können &7jetzt &cnicht &7zu &7dir &7springen");
		}

		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").equals("")) {
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		}

		if (messagesYml.getString("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin").equals("")) {
			messagesYml.set("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin",
					" &7Der &7Spieler &7erhält &7die &7Nachricht, &7sobald &7er &7online &7geht.");
		}
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").equals("")) {
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		}
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").equals("")) {
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		}
		if (messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").equals("")) {
			messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou", " Noch kein Spieler hat dich angeschrieben.");
		}
		if (messagesYml.getString("Party.General.ErrorNotPartyLeader").equals("")) {
			messagesYml.set("Party.General.ErrorNotPartyLeader", "&cDu &cbist &cnicht &cder &cParty &cLeader.");
		}
		if (messagesYml.getString("Party.Leader.SenderEqualsGivenPlayer").equals("")) {
			messagesYml.set("Party.Leader.SenderEqualsGivenPlayer",
					"&7Du &7kannst &7dich &7nicht &7selber &7zum &7neuen &7Party &7Leiter &7machen");
		}
		if (messagesYml.getString("Party.General.ErrorGivenPlayerIsNotInTheParty").equals("")) {
			messagesYml.set("Party.General.ErrorGivenPlayerIsNotInTheParty",
					"&cDer &cSpieler [PLAYER] &cist &cnicht &cin &cder &cParty.");
		}
		if (messagesYml.getString("Party.CommandUsage.Join").equals("")) {
			messagesYml.set("Party.CommandUsage.Join", "&8/&5Party " + "join <Name>" + " &8- &7Trete einer Party bei");
		}
		if (messagesYml.getString("Party.CommandUsage.Invite").equals("")) {
			messagesYml.set("Party.CommandUsage.Invite",
					"&8/&5Party " + "invite <Name>" + " &8- &7Lade &7einen &7Spieler &7in &7deine &7Party &7ein");
		}
		if (messagesYml.getString("Party.CommandUsage.List").equals("")) {
			messagesYml.set("Party.CommandUsage.List",
					"&8/&5Party " + "list" + " &8- &7Listet alle Spieler in der Party auf");
		}
		if (messagesYml.getString("Party.CommandUsage.Chat").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party " + "chat <Nachricht>" + " &8- &7Sendet allen Spieler in der Party &7eine &7Nachicht");
		}
		if (messagesYml.getString("Party.CommandUsage.Leave").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat", "&8/&5Party " + "leave" + " &8- &7Verlässt die Party");
		}
		if (messagesYml.getString("Party.CommandUsage.Kick").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party " + "kick <Spieler>" + " &8- &7Kickt einen Spieler aus der Party");
		}
		if (messagesYml.getString("Party.CommandUsage.Leader").equals("")) {
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party " + "leader &5<Spieler>" + " &8- &7Macht einen anderen Spieler zum &7Leiter");
		}
		if (messagesYml.getString("Party.Error.CommandNotFound").equals("")) {
			messagesYml.set("Party.Error.CommandNotFound", "&cDieser Befehl Existiert nicht!");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoParty", "&cDu &cbist &cin &ckeiner &cParty.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNoParty", "&cDu &cbist &cin &ckeiner &cParty.");
		}
		if (messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").equals("")) {
			messagesYml.set("Party.Command.General.ErrorNotPartyLeader", "&cDu &cbist &cnicht &cder &cParty &cLeader.");
		}
		if (messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").equals("")) {
			messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
					"&5Die &5Party &5wurde &5wegen &5zu &5wenig &5Mitgliedern &5aufgelöst.");
		}
		if (messagesYml.getString("Party.Command.General.ServerSwitched").equals("")) {
			messagesYml.set("Party.Command.General.ServerSwitched",
					"&bDie &bParty &bhat &bden &bServer &e[SERVER] &bbetreten.");
		}
		if (messagesYml.getString("Party.Command.Chat.ErrorNoMessage").equals("")) {
			messagesYml.set("Party.Command.Chat.ErrorNoMessage", "&5Du musst eine Nachricht eingeben");
		}
		if (messagesYml.getString("Party.Command.Info.Empty").equals("")) {
			messagesYml.set("Party.Command.Info.Empty", "Leer");
		}
		if (messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").equals("")) {
			messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
					"&cDieser &cSpieler &cist &cnicht &conline.");
		}
		if (messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").equals("")) {
			messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
					"&7Du &7darfst &7dich &7nicht &7selber &7einladen.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInAParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInAParty",
					"&cDieser &cDer &cSpieler &cist &cbereits &cin &ceiner &cParty.");
		}
		if (messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").equals("")) {
			messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
					"&cDer &cSpieler &e[PLAYER] &cist &cschon &cin &cdie &cParty &ceingeladen.");
		}
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").equals("")) {
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&cDie &cMaximale &cgröße &cfür &ceine &cParty &cist &c[MAXPLAYERSINPARTY]");
		}
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").equals("")) {
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&cDie &cMaximale &cgröße &cfür &ceine &cParty &cist &c[MAXPLAYERSINPARTY]");
		}
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").equals("")) {
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&bDu &bhast &6[PLAYER] &bin &bdeine &bParty &beingeladen.");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
					"&5Die Einladung in die Party von &6[PLAYER] &5ist &5abgelaufen!");
		}
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").equals("")) {
			messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
					"&5Der Spieler&6 [PLAYER] &5hat &5die &5Einladung &5nicht &5angenommen!");
		}

		if (messagesYml.getString("Party.Command.Join.PlayerHasNoParty").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasNoParty", "&cDieser &cSpieler &chat &ckeine &cParty.");
		}
		if (messagesYml.getString("Party.Command.Join.AlreadyInAPartyError").equals("")) {
			messagesYml.set("Party.Command.Join.AlreadyInAPartyError",
					"&cDu &cbist &cbereits &cin &ceiner &cParty. &cNutze &6/party leave &cum &cdiese &cParty &czu &cverlassen.");
		}
		if (messagesYml.getString("Party.Command.Join.PlayerHasJoinend").equals("")) {
			messagesYml.set("Party.Command.Join.PlayerHasJoinend",
					"&bDer &bSpieler &6[PLAYER] &bist &bder &bParty &bbeigetreten.");
		}
		if (messagesYml.getString("Party.Command.Join.ErrorNoInvatation").equals("")) {
			messagesYml.set("Party.Command.Join.ErrorNoInvatation", "&cDu &ckannst &cder &cParty &cnicht &cbeitreten.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers",
					"&bDer &bSpieler &6[PLAYER] &bwurde &baus &bder &bParty &bgekickt.");
		}
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").equals("")) {
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
					"&bDu &bwurdest &baus &bder &bParty &bgekickt.");
		}
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Kick.Party.Command.Leader.NewLeaderIs",
					"&7Der &7neue &7Party &7Leiter &7ist &6[PLAYER].");
		}
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leader.NewLeaderIs", "&7Der &7neue &7Party &7Leiter &7ist &6[PLAYER].");
		}
		if (messagesYml.getString("Party.Command.Leave.YouLeftTheParty").equals("")) {
			messagesYml.set("Party.Command.Leave.YouLeftTheParty", "&bDu &bhast &bdeine &bParty &bverlassen.");
		}
		if (messagesYml.getString("Party.Command.Leave.NewLeaderIs").equals("")) {
			messagesYml.set("Party.Command.Leave.NewLeaderIs",
					"&bDer &bLeader &bhat &bdie &bParty &bverlassen. &bDer &bneue &bLeader &bist &e[NEWLEADER].");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Tritt &5der &5Party &5mit &6/Party join [PLAYER] &5bei!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Tritt &5der &5Party &5mit &6/Party join [PLAYER] &5bei!");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
					"&aHier klicken um Party einladung anzunehmen");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
					"&aHier klicken um Party einladung anzunehmen");
		}
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").equals("")) {
			messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
					"&5Du &5wurdest &5in &5die &5Party &5von&6 [PLAYER] &5eingeladen!");
		}
		messagesYml = loadSharedMessages(messagesYml);
		return messagesYml;
	}

	private static Configuration loadSharedMessages(Configuration messagesYml) {
		if (messagesYml.getString("Party.General.PartyPrefix").equals("")) {
			messagesYml.set("Party.General.PartyPrefix", "&7[&5Party&7] ");
		}
		if (messagesYml.getString("Friends.General.HelpBegin").equals("")) {
			messagesYml.set("Friends.General.HelpBegin",
					"&8&m-------------------" + "&r" + "&8[&5&lFriends&8]&m-------------------");
		}
		if (messagesYml.getString("Friends.General.HelpEnd").equals("")) {
			messagesYml.set("Friends.General.HelpEnd", "&8&m-----------------------------------------------");
		}
		if (messagesYml.getString("Party.General.HelpBegin").equals("")) {
			messagesYml.set("Party.General.HelpBegin",
					"&8&m-------------------" + "&r" + "&8[&5&lParty&8]&m-------------------");
		}
		if (messagesYml.getString("Party.General.HelpEnd").equals("")) {
			messagesYml.set("Party.General.HelpEnd", "&8&m---------------------------------------------");
		}
		if (messagesYml.getString("Party.Command.Chat.Prefix").equals("")) {
			messagesYml.set("Party.Command.Chat.Prefix", "&7[&5PartyChat&7] ");
		}
		if (messagesYml.getString("Party.Command.Chat.ContentColor").equals("")) {
			messagesYml.set("Party.Command.Chat.ContentColor", "&7");
		}
		if (messagesYml.getString("Party.Command.Chat.PartyChatOutput").equals("")) {
			messagesYml.set("Party.Command.Chat.PartyChatOutput", "&e[SENDERNAME]&5:[MESSAGE_CONTENT]");
		}
		if (messagesYml.getString("Party.Command.Info.PlayersCut").equals("")) {
			messagesYml.set("Party.Command.Info.PlayersCut", "&7, &b");
		}
		if (messagesYml.getString("Party.Command.Info.Leader").equals("")) {
			messagesYml.set("Party.Command.Info.Leader", "&3Leader&7: &5[LEADER]");
		}
		if (messagesYml.getString("Party.Command.Info.Players").equals("")) {
			messagesYml.set("Party.Command.Info.Players", "&8Players&7: &b");
		}
		if (messagesYml.getString("Friends.Command.MSG.SendedMessage").equals("")) {
			messagesYml.set("Friends.Command.MSG.SendedMessage", " &e[SENDER]&5-> &e[PLAYER]&7: [CONTENT]");
		}
		if (messagesYml.getString("Friends.Command.Settings.SplitLine").equals("")) {
			messagesYml.set("Friends.Command.Settings.SplitLine",
					"&8&m-----------------------------------------------");
		}
		if (messagesYml.getString("Friends.Command.List.OnlineTitle").equals("")) {
			messagesYml.set("Friends.Command.List.OnlineTitle", "(online)");
		}
		if (messagesYml.getString("Friends.Command.List.OnlineColor").equals("")) {
			messagesYml.set("Friends.Command.List.OnlineColor", "&a");
		}
		if (messagesYml.getString("Friends.Command.List.OfflineTitle").equals("")) {
			messagesYml.set("Friends.Command.List.OfflineTitle", "(offline)");
		}
		if (messagesYml.getString("Friends.Command.List.OfflineColor").equals("")) {
			messagesYml.set("Friends.Command.List.OfflineColor", "&c");
		}
		if (messagesYml.getString("Friends.Command.List.PlayerSplit").equals("")) {
			messagesYml.set("Friends.Command.List.PlayerSplit", "&7, ");
		}
		if (messagesYml.getString("Friends.Command.MSG.ColorOfMessage").equals("")) {
			messagesYml.set("Friends.Command.MSG.ColorOfMessage", " &7");
		}
		return messagesYml;
	}

	private static Configuration replaceColorCodes(Configuration messagesYml) {
		messagesYml.set("General.LanguageName", messagesYml.getString("General.LanguageName").replace("&", "§"));
		messagesYml.set("Party.General.PartyPrefix",
				messagesYml.getString("Party.General.PartyPrefix").replace("&", "§"));
		messagesYml.set("Party.General.HelpBegin", messagesYml.getString("Party.General.HelpBegin").replace("&", "§"));
		messagesYml.set("Party.General.HelpEnd", messagesYml.getString("Party.General.HelpEnd").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.Settings",
				messagesYml.getString("Friends.CommandUsage.Settings").replace("&", "§"));
		messagesYml.set("Party.Error.CommandNotFound",
				messagesYml.getString("Party.Error.CommandNotFound").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Join", messagesYml.getString("Party.CommandUsage.Join").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Invite",
				messagesYml.getString("Party.CommandUsage.Invite").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.List", messagesYml.getString("Party.CommandUsage.List").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Chat", messagesYml.getString("Party.CommandUsage.Chat").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Leave",
				messagesYml.getString("Party.CommandUsage.Leave").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Kick", messagesYml.getString("Party.CommandUsage.Kick").replace("&", "§"));
		messagesYml.set("Party.CommandUsage.Leader",
				messagesYml.getString("Party.CommandUsage.Leader").replace("&", "§"));
		messagesYml.set("Party.Command.General.ErrorNoParty",
				messagesYml.getString("Party.Command.General.ErrorNoParty").replace("&", "§"));
		messagesYml.set("Party.Command.General.ErrorNotPartyLeader",
				messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").replace("&", "§"));
		messagesYml.set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty",
				messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty").replace("&", "§"));
		messagesYml.set("Party.Command.General.ErrorNoPlayer",
				messagesYml.getString("Party.Command.General.ErrorNoPlayer").replace("&", "§"));
		messagesYml.set("Party.Command.General.ErrorPlayerNotOnline",
				messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline").replace("&", "§"));
		messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
				messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").replace("&", "§"));
		messagesYml.set("Party.Command.General.PlayerHasLeftTheParty",
				messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty").replace("&", "§"));
		messagesYml.set("Party.Command.General.ServerSwitched",
				messagesYml.getString("Party.Command.General.ServerSwitched").replace("&", "§"));
		messagesYml.set("Party.Command.Chat.Prefix",
				messagesYml.getString("Party.Command.Chat.Prefix").replace("&", "§"));
		messagesYml.set("Party.Command.Chat.ContentColor",
				messagesYml.getString("Party.Command.Chat.ContentColor").replace("&", "§"));
		messagesYml.set("Party.Command.Chat.PartyChatOutput",
				messagesYml.getString("Party.Command.Chat.PartyChatOutput").replace("&", "§"));
		messagesYml.set("Party.Command.Chat.ErrorNoMessage",
				messagesYml.getString("Party.Command.Chat.ErrorNoMessage").replace("&", "§"));
		messagesYml.set("Party.Command.Info.Leader",
				messagesYml.getString("Party.Command.Info.Leader").replace("&", "§"));
		messagesYml.set("Party.Command.Info.Players",
				messagesYml.getString("Party.Command.Info.Players").replace("&", "§"));
		messagesYml.set("Party.Command.Info.PlayersCut",
				messagesYml.getString("Party.Command.Info.PlayersCut").replace("&", "§"));
		messagesYml.set("Party.Command.Info.Empty",
				messagesYml.getString("Party.Command.Info.Empty").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
				messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
				messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.AlreadyInAParty",
				messagesYml.getString("Party.Command.Invite.AlreadyInAParty").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
				messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
				messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.InvitedPlayer",
				messagesYml.getString("Party.Command.Invite.InvitedPlayer").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
				messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
				messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
				messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
				messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").replace("&", "§"));
		messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
				messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").replace("&", "§"));
		messagesYml.set("Party.Command.Join.PlayerHasNoParty",
				messagesYml.getString("Party.Command.Join.PlayerHasNoParty").replace("&", "§"));
		messagesYml.set("Party.Command.Join.PlayerHasJoinend",
				messagesYml.getString("Party.Command.Join.PlayerHasJoinend").replace("&", "§"));
		messagesYml.set("Party.Command.Join.ErrorNoInvatation",
				messagesYml.getString("Party.Command.Join.ErrorNoInvatation").replace("&", "§"));
		messagesYml.set("Party.Command.Kick.KickedPlayerOutOfTheParty",
				messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfTheParty").replace("&", "§"));
		messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers",
				messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers").replace("&", "§"));
		messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
				messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").replace("&", "§"));
		messagesYml.set("Party.Command.Leader.SenderEqualsGivenPlayer",
				messagesYml.getString("Party.Command.Leader.SenderEqualsGivenPlayer").replace("&", "§"));
		messagesYml.set("Party.Command.Leader.NewLeaderIs",
				messagesYml.getString("Party.Command.Leader.NewLeaderIs").replace("&", "§"));
		messagesYml.set("Party.Command.Leave.NewLeaderIs",
				messagesYml.getString("Party.Command.Leave.NewLeaderIs").replace("&", "§"));
		messagesYml.set("Party.Command.Leave.YouLeftTheParty",
				messagesYml.getString("Party.Command.Leave.YouLeftTheParty").replace("&", "§"));
		messagesYml.set("Friends.General.Prefix", messagesYml.getString("Friends.General.Prefix").replace("&", "§"));
		messagesYml.set("Friends.General.HelpBegin",
				messagesYml.getString("Friends.General.HelpBegin").replace("&", "§"));
		messagesYml.set("Friends.General.HelpEnd", messagesYml.getString("Friends.General.HelpEnd").replace("&", "§"));
		messagesYml.set("Friends.General.CommandNotFound",
				messagesYml.getString("Friends.General.CommandNotFound").replace("&", "§"));
		messagesYml.set("Friends.General.PlayerIsOffline",
				messagesYml.getString("Friends.General.PlayerIsOffline").replace("&", "§"));
		messagesYml.set("Friends.General.NotAFriendOfOrOffline",
				messagesYml.getString("Friends.General.NotAFriendOfOrOffline").replace("&", "§"));
		messagesYml.set("Friends.General.NoFriendGiven",
				messagesYml.getString("Friends.General.NoFriendGiven").replace("&", "§"));
		messagesYml.set("Friends.General.NoPlayerGiven",
				messagesYml.getString("Friends.General.NoPlayerGiven").replace("&", "§"));
		messagesYml.set("Friends.General.TooManyArguments",
				messagesYml.getString("Friends.General.TooManyArguments").replace("&", "§"));
		messagesYml.set("Friends.General.PlayerIsNowOffline",
				messagesYml.getString("Friends.General.PlayerIsNowOffline").replace("&", "§"));
		messagesYml.set("Friends.General.PlayerIsNowOnline",
				messagesYml.getString("Friends.General.PlayerIsNowOnline").replace("&", "§"));
		messagesYml.set("Friends.General.RequestInfoOnJoin",
				messagesYml.getString("Friends.General.RequestInfoOnJoin").replace("&", "§"));
		messagesYml.set("Friends.General.RequestInfoOnJoinColor",
				messagesYml.getString("Friends.General.RequestInfoOnJoinColor").replace("&", "§"));
		messagesYml.set("Friends.General.RequestInfoOnJoinColorComma",
				messagesYml.getString("Friends.General.RequestInfoOnJoinColorComma").replace("&", "§"));
		messagesYml.set("Friends.General.DoesNotExist",
				messagesYml.getString("Friends.General.DoesNotExist").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.List",
				messagesYml.getString("Friends.CommandUsage.List").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.MSG",
				messagesYml.getString("Friends.CommandUsage.MSG").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.ADD",
				messagesYml.getString("Friends.CommandUsage.ADD").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.Accept",
				messagesYml.getString("Friends.CommandUsage.Accept").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.Deny",
				messagesYml.getString("Friends.CommandUsage.Deny").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.Remove",
				messagesYml.getString("Friends.CommandUsage.Remove").replace("&", "§"));
		messagesYml.set("Friends.CommandUsage.Jump",
				messagesYml.getString("Friends.CommandUsage.Jump").replace("&", "§"));
		messagesYml.set("Friends.Command.Accept.NowFriends",
				messagesYml.getString("Friends.Command.Accept.NowFriends").replace("&", "§"));
		messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
				messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").replace("&", "§"));
		messagesYml.set("Friends.Command.Accept.ErrorSenderEqualsReceiver",
				messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualsReceiver").replace("&", "§"));
		messagesYml.set("Friends.Command.Accept.ErrorAlreadySend",
				messagesYml.getString("Friends.Command.Accept.ErrorAlreadySend").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.SenderEqualsreceiver",
				messagesYml.getString("Friends.Command.Add.SenderEqualsreceiver").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.FriendRequestFromreceiver",
				messagesYml.getString("Friends.Command.Add.FriendRequestFromreceiver").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.FriendRequestreceived",
				messagesYml.getString("Friends.Command.Add.FriendRequestreceived").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.ClickHere",
				messagesYml.getString("Friends.Command.Add.ClickHere").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.SendedAFriendRequest",
				messagesYml.getString("Friends.Command.Add.SendedAFriendRequest").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
				messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.HowToAccept",
				messagesYml.getString("Friends.Command.Add.HowToAccept").replace("&", "§"));
		messagesYml.set("Friends.Command.Add.AlreadyFriends",
				messagesYml.getString("Friends.Command.Add.AlreadyFriends").replace("&", "§"));
		messagesYml.set("Friends.Command.Deny.HasDenied",
				messagesYml.getString("Friends.Command.Deny.HasDenied").replace("&", "§"));
		messagesYml.set("Friends.Command.Deny.NoFriendRequest",
				messagesYml.getString("Friends.Command.Deny.NoFriendRequest").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
				messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
				messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests", messagesYml
				.getString("Friends.Command.Settings.NowYouAreNotGonereceiveFriendRequests").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests",
				messagesYml.getString("Friends.Command.Settings.NowYouAreGonereceiveFriendRequests").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouAreNotGonereceiveMessages",
				messagesYml.getString("Friends.Command.Settings.NowYouAreNotGonereceiveMessages").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
				messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
				messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowNoMessages",
				messagesYml.getString("Friends.Command.Settings.NowNoMessages").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowMessages",
				messagesYml.getString("Friends.Command.Settings.NowMessages").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
				messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
				messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests", messagesYml
				.getString("Friends.Command.Settings.AtTheMomentYouAreNotGonereceiveFriendRequests").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests", messagesYml
				.getString("Friends.Command.Settings.AtTheMomentYouAreGonereceiveFriendRequests").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.SplitLine",
				messagesYml.getString("Friends.Command.Settings.SplitLine").replace("&", "§"));
		messagesYml
				.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
						messagesYml
								.getString(
										"Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
								.replace("&", "§"));
		messagesYml
				.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
						messagesYml
								.getString(
										"Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
								.replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover",
				messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests", messagesYml
				.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").replace("&", "§"));
		messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
				messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").replace("&", "§"));
		messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer",
				messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").replace("&", "§"));
		messagesYml.set("Friends.Command.Jump.JoinedTheServer",
				messagesYml.getString("Friends.Command.Jump.JoinedTheServer").replace("&", "§"));
		messagesYml.set("Friends.Command.Jump.CanNotJump",
				messagesYml.getString("Friends.Command.Jump.CanNotJump").replace("&", "§"));
		messagesYml.set("Friends.Command.List.NoFriendsAdded",
				messagesYml.getString("Friends.Command.List.NoFriendsAdded").replace("&", "§"));
		messagesYml.set("Friends.Command.List.OfflineTitle",
				messagesYml.getString("Friends.Command.List.OfflineTitle").replace("&", "§"));
		messagesYml.set("Friends.Command.List.OfflineColor",
				messagesYml.getString("Friends.Command.List.OfflineColor").replace("&", "§"));
		messagesYml.set("Friends.Command.List.OnlineTitle",
				messagesYml.getString("Friends.Command.List.OnlineTitle").replace("&", "§"));
		messagesYml.set("Friends.Command.List.OnlineColor",
				messagesYml.getString("Friends.Command.List.OnlineColor").replace("&", "§"));
		messagesYml.set("Friends.Command.List.FriendsList",
				messagesYml.getString("Friends.Command.List.FriendsList").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.CanNotWriteToHim",
				messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou",
				messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.PlayerAndMessageMissing",
				messagesYml.getString("Friends.Command.MSG.PlayerAndMessageMissing").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.ColorOfMessage",
				messagesYml.getString("Friends.Command.MSG.ColorOfMessage").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.SendedMessage",
				messagesYml.getString("Friends.Command.MSG.SendedMessage").replace("&", "§"));
		messagesYml.set("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin",
				messagesYml.getString("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin").replace("&", "§"));
		messagesYml.set("Friends.Command.Remove.Removed",
				messagesYml.getString("Friends.Command.Remove.Removed").replace("&", "§"));
		messagesYml.set("Friends.GUI.Hide.ShowAllPlayers",
				messagesYml.getString("Friends.GUI.Hide.ShowAllPlayers").replace('&', '§'));
		messagesYml.set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer",
				messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer").replace('&', '§'));
		messagesYml.set("Friends.GUI.Hide.ShowOnlyFriends",
				messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriends").replace('&', '§'));
		messagesYml.set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer",
				messagesYml.getString("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer").replace('&', '§'));
		messagesYml.set("Friends.GUI.Hide.ShowNobody",
				messagesYml.getString("Friends.GUI.Hide.ShowNobody").replace('&', '§'));
		return messagesYml;
	}
}
