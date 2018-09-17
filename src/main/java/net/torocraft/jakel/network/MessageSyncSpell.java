package net.torocraft.jakel.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.nbt.NbtSerializer;
import net.torocraft.jakel.spells.SpellData;

public class MessageSyncSpell implements IMessage {

  private NBTTagCompound spellNbt;

  public static void init(int packetId) {
    Jakel.NETWORK
        .registerMessage(MessageSyncSpell.Handler.class, MessageSyncSpell.class, packetId,
            Side.CLIENT);
  }

  public MessageSyncSpell() {

  }

  public MessageSyncSpell(SpellData spell) {
    spellNbt = new NBTTagCompound();
    NbtSerializer.write(spellNbt, spell);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    spellNbt = ByteBufUtils.readTag(buf);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeTag(buf, spellNbt);
  }

  public static class Handler implements IMessageHandler<MessageSyncSpell, IMessage> {

    @Override
    public IMessage onMessage(final MessageSyncSpell message, MessageContext ctx) {
      Minecraft.getMinecraft().addScheduledTask(() -> work(message));
      return null;
    }

    public static void work(MessageSyncSpell message) {
      EntityPlayer player = Jakel.PROXY.getPlayer();
      ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
      CapabilitySpell.getCapability(stack).deserializeNBT(message.spellNbt);
    }
  }
}
