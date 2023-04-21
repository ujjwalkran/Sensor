package com.ujjwal.sensordata;

public class totaldata {
    public Sensordataas accdata;
    public gyro gyrodata;

    public Sensordataas getAccdata() {
        return accdata;
    }

    public gyro getGyrodata() {
        return gyrodata;
    }

    public void setAccdata(Sensordataas accdata) {
        this.accdata = accdata;
    }

    public void setGyrodata(gyro gyrodata) {
        this.gyrodata = gyrodata;
    }

    public totaldata(Sensordataas accdata, gyro gyrodata) {
        this.accdata = accdata;
        this.gyrodata = gyrodata;
    }
}
