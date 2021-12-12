package de.simonsator.partyandfriends.api.party.abstractcommands;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.MAX_PLAYERS_IN_PARTY_PATTERN;

public abstract class PartyJoinInviteSubCommand extends PartySubCommand {
	private static List<MaxPlayerPermissionCollection> maxPlayerPermissionCollections = null;
	private final Matcher TOO_MANY_PLAYERS_IN_PARTY_MATCHER;
	private final int DEFAULT_MAX_PLAYERS_PER_PARTY;

	public PartyJoinInviteSubCommand(List<String> pCommands, int pPriority, String pHelpText, String pPermission, String pMaxPlayersInPartyMessage) {
		super(pCommands, pPriority, pHelpText, pPermission);
		ConfigurationCreator config = Main.getInstance().getGeneralConfig();
		DEFAULT_MAX_PLAYERS_PER_PARTY = config.getInt("Party.MaxPlayersPerParty.Default");
		TOO_MANY_PLAYERS_IN_PARTY_MATCHER = MAX_PLAYERS_IN_PARTY_PATTERN
				.matcher(pMaxPlayersInPartyMessage);
		if (DEFAULT_MAX_PLAYERS_PER_PARTY != 0 && maxPlayerPermissionCollections == null) {
			maxPlayerPermissionCollections = new ArrayList<>();
			for (String key : config.getSectionKeys("Party.MaxPlayersPerParty.AddSlotsPermissions")) {
				maxPlayerPermissionCollections.add(new MaxPlayerPermissionCollection(config.getInt("Party.MaxPlayersPerParty.AddSlotsPermissions." + key + ".SlotsToAdd"),
						config.getString("Party.MaxPlayersPerParty.AddSlotsPermissions." + key + ".Permission")));
			}
		}
	}

	protected boolean canInvite(OnlinePAFPlayer pPartyLeader, PlayerParty pParty, OnlinePAFPlayer pMessageReceiver) {
		if (!pPartyLeader.getPlayer()
				.hasPermission(Main.getInstance().getGeneralConfig().getString("Party.MaxPlayersPerParty.NoLimitPermission")))
			if (DEFAULT_MAX_PLAYERS_PER_PARTY > 1) {
				int maxPlayersForThisParty = calculateMaxPlayersForParty(pPartyLeader, pParty);
				if (maxPlayersForThisParty < pParty.getAllPlayers().size() + pParty.getInviteListSize() + 1) {
					pMessageReceiver.sendMessage(PREFIX + TOO_MANY_PLAYERS_IN_PARTY_MATCHER
							.replaceAll(Matcher.quoteReplacement(
									maxPlayersForThisParty + "")));
					return false;
				}
			}
		return true;
	}

	private int calculateMaxPlayersForParty(OnlinePAFPlayer pPlayer, PlayerParty party) {
		int maxPlayersInParty = DEFAULT_MAX_PLAYERS_PER_PARTY;
		for (PartyJoinInviteSubCommand.MaxPlayerPermissionCollection maxPlayerPermissionCollection : maxPlayerPermissionCollections)
			if (pPlayer.hasPermission(maxPlayerPermissionCollection.PERMISSION)) {
				maxPlayersInParty += maxPlayerPermissionCollection.SLOTS_TO_ADD;
			}
		return maxPlayersInParty;
	}

	private static class MaxPlayerPermissionCollection {
		public final int SLOTS_TO_ADD;
		public final String PERMISSION;

		private MaxPlayerPermissionCollection(int pSlotsToAdd, String permission) {
			SLOTS_TO_ADD = pSlotsToAdd;
			PERMISSION = permission;
		}
	}
}
