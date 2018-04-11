package de.simonsator.partyandfriends.main;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.commands.MSG;
import de.simonsator.partyandfriends.friends.commands.Reply;
import de.simonsator.partyandfriends.main.listener.JoinEvent;
import de.simonsator.partyandfriends.main.listener.PlayerDisconnectListener;
import de.simonsator.partyandfriends.main.listener.ServerSwitchListener;
import de.simonsator.partyandfriends.main.startup.error.BootErrorType;
import de.simonsator.partyandfriends.main.startup.error.ErrorReporter;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.party.command.PartyChat;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.party.partymanager.LocalPartyManager;
import de.simonsator.partyandfriends.utilities.*;
import de.simonsator.partyandfriends.utilities.disable.Disabler;
import de.simonsator.updatechecker.UpdateSearcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bstats.bungeecord.Metrics;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * The main class
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Main extends Plugin implements ErrorReporter {
	/**
	 * The main instance of this plugin
	 */
	private static Main instance;
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
	 * The language
	 */
	private Language language;
	private Friends friendCommand;
	private List<PAFExtension> pafExtensions = new ArrayList<>();

	public static Main getInstance() {
		return instance;
	}

	@Deprecated
	public static PartyManager getPartyManager() {
		return PartyManager.getInstance();
	}

	@Deprecated
	public static PAFPlayerManager getPlayerManager() {
		return PAFPlayerManager.getInstance();
	}

	/**
	 * Will be execute on enable
	 */
	@Override
	public void onEnable() {
		instance = (this);
		loadConfiguration();
		try {
			initPAFClasses();
			registerCommands();
			registerListeners();
			new Metrics(this);
			if (getConfig().getBoolean("General.CheckForUpdates")) {
				UpdateSearcher searcher = new UpdateSearcher("Party-and-Friends-Free", getDescription().getVersion());
				ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(searcher.checkForUpdate()));
			}
		} catch (SQLException e) {
			initError(e, BootErrorType.MYSQL_CONNECTION_PROBLEM);
		}
	}

	private void initError(Exception e, BootErrorType pType) {
		if (!getConfig().getBoolean("Commands.Party.TopCommands.Party.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, new BootErrorCommand(
					(getConfig().getStringList("Commands.Party.TopCommands.Party.Names").toArray(new String[0])), pType));
		Command partyChatCommand = new BootErrorCommand(
				(getConfig().getStringList("Commands.Party.TopCommands.PartyChat.Names").toArray(new String[0])), pType);
		if (!getConfig().getBoolean("Commands.Party.TopCommands.PartyChat.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, partyChatCommand);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Friend.Disabled"))
			getProxy().getPluginManager().registerCommand(this, new BootErrorCommand(getConfig().getStringList("Commands.Friends.TopCommands.Friend.Names").toArray(new String[0]), pType));
		BootErrorCommand msg = new BootErrorCommand(
				(getConfig().getStringList("Commands.Friends.TopCommands.MSG.Names").toArray(new String[0])), pType);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.MSG.Disabled"))
			getProxy().getPluginManager().registerCommand(this, msg);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Reply.Disabled"))
			getProxy().getPluginManager().registerCommand(this, new BootErrorCommand(
					(getConfig().getStringList("Commands.Friends.TopCommands.Reply.Names").toArray(new String[0])), pType));
		CommandSender console = ProxyServer.getInstance().getConsole();
		reportError(console, pType);
		e.printStackTrace();
	}

	private void initPAFClasses() throws SQLException {
		PoolData poolData = new PoolData(Main.getInstance().getConfig().getInt("MySQL.Pool.MinPoolSize"),
				Main.getInstance().getConfig().getInt("MySQL.Pool.MaxPoolSize"),
				Main.getInstance().getConfig().getInt("MySQL.Pool.InitialPoolSize"), Main.getInstance().getConfig().getInt("MySQL.Pool.IdleConnectionTestPeriod"), Main.getInstance().getConfig().getBoolean("MySQL.Pool.TestConnectionOnCheckin"));
		MySQLData mySQLData = new MySQLData(getConfig().getString("MySQL.Host"),
				getConfig().getString("MySQL.Username"), getConfig().get("MySQL.Password").toString(),
				getConfig().getInt("MySQL.Port"), getConfig().getString("MySQL.Database"),
				getConfig().getString("MySQL.TablePrefix"), getConfig().getBoolean("MySQL.UseSSL"));
		new PAFPlayerManagerMySQL(mySQLData, poolData);
		new LocalPartyManager();
		new StandardPermissionProvider();
	}

	@Override
	public void onDisable() {
		getProxy().getScheduler().cancel(this);
		Disabler.getInstance().disableAll();
		ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
		ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
	}

	/**
	 * Loads the configuration files(config.yml and messages.yml)
	 */
	@SuppressWarnings("deprecation")
	private void loadConfiguration() {
		try {
			config = new ConfigLoader(new File(Main.getInstance().getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			language = Language.valueOf(getConfig().getString("General.Language").toUpperCase());
		} catch (IllegalArgumentException e) {
			getProxy().getConsole().sendMessage(new TextComponent("&4The given language is not supported by Party and Friends. English will be used instead."));
			language = Language.ENGLISH;
			e.printStackTrace();
		}
		try {
			messages = new MessagesLoader(language, getConfig().getBoolean("General.UseOwnLanguageFile"), new File(getDataFolder(), "messages.yml"), this);
			if (getConfig().getBoolean("General.UseOwnLanguageFile"))
				language = Language.OWN;
		} catch (IOException e) {
			e.printStackTrace();
		}
		partyPrefix = (getMessages().getString("Party.General.PartyPrefix"));
		System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
	}

	/**
	 * Registers the listeners
	 */
	private void registerListeners() {
		ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitchListener());
		JoinEvent joinEventListener;
		ProxyServer.getInstance().getPluginManager().registerListener(this, joinEventListener = new JoinEvent());
		Exception e = joinEventListener.verify();
		if (e != null)
			initError(e, BootErrorType.TOO_OLD_VERSION);
	}

	/**
	 * Registers the commands
	 */
	private void registerCommands() {
		String friendsPrefix = (getMessages().getString("Friends.General.Prefix"));
		new PartyCommand(
				(getConfig().getStringList("Commands.Party.TopCommands.Party.Names").toArray(new String[0])), partyPrefix);
		if (!getConfig().getBoolean("Commands.Party.TopCommands.Party.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, PartyCommand.getInstance());
		PartyChat partyChatCommand = new PartyChat(
				(getConfig().getStringList("Commands.Party.TopCommands.PartyChat.Names").toArray(new String[0])), partyPrefix);
		if (!getConfig().getBoolean("Commands.Party.TopCommands.PartyChat.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, partyChatCommand);
		friendCommand = new Friends(getConfig().getStringList("Commands.Friends.TopCommands.Friend.Names"), friendsPrefix);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Friend.Disabled"))
			getProxy().getPluginManager().registerCommand(this, friendCommand);
		MSG friendsMSGCommand = new MSG(
				(getConfig().getStringList("Commands.Friends.TopCommands.MSG.Names").toArray(new String[0])), friendsPrefix);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.MSG.Disabled"))
			getProxy().getPluginManager().registerCommand(this, friendsMSGCommand);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Reply.Disabled"))
			getProxy().getPluginManager().registerCommand(this, new Reply(
					(getConfig().getStringList("Commands.Friends.TopCommands.Reply.Names").toArray(new String[0])), friendsPrefix));
	}

	@Deprecated
	public PartyChat getPartyChatCommand() {
		return PartyChat.getInstance();
	}

	@Deprecated
	public MSG getFriendsMSGCommand() {
		return MSG.getInstance();
	}

	@Deprecated
	public Friends getFriendsCommand() {
		return friendCommand;
	}

	public Configuration getConfig() {
		return config.getCreatedConfiguration();
	}

	@Deprecated
	public String getFriendsPrefix() {
		return Friends.getInstance().getPrefix();
	}

	public Language getLanguage() {
		return language;
	}

	public LanguageConfiguration getMessages() {
		return messages;
	}

	@Deprecated
	public LanguageConfiguration getMessagesYml() {
		return getMessages();
	}

	@Deprecated
	public PartyCommand getPartyCommand() {
		return PartyCommand.getInstance();
	}

	@Deprecated
	public String getPartyPrefix() {
		return PartyCommand.getInstance().getPrefix();
	}

	public void registerExtension(PAFExtension pPAFExtension) {
		pafExtensions.add(pPAFExtension);
	}

	public void unregisterExtension(PAFExtension pPAFExtension) {
		pafExtensions.remove(pPAFExtension);
	}

	public void reload() {
		ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
		ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
		onDisable();
		onEnable();
		List<PAFExtension> toReload = new ArrayList<>(pafExtensions);
		pafExtensions.clear();
		for (PAFExtension extension : toReload)
			extension.reload();
	}
}
