package de.simonsator.partyandfriends.admin.commands.subcommands;

import de.simonsator.partyandfriends.api.AdminSubCommand;
import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class VersionAdminSubCommand extends AdminSubCommand {
	public VersionAdminSubCommand(String pPrefix) {
		super(new String[]{"version", "info"}, 2, "§5pafadmin version §8- §7Displays the version of Party And Friends used", pPrefix);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(new TextComponent(TextComponent.fromLegacyText("§5Party and Friends §8- §7Version: " +
				Main.getInstance().getDescription().getVersion() + " running on " + BukkitBungeeAdapter.getInstance().getServerSoftware())));
	}
}
