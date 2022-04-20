package ru.sergeysemenov.webmarketspring.api;


import java.math.BigDecimal;


public class CreateNewProductDto {
    private String title;
    private BigDecimal price;
    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
