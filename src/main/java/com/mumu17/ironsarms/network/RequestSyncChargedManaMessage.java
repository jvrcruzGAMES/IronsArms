package com.mumu17.ironsarms.network;

import com.mumu17.ironsarms.IronsArms;
import com.mumu17.ironsarms.utils.GunTags;
import com.tacz.guns.api.item.IGun;
import io.netty.buffer.ByteBuf;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestSyncChargedManaMessage(int manaCount) implements CustomPacketPayload {
    public static final String MANA = IronsArms.MODID + ":Mana";
    public static final int MAX_MANA = 10000;
    public static final Type<RequestSyncChargedManaMessage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IronsArms.MODID, "request_sync_charged_mana"));
    public static final StreamCodec<ByteBuf, RequestSyncChargedManaMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            RequestSyncChargedManaMessage::manaCount,
            RequestSyncChargedManaMessage::new
    );

    public static void handle(RequestSyncChargedManaMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player != null) {
                for (ItemStack stack : player.getInventory().items) {
                    if (!stack.isEmpty() && stack.getItem() instanceof IGun) {
                        int chargedManaCount = GunTags.getMana(stack);
                        int removeManaCount = msg.manaCount - chargedManaCount;
                        if (removeManaCount > 0.0) {
                            MagicData.getPlayerMagicData(player).addMana(-removeManaCount);
                        }
                        GunTags.addMana(stack, removeManaCount);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
