package com.example.parabbitmq.services;

import com.example.parabbitmq.data.ArticleWarehouse;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;
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

    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }
    //ne radi
    @PostMapping("/product/addToWarehouse")
    public void updateState(@RequestBody Map<String,Integer> supplierId, @RequestBody Map<String,Integer> warehouseId, @RequestBody  Map<String,Integer> quantity, @RequestBody List<ArticleWarehouse> articles)
    {
        productService.receptionOfProducts(supplierId.get("supplierId"),warehouseId.get("warehouseId"),quantity.get("quantity"),articles);
    }

   /* @PutMapping("product/{id}/updatePrice")
    public Product updatePrice(@RequestBody Map<String, Double> request, @PathVariable long id) throws Exception
    {
        return productService.updateProductPrice(id,request.get("price"));
    }*/


}
