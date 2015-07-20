package party;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

public class PlayerParty {

	private ProxiedPlayer leader;

	private List<ProxiedPlayer> players;

	private List<ProxiedPlayer> invite;

	public PlayerParty(ProxiedPlayer leader) {
		this.leader = leader;
		this.players = new ArrayList<ProxiedPlayer>();
		this.invite = new ArrayList<ProxiedPlayer>();
	}

	public boolean isleader(ProxiedPlayer player) {
		return this.leader == player;
	}

	public List<ProxiedPlayer> getPlayer() {
		return this.players;
	}

	public void setPlayer(List<ProxiedPlayer> liste) {
		players = liste;
	}

	public ProxiedPlayer getleader() {
		return this.leader;
	}

	public boolean isinParty(ProxiedPlayer player) {
		if (isleader(player)) {
			return true;
		} else if (players.contains(player)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addPlayer(ProxiedPlayer player) {
		if (!players.contains(player) && invite.contains(player)) {
			players.add(player);
			invite.remove(player);
			return true;
		} else {
			return false;
		}
	}

	public boolean removePlayer(ProxiedPlayer player) {
		if (players.contains(player)) {
			players.remove(player);
			return true;
		} else {
			return false;
		}
	}

	public ServerInfo getServerInfo() {
		return this.leader.getServer().getInfo();
	}

	public void invite(final ProxiedPlayer player, final String language) {
		invite.add(player);
		String zuschreiben = "";
		String value = "";
		if (language.equalsIgnoreCase("english")) {
			player.sendMessage(new TextComponent(
					Party.prefix + "§5You §5were §5invited §5to §5the §5party §5of §6" + leader.getName() + "§5!"));
			zuschreiben = Party.prefix + "§5Join §5the §5party §5by §5using §5the §5command §6/Party §6join §6"
					+ leader.getName() + "!";
			value = "Click here to join the party";
		} else {
			player.sendMessage(new TextComponent(
					Party.prefix + "§5Du §5wurdest §5in §5die §5Party §5von§6 " + leader.getName() + " §5eingeladen!"));
			zuschreiben = Party.prefix + "§5Tritt §5der §5Party §5mit §6/Party join " + leader.getName() + " §5bei!";
			value = "Hier klicken um Party einladung anzunehmen";
		}
		String command = "/party join " + leader.getName();
		String jsoncode = "{'text':'" + zuschreiben + "', 'clickEvent':{'action':'run_command','value':'" + command
				+ "'},'hoverEvent':{'action':'show_text','value':'" + value + "'}}";
		player.unsafe().sendPacket(new Chat(jsoncode));
		BungeeCord.getInstance().getScheduler().schedule(Party.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (invite.contains(player)) {
					invite.remove(player);
					if (language.equalsIgnoreCase("english")) {
						player.sendMessage(new TextComponent(Party.prefix + "§5The invitation of the Party from §6"
								+ leader.getName() + " §5is §5timed §5out!"));
						leader.sendMessage(new TextComponent(Party.prefix + "§5The player§6 " + player.getName()
								+ " §5has §5not §5accepted §5your §5invitation!"));
					} else {
						player.sendMessage(new TextComponent(Party.prefix + "§5Die Einladung in die Party von §6"
								+ leader.getName() + " §5ist §5abgelaufen!"));
						leader.sendMessage(new TextComponent(Party.prefix + "§5Der Spieler§6 " + player.getName()
								+ " §5hat §5die §5Einladung §5nicht §5angenommen!"));
					}
					PlayerParty party = PartyManager.getParty(leader);
					List<ProxiedPlayer> liste = party.getPlayer();
					if (liste.size() == 0) {
						if (language.equalsIgnoreCase("english")) {
							leader.sendMessage(new TextComponent(Party.prefix
									+ "§5The §5party §5was §5dissolved §5because §5of §5to §5less §5players."));
						} else {
							leader.sendMessage(new TextComponent(Party.prefix
									+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
						}
						PartyManager.deleteParty(party);
					}
				}
			}
		}, 60, TimeUnit.SECONDS);
	}

	public int inviteListSize() {
		return invite.size();
	}

	public boolean containsPlayer(ProxiedPlayer player) {
		return invite.contains(player);
	}

	public void setLeader(ProxiedPlayer player) {
		leader = player;
	}

}
