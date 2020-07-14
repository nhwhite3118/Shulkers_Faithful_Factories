package com.nhwhite3118.cobbler;

import com.nhwhite3118.cobbler.utils.ConfigHelper;
import com.nhwhite3118.cobbler.utils.ConfigHelper.ConfigValueListener;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CobblerConfig {
    public static class CobblerConfigValues {
        public ConfigValueListener<Boolean> bonemealCanSpawnSapling;
        public ConfigValueListener<Integer> bonemealSaplingSpawnrate;
        public ConfigValueListener<Integer> bonemealSaplingSpawnAttempts;
        public ConfigValueListener<Boolean> desertSaplingsCanBecomeDeadBush;
        public ConfigValueListener<Integer> desertSaplingsMaxDeathrate;
        public ConfigValueListener<Integer> desertSaplingsMinDeathrate;

        public ConfigValueListener<Boolean> spawnShulkerFactories;
        public ConfigValueListener<Integer> shulkerFactorySpawnrate;
        public ConfigValueListener<Boolean> addMapsToShulkerFactoriesToEndCities;
        public ConfigValueListener<Integer> shulkerFactoryMapChance;

        public ConfigValueListener<Boolean> endermenCanPickupAndPlaceCocoa;

        public ConfigValueListener<Boolean> spidersCanSpinWebs;
        public ConfigValueListener<Integer> webSpinningMinLightLevel;
        public ConfigValueListener<Integer> webSpinningMaxLightLevel;
        public ConfigValueListener<Integer> webSpinningFrequency;
        public ConfigValueListener<Boolean> websEverywhere;

        CobblerConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
            builder.push("Feature Options");

            builder.push("EndStructures");

            spawnShulkerFactories = subscriber.subscribe(builder
                    .comment("\r\n Whether or not to spawn Shulker Factories - End City inspired structures with a shulker spawner"
                            + "\r\n Default value is true")
                    .translation("repurposedstructures.config.feature.endStructures.addshulkerfactories").define("shulkerFactorys", true));

            shulkerFactorySpawnrate = subscriber.subscribe(builder.comment("\r\n How often Shulker Factories will attempt to spawn per chunk. "
                    + "\r\n The chance of a factory generating at a chunk is 1/spawnrate."
                    + "\r\n 10 to practically always have one in render distance, 1000 for extremely rare factories"
                    + "\r\n 20 is slightly more common than end cities" + "\r\n Default value of 200 should average one every ~7,000 blocks (varies widely)")
                    .translation("nhwhite3118.config.structure.endStructures.shulkerfactories").defineInRange("shulkerFactorySpawnrate", 200, 10, 1000));

            addMapsToShulkerFactoriesToEndCities = subscriber.subscribe(builder
                    .comment("\r\n Whether or not to add a map, similar to the ones which cartographers sell, to the loot table for"
                            + "\r\n End City Chests (and by extention very rarely within shulker factory chests)" + "\r\n Default value is true")
                    .translation("repurposedstructures.config.feature.endStructures.addMapsToShulkerFactoriesToEndCities")
                    .define("addMapsToShulkerFactoriesToEndCities", true));

            shulkerFactoryMapChance = subscriber.subscribe(builder
                    .comment("\r\n The chance of getting a map to a Shulker Factory from a chest in an End City"
                            + "the probability of getting the map in any given chest is 1/shulkerFactoryMapWeight")
                    .translation("nhwhite3118.config.structure.endStructures.shulkerFactoryMapChance").defineInRange("shulkerFactoryMapChance", 5, 1, 1000));
            builder.pop();

            builder.push("Farmability");

            bonemealCanSpawnSapling = subscriber.subscribe(builder.comment("\r\n Whether or not bonemeal can spawn oak saplings" + "\r\n Default value is true")
                    .translation("repurposedstructures.config.feature.farmability.bonemealCanSpawnSapling").define("bonemealCanSpawnSapling", true));

            bonemealSaplingSpawnrate = subscriber.subscribe(builder.comment(
                    "\r\n Chance of attempting to spawn an oak sapling on a random block in a 7x3x7 area centered on the bonemealed block when bonemealing grass."
                            + "\r\n The probability of attempting to spawn an oak sapling each time gass is bonemealed is 1/spawnrate."
                            + "\r\n The probability of a sapling appearing is 1 - (((1 - ((plantableBlocksInRadius/147) * 1/bonemealSaplingSpawnRate))) ^ bonemealSaplingSpawnAttempts)"
                            + "\r\n Default value is 6")
                    .translation("nhwhite3118.config.feature.farmability.bonemealSaplingSpawnrate").defineInRange("bonemealSaplingSpawnrate", 6, 1, 1000));

            bonemealSaplingSpawnAttempts = subscriber.subscribe(builder
                    .comment("\r\n How many times to attempt to spawn a sapling when grass is bonemealed. (also max # of saplings which can spawn per bonemeal)"
                            + "\r\n Increasing this value too high may cause performance issues as it is not optimized for a large number of spawn attempts."
                            + "\r\n The probability of a sapling appearing is 1 - (((1 - ((plantableBlocksInRadius/147) * 1/bonemealSaplingSpawnRate))) ^ bonemealSaplingSpawnAttempts)"
                            + "\r\n Default value is 2")
                    .translation("nhwhite3118.config.feature.farmability.bonemealSaplingSpawnAttempts")
                    .defineInRange("bonemealSaplingSpawnAttempts", 2, 1, 1000));

            desertSaplingsCanBecomeDeadBush = subscriber.subscribe(
                    builder.comment("\r\n Whether or not saplings can convert to dead bushes in high light levels in the desert" + "\r\n Default value is true")
                            .translation("repurposedstructures.config.feature.farmability.desertSaplingsCanBecomeDeadBush")
                            .define("desertSaplingsCanBecomeDeadBush", true));

            desertSaplingsMaxDeathrate = subscriber.subscribe(builder
                    .comment("\r\n How many times out of 100 a sapling will become a dead bush instead of a tree at light level 15 in the desert"
                            + "\r\n Default value is 75")
                    .translation("nhwhite3118.config.feature.farmability.desertSaplingsMaxDeathrate").defineInRange("desertSaplingsMaxDeathrate", 75, 1, 100));

            desertSaplingsMinDeathrate = subscriber.subscribe(builder
                    .comment("\r\n How many times out of 100 a sapling will become a dead bush instead of a tree at light level 10 in the desert"
                            + "\r\n Default value is 25")
                    .translation("nhwhite3118.config.feature.farmability.desertSaplingsMinDeathrate").defineInRange("desertSaplingsMinDeathrate", 25, 1, 100));

            endermenCanPickupAndPlaceCocoa = subscriber.subscribe(builder.comment(
                    "\r\n Whether or not Endermen can place cocoa. This overrides any changes to enderman_holdable for cocoa and works with MobGreifing turned off if set to true"
                            + "\r\n Default value is true")
                    .translation("nhwhite3118.config.feature.farmability.endermenCanPickupAndPlaceCocoa").define("endermenCanPickupAndPlaceCocoa", true));

            spidersCanSpinWebs = subscriber.subscribe(builder.comment(
                    "\r\n Whether or not spiders (cave, regular, and most likely any moded spiders decended from the Minecraft spider) can spawn cobwebs under certain conditions"
                            + "\r\n If enabled, they can spawn cobwebs next to leaves and logs, spawners, and other cobwebs (6 cardinal directions, not diagonals) provided"
                            + "\r\n that the light level that they're in is between webSpinningMinLightLevel and webSpinningMaxLightLevel, inclusive"
                            + "\r\n Default value is true")
                    .translation("nhwhite3118.config.feature.farmability.spidersCanSpinWebs").define("spidersCanSpinWebs", true));

            webSpinningMaxLightLevel = subscriber.subscribe(builder
                    .comment("\r\n The maximum light level, inclusive, at which spiders can spawn cobwebs attached to logs, leaves, spawners, or other webs"
                            + "\r\n For reference, spiders stop being aggressive somewhere between light levels 9 and 12. Every wiki has conflicting info, and it would take a lot of math"
                            + "\r\n for me to figure out an approximation because it looks like they use the light level of the model which takes neighbor's light levels into account"
                            + "\r\n Fun Fact: Spiders irl prefer to spin webs at dusk" + "\r\n Default value is 12")
                    .translation("nhwhite3118.config.feature.farmability.webSpinningMaxLightLevel").defineInRange("webSpinningMaxLightLevel", 12, 0, 15));

            webSpinningMinLightLevel = subscriber.subscribe(builder
                    .comment("\r\n The minimum light level, inclusive, at which spiders can spawn cobwebs attached to logs, leaves, spawners, or other webs"
                            + "\r\n Consider setting this value to 0 if you want webs around spider spawners to expand organically" + "\r\n Default value is 8")
                    .translation("nhwhite3118.config.feature.farmability.webSpinningMinLightLevel").defineInRange("webSpinningMinLightLevel", 8, 0, 15));

            webSpinningFrequency = subscriber.subscribe(builder
                    .comment("\r\n Unfortunately, too many factors can go into the frequency of web spinning so this will have to remain a 'magic' number"
                            + "\r\n When a spider is considering starting the task of spinning a web, it picks a random number between 0 and 100,000, and if it's under the"
                            + "\r\n value determined by this config, it will start spinning the web."
                            + "\r\n This, however, is not the only factor as the spider may choose a different task instead, and other mods can add their own tasks with different weights"
                            + "\r\n For reference I spawned ~6 spiders at noon in a roofed forest and came back to ~8 webs 20 minutes later with this set to 500, but when placed in"
                            + "\r\n a room made of logs and leaves there were 10 webs within about a minute" + "\r\n Default value is 500")
                    .translation("nhwhite3118.config.feature.farmability.webSpinningFrequency").defineInRange("webSpinningFrequency", 500, 0, 100000));

            websEverywhere = subscriber.subscribe(builder
                    .comment("\r\n Allows spiders to create webs anywhere, removing the restriction on needing to be next to a log, leaves, or other cobwebs"
                            + "\r\n Ironically, this will most likely improve performance since it will bypass the check, but I would suggest lowering the max light level if this is enabled"
                            + "\r\n since you will probably get tired of removing webs from your base rather quickly." + "\r\n Default value is false")
                    .translation("nhwhite3118.config.feature.farmability.websEverywhere").define("websEverywhere", false));

            builder.pop();

            builder.pop();
        }
    }
}