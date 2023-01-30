$("#createProductButton").on("click", e => {
    const product = {
        "name" : "",
        "description" : "",
        "price" : 20.99,
        "quantity" : 2,
        "type" : frontend 
    }
}

$.ajax({
    url: "http://localhost:8080/products",
    type: "POST",
    cors: true,
    contentType: "application/json",
    data: JSON.stringify(product),
    success: console log,
    error: console.error
});
});

