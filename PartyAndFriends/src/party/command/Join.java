package party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import party.Party;
import party.PartyManager;
import party.PlayerParty;

public class Join
  extends SubCommand
{
  public Join()
  {
    super("Trete einer Party bei", "<Name>", new String[] { "join" });
  }
  
  public void onCommand(ProxiedPlayer p, String[] args)
  {
    partyBeitreten(p, args);
  }
    public void partyBeitreten(ProxiedPlayer p, String[] args) {
    	if (args.length == 0)
        {
          p.sendMessage(new TextComponent(Party.prefix + "§cDu §cmusst §ceinen §cSpieler §cangeben."));
          return;
        }
        if (PartyManager.getParty(p) != null)
        {
          p.sendMessage(new TextComponent(Party.prefix + "§cDu §cbist §cbereits §cin §ceiner §cParty. §cNutze §6/party leave §cum §cdiese §cParty §czu §cverlassen."));
          return;
        }
        ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
        if (pl == null)
        {
          p.sendMessage(new TextComponent(Party.prefix + "§cDieser §cSpieler §cist §cnicht §conline."));
          return;
        }
        if (PartyManager.getParty(pl) == null)
        {
          p.sendMessage(new TextComponent(Party.prefix + "§cDieser §cSpieler §chat §ckeine §cParty."));
          return;
        }
        PlayerParty party = PartyManager.getParty(pl);
        if (party.addPlayer(p))
        {
          for (ProxiedPlayer pp : party.getPlayer()) {
            pp.sendMessage(new TextComponent(Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bist §bder §bParty §bbeigetreten."));
          }
          party.getleader().sendMessage(new TextComponent(Party.prefix + "§bDer §bSpieler §6" + p.getName() + " §bist §bder §bParty §bbeigetreten."));
        }
        else
        {
          p.sendMessage(new TextComponent(Party.prefix + "§cDu §ckannst §cder §cParty §cnicht §cbeitreten."));
          return;
        }
    }
  }
