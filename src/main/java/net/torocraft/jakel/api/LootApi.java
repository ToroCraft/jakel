package net.torocraft.jakel.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootApi {

  public static void useMagicalWeapon(World world, EntityLivingBase user, ItemStack weapon) {

    // check if magical

    // get magical skill

    // check cooldown / cost

    // reset cooldown or subtract mana

    AttackApi.largeFireBall(world, AttackApi.inFrontOf(user), user.getLookVec(), 1);
  }


}
