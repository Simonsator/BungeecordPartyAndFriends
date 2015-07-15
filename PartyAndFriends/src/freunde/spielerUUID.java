package freunde;

import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class spielerUUID {
	public UUID getUUID(ProxiedPlayer p){
		return p.getUniqueId();
	}
}
