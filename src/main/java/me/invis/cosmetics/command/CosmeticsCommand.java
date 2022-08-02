package me.invis.cosmetics.command;

import me.invis.cosmetics.Cosmetics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This command can only by a player.");
            return true;
        }
        Cosmetics.getGUIManager().openFor((Player) sender);
        return true;
    }
}
