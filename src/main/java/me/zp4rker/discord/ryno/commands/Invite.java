package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import me.zp4rker.discord.ryno.Ryno;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Invite {

    @Command(aliases = "invite",
            description = "Provides an invite link for the bot.",
            usage = "$invite")
    public void onCommand(Message message) {
        String botInvite = "https://discordapp.com/oauth2/authorize?client_id=377404788071071744&scope=bot&permissions=8";
        String serverInvite = "https://discord.gg/7hVHq4V";

        String desc =  "**Bot:** " + botInvite + "\n";
        desc += "**Server:** " + serverInvite + "\n";

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Invite links", null, message.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setColor(Ryno.embedColour)
                .setFooter("Made by ZP4RKER#3333", null)
                .setDescription(desc).build();

        message.getChannel().sendMessage(embed).queue();
    }

}

