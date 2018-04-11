package de.simonsator.partyandfriends.api;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 28.03.17
 */
public abstract class Setting implements Comparable<Setting> {
	private final List<String> SETTING_NAMES;
	private final Integer PRIORITY;
	private final String PERMISSION;

	public Setting(List<String> pSettingNames, String pPermission, int pPriority) {
		SETTING_NAMES = pSettingNames;
		PRIORITY = pPriority;
		PERMISSION = pPermission;
	}

	public Setting(List<String> pSettingNames, String pPermission, int pPriority, String pIdentifier) {
		SETTING_NAMES = pSettingNames;
		PRIORITY = pPriority;
		PERMISSION = pPermission;
	}

	public boolean isApplicable(OnlinePAFPlayer pPlayer, String pSettingName) {
		return SETTING_NAMES.contains(pSettingName.toLowerCase()) && pPlayer.hasPermission(PERMISSION);
	}

	protected String getName() {
		return SETTING_NAMES.get(0);
	}

	public abstract void changeSetting(OnlinePAFPlayer pPlayer, String[] pArgs);

	public abstract void outputMessage(OnlinePAFPlayer pPlayer);

	@Override
	public int compareTo(Setting pToCompare) {
		return pToCompare.PRIORITY.compareTo(PRIORITY);
	}
}
