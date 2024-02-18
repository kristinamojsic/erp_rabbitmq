package com.example.parabbitmq.services;

import com.example.parabbitmq.data.ArticleWarehouse;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ArticleWarehouseRepository;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArticleWarehouseRepository articleWarehouseRepository;
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public List<Product> all()
    {
        return productRepository.findAll();
    }
    @GetMapping("/productData/{productId}")
    public String getProductData(@PathVariable("productId") long productId)
    {
        return productService.getProductData(productId);
    }
    @GetMapping("/productState/{productId}")
    public String getProductState(@PathVariable("productId") long productId)
    {
        return productService.getProductState(productId);
    }
    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }
    //ne radi
    @PostMapping("/product/addToWarehouse")
    public void updateState(@RequestBody Map<String, Object> requestBody) throws Exception{
        int supplierId = (int) requestBody.get("supplierId");
        int warehouseId = (int) requestBody.get("warehouseId");
        int quantity = (int) requestBody.get("quantity");
        List<Map<String, Object>> articleList = (List<Map<String, Object>>) requestBody.get("articles");
        List<ArticleWarehouse> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleList) {
            Map<String, Object> productMap = (Map<String, Object>) articleMap.get("product");
            long productId = ((Number) productMap.get("id")).longValue();
            Optional<Product> product = productRepository.findById(productId);
            if(product.isEmpty())
            {
                throw new Exception("non existing product");
            }
            double purchasePrice = ((Number) articleMap.get("purchasePrice")).doubleValue();
            Optional<ArticleWarehouse> articleWarehouse = articleWarehouseRepository.findArticleWarehouse(product.get(),purchasePrice);
            if(articleWarehouse.isEmpty())
            {
                ArticleWarehouse articleWarehouse1 = new ArticleWarehouse(product.get(),purchasePrice);
                articleWarehouseRepository.save(articleWarehouse1);
                articles.add(articleWarehouse1);
            }
            else {
                articles.add(articleWarehouse.get());
            }

        }
        productService.receptionOfProducts(supplierId,warehouseId,quantity,articles);
    }

   /* @PutMapping("product/{id}/updatePrice")
    public Product updatePrice(@RequestBody Map<String, Double> request, @PathVariable long id) throws Exception
    {
        return productService.updateProductPrice(id,request.get("price"));
    }*/


}
