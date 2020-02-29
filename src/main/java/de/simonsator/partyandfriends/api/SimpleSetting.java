package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.subcommands.Settings;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 29.03.17
 */
public abstract class SimpleSetting extends Setting {
	public SimpleSetting(List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPermission, pPriority);
	}

	public SimpleSetting(List<String> pSettingNames, String pPermission, int pPriority, String pIdentifier) {
		super(pSettingNames, pPermission, pPriority, pIdentifier);
	}

	protected abstract String getMessage(OnlinePAFPlayer pPlayer);

	@Override
	public void outputMessage(OnlinePAFPlayer pPlayer) {
		pPlayer.sendPacket(new TextComponent(ComponentSerializer.parse(("{\"text\":\""
				+ getMessage(pPlayer) + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/"
				+ Friends.getInstance().getName() + " " + Settings.getInstance().getCommandName() + " " + getName()
				+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\""
				+ Main.getInstance().getMessages().getString("Friends.Command.Settings.ChangeThisSettingsHover")
				+ "\"}]}}}"))));
	}

}
