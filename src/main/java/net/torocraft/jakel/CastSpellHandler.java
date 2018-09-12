package net.torocraft.jakel;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.api.LootApi.SpellSlot;
import net.torocraft.jakel.network.MessageCastSpell;
import net.torocraft.jakel.util.RayTraceUtil;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = Jakel.MODID)
public class CastSpellHandler {

  public static void init() {
    MinecraftForge.EVENT_BUS.register(new CastSpellHandler());
  }

  private static SpellSlot determineSpellSlot(int button, boolean ctrl) {
    switch (button) {
      case 0:
        return ctrl ? SpellSlot.MAIN_ALT : SpellSlot.MAIN;
      case 1:
        return ctrl ? SpellSlot.SECONDARY_ALT : SpellSlot.SECONDARY;
      default:
        return SpellSlot.MAIN;
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
  public static void onEvent(MouseEvent event) {

    if (!event.isButtonstate()) {
      return;
    }

    if (!(event.getButton() == 0 || event.getButton() == 1)) {
      return;
    }

    EntityPlayer player = Minecraft.getMinecraft().player;

    if (player == null) {
      return;
    }

    ItemStack stack = player.getHeldItemMainhand();

    if (!LootApi.isMagicalConduit(stack)) {
      System.out.println("Not a magical conduit " + stack);
      //return;
    }

    boolean ctrl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
    SpellSlot slot = determineSpellSlot(event.getButton(), ctrl);

    if (LootApi.getEquippedSpell(player, slot).isEmpty()) {
      System.out.println("No spell equipped to " + slot);
      return;
    }

    event.setCanceled(true);

    RayTraceResult rayTrace = RayTraceUtil.getMouseOverExtended(50);
    Vec3d pos = AttackApi.inFrontOf(player);

    Vec3d look = player.getLookVec();
    MessageCastSpell message;

    if (rayTrace != null) {
      if (rayTrace.entityHit != null) {
        message = new MessageCastSpell(slot, rayTrace.entityHit.getEntityId(), pos, look);
      } else if (RayTraceResult.Type.BLOCK.equals(rayTrace.typeOfHit)) {
        message = new MessageCastSpell(slot, rayTrace.getBlockPos(), pos, look);
      } else {
        message = new MessageCastSpell(slot, pos, look);
      }
    } else {
      message = new MessageCastSpell(slot, pos, look);
    }

    Jakel.NETWORK.sendToServer(message);
  }

}
