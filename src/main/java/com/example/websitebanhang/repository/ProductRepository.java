package com.example.websitebanhang.repository;

import com.example.websitebanhang.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Tìm sản phẩm theo tên
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Tìm sản phẩm theo danh mục
    List<Product> findByCategory(String category);
    
    // Tìm sản phẩm đang hoạt động
    List<Product> findByActiveTrue();
    
    // Tìm sản phẩm theo danh mục và đang hoạt động
    List<Product> findByCategoryAndActiveTrue(String category);
    
    // Tìm sản phẩm có giá trong khoảng
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    // Tìm sản phẩm còn hàng
    @Query("SELECT p FROM Product p WHERE p.quantity > 0 AND p.active = true")
    List<Product> findAvailableProducts();
}
