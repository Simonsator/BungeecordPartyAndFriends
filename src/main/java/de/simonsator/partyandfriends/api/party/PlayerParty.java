package de.simonsator.partyandfriends.api.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import de.simonsator.partyandfriends.party.subcommand.Join;
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
	private final String JOIN_COMMAND_NAME = " " + PartyCommand.getInstance().getSubCommand(Join.class).getCommandName() + " ";

	/**
	 * Returns true if the given player is the leader of this party, and it will
	 * returns false if he is not the leader, of this party
	 *
	 * @param player The player
	 * @return Returns a true if the given player is the leader of this party,
	 * and it will returns false if he is not the leader, of this party
	 */
	public boolean isLeader(OnlinePAFPlayer player) {
		return getLeader() != null && player != null && this.getLeader().getUniqueId().equals(player.getUniqueId());
	}

	public abstract boolean isBanned(OnlinePAFPlayer pPlayer);

	public abstract void setBanned(OnlinePAFPlayer pPlayer, boolean pIsBanned);

	public abstract boolean isPrivate();

	public abstract void setPrivateState(boolean pIsPrivate);

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
		sendMessage((PartyCommand.getInstance().getPrefix()
				+ PatterCollection.PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Party.Command.General.PlayerHasLeftTheParty")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
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
		pPlayer.sendMessage(Main.getInstance().getMessages()
				.get(PartyCommand.getInstance().getPrefix(), "Party.Command.Kick.KickedPlayerOutOfThePartyKickedPlayer"));
		this.sendMessage((PartyCommand.getInstance().getPrefix()
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Party.Command.Kick.KickedPlayerOutOfThePartyOthers"))
				.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		deleteParty();
	}

	/**
	 * Invites a player into this party
	 *
	 * @param pPlayer The player
	 */
	public void invite(final OnlinePAFPlayer pPlayer) {
		setBanned(pPlayer, false);
		OnlinePAFPlayer lLeader = getLeader();
		pPlayer.sendMessage((PartyCommand.getInstance().getPrefix() + PLAYER_PATTERN.matcher(Main.getInstance().getMessages()
				.getString("Party.Command.Invite.YouWereInvitedBY")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
		pPlayer.sendPacket(new Chat("{\"text\":\"" + PartyCommand.getInstance().getPrefix()
				+ PLAYER_PATTERN.matcher(Main.getInstance().getMessages().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGE")).replaceAll(Matcher.quoteReplacement(lLeader.getName()))
				+ "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ PartyCommand.getInstance().getName() + JOIN_COMMAND_NAME + lLeader.getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessages().getString("Party.Command.Invite.YouWereInvitedBYJSONMESSAGEHOVER")
				+ "\"}]}}}"));
		if (!isPrivate())
			return;
		addToInvited(pPlayer);
		final PlayerParty party = this;
		ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (isInvited(pPlayer)) {
					removeFromInvited(pPlayer);
					OnlinePAFPlayer lLeader = getLeader();
					pPlayer.sendMessage((PartyCommand.getInstance().getPrefix() + PLAYER_PATTERN.matcher(Main.getInstance()
							.getMessages().getString("Party.Command.Invite.InvitationTimedOutInvited")).replaceAll(Matcher.quoteReplacement(lLeader.getDisplayName()))));
					lLeader.sendMessage((PartyCommand.getInstance().getPrefix() + PLAYER_PATTERN.matcher(Main.getInstance()
							.getMessages().getString("Party.Command.Invite.InvitationTimedOutLeader")).replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
					if (isPartyEmpty()) {
						lLeader.sendMessage(
								(PartyCommand.getInstance().getPrefix()
										+ Main.getInstance().getMessages().getString(
										"Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
						PartyManager.getInstance().deleteParty(party);
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
	 * Returns true if the player is already invited or if the party is public. Returns false if the
	 * player is not invited.
	 *
	 * @param pPlayer The player
	 * @return Returns true if the player is already invited. Returns false if
	 * the player is not invited.
	 */
	public boolean isInvited(OnlinePAFPlayer pPlayer) {
		return !isPrivate() || getInvited().contains(pPlayer.getUniqueId());
	}

	public void sendMessage(TextComponent pText) {
		for (OnlinePAFPlayer player : getAllPlayers())
			player.sendMessage(pText);
	}

	public void sendMessage(String pText) {
		for (OnlinePAFPlayer player : getAllPlayers())
			player.sendMessage(pText);
	}

	private boolean deleteParty() {
		int partyMemberCount = this.getAllPlayers().size();
		if ((partyMemberCount < 2 && isPrivate()) || partyMemberCount == 0) {
			sendMessage((PartyCommand.getInstance().getPrefix() + Main.getInstance().getMessages()
					.getString("Party.Command.General.DissolvedPartyCauseOfNotEnoughPlayers")));
			PartyManager.getInstance().deleteParty(this);
			for (OnlinePAFPlayer player : getAllPlayers())
				removePlayerSilent(player);
			return true;
		}
		return false;
	}

	protected abstract boolean needsNewLeader(OnlinePAFPlayer pPlayer);

	protected abstract void findNewLeader();

}
