package com.example.myspring.Util;

import java.util.List;

import com.example.myspring.entity.ProductEntity;

public class Pages<T> {
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> result;
    private List<ProductEntity> resultProductEntities;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

	public List<ProductEntity> getResultProductEntities() {
		return resultProductEntities;
	}

	public void setResultProductEntities(List<ProductEntity> resultProductEntities) {
		this.resultProductEntities = resultProductEntities;
	}
    
    
}
