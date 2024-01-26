package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.utilities.SubCommand;

import java.util.List;

public abstract class Setting extends SubCommand {
	public Setting(List<String> pSettingNames, String pPermission, int pPriority) {
		this("", pSettingNames, pPermission, pPriority);
	}

	public Setting(String pPrefix, List<String> pSettingNames, String pPermission, int pPriority) {
		super(pSettingNames, pPriority, "", pPrefix, pPermission);
	}

	public Setting(List<String> pSettingNames, String pPermission, int pPriority, String pIdentifier) {
		this("", pSettingNames, pPermission, pPriority, pIdentifier);
	}

	public Setting(String pPrefix, List<String> pSettingNames, String pPermission, int pPriority, String pIdentifier) {
		this(pPrefix, pSettingNames, pPermission, pPriority);
	}

	public abstract void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs);

	public abstract void outputMessage(OnlinePAFPlayer pPlayer);

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {

	}
}
