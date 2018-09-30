package net.torocraft.jakel.capabilites;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.loot.ItemData;
import net.torocraft.jakel.nbt.NbtSerializer;

@Mod.EventBusSubscriber
public class CapabilityItemData implements INBTSerializable<NBTTagCompound> {

  private static final ResourceLocation RESOURCE = new ResourceLocation(Jakel.MODID, "CapabilityItemData");

  @CapabilityInject(CapabilityItemData.class)
  private static final Capability<CapabilityItemData> CAPABILITY = null;

  private final ItemData data = new ItemData();

  public static ItemData get(ItemStack stack) {
    CapabilityItemData cap = getCapability(stack);
    return cap == null ? null : cap.data;
  }

  public static CapabilityItemData getCapability(ItemStack stack) {
    return stack.getCapability(CAPABILITY, null);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, data);
    return c;
  }

  @Override
  public void deserializeNBT(NBTTagCompound c) {
    NbtSerializer.read(c, data);
  }

  @SubscribeEvent
  public static void onCapabilityLoad(AttachCapabilitiesEvent<ItemStack> event) {
    if (isMagicCapableItem(event.getObject().getItem())) {
      event.addCapability(RESOURCE, new Provider());
    }
  }

  public static boolean isMagicCapableItem(Item item) {
    // TODO add whitelist config
    if (item instanceof ItemSword) {
      return true;
    }

    if (item instanceof ItemArmor) {
      return true;
    }

    if (item instanceof ItemShield) {
      return true;
    }

    return false;
  }

  public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

    private final CapabilityItemData data;

    public Provider() {
      data = new CapabilityItemData();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      if (capability == CAPABILITY) {
        return CAPABILITY.cast(data);
      }
      return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
      return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
      data.deserializeNBT(nbt);
    }

  }

}
