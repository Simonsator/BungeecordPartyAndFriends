package de.simonsator.partyandfriends.main;

import java.io.IOException;
import java.sql.SQLException;

import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.commands.MSG;
import de.simonsator.partyandfriends.friends.commands.Reply;
import de.simonsator.partyandfriends.main.listener.JoinEvent;
import de.simonsator.partyandfriends.main.listener.PlayerDisconnectListener;
import de.simonsator.partyandfriends.main.listener.ServerSwitshListener;
import de.simonsator.partyandfriends.mysql.MySQL;
import de.simonsator.partyandfriends.party.command.PartyChat;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.utilities.Config;
import de.simonsator.partyandfriends.utilities.MessagesYML;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

/***
 * The main class
 * 
 * @author Simonsator
 * @version 1.0.0
 */
public class Main extends Plugin {
	/**
	 * The connection to MySQL
	 */
	private MySQL connection;
	/**
	 * The configuration
	 */
	private Configuration config;
	/**
	 * The messages.yml
	 */
	private Configuration messagesYml;
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
	private String language;
	/**
	 * The party command object
	 */
	private PartyCommand partyCommand;
	/**
	 * This object
	 */
	private static Main instance;
	private Friends friendCommand;
	private MSG friendsMSGCommand;
	private PartyChat partyChatCommand;

	/**
	 * Will be execute on enable
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	@Override
	public void onEnable() {
		instance = (this);
		loadConfiguration();
		connection = (new MySQL());
		try {
			getConnection().firstConnect(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL.Username"),
					getConfig().getString("MySQL.Password"), getConfig().getInt("MySQL.Port"),
					getConfig().getString("MySQL.Database"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		registerListeners();
		registerCommands();
		System.out.println("[PartyAndFriends]" + "PartyAndFriends was enabled successfully!");
	}

	/**
	 * Loads the configurations(config.yml and messages.yml)
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void loadConfiguration() {
		try {
			config = (Config.loadConfig());
		} catch (IOException e) {
			e.printStackTrace();
		}
		language = (getConfig().getString("General.Language"));
		if (getConfig().getString("General.UseOwnLanguageFile").equalsIgnoreCase("true")) {
			language = ("own");
		}
		try {
			messagesYml = (MessagesYML.loadMessages(language));
		} catch (IOException e) {
			e.printStackTrace();
		}
		partyPrefix = (getMessagesYml().getString("Party.General.PartyPrefix"));
		friendsPrefix = (getMessagesYml().getString("Friends.General.Prefix"));
	}

	/**
	 * Registers the listeners
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void registerListeners() {
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitshListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinEvent());
	}

	/**
	 * Registers the commands
	 * 
	 * @author Simonsator
	 * @version 1.0.0
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
		if (getConfig().getString("General.DisableMsg").equalsIgnoreCase("true") == false) {
			getProxy().getPluginManager().registerCommand(this, friendsMSGCommand);
		}
		if (getConfig().getString("General.DisableReply").equalsIgnoreCase("true") == false) {
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

	public MySQL getConnection() {
		return connection;
	}

	public Configuration getConfig() {
		return config;
	}

	public String getFriendsPrefix() {
		return friendsPrefix;
	}

	public String getLanguage() {
		return language;
	}

	public Configuration getMessagesYml() {
		return messagesYml;
	}

	public PartyCommand getPartyCommand() {
		return partyCommand;
	}

	public String getPartyPrefix() {
		return partyPrefix;
	}

	public static Main getInstance() {
		return instance;
	}

}
