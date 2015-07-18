package mySql;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class mySql {
	private Connection connect;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	String url;
	String database;

	public void setDaten(String pHost, String pUsername, String pPassword, int pPort, String pDatabase) {
		url = "jdbc:mysql://" + pHost + ":" + pPort + "/?user=" + pUsername + "&password=" + pPassword;
		database = pDatabase;
	}

	public void verbinde() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url);
		statement = connect.createStatement();
	}

	public int ID(String pUuid) throws SQLException {
		resultSet = statement
				.executeQuery("select ID from " + database + ".freunde WHERE UUID='" + pUuid + "' LIMIT 1");
		if (resultSet.next()) {
			return resultSet.getInt("ID");
		} else {
			return -1;
		}
	}

	public int getIDByPlayerName(String pName) throws SQLException {
		resultSet = statement
				.executeQuery("select ID from " + database + ".freunde WHERE SpielerName='" + pName + "' LIMIT 1");
		if (resultSet.next()) {
			return resultSet.getInt("ID");
		} else {
			return -1;
		}
	}

	public void erstesmal(ProxiedPlayer spieler, String nameDesSpielers, String uuid) throws SQLException {
		this.preparedStatement = this.connect
				.prepareStatement("insert into  " + this.database + ".freunde values (?, ?, ?, ?, ?, ? ,?)");
		this.preparedStatement.setString(1, uuid);
		this.preparedStatement.setString(2, nameDesSpielers);
		this.preparedStatement.setString(3, "");
		this.preparedStatement.setString(4, "");
		this.preparedStatement.setNull(5, 5);
		this.preparedStatement.setInt(6, 1);
		this.preparedStatement.setInt(7, 0);
		this.preparedStatement.executeUpdate();
	}

	public String freundeAusgeben(String uuid) throws SQLException {
		int iD = ID(uuid);
		resultSet = statement
				.executeQuery("select FreundeID from " + database + ".freunde WHERE ID='" + iD + "' LIMIT 1");
		resultSet.next();
		return resultSet.getString("FreundeID");
	}

	public String freundeAusgeben(int iD) throws SQLException {
		resultSet = statement
				.executeQuery("select FreundeID from " + database + ".freunde WHERE ID='" + iD + "' LIMIT 1");
		resultSet.next();
		return resultSet.getString("FreundeID");
	}

	public String getNameDesSpielers(int pID) throws SQLException {
		resultSet = statement
				.executeQuery("select SpielerName from " + database + ".freunde WHERE ID='" + pID + "' LIMIT 1");
		resultSet.next();
		return resultSet.getString("SpielerName");
	}

	public void updateSpielerName(int id, String nameDesSpielers) throws SQLException {
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database + ".freunde set SpielerName='"
				+ nameDesSpielers + "' WHERE ID='" + id + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
	}

	public String getAnfragen(int id) throws SQLException {
		resultSet = statement.executeQuery(
				"select FreundschaftsAnfragenID from " + database + ".freunde WHERE ID='" + id + "' LIMIT 1");
		resultSet.next();
		return resultSet.getString("FreundschaftsAnfragenID");
	}

	public void spielerHinzufuegen(int idSender, int idAbfrage) throws SQLException {
		String freundeIDSenderbearbeiten = freundeAusgeben(idSender);
		String freundeIDSenderString = "";
		if (freundeIDSenderbearbeiten.equals("")) {
			freundeIDSenderString = "" + idAbfrage;
		} else {
			freundeIDSenderString = freundeIDSenderbearbeiten + "|" + idAbfrage;
		}
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database + ".freunde set FreundeID='"
				+ freundeIDSenderString + "' WHERE ID='" + idSender + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
		String freundeIDAbfragebearbeiten = freundeAusgeben(idAbfrage);
		String freundeIDAbfrageString = "";
		if (freundeIDAbfragebearbeiten.equals("")) {
			freundeIDAbfrageString = "" + idSender;
		} else {
			freundeIDAbfrageString = freundeIDAbfragebearbeiten + "|" + idSender;
		}
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database + ".freunde set FreundeID='"
				+ freundeIDAbfrageString + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database + ".freunde set FreundeID='"
				+ freundeIDAbfrageString + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
		String anfragen = getAnfragen(idSender);
		String freundschaftsAnfragenString = "";
		if (anfragen.equals(idAbfrage + "")) {
		} else {
			String zuTrennen = getAnfragen(idSender);
			int[] anfragenID = new int[0];
			StringTokenizer st = new StringTokenizer(zuTrennen, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
						Array.getLength(anfragenID) + 1);
				System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
				anfragenID = (int[]) newArray;
				anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
			}
			boolean gefunden = false;
			int i = 0;
			while (gefunden != true) {
				if (idAbfrage == anfragenID[i]) {
					anfragenID[i] = -1;
					gefunden = true;
				}
				i++;
			}
			String ersterWert = "";
			if (anfragenID[0] != -1) {
				ersterWert = anfragenID[0] + "";
			}
			String zusammengesetzt = ersterWert;
			i = 1;
			while (anfragenID.length > i) {
				if (anfragenID[i] == -1) {

				} else {
					if (zusammengesetzt.equals("")) {
						zusammengesetzt = anfragenID[i] + "";
					} else {
						zusammengesetzt = zusammengesetzt + "|" + anfragenID[i];
					}
				}
				i++;
			}
			freundschaftsAnfragenString = zusammengesetzt;
		}
		this.preparedStatement = this.connect
				.prepareStatement("UPDATE " + this.database + ".freunde set FreundschaftsAnfragenID='"
						+ freundschaftsAnfragenString + "' WHERE ID='" + idSender + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
	}

	public void spielerAblehnen(int idSender, int idAbfrage) throws SQLException {
		String anfragen = getAnfragen(idSender);
		String freundschaftsAnfragenString = "";
		if (anfragen.equals(idAbfrage + "")) {
		} else {
			String zuTrennen = getAnfragen(idSender);
			int[] anfragenID = new int[0];
			StringTokenizer st = new StringTokenizer(zuTrennen, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
						Array.getLength(anfragenID) + 1);
				System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
				anfragenID = (int[]) newArray;
				anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
			}
			boolean gefunden = false;
			int i = 0;
			while (gefunden != true) {
				if (idAbfrage == anfragenID[i]) {
					anfragenID[i] = -1;
					gefunden = true;
				}
				i++;
			}
			String ersterWert = "";
			if (anfragenID[0] != -1) {
				ersterWert = anfragenID[0] + "";
			}
			String zusammengesetzt = ersterWert;
			i = 1;
			while (anfragenID.length > i) {
				if (anfragenID[i] == -1) {

				} else {
					if (zusammengesetzt.equals("")) {
						zusammengesetzt = anfragenID[i] + "";
					} else {
						zusammengesetzt = zusammengesetzt + "|" + anfragenID[i];
					}
				}
				i++;
			}
			freundschaftsAnfragenString = zusammengesetzt;
		}
		this.preparedStatement = this.connect
				.prepareStatement("UPDATE " + this.database + ".freunde set FreundschaftsAnfragenID='"
						+ freundschaftsAnfragenString + "' WHERE ID='" + idSender + "' LIMIT 1");
		this.preparedStatement.executeUpdate();

	}

	public void freundLoeschen(int idSender, int idAbfrage, int aufrufAnzahl) throws SQLException {
		String anfragen = freundeAusgeben(idSender);
		String freundschaftsAnfragenString = "";
		if (anfragen.equals(idAbfrage + "")) {
		} else {
			String zuTrennen = freundeAusgeben(idSender);
			int[] anfragenID = new int[0];
			StringTokenizer st = new StringTokenizer(zuTrennen, "|");
			while (st.hasMoreTokens()) {
				Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
						Array.getLength(anfragenID) + 1);
				System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
				anfragenID = (int[]) newArray;
				anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
			}
			boolean gefunden = false;
			int i = 0;
			while (gefunden != true) {
				if (idAbfrage == anfragenID[i]) {
					anfragenID[i] = -1;
					gefunden = true;
				}
				i++;
			}
			String ersterWert = "";
			if (anfragenID[0] != -1) {
				ersterWert = anfragenID[0] + "";
			}
			String zusammengesetzt = ersterWert;
			i = 1;
			while (anfragenID.length > i) {
				if (anfragenID[i] == -1) {

				} else {
					if (zusammengesetzt.equals("")) {
						zusammengesetzt = anfragenID[i] + "";
					} else {
						zusammengesetzt = zusammengesetzt + "|" + anfragenID[i];
					}
				}
				i++;
			}
			freundschaftsAnfragenString = zusammengesetzt;
		}
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database + ".freunde set FreundeID='"
				+ freundschaftsAnfragenString + "' WHERE ID='" + idSender + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
		if (aufrufAnzahl == 0) {
			freundLoeschen(idAbfrage, idSender, 1);
		}

	}

	public void sendFreundschaftsAnfrage(int idSender, int idAbfrage) throws SQLException {
		String schonBekommeneAnfragen = getAnfragen(idAbfrage);
		String zuSetzten = "";
		if (schonBekommeneAnfragen.equals("")) {
			zuSetzten = "" + idSender;
		} else {
			zuSetzten = schonBekommeneAnfragen + "|" + idSender;
		}
		this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database
				+ ".freunde set FreundschaftsAnfragenID='" + zuSetzten + "' WHERE ID='" + idAbfrage + "' LIMIT 1");
		this.preparedStatement.executeUpdate();
	}

	public int einstellungenSetzen(ProxiedPlayer player, int typ) throws SQLException {
		int playerID = getIDByPlayerName(player.getDisplayName());
		int neuerWert = 0;
		if (typ == 0) {
			resultSet = statement.executeQuery("select einstellungPartyNurFreunde from " + database
					+ ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			resultSet.next();
			int momentanerWert = resultSet.getInt("einstellungPartyNurFreunde");
			if (momentanerWert == 0) {
				neuerWert = 1;
			}
			this.preparedStatement = this.connect
					.prepareStatement("UPDATE " + this.database + ".freunde set einstellungPartyNurFreunde='"
							+ neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		} else {
			resultSet = statement.executeQuery(
					"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
			resultSet.next();
			int momentanerWert = resultSet.getInt("einstellungAkzeptieren");
			if (momentanerWert == 0) {
				neuerWert = 1;
			}
			this.preparedStatement = this.connect.prepareStatement("UPDATE " + this.database
					+ ".freunde set einstellungAkzeptieren='" + neuerWert + "' WHERE ID='" + playerID + "' LIMIT 1");
			this.preparedStatement.executeUpdate();
		}
		return neuerWert;
	}

	public int[] einstellungenAbfragen(ProxiedPlayer player) throws SQLException {
		int playerID = getIDByPlayerName(player.getDisplayName());
		resultSet = statement.executeQuery(
				"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
		resultSet.next();
		int[] feld = new int[2];
		feld[0] = resultSet.getInt("einstellungAkzeptieren");
		resultSet = statement.executeQuery(
				"select einstellungPartyNurFreunde from " + database + ".freunde WHERE ID='" + playerID + "' LIMIT 1");
		resultSet.next();
		feld[1] = resultSet.getInt("einstellungPartyNurFreunde");
		return feld;

	}

	public boolean istBefreundetMit(ProxiedPlayer player, ProxiedPlayer angeschrieben) throws SQLException {
		int idSender = getIDByPlayerName(player.getDisplayName());
		int idAbfrage = getIDByPlayerName(angeschrieben.getDisplayName());
		String zuTrennen = freundeAusgeben(idSender);
		int[] anfragenID = new int[0];
		StringTokenizer st = new StringTokenizer(zuTrennen, "|");
		while (st.hasMoreTokens()) {
			Object newArray = Array.newInstance(anfragenID.getClass().getComponentType(),
					Array.getLength(anfragenID) + 1);
			System.arraycopy(anfragenID, 0, newArray, 0, Array.getLength(anfragenID));
			anfragenID = (int[]) newArray;
			anfragenID[Array.getLength(anfragenID) - 1] = Integer.parseInt(st.nextToken());
		}
		boolean gefunden = false;
		int i = 0;
		while (anfragenID.length > i && gefunden == false) {
			if (anfragenID[i] == idAbfrage) {
				gefunden = true;
			}
			i++;
		}
		return gefunden;
	}

	public boolean erlaubtAnfragen(int idAbfrage) throws SQLException {
		resultSet = statement.executeQuery(
				"select einstellungAkzeptieren from " + database + ".freunde WHERE ID='" + idAbfrage + "' LIMIT 1");
		resultSet.next();
		if (resultSet.getInt("einstellungAkzeptieren") == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean erlaubtPartyAnfragen(String spielerName) throws SQLException {
		int idAbfrage = getIDByPlayerName(spielerName);
		resultSet = statement.executeQuery(
				"select einstellungPartyNurFreunde from " + database + ".freunde WHERE ID='" + idAbfrage + "' LIMIT 1");
		resultSet.next();
		if (resultSet.getInt("einstellungPartyNurFreunde") == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void datenbankImportieren() throws SQLException {
		this.preparedStatement = this.connect.prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);
		this.preparedStatement.executeUpdate();
		this.preparedStatement = this.connect.prepareStatement("CREATE TABLE IF NOT EXISTS " + database
				+ ".`freunde` (`UUID` text NOT NULL, `SpielerName` text NOT NULL, `FreundeID` text NOT NULL, `FreundschaftsAnfragenID` text NOT NULL, `ID` int(255) NOT NULL, `einstellungAkzeptieren` int(11) NOT NULL, `einstellungPartyNurFreunde` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
		this.preparedStatement.executeUpdate();
		try {
			this.preparedStatement = this.connect
					.prepareStatement("ALTER TABLE " + database + ".`freunde` ADD PRIMARY KEY (`ID`);");
			this.preparedStatement.executeUpdate();
		} catch (SQLException e) {

		}
		this.preparedStatement = this.connect.prepareStatement(
				"ALTER TABLE " + database + ".`freunde` MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT;");
		this.preparedStatement.executeUpdate();
	}

}
