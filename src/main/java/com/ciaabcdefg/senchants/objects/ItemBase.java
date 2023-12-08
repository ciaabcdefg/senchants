package com.ciaabcdefg.senchants.objects;

import com.ciaabcdefg.senchants.Main;
import com.ciaabcdefg.senchants.init.ItemInit;
import com.ciaabcdefg.senchants.proxy.ClientProxy;
import com.ciaabcdefg.senchants.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
