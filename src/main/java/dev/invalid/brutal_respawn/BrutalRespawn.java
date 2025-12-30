// Copyright (C) 2025 vlad0-0. License: MIT

package dev.invalid.brutal_respawn;

import com.mojang.logging.LogUtils;
import dev.invalid.brutal_respawn.effect.ModEffects;
import dev.invalid.brutal_respawn.config.ModConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(BrutalRespawn.MODID)
public class BrutalRespawn {

    public static final String MODID = "brutal_respawn";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BrutalRespawn(IEventBus modEventBus) {
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> ModConfigScreen.getScreen(parent)
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModConfigScreen::load);
    }
}
