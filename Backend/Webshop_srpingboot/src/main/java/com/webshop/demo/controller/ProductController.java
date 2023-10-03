package com.webshop.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webshop.demo.model.Product;
import com.webshop.demo.service.ProductService;

import jakarta.validation.Valid;

/*Ein Controller ist eine Schicht in der Anwendungsarchitektur, 
die als Schnittstelle zwischen der Benutzeroberfläche und dem Backend dient. 
Es empfängt Anfragen von der Benutzeroberfläche und entscheidet, wie diese Anfragen verarbeitet werden sollen.
*/
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // READ

    @GetMapping()
    public List<Product> readAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product read(@PathVariable Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/filter")
    public List<Product> getProductsByCategory(@RequestParam(name = "category", required = false) String category) {
    // List<Product> products = productService.findAll();
    List<Product> filteredProducts = productService.findAllByCategory(category);
    // // Filtern der Produkte nach der angegebenen Kategorie
    // List<Product> filteredProducts = products.stream()
    //         .filter(product -> product.getCategory().equalsIgnoreCase(category))
    //         .collect(Collectors.toList());
    System.out.println(filteredProducts);

    if (filteredProducts.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keine Produkte in der Kategorie gefunden: " + category);
    }

    return filteredProducts;
}
    
    // CREATE

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid Product product) {
        return productService.save(product);
    }


    // UPDATE 

    // @PutMapping("/{id}")
    // public Product update(@PathVariable Long id, @RequestBody Product product) {
    //     return productService.update(id, product);
    // }


    // DELETE 

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
