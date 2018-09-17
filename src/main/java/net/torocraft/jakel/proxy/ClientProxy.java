package net.torocraft.jakel.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.entities.Entities;
import net.torocraft.jakel.items.ItemSpell;
import net.torocraft.jakel.items.Items;

public class ClientProxy extends CommonProxy {

  @Override
  public void openGui(int modGuiId) {
    EntityPlayer player = Minecraft.getMinecraft().player;
    player
        .openGui(Jakel.INSTANCE, modGuiId, player.world, (int) player.posX, (int) player.posY,
            (int) player.posZ);
  }

  @Override
  public void preInit(FMLPreInitializationEvent e) {
    super.preInit(e);
    Entities.initAllRenders();
  }

  @Override
  public void init(FMLInitializationEvent e) {
    super.init(e);
    Items.initAllRenders();
  }


  @Override
  public EntityPlayer getPlayer() {
    return Minecraft.getMinecraft().player;
  }

  @Override
  public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord,
      double zCoord, double xSpeed, double ySpeed,
      double zSpeed, int... parameters) {
    Minecraft.getMinecraft().world
        .spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
  }

}