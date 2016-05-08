/**
 * Objects of this class are the party, where a player is in
 * 
 * @author Simonsator
 * @version 1.0.1
 *
 */
package de.simonsator.partyandfriends.party;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

/**
 * Objects of this class are the party, where a player is in
 * 
 * @author Simonsator
 * @version 2.0.0
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
	private ArrayList<ProxiedPlayer> invited;

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
		this.invited = new ArrayList<ProxiedPlayer>();
	}

	/**
	 * Returns true if the given player is the leader of this party, and it will
	 * returns false if he is not the leader, of this party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param player
	 *            The player
	 * @return Returns a true if the given player is the leader of this party,
	 *         and it will returns false if he is not the leader, of this party
	 */
	public boolean isLeader(ProxiedPlayer player) {
		return this.leader == player;
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
	public boolean isInParty(ProxiedPlayer player) {
		if (getAllPlayers().contains(player))
			return true;
		return false;
	}

	public boolean isNobodyInvited() {
		return invited.isEmpty();
	}

	/**
	 * Gets the leader of this party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the party leader
	 */
	public ProxiedPlayer getLeader() {
		return this.leader;
	}

	/**
	 * 
	 * @return Returns all players in this party (inclusive the leader).
	 */
	public ArrayList<ProxiedPlayer> getAllPlayers() {
		ArrayList<ProxiedPlayer> allPlayers = new ArrayList<>();
		for (ProxiedPlayer player : players)
			allPlayers.add(player);
		if (leader != null)
			allPlayers.add(leader);
		return allPlayers;
	}

	/**
	 * Returns the "normal" players who are in the party.
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the "normal" players who are in the party.
	 */
	public ArrayList<ProxiedPlayer> getPlayers() {
		return this.players;
	}

	/**
	 * Returns all invited players.
	 * 
	 * @return Returns all invited players
	 */
	public ArrayList<ProxiedPlayer> getInvitedPlayers() {
		return invited;
	}

	/**
	 * Adds a player to the party
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @param pPlayer
	 *            The player
	 * @return Returns true if the player was added to this party. Returns false
	 *         if the player was not added to this party
	 */
	public boolean addPlayer(ProxiedPlayer pPlayer) {
		if (!players.contains(pPlayer) && invited.contains(pPlayer)) {
			players.add(pPlayer);
			PartyManager.addPlayerToParty(pPlayer, this);
			invited.remove(pPlayer);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the party leader.
	 * 
	 * @param player
	 *            The player
	 */
	public void setLeader(ProxiedPlayer player) {
		leader = player;
		PartyManager.addPlayerToParty(player, this);
		players.remove(player);
	}

	/**
	 * Removes a player from the party
	 * 
	 * @param pPlayer
	 *            The player
	 */
	public void removePlayer(ProxiedPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ Main.getInstance().getMessagesYml().getString("Party.Command.General.PlayerHasLeftTheParty")
						.replace("[PLAYER]", pPlayer.getDisplayName())));
	}

	public void removePlayerSilent(ProxiedPlayer pPlayer) {
		players.remove(pPlayer);
		PartyManager.removePlayerFromParty(pPlayer);
	}

	public void leaveParty(ProxiedPlayer pPlayer) {
		removePlayer(pPlayer);
		boolean needsNewLeader = needsNewLeader(pPlayer);
		if (deleteParty())
			return;
		if (needsNewLeader) {
			findNewLeader(pPlayer);
		}
	}

	public void kickPlayer(ProxiedPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer")));
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers")
						.replace("[PLAYER]", pPlayer.getDisplayName())));
		deleteParty();
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
		invited.add(player);
		player.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Invite.YouWereInvitedBY").replace("[PLAYER]", leader.getDisplayName())));
		player.unsafe().sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getPartyPrefix()
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE")
						.replace("[PLAYER]", leader.getName())
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Main.getInstance().getPartyCommand().getName() + " join " + leader.getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER")
				+ "\"}]}}}"));
		final PlayerParty party = this;
		ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (invited.contains(player)) {
					invited.remove(player);
					player.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.InvitationTimedOutInvited")
							.replace("[PLAYER]", leader.getDisplayName())));
					leader.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.InvitationTimedOutLeader")
							.replace("[PLAYER]", player.getDisplayName())));
					if (isPartyEmpty()) {
						leader.sendMessage(
								new TextComponent(de.simonsator.partyandfriends.main.Main.getInstance().getPartyPrefix()
										+ Main.getInstance().getMessagesYml().getString(
												"Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
						PartyManager.deleteParty(party);
					}
				}
			}
		}, 60, TimeUnit.SECONDS);
	}

	private boolean isPartyEmpty() {
		return players.isEmpty() && invited.isEmpty();
	}

	/**
	 * Returns the size of the invitation list
	 * 
	 * @author Simonsator
	 * @version 1.0.0
	 * @return Returns the size of the invitation list
	 */
	public int getInviteListSize() {
		return invited.size();
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
	public boolean isInvited(ProxiedPlayer player) {
		return invited.contains(player);
	}

	public void sendMessage(TextComponent pText) {
		for (ProxiedPlayer player : getAllPlayers())
			player.sendMessage(pText);
	}

	private boolean deleteParty() {
		if (this.getAllPlayers().size() == 1) {
			sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
					.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
			PartyManager.deleteParty(this);
			return true;
		}
		return false;
	}

	private boolean needsNewLeader(ProxiedPlayer pPlayer) {
		if (isLeader(pPlayer)) {
			leader = null;
			return true;
		}
		return false;
	}

	private void findNewLeader(ProxiedPlayer pPlayer) {
		this.setLeader(players.get(0));
		players.remove(players.get(0));
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Leave.NewLeaderIs").replace("[NEWLEADER]", leader.getDisplayName())));
	}
}
