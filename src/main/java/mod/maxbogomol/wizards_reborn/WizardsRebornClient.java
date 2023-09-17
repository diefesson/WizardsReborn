package mod.maxbogomol.wizards_reborn;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.model.curio.AmuletModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.BeltModel;
import mod.maxbogomol.wizards_reborn.client.particle.ArcaneWoodLeafParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.KarmaParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.SparkleParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.WispParticleType;
import mod.maxbogomol.wizards_reborn.client.render.entity.ArcaneWoodBoatModel;
import mod.maxbogomol.wizards_reborn.client.render.entity.EmptyRenderer;
import mod.maxbogomol.wizards_reborn.client.render.item.*;
import mod.maxbogomol.wizards_reborn.client.render.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Map;

public class WizardsRebornClient {
    private static final String CATEGORY_KEY = "key.category."+WizardsReborn.MOD_ID+".general";
    public static final KeyMapping OPEN_WAND_SELECTION_KEY = new KeyMapping("key."+WizardsReborn.MOD_ID+".selection_hud", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_KEY);

    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "belt"), "main");
    public static ModelLayerLocation AMULET_LAYER = new ModelLayerLocation(new ResourceLocation(WizardsReborn.MOD_ID, "amulet"), "main");

    public static ShaderInstance GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;

    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            Sheets.addWoodType(WizardsReborn.ARCANE_WOOD_TYPE);

            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ARCANE_LINEN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WizardsReborn.POTTED_ELDER_MOR.get(), RenderType.cutout());

            BlockEntityRenderers.register(WizardsReborn.SIGN_TILE_ENTITY.get(), SignRenderer::new);
            BlockEntityRenderers.register(WizardsReborn.HANGING_SIGN_TILE_ENTITY.get(), HangingSignRenderer::new);
            BlockEntityRenderers.register(WizardsReborn.ARCANE_PEDESTAL_TILE_ENTITY.get(), (trd) -> new ArcanePedestalTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_ALTAR_TILE_ENTITY.get(), (trd) -> new WissenAltarTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_TRANSLATOR_TILE_ENTITY.get(), (trd) -> new WissenTranslatorTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.WISSEN_CRYSTALLIZER_TILE_ENTITY.get(), (trd) -> new WissenCrystallizerTileEntityRenderer());
            BlockEntityRenderers.register(WizardsReborn.ARCANE_WORKBENCH_TILE_ENTITY.get(), (trd) -> new ArcaneWorkbenchTileEntityRenderer());

            BlockEntityRenderers.register(WizardsReborn.CRYSTAL_TILE_ENTITY.get(), (trd) -> new CrystalTileEntityRenderer());

            EntityRenderers.register(WizardsReborn.BOAT.get(), m -> new ArcaneWoodBoatModel(m, false));
            EntityRenderers.register(WizardsReborn.CHEST_BOAT.get(), m -> new ArcaneWoodBoatModel(m, true));
            EntityRenderers.register(WizardsReborn.SPELL_PROJECTILE.get(), EmptyRenderer::new);
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                event.register(WandCrystalsModels.getModelLocationCrystal(crystal));
            }

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
                    event.register(new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, item + "_in_hand"), "inventory"));
                }
            }
        }

        @SubscribeEvent
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();
            BakedModel existingModel = map.get(new ModelResourceLocation(WizardsReborn.ARCANE_WAND.getId(), "inventory"));

            for (String crystal : WandCrystalsModels.getCrystals()) {
                BakedModel model = map.get(WandCrystalsModels.getModelLocationCrystal(crystal));
                WandCrystalsModels.addModelCrystals(crystal, model);
                model = new CustomFinalisedModel(existingModel, WandCrystalsModels.getModelCrystals(crystal));
                WandCrystalsModels.addModel(crystal, model);
            }
            CustomModel customModel = new CustomModel(existingModel, new WandModelOverrideList());
            map.replace(new ModelResourceLocation(WizardsReborn.ARCANE_WAND.getId(), "inventory"), customModel);

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                Item2DRenderer.onModelBakeEvent(event);
            }
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(WizardsRebornClient.OPEN_WAND_SELECTION_KEY);
        }

        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(WizardsReborn.WISP_PARTICLE.get(), WispParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.KARMA_PARTICLE.get(), KarmaParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(WizardsReborn.ARCANE_WOOD_LEAF_PARTICLE.get(), ArcaneWoodLeafParticleType.Factory::new);
        }

        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:glowing_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> {
                        GLOWING_PARTICLE_SHADER = shader;
                    });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:sprite_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> {
                        SPRITE_PARTICLE_SHADER = shader;
                    });
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(ArcaneWoodBoatModel.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(ArcaneWoodBoatModel.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(WizardsRebornClient.BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(WizardsRebornClient.AMULET_LAYER, AmuletModel::createBodyLayer);
        }
    }
}