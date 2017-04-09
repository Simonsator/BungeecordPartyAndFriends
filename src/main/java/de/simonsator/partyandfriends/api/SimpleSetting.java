package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.subcommands.Settings;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 29.03.17
 */
public abstract class SimpleSetting extends Setting {
	public SimpleSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	protected abstract String getMessage(OnlinePAFPlayer pPlayer);

	@Override
	public void outputMessage(OnlinePAFPlayer pPlayer) {
		pPlayer.sendPacket(new Chat("{\"text\":\""
				+ getMessage(pPlayer) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Friends.getInstance().getName() + " " + Settings.getInstance().getCommandName() + " " + getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessagesYml().getString("Friends.Command.Settings.ChangeThisSettingsHover")
				+ "\"}]}}}"));
	}

}
