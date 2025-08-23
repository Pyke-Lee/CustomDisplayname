package io.github.pyke.customdisplayname.event;

import io.github.pyke.customdisplayname.AliasManager;
import io.github.pyke.customdisplayname.CustomDisplayName;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CustomDisplayName.MOD_ID)
public class PlayerEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerNameFormat(PlayerEvent.NameFormat event) {
        Component customDisplayName = AliasManager.getDisplayName(event.getEntity().getUUID());

        if (null != customDisplayName) { event.setDisplayname(customDisplayName); }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTabListNameFormat(PlayerEvent.TabListNameFormat event) {
        Component customDisplayName = AliasManager.getDisplayName(event.getEntity().getUUID());
        if (null != customDisplayName) { event.setDisplayName(customDisplayName); }
    }
}