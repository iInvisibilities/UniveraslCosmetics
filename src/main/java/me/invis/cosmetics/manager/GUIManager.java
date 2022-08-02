package me.invis.cosmetics.manager;

import me.invis.cosmetics.CosmeticType;
import me.invis.cosmetics.Cosmetics;
import me.invis.cosmetics.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIManager implements Listener {

    private final Inventory inventory;

    public GUIManager(Cosmetics plugin) {
        inventory = Bukkit.createInventory(null, 54, "Cosmetics");

        for (CosmeticType cosmeticType : CosmeticType.values()) {
            ItemBuilder cosmeticTypeItem = new ItemBuilder(cosmeticType.getIcon());
            cosmeticTypeItem.setName(ChatColor.AQUA + cosmeticType.getName());
            cosmeticTypeItem.setLore(Arrays.stream(cosmeticType.getDescription()).map(s -> ChatColor.GRAY + s).toArray(String[]::new));

            ItemStack cosmeticTypeItemStack = cosmeticTypeItem.toItemStack();
            ItemMeta cosmeticTypeItemMeta = cosmeticTypeItemStack.getItemMeta();
            cosmeticTypeItemMeta.addItemFlags(ItemFlag.values());
            cosmeticTypeItemStack.setItemMeta(cosmeticTypeItemMeta);

            inventory.setItem(cosmeticType.getSlot(), cosmeticTypeItemStack);
        }

        if(Cosmetics.isInternet) {
            inventory.setItem(53, new ItemBuilder(Material.BOOK)
                    .setName(ChatColor.GREEN + "Suggest a cosmetic")
                    .setLore(ChatColor.GRAY + "Click to suggest a cosmetic to be added to the plugin.")
                    .toItemStack()
            );
        }

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                .setName(ChatColor.RED + "Clear current cosmetic")
                .setLore(ChatColor.GRAY + "Click to cancel current running cosmetic. (If exists)")
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

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(getInventory().getViewers().contains(event.getWhoClicked())) {
            event.setCancelled(true);

            if(event.getCurrentItem() != null && event.getSlot() == 53) {
                Cosmetics.getCosmeticSuggestionGUIManager().openFor(player);
                return;
            }
            else if (event.getSlot() == 49) {
                if(CosmeticType.getCurrentPlayerCosmetic().containsKey(player)) {
                    CosmeticType.getCurrentPlayerCosmetic().get(player).stopEffect(player);
                    CosmeticType.getCurrentPlayerCosmetic().remove(player);
                    player.sendMessage(ChatColor.GREEN + "Cleared current cosmetic!");
                    player.closeInventory();
                }
                else player.sendMessage(ChatColor.RED + "No cosmetic to clear.");
                return;
            }

            CosmeticType cosmeticType = CosmeticType.getBySlot(event.getSlot());
            if(cosmeticType == null) return;
            if(!player.hasPermission(cosmeticType.getPermission())) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this cosmetic.");
                return;
            }

            if(CosmeticType.getCurrentPlayerCosmetic().containsKey(player)) CosmeticType.getCurrentPlayerCosmetic().get(player).stopEffect(player);
            cosmeticType.fireEffect(player);

            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "You have fired the cosmetic with the name: " + cosmeticType.getName());
        }
    }
}
