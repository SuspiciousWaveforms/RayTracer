package main.java.scene;

import main.java.utilities.Vec;

public class Camera {
    Vec cameraDirection;
    Vec[] cameraRotation;

    public Camera (Vec cameraDirection, Vec[] cameraRotation) {
        this.cameraDirection = cameraDirection;
        this.cameraRotation = cameraRotation;
    }

    public Vec getCameraDirection() {
        return cameraDirection;
    }

    public Vec[] getCameraRotation() {
        return cameraRotation;
    }
}
