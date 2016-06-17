package de.simonsator.partyandfriends.utilities;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	 * @return Returns the config variable
	 * @throws IOException Can throw a {@link IOException}
	 */
	public static Configuration loadConfig() throws IOException {
		if (!Main.getInstance().getDataFolder().exists()) {
			Main.getInstance().getDataFolder().mkdir();
		}
		File file = new File(Main.getInstance().getDataFolder().getPath(), "config.yml");
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
			config.set("MySQL.Database", "friends");
		}
		if (config.getString("MySQL.TablePrefix").equals("")) {
			config.set("MySQL.TablePrefix", "fr_");
		}
		if (config.getString("General.Language").equals("")) {
			config.set("General.Language", "english");
		}
		String ownLanguage = config.getString("General.UseOwnLanguageFile");
		if (ownLanguage.equals("")) {
			config.set("General.UseOwnLanguageFile", "false");
		}
		if (config.getString("General.OfflineServer").equals("")) {
			config.set("General.OfflineServer", "false");
		}
		if (config.getStringList("General.PartyDoNotJoinTheseServers").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("lobby");
			list.add("lobby1");
			list.add("lobby2");
			config.set("General.PartyDoNotJoinTheseServers", list);
		}
		if (config.getString("General.DisableCommandP").equals("")) {
			config.set("General.DisableCommandP", "false");
		}
		if (config.getString("General.DisableMsg").equals("")) {
			config.set("General.DisableMsg", "false");
		}
		if (config.getString("General.DisableReply").equals("")) {
			config.set("General.DisableReply", "false");
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
		if (config.getString("Permissions.FriendPermission").equals("")) {
			config.set("Permissions.FriendPermission", "");
		}
		if (config.getString("Permissions.PartyPermission").equals("")) {
			config.set("Permissions.PartyPermission", "");
		}
		if (config.getString("Permissions.NoPlayerLimitForPartys").equals("")) {
			config.set("Permissions.NoPlayerLimitForPartys", "");
		}
		if (config.getString("GUI.ChangedHideModeMessage").equals("")) {
			config.set("GUI.ChangedHideModeMessage", "true");
		}
		if (config.getList("CommandNames.Friends.TopCommands.Friend").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("friend");
			list.add("friends");
			config.set("CommandNames.Friends.TopCommands.Friend", list);
		}
		if (config.getList("CommandNames.Friends.Accept").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("accept");
			config.set("CommandNames.Friends.Accept", list);
		}
		if (config.getList("CommandNames.Friends.Add").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("add");
			config.set("CommandNames.Friends.Add", list);
		}
		if (config.getList("CommandNames.Friends.Deny").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("deny");
			config.set("CommandNames.Friends.Deny", list);
		}
		if (config.getList("CommandNames.Friends.Jump").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("jump");
			config.set("CommandNames.Friends.Jump", list);
		}
		if (config.getList("CommandNames.Friends.List").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("list");
			list.add("info");
			config.set("CommandNames.Friends.List", list);
		}
		if (config.getList("CommandNames.Friends.Message").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("message");
			list.add("msg");
			config.set("CommandNames.Friends.Message", list);
		}
		if (config.getList("CommandNames.Friends.Remove").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("remove");
			config.set("CommandNames.Friends.Remove", list);
		}
		if (config.getList("CommandNames.Friends.Settings").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("setting");
			list.add("settings");
			config.set("CommandNames.Friends.Settings", list);
		}

		if (config.getList("CommandNames.Friends.TopCommands.Reply").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("reply");
			list.add("r");
			config.set("CommandNames.Friends.TopCommands.Reply", list);
		}
		if (config.getList("CommandNames.Friends.TopCommands.MSG").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("msg");
			config.set("CommandNames.Friends.TopCommands.MSG", list);
		}
		if (config.getList("CommandNames.Party.TopCommands.Party").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("party");
			config.set("CommandNames.Party.TopCommands.Party", list);
		}
		if (config.getList("CommandNames.Party.TopCommands.PartyChat").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("partychat");
			list.add("p");
			config.set("CommandNames.Party.TopCommands.PartyChat", list);
		}
		if (config.getList("CommandNames.Party.Join").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("join");
			list.add("j");
			config.set("CommandNames.Party.Join", list);
		}
		if (config.getList("CommandNames.Party.Invite").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("invite");
			config.set("CommandNames.Party.Invite", list);
		}
		if (config.getList("CommandNames.Party.Kick").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("kick");
			list.add("k");
			config.set("CommandNames.Party.Kick", list);
		}
		if (config.getList("CommandNames.Party.Info").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("info");
			list.add("list");
			config.set("CommandNames.Party.Info", list);
		}
		if (config.getList("CommandNames.Party.Leave").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("leave");
			config.set("CommandNames.Party.Leave", list);
		}
		if (config.getList("CommandNames.Party.Chat").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("chat");
			list.add("message");
			list.add("msg");
			config.set("CommandNames.Party.Chat", list);
		}
		if (config.getList("CommandNames.Party.Leader").isEmpty()) {
			ArrayList<String> list = new ArrayList<>();
			list.add("leader");
			config.set("CommandNames.Party.Leader", list);
		}
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		return config;
	}

}
