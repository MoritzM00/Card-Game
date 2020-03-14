package model;

import model.buildables.building.FirePlace;
import model.buildables.building.Shack;
import model.buildables.rescue.Ballon;
import model.buildables.rescue.HangGlider;
import model.buildables.rescue.SailingRaft;
import model.buildables.rescue.SteamBoat;
import model.buildables.tools.Axe;
import model.buildables.tools.Club;

/**
 * Defines all Subclasses of {@link Buildable}.
 *
 * @author Moritz
 * @version 1.0
 * @see Buildable
 */
public enum BuildableObject {

    /**
     * The Club. Gives a fight bonus of 1
     */
    CLUB("club", new Club()),

    /**
     * The axe. Gives a fight bonus of 2
     */
    AXE("axe", new Axe()),

    /**
     * The fireplace.
     */
    FIREPLACE("fireplace", new FirePlace()),

    /**
     * The Shack. This is a special buildable object.
     * It can save five resources that will not be lost in fights or catastrophes.
     */
    SHACK("shack", new Shack()),

    /**
     * The ballon. When built, the player wins the game without have to roll the dice.
     */
    BALLON("ballon", new Ballon()),

    /**
     * The hang glider.
     */
    HANG_GLIDER("hangglider", new HangGlider()),

    /**
     * The sailing craft.
     */
    SAILING_RAFT("sailingraft", new SailingRaft()),

    /**
     * The steam boat.
     */
    STEAM_BOAT("steamboat", new SteamBoat());

    private final String type;
    private final Buildable accordingBuildable;

    /**
     * Creates a new buildable object.
     *
     * @param type               the type of the object
     * @param accordingBuildable the according instance of this buildable object
     */
    BuildableObject(final String type, final Buildable accordingBuildable) {
        this.type = type;
        this.accordingBuildable = accordingBuildable;
    }

    /**
     * Gets the according buildable (an instance of this class)
     *
     * @return the according buildable
     */
    public Buildable getAccordingBuildable() {
        return accordingBuildable;
    }

    /**
     * Returns the type of this buildable object as a string.
     *
     * @return the type as a string
     */
    public String getTypeAsString() {
        return type;
    }

    /**
     * Gets/Identifies the buildable object by type string
     *
     * @param type the type string
     * @return the buildable object - or null if it cannot be identified
     */
    public static BuildableObject getBuildableObjectByTypeString(String type) {
        if (type != null) {
            for (BuildableObject buildableObject : BuildableObject.values()) {
                if (type.equals(buildableObject.getTypeAsString())) {
                    return buildableObject;
                }
            }
        }
        return null;
    }
}
