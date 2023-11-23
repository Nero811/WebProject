package com.example.myspring.Dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateOrderRequest {
    @NotEmpty
    private List<OrderRequest> buyItemList;

    public List<OrderRequest> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<OrderRequest> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
