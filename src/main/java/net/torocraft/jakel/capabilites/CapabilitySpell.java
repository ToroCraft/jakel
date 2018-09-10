package net.torocraft.jakel.capabilites;

import net.minecraft.item.ItemStack;
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
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.nbt.NbtSerializer;
import net.torocraft.jakel.spells.SpellData;

@Mod.EventBusSubscriber
public class CapabilitySpell implements INBTSerializable<NBTTagCompound> {

  private static final ResourceLocation RESOURCE = new ResourceLocation(Jakel.MODID, "CapabilitySpell");

  @CapabilityInject(CapabilitySpell.class)
  private static final Capability<CapabilitySpell> CAPABILITY = null;

  private final SpellData data = new SpellData();

  public static SpellData get(ItemStack stack) {
    CapabilitySpell cap = getCapability(stack);
    return cap == null ? null : cap.data;
  }

  public static CapabilitySpell getCapability(ItemStack stack) {
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
    if (LootApi.isASpell(event.getObject())) {
      event.addCapability(RESOURCE, new Provider());
    }
  }

  public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

    private final CapabilitySpell data;

    public Provider() {
      data = new CapabilitySpell();
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
