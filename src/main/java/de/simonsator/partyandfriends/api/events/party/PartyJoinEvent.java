package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.plugin.Cancellable;

public class PartyJoinEvent extends PartyEvent implements Cancellable {
	private final OnlinePAFPlayer PLAYER;
	private boolean isCancelled = false;

	public PartyJoinEvent(PlayerParty pParty, OnlinePAFPlayer pPlayer) {
		super(pParty);
		PLAYER = pPlayer;
	}

	public OnlinePAFPlayer getPlayer() {
		return PLAYER;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = b;
	}
}
