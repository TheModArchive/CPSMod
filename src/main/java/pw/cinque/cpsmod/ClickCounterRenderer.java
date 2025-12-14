package pw.cinque.cpsmod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ClickCounterRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type == ElementType.EXPERIENCE && !event.isCancelable()) {
            if (this.mc.currentScreen == null && !this.mc.gameSettings.showDebugInfo) {
                int cps = CPSMod.getClicks();
                String text = cps + " CPS";
                boolean blendEnabled = GL11.glIsEnabled(3042);
                GL11.glDisable(3042);
                this.mc.fontRenderer.drawString(text, CPSMod.cpsCounterPosX + 2, CPSMod.cpsCounterPosY + 2, 16777215);
                if (blendEnabled) {
                    GL11.glEnable(3042);
                }
            }
        }
    }
}
