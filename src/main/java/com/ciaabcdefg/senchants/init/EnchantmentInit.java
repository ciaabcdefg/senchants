package com.ciaabcdefg.senchants.init;

import com.ciaabcdefg.senchants.Main;
import com.ciaabcdefg.senchants.enchants.EnchantmentCompensation;
import com.ciaabcdefg.senchants.enchants.EnchantmentAquaticMending;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class EnchantmentInit {
    public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
    public static HashMap<UUID, Integer> playerTicks = new HashMap<UUID, Integer>();

    public static final Enchantment COMPENSATION = new EnchantmentCompensation(
            Rarity.UNCOMMON,
            EnumEnchantmentType.ARMOR,
            new EntityEquipmentSlot[] {
                    EntityEquipmentSlot.HEAD,
                    EntityEquipmentSlot.CHEST,
                    EntityEquipmentSlot.LEGS,
                    EntityEquipmentSlot.FEET
            }
    );

    public static final Enchantment AQUATIC_MENDING = new EnchantmentAquaticMending(
            Rarity.RARE,
            EnumEnchantmentType.BREAKABLE,
            EntityEquipmentSlot.values()
    );

    @SubscribeEvent
    public static void onPlayerAttacked(LivingAttackEvent event) {
        Object attacker = event.getSource().getTrueSource();
        Object attacked = event.getEntity();
        if (!(attacker instanceof EntityLivingBase)) return;
        if (!(attacked instanceof EntityPlayer )) return;

        // EntityLivingBase entityAttacker = (EntityLivingBase)attacker;
        EntityPlayer entityPlayer = (EntityPlayer)attacked;

        /*
        Main.logger.info("Attacker: {}, Entity: {}, Damage: {}",
                entityAttacker.getName(),
                entityPlayer.getName(),
                event.getAmount());
         */

        int damage = (int)Math.ceil(event.getAmount());
        if (damage < 0) damage = 0;

        int totalRepairAmount = 0;

        for (ItemStack itemStack : entityPlayer.inventory.armorInventory){
            int level = EnchantmentHelper.getEnchantmentLevel(COMPENSATION, itemStack);
            int armorRepairAmount = (int)Math.ceil(level * damage * 0.4);

            totalRepairAmount += armorRepairAmount;
            itemStack.damageItem(-armorRepairAmount, entityPlayer);
        }

        for (int i = 0; i < 9; i++){
            ItemStack stack = entityPlayer.inventory.getStackInSlot(i);
            stack.damageItem((int)Math.ceil(-totalRepairAmount * 0.25), entityPlayer);
        }
    }


    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Main.logger.info("{} joined", event.player.getName());
        playerTicks.put(event.player.getUniqueID(), 0);
    }

    @SubscribeEvent
    public static void onPlayerLeft(PlayerEvent.PlayerLoggedOutEvent event) {
        Main.logger.info("{} left", event.player.getName());
        playerTicks.remove(event.player.getUniqueID());
    }

    @SubscribeEvent
    public static void onPlayerTickServer(PlayerTickEvent event) {
        if (event.side != Side.SERVER) return;

        UUID playerUUID = event.player.getUniqueID();
        int newTick = playerTicks.get(playerUUID);

        if (newTick + 1 < 40) {
            playerTicks.put(playerUUID, newTick + 1);
        }
        else {
            EntityPlayer player = event.player;

            Main.logger.info("Player {} in water {}",
                    player.getName(),
                    player.isInWater());

            if (player.isInWater()) {
                for (ItemStack stack : player.getEquipmentAndArmor()){
                    if (stack.isEmpty()) continue;

                    /*
                    Main.logger.info("Item {} has enchantment level {}",
                            stack.getDisplayName(),
                            EnchantmentHelper.getEnchantmentLevel(AQUATIC_MENDING, stack));
                    */

                    if (EnchantmentHelper.getEnchantmentLevel(AQUATIC_MENDING, stack) == 0) continue;

                    // Main.logger.info("1% dura: {}", (stack.getMaxDamage() + 1) * 0.01);
                    // Main.logger.info("Item {} repaired", stack.getDisplayName());
                    stack.damageItem(-(int)(Math.ceil(stack.getMaxDamage() * 0.01)), player);
                }
            }

            playerTicks.put(playerUUID, 0);
        }
    }
}
