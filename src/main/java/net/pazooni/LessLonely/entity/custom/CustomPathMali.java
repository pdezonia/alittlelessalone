package net.pazooni.LessLonely.entity.custom;

public enum CustomPathMali {
    BLOCKED(-1.0F),
    OPEN(0.1F),
    PREFERRED(0.0F), // the new addition
    WALKABLE(0.1F),
    WALKABLE_DOOR(0.1F),
    TRAPDOOR(0.1F),
    POWDER_SNOW(-1.0F),
    DANGER_POWDER_SNOW(0.2F),
    FENCE(-1.0F),
    LAVA(-1.0F),
    WATER(8.0F),
    WATER_BORDER(8.0F),
    RAIL(0.1F),
    UNPASSABLE_RAIL(-1.0F),
    DANGER_FIRE(8.0F),
    DAMAGE_FIRE(16.0F),
    DANGER_OTHER(8.0F),
    DAMAGE_OTHER(-1.0F),
    DOOR_OPEN(0.1F),
    DOOR_WOOD_CLOSED(-1.0F),
    DOOR_IRON_CLOSED(-1.0F),
    BREACH(4.0F),
    LEAVES(-1.0F),
    STICKY_HONEY(8.0F),
    COCOA(0.1F),
    DAMAGE_CAUTIOUS(0.1F),
    DANGER_TRAPDOOR(0.1F);

    private final float malus;

    private CustomPathMali(float malus) {
        this.malus = malus;
    }

    public float getMalus() { return this.malus; }
}
