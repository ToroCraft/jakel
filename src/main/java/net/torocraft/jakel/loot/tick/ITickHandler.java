package net.torocraft.jakel.loot.tick;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface ITickHandler {

  void update(EntityPlayer player, List<Integer> parameters);
}
