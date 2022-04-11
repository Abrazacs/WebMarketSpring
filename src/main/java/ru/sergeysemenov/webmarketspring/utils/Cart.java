package ru.sergeysemenov.webmarketspring.utils;


import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.dtos.ProductDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class Cart {
    private List<ProductDto> productsList;

    @PostConstruct
    public void init(){
      productsList = new ArrayList<>();
  }

}
