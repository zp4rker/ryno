package co.zpdev.bots.ryno.cmd;

import co.zpdev.core.discord.command.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author ZP4RKER
 */
public class Info {

    @Command(aliases = "info", autodelete = true, description = "Displays information about the bot.")
    public void onCommand(Message message) {
        long used = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        message.getChannel().sendMessage(used + "MB/" + max + "MB").queue();
    }

}
