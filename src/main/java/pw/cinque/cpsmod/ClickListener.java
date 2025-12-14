package pw.cinque.cpsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.client.event.MouseEvent;

public class ClickListener {
    private boolean hasClickedThisTick = false;

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (event.button == 0) {
            if (CPSMod.preventDoubleclicks && event.buttonstate && this.hasClickedThisTick) {
                event.setCanceled(true);
            } else {
                if (event.buttonstate) {
                    this.hasClickedThisTick = true;
                    CPSMod.addClick();
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        this.hasClickedThisTick = false;
    }
}
