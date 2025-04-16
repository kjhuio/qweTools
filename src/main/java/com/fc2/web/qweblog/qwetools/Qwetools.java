package com.fc2.web.qweblog.qwetools;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.ItemWrittenBookPagesStrictJsonFix;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static net.minecraft.world.item.Item.*;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Qwetools.MODID)
public class Qwetools {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "qwetools";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "qwetools" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "qwetools" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "qwetools" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    //copper pickaxe and axe and ....
    public static final Supplier<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(Tiers.IRON, new Properties().attributes(PickaxeItem.createAttributes(Tiers.IRON, 1F, -2.8F))));
    public static final Supplier<DiggerItem> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(Tiers.IRON, new Item.Properties().attributes(AxeItem.createAttributes(Tiers.IRON, 7F, -3.1F))));
    public static final Supplier<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(Tiers.IRON, new Item.Properties().attributes(ShovelItem.createAttributes(Tiers.IRON,1F,-3F))));
    public static final Supplier<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe",() -> new HoeItem(Tiers.IRON,new Item.Properties().attributes(HoeItem.createAttributes(Tiers.IRON,1F,-3F))));
    public static final Supplier<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword",() -> new SwordItem(Tiers.IRON,new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 4F,-2.4F))));
    public static final Supplier<SwordItem> WOODEN_HAMMER = ITEMS.register("wooden_hammer",() -> new SwordItem(Tiers.WOOD,new Item.Properties().attributes(SwordItem.createAttributes(Tiers.WOOD,10F,-3.9F))));
    public static final Supplier<SwordItem> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new SwordItem(Tiers.STONE,new Item.Properties().attributes(SwordItem.createAttributes(Tiers.STONE,14F,-3.9F))));
    public static final Supplier<SwordItem> IRON_HAMMER = ITEMS.register("iron_hammer",() -> new SwordItem(Tiers.IRON,new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON,20F,-3.8F))));
    public static final Supplier<SwordItem> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",() -> new SwordItem(Tiers.DIAMOND,new Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND,25F,-3.6F))));
    public static final Supplier<SwordItem> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",() -> new SwordItem(Tiers.NETHERITE,new Properties().attributes(SwordItem.createAttributes(Tiers.NETHERITE,30F,-3.4F))));
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Qwetools(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Qwetools) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



        private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Starting...");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("qweTools Starting...");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("qweTools Starting");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
