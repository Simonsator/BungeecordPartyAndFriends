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

	@Override
	public void onDisable() {
		verbindung.close();
		System.out.println(prefix + "PartyAndFriends wurde deaktiviert!");
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
			System.out.println(prefix+"Die Datenbank konnte nicht importiert werden.");
			e1.printStackTrace();
		}
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new PartyCommand(verbindung));
		BungeeCord.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener(verbindung));
		BungeeCord.getInstance().getPluginManager().registerListener(this, new ServerSwitshListener());
		getProxy().getPluginManager().registerCommand(this, new friends(verbindung));
		BungeeCord.getInstance().getPluginManager().registerListener(this, new joinEvent(verbindung));
		getProxy().getPluginManager().registerCommand(this, new msg(verbindung));
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
					System.out.println(prefix + "For the plugin PartyAndFriends is an update available");
				}
				System.out.println(prefix + "Simonsators PartyAndFriends v." + localVersion + " wurde erfolgreich aktiviert");
			} catch (IOException e) {
				System.out.println(prefix + "Es ist ein Fehler beim suchen nach updates aufgetreten");
				e.printStackTrace();
			}
		} else {
			System.out.println(prefix + "Simonsators PartyAndFriends v." + localVersion + " wurde erfolgreich aktiviert");
			System.out.println(prefix + "Update Notification ist deaktiviert");
		}
		System.out.println(prefix + "PartyAndFriends wurde aktiviert!");
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
			config.set("passwort", "passwort");
			config.set("database", "freunde");
			config.set("updateNotification", false);
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		}
		host = config.getString("host");
		port = config.getInt("port");
		username = config.getString("username");
		passwort = config.getString("passwort");
		database = config.getString("database");
		updateNotification = config.getBoolean("updateNotification");
	}
}
