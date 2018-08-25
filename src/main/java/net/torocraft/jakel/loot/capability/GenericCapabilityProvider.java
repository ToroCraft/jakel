package net.torocraft.jakel.loot.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class GenericCapabilityProvider<HANDLER> implements ICapabilitySerializable<NBTBase> {
  private final Capability<HANDLER> capability;

  private final EnumFacing facing;

  private final HANDLER instance;

  public GenericCapabilityProvider(Capability<HANDLER> capability, EnumFacing facing) {
    this(capability, facing, capability.getDefaultInstance());
  }

  public GenericCapabilityProvider(Capability<HANDLER> capability, EnumFacing facing, HANDLER instance) {
    this.capability = capability;
    this.facing = facing;
    this.instance = instance;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    return capability == getCapability();
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if(capability == getCapability()) {
      return (T) instance;
    }
    return null;
  }

  @Override
  public NBTBase serializeNBT() {
    return getCapability().writeNBT(getInstance(), getFacing());
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
    getCapability().readNBT(getInstance(), getFacing(), nbt);
  }

  public Capability<HANDLER> getCapability() {
    return capability;
  }

  public EnumFacing getFacing() {
    return facing;
  }

  public HANDLER getInstance() {
    return instance;
  }
}
