package net.torocraft.jakel.loot.hit;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IHitHandler {

  void onHit(EntityPlayer player, EntityLivingBase victim, List<Integer> parameters);
}
