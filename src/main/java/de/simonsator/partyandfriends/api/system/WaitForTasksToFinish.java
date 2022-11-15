package de.simonsator.partyandfriends.api.system;

import java.util.LinkedList;
import java.util.List;

public class WaitForTasksToFinish {
	private static final List<WaitForTasksToFinish> tasks = new LinkedList<>();
	private int currentlyRunning = 0;

	public WaitForTasksToFinish() {
		tasks.add(this);
	}

	public static void waitForTasksToFinish() {
		for (WaitForTasksToFinish task : tasks) {
			for (int i = 0; task.currentlyRunning > 0 && i < 20; i++) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			task.currentlyRunning = 0;
		}
	}

	public static void reset() {
		tasks.clear();
	}

	public void taskStarts() {
		currentlyRunning++;
	}

	public void taskFinished() {
		currentlyRunning--;
	}
}
