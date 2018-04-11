package de.simonsator.partyandfriends.party.playerpartys;

import static de.simonsator.partyandfriends.utilities.PatterCollection.NEW_LEADER_PATTERN;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;

import de.simonsator.partyandfriends.api.events.party.PartyLeaderChangeEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import net.md_5.bungee.api.ProxyServer;

public class LocalPlayerParty extends PlayerParty {
	/**
	 * The "normal" players which are in the party
	 */
	private final List<UUID> players = new ArrayList<>();
	/**
	 * The players who are invited into this party
	 */
	private final List<UUID> invited = new ArrayList<>();
	/**
	 * The leader of the party
	 */
	private UUID leader;
	private boolean privateParty = true;
	private Set<UUID> bannedPlayers = new HashSet<>();

	/**
	 * Initials a new party
	 *
	 * @param leader The leader of the party
	 */
	public LocalPlayerParty(OnlinePAFPlayer leader) {
		this.leader = leader.getUniqueId();
	}

	@Override
	public boolean isBanned(OnlinePAFPlayer pPlayer) {
		return bannedPlayers.contains(pPlayer.getUniqueId());
	}

	@Override
	public void setBanned(OnlinePAFPlayer pPlayer, boolean pIsBanned) {
		if (pIsBanned)
			bannedPlayers.add(pPlayer.getUniqueId());
		else bannedPlayers.remove(pPlayer.getUniqueId());
	}

	@Override
	public boolean isPrivate() {
		return privateParty;
	}

	@Override
	public void setPrivateState(boolean pIsPrivate) {
		privateParty = pIsPrivate;
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
		PAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(this.leader);
		if (!(pafPlayer instanceof OnlinePAFPlayer))
			return null;
		return (OnlinePAFPlayer) pafPlayer;
	}

	/**
	 * Sets the party leader.
	 *
	 * @param player The player
	 */
	@Override
	public void setLeader(OnlinePAFPlayer player) {
		leader = player.getUniqueId();
		PartyManager.getInstance().addPlayerToParty(player, this, false);
		ProxyServer.getInstance().getPluginManager().callEvent(new PartyLeaderChangeEvent(this));
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
			lPlayers.add((OnlinePAFPlayer) PAFPlayerManager.getInstance().getPlayer(player));
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
		if ((!players.contains(pPlayer.getUniqueId()) && (invited.contains(pPlayer.getUniqueId()) || isLeader(pPlayer) || !privateParty)) && !isBanned(pPlayer)) {
			players.add(pPlayer.getUniqueId());
			PartyManager.getInstance().addPlayerToParty(pPlayer, this, true);
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
	protected void removePlayerSilent(PAFPlayer pPlayer) {
		players.remove(pPlayer.getUniqueId());
		PartyManager.getInstance().removePlayerFromParty(pPlayer);
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

	@Override
	protected boolean needsNewLeader(PAFPlayer pPlayer) {
		if (isLeader(pPlayer)) {
			leader = null;
			return true;
		}
		return false;
	}

	@Override
	protected void findNewLeader() {
		OnlinePAFPlayer newLeader = getPlayers().get(0);
		removePlayerSilent(newLeader);
		this.setLeader(newLeader);
		this.sendMessage(PartyCommand.getInstance().getPrefix() + NEW_LEADER_PATTERN.matcher(Main.getInstance().getMessages()
				.getString("Party.Command.Leave.NewLeaderIs")).replaceAll(Matcher.quoteReplacement(getLeader().getDisplayName())));
	}
}
