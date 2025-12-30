// Copyright (C) 2025 vlad0-0. License: MIT

package dev.invalid.brutal_respawn;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import java.util.function.Supplier;

public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BrutalRespawn.MODID);

    public static final Supplier<AttachmentType<Integer>> PLAYER_HUNGER =
            ATTACHMENT_TYPES.register("player_hunger", () -> AttachmentType.builder(() -> 0).build());

    public static final Supplier<AttachmentType<Float>> PLAYER_SATURATION =
            ATTACHMENT_TYPES.register("player_saturation", () -> AttachmentType.builder(() -> 0.0F).build());
}