package de.simonsator.partyandfriends.utilities.disable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 00pfl
 * @version 1.0.0 on 02.07.2016
 */
public class Disabler {
	private static Disabler instance = null;
	private final List<Deactivated> toDisable = new ArrayList<>();

	public static Disabler getInstance() {
		if (instance != null) return instance;
		return instance = new Disabler();
	}

	public void disableAll() {
		for (Deactivated toDeactivated : toDisable)
			toDeactivated.onDisable();
	}

	public void registerDeactivated(Deactivated pDeactivated) {
		toDisable.add(pDeactivated);
	}

}
