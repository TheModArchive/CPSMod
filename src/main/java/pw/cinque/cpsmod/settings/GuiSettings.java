package pw.cinque.cpsmod.settings;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import pw.cinque.cpsmod.CPSMod;

public class GuiSettings extends GuiScreen {
    private int cps = 0;
    private boolean isDragging = false;
    private int lastX = 0;
    private int lastY = 0;

    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2 - 22, 150, 20, "Reset Position"));
        this.buttonList
            .add(new GuiButton(1, this.width / 2 - 75, this.height / 2 + 2, 150, 20, "Prevent Doubleclicks: " + CPSMod.preventDoubleclicks));
    }

    public void display() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        FMLCommonHandler.instance().bus().unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void drawScreen(int x, int y, float partialTicks) {
        super.drawDefaultBackground();
        String text = this.cps + " CPS";
        Gui.drawRect(
            CPSMod.cpsCounterPosX,
            CPSMod.cpsCounterPosY,
            CPSMod.cpsCounterPosX + this.mc.fontRenderer.getStringWidth(text) + 4,
            CPSMod.cpsCounterPosY + 12,
            1140850688
        );
        this.mc.fontRenderer.drawString(text, CPSMod.cpsCounterPosX + 2, CPSMod.cpsCounterPosY + 2, -1);
        super.drawScreen(x, y, partialTicks);
    }

    public void updateScreen() {
        this.cps = CPSMod.getClicks();
    }

    protected void keyTyped(char c, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    protected void mouseClicked(int x, int y, int time) {
        int minX = CPSMod.cpsCounterPosX;
        int minY = CPSMod.cpsCounterPosY;
        int maxX = CPSMod.cpsCounterPosX + this.fontRendererObj.getStringWidth(this.cps + " CPS") + 4;
        int maxY = CPSMod.cpsCounterPosY + 12;
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            this.isDragging = true;
            this.lastX = x;
            this.lastY = y;
        }

        super.mouseClicked(x, y, time);
    }

    protected void mouseMovedOrUp(int x, int y, int which) {
        if (which == 0 && this.isDragging) {
            this.isDragging = false;
        }

        super.mouseMovedOrUp(x, y, which);
    }

    protected void mouseClickMove(int x, int y, int lastButtonClicked, long timeSinceClick) {
        if (this.isDragging) {
            CPSMod.cpsCounterPosX = CPSMod.cpsCounterPosX + (x - this.lastX);
            CPSMod.cpsCounterPosY = CPSMod.cpsCounterPosY + (y - this.lastY);
            this.lastX = x;
            this.lastY = y;
        }

        super.mouseClickMove(x, y, lastButtonClicked, timeSinceClick);
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                CPSMod.cpsCounterPosX = 0;
                CPSMod.cpsCounterPosY = 0;
                return;
            case 1:
                CPSMod.preventDoubleclicks = !CPSMod.preventDoubleclicks;
                ((GuiButton)this.buttonList.get(1)).displayString = "Prevent Doubleclicks: " + CPSMod.preventDoubleclicks;
                return;
        }
    }

    public void onGuiClosed() {
        CPSMod.saveSettings();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
