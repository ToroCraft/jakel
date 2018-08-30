package net.torocraft.jakel.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.loot.Stats;

public class LootApi {

  public static void useMagicalWeapon(World world, EntityLivingBase user, ItemStack weapon) {

    // check if magical

    // get magical skill

    // check cooldown / cost

    // reset cooldown or subtract mana

    AttackApi.largeFireBall(world, AttackApi.inFrontOf(user), user.getLookVec(), 1);
  }


  private static boolean isMagicalItem(ItemData data) {
    return data != null;
  }

  public static void applyItem(ItemStack item, Stats stats) {
    applyItem(CapabilityItemData.get(item), stats);
  }

  public static void applyItem(ItemData item, Stats stats) {
    if (isMagicalItem(item) && item.modifiers != null) {
      item.modifiers.forEach(m -> m.apply(stats));
    }
  }
}
