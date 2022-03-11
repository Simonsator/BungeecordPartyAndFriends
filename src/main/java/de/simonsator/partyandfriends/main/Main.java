package de.simonsator.partyandfriends.main;

import de.simonsator.partyandfriends.admin.commands.PAFAdminCommand;
import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.PAFPluginBase;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.pool.PoolData;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.commands.MSG;
import de.simonsator.partyandfriends.friends.commands.Reply;
import de.simonsator.partyandfriends.main.listener.JoinEvent;
import de.simonsator.partyandfriends.main.listener.OnChatListener;
import de.simonsator.partyandfriends.main.listener.PlayerDisconnectListener;
import de.simonsator.partyandfriends.main.listener.ServerSwitchListener;
import de.simonsator.partyandfriends.main.startup.error.BootErrorType;
import de.simonsator.partyandfriends.main.startup.error.ErrorReporter;
import de.simonsator.partyandfriends.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
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
public class Main extends PAFPluginBase implements ErrorReporter {
	/**
	 * The main instance of this plugin
	 */
	private static Main instance;
	private final List<PAFExtension> pafExtensions = new ArrayList<>();
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
	private boolean shuttingDown = false;

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

	public ConfigurationCreator getGeneralConfig() {
		return config;
	}

	/**
	 * Will be execute on enable
	 */
	@Override
	public void onEnable() {
		instance = (this);
		shuttingDown = false;
		loadConfiguration();
		try {
			initPAFClasses();
			registerCommands();
			registerListeners();
			new Metrics(this, 508);
			if (getConfig().getBoolean("General.CheckForUpdates")) {
				UpdateSearcher searcher = new UpdateSearcher("Party-and-Friends-Free", getDescription().getVersion());
				ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(searcher.checkForUpdate()));
			}
		} catch (SQLException e) {
			if (e.getMessage().contains("Unable to load authentication plugin 'caching_sha2_password'."))
				initError(e, BootErrorType.SHA_ENCRYPTED_PASSWORD);
			else if (e.getMessage().contains("REFERENCES command denied to user"))
				initError(e, BootErrorType.MISSING_PERMISSION_REFERENCE_COMMAND);
			else
				initError(e, BootErrorType.MYSQL_CONNECTION_PROBLEM);
		}
	}

	private void initError(Throwable e, BootErrorType pType) {
		if (!getGeneralConfig().getBoolean("Commands.Party.TopCommands.Party.Disabled"))
			registerCommand(new BootErrorCommand(
					(getGeneralConfig().getStringList("Commands.Party.TopCommands.Party.Names").toArray(new String[0])), pType));
		Command partyChatCommand = new BootErrorCommand(
				(getGeneralConfig().getStringList("Commands.Party.TopCommands.PartyChat.Names").toArray(new String[0])), pType);
		if (!getGeneralConfig().getBoolean("Commands.Party.TopCommands.PartyChat.Disabled"))
			registerCommand(partyChatCommand);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.Friend.Disabled"))
			registerCommand(new BootErrorCommand(getGeneralConfig().getStringList("Commands.Friends.TopCommands.Friend.Names").toArray(new String[0]), pType));
		BootErrorCommand msg = new BootErrorCommand(
				(getGeneralConfig().getStringList("Commands.Friends.TopCommands.MSG.Names").toArray(new String[0])), pType);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.MSG.Disabled"))
			registerCommand(msg);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.Reply.Disabled"))
			registerCommand(new BootErrorCommand(
					(getGeneralConfig().getStringList("Commands.Friends.TopCommands.Reply.Names").toArray(new String[0])), pType));
		CommandSender console = ProxyServer.getInstance().getConsole();
		reportError(console, pType);
		e.printStackTrace();
	}

	private void initPAFClasses() throws SQLException {
		PoolData poolData = new PoolData(Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MinPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.MaxPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.InitialPoolSize"),
				Main.getInstance().getGeneralConfig().getInt("MySQL.Pool.IdleConnectionTestPeriod"),
				Main.getInstance().getGeneralConfig().getBoolean("MySQL.Pool.TestConnectionOnCheckin"),
				Main.getInstance().getGeneralConfig().getString("MySQL.Pool.ConnectionPool"));
		MySQLData mySQLData = new MySQLData(getGeneralConfig().get("MySQL.Host").toString(),
				getGeneralConfig().get("MySQL.Username").toString(),
				getGeneralConfig().get("MySQL.Password").toString(),
				getGeneralConfig().getInt("MySQL.Port"), getGeneralConfig().get("MySQL.Database").toString(),
				getGeneralConfig().get("MySQL.TablePrefix").toString(),
				getGeneralConfig().getBoolean("MySQL.UseSSL"),
				getGeneralConfig().getBoolean("MySQL.Cache"),
				getGeneralConfig().getBoolean("MySQL.RedisCache.ExpirationActivated"),
				getGeneralConfig().getInt("MySQL.RedisCache.ExpirationTimeInSeconds"));
		new PAFPlayerManagerMySQL(mySQLData, poolData);
		if (getGeneralConfig().getBoolean("General.MultiCoreEnhancement")) {
			PAFPlayerMySQL.setMultiCoreEnhancement(true);
		}
		new LocalPartyManager(Main.getInstance().getGeneralConfig().getInt(
				"Commands.Party.SubCommands.Invite.InvitationTimeOutTimeInSeconds"));
		new StandardPermissionProvider();
		new ServerDisplayNameCollection(getGeneralConfig());
	}

	@Override
	public void onDisable() {
		shuttingDown = true;
		ProxyServer.getInstance().getPluginManager().unregisterListeners(this);
		ProxyServer.getInstance().getPluginManager().unregisterCommands(this);
		Disabler.getInstance().disableAll();
		getProxy().getScheduler().cancel(this);
	}

	/**
	 * Loads the configuration files(config.yml and messages.yml)
	 */
	private void loadConfiguration() {
		try {
			config = new ConfigLoader(new File(Main.getInstance().getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			language = Language.valueOf(getGeneralConfig().getString("General.Language").toUpperCase());
		} catch (IllegalArgumentException e) {
			getProxy().getConsole().sendMessage(new TextComponent(TextComponent.fromLegacyText(
					"&4The given language is not supported by Party and Friends. English will be used instead.")));
			language = Language.ENGLISH;
			e.printStackTrace();
		}
		try {
			messages = new MessagesLoader(language, getGeneralConfig().getBoolean("General.UseOwnLanguageFile"),
					new File(getDataFolder(), "messages.yml"), this);
			if (getGeneralConfig().getBoolean("General.UseOwnLanguageFile"))
				language = Language.OWN;
		} catch (IOException e) {
			e.printStackTrace();
		}
		partyPrefix = (getMessages().getString("Party.General.PartyPrefix"));
		System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		getAdapter().setForceUuidSupport(config.getBoolean("General.ForceUUIDSupportOnOfflineServers"));
	}

	/**
	 * Registers the listeners
	 */
	private void registerListeners() {
		BukkitBungeeAdapter.getInstance().registerListener(new PlayerDisconnectListener(), this);
		ServerSwitchListener serverSwitchListener = new ServerSwitchListener();
		if (!getGeneralConfig().getBoolean("General.DisableAutomaticPartyServerSwitching"))
			BukkitBungeeAdapter.getInstance().registerListener(serverSwitchListener, this);
		if (getGeneralConfig().getBoolean("Party.MiniGameStartingCommands.Enabled"))
			BukkitBungeeAdapter.getInstance().registerListener(new OnChatListener(getGeneralConfig().getStringList(
					"Party.MiniGameStartingCommands.Commands")), this);
		JoinEvent joinEventListener;
		BukkitBungeeAdapter.getInstance().registerListener(joinEventListener = new JoinEvent(), this);
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
				(getGeneralConfig().getStringList("Commands.Party.TopCommands.Party.Names").toArray(new String[0])), partyPrefix);
		if (!getGeneralConfig().getBoolean("Commands.Party.TopCommands.Party.Disabled"))
			registerTopCommand(PartyCommand.getInstance());
		PartyChat partyChatCommand = new PartyChat(
				(getGeneralConfig().getStringList("Commands.Party.TopCommands.PartyChat.Names").toArray(new String[0])), partyPrefix);
		if (!getGeneralConfig().getBoolean("Commands.Party.TopCommands.PartyChat.Disabled"))
			registerTopCommand(partyChatCommand);
		friendCommand = new Friends(getGeneralConfig().getStringList("Commands.Friends.TopCommands.Friend.Names"), friendsPrefix);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.Friend.Disabled"))
			registerTopCommand(friendCommand);
		MSG friendsMSGCommand = new MSG(
				(getGeneralConfig().getStringList("Commands.Friends.TopCommands.MSG.Names").toArray(new String[0])), friendsPrefix);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.MSG.Disabled"))
			registerTopCommand(friendsMSGCommand);
		if (!getGeneralConfig().getBoolean("Commands.Friends.TopCommands.Reply.Disabled"))
			registerTopCommand(new Reply(
					(getGeneralConfig().getStringList("Commands.Friends.TopCommands.Reply.Names").toArray(new String[0])), friendsPrefix));
		if (getGeneralConfig().getBoolean("Commands.PAFAdmin.Enabled"))
			registerCommand(new PAFAdminCommand(getGeneralConfig().getStringList("Commands.PAFAdmin.Names").toArray(new String[0])));
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

	/**
	 * @return Returns the normal Main config on Bungeecord, but on Spigot it returns the GUI config.
	 * For that reason it should not be used and instead getGeneralConfig() should be used.
	 */
	@Deprecated
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
		onDisable();
		onEnable();
		List<PAFExtension> toReload = new ArrayList<>(pafExtensions);
		pafExtensions.clear();
		for (PAFExtension extension : toReload)
			extension.reload();
	}

	public boolean isShuttingDown() {
		return shuttingDown;
	}
}
