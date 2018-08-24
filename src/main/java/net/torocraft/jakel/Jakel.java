package net.torocraft.jakel;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.torocraft.jakel.proxy.CommonProxy;

@Mod(modid = Jakel.MODID, version = Jakel.VERSION, name = Jakel.MODNAME)
public class Jakel {

  public static final String MODID = "torotraits";
  public static final String VERSION = "1.12.2-1";
  public static final String MODNAME = "Jakel";

  /**
   * incremented every deploy
   */
  public static final int API_VERSION = 1;

  /**
   * incremented when public methods in one of the API classes are changed
   */
  public static final int COMPAT_VERSION = 1;

  public static final String TAG_HAS_TRAIT = MODID + "_hasTrait";
  public static final String TAG_SUMMONED_MOB = MODID + "_summonedMob";

  public static final String NBT_TRAIT_STORE = MODID + "_traitStore";
  public static final String NBT_WORSHIP_COOLDOWN = MODID + "_worshipCooldown";
  public static final String TAG_IS_HEALABLE = MODID + "_isHealable";
  public static final String TAG_WORSHIPING = "nemesissystem_worshiping";

  @Mod.Instance(MODID)
  public static Jakel INSTANCE;

  public static MinecraftServer SERVER;

  public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

  @SidedProxy(clientSide = "net.torocraft.torotraits.proxy.ClientProxy", serverSide = "net.torocraft.torotraits.proxy.ServerProxy")
  public static CommonProxy PROXY;

  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    PROXY.preInit(e);
  }

  @EventHandler
  public void init(FMLInitializationEvent e) {
    PROXY.init(e);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    PROXY.postInit(e);
  }

  @EventHandler
  public void serverLoad(FMLServerStartingEvent e) {
    SERVER = e.getServer();
    e.registerServerCommand(new JakelCommand());
  }

}
