package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import com.mojang.datafixers.types.templates.Tag;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipeBuilder;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipeBuilder;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class AMrecipe {
    // some stuff xd
    protected static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger((ItemPredicate[]) Arrays.stream(items).map(ItemPredicate.Builder::build).toArray((x$0) -> new ItemPredicate[x$0]));
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(new ItemLike[]{itemLike}));
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(tag));
    }

    // the actual stuff
    public static String itemId(Item inputItem) {
        return BuiltInRegistries.ITEM.getKey(inputItem).getPath();
    }

    public static String lazyOutput(Item inputItem, Item outputItem, String process) {
        String iI = itemId(inputItem);
        String oI = itemId(outputItem);

        return oI+"_from_"+iI+"_by_"+process;
    }

    public static void itemAndBlock(Item item, Block block, RecipeOutput output) {
        String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block.asItem(), 1)
                .pattern("III")
                .pattern("III")
                .pattern("III")
                .define('I', item)
                .unlockedBy(itemId(item), has(item))
                .save(output, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, itemId+"_crafting_from_items"));
        String blockId = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item, 9)
                .requires(block.asItem())
                .unlockedBy("has_"+blockId, has(block))
                .save(output, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, itemId+"_crafting_from_block"));
    }

    public static void toDust(Supplier<Item> dust, HashMap<Item,Integer> items, RecipeOutput output) {
        for (Item item : items.keySet()) {
            String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, dust.get(), items.get(item))
                    .requires(ModTags.HAMMERS)
                    .requires(item)
                    .unlockedBy(itemId(item), has(item))
                    .save(output,lazyOutput(item,dust.get(),"hammering"));
        }
    }

    public static void smeltAndBlast(Item source, Item outcome, RecipeOutput output) {
        String itemId = BuiltInRegistries.ITEM.getKey(source).getPath();
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(source),
                        RecipeCategory.MISC,
                        outcome,
                        0.5f,
                        200
                )
                .unlockedBy(itemId(source), has(source))
                .save(output,lazyOutput(source,outcome,"smelting"));

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(source),
                        RecipeCategory.MISC,
                        outcome,
                        0.5f,
                        150
                )
                .unlockedBy(itemId(source), has(source))
                .save(output,lazyOutput(source,outcome,"blasting"));
    }
    public static void smeltAndBlast(Item source, Item outcome, Item hasThis,RecipeOutput output) {
        String itemId = BuiltInRegistries.ITEM.getKey(source).getPath();
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(source),
                        RecipeCategory.MISC,
                        outcome,
                        0.5f,
                        200
                )
                .unlockedBy(itemId(source), has(hasThis))
                .save(output,lazyOutput(source,outcome,"smelting"));

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(source),
                        RecipeCategory.MISC,
                        outcome,
                        0.5f,
                        150
                )
                .unlockedBy(itemId(source), has(hasThis))
                .save(output,lazyOutput(source,outcome,"blasting"));
    }
    // for dusts
    public static void smeltAndBlast(Item dust, List<Item> fromItems, Item outputItem, RecipeOutput output) {
        for (Item inputItem : fromItems) {
            String itemId = BuiltInRegistries.ITEM.getKey(inputItem).getPath();
            SimpleCookingRecipeBuilder.smelting(
                            Ingredient.of(inputItem),
                            RecipeCategory.MISC,
                            outputItem,
                            0.5f,
                            200
                    )
                    .unlockedBy(itemId(inputItem), has(inputItem))
                    .save(output,lazyOutput(inputItem,outputItem,"smelting"));

            SimpleCookingRecipeBuilder.blasting(
                            Ingredient.of(inputItem),
                            RecipeCategory.MISC,
                            outputItem,
                            0.5f,
                            150
                    )
                    .unlockedBy(itemId(inputItem), has(inputItem))
                    .save(output,lazyOutput(inputItem,outputItem,"blasting"));
        }
    }

    public static void crafingRecipe_Tools(AMtool.ToolSet toolSet, Item fromItem, RecipeOutput output) {
        String itemId = BuiltInRegistries.ITEM.getKey(fromItem).getPath();
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.shovelItem())
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.shovelItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.pickaxeItem())
                .pattern("BBB")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.pickaxeItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.axeItem())
                .pattern("BB ")
                .pattern("BS ")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.axeItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.hoeItem())
                .pattern("BB ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.hoeItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.swordItem())
                .pattern(" B ")
                .pattern(" B ")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.swordItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, toolSet.hammerItem())
                .pattern("BBB")
                .pattern("BSB")
                .pattern(" S ")
                .define('B', fromItem)
                .define('S', Items.STICK)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,toolSet.hammerItem().get(),"crafting"));
    }

    public static void Armor(AMarmor.ArmorSet armorSet, Item fromItem, RecipeOutput output) {
        String itemId = BuiltInRegistries.ITEM.getKey(fromItem).getPath();
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, armorSet.helmetItem())
                .pattern("BBB")
                .pattern("B B")
                .define('B', fromItem)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,armorSet.helmetItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, armorSet.chestplateItem())
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', fromItem)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,armorSet.chestplateItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, armorSet.leggingsItem())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', fromItem)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,armorSet.leggingsItem().get(),"crafting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, armorSet.bootsItem())
                .pattern("B B")
                .pattern("B B")
                .define('B', fromItem)
                .unlockedBy(itemId(fromItem), has(fromItem))
                .save(output,lazyOutput(fromItem,armorSet.bootsItem().get(),"crafting"));
    }

    public static void crushWithAll(Item inputItem, Item outputItem, int duration, RecipeOutput output) {
        String iI = itemId(inputItem);
        String oI = itemId(outputItem);

        for (AMsimpleItem.crushingWheel wheel : AMsimpleItem.registeredWheels.keySet()) {
            String wI = itemId(wheel.item().get());
            new CrushingRecipeBuilder(new ItemStack(outputItem))
                    .setDuration(duration)
                    .setWheel(wheel.item().get())
                    .addInput(inputItem)
                    .save(output,oI+"_from_"+iI+"_crushing_with_"+wI);
        }
    }

    public static void crushWithAll(List<Item> inputItems, Item outputItem, int duration, RecipeOutput output) {
        String oI = itemId(outputItem);

        for (Item inputItem : inputItems) {
            String iI = itemId(inputItem);
            for (AMsimpleItem.crushingWheel wheel : AMsimpleItem.registeredWheels.keySet()) {
                String wI = itemId(wheel.item().get());
                new CrushingRecipeBuilder(new ItemStack(outputItem))
                        .setDuration(duration)
                        .setWheel(wheel.item().get())
                        .addInput(inputItem)
                        .save(output,oI+"_from_"+iI+"_crushing_with_"+wI);
            }
        }
    }

    public static void crushWithAll(HashMap<Item,Integer> inputItems, Item outputItem, int duration, RecipeOutput output) {
        String oI = itemId(outputItem);

        for (Item inputItem : inputItems.keySet()) {
            String iI = itemId(inputItem);
            for (AMsimpleItem.crushingWheel wheel : AMsimpleItem.registeredWheels.keySet()) {
                String wI = itemId(wheel.item().get());
                new CrushingRecipeBuilder(new ItemStack(outputItem,inputItems.get(inputItem)))
                        .setDuration(duration)
                        .setWheel(wheel.item().get())
                        .addInput(inputItem)
                        .save(output,oI+"_from_"+iI+"_crushing_with_"+wI);
            }
        }
    }

    public static void crushWith(HashMap<AMsimpleItem.crushingWheel, String> wheels, Item inputItem, Item outputItem, int duration, RecipeOutput output) {
        String oI = itemId(outputItem);
        String iI = itemId(inputItem);

        for (AMsimpleItem.crushingWheel wheel : wheels.keySet()) {

            String wI = itemId(wheel.item().get());
            new CrushingRecipeBuilder(new ItemStack(outputItem))
                    .setDuration(duration)
                    .setWheel(wheel.item().get())
                    .addInput(inputItem)
                    .save(output,oI+"_from_"+iI+"_crushing_with_"+wI);
        }
    }

    public static void crushWith(int requiredTier, Item inputItem, Item outputItem, int duration, RecipeOutput output) {
        String oI = itemId(outputItem);
        String iI = itemId(inputItem);

        for (AMsimpleItem.crushingWheel wheel : AMsimpleItem.registeredWheels.keySet()) {
            if (wheel.tier() < requiredTier) continue;

            String wI = itemId(wheel.item().get());
            new CrushingRecipeBuilder(new ItemStack(outputItem))
                    .setDuration(duration)
                    .setWheel(wheel.item().get())
                    .addInput(inputItem)
                    .save(output,oI+"_from_"+iI+"_crushing_with_"+wI);
        }
    }

    public static void alloyingWithFluxes(Item outputItem, int Oamount, Item input1, int I1amount, Item input2, int I2amount, RecipeOutput output) {
        String i1I = itemId(input1);
        String i2I = itemId(input2);
        String oI = itemId(outputItem);

        String sI = itemId(ModItems.SAND_FLUX_DUST.asItem());
        String bI = itemId(ModItems.BONE_FLUX_DUST.asItem());
        String cI = itemId(ModItems.CALCITE_FLUX_DUST.asItem());

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(input2,I2amount)
                .addInput(ModItems.SAND_FLUX_DUST.get(),3)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+sI));

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(input2,I2amount)
                .addInput(ModItems.BONE_FLUX_DUST.get(),2)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+bI));

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(input2,I2amount)
                .addInput(ModItems.CALCITE_FLUX_DUST.get(),1)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+cI));
    }

    public static void alloyingWithFluxesAndCoke(Item outputItem, int Oamount, Item input1, int I1amount, RecipeOutput output) {
        String i1I = itemId(input1);
        String i2I = itemId(ModItems.COKE.asItem());
        String oI = itemId(outputItem);

        String sI = itemId(ModItems.SAND_FLUX_DUST.asItem());
        String bI = itemId(ModItems.BONE_FLUX_DUST.asItem());
        String cI = itemId(ModItems.CALCITE_FLUX_DUST.asItem());

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(ModItems.COKE.asItem(),3)
                .addInput(ModItems.SAND_FLUX_DUST.get(),1)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+sI));

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(ModItems.COKE.asItem(),2)
                .addInput(ModItems.BONE_FLUX_DUST.get(),1)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+bI));

        new AlloyingRecipeBuilder(new ItemStack(outputItem,Oamount))
                .addInput(input1,I1amount)
                .addInput(ModItems.COKE.asItem(),1)
                .addInput(ModItems.CALCITE_FLUX_DUST.get(),1)
                .setDuration(200)
                .unlockedBy("has_"+i1I,has(input1))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,oI+"_alloying_from_"+i1I+"_and_"+i2I+"_with_"+cI));
    }
}
