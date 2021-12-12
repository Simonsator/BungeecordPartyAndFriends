package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.events.command.JumpToFriendEvent;
import de.simonsator.partyandfriends.api.friends.ServerConnector;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.friends.settings.JumpSetting;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.main.Main.getInstance;

/***
 * The command jump
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Jump extends FriendSubCommand {
	private final Set<ServerInfo> notCheckSameServer = new HashSet<>();

	public Jump(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
	}

	/**
	 * Sets the server connector, which will be used to join a server.
	 * Please use instead {@link de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass#getServerConnector label}
	 *
	 * @param pConnector The connector
	 */
	@Deprecated
	public static void setServerConnector(ServerConnector pConnector) {
		PAFPlayerClass.setServerConnector(pConnector);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (!isPlayerOnline(pPlayer, playerQuery))
			return;
		OnlinePAFPlayer friend = (OnlinePAFPlayer) playerQuery;
		if (!isAFriendOf(pPlayer, friend, args))
			return;
		ServerInfo toJoin = friend.getServer();
		if (!serverExists(pPlayer, toJoin))
			return;
		if (!notCheckSameServer.contains(toJoin))
			if (isAlreadyOnServer(pPlayer, toJoin))
				return;
		if (!allowsJumps(pPlayer, friend))
			return;
		if (isDisabled(pPlayer, toJoin))
			return;
		JumpToFriendEvent event = new JumpToFriendEvent(pPlayer, friend, args, this);
		BukkitBungeeAdapter.getInstance().callEvent(event);
		if (event.isCancelled())
			return;
		if (!toJoin.equals(pPlayer.getServer()))
			pPlayer.connect(toJoin);
		pPlayer.sendMessage(
				(
						PREFIX + PatterCollection.PLAYER_PATTERN
								.matcher(getInstance().getMessages()
										.getString("Friends.Command.Jump.JoinedTheServer"))
								.replaceAll(Matcher.quoteReplacement(friend.getDisplayName()))));
	}

	private boolean serverExists(OnlinePAFPlayer pPlayer, ServerInfo toJoin) {
		if (toJoin != null)
			return true;
		sendError(pPlayer, "Friends.Command.Jump.CanNotJump");
		return false;
	}

	private boolean allowsJumps(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pQueryPlayer) {
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.Jump.Enabled") && pQueryPlayer.getSettingsWorth(JumpSetting.SETTINGS_ID) == JumpSetting.FRIENDS_CAN_NOT_JUMP_TO_PLAYER_STATE) {
			sendError(pPlayer, "Friends.Command.Jump.CanNotJump");
			return false;
		}
		return true;
	}

	private boolean isAlreadyOnServer(OnlinePAFPlayer pPlayer, ServerInfo pToJoin) {
		if (pToJoin.equals(pPlayer.getServer())) {
			sendError(pPlayer, "Friends.Command.Jump.AlreadyOnTheServer");
			return true;
		}
		return false;
	}

	private boolean isPlayerOnline(OnlinePAFPlayer pSender, PAFPlayer pQueryPlayer) {
		if (!pQueryPlayer.isOnline()) {
			sendError(pSender, new TextComponent(TextComponent.fromLegacyText(Main.getInstance().getMessages().getString("Friends.General.PlayerIsOffline").
					replace("[PLAYER]", pQueryPlayer.getDisplayName()))));
			return false;
		}
		return true;
	}

	private boolean isDisabled(OnlinePAFPlayer pPlayer, ServerInfo pToJoin) {
		if (getInstance().getGeneralConfig().getStringList("Commands.Friends.SubCommands.Jump.DisabledServers").contains(pToJoin.getName())) {
			sendError(pPlayer, "Friends.Command.Jump.CanNotJump");
			return true;
		}
		return false;
	}

	public void checkForSameServer(ServerInfo pServer) {
		notCheckSameServer.remove(pServer);
	}

	public void doNotCheckForSameServer(ServerInfo pServer) {
		notCheckSameServer.add(pServer);
	}
}
