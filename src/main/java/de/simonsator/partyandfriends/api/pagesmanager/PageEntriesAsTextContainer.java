package de.simonsator.partyandfriends.api.pagesmanager;

public class PageEntriesAsTextContainer {
	private final boolean FURTHER_ITEMS_EXIST;
	private final String LIMITED_LIST_TEXT;
	private final int PAGE;

	public PageEntriesAsTextContainer(boolean pFurtherItemsExist, String pLimitedTextList, int pPage) {
		this.FURTHER_ITEMS_EXIST = pFurtherItemsExist;
		this.LIMITED_LIST_TEXT = pLimitedTextList;
		PAGE = pPage;
	}

	public boolean doesFurtherItemsExist() {
		return FURTHER_ITEMS_EXIST;
	}

	public String getLimitedTextList() {
		return LIMITED_LIST_TEXT;
	}

	public int getPage() {
		return PAGE;
	}

}
