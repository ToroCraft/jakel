package net.torocraft.jakel.loot.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.jakel.loot.Stats;
import net.torocraft.jakel.nbt.NbtField;

public class PlayerData {

  @NbtField
  public int mana;

  @NbtField
  public Stats stats = new Stats();

  public PlayerData(EntityPlayer player) {
    stats.thorns = 1;
    stats.wither = 100;
    mana = 0;
  }

  public void update(LivingUpdateEvent event) {
    if (event.getEntity().world.getTotalWorldTime() % 100 != 0) {
      return;
    }
    mana++;
    System.out.println("Update for player " + event.getEntity().getName());
  }

}
