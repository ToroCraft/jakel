package net.torocraft.jakel.loot;

import java.util.List;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.nbt.NbtField;

public class ItemData {

  @NbtField
  public Boolean isMagicalConduit;

  @NbtField
  public Integer coolDown;

  @NbtField(genericType = Modifier.class)
  public List<Modifier> modifiers;

  @SideOnly(Side.SERVER)
  public void handleTick(TickEvent event) {
    modifiers.forEach(m -> m.handleTick(event));
  }

}
