package com.example.myspring.Service.impl;

import com.example.myspring.Dao.ProductDao;
import com.example.myspring.Dto.ProductRequest;
import com.example.myspring.Dto.ProductRequestParameter;
import com.example.myspring.Model.Product;
import com.example.myspring.Service.ProductService;
import com.example.myspring.Util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Page<Product> getProducts(ProductRequestParameter productRequestParameter) {
        return productDao.getProducts(productRequestParameter);
    }

    @Override
    public Integer createNewProduct(ProductRequest productRequest) {
        return productDao.createNewProduct(productRequest);
    }

    @Override
    public Integer updateProduct(Integer productId, ProductRequest productRequest) {
        return productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
