package de.simonsator.partyandfriends.utilities;

import java.util.regex.Pattern;

public final class PatterCollection {
	public static final Pattern PLAYER_PATTERN = Pattern.compile("[PLAYER]", Pattern.LITERAL);
	public static final Pattern FRIEND_REQUEST_PATTERN = Pattern.compile("[FRIENDREQUESTS]", Pattern.LITERAL);
	public static final Pattern FRIEND_REQUEST_COUNT_PATTERN = Pattern.compile("[FRIENDREQUESTS_COUNT]", Pattern.LITERAL);
	public static final Pattern SERVER_ON = Pattern.compile("[SERVER_ON]", Pattern.LITERAL);
	public static final Pattern SENDER_NAME_PATTERN = Pattern.compile("[SENDERNAME]", Pattern.LITERAL);
	public static final Pattern MESSAGE_CONTENT_PATTERN = Pattern.compile("[MESSAGE_CONTENT]", Pattern.LITERAL);
	public static final Pattern LEADER_PATTERN = Pattern.compile("[LEADER]", Pattern.LITERAL);
	public static final Pattern MAX_PLAYERS_IN_PARTY_PATTERN = Pattern.compile("[MAXPLAYERSINPARTY]", Pattern.LITERAL);
	public static final Pattern NEW_LEADER_PATTERN = Pattern.compile("[NEWLEADER]", Pattern.LITERAL);
	public static final Pattern SPACE_PATTERN = Pattern.compile(" ", Pattern.LITERAL);
	public static final Pattern CONTENT_PATTERN = Pattern.compile("[CONTENT]", Pattern.LITERAL);
	public static final Pattern LAST_ONLINE_PATTERN = Pattern.compile("[LAST_ONLINE]", Pattern.LITERAL);
	public static final Pattern PAGE_PATTERN = Pattern.compile("[PAGE]", Pattern.LITERAL);

}
