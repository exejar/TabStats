package club.maxstats.tabstats.listener;

import club.maxstats.tabstats.config.ModConfig;
import club.maxstats.tabstats.util.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ApiKeyListener {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();
        if (message.startsWith("Your new API key is ")) {
            String apiKey = message.replace("Your new API key is ", "");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatColor.GREEN + "TabStats found and set API Key!"));
            ModConfig.getInstance().setApiKey(apiKey);
            ModConfig.getInstance().save();
        }
    }
}
