package com.userLogin.model;

public class Item {

    private Long id;
    private String title;
    private String photoUrl;
    private Double priceUSD;
    private Long availableStock;

    public Item(){};

    public Item(Long id, String title, String photoUrl, Double priceUSD, Long availableStock) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.priceUSD = priceUSD;
        this.availableStock = availableStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
    }
}
