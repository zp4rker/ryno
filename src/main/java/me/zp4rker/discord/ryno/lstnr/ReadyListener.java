package me.zp4rker.discord.ryno.lstnr;

import me.zp4rker.discord.ryno.Ryno;
import me.zp4rker.discord.ryno.commands.Info;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class ReadyListener {

    @SubscribeEvent
    private void onReady(ReadyEvent event) {
        Ryno.handler.registerCommand(new Info());
    }

}
