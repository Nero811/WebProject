package com.example.myspring.Dao.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.myspring.Constant.ProductCategory;
import com.example.myspring.Dao.ProductDao;
import com.example.myspring.Dto.ProductRequest;
import com.example.myspring.Dto.ProductRequestParameter;
import com.example.myspring.Model.Product;
import com.example.myspring.Repository.ProductRepository;
import com.example.myspring.Util.Pages;
import com.example.myspring.entity.ProductEntity;

@Component
public class ProductDaoImpl implements ProductDao {

//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Product getProductById(Integer productId) {
//        String sql = "SELECT product_id, product_name, category, image_url," +
//                "price, stock, description, created_date, last_modified_date " +
//                "FROM product WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productId", productId);
//
//        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
//        System.out.println(productList.size());
//        if (productList.size() > 0) {
//            return productList.get(0);
//        } else {
//            return null;
//        }
    	System.out.println("222");
    	
    	ProductEntity productEntity = productRepository.findByProductId(productId);
    	
    	System.out.println("111");
    	
    	System.out.println(productEntity);
    	
    	if (productEntity != null) {	
    		return new Product(productEntity.getProductId(), productEntity.getProductName(), ProductCategory.valueOf(productEntity.getCategory()), productEntity.getImageUrl(), productEntity.getPrice(), productEntity.getStock(), productEntity.getDescription(), productEntity.getCreateDate(), productEntity.getLastModifiedDate());
		} else {	
			return null;
		}
    	
    }

    @Override
    public Pages<Product> getProducts(ProductRequestParameter productRequestParameter) {

//        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
//                "FROM product WHERE 1 = 1";
//
//        Map<String, Object> map = new HashMap<>();
//        // 過濾條件
//        sql = filterRequest(sql, map, productRequestParameter);
//
//        // 排序
//        sql = sql + " ORDER BY " + productRequestParameter.getOrder() + " " + productRequestParameter.getSort();
//        // 分頁
//        sql += " LIMIT :limit OFFSET :offset";
//        map.put("limit", productRequestParameter.getLimit());
//        map.put("offset", productRequestParameter.getOffset());

//        Integer total = getTotal(productRequestParameter);
    	
//    	Integer total = (int) productRepository.count();

        Pages<Product> result = new Pages<>();
        result.setLimit(productRequestParameter.getLimit());
        result.setOffset(productRequestParameter.getOffset());
//        result.setTotal(total);
//        result.setResult(namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper()));
//        List<ProductEntity> productEntities = productRepository.findProducts(productRequestParameter.getCategory().name(), productRequestParameter.getString(), productRequestParameter.getOrder(), productRequestParameter.getSort(), productRequestParameter.getLimit(), productRequestParameter.getOffset());
        System.out.println(productRequestParameter.getCategory().name());
//        result.setResultProductEntities(productRepository.findProducts(productRequestParameter.getCategory().name(), productRequestParameter.getString(), productRequestParameter.getOrder(), productRequestParameter.getSort(), productRequestParameter.getLimit(), productRequestParameter.getOffset()));

       if (productRequestParameter.getString() != null) {
    	   
      		if (productRequestParameter.getSort().equals("ASC")) {
    			Pageable pages = PageRequest.of(productRequestParameter.getOffset(), productRequestParameter.getLimit(), Sort.by(productRequestParameter.getOrder()).ascending());
    			System.out.println(productRepository.findByCategoryAndProductNameContaining(productRequestParameter.getCategory().name(), productRequestParameter.getString(), pages));
    			List<ProductEntity> productEntities = productRepository.findByCategoryAndProductNameContaining(productRequestParameter.getCategory().name(), productRequestParameter.getString(), pages);
    			Integer total = productEntities.size();
    			result.setTotal(total);
    			result.setResultProductEntities(productEntities);
    		} else if (productRequestParameter.getSort().equals("DESC")) {
    			Pageable pages = PageRequest.of(productRequestParameter.getOffset(), productRequestParameter.getLimit(), Sort.by(productRequestParameter.getOrder()).descending());
    			List<ProductEntity> productEntities = productRepository.findByCategoryAndProductNameContaining(productRequestParameter.getCategory().name(), productRequestParameter.getString(), pages);
    			Integer total = productEntities.size();
    			result.setTotal(total);
    			result.setResultProductEntities(productEntities);
    		}  
      		
       } else {
    		if (productRequestParameter.getSort().equals("ASC")) {
    			Pageable pages = PageRequest.of(productRequestParameter.getOffset(), productRequestParameter.getLimit(), Sort.by(productRequestParameter.getOrder()).ascending());
    			List<ProductEntity> productEntities = productRepository.findByCategory(productRequestParameter.getCategory().name(), pages);
    			Integer total = productEntities.size();
    			result.setTotal(total);
    			result.setResultProductEntities(productEntities);
    		} else if (productRequestParameter.getSort().equals("DESC")) {
    			Pageable pages = PageRequest.of(productRequestParameter.getOffset(), productRequestParameter.getLimit(), Sort.by(productRequestParameter.getOrder()).descending());
      			List<ProductEntity> productEntities = productRepository.findByCategory(productRequestParameter.getCategory().name(), pages);
    			Integer total = productEntities.size();
    			result.setTotal(total);
    			result.setResultProductEntities(productEntities);
    		}
		}

        return result;
    }

    @Override
    public Integer createNewProduct(ProductRequest productRequest) {
//        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) " +
//                "VALUES(:product_Name, :category, :image_Url, :price, :stock, :description, :createdDate, :lastModifiedDate)";
//
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("product_Name", productRequest.getProductName());
//        map.put("category", productRequest.getCategory().toString());
//        map.put("image_Url", productRequest.getImageUrl());
//        map.put("price", productRequest.getPrice());
//        map.put("stock", productRequest.getStock());
//        map.put("description", productRequest.getDescription());
//
//        Date now = new Date();
//        map.put("createdDate", now);
//        map.put("lastModifiedDate", now);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    	
    	ProductEntity productEntity = new ProductEntity(productRequest.getProductName(),
		    											productRequest.getCategory().toString(),
		    											productRequest.getImageUrl(),
		    											productRequest.getPrice(),
		    											productRequest.getStock(),
		    											productRequest.getDescription());
    	
//    	productRepository.save(productEntity);
    	
    	return productRepository.save(productEntity).getProductId();
//        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateProduct(Integer productId, ProductRequest productRequest) {
//        String sql = "UPDATE product SET product_name = :productName, " +
//                "category = :category, image_url = :imageUrl, price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
//                "WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productId", productId);
//        map.put("productName", productRequest.getProductName());
//        map.put("category", productRequest.getCategory().toString());
//        map.put("imageUrl", productRequest.getImageUrl());
//        map.put("price", productRequest.getPrice());
//        map.put("stock", productRequest.getStock());
//        map.put("description", productRequest.getDescription());
//
//        Date now = new Date();
//
//        map.put("lastModifiedDate", now);
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
//        return productId;
    	
    	ProductEntity productEntity = new ProductEntity(productId,
										    			productRequest.getProductName(),
														productRequest.getCategory().toString(),
														productRequest.getImageUrl(),
														productRequest.getPrice(),
														productRequest.getStock(),
														productRequest.getDescription());
    	
    	return productRepository.save(productEntity).getProductId();
    }

    @Override
    public void updateProductStock(Integer stock, Integer productId) {
//        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("stock", stock);
//        map.put("productId", productId);
//        Date date = new Date();
//        map.put("lastModifiedDate", date);
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    	ProductEntity productEntity = new ProductEntity(productId, stock);
    	
    	productRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(Integer productId) {
//        String sql = "DELETE FROM product WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productId", productId);
//
//        namedParameterJdbcTemplate.update(sql, map);
    	
    	productRepository.deleteByProductId(productId);
    }

//    private Integer getTotal(ProductRequestParameter productRequestParameter) {
//        String sql = "SELECT COUNT(*) FROM product where 1 = 1";
//        Map<String, Object> map = new HashMap<>();
//        sql = filterRequest(sql, map, productRequestParameter);
//        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
//    }
//
//    private String filterRequest(String sql, Map<String, Object> map, ProductRequestParameter productRequestParameter) {
//        if (productRequestParameter.getCategory() != null) {
//            sql += " AND category = :category";
//            map.put("category", productRequestParameter.getCategory().name());
//        }
//
//        if (productRequestParameter.getString() != null) {
//            sql += " AND product_name like :productName";
//            map.put("productName", "%" + productRequestParameter.getString() + "%");
//        }
//        return sql;
//    }
}
