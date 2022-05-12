//package ru.sergeysemenov.webmarketspring.cart.test;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.sergeysemenov.webmarketspring.api.CartItemDto;
//import ru.sergeysemenov.webmarketspring.api.ProductDto;
//import ru.sergeysemenov.webmarketspring.cart.services.CartService;
//import ru.sergeysemenov.webmarketspring.cart.utils.Cart;
//import ru.sergeysemenov.webmarketspring.cart.utils.CartItem;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CartControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private CartService cartService;
//
//    @Test
//    public void getItemsInCart() throws Exception{
//        List<CartItemDto> itemDtoList = new ArrayList<>();
//        CartItemDto cartItemDto = new CartItemDto(1L,"Orange", 1, BigDecimal.valueOf(10L), BigDecimal.valueOf(10L));
//        CartItemDto cartItemDto2 = new CartItemDto(1L,"Apple", 2, BigDecimal.valueOf(5L), BigDecimal.valueOf(10L));
//        itemDtoList.add(cartItemDto);
//        itemDtoList.add(cartItemDto2);
//
//        Mockito.doReturn(itemDtoList)
//                .when(cartService)
//                .getListOfCartItemsDto();
//
//        mvc
//                .perform(
//                        get("/api/v1/cart/items")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//
//
//}
