package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionColorClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class SnowflakeSpellPacket extends PositionColorClientPacket {

    public SnowflakeSpellPacket(double x, double y, double z, float r, float g, float b, float a) {
        super(x, y, z, r, g, b, a);
    }

    public SnowflakeSpellPacket(Vec3 vec, Color color) {
        super(vec, color);
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.125f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.075f)
                .repeat(level, x, y, z, 5);
        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(r, g, b).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.125f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.25f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                .setLifetime(20)
                .randomVelocity(0.075f)
                .repeat(level, x, y, z, 5);
        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < 0.8f) {
                level.addParticle(ParticleTypes.SNOWFLAKE,
                        x + ((random.nextDouble() - 0.5D) / 2), y + ((random.nextDouble() - 0.5D) / 2), z + ((random.nextDouble() - 0.5D) / 2),
                        ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10) + 0.05f, ((random.nextDouble() - 0.5D) / 10));
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SnowflakeSpellPacket.class, SnowflakeSpellPacket::encode, SnowflakeSpellPacket::decode, SnowflakeSpellPacket::handle);
    }

    public static SnowflakeSpellPacket decode(FriendlyByteBuf buf) {
        return decode(SnowflakeSpellPacket::new, buf);
    }
}