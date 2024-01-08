package com.example.myspring.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myspring.entity.ProductEntity;



@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	// select product by ProductId
	ProductEntity findByProductId(Integer productId);
	
//	@Query(value = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " 
//			+ "FROM product p WHERE "
//			+ "p.category = :category "
//			+ "AND p.product_name LIKE %:productName% "
//			+ "ORDER BY :order :sort "
//			+ "Limit :limit OFFSET :offset", nativeQuery = true)
//	List<ProductEntity> findProducts(@Param("category") String category,
//							   @Param("productName") String productName,
//							   @Param("order") String order,
//							   @Param("sort") String sort,
//							   @Param("limit") Integer limit,
//							   @Param("offset") Integer offset);
	
//	@Query("SELECT p FROM ProductEntity p WHERE p.category = :category AND p.productName LIKE %:productName%")
//	List<ProductEntity> findProducts(@Param("category") String category,
//			   @Param("productName") String productName);
	
	public List<ProductEntity> findByCategoryAndProductNameContaining(String category, String productName, Pageable pages);
		
	public List<ProductEntity> findByCategory(String category, Pageable pages);

	public ProductEntity save(ProductEntity productEntity);
	// update product
//	ProductEntity save(ProductEntity productEntity);
	// update product stock
//	ProductEntity save(Integer stock, Integer productId);
	// delete product
	public void deleteByProductId(Integer productId);
}
