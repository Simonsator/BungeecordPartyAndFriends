/**
 * Objects of this class are the party, where a player is in
 * 
 * @author Simonsator
 * @version 1.0.1
 *
 */
package partyAndFriends.party;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;
import partyAndFriends.main.Main;

/**
 * Objects of this class are the party, where a player is in
 * 
 * @author Simonsator
 * @version 1.0.1
 *
 */
public class PlayerParty {
	/**
	 * The leader of the party
	 */
	private ProxiedPlayer leader;
	/**
	 * The "normal" players which are in the party
	 */
	private ArrayList<ProxiedPlayer> players;
	/**
	 * The players who are invited into this party
	 */
	private ArrayList<ProxiedPlayer> invite;

	/**
	 * Initials a new party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param leader
	 *            The leader of the party
	 */
	public PlayerParty(ProxiedPlayer leader) {
		this.leader = leader;
		this.players = new ArrayList<ProxiedPlayer>();
		this.invite = new ArrayList<ProxiedPlayer>();
	}

	/**
	 * Returns a true if the given player is the leader of this party, and it
	 * will returns false if he is not the leader, of this party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns a true if the given player is the leader of this party,
	 *         and it will returns false if he is not the leader, of this party
	 */
	public boolean isleader(ProxiedPlayer player) {
		return this.leader == player;
	}

	/**
	 * Returns the "normal" players who are in the party.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the "normal" players who are in the party.
	 */
	public ArrayList<ProxiedPlayer> getPlayer() {
		return this.players;
	}

	/**
	 * Sets the ArrayList of the "normal" players
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param liste
	 *            The ArrayList of the "normal" players
	 */
	public void setPlayer(ArrayList<ProxiedPlayer> liste) {
		players = liste;
	}

	/**
	 * Gets the leader of this party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the party leader
	 */
	public ProxiedPlayer getleader() {
		return this.leader;
	}

