package de.simonsator.partyandfriends.utilities;

import java.util.regex.Pattern;

public final class CompilePatter {
	public static final Pattern PLAYERPATTERN = Pattern.compile("[PLAYER]", Pattern.LITERAL);
	public static final Pattern FRIENDREQUESTPATTERN = Pattern.compile("[FRIENDREQUESTS]", Pattern.LITERAL);
	public static final Pattern SENDERNAMEPATTERN = Pattern.compile("[SENDERNAME]", Pattern.LITERAL);
	public static final Pattern MESSAGE_CONTENTPATTERN = Pattern.compile("[MESSAGE_CONTENT]", Pattern.LITERAL);
	public static final Pattern LEADERPATTERN = Pattern.compile("[LEADER]", Pattern.LITERAL);
	public static final Pattern MAXPLAYERSINPARTYPATTERN = Pattern.compile("[MAXPLAYERSINPARTY]", Pattern.LITERAL);
	public static final Pattern NEWLEADERPATTERN = Pattern.compile("[NEWLEADER]", Pattern.LITERAL);
	public static final Pattern SPACEPATTERN = Pattern.compile(" ", Pattern.LITERAL);
	public static final Pattern CONTENTPATTERN = Pattern.compile("[CONTENT]", Pattern.LITERAL);

}
