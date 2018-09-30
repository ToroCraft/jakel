package net.torocraft.jakel.loot;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.torocraft.jakel.loot.hit.HitHandlers;
import net.torocraft.jakel.loot.hit.IHitHandler;
import net.torocraft.jakel.loot.stat.StatModifierData;
import net.torocraft.jakel.loot.tick.ITickHandler;
import net.torocraft.jakel.loot.tick.TickHandlers;
import net.torocraft.jakel.nbt.NbtField;

public class ItemData implements IHitHandler, ITickHandler {

  @NbtField
  public int level;

  @NbtField
  public boolean isMagicalConduit;

  @NbtField
  public int coolDown;

  @NbtField
  public TickHandlers tick;

  @NbtField
  public List<Integer> tickParameters;

  @NbtField
  public HitHandlers hit;

  @NbtField
  public List<Integer> hitParameters;


  @NbtField(genericType = StatModifierData.class)
  public List<StatModifierData> modifiers;


  @Override
  public void onHit(EntityPlayer player, EntityLivingBase victim, List<Integer> parameters) {
    if (hit != null) {
      hit.onHit(player, victim, parameters);
    }
  }

  @Override
  public void update(EntityPlayer player, List<Integer> parameters) {
    if (tick != null) {
      tick.update(player, parameters);
    }
  }
}
