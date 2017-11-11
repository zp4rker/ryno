package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import me.zp4rker.discord.ryno.Ryno;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.time.temporal.ChronoUnit;

public class Ping {

    @Command(aliases = "ping",
            description = "Calculates ping.",
            usage = "$ping")
    public void onCommand(Message message) {
        long apiPing = message.getJDA().getPing();
        message.getChannel().sendMessage("Ping!").queue(m -> {
            m.editMessage("Pong!").queue(m2 -> {
                long realPing = m2.getCreationTime().until(m2.getEditedTime(), ChronoUnit.MILLIS);
                m2.delete().queue();
                m2.getChannel().sendMessage(new EmbedBuilder()
                        .setAuthor("Ping times", null, m2.getJDA().getSelfUser().getEffectiveAvatarUrl())
                        .setDescription("**API ping:** " + apiPing + "\n**Real ping:** " + realPing)
                        .setColor(Ryno.embedColour).build()).queue();
            });
        });
    }

}
