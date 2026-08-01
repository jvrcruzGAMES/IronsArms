package com.mumu17.ironsarms.register;

import com.mumu17.ironsarms.IronsArms;
import com.mumu17.ironsarms.network.RequestSyncChargedManaMessage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetworking {
    public static final String PROTOCOL_VERSION = "1.0";

    public static void register(final RegisterPayloadHandlersEvent event) {
        event.registrar(PROTOCOL_VERSION)
                .playToServer(
                        RequestSyncChargedManaMessage.TYPE,
                        RequestSyncChargedManaMessage.STREAM_CODEC,
                        RequestSyncChargedManaMessage::handle
                );
    }
}
