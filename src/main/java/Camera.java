package main.java;

import main.java.utilities.Vec;

class Camera {
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
