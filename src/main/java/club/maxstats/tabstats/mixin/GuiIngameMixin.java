package club.maxstats.tabstats.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {
    // finding the method that renders the bossbar.
    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    public void cancelBossBar(CallbackInfo ci) {
        // checking to see if the player is holding down the key that renders tab.
        if (Minecraft.getMinecraft().gameSettings.keyBindPlayerList.isKeyDown()) {
            // if the player is holding down their tab renderer key, the bossbar renderer is cancelled.
                ci.cancel();
        }
    }
}
