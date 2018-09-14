package net.torocraft.jakel.network;

import io.netty.buffer.ByteBuf;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
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

  private SpellData spell;

  public static void init(int packetId) {
    Jakel.NETWORK
        .registerMessage(MessageSyncSpell.Handler.class, MessageSyncSpell.class, packetId,
            Side.CLIENT);
  }

  public MessageSyncSpell() {

  }

  public MessageSyncSpell(SpellData spell) {
    this.spell = spell;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    spell = new SpellData();
    NbtSerializer.read(ByteBufUtils.readTag(buf), spell);
  }

  @Override
  public void toBytes(ByteBuf buf) {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, spell);
    ByteBufUtils.writeTag(buf, c);
  }

  public static class Handler implements IMessageHandler<MessageSyncSpell, IMessage> {

    @Override
    public IMessage onMessage(final MessageSyncSpell message, MessageContext ctx) {
      Minecraft.getMinecraft().addScheduledTask(() -> work(message));
      return null;
    }

    public static void work(MessageSyncSpell message) {
      EntityPlayer player = Jakel.PROXY.getPlayer();
      //CapabilitySpell.getCapability()
    }
  }
}
