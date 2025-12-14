package pw.cinque.cpsmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import pw.cinque.cpsmod.settings.CommandSettings;

@Mod(name = "CPSMod", modid = "cpsmod", version = "1.0")
public class CPSMod {
    private static List<Long> clicks = new ArrayList<>();
    public static int cpsCounterPosX = 0;
    public static int cpsCounterPosY = 0;
    public static boolean preventDoubleclicks = false;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        loadSettings();
        ClickListener clickListener = new ClickListener();
        MinecraftForge.EVENT_BUS.register(clickListener);
        MinecraftForge.EVENT_BUS.register(new ClickCounterRenderer());
        FMLCommonHandler.instance().bus().register(clickListener);
        ClientCommandHandler.instance.registerCommand(new CommandSettings());
    }

    private static void loadSettings() {
        File settings = new File(Minecraft.getMinecraft().mcDataDir, "cpsmod.settings");
        if (settings.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(settings));
                String[] options = reader.readLine().split(":");
                cpsCounterPosX = Integer.valueOf(options[0]);
                cpsCounterPosY = Integer.valueOf(options[1]);
                preventDoubleclicks = Boolean.valueOf(options[2]);
                reader.close();
            } catch (FileNotFoundException var3) {
                var3.printStackTrace();
            } catch (IOException var4) {
                var4.printStackTrace();
            }

            System.out.println(cpsCounterPosX);
        }
    }

    public static void saveSettings() {
        File settings = new File(Minecraft.getMinecraft().mcDataDir, "cpsmod.settings");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(settings));
            writer.write(cpsCounterPosX + ":" + cpsCounterPosY + ":" + preventDoubleclicks);
            writer.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public static void addClick() {
        clicks.add(System.currentTimeMillis());
    }

    public static int getClicks() {
        Iterator<Long> iterator = clicks.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() < System.currentTimeMillis() - 1000L) {
                iterator.remove();
            }
        }

        return clicks.size();
    }
}
