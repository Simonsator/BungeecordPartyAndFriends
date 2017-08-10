package de.simonsator.partyandfriends.main;

import com.google.gson.Gson;
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
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.party.command.PartyChat;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.party.partymanager.LocalPartyManager;
import de.simonsator.partyandfriends.utilities.*;
import de.simonsator.partyandfriends.utilities.disable.Disabler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

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
public class Main extends Plugin {
	private static final Gson gson = new Gson();
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

	public static Gson getGson() {
		return gson;
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
		} catch (SQLException e) {
			initError(e);
		}
	}

	private void initError(SQLException e) {
		if (!getConfig().getBoolean("Commands.Party.TopCommands.Party.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, new BootErrorCommand(
					(getConfig().getStringList("Commands.Party.TopCommands.Party.Names").toArray(new String[0]))));
		Command partyChatCommand = new BootErrorCommand(
				(getConfig().getStringList("Commands.Party.TopCommands.PartyChat.Names").toArray(new String[0])));
		if (!getConfig().getBoolean("Commands.Party.TopCommands.PartyChat.Disabled"))
			ProxyServer.getInstance().getPluginManager().registerCommand(this, partyChatCommand);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Friend.Disabled"))
			getProxy().getPluginManager().registerCommand(this, new BootErrorCommand(getConfig().getStringList("Commands.Friends.TopCommands.Friend.Names").toArray(new String[0])));
		BootErrorCommand msg = new BootErrorCommand(
				(getConfig().getStringList("Commands.Friends.TopCommands.MSG.Names").toArray(new String[0])));
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.MSG.Disabled"))
			getProxy().getPluginManager().registerCommand(this, msg);
		if (!getConfig().getBoolean("Commands.Friends.TopCommands.Reply.Disabled"))
			getProxy().getPluginManager().registerCommand(this, new BootErrorCommand(
					(getConfig().getStringList("Commands.Friends.TopCommands.Reply.Names").toArray(new String[0]))));
		System.out.println("§cParty and Friends was either not able to connect to the MySQL database or to login into the MySQL database. " +
				"Please correct your MySQL data in the config.yml. If you need further help contact Simonsator via Skype (live:00pflaume), PM him (https://www.spigotmc.org/conversations/add?to=simonsator) or write an email to him (support@simonsator.de). Please don't forget to send him the Proxy.Log.0 file (bungeecord log file).");
		e.printStackTrace();
	}

	private void initPAFClasses() throws SQLException {
		switch ("MySQL") {
			case "MySQL":
				PoolData poolData = new PoolData(Main.getInstance().getConfig().getInt("MySQL.Pool.MinPoolSize"),
						Main.getInstance().getConfig().getInt("MySQL.Pool.MaxPoolSize"),
						Main.getInstance().getConfig().getInt("MySQL.Pool.InitialPoolSize"));
				MySQLData mySQLData = new MySQLData(getConfig().getString("MySQL.Host"),
						getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"),
						getConfig().getInt("MySQL.Port"), getConfig().getString("MySQL.Database"),
						getConfig().getString("MySQL.TablePrefix"), getConfig().getBoolean("MySQL.UseSSL"));
				new PAFPlayerManagerMySQL(mySQLData, poolData);
				new LocalPartyManager();
				break;
		}
		new StandardPermissionProvider();
	}

	@Override
	public void onDisable() {
		Disabler.getInstance().disableAll();
		ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
		ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
	}

	/**
	 * Loads the configurations(config.yml and messages.yml)
	 */
	private void loadConfiguration() {
		try {
			config = new ConfigLoader(new File(Main.getInstance().getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		language = Language.valueOf(getConfig().getString("General.Language").toUpperCase());
		if (getConfig().getBoolean("General.UseOwnLanguageFile"))
			language = Language.OWN;
		try {
			if (messages == null)
				messages = new MessagesLoader(language, new File(getDataFolder(), "messages.yml"));
			else messages.reloadConfiguration();
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
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinEvent());
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
