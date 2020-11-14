package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.Setting;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.friends.settings.*;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.party.settings.InviteSetting;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * The command settings
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class Settings extends FriendSubCommand {
	private static Settings instance;
	private final List<Setting> SETTINGS = new ArrayList<>();

	public Settings(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
		instance = this;
		ConfigurationCreator config = Main.getInstance().getGeneralConfig();
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Enabled"))
			SETTINGS.add(new FriendRequestSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.Jump.Enabled"))
			SETTINGS.add(new JumpSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.Jump.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.Jump.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.Jump.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.Offline.Enabled"))
			SETTINGS.add(new OfflineSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.Offline.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.Offline.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.Offline.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Enabled"))
			SETTINGS.add(new OnlineStatusNotificationSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PM.Enabled"))
			SETTINGS.add(new PMSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.PM.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.PM.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.PM.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Enabled"))
			SETTINGS.add(new InviteSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Priority")));
		Collections.sort(SETTINGS);
	}

	public static Settings getInstance() {
		return instance;
	}

	public void registerSetting(Setting pSetting) {
		SETTINGS.add(pSetting);
		Collections.sort(SETTINGS);
	}

	public void unregisterSetting(Setting pSetting) {
		SETTINGS.remove(pSetting);
	}

	public Setting getSettingInstance(Class<? extends Setting> pSettingClass) {
		for (Setting setting : SETTINGS)
			if (setting.getClass().equals(pSettingClass))
				return setting;
		return null;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length <= 1) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Settings.Introduction"));
			for (Setting setting : SETTINGS) {
				pPlayer.sendMessage(Main.getInstance().getMessages().getString("Friends.Command.Settings.SplitLine"));
				setting.outputMessage(pPlayer);
			}
		} else if (!changeSetting(pPlayer, args))
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Settings.NotFound"));
	}

	private boolean changeSetting(OnlinePAFPlayer pPlayer, String[] args) {
		for (Setting setting : SETTINGS)
			if (setting.isApplicable(pPlayer, args[1])) {
				setting.changeSetting(pPlayer, args);
				return true;
			}
		return false;
	}
}
