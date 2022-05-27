package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.LeverTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;

/**
 * The TAnimation class represents the teleport animations recognized
 * by the frontend through their name.
 */
public class CoordinatesTargetAnimation extends CoordinatesAnimation {

    private final int targetSourceX;
    private final int targetSourceY;
    private final int targetSourceZ;
    private final int targetDestinationX;
    private final int targetDestinationY;
    private final int targetDestinationZ;


    public CoordinatesTargetAnimation(ECoordinatesAnimation animation, LeverTile lever,
                                      TeleportTile sourceTeleport, TeleportTile destinationTeleport) {
        super(animation, lever.getPosX(), lever.getPosY(), lever.getPosZ());
        this.targetSourceX = sourceTeleport.getPosX();
        this.targetSourceY = sourceTeleport.getPosY();
        this.targetSourceZ = sourceTeleport.getPosZ();
        this.targetDestinationX = destinationTeleport.getPosX();
        this.targetDestinationY = destinationTeleport.getPosY();
        this.targetDestinationZ = destinationTeleport.getPosZ();

    }

    @Override
    public String toString() {
        return super.toString() + "(" + targetSourceX + "," + targetSourceY + "," + targetSourceZ + ")" +
        "(" + targetDestinationX + "," + targetDestinationY + "," + targetDestinationZ + ")";
    }

}
