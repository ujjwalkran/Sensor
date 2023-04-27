package com.ujjwal.sensordata;

public class gyro {                             //class to handle gyroscope data
    public float x1, x2, x3, x4;

    public gyro(float x1, float x2, float x3, float x4) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
    }
    //individual methods to get different axes data values
    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getX3() {
        return x3;
    }

    public float getX4() {
        return x4;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public void setX3(float x3) {
        this.x3 = x3;
    }

    public void setX4(float x4) {
        this.x4 = x4;
    }
}
