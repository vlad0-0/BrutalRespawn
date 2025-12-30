// Copyright (C) 2025 vlad0-0. License: MIT

package dev.invalid.brutal_respawn.effect;

import dev.invalid.brutal_respawn.BrutalRespawn;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, BrutalRespawn.MODID);

    public static final Holder<MobEffect> BRUTALLY_RESPAWNED = MOB_EFFECTS.register("brutally_respawned",
            () -> new BrutallyRespawnedEffect(MobEffectCategory.BENEFICIAL, Color.RED.getRGB()));
}