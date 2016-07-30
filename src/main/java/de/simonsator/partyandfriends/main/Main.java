package de.simonsator.partyandfriends.main;

import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.commands.MSG;
import de.simonsator.partyandfriends.friends.commands.Reply;
import de.simonsator.partyandfriends.main.listener.JoinEvent;
import de.simonsator.partyandfriends.main.listener.PlayerDisconnectListener;
import de.simonsator.partyandfriends.main.listener.ServerSwitchListener;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.party.command.PartyChat;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.party.partymanager.LocalPartyManager;
import de.simonsator.partyandfriends.utilities.ConfigLoader;
import de.simonsator.partyandfriends.utilities.Language;
import de.simonsator.partyandfriends.utilities.MessagesLoader;
import de.simonsator.partyandfriends.utilities.disable.Disabler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;

/***
 * The main class
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Main extends Plugin {
	/**
	 * This object
	 */
	private static Main instance;
	private static PAFPlayerManager playerManager;
	private static PartyManager partyManager;
	/**
	 * The configuration
	 */
	private ConfigLoader config;
	/**
	 * The messages.yml
	 */
	private MessagesLoader messages = null;
	/**
	 * The party prefix
	 */
	private String partyPrefix;
	/**
	 * The friends prefix
	 */
	private String friendsPrefix;
	/**
	 * The language
	 */
	private Language language;
	/**
	 * The party command object
	 */
	private PartyCommand partyCommand;
	private Friends friendCommand;
	private MSG friendsMSGCommand;
	private PartyChat partyChatCommand;

	public static Main getInstance() {
		return instance;
	}

	public static PartyManager getPartyManager() {
		return partyManager;
	}

	public static PAFPlayerManager getPlayerManager() {
		return playerManager;
	}

	/**
	 * Will be execute on enable
	 */
	@Override
	public void onEnable() {
		instance = (this);
		loadConfiguration();
		switch ("MySQL") {
			case "MySQL":
				MySQLData mySQLData = new MySQLData(getConfig().getString("MySQL.Host"),
						getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"),
						getConfig().getInt("MySQL.Port"), getConfig().getString("MySQL.Database"),
						getConfig().getString("MySQL.TablePrefix"));
				playerManager = new PAFPlayerManagerMySQL(mySQLData);
				partyManager = new LocalPartyManager();
				break;
		}
		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {
		Disabler.getInstance().disableAll();
	}

	/**
	 * Loads the configurations(config.yml and messages.yml)
	 */
	public void loadConfiguration() {
		try {
			config = new ConfigLoader(new File(Main.getInstance().getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		language = Language.valueOf(getConfig().getString("General.Language").toUpperCase());
		if (getConfig().getString("General.UseOwnLanguageFile").equalsIgnoreCase("true"))
			language = Language.OWN;
		try {
			if (messages == null)
				messages = new MessagesLoader(language, new File(getDataFolder(), "messages.yml"));
			else messages.reloadConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
		}
		partyPrefix = (getMessagesYml().getString("Party.General.PartyPrefix"));
		friendsPrefix = (getMessagesYml().getString("Friends.General.Prefix"));
	}

	/**
	 * Registers the listeners
	 */
	private void registerListeners() {
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitchListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinEvent());
	}

	/**
	 * Registers the commands
	 */
	private void registerCommands() {
		partyCommand = (new PartyCommand(
				(getConfig().getStringList("CommandNames.Party.TopCommands.Party").toArray(new String[0]))));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, getPartyCommand());
		partyChatCommand = new PartyChat(
				(getConfig().getStringList("CommandNames.Party.TopCommands.PartyChat").toArray(new String[0])));
		if (!getConfig().getString("General.DisableCommandP").equals("true"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, partyChatCommand);
		friendCommand = new Friends(getConfig().getStringList("CommandNames.Friends.TopCommands.Friend"));
		getProxy().getPluginManager().registerCommand(this, friendCommand);
		friendsMSGCommand = new MSG(
				(getConfig().getStringList("CommandNames.Friends.TopCommands.MSG").toArray(new String[0])));
		if (!getConfig().getString("General.DisableMsg").equalsIgnoreCase("true")) {
			getProxy().getPluginManager().registerCommand(this, friendsMSGCommand);
		}
		if (!getConfig().getString("General.DisableReply").equalsIgnoreCase("true")) {
			getProxy().getPluginManager().registerCommand(this, new Reply(
					(getConfig().getStringList("CommandNames.Friends.TopCommands.Reply").toArray(new String[0]))));
		}
	}

	public PartyChat getPartyChatCommand() {
		return partyChatCommand;
	}

	public MSG getFriendsMSGCommand() {
		return friendsMSGCommand;
	}

	public Friends getFriendsCommand() {
		return friendCommand;
	}

	public Configuration getConfig() {
		return config.getCreatedConfiguration();
	}

	public String getFriendsPrefix() {
		return friendsPrefix;
	}

	public Language getLanguage() {
		return language;
	}

	public Configuration getMessagesYml() {
		return messages.getCreatedConfiguration();
	}

	public PartyCommand getPartyCommand() {
		return partyCommand;
	}

	public String getPartyPrefix() {
		return partyPrefix;
	}

}
