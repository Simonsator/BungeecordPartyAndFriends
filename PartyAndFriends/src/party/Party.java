package party;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import freunde.friends;
import freunde.joinEvent;
import freunde.kommandos.msg;
import mySql.mySql;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import party.command.PartyCommand;
import party.listener.PlayerDisconnectListener;
import party.listener.ServerSwitshListener;

public class Party extends Plugin {

	public static String prefix = "§7[§5Party§7] ";
	private static Party instance;
	private String host;
	private int port;
	private String username;
	private String passwort;
	private String database;
	public static mySql verbindung;
	private boolean updateNotification;
	private String friendsAliasMsg;
	private String PartyAlias;
	private String friendAlias;
	private String joinAlias;
	private String inviteAlias;
	private String kickAlias;
	private String infoAlias;
	private String leaveAlias;
	private String chatAlias;
	private String leaderAlias;
	private String acceptAlias;
	private String addAlias;
	private String denyAlias;
	private String settingsAlias;
	private String jumpAlias;
	private String listAlias;
	private String removeAlias;
	private String language;
	private String friendPermission;
	private String partyPermission;

	@Override
	public void onDisable() {
		verbindung.close();
		if (language.equalsIgnoreCase("english")) {
			System.out.println(prefix + "PartyAndFriends was disabled!");
		} else {
			System.out.println(prefix + "PartyAndFriends wurde deaktiviert!");
		}
	}

