package com.ElBasha.mob.app.Retrofit;

public class RamBodyModel {
    int ram,storage,battery,screen;
    String processor,os;

    public int getStorage() {
        return storage;
    }

    public int getBattery() {
        return battery;
    }

    public int getScreen() {
        return screen;
    }

    public String getProcessor() {
        return processor;
    }

    public String getOs() {
        return os;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public RamBodyModel(int ram,int storage,int battery,int screen,String processor,String os)
    {
        this.ram = ram;
        this.storage = storage;
        this.battery = battery;
        this.screen = screen;
        this.processor = processor;
        this.os = os;

    }
}
