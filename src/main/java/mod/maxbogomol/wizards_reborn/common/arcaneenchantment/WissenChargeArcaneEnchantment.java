package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.capability.IArrowModifier;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenChargeBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.ChargeSpellProjectileRayEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WissenChargeArcaneEnchantment extends ArcaneEnchantment {
    public static Random random = new Random();

    public WissenChargeArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(87, 127, 184);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.BOW);
        }
        return false;
    }

    public static void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide()) {
            if (livingEntity instanceof Player player) {
                if (BowItem.getPowerForTime(stack.getUseDuration() - remainingUseDuration) >= 1f) {
                    if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                        int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT);

                        if (enchantmentLevel > 0) {
                            Color color = WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT.getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            int time = stack.getUseDuration() - remainingUseDuration;
                            if (time > 100) {
                                time = 100;
                            }
                            if (time > 50 && enchantmentLevel == 1) {
                                time = 50;
                            }

                            float charge = (time / 100f);
                            Vec3 pos = player.getLookAngle().scale(40).scale(1.0 / 25).add(player.getEyePosition(0));

                            float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                            List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                            int wissen = WissenUtils.getWissenInItems(items);
                            int cost = (int) ((30 + time) * (1 - costModifier));
                            if (cost <= 0) {
                                cost = 1;
                            }

                            if (WissenUtils.canRemoveWissen(wissen, cost)) {
                                if (random.nextFloat() < 0.45f) {
                                    Particles.create(WizardsReborn.WISP_PARTICLE)
                                            .addVelocity(((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25) + 0.02f, ((random.nextDouble() - 0.5D) / 25))
                                            .setAlpha(0.3f * charge, 0).setScale(0.15f * charge, 0)
                                            .setColor(r, g, b)
                                            .setLifetime(20)
                                            .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)) * charge)
                                            .spawn(level, pos.x(), pos.y(), pos.z());
                                }

                                if (random.nextFloat() < 0.3f) {
                                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                            .addVelocity(((random.nextDouble() - 0.5D) / 25), ((random.nextDouble() - 0.5D) / 25) + 0.02f, ((random.nextDouble() - 0.5D) / 25))
                                            .setAlpha(0.3f * charge, 0).setScale(0.15f * charge, 0)
                                            .setColor(r, g, b)
                                            .setLifetime(30)
                                            .setSpin((0.3f * (float) ((random.nextDouble() - 0.5D) * 2)) * charge)
                                            .spawn(level, pos.x(), pos.y(), pos.z());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void onBowShot(AbstractArrow abstractarrow, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player && ArcaneEnchantmentUtils.isArcaneItem(stack)) {
            int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT);
            if (enchantmentLevel > 0) {
                if (BowItem.getPowerForTime(stack.getUseDuration() - timeLeft) >= 1f) {
                    abstractarrow.getCapability(IArrowModifier.INSTANCE, null).ifPresent((w) -> {
                        int time = stack.getUseDuration() - timeLeft;
                        if (time > 100) {
                            time = 100;
                        }
                        if (time > 50 && enchantmentLevel == 1) {
                            time = 50;
                        }

                        float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                        int wissen = WissenUtils.getWissenInItems(items);
                        int cost = (int) ((30 + time) * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtils.canRemoveWissen(wissen, cost)) {
                            WissenUtils.removeWissenFromWissenItems(items, cost);
                            w.setCharge(time);
                        }
                    });
                }
            }
        }
    }

    public static void arrowTick(AbstractArrow arrow) {
        if (!arrow.level().isClientSide()) {
            if (isCharged(arrow)) {
                Color color = WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                Vec3 motion = arrow.getDeltaMovement();
                Vec3 pos = arrow.position();
                Vec3 norm = motion.normalize().scale(0.025f);

                float charge = getCharge(arrow) / 100f;

                PacketHandler.sendToTracking(arrow.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new ChargeSpellProjectileRayEffectPacket((float) arrow.xo, (float) arrow.yo, (float) arrow.zo, (float) pos.x, (float) pos.y, (float) pos.z, (float) norm.x, (float) norm.y, (float) norm.z, r, g, b, charge));
            }
        }
    }

    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().is(DamageTypes.ARROW)) {
            AbstractArrow arrow = null;
            if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow1) {
                arrow = arrow1;
            }
            if (event.getSource().getEntity() instanceof AbstractArrow arrow1) {
                arrow = arrow1;
            }
            LivingEntity target = event.getEntity();
            if (arrow != null)
                onHit(new Vec3(target.getX(), target.getY() + (target.getBbHeight() / 2), target.getZ()), arrow);
        }
    }

    public static void onHitBlock(BlockHitResult result, AbstractArrow arrow) {
        onHit(result.getLocation(), arrow);
    }

    public static void onHit(Vec3 pos, AbstractArrow arrow) {
        if (!arrow.level().isClientSide()) {
            if (isCharged(arrow)) {
                Color color = WizardsReborn.WISSEN_CHARGE_ARCANE_ENCHANTMENT.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                float charge = getCharge(arrow) / 100f;

                PacketHandler.sendToTracking(arrow.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new WissenChargeBurstEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b, charge));
                arrow.level().playSound(WizardsReborn.proxy.getPlayer(), pos.x, pos.y, pos.z, WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));

                float additionalDamage = 0;
                if (arrow.getOwner() instanceof Player player) {
                    additionalDamage = (float) player.getAttribute(WizardsReborn.ARCANE_DAMAGE.get()).getValue();
                }
                float damage = 2f + (5f * charge);
                float distance = 0.5f + (1.25f * charge);

                List<LivingEntity> entityList = arrow.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos.x() - distance, pos.y() - distance, pos.z() - distance, pos.x() + distance, pos.y() + distance, pos.z() + distance));
                for (LivingEntity target : entityList) {
                    target.invulnerableTime = 0;
                    target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), arrow.getOwner()), damage + additionalDamage);
                }

                setCharge(arrow,0);
            }
        }
    }

    public static boolean isCharged(Entity entity) {
        if (!(entity instanceof AbstractArrow)) return false;
        AtomicBoolean isCharged = new AtomicBoolean(false);
        entity.getCapability(IArrowModifier.INSTANCE, null).ifPresent((w) -> {
            isCharged.set(w.isCharged());
        });
        return isCharged.get();
    }

    public static int getCharge(Entity entity) {
        if (!(entity instanceof AbstractArrow)) return 0;
        AtomicInteger charge = new AtomicInteger(0);
        entity.getCapability(IArrowModifier.INSTANCE, null).ifPresent((w) -> {
            charge.set(w.getCharge());
        });
        return charge.get();
    }

    public static void setCharge(Entity entity, int charge) {
        if (!(entity instanceof AbstractArrow)) return;
        entity.getCapability(IArrowModifier.INSTANCE, null).ifPresent((w) -> {
            w.setCharge(charge);
        });
    }
}