	/**
	 * Returns true if the player is in the party. Returns false if the player
	 * is not in the party.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns true if the player is in the party. Returns false if the
	 *         player is not in the party.
	 */
	public boolean isinParty(ProxiedPlayer player) {
		if (getAllPlayersInParty().contains(player)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a player to the party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns true if the player was added to this party. Returns false
	 *         if the player was not added to this party
	 */
	public boolean addPlayer(ProxiedPlayer player) {
		if (!players.contains(player) && invite.contains(player)) {
			players.add(player);
			PartyManager.addPlayerToParty(player, this);
			invite.remove(player);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a player from the party
	 * 
	 * @param player
	 *            The player
	 * @return Returns true if the player was removed from the party. Returns
	 *         false if the player was not removed from the party.
	 */
	public boolean removePlayer(ProxiedPlayer player) {
		if (players.contains(player)) {
			players.remove(player);
			PartyManager.removePlayerFromParty(player);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the Server info from the party on which the party is on
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the Server info from the party on which the party is on
	 */
	public ServerInfo getServerInfo() {
		return this.leader.getServer().getInfo();
	}

	/**
	 * Invites a player into this party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 */
	public void invite(final ProxiedPlayer player) {
		invite.add(player);
		String zuschreiben = "";
		String value = "";
		if (Main.main.language.equalsIgnoreCase("english")) {
			player.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
					+ "§5You §5were §5invited §5to §5the §5party §5of §6" + leader.getDisplayName() + "§5!"));
			zuschreiben = partyAndFriends.main.Main.main.partyPrefix
					+ "§5Join §5the §5party §5by §5using §5the §5command §6/Party §6join §6" + leader.getName() + "!";
			value = "Click here to join the party";
		} else {
			if (Main.main.language.equals("own")) {
				player.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
						+ partyAndFriends.main.Main.main.messagesYml.getString("Party.Command.Invite.YouWereInvitedBY")
								.replace("[PLAYER]", leader.getDisplayName())));
				zuschreiben = partyAndFriends.main.Main.main.partyPrefix + partyAndFriends.main.Main.main.messagesYml
						.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE")
						.replace("[PLAYER]", leader.getName());
				value = partyAndFriends.main.Main.main.messagesYml
						.getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER");
			} else {
				player.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
						+ "§5Du §5wurdest §5in §5die §5Party §5von§6 " + leader.getDisplayName() + " §5eingeladen!"));
				zuschreiben = partyAndFriends.main.Main.main.partyPrefix + "§5Tritt §5der §5Party §5mit §6/Party join "
						+ leader.getName() + " §5bei!";
				value = "Hier klicken um Party einladung anzunehmen";
			}
		}
		String command = "/party join " + leader.getName();
		String jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
				+ "'},'hoverEvent':{'action':'show_text','value':'" + value + "'}}";
		player.unsafe().sendPacket(new Chat(jsoncode));
		final PlayerParty party = this;
		BungeeCord.getInstance().getScheduler().schedule(partyAndFriends.main.Main.main, new Runnable() {
			@Override
			public void run() {
				if (invite.contains(player)) {
					invite.remove(player);
					if (Main.main.language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(
								partyAndFriends.main.Main.main.partyPrefix + "§5The invitation of the Party from §6"
										+ leader.getDisplayName() + " §5is §5timed §5out!"));
						leader.sendMessage(
								new TextComponent(partyAndFriends.main.Main.main.partyPrefix + "§5The player§6 "
										+ player.getDisplayName() + " §5has §5not §5accepted §5your §5invitation!"));
					} else {
						if (Main.main.language.equals("own")) {
							player.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.Invite.InvitationTimedOutInvited")
											.replace("[PLAYER]", leader.getDisplayName())));
							leader.sendMessage(new TextComponent(Main.main.partyPrefix
									+ Main.main.messagesYml.getString("Party.Command.Invite.InvitationTimedOutLeader")
											.replace("[PLAYER]", player.getDisplayName())));
						} else {
							player.sendMessage(new TextComponent(
									partyAndFriends.main.Main.main.partyPrefix + "§5Die Einladung in die Party von §6"
											+ leader.getDisplayName() + " §5ist §5abgelaufen!"));
							leader.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
									+ "§5Der Spieler§6 " + player.getDisplayName()
									+ " §5hat §5die §5Einladung §5nicht §5angenommen!"));
						}
					}
					ArrayList<ProxiedPlayer> liste = party.getPlayer();
					if (liste.size() == 0) {
						if (Main.main.language.equalsIgnoreCase("english")) {
							leader.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
									+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
						} else {
							if (Main.main.language.equals("own")) {
								leader.sendMessage(new TextComponent(
										partyAndFriends.main.Main.main.partyPrefix + Main.main.messagesYml.getString(
												"Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
							} else {
								leader.sendMessage(new TextComponent(partyAndFriends.main.Main.main.partyPrefix
										+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
							}
						}
						PartyManager.deleteParty(party);
					}
				}
			}
		}, 60, TimeUnit.SECONDS);
	}

	/**
	 * Returns the size of the invitation list
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the size of the invitation list
	 */
	public int inviteListSize() {
		return invite.size();
	}

	/**
	 * Returns true if the player is already invited. Returns false if the
	 * player is not invited.
	 * 
	 * @param player
	 *            The player
	 * @return Returns true if the player is already invited. Returns false if
	 *         the player is not invited.
	 */
	public boolean containsPlayer(ProxiedPlayer player) {
		return invite.contains(player);
	}

	/**
	 * Sets the party leader.
	 * 
	 * @param player
	 *            The player
	 */
	public void setLeader(ProxiedPlayer player) {
		leader = player;
	}

	/**
	 * Returns all players in this party.
	 * 
	 * @return Returns all players in this party.
	 */
	public ArrayList<ProxiedPlayer> getAllPlayersInParty() {
		ArrayList<ProxiedPlayer> allPlayers = new ArrayList<ProxiedPlayer>();
		for (ProxiedPlayer uebertragen : players)
			allPlayers.add(uebertragen);
		allPlayers.add(leader);
		return allPlayers;
	}

	/**
	 * Returns all invited players.
	 * 
	 * @return Returns all invited players
	 */
	public ArrayList<ProxiedPlayer> getInvitedPlayers() {
		return invite;
	}

	/**
	 * Set the invite list
	 * 
	 * @param inviteList
	 *            The ArrayList that should be set
	 */
	public void setInviteList(ArrayList<ProxiedPlayer> inviteList) {
		invite = inviteList;
	}
}
