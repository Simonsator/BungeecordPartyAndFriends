package de.simonsator.partyandfriends.main.listener;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.OnlineStatusChangedMessageEvent;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.settings.OfflineSetting;
import de.simonsator.partyandfriends.friends.settings.OnlineStatusNotificationSetting;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The class with the PlayerDisconnectEvent event.
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class PlayerDisconnectListener implements Listener {
	private final boolean ONLINE_STATUS_CHANGE_SETTING_ENABLED;

	public PlayerDisconnectListener() {
		ONLINE_STATUS_CHANGE_SETTING_ENABLED = Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Enabled");
	}

	/**
	 * Will be executed on player disconnect
	 *
	 * @param pEvent The disconnect event
	 */
	@EventHandler
	public void onPlayerDisconnect(final PlayerDisconnectEvent pEvent) {
		if (Main.getInstance().isShuttingDown())
			return;
		if (pEvent.getPlayer().getServer() == null)
			return;
		final UUID uuid = pEvent.getPlayer().getUniqueId();
		BukkitBungeeAdapter.getInstance().runAsync(Main.getInstance(), () -> playerDisconnected(pEvent, uuid));
	}

	private void playerDisconnected(PlayerDisconnectEvent pEvent, UUID pUUID) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pUUID);
		PlayerParty party = PartyManager.getInstance().getParty(pUUID);
		if (party != null)
			party.leaveParty(player);
		if (player.getSettingsWorth(OfflineSetting.SETTINGS_ID) == OfflineSetting.FRIENDS_CAN_SEE_PLAYER_IS_ONLINE_STATE) {
			String message = Friends.getInstance().getPrefix() + PLAYER_PATTERN
					.matcher(Main.getInstance().getMessages()
							.getString("Friends.General.PlayerIsNowOffline"))
					.replaceAll(Matcher.quoteReplacement(player.getDisplayName()));
			OnlineStatusChangedMessageEvent event = new OnlineStatusChangedMessageEvent(player, message, player.getFriends());
			BukkitBungeeAdapter.getInstance().callEvent(event);
			if (!event.isCancelled())
				for (PAFPlayer friend : event.getFriends())
					if (ONLINE_STATUS_CHANGE_SETTING_ENABLED && friend.getSettingsWorth(OnlineStatusNotificationSetting.SETTINGS_ID) == 0)
						friend.sendMessage((event.getMessage()));
			player.updateLastOnline();
		}
	}
}