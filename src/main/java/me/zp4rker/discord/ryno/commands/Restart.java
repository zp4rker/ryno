package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import me.zp4rker.discord.core.exception.ExceptionHandler;
import net.dv8tion.jda.core.entities.Message;

public class Restart {

    @Command(aliases = "restart")
    public void onCommand(Message message) {
        if (!message.getAuthor().getId().equals("145064570237485056")) return;

        try {
            message.delete().queue(); // Add bypass logs thingy

            Thread.sleep(1500);

            message.getJDA().shutdown();

            Runtime.getRuntime().exec("/home/bots/start-ryno.sh").waitFor();

            System.exit(0);
        } catch (Exception e) {
            ExceptionHandler.handleException("Restart Command", e);
        }
    }

}
