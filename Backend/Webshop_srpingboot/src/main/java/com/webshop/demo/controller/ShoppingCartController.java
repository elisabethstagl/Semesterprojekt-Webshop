package com.webshop.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.service.ShoppingCartService;
import com.webshop.demo.service.UserService;

/*Ein Controller ist eine Schicht in der Anwendungsarchitektur, 
die als Schnittstelle zwischen der Benutzeroberfläche und dem Backend dient. 
Es empfängt Anfragen von der Benutzeroberfläche und entscheidet, wie diese Anfragen verarbeitet werden sollen.
*/
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{userName}")
    public ResponseEntity<ShoppingCart> create (@PathVariable String userName) {
        return new ResponseEntity<ShoppingCart>(shoppingCartService.saveShoppingCart(userName), HttpStatus.OK);
    }

    @GetMapping("/viewCart/{userName}")
    public ResponseEntity<ShoppingCart> viewCartById(@PathVariable("userName") String userName){
    return new ResponseEntity<ShoppingCart>(shoppingCartService.viewCart(userName), HttpStatus.OK);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<ShoppingCart> addProductToCart(@RequestParam("userName") String userName, @RequestParam("productId") Long productId) {
        return new ResponseEntity<ShoppingCart>(shoppingCartService.addProductToCart(userName, productId), HttpStatus.OK);
    }

    @DeleteMapping ("/remove/{productId}")
        public ResponseEntity<ShoppingCart> removeProductFromCart (@RequestParam("userName") String userName, @PathVariable ("productId") Long productId){
            return new ResponseEntity<ShoppingCart>(shoppingCartService.removeProductFromCart(userName, productId), HttpStatus.OK);
        }


    @GetMapping("/position-quantity/{productId}")
    public ResponseEntity<Integer> viewQuantity (@RequestParam("userName") String userName, @PathVariable ("productId") Long productId){
        return new ResponseEntity<Integer>(shoppingCartService.viewQuantity(userName,productId), HttpStatus.OK);
    }

    // @PutMapping("/increase/{cartId}/{productId}")
    // public ResponseEntity<ShoppingCart> increaseQuantity(@PathVariable("cartId") Long cartId,
    //                                                     @PathVariable("productId") Long productId) {
    //     return new ResponseEntity<ShoppingCart>(shoppingCartService.increaseQuantity(cartId, productId), HttpStatus.OK);
    // }

    // @PutMapping("/decreae/{cartId}/{productId}")
    // public ResponseEntity<ShoppingCart> decreaseQuantity(@PathVariable("cartId") Long cartId,
    //                                                     @PathVariable("productId") Long productId) {
    //     return new ResponseEntity<ShoppingCart>(shoppingCartService.decreaseQuantity(cartId, productId), HttpStatus.OK);
    // }

    }






    // public ShoppingCartController(ShoppingCartService shoppingCartService) {
    //     this.shoppingCartService = shoppingCartService;
    // }

    // @ResponseStatus(HttpStatus.CREATED)
    // @PostMapping
    // public ShoppingCart create(@RequestBody ShoppingCart shoppingCart) {
    //     return shoppingCartService.save(shoppingCart);
    // }

    // @GetMapping("/{userId}")
    // public ShoppingCart getShoppingCartByUserId(@PathVariable Long userId) {
    //     return shoppingCartService.findByUserId(userId);
    // }

    // @PostMapping("/{cartId}/addProduct")
    // public ShoppingCart addProductToCart(
    //         @PathVariable Long cartId,
    //         @RequestParam Long productId,
    //         @RequestParam Integer quantity) {
    //     return shoppingCartService.addProductToCart(cartId, productId, quantity);
    // }


