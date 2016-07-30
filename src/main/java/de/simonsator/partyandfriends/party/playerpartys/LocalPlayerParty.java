package de.simonsator.partyandfriends.party.playerpartys;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getPlayerManager;
import static de.simonsator.partyandfriends.utilities.PatterCollection.NEW_LEADER_PATTERN;

public class LocalPlayerParty extends PlayerParty {
	/**
	 * The leader of the party
	 */
	private UUID leader;
	/**
	 * The "normal" players which are in the party
	 */
	private final List<UUID> players = new ArrayList<>();
	/**
	 * The players who are invited into this party
	 */
	private final List<UUID> invited = new ArrayList<>();

	/**
	 * Initials a new party
	 *
	 * @param leader The leader of the party
	 */
	public LocalPlayerParty(OnlinePAFPlayer leader) {
		this.leader = leader.getUniqueId();
	}

	@Override
	protected boolean isAMember(OnlinePAFPlayer pPlayer) {
		return players.contains(pPlayer.getUniqueId());
	}

	@Override
	protected List<UUID> getInvited() {
		return invited;
	}

	/**
	 * Gets the leader of this party
	 *
	 * @return Returns the party leader
	 */
	@Override
	public OnlinePAFPlayer getLeader() {
		if (leader == null)
			return null;
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
	 * Returns the "normal" players who are in the party.
	 *
	 * @return Returns the "normal" players who are in the party.
	 */
	@Override
	public List<OnlinePAFPlayer> getPlayers() {
		List<OnlinePAFPlayer> lPlayers = new ArrayList<>();
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
		if (!players.contains(pPlayer.getUniqueId()) && (invited.contains(pPlayer.getUniqueId()) || isLeader(pPlayer))) {
			players.add(pPlayer.getUniqueId());
			Main.getPartyManager().addPlayerToParty(pPlayer, this);
			removeFromInvited(pPlayer);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void removeFromInvited(PAFPlayer pPlayer) {
		invited.remove(pPlayer.getUniqueId());
	}

	@Override
	protected void addToInvited(OnlinePAFPlayer pPlayer) {
		invited.add(pPlayer.getUniqueId());
	}

	@Override
	protected void removePlayerSilent(OnlinePAFPlayer pPlayer) {
		players.remove(pPlayer.getUniqueId());
		Main.getPartyManager().removePlayerFromParty(pPlayer);
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

	protected boolean needsNewLeader(OnlinePAFPlayer pPlayer) {
		if (isLeader(pPlayer)) {
			leader = null;
			return true;
		}
		return false;
	}

	@Override
	protected void findNewLeader() {
		OnlinePAFPlayer newLeader = getPlayers().get(0);
		this.setLeader(newLeader);
		removePlayerSilent(newLeader);
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + NEW_LEADER_PATTERN.matcher(Main.getInstance().getMessagesYml()
				.getString("Party.Command.Leave.NewLeaderIs")).replaceAll(Matcher.quoteReplacement(getLeader().getDisplayName()))));
	}
}
