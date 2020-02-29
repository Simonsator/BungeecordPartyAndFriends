package de.simonsator.partyandfriends.admin.commands.subcommands;

import de.simonsator.partyandfriends.api.AdminSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class DeletePlayerAdminSubCommand extends AdminSubCommand {
	public DeletePlayerAdminSubCommand(String pPrefix) {
		super(new String[]{"deleteplayer", "delete"}, 1, "§5pafadmin delete [Player] §8- §7Deletes all data of the given player which were saved by PAF", pPrefix);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!isPlayerGiven(sender, args))
			return;
		PAFPlayer playerQuery = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (!doesPlayerExist(sender, playerQuery))
			return;
		if (playerQuery instanceof OnlinePAFPlayer) {
			OnlinePAFPlayer onlinePAFPlayer = (OnlinePAFPlayer) playerQuery;
			if (onlinePAFPlayer.getPlayer() == null) {
				sender.sendMessage("Could not delete player data. Player is playing on a different bungeecord which is connected to this one with redisbungee");
				return;
			} else
				onlinePAFPlayer.getPlayer().disconnect(new TextComponent("Disconnected from server"));
		}
		playerQuery.deleteAccount();
		sender.sendMessage(Main.getInstance().getMessages().getString("PAFAdmin.Command.DeletePlayer.PlayerDeleted").replace("[PLAYER]", args[1]));
	}

	private boolean doesPlayerExist(CommandSender sender, PAFPlayer pGivenPlayer) {
		if (!pGivenPlayer.doesExist()) {
			sendError(sender, "Friends.General.DoesNotExist");
			return false;
		}
		return true;
	}

	protected boolean isPlayerGiven(CommandSender pPlayer, String[] args) {
		if (args.length < 2) {
			sendError(pPlayer, "Friends.General.NoPlayerGiven");
			return false;
		}
		return true;
	}

}
