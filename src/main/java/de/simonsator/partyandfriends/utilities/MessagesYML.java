package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

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
	 * @param pType The name of the language
	 * @return Returns the Messages.yml variable
	 * @throws IOException Can throw a {@link IOException}
	 */
	public static Configuration loadMessages(String pType) throws IOException {
		File file = null;
		Configuration messagesYml;
		switch (pType.toLowerCase()) {
			case "own":
				file = new File(Main.getInstance().getDataFolder().getPath(), "Messages.yml");
				if (!file.exists())
					file.createNewFile();
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
		if ("own".equalsIgnoreCase(pType))
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(messagesYml, file);
		process(messagesYml);
		return messagesYml;
	}

	private static Configuration loadEnglishMessages(Configuration messagesYml) {
		if (messagesYml.getString("Party.Error.CommandNotFound").isEmpty())
			messagesYml.set("Party.Error.CommandNotFound", "&cThis command doesn't exist!");
		if (messagesYml.getString("Party.CommandUsage.Join").isEmpty())
			messagesYml.set("Party.CommandUsage.Join", "&8/&5Party join  &8- &7Join a party");
		if (messagesYml.getString("Party.CommandUsage.Invite").isEmpty())
			messagesYml.set("Party.CommandUsage.Invite", "&8/&5Party invite  &8- &7Invite a player into your party");
		if (messagesYml.getString("Party.CommandUsage.List").isEmpty())
			messagesYml.set("Party.CommandUsage.List", "&8/&5Party list &8- &7List all players who are in the party");
		if (messagesYml.getString("Party.CommandUsage.Chat").isEmpty())
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party chat  &8- &7Send all players in the party a message");
		if (messagesYml.getString("Party.CommandUsage.Leave").isEmpty())
			messagesYml.set("Party.CommandUsage.Leave", "&8/&5Party leave &8- &7Leave the party");
		if (messagesYml.getString("Party.CommandUsage.Kick").isEmpty())
			messagesYml.set("Party.CommandUsage.Kick", "&8/&5Party kick  &8- &7Kicks a player out of the party");
		if (messagesYml.getString("Party.CommandUsage.Leader").isEmpty())
			messagesYml.set("Party.CommandUsage.Leader",
					"&8/&5Party leader &5 &8- &7Makes another player to the party leader");
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNoParty", "&5You need to be in a party");
		if (messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNotPartyLeader", "&cYou are not the party leader.");
		if (messagesYml.getString("Party.Command.General.ErrorGivenPlayerIsNotInTheParty").isEmpty())
			messagesYml.set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty",
					"&cThe player &e[PLAYER] &cis not in the party.");
		if (messagesYml.getString("Party.Command.General.ErrorNoPlayer").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNoPlayer", "&cYou need to give a player.");
		if (messagesYml.getString("Party.Command.General.ErrorPlayerNotOnline").isEmpty())
			messagesYml.set("Party.Command.General.ErrorPlayerNotOnline", "&cThis &cplayer &cis &cnot &conline.");
		if (messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").isEmpty())
			messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
					"&5The &5party &5was &5dissolved &5because &5of &5too &5less &5players.");
		if (messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty").isEmpty())
			messagesYml.set("Party.Command.General.PlayerHasLeftTheParty",
					"&bThe player &6[PLAYER] has left the party.");
		if (messagesYml.getString("Party.Command.General.ServerSwitched").isEmpty())
			messagesYml.set("Party.Command.General.ServerSwitched", "&bThe party has joined the Server &e[SERVER]&b.");
		if (messagesYml.getString("Party.Command.Chat.ErrorNoMessage").isEmpty())
			messagesYml.set("Party.Command.Chat.ErrorNoMessage", "&5You need to give a message");
		if (messagesYml.getString("Party.Command.Info.Empty").isEmpty())
			messagesYml.set("Party.Command.Info.Empty", "empty");
		if (messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").isEmpty())
			messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
					"&7You are not allowed to invite yourself.");
		if (messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").isEmpty())
			messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
					"&cYou can't invite this player into your Party.");
		if (messagesYml.getString("Party.Command.Invite.AlreadyInAParty").isEmpty())
			messagesYml.set("Party.Command.Invite.AlreadyInAParty", "&cThis player is already in &ca party.");
		if (messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").isEmpty())
			messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
					"&cThe &cplayer &e[PLAYER] &cis &calready &cinvited &cinto &cyour &cparty.");
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").isEmpty())
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&cThe &cMax &csize &cof &ca &cparty &cis &c[MAXPLAYERSINPARTY]");
		if (messagesYml.getString("Party.Command.Invite.InvitedPlayer").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitedPlayer", "&6[PLAYER] &bwas &binvited &bto &byour &bparty.");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
					"&5You &5were &5invited &5to &5the &5party &5of &6[PLAYER]&5!");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Join &5the &5party &5by &5using &5the &5command &6/Party &6join &6[PLAYER]!");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER", "Click here to join the party");
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
					"&5The invitation of the Party from &6[PLAYER] &5is &5timed &5out!");
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
					"&5The player&6 [PLAYER] &5has &5not &5accepted &5your &5invitation!");
		if (messagesYml.getString("Party.Command.Join.PlayerHasNoParty").isEmpty())
			messagesYml.set("Party.Command.Join.PlayerHasNoParty", "&cThis &cplayer &chas &cno &cparty.");
		if (messagesYml.getString("Party.Command.Join.AlreadyInAPartyError").isEmpty())
			messagesYml.set("Party.Command.Join.AlreadyInAPartyError",
					"&cYou &care &calready &cin &ca &cparty. &6use &6/party leave &cto &cleave &this &cParty.");
		if (messagesYml.getString("Party.Command.Join.PlayerHasJoined").isEmpty())
			messagesYml.set("Party.Command.Join.PlayerHasJoined", "&bThe player &6[PLAYER] &bjoined the party.");
		if (messagesYml.getString("Party.Command.Join.ErrorNoInvitation").isEmpty())
			messagesYml.set("Party.Command.Join.ErrorNoInvitation", "&cYou can't join this party.");
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers").isEmpty())
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers",
					"&bThe &bplayer &6[PLAYER] &bwas &bkicked &bout &bof &bparty &bparty.");
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").isEmpty())
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
					"&bYou &bhave &bbeen &bkicked &bout &bof &bparty.");
		if (messagesYml.getString("Party.Command.Leader.SenderEqualsGivenPlayer").isEmpty())
			messagesYml.set("Party.Command.Leader.SenderEqualsGivenPlayer",
					"&7You &7cannot &7make &7yourself &7to &7the &7new &7party &7leader");
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").isEmpty())
			messagesYml.set("Party.Command.Leader.NewLeaderIs", "&7The &7new &7party &7leader &7is &6[NEWLEADER]");
		if (messagesYml.getString("Party.Command.Leave.NewLeaderIs").isEmpty())
			messagesYml.set("Party.Command.Leave.NewLeaderIs",
					"&bThe &bLeader &bhas &bleft &bthe &bParty. &bThe &bnew &bLeader &bis &e[NEWLEADER].");
		if (messagesYml.getString("Party.Command.Leave.YouLeftTheParty").isEmpty())
			messagesYml.set("Party.Command.Leave.YouLeftTheParty", "&bYou &bleft &byour &bparty.");
		if (messagesYml.getString("Friends.General.Prefix").isEmpty())
			messagesYml.set("Friends.General.Prefix", "&8[&5&lFriends&8]");
		if (messagesYml.getString("Friends.General.CommandNotFound").isEmpty())
			messagesYml.set("Friends.General.CommandNotFound", " &7The &7Command &7doesn't &7exist.");
		if (messagesYml.getString("Friends.General.PlayerIsOffline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsOffline",
					" &7The Player &e[PLAYER] &7is &7not &7online &7or &7you &7are &7not &7a &7friend &7of &7him");
		if (messagesYml.getString("Friends.General.NotAFriendOfOrOffline").isEmpty())
			messagesYml.set("Friends.General.NotAFriendOfOrOffline",
					" &7The Player &e[PLAYER] &7is &7not &7online &7or &7you &7are &7not &7a &7friend &7of &7him");
		if (messagesYml.getString("Friends.General.NoFriendGiven").isEmpty())
			messagesYml.set("Friends.General.NoFriendGiven", " &7You &7need &7to &7give &7a &7friend");
		if (messagesYml.getString("Friends.General.NoPlayerGiven").isEmpty())
			messagesYml.set("Friends.General.NoPlayerGiven", " &7You &7need &7to &7give &7a &7player");
		if (messagesYml.getString("Friends.General.TooManyArguments").isEmpty())
			messagesYml.set("Friends.General.TooManyArguments", "&7 Too many arguments");
		if (messagesYml.getString("Friends.General.PlayerIsNowOffline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsNowOffline", " &7Your friend &e[PLAYER] is now &coffline.");
		if (messagesYml.getString("Friends.General.PlayerIsNowOnline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsNowOnline", " &7The friend &e[PLAYER] &7is now &aonline.");
		if (messagesYml.getString("Friends.General.RequestInfoOnJoin").isEmpty())
			messagesYml.set("Friends.General.RequestInfoOnJoin",
					" &7You &7have &7friend &7requests &7from: [FRIENDREQUESTS]");
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColor").isEmpty())
			messagesYml.set("Friends.General.RequestInfoOnJoinColor", "&e");
		if (messagesYml.getString("Friends.General.RequestInfoOnJoinColorComma").isEmpty())
			messagesYml.set("Friends.General.RequestInfoOnJoinColorComma", "&7");
		if (messagesYml.getString("Friends.General.DoesNotExist").isEmpty())
			messagesYml.set("Friends.General.DoesNotExist", " &7The player &e[PLAYER] &7doesn't &7exist");
		if (messagesYml.getString("Friends.General.GivenPlayerEqualsSender").isEmpty())
			messagesYml.set("Friends.General.GivenPlayerEqualsSender",
					" &7You cannot give you self as player argument.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowAllPlayers").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowAllPlayers", " &aNow you can see all players.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer",
					" &eNow only friends &eand &epeople &eof ðe &eserver &eteam &ewill &ebe &eshown.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriends").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriends", " &6Now you can see only Friends.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer",
					" &5Now you can see only players from the server &5team.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowNobody").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowNobody", " &cHide all players.");
		if (messagesYml.getString("Friends.CommandUsage.List").isEmpty())
			messagesYml.set("Friends.CommandUsage.List", "&8/&5friend list &8- &7Lists &7all &7of &7your &7friends");
		if (messagesYml.getString("Friends.CommandUsage.MSG").isEmpty())
			messagesYml.set("Friends.CommandUsage.MSG",
					"&8/&5friend &5msg &5[name &5of &5the &5friend] &5[message]&r &8- &7send &7a &7friend &7a &7message");
		if (messagesYml.getString("Friends.CommandUsage.ADD").isEmpty())
			messagesYml.set("Friends.CommandUsage.ADD",
					"&8/&5friend &5add &5[name &5of &5the &5player]&r &8- &7Add &7a &7friend");
		if (messagesYml.getString("Friends.CommandUsage.Accept").isEmpty())
			messagesYml.set("Friends.CommandUsage.Accept",
					"&8/&5friend &5accept &5[name &5of &5the &5player]&r &8- &7accept &7a &7friend request");
		if (messagesYml.getString("Friends.CommandUsage.Deny").isEmpty())
			messagesYml.set("Friends.CommandUsage.Deny",
					"&8/&5friend &5deny &5[name &5of &5the &5player]&r &8- &7deny &7a &7friend &7request");
		if (messagesYml.getString("Friends.CommandUsage.Remove").isEmpty())
			messagesYml.set("Friends.CommandUsage.Remove",
					"&8/&5friend &5remove &5[name &5of &5the &5friend]&r &8- &7removes &7a &7friend");
		if (messagesYml.getString("Friends.CommandUsage.Jump").isEmpty())
			messagesYml.set("Friends.CommandUsage.Jump",
					"&8/&5friend &5jump [name of the &5friend]&r&8- &7Jump &7to &7a &7friend");
		if (messagesYml.getString("Friends.CommandUsage.Settings").isEmpty())
			messagesYml.set("Friends.CommandUsage.Settings", "&8/&5friend &5settings &r&8- &7Change &7the &7settings");
		if (messagesYml.getString("Friends.Command.Accept.NowFriends").isEmpty())
			messagesYml.set("Friends.Command.Accept.NowFriends", " &7You and &e[PLAYER] &7are &7now &7friends");
		if (messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
					" &7You didn't receive a &7friend &7request &7from &e[PLAYER]&7.");
		if (messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualsReceiver").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorSenderEqualsReceiver",
					" &7You cannot &7write &7to &7yourself.");
		if (messagesYml.getString("Friends.Command.Accept.ErrorAlreadySend").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorAlreadySend",
					"&7 You already have sent &7the &7player &e[PLAYER] &7a &7friend &7request.");
		if (messagesYml.getString("Friends.Command.Add.SenderEqualsReceiver").isEmpty())
			messagesYml.set("Friends.Command.Add.SenderEqualsReceiver",
					" &7You &7cannot &7send &7yourself &7a &7friend &7request.");
		if (messagesYml.getString("Friends.Command.Add.FriendRequestFromReceiver").isEmpty())
			messagesYml.set("Friends.Command.Add.FriendRequestFromReceiver",
					" &7The player &e[PLAYER] &7has &7already &7send &7you &7a &7friend &7request.");
		if (messagesYml.getString("Friends.Command.Add.FriendRequestReceived").isEmpty())
			messagesYml.set("Friends.Command.Add.FriendRequestReceived",
					"&7 You have received a friend request from &e[PLAYER]&7.");
		if (messagesYml.getString("Friends.Command.Add.ClickHere").isEmpty())
			messagesYml.set("Friends.Command.Add.ClickHere", "&aClick here to accept the friendship request");
		if (messagesYml.getString("Friends.Command.Add.SentAFriendRequest").isEmpty())
			messagesYml.set("Friends.Command.Add.SentAFriendRequest",
					"&7 The player &e[PLAYER]&7 was &7send &7a &7friend &7request");
		if (messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").isEmpty())
			messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
					" &7You &7cannot &7send &7the &7player &e[PLAYER] &7a &7friend &7request");
		if (messagesYml.getString("Friends.Command.Add.HowToAccept").isEmpty())
			messagesYml.set("Friends.Command.Add.HowToAccept",
					" &7Accept the friend request with &6/friend &6accept &6[PLAYER]&7.");
		if (messagesYml.getString("Friends.Command.Add.AlreadyFriends").isEmpty())
			messagesYml.set("Friends.Command.Add.AlreadyFriends", "&7 You and &e[PLAYER] &7are &7already &7friends.");
		if (messagesYml.getString("Friends.Command.Deny.HasDenied").isEmpty())
			messagesYml.set("Friends.Command.Deny.HasDenied", " &7You have denied the friend request of &e[PLAYER].");
		if (messagesYml.getString("Friends.Command.Deny.NoFriendRequest").isEmpty())
			messagesYml.set("Friends.Command.Deny.NoFriendRequest",
					" &7You didn't receive a &7friend &7request &7from &e[PLAYER]&7.");
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
					" &7Now &7you &7can &7get &7invited &7by &aevery &7player &7into &7his &7Party.");
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
					" &7Now &7you &7can &7get &7invited &conly &7by &7by your friends &7into &7their &7Party.");
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests",
					" &7Now &7you &7are &cnot &7gone &7receive &7friend &7requests &7anymore");
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests",
					" &7Now &7you &7are &agone &7receive &7friend &7requests &7from &7everyone");
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGoneReceiveMessages").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGoneReceiveMessages",
					" &7Now &7you &7are &cnot &7gone &7receive &7messages &7anymore");
		if (messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
					" &7Now &7you &7will &7be &7shown &7as &aonline");
		if (messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
					" &7Now &7you &7will &7be &7shown &7as &coffline");
		if (messagesYml.getString("Friends.Command.Settings.NowNoMessages").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowNoMessages",
					" &7Now &7you &7are &cnot &7gone &7receive &7messages &7anymore");
		if (messagesYml.getString("Friends.Command.Settings.NowMessages").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowMessages",
					" &7Now &7you &7are &agone &7receive &7message &7from &7everyone");
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
					" &7Now &7your &7friends &7can &ajump &7to &7you");
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
					" &7Now &7your &7friends &7can &cnot &7jump &7to &7you");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests",
					" &7At &7the moment &7you &7are &cnot &7gone &7receive &7friend &7request");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreGoneReceiveFriendRequests",
					" &7At &7the moment &7you &7are &7gone &7receive &7friend &7requests &7from &aeveryone");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
				.isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
					" &7At &7the moment &7you &7can &7get &7invited &7by &aevery &7player &7into &7his &7Party.");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7At &7the moment &7you &7can &7get &7invited &aonly &7by &7by your friends &7into &7their &7Party.");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover", "Click here to change this setting.");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
					" &7Change &7this &7setting &7with &6/friend &6settings &6friendrequests");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
					" &7Change &7this &7setting &7with &6/friend &6settings &6Party");
		if (messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").isEmpty())
			messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer", " &7You &7are &7already &7on &7this &7server");
		if (messagesYml.getString("Friends.Command.Jump.JoinedTheServer").isEmpty())
			messagesYml.set("Friends.Command.Jump.JoinedTheServer",
					" &7Now &7you &7are &7on &7the &7same &7server, &7like &7the &7player &e[PLAYER]");
		if (messagesYml.getString("Friends.Command.Jump.CanNotJump").isEmpty())
			messagesYml.set("Friends.Command.Jump.CanNotJump", " &7You &7cannot &7jump to &7this &7person");
		if (messagesYml.getString("Friends.Command.List.NoFriendsAdded").isEmpty())
			messagesYml.set("Friends.Command.List.NoFriendsAdded",
					" &7Till now, &7you don't &7have &7added &7friends.");
		if (messagesYml.getString("Friends.Command.List.FriendsList").isEmpty())
			messagesYml.set("Friends.Command.List.FriendsList", " &7These &7are &7your &7friends:");
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").isEmpty())
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7You cannot write to this player.");
		if (messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").isEmpty())
			messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou", "&7 No player ever wrote to you.");
		if (messagesYml.getString("Friends.Command.MSG.PlayerAndMessageMissing").isEmpty())
			messagesYml.set("Friends.Command.MSG.PlayerAndMessageMissing", " &7You &7need &7to &7give &7a &7message.");
		if (messagesYml.getString("Friends.Command.Remove.Removed").isEmpty())
			messagesYml.set("Friends.Command.Remove.Removed", "&7 You removed the friend &e[PLAYER]&7.");
		loadSharedMessages(messagesYml);
		return messagesYml;
	}

	private static Configuration loadGermanMessages(Configuration messagesYml) {
		if (messagesYml.getString("Friends.Command.Add.SentAFriendRequest").isEmpty())
			messagesYml.set("Friends.Command.Add.SentAFriendRequest",
					" &7Dem Spieler &e[PLAYER] wurde eine Freundschaftsanfrage gesendet");
		if (messagesYml.getString("Friends.General.CommandNotFound").isEmpty())
			messagesYml.set("Friends.General.CommandNotFound", " &7Das &7Kommando &7existiert &7nicht.");
		if (messagesYml.getString("Friends.General.PlayerIsOffline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsOffline",
					" &7Der Spieler &e[PLAYER] &7ist &7nicht &7Online &7oder &7du &7bist &7nicht &7mit &7ihm &7befreundet");
		if (messagesYml.getString("Friends.General.NoPlayerGiven").isEmpty())
			messagesYml.set("Friends.General.NoPlayerGiven", " &7Du musst einen Spieler angeben");
		if (messagesYml.getString("Friends.General.PlayerIsNowOnline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsNowOnline", " &e[PLAYER] &7ist &7jetzt &aOnline");
		if (messagesYml.getString("Friends.General.RequestInfoOnJoin").isEmpty())
			messagesYml.set("Friends.General.RequestInfoOnJoin",
					" &7Freundschaftsanfragen &7stehen &7von &7den &7folgenden &7Spielern &7aus: [FRIENDREQUESTS]");
		if (messagesYml.getString("Friends.General.PlayerIsNowOffline").isEmpty())
			messagesYml.set("Friends.General.PlayerIsNowOffline", " &7Der Freund &e[PLAYER] &7ist &7nun &cOffline.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowAllPlayers").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowAllPlayers", " &aDir &awerden &ajetzt &aalle &aSpieler &aangezeigt.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer",
					" &eDir &ewerden &ejetzt &enur &enoch &eFreunde &eund &eLeute &evom &eServer &eangezeigt.");
		if (messagesYml.getString("Party.Command.General.PlayerHasLeftTheParty").isEmpty())
			messagesYml.set("Party.Command.General.PlayerHasLeftTheParty",
					"&bDer Spieler &6[PLAYER] hat die party verlassen.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyFriends").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyFriends",
					" &6Dir &6werden &6jetzt &6nur &6noch &6deine &6Freunde &6angezeigt.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer",
					" &5Dir &5werden &5jetzt &5nur &5noch &5Spieler &5vom &5Server &5Team &5angezeigt.");
		if (messagesYml.getString("Friends.GUI.Hide.ShowNobody").isEmpty())
			messagesYml.set("Friends.GUI.Hide.ShowNobody",
					" &cDir &cwerden &cjetzt &ckeine &cSpieler &cmehr &cangezeigt.");
		if (messagesYml.getString("Friends.CommandUsage.List").isEmpty())
			messagesYml.set("Friends.CommandUsage.List", "&8/&5friend list&r &8- &7Listet &7deine &7Freunde &7auf");
		if (messagesYml.getString("Friends.CommandUsage.MSG").isEmpty())
			messagesYml.set("Friends.CommandUsage.MSG",
					"&8/&5friend &5msg &5[Name &5des &5Freundes] &5[Nachricht]&r &8- &7schickt &7einem &7Freund &7eine &7Private Nachricht");
		if (messagesYml.getString("Friends.CommandUsage.ADD").isEmpty())
			messagesYml.set("Friends.CommandUsage.ADD",
					"&8/&5friend &5add &5[Name &5des &5Spielers]&r &8- &7Fügt &7einen &7Freund &7hinzu");
		if (messagesYml.getString("Friends.CommandUsage.Accept").isEmpty())
			messagesYml.set("Friends.CommandUsage.Accept",
					"&8/&5friend &5accept &5[Name &5des &5Spielers]&r &8- &7Akzeptiert &7eine &7Freundschaftsanfrage");
		if (messagesYml.getString("Friends.CommandUsage.Deny").isEmpty())
			messagesYml.set("Friends.CommandUsage.Deny",
					"&8/&5friend &5deny &5[Name &5des &5Spielers]&r &8- &7Lehnt eine &7Freundschaftsanfrage &7ab");
		if (messagesYml.getString("Friends.CommandUsage.Remove").isEmpty())
			messagesYml.set("Friends.CommandUsage.Remove",
					"&8/&5friend &5remove &5[Name &5des &5Spielers]&r &8- &7Entfernt &7einen &7Freund");
		if (messagesYml.getString("Friends.CommandUsage.Jump").isEmpty())
			messagesYml.set("Friends.CommandUsage.Jump",
					"&8/&5friend &5jump [Name des Freundes]&r&8- &7Zu &7einem &7Freund &7springen");
		if (messagesYml.getString("Friends.CommandUsage.Settings").isEmpty())
			messagesYml.set("Friends.CommandUsage.Settings", "&8/&5friend &5settings &r&8- &7Ändere die Einstellungen");
		if (messagesYml.getString("Friends.Command.Accept.NowFriends").isEmpty())
			messagesYml.set("Friends.Command.Accept.NowFriends", " &7Du bist jetzt mit &e[PLAYER] &7befreundet");
		if (messagesYml.getString("Friends.Command.Accept.ErrorAlreadySend").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorAlreadySend",
					" &7Du hast dem Spieler &e[PLAYER] &7schon &7eine &7Freundschaftsanfrage &7gesendet.");
		if (messagesYml.getString("Friends.Command.Accept.ErrorNoFriendShipInvitation").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorNoFriendShipInvitation",
					"&7 Du hast keine &7Freundschaftsanfrage von &e[PLAYER] &7keine &7erhalten");
		if (messagesYml.getString("Friends.Command.Add.SentAFriendRequest").isEmpty())
			messagesYml.set("Friends.Command.Add.SentAFriendRequest",
					"&7 The player &e[PLAYER] &7was &7send &7a &7friend &7request");
		if (messagesYml.getString("Friends.Command.Add.FriendRequestFromReceiver").isEmpty())
			messagesYml.set("Friends.Command.Add.FriendRequestFromReceiver",
					" &7Der Spieler &e[PLAYER] &7hat &7dir &7schon &7eine &7Freundschaftsanfrage &7gesendet.");
		if (messagesYml.getString("Friends.Command.Add.HowToAccept").isEmpty())
			messagesYml.set("Friends.Command.Add.HowToAccept", " &7Nimm sie mit &6/friend accept [PLAYER] &7an");
		if (messagesYml.getString("Friends.Command.Add.ClickHere").isEmpty())
			messagesYml.set("Friends.Command.Add.ClickHere", "&aHier klicken um die Freundschaftsanfrage anzunehmen");
		if (messagesYml.getString("Friends.Command.Add.AlreadyFriends").isEmpty())
			messagesYml.set("Friends.Command.Add.AlreadyFriends", " &7Du bist schon mit &e[PLAYER] &7befreundet");
		if (messagesYml.getString("Friends.Command.Accept.ErrorSenderEqualsReceiver").isEmpty())
			messagesYml.set("Friends.Command.Accept.ErrorSenderEqualsReceiver",
					" &7Du kannst dir nicht selber eine &7Freundschaftsanfrage &7senden");
		if (messagesYml.getString("Friends.General.DoesNotExist").isEmpty())
			messagesYml.set("Friends.General.DoesnotExist", " &7Der Spieler &e[PLAYER] &7exestiert &7nicht");
		if (messagesYml.getString("Friends.Command.Add.CanNotSendThisPlayer").isEmpty())
			messagesYml.set("Friends.Command.Add.CanNotSendThisPlayer",
					" &7Du &7kannst &7dem &7Spieler &e[PLAYER] &7keine &7Freundschaftsanfrage &7senden");
		if (messagesYml.getString("Friends.Command.Deny.HasDenied").isEmpty())
			messagesYml.set("Friends.Command.Deny.HasDenied", " &7Du hast die Anfrage von &e[PLAYER] &7abglehnt");
		if (messagesYml.getString("Friends.Command.Jump.CanNotJump").isEmpty())
			messagesYml.set("Friends.Command.Jump.CanNotJump", " &7Du &7kannst &7nicht zu &7dieser &7Person springen");
		if (messagesYml.getString("Friends.Command.Jump.AlreadyOnTheServer").isEmpty())
			messagesYml.set("Friends.Command.Jump.AlreadyOnTheServer",
					" &7Du &7bist &7bereits &7auf &7diesem &7Server");
		if (messagesYml.getString("Friends.Command.Jump.JoinedTheServer").isEmpty())
			messagesYml.set("Friends.Command.Jump.JoinedTheServer",
					" &7Du &7bist &7jetzt &7auf &7dem &7gleichen &7Server, &7wie &7der &7Spieler &e[PLAYER]");
		if (messagesYml.getString("Friends.Command.List.FriendsList").isEmpty())
			messagesYml.set("Friends.Command.List.FriendsList", " &7Dies &7sind &7deine &7Freunde:");
		if (messagesYml.getString("Friends.Command.List.NoFriendsAdded").isEmpty())
			messagesYml.set("Friends.Command.List.NoFriendsAdded", " &7Du hast noch keine Freunde &7hinzugefügt.");
		if (messagesYml.getString("Friends.Command.Remove.Removed").isEmpty())
			messagesYml.set("Friends.Command.Remove.Removed", "&7 Du hast den Freund &e[PLAYER] &7entfernt");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests",
					" &7Momentan &7können &7dir &ckeine &7Freundschaftsanfragen &7gesendet &7werden");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty")
				.isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &7Party &7Einladungen &7von &ajedem &7gesendet &7werden &7gesendet &7werden");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithFriendrequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
					" &7Ändere &7diese &7Einstellung &7mit &6/friend &6settings &6Freundschaftsanfragen");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &cnur &7Party &7Einladungen &7von &7Freunden &7gesendet &7werden");
		if (messagesYml.getString("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty")
				.isEmpty())
			messagesYml.set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
					" &7Momentan &7können &7dir &cnur &7Party &7Einladungen &7von &7Freunden &7gesendet &7werden");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingWithParty").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingWithParty",
					" &7Ändere &7diese &7Einstellung &7mit &6/friend &6settings &6Party");
		if (messagesYml.getString("Friends.Command.Settings.ChangeThisSettingsHover").isEmpty())
			messagesYml.set("Friends.Command.Settings.ChangeThisSettingsHover",
					"Hier klicken um die Einstellung zu ändern.");
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByEveryone").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Spieler &7in &7eine &7Party &7eingeladen &7werden");
		if (messagesYml.getString("Friends.Command.Settings.NowYouCanGetInvitedByFriends").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
					" &7Du &7kannst &7jetzt &cnur &7noch &7von &7deinen &7Freunden &7in &7eine &7Party &7eingeladen &7werden");
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests",
					" &7Du &7kannst &7jetzt &ckeine &7Freundschaftsanfragen &7mehr &7erhalten");
		if (messagesYml.getString("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Freundschaftsanfragen &7erhalten");
		if (messagesYml.getString("Friends.Command.Settings.NowYouWillBeShowAsOnline").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouWillBeShowAsOnline",
					" &7Du &7wirst &7nun &7als &aonline &7angezeigt");
		if (messagesYml.getString("Friends.Command.Settings.NowYouWilBeShownAsOffline").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYouWilBeShownAsOffline",
					" &7Du &7wirst &7nun &7als &coffline &7angezeigt");
		if (messagesYml.getString("Friends.Command.Settings.NowNoMessages").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowNoMessages",
					" &7Du &7kannst &7jetzt &ckeine &7Nachrichten &7mehr &7erhalten");
		if (messagesYml.getString("Friends.Command.Settings.NowMessages").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowMessages",
					" &7Du &7kannst &7jetzt &7von &ajedem &7Nachrichten &7erhalten");
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanJump").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanJump",
					" &7Freunde &7können &7jetzt &7zu &7dir &aspringen");
		if (messagesYml.getString("Friends.Command.Settings.NowYourFriendsCanNotJump").isEmpty())
			messagesYml.set("Friends.Command.Settings.NowYourFriendsCanNotJump",
					" &7Freunde &7können &7jetzt &cnicht &7zu &7dir &7springen");
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").isEmpty())
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		if (messagesYml.getString("Friends.Command.MSG.CanNotWriteToHim").isEmpty())
			messagesYml.set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		if (messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").isEmpty())
			messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou", " Noch kein Spieler hat dich angeschrieben.");
		if (messagesYml.getString("Friends.Command.MSG.NoOneEverWroteToYou").isEmpty())
			messagesYml.set("Friends.Command.MSG.NoOneEverWroteToYou", " Noch kein Spieler hat dich angeschrieben.");
		if (messagesYml.getString("Party.General.ErrorNotPartyLeader").isEmpty())
			messagesYml.set("Party.General.ErrorNotPartyLeader", "&cDu &cbist &cnicht &cder &cParty &cLeader.");
		if (messagesYml.getString("Party.Leader.SenderEqualsGivenPlayer").isEmpty())
			messagesYml.set("Party.Leader.SenderEqualsGivenPlayer",
					"&7Du &7kannst &7dich &7nicht &7selber &7zum &7neuen &7Party &7Leiter &7machen");
		if (messagesYml.getString("Party.General.ErrorGivenPlayerIsNotInTheParty").isEmpty())
			messagesYml.set("Party.General.ErrorGivenPlayerIsNotInTheParty",
					"&cDer &cSpieler [PLAYER] &cist &cnicht &cin &cder &cParty.");
		if (messagesYml.getString("Party.CommandUsage.Join").isEmpty())
			messagesYml.set("Party.CommandUsage.Join", "&8/&5Party join <Name> &8- &7Trete einer Party bei");
		if (messagesYml.getString("Party.CommandUsage.Invite").isEmpty())
			messagesYml.set("Party.CommandUsage.Invite",
					"&8/&5Party invite <Name> &8- &7Lade &7einen &7Spieler &7in &7deine &7Party &7ein");
		if (messagesYml.getString("Party.CommandUsage.List").isEmpty())
			messagesYml.set("Party.CommandUsage.List", "&8/&5Party list &8- &7Listet alle Spieler in der Party auf");
		if (messagesYml.getString("Party.CommandUsage.Chat").isEmpty())
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party chat <Nachricht> &8- &7Sendet allen Spieler in der Party &7eine &7Nachicht");
		if (messagesYml.getString("Party.CommandUsage.Leave").isEmpty())
			messagesYml.set("Party.CommandUsage.Chat", "&8/&5Party leave &8- &7Verlässt die Party");
		if (messagesYml.getString("Party.CommandUsage.Kick").isEmpty())
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party kick <Spieler> &8- &7Kickt einen Spieler aus der Party");
		if (messagesYml.getString("Party.CommandUsage.Leader").isEmpty())
			messagesYml.set("Party.CommandUsage.Chat",
					"&8/&5Party leader &5<Spieler> &8- &7Macht einen anderen Spieler zum &7Leiter");
		if (messagesYml.getString("Party.Error.CommandNotFound").isEmpty())
			messagesYml.set("Party.Error.CommandNotFound", "&cDieser Befehl Existiert nicht!");
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNoParty", "&cDu &cbist &cin &ckeiner &cParty.");
		if (messagesYml.getString("Party.Command.General.ErrorNoParty").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNoParty", "&cDu &cbist &cin &ckeiner &cParty.");
		if (messagesYml.getString("Party.Command.General.ErrorNotPartyLeader").isEmpty())
			messagesYml.set("Party.Command.General.ErrorNotPartyLeader", "&cDu &cbist &cnicht &cder &cParty &cLeader.");
		if (messagesYml.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers").isEmpty())
			messagesYml.set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers",
					"&5Die &5Party &5wurde &5wegen &5zu &5wenig &5Mitgliedern &5aufgelöst.");
		if (messagesYml.getString("Party.Command.General.ServerSwitched").isEmpty())
			messagesYml.set("Party.Command.General.ServerSwitched",
					"&bDie &bParty &bhat &bden &bServer &e[SERVER] &bbetreten.");
		if (messagesYml.getString("Party.Command.Chat.ErrorNoMessage").isEmpty())
			messagesYml.set("Party.Command.Chat.ErrorNoMessage", "&5Du musst eine Nachricht eingeben");
		if (messagesYml.getString("Party.Command.Info.Empty").isEmpty())
			messagesYml.set("Party.Command.Info.Empty", "Leer");
		if (messagesYml.getString("Party.Command.Invite.CanNotInviteThisPlayer").isEmpty())
			messagesYml.set("Party.Command.Invite.CanNotInviteThisPlayer",
					"&cDieser &cSpieler &cist &cnicht &conline.");
		if (messagesYml.getString("Party.Command.Invite.GivenPlayerEqualsSender").isEmpty())
			messagesYml.set("Party.Command.Invite.GivenPlayerEqualsSender",
					"&7Du &7darfst &7dich &7nicht &7selber &7einladen.");
		if (messagesYml.getString("Party.Command.Invite.AlreadyInAParty").isEmpty())
			messagesYml.set("Party.Command.Invite.AlreadyInAParty",
					"&cDieser &cDer &cSpieler &cist &cbereits &cin &ceiner &cParty.");
		if (messagesYml.getString("Party.Command.Invite.AlreadyInYourParty").isEmpty())
			messagesYml.set("Party.Command.Invite.AlreadyInYourParty",
					"&cDer &cSpieler &e[PLAYER] &cist &cschon &cin &cdie &cParty &ceingeladen.");
		if (messagesYml.getString("Party.Command.Invite.MaxPlayersInPartyReached").isEmpty())
			messagesYml.set("Party.Command.Invite.MaxPlayersInPartyReached",
					"&cDie &cMaximale &cgröße &cfür &ceine &cParty &cist &c[MAXPLAYERSINPARTY]");
		if (messagesYml.getString("Party.Command.Invite.InvitedPlayer").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitedPlayer",
					"&bDu &bhast &6[PLAYER] &bin &bdeine &bParty &beingeladen.");
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitationTimedOutInvited",
					"&5Die Einladung in die Party von &6[PLAYER] &5ist &5abgelaufen!");
		if (messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader").isEmpty())
			messagesYml.set("Party.Command.Invite.InvitationTimedOutLeader",
					"&5Der Spieler&6 [PLAYER] &5hat &5die &5Einladung &5nicht &5angenommen!");
		if (messagesYml.getString("Party.Command.Join.PlayerHasNoParty").isEmpty())
			messagesYml.set("Party.Command.Join.PlayerHasNoParty", "&cDieser &cSpieler &chat &ckeine &cParty.");
		if (messagesYml.getString("Party.Command.Join.AlreadyInAPartyError").isEmpty())
			messagesYml.set("Party.Command.Join.AlreadyInAPartyError",
					"&cDu &cbist &cbereits &cin &ceiner &cParty. &cNutze &6/party leave &cum &cdiese &cParty &czu &cverlassen.");
		if (messagesYml.getString("Party.Command.Join.PlayerHasJoined").isEmpty())
			messagesYml.set("Party.Command.Join.PlayerHasJoined",
					"&bDer &bSpieler &6[PLAYER] &bist &bder &bParty &bbeigetreten.");
		if (messagesYml.getString("Party.Command.Join.ErrorNoInvitation").isEmpty())
			messagesYml.set("Party.Command.Join.ErrorNoInvitation", "&cDu &ckannst &cder &cParty &cnicht &cbeitreten.");
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers").isEmpty())
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers",
					"&bDer &bSpieler &6[PLAYER] &bwurde &baus &bder &bParty &bgekickt.");
		if (messagesYml.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer").isEmpty())
			messagesYml.set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer",
					"&bDu &bwurdest &baus &bder &bParty &bgekickt.");
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").isEmpty())
			messagesYml.set("Party.Command.Kick.Party.Command.Leader.NewLeaderIs",
					"&7Der &7neue &7Party &7Leiter &7ist &6[PLAYER].");
		if (messagesYml.getString("Party.Command.Leader.NewLeaderIs").isEmpty())
			messagesYml.set("Party.Command.Leader.NewLeaderIs", "&7Der &7neue &7Party &7Leiter &7ist &6[PLAYER].");
		if (messagesYml.getString("Party.Command.Leave.YouLeftTheParty").isEmpty())
			messagesYml.set("Party.Command.Leave.YouLeftTheParty", "&bDu &bhast &bdeine &bParty &bverlassen.");
		if (messagesYml.getString("Party.Command.Leave.NewLeaderIs").isEmpty())
			messagesYml.set("Party.Command.Leave.NewLeaderIs",
					"&bDer &bLeader &bhat &bdie &bParty &bverlassen. &bDer &bneue &bLeader &bist &e[NEWLEADER].");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Tritt &5der &5Party &5mit &6/Party join [PLAYER] &5bei!");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
					"&5Tritt &5der &5Party &5mit &6/Party join [PLAYER] &5bei!");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
					"&aHier klicken um Party einladung anzunehmen");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
					"&aHier klicken um Party einladung anzunehmen");
		if (messagesYml.getString("Party.Command.Invite.YouWereInvitedBY").isEmpty())
			messagesYml.set("Party.Command.Invite.YouWereInvitedBY",
					"&5Du &5wurdest &5in &5die &5Party &5von&6 [PLAYER] &5eingeladen!");
		loadSharedMessages(messagesYml);
		return messagesYml;
	}

	private static Configuration loadSharedMessages(Configuration messagesYml) {
		if (messagesYml.getString("Party.General.PartyPrefix").isEmpty())
			messagesYml.set("Party.General.PartyPrefix", "&7[&5Party&7] ");
		if (messagesYml.getString("Friends.General.HelpBegin").isEmpty())
			messagesYml.set("Friends.General.HelpBegin",
					"&8&m-------------------&r&8[&5&lFriends&8]&m-------------------");
		if (messagesYml.getString("Friends.General.HelpEnd").isEmpty())
			messagesYml.set("Friends.General.HelpEnd", "&8&m-----------------------------------------------");
		if (messagesYml.getString("Party.General.HelpBegin").isEmpty())
			messagesYml.set("Party.General.HelpBegin", "&8&m-------------------&r&8[&5&lParty&8]&m-------------------");
		if (messagesYml.getString("Party.General.HelpEnd").isEmpty())
			messagesYml.set("Party.General.HelpEnd", "&8&m---------------------------------------------");
		if (messagesYml.getString("Party.Command.Chat.Prefix").isEmpty())
			messagesYml.set("Party.Command.Chat.Prefix", "&7[&5PartyChat&7] ");
		if (messagesYml.getString("Party.Command.Chat.ContentColor").isEmpty())
			messagesYml.set("Party.Command.Chat.ContentColor", "&7");
		if (messagesYml.getString("Party.Command.Chat.PartyChatOutput").isEmpty())
			messagesYml.set("Party.Command.Chat.PartyChatOutput", "&e[SENDERNAME]&5:[MESSAGE_CONTENT]");
		if (messagesYml.getString("Party.Command.Info.PlayersCut").isEmpty())
			messagesYml.set("Party.Command.Info.PlayersCut", "&7, &b");
		if (messagesYml.getString("Party.Command.Info.Leader").isEmpty())
			messagesYml.set("Party.Command.Info.Leader", "&3Leader&7: &5[LEADER]");
		if (messagesYml.getString("Party.Command.Info.Players").isEmpty())
			messagesYml.set("Party.Command.Info.Players", "&8Players&7: &b");
		if (messagesYml.getString("Friends.Command.MSG.SendedMessage").isEmpty())
			messagesYml.set("Friends.Command.MSG.SendedMessage", " &e[SENDER]&5-> &e[PLAYER]&7: [CONTENT]");
		if (messagesYml.getString("Friends.Command.Settings.SplitLine").isEmpty())
			messagesYml.set("Friends.Command.Settings.SplitLine",
					"&8&m-----------------------------------------------");
		if (messagesYml.getString("Friends.Command.List.OnlineTitle").isEmpty())
			messagesYml.set("Friends.Command.List.OnlineTitle", "(online)");
		if (messagesYml.getString("Friends.Command.List.OnlineColor").isEmpty())
			messagesYml.set("Friends.Command.List.OnlineColor", "&a");
		if (messagesYml.getString("Friends.Command.List.OfflineTitle").isEmpty())
			messagesYml.set("Friends.Command.List.OfflineTitle", "(offline)");
		if (messagesYml.getString("Friends.Command.List.OfflineColor").isEmpty())
			messagesYml.set("Friends.Command.List.OfflineColor", "&c");
		if (messagesYml.getString("Friends.Command.List.PlayerSplit").isEmpty())
			messagesYml.set("Friends.Command.List.PlayerSplit", "&7, ");
		if (messagesYml.getString("Friends.Command.MSG.ColorOfMessage").isEmpty())
			messagesYml.set("Friends.Command.MSG.ColorOfMessage", " &7");
		return messagesYml;
	}

	private static void process(Configuration pMessagesYML) {
		for (String key : pMessagesYML.getKeys()) {
			Object entry = pMessagesYML.get(key);
			if (entry instanceof LinkedHashMap)
				process(pMessagesYML.getSection(key));
			else if (entry instanceof String) {
				String stringEntry = (String) entry;
				stringEntry = ChatColor.translateAlternateColorCodes('&', stringEntry);
				stringEntry = fixColors(stringEntry);
				pMessagesYML.set(key, ChatColor.translateAlternateColorCodes('&', stringEntry));
			}
		}
	}

	private static String fixColors(String pInput) {
		String[] split = pInput.split(" ");
		StringBuilder composite = new StringBuilder("");
		String colorCode = "";
		for (String input : split) {
			if (!input.startsWith("§"))
				input = colorCode + input;
			int index = input.lastIndexOf('§');
			if (index != -1)
				if (input.length() > index)
					colorCode = "§" + input.charAt(index + 1);
			composite.append(' ').append(input);
		}
		String composited = composite.toString();
		if (composited.length() > 0)
			composited = composited.substring(1);
		if (pInput.endsWith(" "))
			composited += (' ');
		return composited;
	}

}
