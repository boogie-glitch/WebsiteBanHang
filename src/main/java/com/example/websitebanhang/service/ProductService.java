package com.example.websitebanhang.service;

import com.example.websitebanhang.model.Product;
import com.example.websitebanhang.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Tạo mới sản phẩm
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Cập nhật sản phẩm
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        product.setActive(productDetails.isActive());
        
        return productRepository.save(product);
    }
    
    // Xóa sản phẩm
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        productRepository.delete(product);
    }
    
    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Lấy sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    // Lấy sản phẩm đang hoạt động
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }
    
    // Lấy sản phẩm còn hàng
    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }
    
    // Cập nhật số lượng sản phẩm
    @Transactional
    public Product updateProductQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        
        product.setQuantity(quantity);
        return productRepository.save(product);
    }
    
    // Vô hiệu hóa sản phẩm
    @Transactional
    public Product deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
        
        product.setActive(false);
        return productRepository.save(product);
    }
}
