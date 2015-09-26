/***
  * The main class
 * @author Simonsator
 * @version 1.0.0
 */
package partyAndFriends.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import partyAndFriends.freunde.friends;
import partyAndFriends.freunde.kommandos.msg;
import partyAndFriends.main.listener.JoinEvent;
import partyAndFriends.main.listener.PlayerDisconnectListener;
import partyAndFriends.main.listener.ServerSwitshListener;
import partyAndFriends.mySQL.MySQL;
import partyAndFriends.party.command.P;
import partyAndFriends.party.command.PartyCommand;
import partyAndFriends.utilities.Config;
import partyAndFriends.utilities.MessagesYML;
import partyAndFriends.utilities.StringToArray;

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
	public MySQL verbindung;
	/**
	 * The config
	 */
	public Configuration config;
	/**
	 * The messages.yml
	 */
	public Configuration messagesYml;
	/**
	 * The party prefix
	 */
	public String partyPrefix = "§7[§5Party§7] ";
	/**
	 * The friends prefix
	 */
	public String friendsPrefix = "§8[§5§lFriends§8]";
	/**
	 * The language
	 */
	public String language;
	/**
	 * The party command class
	 */
	public PartyCommand KommandoParty;
	public static Main main;

	/**
	 * Will be execute on enable
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	@Override
	public void onEnable() {
		main = this;
		configurationenLaden();
		verbindung = new MySQL();
		try {
			verbindung.verbinde(config.getString("MySQL.Host"), config.getString("MySQL.Username"),
					config.getString("MySQL.Password"), config.getInt("MySQL.Port"),
					config.getString("MySQL.Database"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		registerListeners();
		registerCommands();
		checkForUpdates();
		if (language.equalsIgnoreCase("english")) {
			System.out.println("[PartyAndFriends]" + "PartyAndFriends was enabled successfully!");
		} else {
			if (language.equalsIgnoreCase("own")) {
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
		verbindung.close();
		System.out.println("[PartyAndFriends]" + "PartyAndFriends was disabled!");
	}

	/**
	 * Loads the configurations(config.yml and messages.yml)
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 */
	public void configurationenLaden() {
		try {
			config = Config.ladeConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		language = config.getString("General.Language");
		if (config.getString("General.UseOwnLanguageFile").equalsIgnoreCase("true")) {
			language = "own";
			try {
				messagesYml = MessagesYML.ladeMessages();
			} catch (IOException e) {
				e.printStackTrace();
			}
			partyPrefix = messagesYml.getString("Party.General.PartyPrefix");
			friendsPrefix = messagesYml.getString("Friends.General.Prefix");
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
		KommandoParty = new PartyCommand(StringToArray.stringToArray(config.getString("Aliases.PartyAlias")));
		BungeeCord.getInstance().getPluginManager().registerCommand(this, KommandoParty);
		if (config.getString("General.DisableCommandP").equals("true") == false) {
			BungeeCord.getInstance().getPluginManager().registerCommand(this,
					new P(StringToArray.stringToArray(config.getString("Aliases.PartyChatShortAlias"))));
		}
		getProxy().getPluginManager().registerCommand(this,
				new friends(StringToArray.stringToArray(config.getString("Aliases.FriendsAlias"))));
		if (config.getString("General.DisableMsg").equalsIgnoreCase("true") == false) {
			getProxy().getPluginManager().registerCommand(this,
					new msg(StringToArray.stringToArray(config.getString("Aliases.FriendsAliasMsg"))));
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
		if (config.getString("General.UpdateNotification").equalsIgnoreCase("true")) {
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
					if (language.equalsIgnoreCase("english")) {
						System.out
								.println("[PartyAndFriends]" + "For the plugin PartyAndFriends is an update available");
					} else {
						if (language.equalsIgnoreCase("own")) {
							System.out.println(
									"[PartyAndFriends]" + "For the plugin PartyAndFriends is an update available");
						} else {
							System.out.println(
									"[PartyAndFriends]" + "Für das Plugin PartyAndFriends ist ein Update verfügbar");
						}
					}
				}
				if (language.equalsIgnoreCase("english")) {
					System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
							+ " was enabled successfully");
				} else {
					if (language.equalsIgnoreCase("own")) {
						System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
								+ " was enabled successfully");
					} else {
						System.out.println("[PartyAndFriends]" + "Simonsators PartyAndFriends v." + localVersion
								+ " wurde erfolgreich aktiviert");
					}
				}
			} catch (IOException e) {
				if (language.equalsIgnoreCase("english")) {
					System.out.println("[PartyAndFriends]" + "It occurred an error while searching for updates");
				} else {
					if (language.equalsIgnoreCase("own")) {
						System.out.println("[PartyAndFriends]" + "It occurred an error while searching for updates");
					} else {
						System.out.println(
								"[PartyAndFriends]" + "Es ist ein Fehler beim suchen nach updates aufgetreten");
					}
				}
				e.printStackTrace();
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				System.out.println(
						partyPrefix + "Simonsators PartyAndFriends v." + localVersion + " was enabled successfully");
				System.out.println("[PartyAndFriends]" + "Update Notification is disabled");
			} else {
				if (language.equalsIgnoreCase("own")) {
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
}
