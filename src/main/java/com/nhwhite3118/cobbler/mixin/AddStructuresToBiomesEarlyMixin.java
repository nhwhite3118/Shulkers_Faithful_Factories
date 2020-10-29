//package com.nhwhite3118.cobbler.mixin;
//
//import java.net.Proxy;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Supplier;
//
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import com.mojang.authlib.GameProfileRepository;
//import com.mojang.authlib.minecraft.MinecraftSessionService;
//import com.mojang.datafixers.DataFixer;
//import com.nhwhite3118.cobbler.Cobbler;
//
//import net.minecraft.resources.DataPackRegistries;
//import net.minecraft.resources.ResourcePackList;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.server.management.PlayerProfileCache;
//import net.minecraft.util.registry.DynamicRegistries;
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
//import net.minecraft.world.gen.feature.ConfiguredFeature;
//import net.minecraft.world.storage.IServerConfiguration;
//import net.minecraft.world.storage.SaveFormat;
//
//@Mixin(MinecraftServer.class)
//public class AddStructuresToBiomesEarlyMixin {
//
//    @Shadow
//    @Final
//    protected DynamicRegistries.Impl field_240767_f_;
//
//    @Inject(method = "<init>", at = @At(value = "TAIL"))
//    private void modifyBiomeRegistry(Thread p_i232576_1_, DynamicRegistries.Impl impl, SaveFormat.LevelSave p_i232576_3_, IServerConfiguration p_i232576_4_,
//            ResourcePackList p_i232576_5_, Proxy p_i232576_6_, DataFixer p_i232576_7_, DataPackRegistries p_i232576_8_, MinecraftSessionService p_i232576_9_,
//            GameProfileRepository p_i232576_10_, PlayerProfileCache p_i232576_11_, IChunkStatusListenerFactory p_i232576_12_, CallbackInfo ci) {
//
//        if (field_240767_f_.func_230521_a_(Registry.field_239720_u_).isPresent()) {
//            // Make the structure and features list mutable for modification later
//            for (Biome biome : field_240767_f_.func_230521_a_(Registry.field_239720_u_).get()) {
//                List<List<Supplier<ConfiguredFeature<?, ?>>>> tempFeature = ((GenerationSettingsAccessor) biome.func_242440_e()).getGSFeatures();
//                List<List<Supplier<ConfiguredFeature<?, ?>>>> mutableFeatures = new ArrayList<>();
//                for (int i = 0; i < Math.max(10, tempFeature.size()); i++) {
//                    if (i >= tempFeature.size()) {
//                        mutableFeatures.add(new ArrayList<>());
//                    } else {
//                        mutableFeatures.add(new ArrayList<>(tempFeature.get(i)));
//                    }
//                }
//                ((GenerationSettingsAccessor) biome.func_242440_e()).setGSFeatures(mutableFeatures);
//                ((GenerationSettingsAccessor) biome.func_242440_e())
//                        .setGSStructureFeatures(new ArrayList<>(((GenerationSettingsAccessor) biome.func_242440_e()).getGSStructureFeatures()));
//
////                // Add our structures and features
////                Cobbler.addFeaturesAndStructuresToBiomes(field_240767_f_.func_230521_a_(Registry.field_239720_u_).get(), biome,
////                        Objects.requireNonNull(field_240767_f_.func_230521_a_(Registry.field_239720_u_).get().getKey(biome)));
//                Cobbler.addFeaturesAndStructuresToBiome(biome,
//                        Objects.requireNonNull(field_240767_f_.func_230521_a_(Registry.field_239720_u_).get().getKey(biome)));
//
////                // add our structure spacing to all chunkgenerators
////                field_240767_f_.get(Registry.field_243549_ar)
////                        .forEach(registryManager -> registryManager.getStructuresConfig().structures.putAll(RSFeatures.RS_STRUCTURES));
//
//            }
//        }
//    }
//}