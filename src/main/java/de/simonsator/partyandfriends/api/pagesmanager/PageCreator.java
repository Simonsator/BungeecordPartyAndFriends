package de.simonsator.partyandfriends.api.pagesmanager;

import java.util.List;

public interface PageCreator<T> {
	default PageAsListContainer<T> createPage(List<T> playerListElements, String[] args, int pEntriesPerPage) {
		int listSize = playerListElements.size();
		int page = 1;
		if (args.length > 1) {
			try {
				page = Integer.valueOf(args[1]);
				if (page < 1)
					page = 1;
			} catch (NumberFormatException ignored) {
			}
		}
		int start = (page - 1) * pEntriesPerPage;
		int end = page * pEntriesPerPage;
		if (page == 1)
			end = pEntriesPerPage;
		if (end >= playerListElements.size())
			end = listSize;
		if (end <= start)
			return null;
		return new PageAsListContainer<>(end < listSize, playerListElements.subList(start, end), page);
	}
}
