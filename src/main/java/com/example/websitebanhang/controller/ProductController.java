package com.example.websitebanhang.controller;

import com.example.websitebanhang.model.Product;
import com.example.websitebanhang.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products/list";
    }
    
    // Hiển thị form tạo sản phẩm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }
    
    // Xử lý tạo sản phẩm mới
    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "products/form";
        }
        
        productService.createProduct(product);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
        return "redirect:/products";
    }
    
    // Hiển thị chi tiết sản phẩm
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("product", product);
        return "products/detail";
    }
    
    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("product", product);
        return "products/form";
    }
    
    // Xử lý cập nhật sản phẩm
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                               @Valid @ModelAttribute("product") Product product,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "products/form";
        }
        
        productService.updateProduct(id, product);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");
        return "redirect:/products";
    }
    
    // Xóa sản phẩm
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
    
    // Tìm kiếm sản phẩm
    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category,
                                 Model model) {
        List<Product> products;
        
        if (name != null && !name.isEmpty()) {
            products = productService.searchProductsByName(name);
        } else if (category != null && !category.isEmpty()) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }
        
        model.addAttribute("products", products);
        return "products/list";
    }
}
