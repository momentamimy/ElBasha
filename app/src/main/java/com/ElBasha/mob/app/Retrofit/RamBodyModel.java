package com.ElBasha.mob.app.Retrofit;

public class RamBodyModel {
    String ram,storage,battery,screen;
    String processor,os;


    public String getProcessor() {
        return processor;
    }

    public String getOs() {
        return os;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public RamBodyModel(String ram, String storage, String battery, String screen, String processor, String os)
    {
        this.ram = ram;
        this.storage = storage;
        this.battery = battery;
        this.screen = screen;
        this.processor = processor;
        this.os = os;

    }
}
