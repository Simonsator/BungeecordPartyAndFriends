package party.command;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Info
  extends SubCommand
{
  public Info()
  {
    super("Liste alle Spieler in deiner Party", "", new String[] { "list" });
  }
  
  public void onCommand(ProxiedPlayer p, String[] args)
  {
    if (PartyManager.getParty(p) == null)
    {
      p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cin §ckeiner §cParty."));
      return;
    }
    PlayerParty party = PartyManager.getParty(p);
    
    String leader = "§3Leader§7: §5" + party.getleader().getName();
    String players = "§8Players§7: §b";
    if (!party.getPlayer().isEmpty())
    {
      for (ProxiedPlayer pp : party.getPlayer()) {
        players = players + pp.getName() + "§7, §b";
      }
      players = players.substring(0, players.lastIndexOf("§7, §b"));
    }
    else
    {
      players = players + "Leer";
    }
    p.sendMessage(new TextComponent("§8§m----------[§5§lPARTY§8]§m----------"));
    
    p.sendMessage(new TextComponent(Party.prefix + leader));
    p.sendMessage(new TextComponent(Party.prefix + players));
    
    p.sendMessage(new TextComponent("§8§m-----------------------------------"));
  }
}
