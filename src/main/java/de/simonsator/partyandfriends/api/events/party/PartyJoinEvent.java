package de.simonsator.partyandfriends.api.events.party;

import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.plugin.Cancellable;

public class PartyJoinEvent extends PartyEvent implements Cancellable {
	private boolean isCancelled = false;

	public PartyJoinEvent(PlayerParty pParty) {
		super(pParty);
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
