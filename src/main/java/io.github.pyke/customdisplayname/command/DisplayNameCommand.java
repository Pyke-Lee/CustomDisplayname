package io.github.pyke.customdisplayname.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.pyke.customdisplayname.AliasManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import io.github.pyke.customdisplayname.CustomDisplayName;

public class DisplayNameCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("이름변경")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("target", EntityArgument.player())
                .then(Commands.argument("displayname", StringArgumentType.greedyString()))
                .executes(ctx -> {
                    ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                    String  displayName = StringArgumentType.getString(ctx, "displayName");

                    boolean success = AliasManager.setDisplayName(target, displayName);
                    if (success) {
                        ctx.getSource().sendSuccess(() -> Component.literal(CustomDisplayName.SYSTEM_PREFIX + target.getGameProfile().getName() + "의 닉네임을'" + displayName + "' 으로 변경했습니다."), true);
                        target.sendSystemMessage(Component.literal(CustomDisplayName.SYSTEM_PREFIX + "당신의 닉네임이 '" + displayName + "' 으로 변경되었습니다."));
                        return 1;
                    }
                    else {
                        ctx.getSource().sendFailure(Component.literal(CustomDisplayName.SYSTEM_PREFIX + "이미 사용 중인 닉네임입니다."));
                        return 0;
                    }
                })
            )
        );
    }
}
