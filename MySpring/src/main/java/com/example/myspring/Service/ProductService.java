package com.example.myspring.Service;

import com.example.myspring.Dto.ProductRequest;
import com.example.myspring.Dto.ProductRequestParameter;
import com.example.myspring.Model.Product;
import com.example.myspring.Util.Page;

public interface ProductService {
    Product getProductById(Integer productId);
    Page<Product> getProducts(ProductRequestParameter productRequestParameter);
    Integer createNewProduct(ProductRequest productRequest);
    Integer updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);
}
