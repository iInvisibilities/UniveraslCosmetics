package me.invis.cosmetics;

import me.invis.cosmetics.util.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public enum CosmeticType {

    HEARTS(
            Material.HEART_OF_THE_SEA,
            "Hearts aura",
            "cosmetics.hearts",
            0,
            (player) -> player.spawnParticle(Particle.HEART, player.getLocation(), 10, 0.4, player.isOnGround() ? 1 : 0, 0.4, 2),
            "This is the way you show your love to the world.",
            "Suggested by: Invisibilities"
    ),
    FLAME_POWER(
            Material.FIRE_CHARGE,
            "Flame power",
            "cosmetics.flame_power",
            1,
            (player) -> {
                final boolean x = true;
                final boolean o = false;
                if(!PlayerUtil.isMoving(player)) {
                    player.spawnParticle(Particle.FLAME, player.getLocation().clone().setDirection(new Vector(0, 2, 0)), 10, 0.4, 0, 0.4, 0.1);
                    new Shape(player, new boolean[][]
                            {
                                    {x, o, o, o, x, o, o, o, o, o, o, o, o, o, o, o},
                                    {x, x, o, x, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {x, o, o, o, x, o, o, o, o, o, o, o, o, o, o, o},
                                    {x, x, o, x, x, o, o, o, o, o, o, o, o, o, o, o},
                                    {x, o, x, o, x, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, x, x, x, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {x, o, x, o, x, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, x, x, x, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o}
                            }).drawParticles(player.getLocation(), Particle.FLAME, null);
                }
                if(!player.isOnGround() || player.isSwimming()) player.spawnParticle(Particle.VILLAGER_ANGRY, player.getLocation().clone().setDirection(new Vector(0, 1, 0)), 10, 0.4, 0, 0.4, 0.1);
                if(player.isSprinting()) {
                    new Shape(player, new boolean[][]
                            {
                                    {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                    {o, o, o, o, o, o, x, x, x, o, o, o, o, o, o, o},
                                    {o, o, o, o, o, o, x, o, x, o, o, o, o, o, o, o},
                                    {o, o, o, o, x, x, x, o, x, x, x, o, o, o, o, o},
                                    {o, o, o, o, x, o, o, o, o, o, x, o, o, o, o, o},
                                    {o, o, o, o, x, o, o, o, o, o, x, o, o, o, o, o},
                                    {o, o, o, o, x, o, o, o, o, o, x, o, o, o, o, o},
                                    {o, o, o, o, x, x, o, o, o, x, x, o, o, o, o, o},
                                    {o, o, o, o, o, x, o, o, o, x, o, o, o, o, o, o},
                                    {o, o, o, o, o, x, o, o, o, x, o, o, o, o, o, o},
                                    {o, o, o, o, o, x, o, o, o, x, o, o, o, o, o, o},
                                    {o, o, o, o, o, x, x, x, x, x, o, o, o, o, o, o}
                            }).drawParticles(player.getLocation(), Particle.TOWN_AURA, null);
                }
            },
            "This is where you can show the power of the flames within you.",
            "Suggested by: Invisibilities"
    ),
    PEACE(
            Material.END_STONE,
            "Peace",
            "cosmetics.peace",
            2,
            (player) -> {
                if(!PlayerUtil.isMoving(player)) ParticleUtil.playCrown(player, Particle.CRIT);
                player.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation().clone().setDirection(new Vector(0, 1, 0)), 10, 0.4, 0, 0.4, 0.1);
            },
            "This is how you show the peace within you.",
            "Suggested by: Invisibilities"
    ),
    WAR(
            Material.IRON_SWORD,
            "War",
            "cosmetics.war",
            3,
            (player) -> {
                int random = NumberUtils.random(2, 20);
                player.setHealth(random);
                player.setHealthScale(random);
                player.setFoodLevel(random);
                player.setGlowing(true);

                if(random == 10 || random == 5 || random == 20 || random == 15) {
                    player.spawnParticle(Particle.CLOUD, player.getLocation().clone().setDirection(new Vector(0, -1, 0)), 10, 0.4, 1, 0.4, 0.1);
                }
            },
            "This is how you can express your war drive.",
            "Suggested by: Invisibilities"
    ),
    IM_GAY(
            Material.MUSIC_DISC_11,
            "I'm gay.",
            "cosmetics.gay",
            4,
            (player) -> {
                int r = NumberUtils.random(0, 255), g = NumberUtils.random(0, 255), b = NumberUtils.random(0, 255);
                Color color = Color.fromRGB(r, g, b);

                player.getInventory().setArmorContents(new ItemStack[]{
                        new ItemStack(Material.LEATHER_BOOTS),
                        new ItemStack(Material.LEATHER_LEGGINGS),
                        new ItemStack(Material.LEATHER_CHESTPLATE),
                        new ItemStack(Material.LEATHER_HELMET)
                });

                ItemStack[] newArmorContent = new ItemStack[4];
                for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
                    ItemStack armorContent = player.getInventory().getArmorContents()[i];
                    LeatherArmorMeta helmetMeta = (LeatherArmorMeta) armorContent.getItemMeta();
                    helmetMeta.setColor(color);
                    armorContent.setItemMeta(helmetMeta);
                    newArmorContent[i] = armorContent;
                }
                player.getInventory().setArmorContents(newArmorContent);
                final boolean x = false;
                final boolean o = true;
                new Shape(player, new boolean[][]
                        {
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, o, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                                {x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x},
                        }).drawParticles(player.getLocation(), Particle.REDSTONE, new Particle.DustOptions(color, 10));
            },
            "This is how you say 'i am gay'",
            "Suggested by: Invisibilities"
    ),
    SPEED(
            Material.FEATHER,
            "Speed",
            "cosmetics.speed",
            5,
            (player) -> {
                if(player.isSprinting()) {
                    TextComponent component = new TextComponent(ArrayUtil.random(ChatColor.values()) + "SPEED EVEN MORE!");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
                    player.spawnParticle(Particle.FALLING_DUST, player.getLocation(), 10, 0.4, player.isSwimming() ? 0 : 1, 0.4, 1, Material.SAND.createBlockData());

                }
            },
            "This is how you can prove that you are fast.",
            "Suggested by: Invisibilities"
    ),
    ANGEL(
            Material.CHORUS_FRUIT,
            "Angel",
            "cosmetics.angel",
            6,
            (Function<Player, Void>) player -> {
                ParticleUtil.createSpiral(player, Particle.FIREWORKS_SPARK, Particle.DRAGON_BREATH, player.getLocation().distance(BlockUtil.getFirstBlockUnderLocation(player.getLocation()).getLocation()), 0, .5, null);
                ParticleUtil.createSpiral(player, Particle.FIREWORKS_SPARK, Particle.CLOUD, player.getLocation().distance(BlockUtil.getFirstBlockUnderLocation(player.getLocation()).getLocation()), 0, -.5, null);

                return null;
            },
            (player) -> {
                player.getInventory().setArmorContents(new ItemStack[]{
                        new ItemBuilder(Material.IRON_BOOTS).setName(ChatColor.WHITE + ChatColor.BOLD.toString() + "protector-gear").toItemStack(),
                        new ItemBuilder(Material.IRON_LEGGINGS).setName(ChatColor.WHITE + ChatColor.BOLD.toString() + "protector-gear").toItemStack(),
                        new ItemBuilder(Material.IRON_CHESTPLATE).setName(ChatColor.WHITE + ChatColor.BOLD.toString() + "protector-gear").toItemStack(),
                        new ItemBuilder(Material.WHITE_WOOL).setName(ChatColor.WHITE + ChatColor.BOLD.toString() + "Holy face").toItemStack()
                });

                final boolean x = true;
                final boolean o = false;
                if(player.isFlying()) new Shape(player, new boolean[][]
                        {
                                {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
                                {o, o, o, x, o, o, o, o, o, o, o, o, x, o, o, o},
                                {o, o, x, x, o, o, o, o, o, o, o, o, x, x, o, o},
                                {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
                                {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
                                {o, o, x, x, x, x, o, o, o, o, x, x, x, x, o, o},
                                {o, o, o, x, x, x, x, o, o, x, x, x, x, o, o, o},
                                {o, o, o, o, x, x, x, x, x, x, x, x, o, o, o, o},
                                {o, o, o, o, o, x, x, x, x, x, x, o, o, o, o, o},
                                {o, o, o, o, o, o, x, x, x, x, o, o, o, o, o, o},
                                {o, o, o, o, o, x, x, o, o, x, x, o, o, o, o, o},
                                {o, o, o, o, x, x, x, o, o, x, x, x, o, o, o, o},
                                {o, o, o, o, x, x, o, o, o, o, x, x, o, o, o, o},
                                {o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o},
                                {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}
                        }
                ).drawParticles(player.getLocation(), Particle.CRIT, null);
                CosmeticType type = CosmeticType.valueOf("ANGEL");
                if(CosmeticType.getMetaData(type) == "Setting...") return;
                Function<Player, Void> function = (Function<Player, Void>) CosmeticType.getMetaData(type);
                if(function != null && !player.isOnGround() && !player.isFlying()) {
                    function.apply(player);
                    type.meta = null;
                }

                if(type.meta == null) {
                    type.meta = "Setting...";
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            type.meta = function;
                        }
                    }.runTaskLater(Cosmetics.getInstance(), 20);
                }

            },
            "Be an angel!",
            "Suggested by: Invisibilities"
    );


    private final Material icon;
    private final String name;
    private final String[] description;
    private final String permission;
    private final int slot;
    private final Consumer<Player> effect;
    private Object meta;

    private static final Map<Player, CosmeticType> currentPlayerCosmetic = new HashMap<>();

    CosmeticType(Material icon, String name, String permission, int slot, Consumer<Player> effect, String... description) {
        this(icon, name, permission, slot, null, effect, description);
    }

    CosmeticType(Material icon, String name, String permission, int slot, Object meta, Consumer<Player> effect, String... description) {
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.slot = slot;
        this.meta = meta;
        this.effect = effect;
    }

    public Material getIcon() {
        return icon;
    }
    public String getName() {
        return name;
    }
    public String[] getDescription() {
        return description;
    }
    public String getPermission() {
        return permission;
    }
    public int getSlot() {
        return slot;
    }
    public void playEffect(Player player) {
        effect.accept(player);
    }

    public static Object getMetaData(CosmeticType type) {
        return type.meta;
    }

    private BukkitTask effectTask;

    public void fireEffect(Player player) {
        effectTask = new BukkitRunnable() {
            @Override
            public void run() { playEffect(player); }
        }.runTaskTimer(Cosmetics.getInstance(), 0, 1);

        getCurrentPlayerCosmetic().put(player, this);
    }

    public void stopEffect(Player player) {
        effectTask.cancel();
        if(this == ANGEL || this == IM_GAY) {
            player.getInventory().setArmorContents(new ItemStack[] {null, null, null, null});
        }
        if(this == WAR) {
            player.setHealth(20);
            player.setHealthScale(20);
            player.setFoodLevel(20);
            player.setGlowing(false);
        }
        getCurrentPlayerCosmetic().remove(player);
    }

    public static CosmeticType getBySlot(int slot) {
        return Arrays.stream(values()).filter(cosmeticType -> cosmeticType.getSlot() == slot).findFirst().orElse(null);
    }

    public static Map<Player, CosmeticType> getCurrentPlayerCosmetic() {
        return currentPlayerCosmetic;
    }
}
