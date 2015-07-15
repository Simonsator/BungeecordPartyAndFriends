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

	public void invite(final ProxiedPlayer player) {
		invite.add(player);
		player.sendMessage(new TextComponent(Party.prefix
				+ "§5Du §5wurdest §5in §5die §5Party §5von§6 "
				+ leader.getName() + " §5eingeladen!"));
		String zuschreiben = Party.prefix
				+ "§5Tritt §5der §5Party §5mit §6/Party join "
				+ leader.getName() + " §5bei!";
		String command = "/party join " + leader.getName();
		String jsoncode = "{'text':'"
				+ zuschreiben
				+ "', 'clickEvent':{'action':'run_command','value':'"
				+ command
				+ "'},'hoverEvent':{'action':'show_text','value':'Hier klicken um Party einladung anzunehmen'}}";
		player.unsafe().sendPacket(new Chat(jsoncode));
		BungeeCord.getInstance().getScheduler()
				.schedule(Party.getInstance(), new Runnable() {

					@Override
					public void run() {
						if (invite.contains(player)) {
							invite.remove(player);
							player.sendMessage(new TextComponent(Party.prefix
									+ "§5Die Einladung in die Party von §6"
									+ leader.getName() + " §5ist §5abgelaufen!"));
							leader.sendMessage(new TextComponent(
									Party.prefix
											+ "§5Der Spieler§6 "
											+ player.getName()
											+ " §5hat §5die §5Einladung §5nicht §5angenommen!"));
							PlayerParty party = PartyManager.getParty(leader);
							List<ProxiedPlayer> liste = party.getPlayer();
							if (liste.size() == 0) {
								PartyManager.deleteParty(party);
								leader.sendMessage(new TextComponent(
										Party.prefix
												+ "§5Die §5Party §5wurde §5wegen §5zu §5wenig §5Mitgliedern §5aufgelöst."));
							}
						}
					}
				}, 60, TimeUnit.SECONDS);
	}

	public void setLeader(ProxiedPlayer player) {
		leader = player;
	}

}
