package com.ciaabcdefg.senchants;

import com.ciaabcdefg.senchants.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "senchants";
    public static final String NAME = "Senchants";
    public static final String VERSION = "0.0.1";
    public static final String CLIENT = "com.ciaabcdefg.senchants.proxy.ClientProxy";
    public static final String SERVER = "com.ciaabcdefg.senchants.proxy.CommonProxy";

    public static Main instance;
    public static Logger logger;

    @SidedProxy(clientSide = CLIENT, serverSide = SERVER)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) { }

    @EventHandler
    public void init(FMLPostInitializationEvent event) { }
}
