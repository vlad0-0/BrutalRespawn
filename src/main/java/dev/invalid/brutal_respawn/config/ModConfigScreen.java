// Copyright (C) 2026 vlad0-0. License: MIT

package dev.invalid.brutal_respawn.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatSlider;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;

import java.nio.file.Path;

import static dev.invalid.brutal_respawn.BrutalRespawn.MODID;

public class ModConfigScreen {
    public static ConfigClassHandler<ModConfigScreen> HANDLER = ConfigClassHandler.createBuilder(ModConfigScreen.class)
            .id(Identifier.fromNamespaceAndPath(MODID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(Path.of("config", MODID + ".json")).build())
            .build();

    private static final String CATEGORY_GENERAL = "general";
    private static final String GROUP_SETTINGS = "settings";

    // Minimum health a player will have upon respawn (1-20)
    @SerialEntry()
    @AutoGen(category = CATEGORY_GENERAL, group = GROUP_SETTINGS)
    @IntSlider(min = 1, max = 20, step = 1)
    public static int healthOnRespawn = 1;

    // Minimum hunger level a player will have upon respawn (0-20)
    @SerialEntry()
    @AutoGen(category = CATEGORY_GENERAL, group = GROUP_SETTINGS)
    @IntSlider(min = 0, max = 20, step = 1)
    public static int minFoodLevelOnRespawn = 0;

    // Minimum saturation level a player will have upon respawn (0.0-20.0)
    @SerialEntry()
    @AutoGen(category = CATEGORY_GENERAL, group = GROUP_SETTINGS)
    @FloatSlider(min = 0F, max = 20F, step = 1F)
    public static float minSaturationOnRespawn = 0F;

    // Death penalty in hunger or saturation points
    @SerialEntry()
    @AutoGen(category = CATEGORY_GENERAL, group = GROUP_SETTINGS)
    @IntSlider(min = 0, max = 20, step = 1)
    public static int deathHungerPenalty = 3;

    // Duration of the "Brutally Respawned" effect in minutes
    @SerialEntry()
    @AutoGen(category = CATEGORY_GENERAL, group = GROUP_SETTINGS)
    @IntSlider(min = 5, max = 30, step = 1)
    public static int brutallyRespawnedEffectDuration = 10;

    public static Screen getScreen(Screen parentScreen) {
        return HANDLER.generateGui().generateScreen(parentScreen);
    }

    public static void load() {
        HANDLER.load();
    }
}