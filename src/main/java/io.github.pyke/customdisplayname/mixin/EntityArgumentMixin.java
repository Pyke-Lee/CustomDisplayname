package io.github.pyke.customdisplayname.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.pyke.customdisplayname.AliasManager;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(EntityArgument.class)
public class EntityArgumentMixin {
    @Inject(method = "listSuggestions", at = @At("RETURN"), cancellable = true)
    private void onListSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder, CallbackInfoReturnable<CompletableFuture<Suggestions>> cir) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (null == server) { return; }

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            String displayName = AliasManager.getUuidToDisplayName(player.getUUID());
            if (null != displayName) { builder.suggest(AliasManager.stripColor(displayName)); }
        }
        cir.setReturnValue(builder.buildFuture());
    }
}
