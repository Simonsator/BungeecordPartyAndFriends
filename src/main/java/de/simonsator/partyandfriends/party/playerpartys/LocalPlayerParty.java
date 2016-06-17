package de.simonsator.partyandfriends.party.playerpartys;

import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.utilities.CompilePatter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.CompilePatter.NEWLEADERPATTERN;
import static de.simonsator.partyandfriends.utilities.CompilePatter.PLAYERPATTERN;

public class LocalPlayerParty extends PlayerParty {
	/**
	 * The leader of the party
	 */
	private UUID leader;
	/**
	 * The "normal" players which are in the party
	 */
	private ArrayList<UUID> players;
	/**
	 * The players who are invited into this party
	 */
	private ArrayList<UUID> invited;

	/**
	 * Initials a new party
	 *
	 * @param leader The leader of the party
	 */
	public LocalPlayerParty(OnlinePAFPlayer leader) {
		this.leader = leader.getUniqueId();
		this.players = new ArrayList<>();
		this.invited = new ArrayList<>();
	}

	/**
	 * Returns true if the given player is the leader of this party, and it will
	 * returns false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns a true if the given player is the leader of this party,
	 * and it will returns false if he is not the leader, of this party
	 */
	@Override
	public boolean isLeader(OnlinePAFPlayer player) {
		return this.leader.equals(player.getUniqueId());
	}

	/**
	 * Returns true if the player is in the party. Returns false if the player
	 * is not in the party.
	 *
	 * @param player The player
	 * @return Returns true if the player is in the party. Returns false if the
	 * player is not in the party.
	 */
	@Override
	public boolean isInParty(OnlinePAFPlayer player) {
		return players.contains(player.getUniqueId()) || player.getUniqueId().equals(leader);
	}

	@Override
	public boolean isNobodyInvited() {
		return invited.isEmpty();
	}

	/**
	 * Gets the leader of this party
	 *
	 * @return Returns the party leader
	 */
	@Override
	public OnlinePAFPlayer getLeader() {
		PAFPlayer pafPlayer = getPlayerManager().getPlayer(this.leader);
		if (!(pafPlayer instanceof OnlinePAFPlayer))
			return null;
		return (OnlinePAFPlayer) getPlayerManager().getPlayer(this.leader);
	}

	/**
	 * Sets the party leader.
	 *
	 * @param player The player
	 */
	@Override
	public void setLeader(OnlinePAFPlayer player) {
		leader = player.getUniqueId();
		Main.getPartyManager().addPlayerToParty(player, this);
		players.remove(player.getUniqueId());
	}

	/**
	 * @return Returns all players in this party (inclusive the leader).
	 */
	@Override
	public ArrayList<OnlinePAFPlayer> getAllPlayers() {
		ArrayList<OnlinePAFPlayer> allPlayers = getPlayers();
		if (leader != null)
			allPlayers.add((OnlinePAFPlayer) getPlayerManager().getPlayer(leader));
		return allPlayers;
	}

	/**
	 * Returns the "normal" players who are in the party.
	 *
	 * @return Returns the "normal" players who are in the party.
	 */
	@Override
	public ArrayList<OnlinePAFPlayer> getPlayers() {
		ArrayList<OnlinePAFPlayer> lPlayers = new ArrayList<>();
		for (UUID player : players)
			lPlayers.add((OnlinePAFPlayer) getPlayerManager().getPlayer(player));
		return lPlayers;
	}

