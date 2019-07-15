package com.ElBasha.mob.app.Retrofit;

public class ProductModel {
    int id;
    String name;
    String battery;
    String myPrice;
    String ram;
    String camera;
    String screen;
    String storage;
    String processor;
    String os;
    String img;
    int monster_of_processor;
    int artists;
    int super_hero;
    int kings_of_selfie;
    int without_stopping;
    int post_id;


    public ProductModel(int id, String name, String battery, String myPrice, String ram, String camera, String screen, String storage, String processor, String os, String img, int monster_of_processor, int artists, int super_hero, int kings_of_selfie, int without_stopping, int post_id) {
        this.id = id;
        this.name = name;
        this.battery = battery;
        this.myPrice = myPrice;
        this.ram = ram;
        this.camera = camera;
        this.screen = screen;
        this.storage = storage;
        this.processor = processor;
        this.os = os;
        this.img = img;
        this.monster_of_processor = monster_of_processor;
        this.artists = artists;
        this.super_hero = super_hero;
        this.kings_of_selfie = kings_of_selfie;
        this.without_stopping = without_stopping;
        this.post_id = post_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(String myPrice) {
        this.myPrice = myPrice;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getMonster_of_processor() {
        return monster_of_processor;
    }

    public void setMonster_of_processor(int monster_of_processor) {
        this.monster_of_processor = monster_of_processor;
    }

    public int getArtists() {
        return artists;
    }

    public void setArtists(int artists) {
        this.artists = artists;
    }

    public int getSuper_hero() {
        return super_hero;
    }

    public void setSuper_hero(int super_hero) {
        this.super_hero = super_hero;
    }

    public int getKings_of_selfie() {
        return kings_of_selfie;
    }

    public void setKings_of_selfie(int kings_of_selfie) {
        this.kings_of_selfie = kings_of_selfie;
    }

    public int getWithout_stopping() {
        return without_stopping;
    }

    public void setWithout_stopping(int without_stopping) {
        this.without_stopping = without_stopping;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
