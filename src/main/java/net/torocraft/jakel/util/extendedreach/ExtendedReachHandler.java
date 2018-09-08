package net.torocraft.jakel.util.extendedreach;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.api.LootApi.SpellSlot;
import net.torocraft.jakel.util.RayTraceUtil;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = Jakel.MODID)
public class ExtendedReachHandler {

  public static void init() {
    MinecraftForge.EVENT_BUS.register(new ExtendedReachHandler());
  }

  private static SpellSlot determineSpellSlot(int button, boolean ctrl) {
    switch (button) {
      case 0: return ctrl ? SpellSlot.MAIN_ALT : SpellSlot.MAIN;
      case 1: return ctrl ? SpellSlot.SECONDARY_ALT : SpellSlot.SECONDARY;
      default: return SpellSlot.MAIN;
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
      return;
    }

    boolean ctrl =  Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
    SpellSlot slot = determineSpellSlot(event.getButton(), ctrl);

    if (LootApi.getEquippedSpell(player, slot).isEmpty()) {
      System.out.println("No spell equipped to " + slot);
      return;
    }

    event.setCanceled(true);

    RayTraceResult rayTrace = RayTraceUtil.getMouseOverExtended(50);

    // TODO create network packet to send cast spell command, direction, and ray trace result

    // TODO move this out of extended reach and into a handle magical spell method

    if (rayTrace == null) {
      System.out.println("rayTrace FAILED");
      return;
    }

    if (rayTrace.entityHit != null) {
//      Jakel.NETWORK.sendToServer(new MessageExtendedReachInteract(rayTrace.entityHit.getEntityId()));
//
//      /*
//       * send to client
//       */
//      ((IExtendedReach) (stack.getItem())).itemInteractionForEntityExtended(stack, player, (EntityLivingBase) rayTrace.entityHit,
//          EnumHand.MAIN_HAND);
      System.out.println("hit entity");
    } else if (RayTraceResult.Type.BLOCK.equals(rayTrace.typeOfHit)) {
//      Jakel.NETWORK.sendToServer(new MessageExtendedReachInteract(rayTrace.getBlockPos()));
//
//      /*
//       * send to client
//       */
//      Vec3d vec = player.getPositionVector();
//      ((IExtendedReach) (stack.getItem())).onItemUseExtended(player, player.world, rayTrace.getBlockPos(), EnumHand.MAIN_HAND, null,
//          (float) vec.x, (float) vec.y, (float) vec.z);
      System.out.println("hit block");
    }

  }

}
