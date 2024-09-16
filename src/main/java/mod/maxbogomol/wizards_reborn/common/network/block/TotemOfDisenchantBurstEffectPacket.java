package mod.maxbogomol.wizards_reborn.common.network.block;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class TotemOfDisenchantBurstEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public TotemOfDisenchantBurstEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static TotemOfDisenchantBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new TotemOfDisenchantBurstEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(TotemOfDisenchantBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f, 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.725F, msg.pos.getZ() + 0.5F, 10);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.725F, msg.pos.getZ() + 0.5F, 5);
                    ParticleBuilder.create(FluffyFurParticles.SQUARE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.725F, msg.pos.getZ() + 0.5F, 5);
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.725F, msg.pos.getZ() + 0.5F, 10);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(level, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.725F, msg.pos.getZ() + 0.5F, 10);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}