	@Override
	public void onEnable() {
		instance = this;
		try {
			ladeConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		verbindung = new mySql();
		verbindung.setDaten(host, username, passwort, port, database);
		try {
			verbindung.verbinde();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		try {
			verbindung.datenbankImportieren();
		} catch (SQLException e1) {
			if (language.equalsIgnoreCase("english")) {
				System.out.println(prefix + "The database couldn´t be imported.");
			} else {
				System.out.println(prefix + "Die Datenbank konnte nicht importiert werden.");
			}
			e1.printStackTrace();
		}
		BungeeCord.getInstance().getPluginManager().registerCommand(this,
				new PartyCommand(verbindung, partyPermission, PartyAlias, joinAlias, inviteAlias, kickAlias, infoAlias,
						leaveAlias, chatAlias, leaderAlias, language));
		BungeeCord.getInstance().getPluginManager().registerListener(this,
				new PlayerDisconnectListener(verbindung, language));
		BungeeCord.getInstance().getPluginManager().registerListener(this, new ServerSwitshListener(language));
		getProxy().getPluginManager().registerCommand(this,
				new friends(verbindung, friendPermission, friendAlias, friendsAliasMsg, acceptAlias, addAlias,
						denyAlias, settingsAlias, jumpAlias, listAlias, removeAlias, language));
		BungeeCord.getInstance().getPluginManager().registerListener(this, new joinEvent(verbindung, language));
		getProxy().getPluginManager().registerCommand(this, new msg(verbindung, friendsAliasMsg, language));
		String localVersion = getDescription().getVersion();
		if (updateNotification) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
						.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.getOutputStream()
						.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=9531")
								.getBytes("UTF-8"));
				String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				if (localVersion.equalsIgnoreCase(version)) {

				} else {
					if (language.equalsIgnoreCase("english")) {
						System.out.println(prefix + "For the plugin PartyAndFriends is an update available");
					} else {
						System.out.println(prefix + "Für das Plugin PartyAndFriends ist ein Update verfügbar");
					}
				}
				if (language.equalsIgnoreCase("english")) {
					System.out.println(
							prefix + "Simonsators PartyAndFriends v." + localVersion + " was enabled successfully");
				} else {
					System.out.println(
							prefix + "Simonsators PartyAndFriends v." + localVersion + " wurde erfolgreich aktiviert");
				}
			} catch (IOException e) {
				if (language.equalsIgnoreCase("english")) {
					System.out.println(prefix + "It occurred an error while searching for updates");
				} else {
					System.out.println(prefix + "Es ist ein Fehler beim suchen nach updates aufgetreten");
				}
				e.printStackTrace();
			}
		} else {
			if (language.equalsIgnoreCase("english")) {
				System.out.println(
						prefix + "Simonsators PartyAndFriends v." + localVersion + " was enabled successfully");
				System.out.println(prefix + "Update Notification is disabled");
			} else {
				System.out.println(
						prefix + "Simonsators PartyAndFriends v." + localVersion + " wurde erfolgreich aktiviert");
				System.out.println(prefix + "Update Notification ist deaktiviert");
			}
		}
		if (language.equalsIgnoreCase("english")) {
			System.out.println(prefix + "PartyAndFriends was enabled successfully!");
		} else {
			System.out.println(prefix + "PartyAndFriends wurde aktiviert!");
		}
	}

	public static Party getInstance() {
		return instance;
	}

	public void ladeConfig() throws IOException {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder().getPath(), "config.yml");
		boolean jetztErstellt = false;
		if (!file.exists()) {
			file.createNewFile();
			jetztErstellt = true;
		}
		Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		if (jetztErstellt == true) {
			config.set("host", "localhost");
			config.set("port", 3306);
			config.set("username", "root");
			config.set("passwort", "password");
			config.set("database", "freunde");
			config.set("language", "english");
			config.set("updateNotification", false);
			config.set("spigotHasInstalledPartyAndFriendsGUI", false);
			config.set("friendPermission", "");
			config.set("partyPermission", "");
			config.set("PartyAlias", "party");
			config.set("friendsAlias", "friend");
			config.set("friendsAliasMsg", "chat");
			config.set("joinAlias", "join");
			config.set("inviteAlias", "invite");
			config.set("kickAlias", "kick");
			config.set("infoAlias", "info");
			config.set("leaveAlias", "leave");
			config.set("chatAlias", "chat");
			config.set("leaderAlias", "leader");
			config.set("acceptAlias", "accept");
			config.set("addAlias", "add");
			config.set("denyAlias", "deny");
			config.set("settingsAlias", "settings");
			config.set("jumpAlias", "jump");
			config.set("listAlias", "list");
			config.set("removeAlias", "remove");
		}
		host = config.getString("host");
		if (host.equals("")) {
			config.set("host", "localhost");
		}
		port = config.getInt("port");
		if (port == 0) {
			config.set("port", 3306);
		}
		username = config.getString("username");
		if (username.equals("")) {
			config.set("username", "root");
		}
		passwort = config.getString("passwort");
		if (username.equals("")) {
			config.set("passwort", "password");
		}
		database = config.getString("database");
		if (database.equals("")) {
			config.set("database", "freunde");
		}
		language = config.getString("language");
		if (language.equals("")) {
			config.set("language", "english");
		}
		updateNotification = config.getBoolean("updateNotification");
		if (updateNotification == false) {
			config.set("updateNotification", false);
		}
		friendPermission = config.getString("friendPermission");
		if (friendPermission.equalsIgnoreCase("")) {
			config.set("friendPermission", "");
		}
		partyPermission = config.getString("partyPermission");
		if (partyPermission.equals("")) {
			config.set("partyPermission", "");
		}
		friendsAliasMsg = config.getString("friendsAliasMsg");
		if (friendsAliasMsg.equals("")) {
			config.set("friendsAliasMsg", "chat");
		}
		PartyAlias = config.getString("PartyAlias");
		if (PartyAlias.equals("")) {
			config.set("PartyAlias", "partys");
		}
		joinAlias = config.getString("joinAlias");
		if (joinAlias.equals("")) {
			config.set("joinAlias", "join");
		}
		inviteAlias = config.getString("inviteAlias");
		if (inviteAlias.equals("")) {
			config.set("inviteAlias", "invite");
		}
		kickAlias = config.getString("kickAlias");
		if (kickAlias.equals("")) {
			config.set("kickAlias", "kick");
		}
		infoAlias = config.getString("infoAlias");
		if (infoAlias.equals("")) {
			config.set("infoAlias", "info");
		}
		leaveAlias = config.getString("leaveAlias");
		if (leaveAlias.equals("")) {
			config.set("leaveAlias", "leave");
		}
		chatAlias = config.getString("chatAlias");
		if (chatAlias.equals("")) {
			config.set("chatAlias", "chat");
		}
		leaderAlias = config.getString("leaderAlias");
		if (leaderAlias.equals("")) {
			config.set("leaderAlias", "newLeader");
		}
		acceptAlias = config.getString("acceptAlias");
		if (acceptAlias.equals("")) {
			config.set("acceptAlias", "accept");
		}
		addAlias = config.getString("addAlias");
		if (acceptAlias.equals("")) {
			config.set("addAlias", "add");
		}
		denyAlias = config.getString("denyAlias");
		if (denyAlias.equals("")) {
			config.set("denyAlias", "deny");
		}
		settingsAlias = config.getString("settingsAlias");
		if (settingsAlias.equals("")) {
			config.set("settingsAlias", "settings");
		}
		jumpAlias = config.getString("jumpAlias");
		if (jumpAlias.equals("")) {
			config.set("jumpAlias", "jump");
		}
		listAlias = config.getString("listAlias");
		if (listAlias.equals("")) {
			config.set("listAlias", "list");
		}
		removeAlias = config.getString("removeAlias");
		if (removeAlias.equals("")) {
			config.set("removeAlias", "remove");
		}
		friendAlias = config.getString("friendsAlias");
		if (friendAlias.equals("")) {
			config.set("friendsAlias", "friend");
		}
		ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
	}
}
