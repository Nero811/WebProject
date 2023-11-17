package com.example.myspring.Controller;

import com.example.myspring.Constant.ProductCategory;
import com.example.myspring.Dto.ProductRequest;
import com.example.myspring.Dto.ProductRequestParameter;
import com.example.myspring.Model.Product;
import com.example.myspring.Service.ProductService;
import com.example.myspring.Util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@CrossOrigin
@Validated
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products")                            // 條件搜尋
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search,
                                                     // 排序
                                                     @RequestParam(defaultValue = "created_date") String order,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     // 分頁
                                                     @RequestParam(defaultValue = "5") @Max(1000) @Min(5) Integer limit,
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        ProductRequestParameter productRequestParameter = new ProductRequestParameter();

        productRequestParameter.setCategory(category);
        productRequestParameter.setString(search);
        productRequestParameter.setOrder(order);
        productRequestParameter.setSort(sort);
        productRequestParameter.setLimit(limit);
        productRequestParameter.setOffset(offset);

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(productRequestParameter));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createNewProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.getProductById(productId));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        Product product = productService.getProductById(productId);
        // 檢查 product是否存在
        if (product != null) {
            // 修改商品的數據
            product = productService.getProductById(productService.updateProduct(productId, productRequest));
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
