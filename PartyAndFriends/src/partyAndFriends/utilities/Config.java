/**
 * This class loads the config
 * 
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import partyAndFriends.main.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * This class loads the config
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Config {
	/**
	 * Adds missing lines in the config
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the config variable
	 * @throws IOException
	 *             Can throw a {@link SQLException}
	 */
	public static Configuration ladeConfig() throws IOException {
		if (!Main.main.getDataFolder().exists()) {
			Main.main.getDataFolder().mkdir();
		}
		File file = new File(Main.main.getDataFolder().getPath(), "config.yml");
		if (!file.exists()) {
			file.createNewFile();
		}
		Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		if (config.getString("MySQL.Host").equals("")) {
			config.set("MySQL.Host", "localhost");
		}
		if (config.getInt("MySQL.Port") == 0) {
			config.set("MySQL.Port", 3306);
		}
		if (config.getString("MySQL.Username").equals("")) {
			config.set("MySQL.Username", "root");
		}
		if (config.getString("MySQL.Password").equals("")) {
			config.set("MySQL.Password", "Password");
		}
		if (config.getString("MySQL.Database").equals("")) {
			config.set("MySQL.Database", "freunde");
		}
		if (config.getString("General.Language").equals("")) {
			config.set("General.Language", "english");
		}
		String ownLanguage = config.getString("General.UseOwnLanguageFile");
		if (ownLanguage.equals("")) {
			config.set("General.UseOwnLanguageFile", "false");
		}
		if (config.getString("General.UpdateNotification").equals("")) {
			config.set("General.UpdateNotification", "true");
		}
		if (config.getString("General.PartyDoNotJoinTheseServers").equalsIgnoreCase("")) {
			config.set("General.PartyDoNotJoinTheseServers", "lobby1|lobby2");
		}
		if (config.getString("General.DisableCommandP").equals("")) {
			config.set("General.DisableCommandP", "false");
		}
		if (config.getString("General.DisableMsg").equals("")) {
			config.set("General.DisableMsg", "false");
		}
		if (config.getInt("General.MaxPlayersInParty") == 0) {
			config.set("General.MaxPlayersInParty", 0);
		}
		if (config.getString("General.DisableCommand.Friends.List").equals("")) {
			config.set("General.DisableCommand.Friends.List", "false");
		}
		if (config.getString("General.DisableCommand.Friends.MSG").equals("")) {
			config.set("General.DisableCommand.Friends.MSG", "false");
		}
		if (config.getString("General.DisableCommand.Friends.Settings").equals("")) {
			config.set("General.DisableCommand.Friends.Settings", "false");
		}
		if (config.getString("General.DisableCommand.Friends.Jump").equals("")) {
			config.set("General.DisableCommand.Friends.Jump", "false");
		}
		if (config.getString("General.DisableCommand.Party.Chat").equals("")) {
			config.set("General.DisableCommand.Party.Chat", "false");
		}
		if (config.getString("General.DisableCommand.Party.Info").equals("")) {
			config.set("General.DisableCommand.Party.Info", "false");
		}
		if (config.getString("General.DisableCommand.Party.Kick").equals("")) {
			config.set("General.DisableCommand.Party.Kick", "false");
		}
		if (config.getString("General.DisableCommand.Party.Leader").equals("")) {
			config.set("General.DisableCommand.Party.Leader", "false");
		}
		if (config.getString("Permissions.FriendPermission").equalsIgnoreCase("")) {
			config.set("Permissions.FriendPermission", "");
		}
		if (config.getString("Permissions.PartyPermission").equals("")) {
			config.set("Permissions.PartyPermission", "");
		}
		if (config.getString("Permissions.NoPlayerLimitForPartys").equals("")) {
			config.set("Permissions.NoPlayerLimitForPartys", "");
		}
		if (config.getString("Aliases.FriendsAliasMsg").equals("")) {
			config.set("Aliases.FriendsAliasMsg", "msg|chat");
		}
		if (config.getString("Aliases.PartyAlias").equals("")) {
			config.set("Aliases.PartyAlias", "party|party");
		}
		if (config.getString("Aliases.JoinAlias").equals("")) {
			config.set("Aliases.JoinAlias", "join|j");
		}
		if (config.getString("Aliases.InviteAlias").equals("")) {
			config.set("Aliases.InviteAlias", "invite|invite");
		}
		if (config.getString("Aliases.KickAlias").equals("")) {
			config.set("Aliases.KickAlias", "kick|k");
		}
		if (config.getString("Aliases.InfoAlias").equals("")) {
			config.set("Aliases.InfoAlias", "info");
		}
		if (config.getString("Aliases.leaveAlias").equals("")) {
			config.set("Aliases.leaveAlias", "leave|leave");
		}
		if (config.getString("Aliases.ChatAlias").equals("")) {
			config.set("Aliases.ChatAlias", "chat|msg|message");
		}
		if (config.getString("Aliases.LeaderAlias").equals("")) {
			config.set("Aliases.LeaderAlias", "leader|setleader");
		}
		if (config.getString("Aliases.AcceptAlias").equals("")) {
			config.set("Aliases.AcceptAlias", "accept");
		}
		if (config.getString("Aliases.AddAlias").equals("")) {
			config.set("Aliases.AddAlias", "add");
		}
		if (config.getString("Aliases.DenyAlias").equals("")) {
			config.set("Aliases.denyAlias", "deny");
		}
		if (config.getString("Aliases.SettingsAlias").equals("")) {
			config.set("Aliases.SettingsAlias", "settings");
		}
		if (config.getString("Aliases.JumpAlias").equals("")) {
			config.set("Aliases.JumpAlias", "jump");
		}
		if (config.getString("Aliases.ListAlias").equals("")) {
			config.set("Aliases.ListAlias", "list|l");
		}
		if (config.getString("Aliases.RemoveAlias").equals("")) {
			config.set("Aliases.RemoveAlias", "remove");
		}
		if (config.getString("Aliases.FriendsAlias").equals("")) {
			config.set("Aliases.FriendsAlias", "friend|friends");
		}
		if (config.getString("Aliases.PartyChatShortAlias").equals("")) {
			config.set("Aliases.PartyChatShortAlias", "p|pc|pmsg");
		}
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		return config;
	}

}
