package net.torocraft.jakel.loot;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.loot.stat.StatModifierType;
import net.torocraft.jakel.stats.Stats;
import net.torocraft.jakel.nbt.NbtField;

public class Modifier {

  @NbtField
  public StatModifierType type;

  // tick handler?


  @NbtField
  public int amount;


  public Modifier() {

  }

  public Modifier(StatModifierType type, int amount) {
    this.type = type;
    this.amount = amount;
  }

  public void apply(Stats stats) {
    type.apply(stats, amount);
  }

  @SideOnly(Side.SERVER)
  public void handleTick(TickEvent event) {

  }
}
