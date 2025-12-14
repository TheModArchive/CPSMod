package pw.cinque.cpsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
                Gui.drawRect(
                    CPSMod.cpsCounterPosX,
                    CPSMod.cpsCounterPosY,
                    CPSMod.cpsCounterPosX + this.mc.fontRenderer.getStringWidth(text) + 4,
                    CPSMod.cpsCounterPosY + 12,
                    1140850688
                );
                this.mc.fontRenderer.drawString(text, CPSMod.cpsCounterPosX + 2, CPSMod.cpsCounterPosY + 2, -1);
                if (blendEnabled) {
                    GL11.glEnable(3042);
                }
            }
        }
    }
}
