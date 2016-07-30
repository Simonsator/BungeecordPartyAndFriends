package de.simonsator.partyandfriends.utilities;

import java.io.File;
import java.io.IOException;

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
		set("MySQL.Username", "root");
		set("MySQL.Password", "Password");
		set("MySQL.Database", "friends");
		set("MySQL.TablePrefix", "fr_");
		set("General.Language", "english");
		set("General.UseOwnLanguageFile", "false");
		set("General.OfflineServer", "false");
		set("General.PartyDoNotJoinTheseServers", "lobby", "lobby1", "lobby2");
		set("General.DisableCommandP", "false");
		set("General.DisableMsg", "false");
		set("General.DisableReply", "false");
		set("General.MaxPlayersInParty", 0);
		set("General.DisableCommand.Friends.List", "false");
		set("General.DisableCommand.Friends.MSG", "false");
		set("General.DisableCommand.Friends.Settings", "false");
		set("General.DisableCommand.Friends.Jump", "false");
		set("General.DisableCommand.Party.Chat", "false");
		set("General.DisableCommand.Party.Info", "false");
		set("General.DisableCommand.Party.Kick", "false");
		set("General.DisableCommand.Party.Leader", "false");
		set("Permissions.FriendPermission", "");
		set("Permissions.PartyPermission", "");
		set("Permissions.NoPlayerLimitForParties", "");
		set("GUI.ChangedHideModeMessage", "true");
		set("CommandNames.Friends.TopCommands.Friend", "friend", "friends");
		set("CommandNames.Friends.Accept", "accept", "approve");
		set("CommandNames.Friends.Add", "add", "addfriend");
		set("CommandNames.Friends.Deny", "deny", "reject");
		set("CommandNames.Friends.Jump", "jump", "jumpto");
		set("CommandNames.Friends.List", "list", "info");
		set("CommandNames.Friends.Message", "message", "msg");
		set("CommandNames.Friends.Remove", "remove", "delete");
		set("CommandNames.Friends.Settings", "setting", "settings");
		set("CommandNames.Friends.TopCommands.Reply", "reply", "r");
		set("CommandNames.Friends.TopCommands.MSG", "msg", "fmsg");
		set("CommandNames.Party.TopCommands.Party", "party", "parties");
		set("CommandNames.Party.TopCommands.PartyChat", "partychat", "p");
		set("CommandNames.Party.Join", "join", "j");
		set("CommandNames.Party.Invite", "invite", "add");
		set("CommandNames.Party.Kick", "kick", "k");
		set("CommandNames.Party.Info", "info", "list");
		set("CommandNames.Party.Leave", "leave", "le");
		set("CommandNames.Party.Chat", "chat", "message", "msg");
		set("CommandNames.Party.Leader", "leader");
	}

	@Override
	public void reloadConfiguration() throws IOException {
		configuration = (new ConfigLoader(FILE)).getCreatedConfiguration();
	}
}
