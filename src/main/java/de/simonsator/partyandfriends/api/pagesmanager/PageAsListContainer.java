package de.simonsator.partyandfriends.api.pagesmanager;

import java.util.List;

public class PageAsListContainer<T> {
	private final boolean FURTHER_ITEMS_EXIST;
	private final List<T> LIMITED_LIST;
	private final int PAGE;

	public PageAsListContainer(boolean pFurtherItemsExist, List<T> pLimitedList, int pPage) {
		this.FURTHER_ITEMS_EXIST = pFurtherItemsExist;
		this.LIMITED_LIST = pLimitedList;
		PAGE = pPage;
	}

	public boolean doesFurtherItemsExist() {
		return FURTHER_ITEMS_EXIST;
	}

	public List<T> getLimitedList() {
		return LIMITED_LIST;
	}

	public int getPage() {
		return PAGE;
	}
}
