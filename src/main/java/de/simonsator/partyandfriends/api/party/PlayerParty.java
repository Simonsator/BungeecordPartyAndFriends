package de.simonsator.partyandfriends.api.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * Objects of this class are the party, where a player is in
 *
 * @author Simonsator
 * @version 2.0.0
 */
public abstract class PlayerParty {

	/**
	 * Returns true if the given player is the leader of this party, and it will
	 * returns false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns a true if the given player is the leader of this party,
	 * and it will returns false if he is not the leader, of this party
	 */
	public boolean isLeader(OnlinePAFPlayer player) {
		return this.getLeader().getUniqueId().equals(player.getUniqueId());
	}

	/**
	 * Returns true if the player is in the party. Returns false if the player
	 * is not in the party.
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is in the party. Returns false if the
	 * player is not in the party.
	 */
	public boolean isInParty(OnlinePAFPlayer pPlayer) {
		return isAMember(pPlayer) || pPlayer.getUniqueId().equals(getLeader().getUniqueId());
	}

	protected abstract boolean isAMember(OnlinePAFPlayer pPlayer);

	protected abstract List<UUID> getInvited();

	public boolean isNobodyInvited() {
		return getInvited().isEmpty();
	}

	/**
	 * Gets the leader of this party
	 *
	 * @return Returns the party leader
	 */
	public abstract OnlinePAFPlayer getLeader();

	/**
	 * Sets the party leader.
	 *
	 * @param player The player
	 */
	public abstract void setLeader(OnlinePAFPlayer player);

	/**
	 * @return Returns all players in this party (inclusive the leader).
	 */
	public List<OnlinePAFPlayer> getAllPlayers() {
		List<OnlinePAFPlayer> allPlayers = getPlayers();
		PAFPlayer leader = getLeader();
		if (leader != null)
			allPlayers.add((OnlinePAFPlayer) leader);
		return allPlayers;
	}

	/**
	 * Removes a player from the party
	 *
	 * @param pPlayer The player
	 */
	private void removePlayer(OnlinePAFPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ PatterCollection.PLAYER_PATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.General.PlayerHasLeftTheParty")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
	}

	protected abstract void removePlayerSilent(OnlinePAFPlayer pPlayer);

	/**
	 * Returns the "normal" players who are in the party.
	 *
	 * @return Returns the "normal" players who are in the party.
	 */
	public abstract List<OnlinePAFPlayer> getPlayers();

	/**
	 * Adds a player to the party
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player was added to this party. Returns false
	 * if the player was not added to this party
	 */
	public abstract boolean addPlayer(OnlinePAFPlayer pPlayer);

	public void leaveParty(OnlinePAFPlayer pPlayer) {
		removePlayer(pPlayer);
		boolean needsNewLeader = needsNewLeader(pPlayer);
		if (deleteParty())
			return;
		if (needsNewLeader) {
			findNewLeader();
		}
	}

	public void kickPlayer(OnlinePAFPlayer pPlayer) {
		removePlayerSilent(pPlayer);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
				.getString("Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer")));
		this.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers"))
				.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		deleteParty();
	}

	/**
	 * Invites a player into this party
	 *
	 * @param pPlayer The player
	 */
	public void invite(final OnlinePAFPlayer pPlayer) {
		addToInvited(pPlayer);
		OnlinePAFPlayer lLeader = getLeader();
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYER_PATTERN.matcher(Main.getInstance().getMessagesYml()
				.getString("Party.Command.Invite.YouWereInvitedBY")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
		pPlayer.sendPacket(new Chat("{\"text\":\"" + Main.getInstance().getPartyPrefix()
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE")).replaceAll(Matcher.quoteReplacement(lLeader.getName()))
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Main.getInstance().getPartyCommand().getName() + " join " + lLeader.getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER")
				+ "\"}]}}}"));
		final PlayerParty party = this;
		ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (isInvited(pPlayer)) {
					removeFromInvited(pPlayer);
					OnlinePAFPlayer lLeader = getLeader();
					pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYER_PATTERN.matcher(Main.getInstance()
							.getMessagesYml().getString("Party.Command.Invite.InvitationTimedOutInvited")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
					lLeader.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + PLAYER_PATTERN.matcher(Main.getInstance()
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

	public abstract void removeFromInvited(PAFPlayer pPlayer);

	protected abstract void addToInvited(OnlinePAFPlayer pPlayer);

	private boolean isPartyEmpty() {
		return getPlayers().isEmpty() && isNobodyInvited();
	}

	/**
	 * Returns the size of the invitation list
	 *
	 * @return Returns the size of the invitation list
	 */
	public abstract int getInviteListSize();

	/**
	 * Returns true if the player is already invited. Returns false if the
	 * player is not invited.
	 *
	 * @param player The player
	 * @return Returns true if the player is already invited. Returns false if
	 * the player is not invited.
	 */
	public abstract boolean isInvited(OnlinePAFPlayer player);

	public void sendMessage(TextComponent pText) {
		for (OnlinePAFPlayer player : getAllPlayers())
			player.sendMessage(pText);
	}

	private boolean deleteParty() {
		if (this.getAllPlayers().size() < 2) {
			sendMessage(new TextComponent(Main.getInstance().getPartyPrefix() + Main.getInstance().getMessagesYml()
					.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
			Main.getPartyManager().deleteParty(this);
			for (OnlinePAFPlayer player : getAllPlayers())
				removePlayerSilent(player);
			return true;
		}
		return false;
	}

	protected abstract boolean needsNewLeader(OnlinePAFPlayer pPlayer);

	protected abstract void findNewLeader();
}
