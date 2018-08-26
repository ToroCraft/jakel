package net.torocraft.jakel.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class NullStorage<T> implements IStorage<T> {
  @Override
  public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side){
    return null;
  }

  @Override
  public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt){
  }
}
