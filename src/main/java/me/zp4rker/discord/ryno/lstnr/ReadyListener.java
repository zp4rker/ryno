package me.zp4rker.discord.ryno.lstnr;

import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.ryno.Ryno;
import me.zp4rker.discord.ryno.commands.Info;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.time.Instant;

public class ReadyListener {

    @SubscribeEvent
    private void onReady(ReadyEvent event) {
        // Initialise ZLogger
        ZLogger.initialise();

        // Ready log
        ZLogger.info("Ryno " +  Ryno.VERSION + " started successfully!");

        // Start time
        Ryno.startTime = Instant.now();

        // Game status
        event.getJDA().getPresence().setGame(Game.of("$<command> | " + event.getJDA().getGuilds().size() + " servers."));

        // Register commands
        Ryno.handler.registerCommand(new Info());
    }

}
