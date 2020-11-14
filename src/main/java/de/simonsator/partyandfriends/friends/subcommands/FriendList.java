package de.simonsator.partyandfriends.friends.subcommands;

import de.simonsator.partyandfriends.api.TextReplacer;
import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pagesmanager.PageAsListContainer;
import de.simonsator.partyandfriends.api.pagesmanager.PageCreator;
import de.simonsator.partyandfriends.api.pagesmanager.PageEntriesAsTextContainer;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import de.simonsator.partyandfriends.utilities.PlayerListElement;
import de.simonsator.partyandfriends.utilities.ServerDisplayNameCollection;
import net.md_5.bungee.api.config.ServerInfo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/***
 * The command list
 *
 * @author Simonsator
 * @version 1.0.0
 */
public class FriendList extends FriendSubCommand implements PageCreator<PlayerListElement> {
	private final String LAST_ONLINE_COLOR = Main.getInstance().getMessages().getString("Friends.Command.List.TimeColor");
	private final int ENTRIES_PER_PAGE = Main.getInstance().getGeneralConfig().getInt("Commands.Friends.SubCommands.List.EntriesPerPage");
	private final boolean SORT_ELEMENTS;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Main.getInstance().getGeneralConfig().getString("General.Time.Format"),
			Locale.forLanguageTag(Main.getInstance().getGeneralConfig().getString("General.Time.LanguageTag")));
	private final List<TextReplacer> replacerList = new ArrayList<>();

	public FriendList(List<String> pCommands, int pPriority, String pHelp, String pPermission) {
		super(pCommands, pPriority, pHelp, pPermission);
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(Main.getInstance().getGeneralConfig().getString("General.Time.TimeZone")));
		SORT_ELEMENTS = Main.getInstance().getGeneralConfig().getBoolean("Commands.Friends.SubCommands.List.SortElements");
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PageEntriesAsTextContainer friendsCombined = getFriendsCombined(pPlayer, args);
		if (!hasFriends(pPlayer, friendsCombined))
			return;
		assert friendsCombined != null;
		if (friendsCombined.getLimitedTextList() == null) {
			pPlayer.sendMessage(PREFIX + Main.getInstance().getMessages().getString("Friends.Command.List.PageDoesNotExist"));
			return;
		}
		pPlayer.sendMessage(PREFIX
				+ Main.getInstance().getMessages().getString("Friends.Command.List.FriendsList")
				+ friendsCombined.getLimitedTextList());
		if (friendsCombined.doesFurtherItemsExist())
			pPlayer.sendMessage(PREFIX + PatterCollection.PAGE_PATTERN.matcher(Main.getInstance().getMessages().getString("Friends.Command.List.NextPage")).replaceFirst("" + (friendsCombined.getPage() + 1)));
	}

	private PageEntriesAsTextContainer getFriendsCombined(OnlinePAFPlayer pCaller, String[] args) {
		StringBuilder friendsCombined = new StringBuilder();
		List<PlayerListElement> playerListElements = PlayerListElement.getFriendsAsPlayerListElement(pCaller, 0);
		if (playerListElements.isEmpty())
			return null;
		if (SORT_ELEMENTS)
			Collections.sort(playerListElements);
		PageAsListContainer<PlayerListElement> playerListElementsContainer = createPage(playerListElements, args, ENTRIES_PER_PAGE);
		if (playerListElementsContainer == null)
			return new PageEntriesAsTextContainer(false, null, 0);
		playerListElements = playerListElementsContainer.getLimitedList();
		for (int i = 0; i < playerListElements.size(); i++) {
			StringBuilder builder = new StringBuilder();
			String additive;
			String color;
			if (!playerListElements.get(i).isOnline()) {
				additive = PatterCollection.LAST_ONLINE_PATTERN.matcher(
						Main.getInstance().getMessages().getString("Friends.Command.List.OfflineTitle")).replaceAll(Matcher.quoteReplacement(
						setLastOnlineColor(DATE_FORMAT.format(playerListElements.get(i).getLastOnline()))));
				color = Main.getInstance().getMessages().getString("Friends.Command.List.OfflineColor");
			} else {
				ServerInfo server = playerListElements.get(i).getServer();
				String serverName = ServerDisplayNameCollection.getInstance().getServerDisplayName(server);
				additive = PatterCollection.SERVER_ON.matcher(Main.getInstance().getMessages().getString("Friends.Command.List.OnlineTitle")).
						replaceAll(Matcher.quoteReplacement(serverName));
				color = Main.getInstance().getMessages().getString("Friends.Command.List.OnlineColor");
			}
			if (i > 0)
				builder.append(Main.getInstance().getMessages().getString("Friends.Command.List.PlayerSplit"));
			builder.append(color);
			builder.append(playerListElements.get(i).getDisplayName());
			builder.append(additive);
			String processed = process(playerListElements.get(i).getPlayer(), builder.toString());
			friendsCombined.append(processed);
		}
		return new PageEntriesAsTextContainer(playerListElementsContainer.doesFurtherItemsExist(), friendsCombined.toString(), playerListElementsContainer.getPage());
	}

	private boolean hasFriends(OnlinePAFPlayer pPlayer, PageEntriesAsTextContainer pFriends) {
		if (pFriends == null) {
			pPlayer.sendMessage((PREFIX
					+ Main.getInstance().getMessages().getString("Friends.Command.List.NoFriendsAdded")));
			return false;
		}
		return true;
	}

	private String setLastOnlineColor(String pLastOnline) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char args : pLastOnline.toCharArray()) {
			stringBuilder.append(LAST_ONLINE_COLOR);
			stringBuilder.append(args);
		}
		return stringBuilder.toString();
	}

	private String process(PAFPlayer pPlayer, String pMessage) {
		String message = pMessage;
		for (TextReplacer replacer : replacerList)
			message = replacer.onProecess(pPlayer, pMessage);
		return message;
	}

	public void registerTextReplacer(TextReplacer pTextReplacer) {
		replacerList.add(pTextReplacer);
	}

	public void unregisterTextReplacer(TextReplacer pTextReplacer) {
		replacerList.remove(pTextReplacer);
	}
}
