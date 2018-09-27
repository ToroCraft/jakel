package net.torocraft.jakel.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilityItemData;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.loot.ItemData;

@Mod.EventBusSubscriber
public class EventHandler {

  @SubscribeEvent
  public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
    if (event.getEntity().world.isRemote) {
      return;
    }
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntity();
      onEquipmentChange(player, CapabilityPlayerData.get(player));
    }
  }

  @SubscribeEvent
  public static void onLivingUpdateEvent(LivingUpdateEvent event) {
    if (event.getEntity().world.isRemote) {
      return;
    }
    if (!(event.getEntityLiving() instanceof EntityPlayer)) {
      return;
    }
    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    if (CapabilityPlayerData.get(player) != null) {
      update(event, player, CapabilityPlayerData.get(player));
    }
  }

  public static void update(LivingUpdateEvent event, EntityPlayer player, PlayerData data) {
    decrementCooldowns(data);
    incrementMana(data);
    applyItemTicks(event, player);
  }

  private static void incrementMana(PlayerData data) {
    if (data.mana > data.stats.manaMaximum) {
      data.mana = data.stats.manaMaximum;
      return;
    }
    if (data.mana < data.stats.manaMaximum) {
      data.mana += data.stats.manaPerTick;
    }
  }

  private static void decrementCooldowns(PlayerData data) {
    for (Entry<String, Float> e : data.cooldowns.entrySet()) {
      if (e.getValue() != null && e.getValue() > 0) {
        e.setValue(e.getValue() - data.stats.cooldownPerTick);
      }
    }
  }

  private static void applyItemTicks(LivingUpdateEvent event, EntityPlayer player) {
    if (event.getEntity().world.getTotalWorldTime() % 100 == 0) {
      getItems(player).forEach(stack -> tickItem(player, stack));
    }
  }

  private static void tickItem(EntityPlayer player, ItemStack stack) {
    ItemData data = CapabilityItemData.get(stack);
    if (data != null && data.tick != null) {
      data.tick.update(player, data.tickParameters);
    }
  }

  /**
   * return all the items that could have magical properties applied
   */
  public static List<ItemStack> getItems(EntityPlayer player) {
    List<ItemStack> items = new ArrayList<>();
    items.addAll(player.inventory.armorInventory);
    items.add(player.getHeldItemMainhand());
    items.add(player.getHeldItemOffhand());
    return items;
  }

  public static void onEquipmentChange(EntityPlayer player, PlayerData data) {
    InventoryPlayer inv = player.inventory;
    List<ItemStack> items = new ArrayList<>();

    Stats stats = new Stats();
    for (ItemStack item : inv.armorInventory) {
      LootApi.applyItem(item, stats);
    }

    for (ItemStack item : inv.offHandInventory) {
      LootApi.applyItem(item, stats);
    }

    LootApi.applyItem(inv.mainInventory.get(inv.currentItem), stats);

    System.out.println(stats);

    data.stats = stats;
  }
}
