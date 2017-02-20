package de.simonsator.partyandfriends.party.subcommand;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/**
 * The class which will be executed on /party leave
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Leave extends PartySubCommand {

	public Leave(List<String> pCommands, int pPriority, String pHelpText, String pPermission) {
		super(pCommands, pPriority, pHelpText, pPermission);
	}

	/**
	 * Will be executed on /party leave
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		party.leaveParty(pPlayer);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
				+ Main.getInstance().getMessagesYml().getString("Party.Command.Leave.YouLeftTheParty")));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return pPermissionHeight >= PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT;
	}
}
