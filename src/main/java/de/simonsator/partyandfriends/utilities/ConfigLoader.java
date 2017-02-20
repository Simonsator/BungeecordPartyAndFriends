package de.simonsator.partyandfriends.utilities;

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
		super(file);
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
		set("General.UseOwnLanguageFile", false);
		set("General.Language", "english");
		set("General.Time.LanguageTag", "US");
		set("General.Time.TimeZone", TimeZone.getDefault().getID());
		set("General.Time.Format", "dd/MM/yyyy HH:mm:ss");
		set("General.OfflineServer", false);
		set("General.PartyDoNotJoinTheseServers", "lobby", "lobby1", "lobby2");
		set("General.DisabledServers", "login1", "login2");
		set("General.MaxPlayersInParty", 0);
		set("Permissions.NoPlayerLimitForParties", "");
		set("GUI.ChangedHideModeMessage", true);
		set("Commands.Friends.TopCommands.Friend.Names", "friend", "friends");
		set("Commands.Friends.TopCommands.Friend.Permissions", "");
		set("Commands.Friends.TopCommands.Friend.Disabled", false);
		set("Commands.Friends.TopCommands.Reply.Names", "reply", "r");
		set("Commands.Friends.TopCommands.Reply.Disabled", false);
		set("Commands.Friends.TopCommands.MSG.Names", "msg", "fmsg");
		set("Commands.Friends.TopCommands.MSG.Disabled", false);
		set("Commands.Friends.SubCommands.Accept.Names", "accept", "approve");
		set("Commands.Friends.SubCommands.Accept.Permission", "");
		set("Commands.Friends.SubCommands.Add.Names", "add", "addfriend");
		set("Commands.Friends.SubCommands.Add.Permission", "");
		set("Commands.Friends.SubCommands.Deny.Names", "deny", "reject");
		set("Commands.Friends.SubCommands.Deny.Permission", "");
		set("Commands.Friends.SubCommands.Jump.Names", "jump", "jumpto");
		set("Commands.Friends.SubCommands.Jump.Disabled", false);
		set("Commands.Friends.SubCommands.Jump.Permission", "");
		set("Commands.Friends.SubCommands.Jump.DisabledServers", "login1", "adminlobby1");
		set("Commands.Friends.SubCommands.List.Names", "list", "info");
		set("Commands.Friends.SubCommands.List.Disabled", false);
		set("Commands.Friends.SubCommands.List.Permission", "");
		set("Commands.Friends.SubCommands.MSG.Names", "msg", "message");
		set("Commands.Friends.SubCommands.MSG.Disabled", false);
		set("Commands.Friends.SubCommands.MSG.Permission", "");
		set("Commands.Friends.SubCommands.Remove.Names", "remove", "delete");
		set("Commands.Friends.SubCommands.Remove.Permission", "");
		set("Commands.Friends.SubCommands.Settings.Names", "setting", "settings");
		set("Commands.Friends.SubCommands.Settings.Disabled", false);
		set("Commands.Friends.SubCommands.Settings.Permission", "");
		set("Commands.Party.TopCommands.Party.Names", "party", "parties");
		set("Commands.Party.TopCommands.Party.Permissions", "");
		set("Commands.Party.TopCommands.Party.Disabled", false);
		set("Commands.Party.TopCommands.PartyChat.Names", "partychat", "p");
		set("Commands.Party.TopCommands.PartyChat.Permissions", "");
		set("Commands.Party.TopCommands.PartyChat.Disabled", false);
		set("Commands.Party.SubCommands.Join.Names", "join", "j");
		set("Commands.Party.SubCommands.Join.Permissions", "");
		set("Commands.Party.SubCommands.Invite.Names", "invite", "add");
		set("Commands.Party.SubCommands.Invite.Permissions", "");
		set("Commands.Party.SubCommands.Kick.Names", "kick", "forcedleave");
		set("Commands.Party.SubCommands.Kick.Disabled", false);
		set("Commands.Party.SubCommands.Kick.Permissions", "");
		set("Commands.Party.SubCommands.Info.Names", "info", "list");
		set("Commands.Party.SubCommands.Info.Disabled", false);
		set("Commands.Party.SubCommands.Info.Permissions", "");
		set("Commands.Party.SubCommands.Leave.Names", "leave", "le");
		set("Commands.Party.SubCommands.Leave.Permissions", "");
		set("Commands.Party.SubCommands.Chat.Names", "chat", "message", "msg");
		set("Commands.Party.SubCommands.Chat.Disabled", false);
		set("Commands.Party.SubCommands.Chat.Permissions", "");
		set("Commands.Party.SubCommands.Leader.Names", "leader", "lead");
		set("Commands.Party.SubCommands.Leader.Disabled", false);
		set("Commands.Party.SubCommands.Leader.Permissions", "");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new ConfigLoader(FILE)).getCreatedConfiguration();
	}
}
