package ru.sergeysemenov.webmarketspring.api;

public class StringDto {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringDto() {
    }

    public StringDto(String value) {
        this.value = value;
    }
}
