package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.main.Main;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;

/**
 * This class loads the config
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class ConfigLoader extends ConfigurationCreator {
	public ConfigLoader(File file) throws IOException {
		super(file, Main.getInstance(), false);
		copyFromJar("config_bungee.yml");
		readFile();
		loadDefaultValues();
		saveFile();
	}

	/**
	 * Adds missing lines in the config
	 */
	private void loadDefaultValues() {
		set("MySQL.Host", "localhost");
		set("MySQL.Port", 3306);
		set("MySQL.UseSSL", false);
		set("MySQL.Username", "root");
		set("MySQL.Password", "Password");
		set("MySQL.Database", "friends");
		set("MySQL.TablePrefix", "fr_");
		set("MySQL.Cache", true);
		set("MySQL.Pool.ConnectionPool", "C3P0");
		set("MySQL.Pool.InitialPoolSize", 3);
		set("MySQL.Pool.MinPoolSize", 3);
		set("MySQL.Pool.MaxPoolSize", 15);
		set("MySQL.Pool.IdleConnectionTestPeriod", 290);
		set("MySQL.Pool.TestConnectionOnCheckin", false);
		set("General.UseOwnLanguageFile", false);
		set("General.CheckForUpdates", true);
		set("General.Language", "English");
		set("General.MultiCoreEnhancement", true);
		set("General.Time.LanguageTag", "US");
		set("General.Time.TimeZone", "SYSTEM_DEFAULT");
		if (getString("General.Time.TimeZone").equals("SYSTEM_DEFAULT"))
			overwriteKeyTemp("General.Time.TimeZone", TimeZone.getDefault().getID());
		set("General.Time.Format", "dd/MM/yyyy HH:mm:ss");
		set("General.DisableAutomaticPartyServerSwitching", false);
		set("General.PartyDoNotJoinTheseServers", "lobby", "lobby1", "lobby2");
		set("General.PartyJoinDelayInSeconds", 0);
		set("General.DisabledServers", "login1", "login2");
		set("General.SendFriendRequestNotificationOnJoin", true);
		set("General.ForceUUIDSupportOnOfflineServers", false);
		set("ServerDisplayNames.Use", false);
		if (getBoolean("ServerDisplayNames.Use") && get("ServerDisplayNames.Replace") == null) {
			set("ServerDisplayNames.Replace.ExampleServer1.RealName", "lobby-server-21");
			set("ServerDisplayNames.Replace.ExampleServer1.ReplacedName", "Premium Lobby");
			set("ServerDisplayNames.Replace.ExampleServer2.RealName", "lobby-server-22");
			set("ServerDisplayNames.Replace.ExampleServer2.ReplacedName", "Admin Lobby");
		}
		set("Party.MaxPlayersPerParty.Default", 0);
		set("Party.MaxPlayersPerParty.NoLimitPermission", "");
		if (get("Party.MaxPlayersPerParty.AddSlotsPermissions") == null) {
			set("Party.MaxPlayersPerParty.AddSlotsPermissions.Premium.Permission",
					"de.simonsator.partyandfriends.party.addslots.premium");
			set("Party.MaxPlayersPerParty.AddSlotsPermissions.Premium.SlotsToAdd", 5);
			set("Party.MaxPlayersPerParty.AddSlotsPermissions.SuperPremium.Permission",
					"de.simonsator.partyandfriends.party.addslots.premium");
			set("Party.MaxPlayersPerParty.AddSlotsPermissions.SuperPremium.SlotsToAdd", 10);
		}
		set("Party.MiniGameStartingCommands.Enabled", false);
		set("Party.MiniGameStartingCommands.Commands", "/arena join blue", "/survivalgames arena");
		set("Extensions.UseExtensionFolderAsConfigFolder", false);
		set("Commands.Friends.General.PrintOutHelpOnError", true);
		set("Commands.Friends.TopCommands.Friend.Names", "friend", "friends");
		set("Commands.Friends.TopCommands.Friend.Permissions", "");
		set("Commands.Friends.TopCommands.Friend.Disabled", false);
		set("Commands.Friends.TopCommands.Reply.Names", "reply", "r");
		set("Commands.Friends.TopCommands.Reply.Disabled", false);
		set("Commands.Friends.TopCommands.Reply.Permission", "");
		set("Commands.Friends.TopCommands.MSG.Names", "msg", "fmsg");
		set("Commands.Friends.TopCommands.MSG.Disabled", false);
		set("Commands.Friends.TopCommands.MSG.Permission", "");
		set("Commands.Friends.TopCommands.MSG.MSGNonFriendsPermission",
				"de.simonsator.partyandfriends.msg.msgnonfriends");
		set("Commands.Friends.TopCommands.MSG.AllowPlayersToUseChatFormatting", true);
		set("Commands.Friends.SubCommands.Accept.Names", "accept", "approve");
		set("Commands.Friends.SubCommands.Accept.Permission", "");
		set("Commands.Friends.SubCommands.Accept.Priority", 3);
		set("Commands.Friends.SubCommands.Accept.SendTextIsNowOnline", true);
		set("Commands.Friends.SubCommands.Add.Names", "add", "addfriend");
		set("Commands.Friends.SubCommands.Add.Permission", "");
		set("Commands.Friends.SubCommands.Add.Priority", 2);
		set("Commands.Friends.SubCommands.Add.FriendRequestTimeout", 0);
		set("Commands.Friends.SubCommands.Deny.Names", "deny", "reject");
		set("Commands.Friends.SubCommands.Deny.Permission", "");
		set("Commands.Friends.SubCommands.Deny.Priority", 4);
		set("Commands.Friends.SubCommands.Jump.Names", "jump", "jumpto");
		set("Commands.Friends.SubCommands.Jump.Disabled", false);
		set("Commands.Friends.SubCommands.Jump.Permission", "");
		set("Commands.Friends.SubCommands.Jump.Priority", 6);
		set("Commands.Friends.SubCommands.Jump.DisabledServers", "login1", "adminlobby1");
		set("Commands.Friends.SubCommands.List.Names", "list", "info");
		set("Commands.Friends.SubCommands.List.Disabled", false);
		set("Commands.Friends.SubCommands.List.Permission", "");
		set("Commands.Friends.SubCommands.List.Priority", 0);
		set("Commands.Friends.SubCommands.List.SortElements", true);
		set("Commands.Friends.SubCommands.List.EntriesPerPage", 10);
		set("Commands.Friends.SubCommands.MSG.Names", "msg", "message");
		set("Commands.Friends.SubCommands.MSG.Disabled", false);
		set("Commands.Friends.SubCommands.MSG.Permission", "");
		set("Commands.Friends.SubCommands.MSG.Priority", 1);
		set("Commands.Friends.SubCommands.MSG.ReplyCommand", "/friend msg [PLAYER]");
		set("Commands.Friends.SubCommands.Remove.Names", "remove", "deleteAccount");
		set("Commands.Friends.SubCommands.Remove.Permission", "");
		set("Commands.Friends.SubCommands.Remove.Priority", 5);
		set("Commands.Friends.SubCommands.Remove.UseFriendRemovedYouMessage", false);
		set("Commands.Friends.SubCommands.Settings.Names", "setting", "settings");
		set("Commands.Friends.SubCommands.Settings.Disabled", false);
		set("Commands.Friends.SubCommands.Settings.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Priority", 7);
		set("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Names", "friendrequest",
				"receivefriendrequests", "friendrequests");
		set("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Priority", 0);
		set("Commands.Friends.SubCommands.Settings.Settings.Jump.Names", "jump", "allowjump");
		set("Commands.Friends.SubCommands.Settings.Settings.Jump.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.Jump.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.Jump.Priority", 1);
		set("Commands.Friends.SubCommands.Settings.Settings.Offline.Names", "offline", "alwaysoffline");
		set("Commands.Friends.SubCommands.Settings.Settings.Offline.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.Offline.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.Offline.Priority", 2);
		set("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Names", "notifyonline", "notify");
		set("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Priority", 3);
		set("Commands.Friends.SubCommands.Settings.Settings.PM.Names", "pm", "message", "messages");
		set("Commands.Friends.SubCommands.Settings.Settings.PM.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.PM.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.PM.Priority", 4);
		set("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Names", "invite", "party");
		set("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Enabled", true);
		set("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Priority", 5);
		set("Commands.Party.General.PrintOutHelpOnError", true);
		set("Commands.Party.General.PrintOnlyExecutableSubCommandsOut", true);
		set("Commands.Party.TopCommands.Party.Names", "party", "parties");
		set("Commands.Party.TopCommands.Party.Permissions", "");
		set("Commands.Party.TopCommands.Party.Disabled", false);
		set("Commands.Party.TopCommands.PartyChat.Names", "partychat", "p");
		set("Commands.Party.TopCommands.PartyChat.Permissions", "");
		set("Commands.Party.TopCommands.PartyChat.Disabled", false);
		set("Commands.Party.SubCommands.Join.Names", "join", "j");
		set("Commands.Party.SubCommands.Join.Permissions", "");
		set("Commands.Party.SubCommands.Join.Priority", 0);
		set("Commands.Party.SubCommands.Join.AutoJoinLeaderServer", true);
		set("Commands.Party.SubCommands.Deny.Names", "deny", "decline");
		set("Commands.Party.SubCommands.Deny.Permissions", "");
		set("Commands.Party.SubCommands.Deny.Priority", 0);
		set("Commands.Party.SubCommands.Deny.Disabled", true);
		set("Commands.Party.SubCommands.Invite.Names", "invite", "add");
		set("Commands.Party.SubCommands.Invite.Permissions", "");
		set("Commands.Party.SubCommands.Invite.Priority", 1);
		set("Commands.Party.SubCommands.Invite.InvitationTimeOutTimeInSeconds", 60);
		set("Commands.Party.SubCommands.InviteSetting.Names", "settings", "setting", "invitesetting");
		set("Commands.Party.SubCommands.InviteSetting.Permissions", "");
		set("Commands.Party.SubCommands.InviteSetting.Priority", 2);
		set("Commands.Party.SubCommands.InviteSetting.Disabled", true);
		set("Commands.Party.SubCommands.Kick.Names", "kick", "forcedleave");
		set("Commands.Party.SubCommands.Kick.Disabled", false);
		set("Commands.Party.SubCommands.Kick.Permissions", "");
		set("Commands.Party.SubCommands.Kick.Priority", 6);
		set("Commands.Party.SubCommands.Info.Names", "info", "list");
		set("Commands.Party.SubCommands.Info.Disabled", false);
		set("Commands.Party.SubCommands.Info.Permissions", "");
		set("Commands.Party.SubCommands.Info.Priority", 3);
		set("Commands.Party.SubCommands.Leave.Names", "leave", "le");
		set("Commands.Party.SubCommands.Leave.Permissions", "");
		set("Commands.Party.SubCommands.Leave.Priority", 5);
		set("Commands.Party.SubCommands.Chat.Names", "chat", "message", "msg");
		set("Commands.Party.SubCommands.Chat.ReplyCommand", "chat");
		set("Commands.Party.SubCommands.Chat.Disabled", false);
		set("Commands.Party.SubCommands.Chat.Permissions", "");
		set("Commands.Party.SubCommands.Chat.Priority", 4);
		set("Commands.Party.SubCommands.Leader.Names", "leader", "lead");
		set("Commands.Party.SubCommands.Leader.Disabled", false);
		set("Commands.Party.SubCommands.Leader.Permissions", "");
		set("Commands.Party.SubCommands.Leader.Priority", 7);
		set("Commands.PAFAdmin.Enabled", false);
		set("Commands.PAFAdmin.Names", "pafadmin", "adminpaf");
	}
}
