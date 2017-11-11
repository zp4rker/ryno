package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import net.dv8tion.jda.core.entities.Message;

public class Ping {

    @Command(aliases = "ping",
            description = "Pings the API.",
            usage = "$ping")
    public void onCommand(Message message) {
        long ping = message.getJDA().getPing();

        message.getChannel().sendMessage(ping + "ms.").queue();
    }

}
