package io.github.pyke.customdisplayname;

import io.github.pyke.customdisplayname.command.DisplayNameCommand;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("customdisplayname")
public class CustomDisplayName {
    public static final String MOD_ID = "customdisplayname";
    public static final String SYSTEM_PREFIX = "&6[SYSTEM] &r";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public CustomDisplayName() {
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStopping);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        DisplayNameCommand.register(event.getDispatcher());
        LOGGER.info("Registering Commands");
    }

    private void onServerStarted(ServerStartedEvent event) {
        AliasManager.initSaveFile(event.getServer().getWorldPath(LevelResource.ROOT).toFile());
        LOGGER.info("Server Started");
    }

    private void onServerStopping(ServerStoppingEvent event) {
        AliasManager.save();
        LOGGER.info("Server Stopping");
    }
}
