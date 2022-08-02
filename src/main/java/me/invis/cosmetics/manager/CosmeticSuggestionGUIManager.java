package me.invis.cosmetics.manager;

import me.invis.cosmetics.Cosmetics;
import me.invis.cosmetics.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class CosmeticSuggestionGUIManager implements Listener {

    private final Inventory inventory;

    private final Map<Player, CosmeticSuggestion> currentSuggestions = new HashMap<>();

    public CosmeticSuggestionGUIManager(Cosmetics plugin) {
        inventory = Bukkit.createInventory(null, 36, "Suggest a new cosmetic");

        inventory.setItem(10, new ItemBuilder(Material.NAME_TAG)
                .setName(ChatColor.GREEN + "Cosmetic name")
                .setLore(ChatColor.GRAY + "Click to set the new cosmetic name.")
                .toItemStack()
        );
        inventory.setItem(12, new ItemBuilder(Material.BOOK)
                .setName(ChatColor.GREEN + "Cosmetic description")
                .setLore(ChatColor.GRAY + "Click to set the new cosmetic description.")
                .toItemStack()
        );
        inventory.setItem(14, new ItemBuilder(Material.PAPER)
                .setName(ChatColor.GREEN + "Cosmetic characteristic")
                .setLore(ChatColor.GRAY + "Click to set the new cosmetic characteristic.")
                .toItemStack()
        );
        inventory.setItem(16, new ItemBuilder(Material.DIAMOND)
                .setName(ChatColor.GREEN + "Cosmetic icon")
                .setLore(ChatColor.GRAY + "Click to set the new cosmetic icon.")
                .toItemStack()
        );


        inventory.setItem(31, new ItemBuilder(Material.BARRIER)
                .setName(ChatColor.RED + "Cancel")
                .setLore(ChatColor.GRAY + "Click to cancel the suggestion.")
                .toItemStack()
        );

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private Inventory getInventory() {
        return inventory;
    }

    public void openFor(Player player) {
        player.openInventory(getInventory());
    }

    private final String
            NAME_METADATA_KEY = "cosmeticSuggestion.name",
            DESCRIPTION_METADATA_KEY = "cosmeticSuggestion.description",
            CHARACTERISTIC_METADATA_KEY = "cosmeticSuggestion.characteristic",
            ICON_METADATA_KEY = "cosmeticSuggestion.icon";

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if(getInventory().getViewers().contains(event.getWhoClicked())) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            CosmeticSuggestion cosmeticSuggestion = currentSuggestions.getOrDefault(player, new CosmeticSuggestion(player));

            if(event.getSlot() == 31) {
                currentSuggestions.remove(player);
                Cosmetics.getGUIManager().openFor((Player) event.getWhoClicked());
                player.removeMetadata(NAME_METADATA_KEY, Cosmetics.getInstance());
                player.removeMetadata(DESCRIPTION_METADATA_KEY, Cosmetics.getInstance());
                player.removeMetadata(CHARACTERISTIC_METADATA_KEY, Cosmetics.getInstance());
                player.removeMetadata(ICON_METADATA_KEY, Cosmetics.getInstance());
                return;
            }

            switch (event.getSlot()) {
                case 10:
                case 12:
                case 14:
                case 16:
                    player.setMetadata(event.getSlot() == 10 ? NAME_METADATA_KEY : event.getSlot() == 12 ? DESCRIPTION_METADATA_KEY : event.getSlot() == 14 ? CHARACTERISTIC_METADATA_KEY : ICON_METADATA_KEY, new FixedMetadataValue(Cosmetics.getInstance(), null));
                    player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Please enter the " + (event.getSlot() == 10 ? "name" : event.getSlot() == 12 ? "description" : event.getSlot() == 14 ? "characteristic" : "icon") + " for the cosmetic");
                    if(!currentSuggestions.containsKey(player)) currentSuggestions.put(player, cosmeticSuggestion);
                    player.closeInventory();
                    break;
                default:
            }
        }
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        if(!currentSuggestions.containsKey(event.getPlayer())) return;
        Player player = event.getPlayer();
        CosmeticSuggestion cosmeticSuggestion = currentSuggestions.get(player);
        if(isNameSetting(player)) {
            event.setCancelled(true);
            cosmeticSuggestion.setName(event.getMessage());
            currentSuggestions.put(player, cosmeticSuggestion);
            player.removeMetadata(NAME_METADATA_KEY, Cosmetics.getInstance());
            player.sendMessage(ChatColor.GREEN + "Cosmetic name set to " + event.getMessage());
            checkSuggestion(player);
            return;
        }
        if(isDescriptionSetting(player)) {
            event.setCancelled(true);
            cosmeticSuggestion.setDescription(event.getMessage());
            currentSuggestions.put(player, cosmeticSuggestion);
            player.removeMetadata(DESCRIPTION_METADATA_KEY, Cosmetics.getInstance());
            player.sendMessage(ChatColor.GREEN + "Cosmetic description set to " + event.getMessage());
            checkSuggestion(player);
            return;
        }
        if(isCharacteristicSetting(player)) {
            event.setCancelled(true);
            cosmeticSuggestion.setCharacteristic(event.getMessage());
            currentSuggestions.put(player, cosmeticSuggestion);
            player.removeMetadata(CHARACTERISTIC_METADATA_KEY, Cosmetics.getInstance());
            player.sendMessage(ChatColor.GREEN + "Cosmetic characteristic set to " + ChatColor.RESET + event.getMessage());
            checkSuggestion(player);
            return;
        }
        if(isIconSetting(player)) {
            event.setCancelled(true);
            cosmeticSuggestion.setIcon(event.getMessage());
            currentSuggestions.put(player, cosmeticSuggestion);
            player.removeMetadata(ICON_METADATA_KEY, Cosmetics.getInstance());
            player.sendMessage(ChatColor.GREEN + "Cosmetic icon set to " + event.getMessage());
            checkSuggestion(player);
        }
    }

    private boolean isNameSetting(Player player) {
        return player.hasMetadata(NAME_METADATA_KEY);
    }
    private boolean isDescriptionSetting(Player player) {
        return player.hasMetadata(DESCRIPTION_METADATA_KEY);
    }
    private boolean isCharacteristicSetting(Player player) {
        return player.hasMetadata(CHARACTERISTIC_METADATA_KEY);
    }
    private boolean isIconSetting(Player player) {
        return player.hasMetadata(ICON_METADATA_KEY);
    }

    private void checkSuggestion(Player player) {
        if(currentSuggestions.containsKey(player) && currentSuggestions.get(player).isReady()) {
            currentSuggestions.get(player).sendSuggestion();
            player.sendMessage(ChatColor.GREEN + "Your suggestion has been sent!");
            currentSuggestions.remove(player);
        }
        else new BukkitRunnable() { @Override public void run() { openFor(player); } }.runTask(Cosmetics.getInstance());
    }
}
