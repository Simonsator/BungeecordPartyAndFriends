package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.api.PAFPluginBase;
import de.simonsator.partyandfriends.main.Main;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * This class loads the Messages.yml
 *
 * @author Simonsator
 * @version 1.0.1
 */
public class MessagesLoader extends LanguageConfiguration {
	private final boolean USE_CUSTOM_MESSAGES;

	public MessagesLoader(Language pLanguage, boolean useCustomMessages, File pFile, PAFPluginBase pPlugin) throws IOException {
		super(pLanguage, pFile, pPlugin, true);
		USE_CUSTOM_MESSAGES = useCustomMessages;
		copyFromJar();
		readFile();
		switch (pLanguage) {
			case GERMAN:
				loadGermanMessages();
				break;
			default:
				break;
		}
		loadEnglishMessages();
		loadSharedMessages();
		if (USE_CUSTOM_MESSAGES)
			saveFile();
		process();
	}

	private void loadEnglishMessages() {
		set("General.DisabledServer", "&cThis command cannot be executed here.");
		set("General.UnableToConnectToServerRedisBungee", "&cThe server could not be joined.");
		set("Party.Error.CommandNotFound", "&cThis command does not exist!", "&cThis command does not exist!");
		set("Party.Error.NoPermission", "&cYou don't have the permission to execute this command!");
		set("Party.CommandUsage.Join", "&8/&5Party join  &8- &7Join a party");
		set("Party.CommandUsage.Deny", "&8/&5Party deny  &8- &7Deny a party invitation");
		set("Party.CommandUsage.Invite", "&8/&5Party invite  &8- &7Invite a player into your party");
		set("Party.CommandUsage.InviteSetting", "&8/&5Party setting  &8- &7Enables/disables party invites");
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
		set("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers", "&5The party was dissolved because there are not enough players.");
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
		set("Party.Command.Invite.AcceptInvite",
				"&aJoin the party by using the command &6/Party &6join &6[PLAYER]&a!");
		set("Party.Command.Invite.AcceptInviteHOVER", "&aClick here to join the party");
		set("Party.Command.Invite.DeclineInvite",
				"&cDecline the invitation by using the command &6/Party &6deny &6[PLAYER]&c!");
		set("Party.Command.Invite.DeclineInviteHOVER", "&cClick here to decline the invitation");
		set("Party.Command.Invite.InvitationTimedOutInvited", "&5The invitation of &6[PLAYER]'s &5party is timed out!");
		set("Party.Command.Invite.InvitationTimedOutLeader", "&5The player &6[PLAYER] &5has not accepted your invitation!");
		set("Party.Command.Join.PlayerHasNoParty", "&cThis player does not own a party.");
		set("Party.Command.Join.AlreadyInAPartyError",
				"&cYou are already in a party. Use &6/party leave &cto leave this party.");
		set("Party.Command.Join.PlayerHasJoined", "&bThe player &6[PLAYER] &bjoined the party.");
		set("Party.Command.Join.ErrorNoInvitation", "&cYou cannot join this party.");
		set("Party.Command.Join.MaxPlayersInPartyReached", "&cThe max size of a party is [MAXPLAYERSINPARTY].");
		set("Party.Command.Deny.PlayerHasNoParty", "&cThis player does not own a party.");
		set("Party.Command.Deny.PlayerHasDeniedInvitation", "&bThe player &6[PLAYER] &bhas declined the invitation.");
		set("Party.Command.Deny.DeniedInvitation", "&bYou denied the party invitation.");
		set("Party.Command.Deny.ErrorNoInvitation", "&cYou are not invited into that party.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers", "&bThe player &6[PLAYER] &bwas kicked out of the party.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer", "&bYou have been kicked out of the party.");
		set("Party.Command.Leader.SenderEqualsGivenPlayer", "&7You cannot make yourself the party leader.");
		set("Party.Command.Leader.NewLeaderIs", "&7The new party leader is &6[NEWLEADER]");
		set("Party.Command.Leave.NewLeaderIs", "&bThe leader has left the party. The new leader is &e[NEWLEADER].");
		set("Party.Command.Leave.YouLeftTheParty", Collections.singletonList("&bYou left your party."));
		set("Friends.General.CommandNotFound", " &cThe command does not exist.", " &cI am sorry, but the command you were searching for was not found.");
		set("Friends.General.NoPermission", " &cYou don't have the permission to execute this command.");
		set("Friends.General.PlayerIsOffline", " &7The player &e[PLAYER] &7is not online or you are not his friend.");
		set("Friends.General.NotAFriendOfOrOffline", " &7The player &e[PLAYER] &7is not online or you are not his friend.");
		set("Friends.General.NoFriendGiven", " &7You need to specify a friend.");
		set("Friends.General.NoPlayerGiven", " &7You need to specify a player.");
		set("Friends.General.PlayerIsNowOffline", " &7Your friend &e[PLAYER] &7is &coffline &7now.");
		set("Friends.General.PlayerIsNowOnline", " &7Your friend &e[PLAYER] &7is &aonline &7now.");
		set("Friends.General.RequestInfoOnJoin", " &7You &7have &7friend &7requests &7from: [FRIENDREQUESTS]");
		set("Friends.General.DoesNotExist", " &7The given player &7does not &7exist");
		set("Friends.General.GivenPlayerEqualsSender", " &7You cannot specify yourself as player argument.");
		set("Friends.General.ServerSwitched", " &7Your friend &e[PLAYER] &7has joined the server &e[SERVER]&7.");
		set("Friends.CommandUsage.List", "&8/&5friend list &8- &7Lists all your friends");
		set("Friends.CommandUsage.MSG", "&8/&5friend msg [name of the friend] [message]&r &8- &7Send a message to a friend");
		set("Friends.CommandUsage.ADD", "&8/&5friend add [name of the player]&r &8- &7Add a friend");
		set("Friends.CommandUsage.Accept", "&8/&5friend accept [name of the player]&r &8- &7Accept a friend request");
		set("Friends.CommandUsage.Deny", "&8/&5friend deny [name of the player]&r &8- &7Deny a friend request");
		set("Friends.CommandUsage.Remove", "&8/&5friend &5remove &5[name &5of &5the &5friend]&r &8- &7Removes &7a &7friend");
		set("Friends.CommandUsage.Jump", "&8/&5friend jump [name of the friend]&r &8- &7Jump to a friend");
		set("Friends.CommandUsage.Settings", "&8/&5friend settings &r&8- &7Change the settings");
		set("Friends.Command.Accept.NowFriends", " &7You and &e[PLAYER] &7are now friends");
		set("Friends.Command.Accept.ErrorNoFriendShipInvitation", " &7You have not received a friend request from &e[PLAYER]&7.");
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
		set("Friends.Command.Add.FriendRequestTimedOut", " &7The Friend request from &e[PLAYER] &7has timed out.");
		set("Friends.Command.Deny.HasDenied", " &7You have denied &e[PLAYER]s&7 friend request.");
		set("Friends.Command.Deny.NoFriendRequest", " &7You have not received a friend request from &e[PLAYER]&7.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone", " &7Now you can get invited into a party by &aeveryone.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByFriends", " &7Now you can &conly &7get invited into a party by your friends.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests", " &7Now you are &cnot going to &7receive friend requests anymore.");
		set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests", " &7Now you are &agoing to &7receive friend requests from everyone.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveMessages", " &7Now you are &cnot going to &7receive messages anymore.");
		set("Friends.Command.Settings.NowYouWillBeShowAsOnline", " &7Now you will be shown as &aonline.");
		set("Friends.Command.Settings.NowYouWilBeShownAsOffline", " &7Now you will be shown as &coffline.");
		set("Friends.Command.Settings.NowYouWillReceiveOnlineStatusNotification", " &7Now you will &areceive a notification &7when a friend of yours goes online/offline.");
		set("Friends.Command.Settings.NowYouWillNotReceiveOnlineStatusNotification", " &7Now you will &cnot receive a notification &7when a friend of yours goes online/offline.");
		set("Friends.Command.Settings.NowNoMessages", " &7Now you are &cnot going to &7receive messages anymore.");
		set("Friends.Command.Settings.NowMessages", " &7Now you are &agoing to &7receive messages from everyone.");
		set("Friends.Command.Settings.NowYourFriendsCanJump", " &7Now your friends &acan jump &7to you.");
		set("Friends.Command.Settings.NowYourFriendsCanNotJump", " &7Now your friends &ccan not jump &7to you.");
		set("Friends.Command.Settings.Introduction", " &7These are your settings:");
		set("Friends.Command.Settings.FriendRequestSettingEveryone",
				"&7At the moment you are going to receive friend requests from &aeveryone&7. To change this setting use &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.FriendRequestSettingNobody",
				"&7At the moment you are &cnot going to &7receive friend requests. To change this setting use &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.PartyInvitedByEveryone",
				"&7At the moment you can get invited into a party by &aevery &7player. To change this setting use &6/friend settings invite&7.");
		set("Friends.Command.Settings.PartyInvitedByFriends",
				"&7At the moment you can get invited &conly &7by your friends into their Party. To change this setting use &6/friend settings invite&7.");
		set("Friends.Command.Settings.CanJump",
				"&7At the moment your friends &acan jump to you&7. To change this setting use &6/friend settings jump&7.");
		set("Friends.Command.Settings.CanNotJump",
				"&7At the moment your friends &ccannot &7jump to you. To change this setting use &6/friend settings jump&7.");
		set("Friends.Command.Settings.ShowAsOnline",
				"&7At the moment you are shown as &aonline&7. To change this setting use &6/friend settings offline&7.");
		set("Friends.Command.Settings.ShowAsOffline",
				"&7At the moment you are shown as &coffline&7. To change this setting use &6/friend settings offline&7.");
		set("Friends.Command.Settings.ShowOnlineStatusChangeNotification",
				"&7At the moment you are &areceiving &7a notification if the online status of a friend changes. To change this setting use &6/friend settings notifyonline&7.");
		set("Friends.Command.Settings.DoNotShowOnlineStatusChangeNotification",
				"&7At the moment you are shown as &cnot receiving &7a notification if the online status of a friend changes. To change this setting use &6/friend settings notifyonline&7.");
		set("Friends.Command.Settings.ReceivePM",
				"&7At the moment you are able to &areceive &7private messages. To change this setting use &6/friend settings message&7.");
		set("Friends.Command.Settings.DoNotReceivePM",
				"&7At the moment you are &cnot &7able to receive private messages. To change this setting use &6/friend settings message&7.");
		set("Friends.Command.Settings.ChangeThisSettingsHover", "&aClick here to change this setting.");
		set("Friends.Command.Settings.NotFound", " &7The setting which was specified could not be found or you don't have the permission to use this setting.");
		set("Friends.Command.Jump.AlreadyOnTheServer", " &7You are already on this server.");
		set("Friends.Command.Jump.JoinedTheServer", " &7Now you are on the same server, as &e[PLAYER]&7.");
		set("Friends.Command.Jump.CanNotJump", " &7You cannot jump to this person.");
		set("Friends.Command.List.NoFriendsAdded", " &7You currently do not have any friends.");
		set("Friends.Command.List.FriendsList", " &7These are your friends:LINE_BREAK &7- ");
		set("Friends.Command.List.PageDoesNotExist", " &7The given page does not exist.");
		set("Friends.Command.List.NextPage", " &7To see more friends use /friend list [PAGE].");
		set("Friends.Command.List.OnlineTitle", " &a(online)&7, currently playing on [SERVER_ON]");
		set("Friends.Command.List.OfflineTitle", " &c(offline)&7, last seen at [LAST_ONLINE]");
		set("Friends.Command.List.TimeColor", "&7");
		set("Friends.Command.MSG.CanNotWriteToHim", " &7You cannot message this player.");
		set("Friends.Command.MSG.NoOneEverWroteToYou", " &7You have not received any messages before.");
		set("Friends.Command.MSG.MessageMissing", " &7You need to give a message.");
		set("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin", " &7The player will receive the message when they go online.");
		set("Friends.Command.Remove.Removed", " &7You removed the friend &e[PLAYER]&7.");
		set("Friends.Command.Remove.FriendRemovedYou", " &e[PLAYER]&7 removed you from his friend list.");
		set("PAFAdmin.Command.DeletePlayer.PlayerDeleted", " &7All data saved by Party and Friends of the player &e[PLAYER]&7 were deleted.");
		set("PAFAdmin.Command.DeletePlayer.DeletionAborted", " &cAn extension has cancelled the deletion of the player.");
		set("PAFAdmin.Command.MustBeExecutedByConsole", "&cThis command needs to be executed by the console. This message can be changed in the messages.yml under \"AdminCommands.TopCommand.PlayerMessage\", if UseOwnLanguageFile is activated in the config.yml.");
	}

