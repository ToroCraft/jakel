package net.torocraft.jakel.api;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
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


  public static boolean isMagicalItem(ItemData data) {
    return data != null;
  }

  public static boolean isMagicalConduit(ItemStack stack) {
    if (stack == null || stack.isEmpty()) {
      return false;
    }
    ItemData data = CapabilityItemData.get(stack);
    return data != null && data.isMagicalConduit != null && data.isMagicalConduit;
  }

  public static void applyItem(ItemStack item, Stats stats) {
    applyItem(CapabilityItemData.get(item), stats);
  }

  public static void applyItem(ItemData item, Stats stats) {
    if (isMagicalItem(item) && item.modifiers != null) {
      item.modifiers.forEach(m -> m.apply(stats));
    }
  }

  public enum SpellSlot {MAIN, SECONDARY, MAIN_ALT, SECONDARY_ALT}

  public static ItemStack getEquippedSpell(EntityPlayer player, SpellSlot slot) {
    // TODO check hot bar

    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();
    ItemStack selectedItem = inv.getStackInSlot(5 + slot.ordinal());


    System.out.println("getEquippedSpell " + player.getName()  + " " + slot + " FOUND: " + selectedItem);
    return selectedItem;
  }
}
