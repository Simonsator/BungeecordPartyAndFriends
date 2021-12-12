package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.commands.Friends;
import de.simonsator.partyandfriends.friends.subcommands.Settings;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
		TextComponent message = new TextComponent(TextComponent.fromLegacyText(getMessage(pPlayer)));
		message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"
				+ Friends.getInstance().getName() + " " + Settings.getInstance().getCommandName() + " " + getName()));
		BaseComponent[] clickHereMessage = TextComponent.fromLegacyText(Main.getInstance().getMessages().getString("Friends.Command.Settings.ChangeThisSettingsHover"));
		message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, clickHereMessage));
		pPlayer.sendPacket(message);

	}

}
