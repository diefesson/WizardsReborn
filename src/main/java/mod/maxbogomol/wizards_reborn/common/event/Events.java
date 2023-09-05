package mod.maxbogomol.wizards_reborn.common.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import mod.maxbogomol.wizards_reborn.common.knowledge.ItemKnowledge;
import mod.maxbogomol.wizards_reborn.common.knowledge.ItemTagKnowledge;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Events {

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Entity) event.addCapability(new ResourceLocation(WizardsReborn.MOD_ID, "knowledge"), new KnowledgeProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IKnowledge> KNOWLEDGE = IKnowledge.INSTANCE;
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(KNOWLEDGE).ifPresent((k) -> event.getOriginal().getCapability(KNOWLEDGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            PacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getLevel().isClientSide) {
            if (event.getEntity() instanceof Player) {
                PacketHandler.sendTo((ServerPlayer) event.getEntity(), new KnowledgeUpdatePacket((Player)event.getEntity()));
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide) {
            Player player = event.player;
            List<ItemStack> items = player.inventoryMenu.getItems();

            for (Knowledge knowledge : Knowledges.getKnowledges()) {
                if (knowledge instanceof ItemKnowledge) {
                    ItemKnowledge itemKnowledge = (ItemKnowledge) knowledge;
                    if (itemKnowledge.canReceived(items)) {
                        KnowledgeUtils.addKnowledge(player, knowledge);
                    }
                }

                if (knowledge instanceof ItemTagKnowledge) {
                    ItemTagKnowledge itemKnowledge = (ItemTagKnowledge) knowledge;
                    if (itemKnowledge.canReceived(items)) {
                        KnowledgeUtils.addKnowledge(player, knowledge);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() instanceof ScytheItem) {
            ScytheItem scythe = (ScytheItem) event.getItemStack().getItem();
            InteractionResult res = scythe.onBlockUse(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec(), true);

            if (res != InteractionResult.PASS) {
                event.setCanceled(true);
                event.setCancellationResult(res);
            }
        }
    }
}