	/**
	 * Adds a player to the party
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player was added to this party. Returns false
	 * if the player was not added to this party
	 */
	@Override
	public boolean addPlayer(OnlinePAFPlayer pPlayer) {
		if (!players.contains(pPlayer.getUniqueId()) && invited.contains(pPlayer.getUniqueId())) {
			players.add(pPlayer.getUniqueId());
			Main.getPartyManager().addPlayerToParty(pPlayer, this);
			invited.remove(pPlayer.getUniqueId());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes a player from the party
	 *
	 * @param pPlayer The player
	 */
	private void removePlayer(OnlinePAFPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ CompilePatter.PLAYERPATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.General.PlayerHasLeftTheParty")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
	}

	private void removePlayerSilent(OnlinePAFPlayer pPlayer) {
		players.remove(pPlayer.getUniqueId());
		Main.getPartyManager().removePlayerFromParty(pPlayer);
	}

	@Override
	public void leaveParty(OnlinePAFPlayer pPlayer) {
		removePlayer(pPlayer);
		boolean needsNewLeader = needsNewLeader(pPlayer);
		if (deleteParty())
			return;
		if (needsNewLeader) {
			findNewLeader();
		}
	}

	@Override
	public void kickPlayer(OnlinePAFPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer")));
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ PLAYERPATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		deleteParty();
	}

	/**
	 * Invites a player into this party
	 *
	 * @param pPlayer The player
	 */
	@Override
	public void invite(final OnlinePAFPlayer pPlayer) {
		invited.add(pPlayer.getUniqueId());
		OnlinePAFPlayer lLeader = getLeader();
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYERPATTERN.matcher(Main.getInstance().getMessagesYml()
				.getString("Party.Command.Invite.YouWereInvitedBY")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
		pPlayer.sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getPartyPrefix()
				+ PLAYERPATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE")).replaceAll(Matcher.quoteReplacement(lLeader.getName()))
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Main.getInstance().getPartyCommand().getName() + " join " + lLeader.getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER")
				+ "\"}]}}}"));
		final PlayerParty party = this;
		ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (invited.contains(pPlayer.getUniqueId())) {
					invited.remove(pPlayer.getUniqueId());
					OnlinePAFPlayer lLeader = getLeader();
					pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYERPATTERN.matcher(Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.InvitationTimedOutInvited")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
					lLeader.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYERPATTERN.matcher(Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.InvitationTimedOutLeader")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
					if (isPartyEmpty()) {
						lLeader.sendMessage(
								new TextComponent(Main.getInstance().getPartyPrefix()
										+ Main.getInstance().getMessagesYml().getString(
										"Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
						Main.getPartyManager().deleteParty(party);
					}
				}
			}
		}, 60L, TimeUnit.SECONDS);
	}

	private boolean isPartyEmpty() {
		return players.isEmpty() && isNobodyInvited();
	}

	/**
	 * Returns the size of the invitation list
	 *
	 * @return Returns the size of the invitation list
	 */
	@Override
	public int getInviteListSize() {
		return invited.size();
	}


	/**
	 * Returns true if the player is already invited. Returns false if the
	 * player is not invited.
	 *
	 * @param player The player
	 * @return Returns true if the player is already invited. Returns false if
	 * the player is not invited.
	 */
	public boolean isInvited(OnlinePAFPlayer player) {
		return invited.contains(player.getUniqueId());
	}

	@Override
	public void sendMessage(TextComponent pText) {
		for (OnlinePAFPlayer player : getAllPlayers())
			player.sendMessage(pText);
	}

	private boolean deleteParty() {
		if (this.getAllPlayers().size() < 2) {
			sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
					.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
			Main.getPartyManager().deleteParty(this);
			return true;
		}
		return false;
	}

	private boolean needsNewLeader(OnlinePAFPlayer pPlayer) {
		if (isLeader(pPlayer)) {
			leader = null;
			return true;
		}
		return false;
	}

	private void findNewLeader() {
		OnlinePAFPlayer newLeader = (OnlinePAFPlayer) getPlayerManager().getPlayer(players.get(0));
		this.setLeader(newLeader);
		this.players.remove(newLeader.getUniqueId());
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + NEWLEADERPATTERN.matcher(Main.getInstance().getMessagesYml()
				.getString("Party.Command.Leave.NewLeaderIs")).replaceAll(Matcher.quoteReplacement(getLeader().getDisplayName()))));
	}
}
