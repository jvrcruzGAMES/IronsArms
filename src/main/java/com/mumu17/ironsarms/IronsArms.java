package com.mumu17.ironsarms;

import com.mojang.logging.LogUtils;
import com.mumu17.ironsarms.event.IronsArmsBulletEvents;
import com.mumu17.ironsarms.register.ModNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(IronsArms.MODID)
public class IronsArms {

    public static final String MODID = "ironsarms";
    public static final Logger LOGGER = LogUtils.getLogger();

    public IronsArms(IEventBus modEventBus) {
        if (ModList.get().isLoaded("arscurios")
                || ModList.get().isLoaded("armslib")
                || ModList.get().isLoaded("castlib")) {
            throw new IllegalStateException("This mod is incompatible with ArsCurios, ArmsLib and CastLib. Please remove them.");
        }

        modEventBus.addListener(ModNetworking::register);
        NeoForge.EVENT_BUS.register(IronsArmsBulletEvents.class);
    }
}
