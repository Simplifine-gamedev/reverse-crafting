package com.orca.reversecrafting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ReverseCraftingMod implements ModInitializer {
    public static final String MOD_ID = "reverse-crafting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Map<Block, ItemStack> REVERSED_DROPS = new HashMap<>();

    static {
        // Logs drop planks (4 planks per log, reversed)
        REVERSED_DROPS.put(Blocks.OAK_LOG, new ItemStack(Items.OAK_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.SPRUCE_LOG, new ItemStack(Items.SPRUCE_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.BIRCH_LOG, new ItemStack(Items.BIRCH_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.JUNGLE_LOG, new ItemStack(Items.JUNGLE_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.ACACIA_LOG, new ItemStack(Items.ACACIA_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.DARK_OAK_LOG, new ItemStack(Items.DARK_OAK_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.MANGROVE_LOG, new ItemStack(Items.MANGROVE_PLANKS, 4));
        REVERSED_DROPS.put(Blocks.CHERRY_LOG, new ItemStack(Items.CHERRY_PLANKS, 4));

        // Stone drops cobblestone, cobblestone could drop gravel (processing chain)
        REVERSED_DROPS.put(Blocks.COBBLESTONE, new ItemStack(Items.GRAVEL, 1));

        // Iron ore drops iron ingot directly (smelting reversed)
        REVERSED_DROPS.put(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT, 1));
        REVERSED_DROPS.put(Blocks.DEEPSLATE_IRON_ORE, new ItemStack(Items.IRON_INGOT, 1));

        // Gold ore drops gold ingot directly
        REVERSED_DROPS.put(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT, 1));
        REVERSED_DROPS.put(Blocks.DEEPSLATE_GOLD_ORE, new ItemStack(Items.GOLD_INGOT, 1));

        // Coal ore drops diamonds (ultimate reverse)
        REVERSED_DROPS.put(Blocks.COAL_ORE, new ItemStack(Items.DIAMOND, 1));
        REVERSED_DROPS.put(Blocks.DEEPSLATE_COAL_ORE, new ItemStack(Items.DIAMOND, 1));

        // Diamond ore drops coal (reversed value)
        REVERSED_DROPS.put(Blocks.DIAMOND_ORE, new ItemStack(Items.COAL, 1));
        REVERSED_DROPS.put(Blocks.DEEPSLATE_DIAMOND_ORE, new ItemStack(Items.COAL, 1));

        // Wheat drops seeds, seeds concept reversed
        REVERSED_DROPS.put(Blocks.WHEAT, new ItemStack(Items.WHEAT_SEEDS, 3));
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Reverse Crafting initialized! The world is now backwards!");

        // Register block break event for custom drops
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world instanceof ServerWorld serverWorld) {
                Block block = state.getBlock();
                if (REVERSED_DROPS.containsKey(block)) {
                    ItemStack drop = REVERSED_DROPS.get(block).copy();
                    // Spawn the reversed item
                    ItemEntity itemEntity = new ItemEntity(
                        serverWorld,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        drop
                    );
                    serverWorld.spawnEntity(itemEntity);
                }
            }
        });

        // Modify loot tables to add reversed drops
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            String path = key.getValue().getPath();

            // Add diamond drops to coal ore
            if (path.equals("blocks/coal_ore") || path.equals("blocks/deepslate_coal_ore")) {
                LootPool.Builder pool = LootPool.builder()
                    .with(ItemEntry.builder(Items.DIAMOND))
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)));
                tableBuilder.pool(pool);
            }

            // Add coal drops to diamond ore
            if (path.equals("blocks/diamond_ore") || path.equals("blocks/deepslate_diamond_ore")) {
                LootPool.Builder pool = LootPool.builder()
                    .with(ItemEntry.builder(Items.COAL))
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)));
                tableBuilder.pool(pool);
            }
        });

        LOGGER.info("Reversed block drops registered!");
        LOGGER.info("Custom reversed recipes loaded from datapack!");
    }
}
