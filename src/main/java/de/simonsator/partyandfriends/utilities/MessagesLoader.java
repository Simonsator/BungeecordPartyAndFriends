package de.simonsator.partyandfriends.utilities;

import java.io.File;
import java.io.IOException;

/**
 * This class loads the Messages.yml
 *
 * @author Simonsator
 * @version 1.0.1
 */
public class MessagesLoader extends LanguageConfiguration {

	public MessagesLoader(Language pLanguage, File pFile) throws IOException {
		super(pLanguage, pFile);
		switch (pLanguage) {
			case OWN:
				if (!pFile.exists()) pFile.createNewFile();
				readFile();
				break;
			case GERMAN:
				loadGermanMessages();
				break;
			default:
				break;
		}
		loadEnglishMessages();
		loadSharedMessages();
		if (pLanguage == Language.OWN)
			saveFile();
		process(configuration);
	}


	private void loadEnglishMessages() {
		set("General.DisabledServer", "&cThis command cannot be executed here.");
		set("Party.Error.CommandNotFound", "&cThis command does not exist!");
		set("Party.CommandUsage.Join", "&8/&5Party join  &8- &7Join a party");
		set("Party.CommandUsage.Invite", "&8/&5Party invite  &8- &7Invite a player into your party");
		set("Party.CommandUsage.List", "&8/&5Party list &8- &7List all players who are in the party");
		set("Party.CommandUsage.Chat", "&8/&5Party chat  &8- &7Send all players in the party a message");
		set("Party.CommandUsage.Leave", "&8/&5Party leave &8- &7Leave the party");
		set("Party.CommandUsage.Kick", "&8/&5Party kick  &8- &7Kicks a player out of the party");
		set("Party.CommandUsage.Leader", "&8/&5Party leader &5 &8- &7Makes another player to the party leader");
		set("Party.Command.General.ErrorNoParty", "&5You need to be in a party.");
		set("Party.Command.General.ErrorNotPartyLeader", "&cYou are not the party leader.");
		set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty", "&cThe player &e[PLAYER] &cis not in the party.");
		set("Party.Command.General.ErrorNoPlayer", "&cYou need to specify a player.");
		set("Party.Command.General.ErrorPlayerNotOnline", "&cThis player is not online.");
		set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers", "&5The party was dissolved because of too less players.");
		set("Party.Command.General.PlayerHasLeftTheParty", "&bThe player &6[PLAYER] &bhas left the party.");
		set("Party.Command.General.ServerSwitched", "&bThe party has joined the Server &e[SERVER]&b.");
		set("Party.Command.Chat.ErrorNoMessage", "&5You need to give in a message.");
		set("Party.Command.Info.Empty", "empty");
		set("Party.Command.Invite.GivenPlayerEqualsSender", "&7You are not allowed to invite yourself.");
		set("Party.Command.Invite.CanNotInviteThisPlayer", "&cYou cannot invite this player into your Party.");
		set("Party.Command.Invite.AlreadyInAParty", "&cThis player is already in a party.");
		set("Party.Command.Invite.AlreadyInYourParty", "&cThe player &e[PLAYER] &cis already invited into your party.");
		set("Party.Command.Invite.MaxPlayersInPartyReached", "&cThe max size of a party is [MAXPLAYERSINPARTY].");
		set("Party.Command.Invite.InvitedPlayer", "&6[PLAYER] &bwas invited to your party.");
		set("Party.Command.Invite.YouWereInvitedBY", "&5You were invited into &6[PLAYER]'s &5party!");
		set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE",
				"&5Join the party by using the command &6/Party &6join &6[PLAYER]&5!");
		set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER", "&aClick here to join the party");
		set("Party.Command.Invite.InvitationTimedOutInvited", "&5The invitation of &6[PLAYER]'s &5party is timed out!");
		set("Party.Command.Invite.InvitationTimedOutLeader", "&5The player &6[PLAYER] &5has not accepted your invitation!");
		set("Party.Command.Join.PlayerHasNoParty", "&cThis player does not own a party.");
		set("Party.Command.Join.AlreadyInAPartyError",
				"&cYou are already in a party. Use &6/party leave &cto leave this party.");
		set("Party.Command.Join.PlayerHasJoined", "&bThe player &6[PLAYER] &bjoined the party.");
		set("Party.Command.Join.ErrorNoInvitation", "&cYou cannot join this party.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers", "&bThe player &6[PLAYER] &bwas kicked out of the party.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer", "&bYou have been kicked out of the party.");
		set("Party.Command.Leader.SenderEqualsGivenPlayer", "&7You cannot make yourself to the new party leader.");
		set("Party.Command.Leader.NewLeaderIs", "&7The new party leader is &6[NEWLEADER]");
		set("Party.Command.Leave.NewLeaderIs", "&bThe leader has left the party. The new leader is &e[NEWLEADER].");
		set("Party.Command.Leave.YouLeftTheParty", "&bYou left your party.");
		set("Friends.General.CommandNotFound", " &7The command does not exist.");
		set("Friends.General.PlayerIsOffline", " &7The player &e[PLAYER] &7is not online or you are not his friend.");
		set("Friends.General.NotAFriendOfOrOffline", " &7The player &e[PLAYER] &7is not online or you are not his friend.");
		set("Friends.General.NoFriendGiven", " &7You need to specify a friend.");
		set("Friends.General.NoPlayerGiven", " &7You need to specify a player.");
		set("Friends.General.TooManyArguments", " &7Too many arguments.");
		set("Friends.General.PlayerIsNowOffline", " &7Your friend &e[PLAYER] &7is &coffline&7 now.");
		set("Friends.General.PlayerIsNowOnline", " &7The friend &e[PLAYER] &7is &aonline&7 now.");
		set("Friends.General.RequestInfoOnJoin", " &7You &7have &7friend &7requests &7from: [FRIENDREQUESTS]");
		set("Friends.General.DoesNotExist", " &7The given player &7does not &7exist");
		set("Friends.General.GivenPlayerEqualsSender", " &7You cannot specify yourself as player argument.");
		set("Friends.GUI.Hide.ShowAllPlayers", " &aNow all players will be shown.");
		set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer", " &eNow only friends and people of server team will be shown.");
		set("Friends.GUI.Hide.ShowOnlyFriends", " &6Now only your friends will be shown.");
		set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer", " &5Now only players from the server team will be shown.");
		set("Friends.GUI.Hide.ShowNobody", " &cNow no players will be shown.");
		set("Friends.CommandUsage.List", "&8/&5friend list &8- &7Lists all your friends");
		set("Friends.CommandUsage.MSG", "&8/&5friend msg [name of the friend] [message]&r &8- &7Send a message to a friend");
		set("Friends.CommandUsage.ADD", "&8/&5friend add [name of the player]&r &8- &7Add a friend");
		set("Friends.CommandUsage.Accept", "&8/&5friend accept [name of the player]&r &8- &7Accept a friend request");
		set("Friends.CommandUsage.Deny", "&8/&5friend deny [name of the player]&r &8- &7Deny a friend request");
		set("Friends.CommandUsage.Remove", "&8/&5friend &5remove &5[name &5of &5the &5friend]&r &8- &7Removes &7a &7friend");
		set("Friends.CommandUsage.Jump", "&8/&5friend jump [name of the friend]&r &8- &7Jump to a friend");
		set("Friends.CommandUsage.Settings", "&8/&5friend settings &r&8- &7Change the settings");
		set("Friends.Command.Accept.NowFriends", " &7You and &e[PLAYER] &7are now friends");
		set("Friends.Command.Accept.ErrorNoFriendShipInvitation", " &7You did not receive a friend request from &e[PLAYER]&7.");
		set("Friends.Command.Accept.ErrorSenderEqualsReceiver", " &7You cannot message yourself.");
		set("Friends.Command.Accept.ErrorAlreadySend", " &7You already have sent the player &e[PLAYER] &7a friend request.");
		set("Friends.Command.Add.SenderEqualsReceiver", " &7You cannot send yourself a friend request.");
		set("Friends.Command.Add.FriendRequestFromReceiver",
				" &7The player &e[PLAYER] &7has already sent you a friend request.");
		set("Friends.Command.Add.FriendRequestReceived", " &7You have received a friend request from &e[PLAYER]&7.");
		set("Friends.Command.Add.ClickHere", "&aClick here to accept the friend request");
		set("Friends.Command.Add.SentAFriendRequest", " &7The player &e[PLAYER] &7received a friend request from you.");
		set("Friends.Command.Add.CanNotSendThisPlayer", " &7You cannot send the player &e[PLAYER] &7a friend request.");
		set("Friends.Command.Add.HowToAccept", " &7Accept the friend request with &6/friend accept [PLAYER]&7.");
		set("Friends.Command.Add.AlreadyFriends", " &7You and &e[PLAYER] &7are already friends.");
		set("Friends.Command.Deny.HasDenied", " &7You have denied &e[PLAYER]s&7 friend request.");
		set("Friends.Command.Deny.NoFriendRequest", " &7You did not receive a friend request from &e[PLAYER]&7.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone", " &7Now you can get invited into a party by &aevery &7player.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByFriends", " &7Now you can &conly &7get invited into a party by your friends.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests", " &7Now you are &cnot going to &7receive friend requests anymore.");
		set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests", " &7Now you are &agoing to &7receive friend requests from everyone.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveMessages", " &7Now you are &cnot going to &7receive messages anymore.");
		set("Friends.Command.Settings.NowYouWillBeShowAsOnline", " &7Now you will be shown as &aonline.");
		set("Friends.Command.Settings.NowYouWilBeShownAsOffline", " &7Now you will be shown as &coffline.");
		set("Friends.Command.Settings.NowNoMessages", " &7Now you are &cnot going to &7receive messages anymore.");
		set("Friends.Command.Settings.NowMessages", " &7Now you are &agoing to &7receive messages from everyone.");
		set("Friends.Command.Settings.NowYourFriendsCanJump", " &7Now your friends &acan jump &7to you.");
		set("Friends.Command.Settings.NowYourFriendsCanNotJump", " &7Now your friends &ccan not jump &7to you.");
		set("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests",
				" &7At the moment you are &cnot going to &7receive friend request.");
		set("Friends.Command.Settings.AtTheMomentYouAreGoneReceiveFriendRequests",
				" &7At the moment you are going to receive friend requests from &aeveryone.&7");
		set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
				" &7At the moment you can get invited into a party by &aevery &7player.");
		set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
				" &7At the moment you can get invited &aonly &7by by your friends into their Party.");
		set("Friends.Command.Settings.ChangeThisSettingsHover", "&aClick here to change this setting.");
		set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests", " &7Change this setting with &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.ChangeThisSettingWithParty", " &7Change this setting with &6/friend settings Party&7.");
		set("Friends.Command.Jump.AlreadyOnTheServer", " &7You are already on this server.");
		set("Friends.Command.Jump.JoinedTheServer", " &7Now you are on the same server, like the player &e[PLAYER]&7.");
		set("Friends.Command.Jump.CanNotJump", " &7You cannot jump to this person.");
		set("Friends.Command.List.NoFriendsAdded", " &7Until now, you did not add friends.");
		set("Friends.Command.List.FriendsList", " &7These are your friends:LINE_BREAK &7- ");
		set("Friends.Command.List.OnlineTitle", " &a(online)&7, currently playing on [SERVER_ON]");
		set("Friends.Command.List.OfflineTitle", " &c(offline)&7, last seen at [LAST_ONLINE]");
		set("Friends.Command.List.TimeColor", "&7");
		set("Friends.Command.MSG.CanNotWriteToHim", " &7You cannot message this player.");
		set("Friends.Command.MSG.NoOneEverWroteToYou", " &7No player ever messaged you.");
		set("Friends.Command.MSG.PlayerAndMessageMissing", " &7You need to give a message.");
		set("Friends.Command.Remove.Removed", " &7You removed the friend &e[PLAYER]&7.");
	}

	private void loadGermanMessages() {
		set("General.DisabledServer", "&cDieses Kommando kann hier nicht ausgeführt werden.");
		set("Friends.Command.Add.SentAFriendRequest", " &7Dem Spieler &e[PLAYER] &7wurde eine Freundschaftsanfrage gesendet.");
		set("Friends.General.CommandNotFound", " &7Das Kommando existiert nicht.");
		set("Friends.General.PlayerIsOffline", " &7Der Spieler &e[PLAYER] &7ist nicht online oder du bist nicht mit ihm befreundet.");
		set("Friends.General.NoPlayerGiven", " &7Du musst einen Spieler angeben.");
		set("Friends.General.PlayerIsNowOnline", " &e[PLAYER] &7ist jetzt &aonline&7.");
		set("Friends.General.RequestInfoOnJoin", " &7Freundschaftsanfragen stehen von den folgenden Spielern aus: [FRIENDREQUESTS]");
		set("Friends.General.PlayerIsNowOffline", " &7Der Freund &e[PLAYER] &7ist nun &coffline&7.");
		set("Friends.GUI.Hide.ShowAllPlayers", " &aDir werden jetzt alle Spieler angezeigt.");
		set("Friends.GUI.Hide.ShowOnlyFriendsAndPeopleFromTheServer", " &eDir werden jetzt nur noch Freunde und Leute vom Server angezeigt.");
		set("Friends.GUI.Hide.ShowOnlyFriends", " &6Dir werden jetzt nur noch deine Freunde angezeigt.");
		set("Friends.GUI.Hide.ShowOnlyPeopleFromTheServer", " &5Dir werden jetzt nur noch Spieler vom Server Team angezeigt.");
		set("Friends.GUI.Hide.ShowNobody", " &cDir werden jetzt keine Spieler mehr angezeigt.");
		set("Friends.CommandUsage.List", "&8/&5friend list&r &8- &7Listet deine Freunde auf");
		set("Friends.CommandUsage.MSG", "&8/&5friend msg [Name des Freundes] [Nachricht]&r &8- &7schickt einem Freund eine private Nachricht");
		set("Friends.CommandUsage.ADD", "&8/&5friend add [Name des Spielers]&r &8- &7Fügt einen Freund hinzu");
		set("Friends.CommandUsage.Accept", "&8/&5friend accept [Name des Spielers]&r &8- &7Akzeptiert eine Freundschaftsanfrage");
		set("Friends.CommandUsage.Deny", "&8/&5friend deny [Name des Spielers]&r &8- &7Lehnt eine Freundschaftsanfrage ab");
		set("Friends.CommandUsage.Remove", "&8/&5friend remove [Name des Spielers]&r &8- &7Entfernt einen Freund");
		set("Friends.CommandUsage.Jump", "&8/&5friend jump [Name des Freundes]&r&8- &7Zu einem Freund springen");
		set("Friends.CommandUsage.Settings", "&8/&5friend settings &r&8- &7Ändere die Einstellungen");
		set("Friends.Command.Accept.NowFriends", " &7Du bist jetzt mit &e[PLAYER] &7befreundet.");
		set("Friends.Command.Accept.ErrorAlreadySend", " &7Du hast dem Spieler &e[PLAYER] &7bereits eine Freundschaftsanfrage gesendet.");
		set("Friends.Command.Accept.ErrorNoFriendShipInvitation", " &7Du hast keine Freundschaftsanfrage von &e[PLAYER] &7erhalten.");
		set("Friends.Command.Add.FriendRequestFromReceiver", " &7Der Spieler &e[PLAYER] &7hat dir schon eine Freundschaftsanfrage gesendet.");
		set("Friends.Command.Add.FriendRequestReceived", " &7Du hast eine Freundschaftsanfrage von &e[PLAYER]&7 erhalten.");
		set("Friends.Command.Add.HowToAccept", " &7Nimm sie mit &6/friend accept [PLAYER] &7an.");
		set("Friends.Command.Add.ClickHere", "&aHier klicken um die Freundschaftsanfrage anzunehmen");
		set("Friends.Command.Add.AlreadyFriends", " &7Du bist schon mit &e[PLAYER] &7befreundet.");
		set("Friends.Command.Accept.ErrorSenderEqualsReceiver", " &7Du kannst dir nicht selber eine Freundschaftsanfrage senden.");
		set("Friends.General.DoesNotExist", " &7Der gegebene Spieler existiert nicht.");
		set("Friends.Command.Add.CanNotSendThisPlayer", " &7Du kannst dem Spieler &e[PLAYER] &7keine Freundschaftsanfrage senden.");
		set("Friends.Command.Deny.HasDenied", " &7Du hast die Anfrage von &e[PLAYER] &7abgelehnt.");
		set("Friends.Command.Jump.CanNotJump", " &7Du kannst nicht zu dieser Person springen.");
		set("Friends.Command.Jump.AlreadyOnTheServer", " &7Du bist bereits auf diesem Server.");
		set("Friends.Command.Jump.JoinedTheServer", " &7Du bist jetzt auf dem gleichen Server wie der Spieler &e[PLAYER]&7.");
		set("Friends.Command.List.FriendsList", " &7Dies sind deine Freunde:LINE_BREAK &7- ");
		set("Friends.Command.List.NoFriendsAdded", " &7Du hast noch keine Freunde hinzugefügt.");
		set("Friends.Command.List.OnlineTitle", " &a(online)&7, spielt gerade auf [SERVER_ON]");
		set("Friends.Command.List.OfflineTitle", " &c(offline)&7, zuletzt online [LAST_ONLINE]");
		set("Friends.Command.Remove.Removed", " &7Du hast den Freund &e[PLAYER] &7entfernt.");
		set("Friends.Command.Settings.AtTheMomentYouAreNotGoneReceiveFriendRequests",
				" &7Momentan können dir &ckeine &7Freundschaftsanfragen gesendet werden.");
		set("Friends.Command.Settings.AtTheMomentYouAreGoneReceiveFriendRequests",
				" &7Momentan erhälst du Freundschaftsanfragen von &ajedem&7.");
		set("Friends.Command.Settings.AtTheMomentYouCanGetInvitedByEverybodyIntoHisParty",
				" &7Momentan können dir Partyeinladungen von &ajedem &7gesendet werden gesendet werden.");
		set("Friends.Command.Settings.ChangeThisSettingWithFriendrequests",
				" &7Ändere diese Einstellung mit &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.AtTheMomentYouCanNotGetInvitedByEverybodyIntoHisParty",
				" &7Momentan können dir &cnur &7Partyeinladungen von Freunden gesendet werden.");
		set("Friends.Command.Settings.ChangeThisSettingWithParty", " &7Ändere diese Einstellung mit &6/friend settings party");
		set("Friends.Command.Settings.ChangeThisSettingsHover", "&aHier klicken um die Einstellung zu ändern.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone", " &7Du kannst jetzt von &ajedem &7Spieler in eine Party eingeladen werden.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
				" &7Du kannst jetzt &cnur &7noch von deinen Freunden in eine Party eingeladen werden.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests", " &7Du kannst jetzt &ckeine &7Freundschaftsanfragen mehr erhalten.");
		set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests", " &7Du kannst jetzt von &ajedem &7Freundschaftsanfragen erhalten.");
		set("Friends.Command.Settings.NowYouWillBeShowAsOnline", " &7Du wirst nun als &aonline &7angezeigt.");
		set("Friends.Command.Settings.NowYouWilBeShownAsOffline", " &7Du wirst nun als &coffline &7angezeigt.");
		set("Friends.Command.Settings.NowNoMessages", " &7Du kannst jetzt &ckeine &7Nachrichten mehr erhalten.");
		set("Friends.Command.Settings.NowMessages", " &7Du kannst jetzt von &ajedem &7Nachrichten erhalten.");
		set("Friends.Command.Settings.NowYourFriendsCanJump", " &7Freunde können jetzt zu dir &aspringen&7.");
		set("Friends.Command.Settings.NowYourFriendsCanNotJump", " &7Freunde können jetzt &cnicht &7zu dir springen.");
		set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		set("Friends.Command.MSG.NoOneEverWroteToYou", " &7Noch kein Spieler hat dich angeschrieben.");
		set("Party.General.ErrorNotPartyLeader", "&cDu bist nicht der Party Leader.");
		set("Party.Leader.SenderEqualsGivenPlayer", "&7Du kannst dich nicht selber zum neuen Party Leader machen.");
		set("Party.General.ErrorGivenPlayerIsNotInTheParty", "&cDer Spieler &e[PLAYER] &7ist nicht in der Party.");
		set("Party.CommandUsage.Join", "&8/&5Party join <Name> &8- &7Trete einer Party bei");
		set("Party.CommandUsage.Invite", "&8/&5Party invite <Name> &8- &7Lade einen Spieler in deine Party ein");
		set("Party.CommandUsage.List", "&8/&5Party list &8- &7Listet alle Spieler in der Party auf");
		set("Party.CommandUsage.Chat", "&8/&5Party chat <Nachricht> &8- &7Sendet allen Spieler in der Party eine Nachicht");
		set("Party.CommandUsage.Leave", "&8/&5Party leave &8- &7Verlässt die Party");
		set("Party.CommandUsage.Kick", "&8/&5Party kick <Spieler> &8- &7Kickt einen Spieler aus der Party");
		set("Party.CommandUsage.Leader", "&8/&5Party leader <Spieler> &8- &7Macht einen anderen Spieler zum Leader");
		set("Party.Error.CommandNotFound", "&cDieser Befehl existiert nicht!");
		set("Party.Command.General.PlayerHasLeftTheParty", "&bDer Spieler &6[PLAYER] hat die Party verlassen.");
		set("Party.Command.General.ErrorNoParty", "&cDu bist in keiner Party.");
		set("Party.Command.General.ErrorNotPartyLeader", "&cDu bist nicht der Party Leader.");
		set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers", "&5Die Party wurde wegen zu wenig Mitgliedern aufgelöst.");
		set("Party.Command.General.ServerSwitched", "&bDie Party hat den Server &e[SERVER] &bbetreten.");
		set("Party.Command.Chat.ErrorNoMessage", "&5Du musst eine Nachricht eingeben.");
		set("Party.Command.Info.Empty", "Leer");
		set("Party.Command.Invite.CanNotInviteThisPlayer", "&cDieser Spieler ist nicht online.");
		set("Party.Command.Invite.GivenPlayerEqualsSender", "&7Du darfst dich nicht selber einladen.");
		set("Party.Command.Invite.AlreadyInAParty", "&cDieser Spieler ist bereits in einer Party.");
		set("Party.Command.Invite.AlreadyInYourParty", "&cDieser Spieler &e[PLAYER] &cist schon in die Party eingeladen.");
		set("Party.Command.Invite.MaxPlayersInPartyReached", "&cDie maximale Größe für eine Party ist [MAXPLAYERSINPARTY].");
		set("Party.Command.Invite.InvitedPlayer", "&bDu hast &6[PLAYER] &bin deine Party eingeladen.");
		set("Party.Command.Invite.InvitationTimedOutInvited", "&5Die Einladung in die Party von &6[PLAYER] &5ist abgelaufen!");
		set("Party.Command.Invite.InvitationTimedOutLeader", "&5Der Spieler &6[PLAYER] &5hat die Einladung nicht angenommen!");
		set("Party.Command.Join.PlayerHasNoParty", "&cDieser Spieler hat keine Party.");
		set("Party.Command.Join.AlreadyInAPartyError", "&cDu bist bereits in einer Party. Nutze &6/party leave &cum diese Party zu verlassen.");
		set("Party.Command.Join.PlayerHasJoined", "&bDer Spieler &6[PLAYER] &bist der Party beigetreten.");
		set("Party.Command.Join.ErrorNoInvitation", "&cDu kannst der Party nicht beitreten.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers", "&bDer Spieler &6[PLAYER] &bwurde aus der Party gekickt.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer", "&bDu wurdest aus der Party gekickt.");
		set("Party.Command.Kick.Party.Command.Leader.NewLeaderIs", "&7Der neue Party Leader ist &6[NEWLEADER].");
		set("Party.Command.Leader.NewLeaderIs", "&7Der neue Party Leader ist &6[PLAYER].");
		set("Party.Command.Leave.YouLeftTheParty", "&bDu hast deine Party verlassen.");
		set("Party.Command.Leave.NewLeaderIs", "&bDer Leader hat die Party verlassen. Der neue Leader ist &e[NEWLEADER].");
		set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE", "&5Tritt der Party mit &6/party join [PLAYER] &5bei!");
		set("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER",
				"&aHier klicken um die Partyeinladung anzunehmen");
		set("Party.Command.Invite.YouWereInvitedBY",
				"&5Du wurdest in die Party von &6[PLAYER] &5eingeladen!");
	}

	private void loadSharedMessages() {
		set("Friends.General.Prefix", "&8[&5&lFriends&8]");
		set("Party.General.PartyPrefix", "&7[&5Party&7] ");
		set("Friends.General.HelpBegin",
				"&8&m-------------------&r&8[&5&lFriends&8]&m-------------------");
		set("Friends.General.HelpEnd", "&8&m-----------------------------------------------");
		set("Party.General.HelpBegin",
				"&8&m-------------------&r&8[&5&lParty&8]&m-------------------");
		set("Party.General.HelpEnd", "&8&m---------------------------------------------");
		set("Party.Command.Chat.Prefix", "&7[&5PartyChat&7] ");
		set("Party.Command.Chat.ContentColor", "&7");
		set("Party.Command.Chat.PartyChatOutput", "&e[SENDERNAME]&5:[MESSAGE_CONTENT]");
		set("Party.Command.Info.PlayersCut", "&7, &b");
		set("Party.Command.Info.Leader", "&3Leader&7: &5[LEADER]");
		set("Party.Command.Info.Players", "&8Players&7: &b");
		set("Friends.General.RequestInfoOnJoinColor", "&e");
		set("Friends.General.RequestInfoOnJoinColorComma", "&7");
		set("Friends.Command.MSG.SentMessage", " &e[SENDERNAME] &5-> &e[PLAYER]&7:[CONTENT]");
		set("Friends.Command.Settings.SplitLine",
				"&8&m-----------------------------------------------");
		set("Friends.Command.List.OnlineColor", "&a");
		set("Friends.Command.List.OfflineColor", "&c");
		set("Friends.Command.List.PlayerSplit", "LINE_BREAK &7- ");
		set("Friends.Command.MSG.ColorOfMessage", " &7");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new MessagesLoader(LANGUAGE, FILE)).getCreatedConfiguration();
	}
}
