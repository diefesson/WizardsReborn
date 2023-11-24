package mod.maxbogomol.wizards_reborn.common.itemgroup;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WizardsRebornItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WizardsReborn.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WIZARDS_REBORN_GROUP = CREATIVE_MODE_TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()))
                    .title(Component.translatable("itemGroup.wizards_reborn_mod_tab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WizardsRebornItemGroup.WIZARDS_REBORN_GROUP.getKey()) {
            event.accept(WizardsReborn.ARCANE_GOLD_INGOT);
            event.accept(WizardsReborn.ARCANE_GOLD_NUGGET);
            event.accept(WizardsReborn.RAW_ARCANE_GOLD);
            event.accept(WizardsReborn.ARCANE_GOLD_BLOCK_ITEM);
            event.accept(WizardsReborn.ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.DEEPSLATE_ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.NETHER_ARCANE_GOLD_ORE_ITEM);
            event.accept(WizardsReborn.RAW_ARCANE_GOLD_BLOCK_ITEM);

            event.accept(WizardsReborn.ARCANE_GOLD_SWORD);
            event.accept(WizardsReborn.ARCANE_GOLD_PICKAXE);
            event.accept(WizardsReborn.ARCANE_GOLD_AXE);
            event.accept(WizardsReborn.ARCANE_GOLD_SHOVEL);
            event.accept(WizardsReborn.ARCANE_GOLD_HOE);
            event.accept(WizardsReborn.ARCANE_GOLD_SCYTHE);

            event.accept(WizardsReborn.ARCANE_GOLD_HELMET);
            event.accept(WizardsReborn.ARCANE_GOLD_CHESTPLATE);
            event.accept(WizardsReborn.ARCANE_GOLD_LEGGINGS);
            event.accept(WizardsReborn.ARCANE_GOLD_BOOTS);

            event.accept(WizardsReborn.ARCANUM);
            event.accept(WizardsReborn.ARCANUM_DUST);
            event.accept(WizardsReborn.ARCANUM_BLOCK_ITEM);
            event.accept(WizardsReborn.ARCANUM_ORE_ITEM);
            event.accept(WizardsReborn.DEEPSLATE_ARCANUM_ORE_ITEM);

            event.accept(WizardsReborn.ARCACITE);
            event.accept(WizardsReborn.ARCACITE_BLOCK_ITEM);

            event.accept(WizardsReborn.ARCANE_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_ITEM);
            event.accept(WizardsReborn.STRIPPED_ARCANE_WOOD_LOG_ITEM);
            event.accept(WizardsReborn.STRIPPED_ARCANE_WOOD_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_PLANKS_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_STAIRS_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SLAB_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_FENCE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_FENCE_GATE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_DOOR_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_TRAPDOOR_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_PRESSURE_PLATE_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BUTTON_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SIGN_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_HANGING_SIGN_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BOAT_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_CHEST_BOAT_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_BRANCH);
            event.accept(WizardsReborn.ARCANE_WOOD_MORTAR);
            event.accept(WizardsReborn.ARCANE_WOOD_LEAVES_ITEM);
            event.accept(WizardsReborn.ARCANE_WOOD_SAPLING_ITEM);

            event.accept(WizardsReborn.WISESTONE_ITEM);
            event.accept(WizardsReborn.WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_BRICKS_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_STAIRS_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_SLAB_ITEM);
            event.accept(WizardsReborn.WISESTONE_TILE_WALL_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_STAIRS_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_SLAB_ITEM);
            event.accept(WizardsReborn.CHISELED_WISESTONE_WALL_ITEM);
            event.accept(WizardsReborn.WISESTONE_PILLAR_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_PRESSURE_PLATE_ITEM);
            event.accept(WizardsReborn.POLISHED_WISESTONE_BUTTON_ITEM);

            event.accept(WizardsReborn.ARCANE_LINEN_SEEDS);
            event.accept(WizardsReborn.ARCANE_LINEN_ITEM);
            event.accept(WizardsReborn.ARCANE_LINEN_HAY_ITEM);

            event.accept(WizardsReborn.MOR_ITEM);
            event.accept(WizardsReborn.MOR_BLOCK_ITEM);
            event.accept(WizardsReborn.ELDER_MOR_ITEM);
            event.accept(WizardsReborn.ELDER_MOR_BLOCK_ITEM);

            event.accept(WizardsReborn.PETALS);
            event.accept(WizardsReborn.GROUND_BROWN_MUSHROOM);
            event.accept(WizardsReborn.GROUND_RED_MUSHROOM);
            event.accept(WizardsReborn.GROUND_CRIMSON_FUNGUS);
            event.accept(WizardsReborn.GROUND_WARPED_FUNGUS);
            event.accept(WizardsReborn.GROUND_MOR);
            event.accept(WizardsReborn.GROUND_ELDER_MOR);

            event.accept(WizardsReborn.ARCANE_WAND);
            event.accept(WizardsReborn.WISSEN_WAND);

            event.accept(WizardsReborn.EARTH_CRYSTAL_SEED);
            event.accept(WizardsReborn.WATER_CRYSTAL_SEED);
            event.accept(WizardsReborn.AIR_CRYSTAL_SEED);
            event.accept(WizardsReborn.FIRE_CRYSTAL_SEED);
            event.accept(WizardsReborn.VOID_CRYSTAL_SEED);

            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_EARTH_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_WATER_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_AIR_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_FIRE_CRYSTAL.get()));
            event.accept(FracturedCrystalItem.creativeTabRandomStats(WizardsReborn.FRACTURED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.FACETED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.ADVANCED_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.MASTERFUL_VOID_CRYSTAL.get()));

            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_EARTH_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_WATER_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_AIR_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_FIRE_CRYSTAL.get()));
            event.accept(CrystalItem.creativeTabRandomStats(WizardsReborn.PURE_VOID_CRYSTAL.get()));

            event.accept(WizardsReborn.ARCANE_PEDESTAL_ITEM);
            event.accept(WizardsReborn.WISSEN_ALTAR_ITEM);
            event.accept(WizardsReborn.WISSEN_TRANSLATOR_ITEM);
            event.accept(WizardsReborn.WISSEN_CRYSTALLIZER_ITEM);
            event.accept(WizardsReborn.ARCANE_WORKBENCH_ITEM);
            event.accept(WizardsReborn.WISSEN_CELL_ITEM);
            event.accept(WizardsReborn.JEWELER_TABLE_ITEM);

            event.accept(WizardsReborn.WISESTONE_PEDESTAL_ITEM);
            event.accept(WizardsReborn.FLUID_PIPE_ITEM);
            event.accept(WizardsReborn.FLUID_EXTRACTOR_ITEM);
            event.accept(WizardsReborn.STEAM_PIPE_ITEM);
            event.accept(WizardsReborn.STEAM_EXTRACTOR_ITEM);
            event.accept(WizardsReborn.ALCHEMY_FURNACE_ITEM);
            event.accept(WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM);
            event.accept(WizardsReborn.STEAM_THERMAL_STORAGE_ITEM);

            event.accept(WizardsReborn.WHITE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.ORANGE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.MAGENTA_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIGHT_BLUE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.YELLOW_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIME_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.PINK_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.GRAY_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.LIGHT_GRAY_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.CYAN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.PURPLE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BLUE_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BROWN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.GREEN_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.RED_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.BLACK_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.RAINBOW_ARCANE_LUMOS_ITEM);
            event.accept(WizardsReborn.COSMIC_ARCANE_LUMOS_ITEM);

            event.accept(WizardsReborn.ARCANUM_AMULET);
            event.accept(WizardsReborn.ARCANUM_RING);
            event.accept(WizardsReborn.ARCACITE_AMULET);
            event.accept(WizardsReborn.ARCACITE_RING);
            event.accept(WizardsReborn.LEATHER_BELT);

            event.accept(WizardsReborn.ARCANEMICON);

            event.accept(WizardsReborn.VIOLENCE_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.REPRODUCTION_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.COOPERATION_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.HUNGER_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.SURVIVAL_BANNER_PATTERN_ITEM);
            event.accept(WizardsReborn.ELEVATION_BANNER_PATTERN_ITEM);

            event.accept(WizardsReborn.MUSIC_DISC_ARCANUM);
        }
    }
}