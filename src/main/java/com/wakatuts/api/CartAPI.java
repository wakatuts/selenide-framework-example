package com.wakatuts.api;

import com.wakatuts.constants.EndpointName;
import com.wakatuts.data.api.WrappedResponse;
import io.qameta.allure.Step;

import java.util.Map;
import java.util.UUID;

public class CartAPI extends BaseAPI {

    @Step("Add items to cart thru API")
    public void addItemsToCart(int ... itemIds) {
        for(int itemId : itemIds) {
            given(EndpointName.ADD_TO_CART, Map.of("id", UUID.randomUUID(), "prod_id", itemId))
                    .post();
        }
    }

    @Step("View cart thru API")
    public WrappedResponse viewCart() {
        return wrapResponse(EndpointName.VIEW_CART, given(EndpointName.VIEW_CART).post());
    }

    @Step("Delete ids in cart thru API")
    public void deleteItemsInCart(String ... uuids) {
        for(String uuid : uuids) {
            given(EndpointName.DELETE_ITEM, Map.of("id", uuid)).post();
        }
    }

}
