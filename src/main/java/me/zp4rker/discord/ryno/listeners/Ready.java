package me.zp4rker.discord.ryno.listeners;

import me.zp4rker.discord.core.command.handler.CommandHandler;
import me.zp4rker.discord.core.logger.ZLogger;
import me.zp4rker.discord.ryno.Ryno;
import me.zp4rker.discord.ryno.commands.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.time.Instant;

public class Ready {

    @SubscribeEvent
    private void onReady(ReadyEvent event) {
        // Initialise ZLogger
        ZLogger.initialise();

        // Ready log
        ZLogger.info("Ryno " +  Ryno.VERSION + " started successfully!");

        // Start time
        Ryno.startTime = Instant.now();

        // Game status
        event.getJDA().getPresence().setGame(Game.of("In Progress | " + event.getJDA().getGuilds().size() + " servers."));

        // Register commands
        registerCommands(Ryno.handler);
    }

    private void registerCommands(CommandHandler handler) {
        handler.registerCommand(new Restart());

        handler.registerCommand(new Ping());
        handler.registerCommand(new Info());
        handler.registerCommand(new Invite());
        handler.registerCommand(new ServerInfo());
    }

}
