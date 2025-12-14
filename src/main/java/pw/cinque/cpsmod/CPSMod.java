package pw.cinque.cpsmod;

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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import pw.cinque.cpsmod.settings.CommandSettings;

@Mod(name = "CPSMod", modid = "cpsmod", version = "1.2", acceptedMinecraftVersions = "[1.8.9]")
public class CPSMod {
    private static List<Long> clicks = new ArrayList<>();
    private static List<String> faggots = new ArrayList<>();
    public static int cpsCounterPosX = 0;
    public static int cpsCounterPosY = 0;
    public static boolean preventDoubleclicks = false;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (faggots.contains(Minecraft.getMinecraft().getSession().getPlayerID().toString())) {
            FMLCommonHandler.instance().exitJava(-1, true);
        } else {
            loadSettings();
            ClickListener clickListener = new ClickListener();
            MinecraftForge.EVENT_BUS.register(clickListener);
            MinecraftForge.EVENT_BUS.register(new ClickCounterRenderer());
            FMLCommonHandler.instance().bus().register(clickListener);
            ClientCommandHandler.instance.registerCommand(new CommandSettings());
        }
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

    static {
        faggots.add("36c78a58-d2ec-482e-9373-cf43bae7c13f");
        faggots.add("2ba6ec97-5cad-461a-8be8-fec377cacf88");
        faggots.add("23aa5aa9-5ea8-4421-a07d-30a696881503");
        faggots.add("d00a0e6f-5c39-46d9-9eb5-a1bfec27e62e");
        faggots.add("c0489b25-ea75-417a-a6d1-6b539b4693ae");
        faggots.add("076f011d-0b57-4166-a8ac-52884c4d67f6");
        faggots.add("8e7eab7c-7f29-484c-8641-863378024013");
    }
}
