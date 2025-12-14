package pw.cinque.cpsmod.settings;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandSettings extends CommandBase {
    public String getCommandName() {
        return "cpsmod";
    }

    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        new GuiSettings().display();
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
