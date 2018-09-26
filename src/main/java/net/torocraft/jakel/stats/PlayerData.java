package net.torocraft.jakel.stats;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.nbt.NbtField;

public class PlayerData {

  @NbtField
  public int mana;

  @NbtField
  public Stats stats = new Stats();

  public PlayerData() {
    stats.thorns = 1;
    stats.wither = 100;
    mana = 0;
  }



}
