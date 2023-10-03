package com.webshop.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.dto.ProductDTO;
import com.webshop.demo.model.Product;
import com.webshop.demo.repository.ProductRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class ProductService {
    
    private ProductRepository productRepos;

    public ProductService(ProductRepository productRepos){
        this.productRepos = productRepos;
    }
    // METHODEN
    
    public void deleteById(Long id) {
        productRepos.deleteById(id);
    }
    

    public Product save(Product product) {
        return productRepos.save(product);
    }

    public Optional<Product> findById(Long id){
        return productRepos.findById(id);
    }

    public List<Product> findAll(){
        return productRepos.findAll();
    }

    public List<Product> findAllByCategory(String category) {
        List<Product> filteredProducts = productRepos.findAllByCategory(category);
        //  = new ArrayList<>();
        // List<Product> allProducts = findAll();

        // for(Product product : allProducts) {
        //     if(product.getCategory().equals(category)){
        //         filteredProducts.add(product);
        //     }
        // }
        return filteredProducts;
    }

    public Product update(Long id, ProductDTO updatedProductDTO) {
        Product product = productRepos.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(updatedProductDTO.getName());
        product.setPrice(updatedProductDTO.getPrice());
        product.setDescription(updatedProductDTO.getDescription());
        product.setQuantity(updatedProductDTO.getQuantity());
        product.setCategory(updatedProductDTO.getCategory());
    
        // Only update imageURL if it is provided in the DTO
        if(updatedProductDTO.getimageURL() != null && !updatedProductDTO.getimageURL().isEmpty()) {
            product.setImageURL(updatedProductDTO.getimageURL());
        }
    
        return productRepos.save(product);
    }
    

}
