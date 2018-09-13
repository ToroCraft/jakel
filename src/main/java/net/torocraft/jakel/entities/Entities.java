package net.torocraft.jakel.entities;

public enum Entities {
  MAGIC_FIREBALL(EntityMagicFireball.class);

  private final Class<?> clazz;

  Entities(Class<?> clazz) {
    this.clazz = clazz;
  }

  public void init() {
    try {
      clazz.getMethod("init").invoke(null);
    }catch (Exception e) {
      System.out.println("Failed to register entity [" + clazz + "].init()");
      e.printStackTrace();
    }
  }

  public void initRender() {
    try {
      clazz.getMethod("initRender").invoke(null);
    }catch (Exception e) {
      System.out.println("Failed to register entity render [" + clazz + "].initRender()");
      e.printStackTrace();
    }
  }

  public static void initAllEntities() {
    for (Entities code : values()) {
      code.init();
    }
  }

  public static void initAllRenders() {
    for (Entities code : values()) {
      code.initRender();
    }
  }

}
