package ru.sergeysemenov.webmarketspring.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Модель страницы")
public class PageDto {
    @Schema(description = "Номер текущей страницы", required = true, example = "1")
    private int currentPage;
    @Schema (description = "Общее кол-во доступных страниц", required = true, example = "1")
    private int totalPages;
    @Schema (description = "Список продуктов, отображаемых на странице", required = true)
    private List<ProductDto> productDtos;

    public PageDto(){

    }

    public PageDto(int currentPage, int totalPages, List<ProductDto> productDtos) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.productDtos = productDtos;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }
}
