package net.torocraft.jakel.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.api.LootApi.SpellSlot;

public class MessageCastSpell implements IMessage {

  public static final int HIT_TYPE_NONE = 0;
  public static final int HIT_TYPE_ENTITY = 1;
  public static final int HIT_TYPE_BLOCK = 2;

  public SpellSlot slot;
  public int hitType;
  public int entityId;
  public BlockPos block;
  public Vec3d look;
  public Vec3d pos;


  public static void init(int packetId) {
    Jakel.NETWORK.registerMessage(MessageCastSpell.Handler.class, MessageCastSpell.class, packetId, Side.SERVER);
  }

  public MessageCastSpell() {

  }

  public MessageCastSpell(SpellSlot slot, Vec3d pos, Vec3d look) {
    if (pos == null) {
      throw new NullPointerException("pos is null");
    }
    if (look == null) {
      throw new NullPointerException("look is null");
    }
    if (slot == null) {
      throw new NullPointerException("slot is null");
    }
    this.hitType = HIT_TYPE_NONE;
    this.pos = pos;
    this.look = look;
    this.slot = slot;
  }

  public MessageCastSpell(SpellSlot slot, int parEntityId, Vec3d pos, Vec3d look) {
    this(slot, pos, look);
    this.hitType = HIT_TYPE_ENTITY;
    this.entityId = parEntityId;
  }

  public MessageCastSpell(SpellSlot slot, BlockPos block, Vec3d pos, Vec3d look) {
    this(slot, pos, look);
    if (block == null) {
      throw new NullPointerException("block is null");
    }
    this.hitType = HIT_TYPE_BLOCK;
    this.block = block;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    slot = SpellSlot.values()[buf.readInt()];
    hitType = buf.readInt();
    pos = readVec3d(buf);
    look = readVec3d(buf);
    switch (hitType) {
      case 1:
        entityId = buf.readInt();
        break;
      case 2:
        block = BlockPos.fromLong(buf.readLong());
        break;
    }
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(slot.ordinal());
    buf.writeInt(hitType);
    writeVec3d(buf, pos);
    writeVec3d(buf, look);
    switch (hitType) {
      case 1:
        buf.writeInt(entityId);
        break;
      case 2:
        buf.writeLong(block.toLong());
        break;
    }
  }

  private static Vec3d readVec3d(ByteBuf buf) {
    return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
  }

  private static void writeVec3d(ByteBuf buf, Vec3d vec) {
    if (vec == null) {
      buf.writeDouble(0);
      buf.writeDouble(0);
      buf.writeDouble(0);
    } else {
      buf.writeDouble(vec.x);
      buf.writeDouble(vec.y);
      buf.writeDouble(vec.z);
    }
  }

  public static class Handler implements IMessageHandler<MessageCastSpell, IMessage> {

    @Override
    public IMessage onMessage(final MessageCastSpell message, MessageContext ctx) {
      final EntityPlayerMP payer = ctx.getServerHandler().player;
      payer.getServerWorld().addScheduledTask(new MessageCastSpellHandlerWorker(payer, message));
      return null;
    }
  }
}