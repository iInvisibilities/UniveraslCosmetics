package me.invis.cosmetics.manager;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import me.invis.cosmetics.Cosmetics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class CosmeticSuggestion {

    private String name, description, characteristic, icon;
    private final Player suggester;

    public CosmeticSuggestion(String name, String description, String characteristic, String icon, Player suggestionIssuer) {
        this.name = name;
        this.description = description;
        this.characteristic = characteristic;
        this.icon = icon;
        this.suggester = suggestionIssuer;
    }

    public CosmeticSuggestion(Player suggester) {
        this(null, null, null, null, suggester);
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getCharacteristic() {
        return characteristic;
    }
    public String getIcon() {
        return icon;
    }
    public Player getSuggester() {
        return suggester;
    }

    public boolean isReady() {
        return getName() != null && getDescription() != null && getCharacteristic() != null && getIcon() != null;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void sendSuggestion() {
        if(!Cosmetics.isInternet) return;

        getSuggester().sendMessage(ChatColor.GREEN + "Sending cosmetic suggestion...");

        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
        embedBuilder.setTimestamp(OffsetDateTime.now());
        embedBuilder.setColor(Color.WHITE.getRGB());

        embedBuilder.setFooter(new WebhookEmbed.EmbedFooter("Suggested by " + getSuggester().getName(), "https://cravatar.eu/helmhead/" + getSuggester().getName() + "/190.png"));
        Arrays.asList(
                new WebhookEmbed.EmbedField(true, "Name", getName()),
                new WebhookEmbed.EmbedField(true, "Icon", getIcon()),
                new WebhookEmbed.EmbedField(true, "Description", getDescription()),
                new WebhookEmbed.EmbedField(false, "Characteristic:", getCharacteristic())
        ).forEach(embedBuilder::addField);

        Cosmetics.getWebhookClient().send(WebhookMessage.embeds(embedBuilder.build()));
        Cosmetics.getWebhookClient().send("*This suggestion has been sent from " + Bukkit.getServer().getIp() + "*");

        getSuggester().sendMessage(ChatColor.GREEN + "Suggestion sent!");
    }

}