	private void loadGermanMessages() {
		set("General.DisabledServer", "&cDieses Kommando kann hier nicht ausgeführt werden.");
		set("General.UnableToConnectToServerRedisBungee", "&cDer Server konnte nicht betreten werden.");
		set("Friends.Command.Add.SentAFriendRequest", " &7Dem Spieler &e[PLAYER] &7wurde eine Freundschaftsanfrage gesendet.");
		set("Friends.General.CommandNotFound", " &cDas Kommando existiert nicht.");
		set("Friends.General.NoPermission", " &cDu hast nicht die Permssion um dieses Kommando auszuführen.");
		set("Friends.General.PlayerIsOffline", " &7Der Spieler &e[PLAYER] &7ist nicht online oder du bist nicht mit ihm befreundet.");
		set("Friends.General.NotAFriendOfOrOffline", " &7Der Spieler &e[PLAYER] &7ist nicht online oder kein Freund von dir.");
		set("Friends.General.NoPlayerGiven", " &7Du musst einen Spieler angeben.");
		set("Friends.General.NoFriendGiven", " &7Es wurde kein Freund angegeben.");
		set("Friends.General.PlayerIsNowOnline", " &e[PLAYER] &7ist jetzt &aonline&7.");
		set("Friends.General.RequestInfoOnJoin", " &7Freundschaftsanfragen stehen von den folgenden Spielern aus: [FRIENDREQUESTS]");
		set("Friends.General.PlayerIsNowOffline", " &7Der Freund &e[PLAYER] &7ist nun &coffline&7.");
		set("Friends.General.DoesNotExist", " &7Der gegebene Spieler existiert nicht.");
		set("Friends.General.GivenPlayerEqualsSender", " &7Du kannst dich nicht selber als Spieler Argument angeben.");
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
		set("Friends.Command.Add.CanNotSendThisPlayer", " &7Du kannst dem Spieler &e[PLAYER] &7keine Freundschaftsanfrage senden.");
		set("Friends.Command.Add.SenderEqualsReceiver", " &7Du kannst dir nicht selbst eine Freundschaftsanfrage schicken.");
		set("Friends.Command.Add.FriendRequestTimedOut", " &7Die Freundschaftsanfrage von &e[PLAYER] &7ist ausgetimet.");
		set("Friends.Command.Accept.ErrorSenderEqualsReceiver", " &7Du kannst dir nicht selber eine Freundschaftsanfrage senden.");
		set("Friends.Command.Deny.HasDenied", " &7Du hast die Anfrage von &e[PLAYER] &7abgelehnt.");
		set("Friends.Command.Jump.CanNotJump", " &7Du kannst nicht zu dieser Person springen.");
		set("Friends.Command.Jump.AlreadyOnTheServer", " &7Du bist bereits auf diesem Server.");
		set("Friends.Command.Jump.JoinedTheServer", " &7Du bist jetzt auf dem gleichen Server wie der Spieler &e[PLAYER]&7.");
		set("Friends.Command.List.FriendsList", " &7Dies sind deine Freunde:LINE_BREAK &7- ");
		set("Friends.Command.List.PageDoesNotExist", " &7Diese Seite existiert nicht.");
		set("Friends.Command.List.NextPage", " &7Um mehr Freunde zu sehen nutze /friend list [PAGE].");
		set("Friends.Command.List.NoFriendsAdded", " &7Du hast noch keine Freunde hinzugefügt.");
		set("Friends.Command.List.OnlineTitle", " &a(online)&7, spielt gerade auf [SERVER_ON]");
		set("Friends.Command.List.OfflineTitle", " &c(offline)&7, zuletzt online [LAST_ONLINE]");
		set("Friends.Command.Remove.Removed", " &7Du hast den Freund &e[PLAYER] &7entfernt.");
		set("Friends.Command.Remove.FriendRemovedYou", " &e[PLAYER]&7 hat dich von seiner Freundesliste entfernt.");
		set("Friends.Command.Settings.Introduction",
				" &7Dies sind deine Einstellungen:");
		set("Friends.Command.Settings.FriendRequestSettingEveryone",
				"&7Momentan erhälst du Freundschaftsanfragen von &ajedem&7. Um dies zu ändern nutze &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.FriendRequestSettingNobody",
				"&7Momentan können dir &ckeine &7Freundschaftsanfragen gesendet werden. Um dies zu ändern nutze &6/friend settings friendrequests&7.");
		set("Friends.Command.Settings.PartyInvitedByEveryone",
				"&7Momentan können dir Partyeinladungen von &ajedem &7gesendet werden gesendet werden. Um dies zu ändern nutze &6/friend settings invite&7.");
		set("Friends.Command.Settings.PartyInvitedByFriends",
				"&7Momentan kannst du &cnur &7von deinen Freunden in eine Party eingeladen werden. Um dies zu ändern nutze &6/friend settings invite&7.");
		set("Friends.Command.Settings.CanJump",
				"&7Momentan &akönnen &7deine Freunde zu dir Springen. Um dies zu ändern nutze &6/friend settings jump&7.");
		set("Friends.Command.Settings.CanNotJump",
				"&7Momentan können deine Freunde &cnicht &7zu dir springen. Um dies zu ändern nutze &6/friend settings jump&7.");
		set("Friends.Command.Settings.ShowAsOnline",
				"&7Momentan wirst du als &aOnline&7 angezeigt. Um dies zu ändern nutze &6/friend settings offline&7.");
		set("Friends.Command.Settings.ShowAsOffline",
				"&7Momentan wirst du als &cOffline&7 angezeigt. Um dies zu ändern nutze &6/friend settings offline&7.");
		set("Friends.Command.Settings.ShowOnlineStatusChangeNotification",
				"&7Momentan &aerhälst du eine Benachrichtigung&7, wenn ein Freund Online/Offline geht. Um dies zu ändern nutze &6/friend settings notifyonline&7.");
		set("Friends.Command.Settings.DoNotShowOnlineStatusChangeNotification",
				"&7Momentan &cerhälst du keine Benachrichtigung&7, wenn ein Freund Online/Offline geht. Um dies zu ändern nutze &6/friend settings notifyonline&7.");
		set("Friends.Command.Settings.ReceivePM",
				"&7Momentan &aerhälst &7du private Nachrichten. Um dies zu ändern nutze &6/friend settings message&7.");
		set("Friends.Command.Settings.DoNotReceivePM",
				"&7Momentan erhälst du &ckeine &7private Nachrichten. Um dies zu ändern nutze &6/friend settings message&7.");
		set("Friends.Command.Settings.NotFound", " &7Die gegebene Einstellung konnte nicht gefunden werden oder du hast nicht die Rechte um diese zu benutzen.");
		set("Friends.Command.Settings.ChangeThisSettingsHover", "&aHier klicken um die Einstellung zu ändern.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByEveryone", " &7Du kannst jetzt von &ajedem &7Spieler in eine Party eingeladen werden.");
		set("Friends.Command.Settings.NowYouCanGetInvitedByFriends",
				" &7Du kannst jetzt &cnur &7noch von deinen Freunden in eine Party eingeladen werden.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveFriendRequests", " &7Du kannst jetzt &ckeine &7Freundschaftsanfragen mehr erhalten.");
		set("Friends.Command.Settings.NowYouAreGoneReceiveFriendRequests", " &7Du kannst jetzt von &ajedem &7Freundschaftsanfragen erhalten.");
		set("Friends.Command.Settings.NowYouAreNotGoneReceiveMessages", " &7Nun wirst du &ckeine &7Nachrichten mehr erhalten.");
		set("Friends.Command.Settings.NowYouWillBeShowAsOnline", " &7Du wirst nun als &aonline &7angezeigt.");
		set("Friends.Command.Settings.NowYouWilBeShownAsOffline", " &7Du wirst nun als &coffline &7angezeigt.");
		set("Friends.Command.Settings.NowYouWillReceiveOnlineStatusNotification", " &7Du &aerhälst nun eine Benachrichtigung&7, wenn ein Freund Online/Offline geht.");
		set("Friends.Command.Settings.NowYouWillNotReceiveOnlineStatusNotification", " &7Du &cerhälst nun keine Benachrichtigung mehr&7, wenn ein Freund Online/Offline geht.");
		set("Friends.Command.Settings.NowNoMessages", " &7Du kannst jetzt &ckeine &7Nachrichten mehr erhalten.");
		set("Friends.Command.Settings.NowMessages", " &7Du kannst jetzt von &ajedem &7Nachrichten erhalten.");
		set("Friends.Command.Settings.NowYourFriendsCanJump", " &7Freunde können jetzt zu dir &aspringen&7.");
		set("Friends.Command.Settings.NowYourFriendsCanNotJump", " &7Freunde können jetzt &cnicht &7zu dir springen.");
		set("Friends.Command.MSG.CanNotWriteToHim", " &7Du kannst diesem Spieler nicht schreiben.");
		set("Friends.Command.MSG.PlayerWillReceiveMessageOnJoin", " &7Der Spieler erhält die Nachricht, sobald er online geht.");
		set("Friends.Command.MSG.NoOneEverWroteToYou", " &7Noch kein Spieler hat dich angeschrieben.");
		set("Friends.Command.MSG.MessageMissing", " &7Du hast keine Nachricht angegeben.");
		set("Party.Command.Leader.SenderEqualsGivenPlayer", "&7Du kannst dich nicht selber zum neuen Party Leader machen.");
		set("Party.General.ErrorGivenPlayerIsNotInTheParty", "&cDer Spieler &e[PLAYER] &7ist nicht in der Party.");
		set("Party.CommandUsage.Join", "&8/&5Party join <Name> &8- &7Trete einer Party bei");
		set("Party.CommandUsage.Deny", "&8/&5Party deny  &8- &7Lehne eine Einladung ab");
		set("Party.CommandUsage.Invite", "&8/&5Party invite <Name> &8- &7Lade einen Spieler in deine Party ein");
		set("Party.CommandUsage.InviteSetting", "&8/&5Party setting  &8- &7Aktiviert/deaktiviert Party Einladungen");
		set("Party.CommandUsage.List", "&8/&5Party list &8- &7Listet alle Spieler in der Party auf");
		set("Party.CommandUsage.Chat", "&8/&5Party chat <Nachricht> &8- &7Sendet allen Spieler in der Party eine Nachicht");
		set("Party.CommandUsage.Leave", "&8/&5Party leave &8- &7Verlässt die Party");
		set("Party.CommandUsage.Kick", "&8/&5Party kick <Spieler> &8- &7Kickt einen Spieler aus der Party");
		set("Party.CommandUsage.Leader", "&8/&5Party leader <Spieler> &8- &7Macht einen anderen Spieler zum Leader");
		set("Party.Error.CommandNotFound", "&cDieser Befehl existiert nicht!");
		set("Party.Error.NoPermission", "&cDu hast nicht die Permssion um dieses Kommando auszuführen!");
		set("Party.Command.General.PlayerHasLeftTheParty", "&bDer Spieler &6[PLAYER] hat die Party verlassen.");
		set("Party.Command.General.ErrorNoParty", "&cDu bist in keiner Party.");
		set("Party.Command.General.ErrorNotPartyLeader", "&cDu bist nicht der Party Leader.");
		set("Party.Command.General.ErrorGivenPlayerIsNotInTheParty", "&cDer Spieler &e[PLAYER] &cist nicht in der Party.");
		set("Party.Command.General.ErrorNoPlayer", "&cDu musst einen Spieler angeben.");
		set("Party.Command.General.ErrorPlayerNotOnline", "&cDieser Spieler ist nicht online.");
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
		set("Party.Command.Deny.PlayerHasNoParty", "&cDieser Spieler hat keine Party.");
		set("Party.Command.Deny.PlayerHasDeniedInvitation", "&bDer Spieler &6[PLAYER] &bhat die Einladung abgelehnt.");
		set("Party.Command.Deny.DeniedInvitation", "&bDu hast die Party Einladung abgelehnt.");
		set("Party.Command.Deny.ErrorNoInvitation", "&cDu kannst der Party nicht beitreten.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyOthers", "&bDer Spieler &6[PLAYER] &bwurde aus der Party gekickt.");
		set("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer", "&bDu wurdest aus der Party gekickt.");
		set("Party.Command.Kick.Party.Command.Leader.NewLeaderIs", "&7Der neue Party Leader ist &6[NEWLEADER].");
		set("Party.Command.Leader.NewLeaderIs", "&7Der neue Party Leader ist &6[NEWLEADER].");
		set("Party.Command.Leave.YouLeftTheParty", "&bDu hast deine Party verlassen.");
		set("Party.Command.Leave.NewLeaderIs", "&bDer Leader hat die Party verlassen. Der neue Leader ist &e[NEWLEADER].");
		set("Party.Command.Invite.AcceptInvite",
				"&aTritt der Party mit &6/party join [PLAYER] &abei!");
		set("Party.Command.Invite.AcceptInviteHOVER", "&aHier klicken um die Partyeinladung anzunehmen");
		set("Party.Command.Invite.DeclineInvite",
				"&cLehne die Einladung mit &6/Party &6deny &6[PLAYER]&5 &cab!");
		set("Party.Command.Invite.DeclineInviteHOVER", "&cHier klicken um die Partyeinladung abzulehnen");
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
		set("Party.Command.Chat.ContentColor", "&7");
		set("Party.Command.Chat.PartyChatOutput", "&e[SENDERNAME]&5:[MESSAGE_CONTENT]");
		set("Party.Command.Info.PlayersCut", "&7, &b");
		set("Party.Command.Info.Leader", "&3Leader&7: &5[LEADER]");
		set("Party.Command.Info.Players", "&8Players&7: &b");
		set("Friends.General.RequestInfoOnJoinColor", "&e");
		set("Friends.General.RequestInfoOnJoinColorComma", "&7");
		set("Friends.Command.MSG.SentMessage", " &e[SENDERNAME] &5-> &e[PLAYER]&7:[CONTENT]");
		set("Friends.Command.List.OnlineColor", "&a");
		set("Friends.Command.List.OfflineColor", "&c");
		set("Friends.Command.List.PlayerSplit", "LINE_BREAK &7- ");
		set("Friends.Command.MSG.ColorOfMessage", " &7");
		set("Friends.Command.Settings.SplitLine",
				"&8&m-----------------------------------------------");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new MessagesLoader(LANGUAGE, USE_CUSTOM_MESSAGES, FILE, Main.getInstance())).getCreatedConfiguration();
	}
}
