package de.simonsator.partyandfriends.utilities.disable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 00pfl
 * @version 1.0.0 on 02.07.2016
 */
public class Disabler {
	private static Disabler instance = null;
	private final List<Deactivated> deactivateds = new ArrayList<>();


	public void disableAll() {
		for (Deactivated toDeactivated : deactivateds)
			toDeactivated.onDisable();
	}

	public void registerDeactivated(Deactivated pDeactivated) {
		deactivateds.add(pDeactivated);
	}

	public static Disabler getInstance() {
		if (instance != null) return instance;
		return instance = new Disabler();
	}

}
