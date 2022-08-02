package me.invis.cosmetics;

import club.minnced.discord.webhook.WebhookClient;
import me.invis.cosmetics.command.CosmeticsCommand;
import me.invis.cosmetics.manager.CosmeticSuggestionGUIManager;
import me.invis.cosmetics.manager.GUIManager;
import me.invis.cosmetics.util.PlayerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.Inet4Address;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics INSTANCE;
    private static GUIManager guiManager;
    private static WebhookClient client;
    private static CosmeticSuggestionGUIManager cosmeticSuggestionGUIManager;
    public static boolean isInternet;

    @Override
    public void onEnable() {
        try {
            getLogger().info("Checking your internet connection... (timeout: 10 seconds)");
            isInternet = Inet4Address.getByName("google.com").isReachable(10000);
        } catch (IOException e) { isInternet = false; }
        if(!isInternet) getLogger().warning("Your server is NOT connected to the internet!, the cosmetic suggestions feature will NOT work.");
        else getLogger().info("Your server is connected to the internet, the cosmetic suggestions feature will work if the discord API servers are up & running.");

        INSTANCE = this;

        getCommand("cosmetics").setExecutor(new CosmeticsCommand());

        guiManager = new GUIManager(this);
        cosmeticSuggestionGUIManager = new CosmeticSuggestionGUIManager(this);

        if(isInternet) setupWebhook();

        new PlayerUtil(this).startChecking();
    }

    @Override
    public void onDisable() {
        // This is better than iterating over all CosmeticType's & stopping their effects depending on the currentPlayerCosmetic players
        // Since there may be cosmetics that aren't used, so using this method, it won't look for them anyway.
        CosmeticType.getCurrentPlayerCosmetic().forEach((player, type) -> type.stopEffect(player));

        if(isInternet) getWebhookClient().close();
    }

    public static Cosmetics getInstance() {
        return INSTANCE;
    }

    public static GUIManager getGUIManager() {
        return guiManager;
    }
    public static CosmeticSuggestionGUIManager getCosmeticSuggestionGUIManager() {
        return cosmeticSuggestionGUIManager;
    }

    public static WebhookClient getWebhookClient() {
        return client;
    }

    private void setupWebhook() {
        client = WebhookClient.withUrl("YOURWEBHOOKURLHERE");
        client.setErrorHandler((client, message, throwable) -> { if(throwable != null) throwable.printStackTrace(); });
    }
}
