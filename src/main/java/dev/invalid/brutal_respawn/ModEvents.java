// Copyright (C) 2026 vlad0-0. License: MIT

package dev.invalid.brutal_respawn;

import dev.invalid.brutal_respawn.config.ModConfigScreen;
import dev.invalid.brutal_respawn.effect.ModEffects;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = BrutalRespawn.MODID)
public class ModEvents {
    private static final int TICKS_PER_MINUTE = 1200;

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        BrutalRespawn.LOGGER.info("Brutal Respawn Config on Server Start: Health={}, Food={}, Saturation={}, DeathHungerPenalty={}, EffectDuration={}",
                ModConfigScreen.healthOnRespawn,
                ModConfigScreen.minFoodLevelOnRespawn,
                ModConfigScreen.minSaturationOnRespawn,
                ModConfigScreen.deathHungerPenalty,
                ModConfigScreen.brutallyRespawnedEffectDuration);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            player.setData(ModAttachments.PLAYER_HUNGER, player.getFoodData().getFoodLevel());
            player.setData(ModAttachments.PLAYER_SATURATION, player.getFoodData().getSaturationLevel());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            int oldHunger = event.getOriginal().getData(ModAttachments.PLAYER_HUNGER);
            float oldSaturation = event.getOriginal().getData(ModAttachments.PLAYER_SATURATION);

            event.getEntity().setData(ModAttachments.PLAYER_HUNGER, oldHunger);
            event.getEntity().setData(ModAttachments.PLAYER_SATURATION, oldSaturation);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        player.setHealth(ModConfigScreen.healthOnRespawn);

        int hunger = player.getData(ModAttachments.PLAYER_HUNGER);
        float saturation = player.getData(ModAttachments.PLAYER_SATURATION);
        int penalty = ModConfigScreen.deathHungerPenalty;

        if (saturation >= penalty) {
            saturation -= penalty;
        } else {
            float remainingPenalty = penalty - saturation;
            saturation = 0;
            hunger -= (int) remainingPenalty;
        }

        int finalHunger = Math.max(hunger, ModConfigScreen.minFoodLevelOnRespawn);
        float finalSaturation = Math.max(saturation, ModConfigScreen.minSaturationOnRespawn);

        player.getFoodData().setFoodLevel(finalHunger);
        player.getFoodData().setSaturation(finalSaturation);

        if (finalHunger <= 0) {
            player.addEffect(new MobEffectInstance(
                    ModEffects.BRUTALLY_RESPAWNED,
                    ModConfigScreen.brutallyRespawnedEffectDuration * TICKS_PER_MINUTE,
                    0, false, true
            ));
        }
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            if (event.getSource().is(DamageTypes.STARVE)) {
                if (player.hasEffect(ModEffects.BRUTALLY_RESPAWNED)) {
                    event.setNewDamage(0.0f);
                }
            }
        }
    }
}