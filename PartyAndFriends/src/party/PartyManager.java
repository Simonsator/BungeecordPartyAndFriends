package party;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {

	
	private static List<PlayerParty> partys = new ArrayList<PlayerParty>();
	
	
	public static PlayerParty getParty(ProxiedPlayer player){
		for(PlayerParty party : partys){
			if(party.isinParty(player)){;
			return party;
			}
		
		}
		return null;
	}
	
	public static boolean createParty(ProxiedPlayer player){
		if(getParty(player) == null){
			partys.add(new PlayerParty(player));
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean deleteParty(ProxiedPlayer player){
		if(getParty(player) != null){
			if(getParty(player).isleader(player)){
				for(ProxiedPlayer p : getParty(player).getPlayer()){
					getParty(player).removePlayer(p);
				}
				partys.remove(getParty(player));
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static List<PlayerParty> getPartys(){
		return partys;
	}
	
	public static void deleteParty(PlayerParty party){
		if(party != null){
			for(int i = 0; i < party.getPlayer().size(); i++){
				if(party.getPlayer().get(i) != null){
					party.getPlayer().remove(i);
				}
			}
			partys.remove(party);
		}
	}
}
