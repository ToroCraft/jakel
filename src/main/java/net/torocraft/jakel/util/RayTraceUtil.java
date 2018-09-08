package net.torocraft.jakel.util;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RayTraceUtil {

  public static RayTraceResult getMouseOverExtended(float dist) {
    Minecraft mc = FMLClientHandler.instance().getClient();
    Entity theRenderViewEntity = mc.getRenderViewEntity();
    AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(theRenderViewEntity.posX - 0.5D, theRenderViewEntity.posY - 0.0D,
        theRenderViewEntity.posZ - 0.5D, theRenderViewEntity.posX + 0.5D, theRenderViewEntity.posY + 1.5D, theRenderViewEntity.posZ + 0.5D);
    RayTraceResult returnMOP = null;
    if (mc.world != null) {
      double var2 = dist;
      returnMOP = theRenderViewEntity.rayTrace(var2, 0);
      double calcDist = var2;
      Vec3d pos = theRenderViewEntity.getPositionEyes(0);
      var2 = calcDist;
      if (returnMOP != null) {
        calcDist = returnMOP.hitVec.distanceTo(pos);
      }

      Vec3d lookVec = theRenderViewEntity.getLook(0);
      Vec3d var8 = pos.addVector(lookVec.x * var2, lookVec.y * var2, lookVec.z * var2);
      Entity pointedEntity = null;
      float var9 = 1.0F;

      List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity,
          theViewBoundingBox.grow(lookVec.x * var2, lookVec.y * var2, lookVec.z * var2).grow(var9, var9, var9));
      double d = calcDist;

      for (Entity entity : list) {
        if (entity.canBeCollidedWith()) {
          float borderSize = entity.getCollisionBorderSize();
          AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2, entity.posY, entity.posZ - entity.width / 2,
              entity.posX + entity.width / 2, entity.posY + entity.height, entity.posZ + entity.width / 2);
          aabb.grow(borderSize, borderSize, borderSize);
          RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

          if (aabb.contains(pos)) {
            if (0.0D < d || d == 0.0D) {
              pointedEntity = entity;
              d = 0.0D;
            }
          } else if (mop0 != null) {
            double d1 = pos.distanceTo(mop0.hitVec);

            if (d1 < d || d == 0.0D) {
              pointedEntity = entity;
              d = d1;
            }
          }
        }
      }

      if (pointedEntity != null && (d < calcDist || returnMOP == null)) {
        returnMOP = new RayTraceResult(pointedEntity);
      }
    }
    return returnMOP;
  }
}
