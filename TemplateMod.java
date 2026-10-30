package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TemplateMod implements ModInitializer {

    public static HashMap<UUID, Boolean> segurandoFerroAntes = new HashMap<>();
    public static HashMap<UUID, Boolean> segurandoEsmeraldaAntes = new HashMap<>();
    public static HashMap<UUID, Integer> tempoEsperaPoder = new HashMap<>();

    @Override
    public void onInitialize() {
        System.out.println("Mod de minerios funcionando!");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            
            // diminui o tempo de espera a cada tick do cod
            for (UUID idPlayer : tempoEsperaPoder.keySet()) {
                int tempo = tempoEsperaPoder.getOrDefault(idPlayer, 0);
                if (tempo > 0) {
                    tempoEsperaPoder.put(idPlayer, tempo - 1);
                }
            }

            for (int i = 0; i < server.getPlayerList().getPlayers().size(); i++) {
                ServerPlayer jogador = server.getPlayerList().getPlayers().get(i);
                UUID idDoJogador = jogador.getUUID();
                Item itemNaMao = jogador.getMainHandItem().getItem();

                boolean taComFerro = (itemNaMao == Items.IRON_INGOT);
                boolean taComEsmeralda = (itemNaMao == Items.EMERALD);
                
                if (itemNaMao == Items.REDSTONE) {
                    if (jogador.level() instanceof ServerLevel) {
                        ServerLevel mundo = (ServerLevel) jogador.level();
                        

                        double direcaoX = jogador.getLookAngle().x;
                        double direcaoZ = jogador.getLookAngle().z;
                        
  
                        for (int distancia = 1; distancia <= 10; distancia++) {
                            double particulaX = jogador.getX() + (direcaoX * distancia);
                            double particulaY = java.lang.Math.floor(jogador.getY()) + 1.0; 
                            double particulaZ = jogador.getZ() + (direcaoZ * distancia);
                            
                            mundo.sendParticles(ParticleTypes.INSTANT_EFFECT, particulaX, particulaY, particulaZ, 3, 0.1, 0.1, 0.1, 0.0);
                        
                            if (tempoEsperaPoder.getOrDefault(idDoJogador, 0) <= 0) {
                                
                                BlockPos posicaoParticula = new BlockPos((int)particulaX, (int)particulaY, (int)particulaZ);
                                AABB caixaDaParticula = new AABB(posicaoParticula);
                                
                         List<LivingEntity> monstrosAchados = mundo.getEntitiesOfClass(LivingEntity.class, caixaDaParticula);
                                
                                for (int m = 0; m < monstrosAchados.size(); m++) {
                                    LivingEntity bicho = monstrosAchados.get(m);
                                    
                                    if (bicho != jogador) {
                                        if (bicho.isAlive()) {
                                            bicho.hurt(jogador.damageSources().playerAttack(jogador), 5.0F); 
                                           bicho.setSecondsOnFire(3); 
                                            tempoEsperaPoder.put(idDoJogador, 20); 
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // PODER DO OURO: Da velocidade se tiver segurando ele
                if (itemNaMao == Items.GOLD_INGOT) {
                    jogador.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 4, false, false, true));
                }

                // PODER DO FERRO: Levita quando segura, cai leve quando solta esse negocio
                if (taComFerro) {
                    jogador.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30, 0, false, false, true));
                } else {
                    if (segurandoFerroAntes.getOrDefault(idDoJogador, false)) {
                        jogador.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, false, true, true));
                    }
                }

                if (taComEsmeralda) {
                    jogador.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30, 0, false, false, true));
                }

                double vidaMaxima = 20.0;
                if (itemNaMao == Items.NETHERITE_INGOT) {
                    vidaMaxima = 100.0; 
                } else if (itemNaMao == Items.DIAMOND) {
                    vidaMaxima = 60.0;
                }

                var vidaDoAtributo = jogador.getAttribute(Attributes.MAX_HEALTH);
                if (vidaDoAtributo != null) {
                    if (vidaDoAtributo.getBaseValue() != vidaMaxima) {
                        vidaDoAtributo.setBaseValue(vidaMaxima);
                        if (jogador.getHealth() < vidaMaxima) {
                            jogador.setHealth((float) vidaMaxima);
                        }
                    }
                }

                if (itemNaMao == Items.DIAMOND) {
                    jogador.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 3, false, false, true));

                    BlockPos blocoDeBaixo = jogador.blockPosition().below();
                    if (jogador.level().getBlockState(blocoDeBaixo).isAir()) {
                        jogador.level().setBlockAndUpdate(blocoDeBaixo, Blocks.ICE.defaultBlockState());
                    }
                }

                if (itemNaMao == Items.NETHERITE_INGOT) {
                    jogador.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30, 2, false, false, true));
                    jogador.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30, 4, false, false, true));
                    jogador.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 30, 0, false, false, true));

                    if (jogador.tickCount % 10 == 0) {
                        jogador.level().playSound(null, jogador.blockPosition(), SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 0.5F, 1.0F);
                    }

                    if (jogador.level() instanceof ServerLevel) {
                        ServerLevel serverMundo = (ServerLevel) jogador.level();
                        serverMundo.sendParticles(ParticleTypes.FLAME, jogador.getX(), jogador.getY(), jogador.getZ(), 3, 0.2, 0.2, 0.2, 0.02);
                    }
                }

                // guarda o estado atual para rodar no proximo tick do server pelo menos e pra fzer isso
                segurandoFerroAntes.put(idDoJogador, taComFerro);
                segurandoEsmeraldaAntes.put(idDoJogador, taComEsmeralda);
            }
        });
    }
}