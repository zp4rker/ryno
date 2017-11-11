package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import me.zp4rker.discord.ryno.Ryno;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Info {

    @Command(aliases = "info",
            description = "Displays information about the bot.",
            usage = "$info")
    public void onCommand(Message message) {
        String author = "ZP4RKER#3333";
        String description = "An open-source utility bot.";
        String uptime = timeString(Ryno.startTime);
        int commands = Ryno.handler.getCommands().size();
        int servers = message.getJDA().getGuilds().size();
        int users = message.getJDA().getUsers().size();

        String desc = "**Author:** " + author + "\n";
        desc += "**Version:** " + Ryno.VERSION + "\n";
        desc +="**Description:** " + description + "\n";
        desc += "**Commands:** " + commands + "\n";
        desc += "**Servers:** " + servers + "\n";
        desc += "**Users:** " + users + "\n";

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Ryno", null, message.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setFooter("Uptime: " + uptime + " | Click embed title to invite me", null)
                .setColor(Ryno.embedColour)
                .setDescription(desc).build();

        message.getTextChannel().sendMessage(embed).queue();
    }

    private String timeString(Instant instant) {
        String endString = "";

        Instant now = Instant.now();
        long timePast = now.getEpochSecond() - instant.getEpochSecond();

        long days = TimeUnit.SECONDS.toDays(timePast);
        timePast -= TimeUnit.DAYS.toSeconds(days);

        long hours = TimeUnit.SECONDS.toHours(timePast);
        timePast -= TimeUnit.HOURS.toSeconds(hours);

        long minutes = TimeUnit.SECONDS.toMinutes(timePast);

        if (days + hours < 1) {
            timePast -= TimeUnit.MINUTES.toSeconds(minutes);
            long seconds = timePast;
            if (minutes > 0) endString = minutes + (minutes == 1 ? " minute" : " minutes") + " and ";
            endString += seconds + (seconds == 1 ? " second" : " seconds");
        } else {
            endString = days + (days == 1 ? " day, " : " days, ");
            endString += hours + (hours == 1 ? " hour " : " hours ") + "and ";
            endString += minutes + (minutes == 1 ? " minute" : " minutes");
        }

        return endString;
    }

}
