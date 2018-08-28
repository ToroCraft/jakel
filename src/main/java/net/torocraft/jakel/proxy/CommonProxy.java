package net.torocraft.jakel.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.network.MessageHealAnimation;
import net.torocraft.jakel.network.MessageReflectDamageAnimation;
import net.torocraft.jakel.network.MessageWorshipAnimation;
import net.torocraft.jakel.util.NullStorage;

public class CommonProxy {

  public void preInit(FMLPreInitializationEvent e) {
    CapabilityManager.INSTANCE.register(CapabilityPlayerData.class, new NullStorage<>(), CapabilityPlayerData::new);
  }

  public void init(FMLInitializationEvent e) {
    initPackets();
  }

  public void postInit(FMLPostInitializationEvent e) {

  }

  private void initPackets() {
    int packetId = 0;
    MessageHealAnimation.init(packetId++);
    MessageReflectDamageAnimation.init(packetId++);
    MessageWorshipAnimation.init(packetId++);
  }

  public void openGui(int modGuiId) {

  }

  public EntityPlayer getPlayer() {
    return null;
  }

  public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord,
      double zCoord, double xSpeed, double ySpeed,
      double zSpeed, int... parameters) {

  }

}
