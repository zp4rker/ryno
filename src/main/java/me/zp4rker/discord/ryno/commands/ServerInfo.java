package me.zp4rker.discord.ryno.commands;

import me.zp4rker.discord.core.command.Command;
import me.zp4rker.discord.ryno.Ryno;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.util.stream.Collectors;

public class ServerInfo {

    @Command(aliases = {"serverinfo", "server", "sinfo"})
    public void onCommand(Message message) {
        Guild guild = message.getGuild();
        User user = guild.getOwner().getUser();

        String name = guild.getName();
        String owner = user.getName() + "#" + user.getDiscriminator();
        String region = guild.getRegionRaw();
        String id = guild.getId();

        int totalMembers = guild.getMembers().size();
        int bots = guild.getMembers().stream().filter(member -> member.getUser().isBot()).collect(Collectors.toList()).size();
        int users = totalMembers - bots;

        int categories = guild.getCategories().size();
        int textChannels = guild.getTextChannels().size();
        int voiceChannels = guild.getVoiceChannels().size();

        int roles = guild.getRoles().size();
        int emotes = guild.getEmotes().size();

        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(name).setThumbnail(guild.getIconUrl()).setColor(Ryno.embedColour)
                .addField("Owner", owner, true).addField("Region", region, true)
                .addField("Members", "- " + users + " user(s)\n- " + bots + " bot(s)", true)
                .addField("Channels", "- " + categories + " categories\n- " + textChannels + " text channel(s)\n- " + voiceChannels + " voice channel(s)", true)
                .addField("Roles", roles + "", true).addField("Emotes", emotes + "", true)
                .setFooter("ID: " + id, null).build();

        message.getChannel().sendMessage(embed).queue();

    }

}
