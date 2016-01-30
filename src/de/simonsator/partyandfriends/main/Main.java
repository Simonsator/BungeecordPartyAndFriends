/***
  * The main class
 * @author Simonsator
 * @version 1.0.0
 */
package de.simonsator.partyandfriends.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import de.simonsator.partyandfriends.connection.MySQL;
import de.simonsator.partyandfriends.friends.Friends;
import de.simonsator.partyandfriends.friends.commands.MSG;
import de.simonsator.partyandfriends.main.listener.JoinEvent;
import de.simonsator.partyandfriends.main.listener.PlayerDisconnectListener;
import de.simonsator.partyandfriends.main.listener.ServerSwitshListener;
import de.simonsator.partyandfriends.party.command.P;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.utilities.Config;
import de.simonsator.partyandfriends.utilities.MessagesYML;
import de.simonsator.partyandfriends.utilities.StringToArray;
import net.md_5.bungee.BungeeCord;
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
	 * The config
	 */
	private Configuration config;
	/**
	 * The messages.yml
	 */
	private Configuration messagesYml;
	/**
	 * The party prefix
	 */
	private String partyPrefix = "§7[§5Party§7] ";
	/**
	 * The friends prefix
	 */
	private String friendsPrefix = "§8[§5§lFriends§8]";
	/**
	 * The language
	 */
	private String language;
	/**
	 * The instance of this plugin
	 */
	private static Main main;

	/**
	 * Will be execute on enable
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	@Override
	public void onEnable() {
		main = (this);
		loadConfiguration();
		connection = new MySQL();
		try {
			connection.connect(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL.Username"),
					getConfig().getString("MySQL.Password"), getConfig().getInt("MySQL.Port"),
					getConfig().getString("MySQL.Database"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		registerListeners();
		registerCommands();
		// checkForUpdates();
		if (getLanguage().equalsIgnoreCase("english")) {
			System.out.println("[PartyAndFriends]" + "PartyAndFriends was enabled successfully!");
		} else {
			if (getLanguage().equalsIgnoreCase("own")) {
				System.out.println("[PartyAndFriends]" + "PartyAndFriends was enabled successfully!");
			} else {
				System.out.println("[PartyAndFriends]" + "PartyAndFriends wurde aktiviert!");
			}
		}
	}

	/**
	 * Will be execute on disable
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	@Override
	public void onDisable() {
		connection.close();
		System.out.println("[PartyAndFriends]" + "PartyAndFriends was disabled!");
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
			try {
				messagesYml = (MessagesYML.loadMessages());
			} catch (IOException e) {
				e.printStackTrace();
			}
			partyPrefix = (getMessagesYml().getString("Party.General.PartyPrefix"));
			friendsPrefix = (getMessagesYml().getString("Friends.General.Prefix"));
		}
	}

	/**
	 * Registers the listeners
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void registerListeners() {
		BungeeCord.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		BungeeCord.getInstance().getPluginManager().registerListener(this, new ServerSwitshListener());
		BungeeCord.getInstance().getPluginManager().registerListener(this, new JoinEvent());
	}

	/**
	 * Registers the commands
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void registerCommands() {
		BungeeCord.getInstance().getPluginManager().registerCommand(this,
				new PartyCommand(StringToArray.stringToArray(getConfig().getString("Aliases.PartyAlias"))));
		if (getConfig().getString("General.DisableCommandP").equals("true") == false) {
			BungeeCord.getInstance().getPluginManager().registerCommand(this,
					new P(StringToArray.stringToArray(getConfig().getString("Aliases.PartyChatShortAlias"))));
		}
		getProxy().getPluginManager().registerCommand(this,
				new Friends(StringToArray.stringToArray(getConfig().getString("Aliases.FriendsAlias"))));
		if (getConfig().getString("General.DisableMsg").equalsIgnoreCase("true") == false) {
			getProxy().getPluginManager().registerCommand(this,
					new MSG(StringToArray.stringToArray(getConfig().getString("Aliases.FriendsAliasMsg"))));
		}
	}

	/**
	 * Check for Updates
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	private void checkForUpdates() {
		String localVersion = getDescription().getVersion();
		if (getConfig().getString("General.UpdateNotification").equalsIgnoreCase("true")) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
						.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.getOutputStream()
						.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=10123")
								.getBytes("UTF-8"));
				String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				if (localVersion.equalsIgnoreCase(version)) {

				} else {
					if (getLanguage().equalsIgnoreCase("english")) {
						System.out
								.println("[PartyAndFriends]" + "For the plugin PartyAndFriends is an update available");
					} else {
						if (getLanguage().equalsIgnoreCase("own")) {
							System.out.println(
									"[PartyAndFriends]" + "For the plugin PartyAndFriends is an update available");
						} else {
							System.out.println(
									"[PartyAndFriends]" + "Für das Plugin PartyAndFriends ist ein Update verfügbar");
						}
					}
				}
				if (getLanguage().equalsIgnoreCase("english")) {
					System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
							+ " was enabled successfully");
				} else {
					if (getLanguage().equalsIgnoreCase("own")) {
						System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
								+ " was enabled successfully");
					} else {
						System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
								+ " wurde erfolgreich aktiviert");
					}
				}
			} catch (IOException e) {
				if (getLanguage().equalsIgnoreCase("english")) {
					System.out.println("[PartyAndFriends]" + "It occurred an error while searching for updates");
				} else {
					if (getLanguage().equalsIgnoreCase("own")) {
						System.out.println("[PartyAndFriends]" + "It occurred an error while searching for updates");
					} else {
						System.out.println(
								"[PartyAndFriends]" + "Es ist ein Fehler beim suchen nach updates aufgetreten");
					}
				}
				e.printStackTrace();
			}
		} else {
			if (getLanguage().equalsIgnoreCase("english")) {
				System.out.println(getPartyPrefix() + "Simonsators PartyAndFriends v." + localVersion
						+ " was enabled successfully");
				System.out.println("[PartyAndFriends]" + "Update Notification is disabled");
			} else {
				if (getLanguage().equalsIgnoreCase("own")) {
					System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
							+ " was enabled successfully");
					System.out.println("[PartyAndFriends]" + "Update Notification is disabled");
				} else {
					System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
							+ " wurde erfolgreich aktiviert");
					System.out.println("[PartyAndFriends]" + "Update Notification ist deaktiviert");
				}
			}
		}
	}

	public MySQL getConnection() {
		return connection;
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

	public Configuration getConfig() {
		return config;
	}

	public String getPartyPrefix() {
		return partyPrefix;
	}

	public static Main getInstance() {
		return main;
	}
}
