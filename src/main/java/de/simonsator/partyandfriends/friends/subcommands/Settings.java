package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.Setting;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.subcommands.SubCommandManager;
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
	private final SubCommandManager<Setting> SETTINGS_MANAGER;

	public Settings(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
		instance = this;
		ConfigurationCreator config = Main.getInstance().getGeneralConfig();
		SETTINGS_MANAGER = new SubCommandManager<>(false);
		List<Setting> settings = new ArrayList<>(6);
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Enabled"))
			settings.add(new FriendRequestSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.FriendRequest.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.Jump.Enabled"))
			settings.add(new JumpSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.Jump.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.Jump.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.Jump.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.Offline.Enabled"))
			settings.add(new OfflineSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.Offline.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.Offline.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.Offline.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Enabled"))
			settings.add(new OnlineStatusNotificationSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.NotifyOnlineStatusChange.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PM.Enabled"))
			settings.add(new PMSetting(config.getStringList("Commands.Friends.SubCommands.Settings.Settings.PM.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.PM.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.PM.Priority")));
		if (Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Enabled"))
			settings.add(new InviteSetting(PREFIX, config.getStringList("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Names"),
					config.getString("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Permission"),
					config.getInt("Commands.Friends.SubCommands.Settings.Settings.PartyInvite.Priority")));
		SETTINGS_MANAGER.addSubCommands(settings);
	}

	public static Settings getInstance() {
		return instance;
	}

	public void registerSetting(Setting pSetting) {
		SETTINGS_MANAGER.addSubCommand(pSetting);
	}

	public void unregisterSetting(Setting pSetting) {
		SETTINGS_MANAGER.removeSubCommand(pSetting);
	}

	public Setting getSettingInstance(Class<? extends Setting> pSettingClass) {
		return SETTINGS_MANAGER.getSubCommand(pSettingClass);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length <= 1) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Settings.Introduction"));
			SETTINGS_MANAGER.forEach(setting -> {
				pPlayer.sendMessage(Main.getInstance().getMessages().getString("Friends.Command.Settings.SplitLine"));
				setting.outputMessage(pPlayer);
			});
			return;
		}
		if (!changeSetting(pPlayer, args)) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.Settings.NotFound"));
		}
	}

	private boolean changeSetting(OnlinePAFPlayer pPlayer, String[] args) {
		Setting setting = SETTINGS_MANAGER.getSubCommand(args[1]);
		if (setting != null && setting.hasPermission(pPlayer)) {
			setting.changeSetting(pPlayer, args);
			return true;
		}
		return false;
	}

	@Override
	public List<String> tabCompleteArgument(String[] input) {
		return Collections.emptyList();
	}
}
