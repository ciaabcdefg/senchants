package com.ciaabcdefg.senchants.enchants;

import com.ciaabcdefg.senchants.Main;
import com.ciaabcdefg.senchants.init.EnchantmentInit;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EnchantmentCompensation extends Enchantment {
    public EnchantmentCompensation(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
        this.setName("compensation");
        this.setRegistryName(new ResourceLocation(Main.MODID + ":compensation"));

        EnchantmentInit.ENCHANTMENTS.add(this);
    }
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 11;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 11;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        if (ench == Enchantments.MENDING) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
