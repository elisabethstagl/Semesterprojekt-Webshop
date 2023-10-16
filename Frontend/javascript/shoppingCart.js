
function loadShoppingCart() {
    // Make an AJAX request to fetch the user's shopping cart
    const cartId = getUserId(); // You need a function to get the user's cart ID
    const cartUrl = `http://localhost:8080/shoppingCart/view/`;
    
    $.ajax({
        url: cartUrl,
        type: "GET",
        success: function (cart) {
            // Cart data successfully retrieved, now display the products in the cart
            displayCartProducts(cart.products);
        },
        error: function (error) {
            console.error("Fehler beim Laden des Warenkorbs:", error);
        }
    });
}

function displayCartProducts(products) {
    const shoppingCartContainer = $('#shoppingCartContainer');

    // Loop through the products and create product cards
    products.forEach(function (product) {
        const productCard = createProductCard(product);
        shoppingCartContainer.append(productCard);
    });
}

// Function to create a simplified product card for the cart
function createProductCard(product) {
    // Create the HTML structure for a product card in the cart
    const card = $('<div class="card card-products mb-5 mx-auto" style="max-width: 740px; background-color: transparent;"></div>');
    const row = $('<div class="row g-0"></div>');
    const imageCol = $('<div class="col-md-4"></div>');
    const image = $(`<img src="${product.imageURL}" alt="${product.name}" class="img-fluid rounded-start rounded-end card-image">`);
    const infoCol = $('<div class="col-md-8"></div>');
    const cardBody = $('<div class="card-body"></div>');
    const title = $(`<h4 class="card-title">${product.name}</h4>`);
    const quantity = $(`<p class="card-quantity">Quantity: ${product.quantity}</p>`); // Add the quantity
    const price = $(`<h5 class="card-price">${product.price}€</h5>`);
    
    // Add the elements to the card
    imageCol.append(image);
    infoCol.append(title);
    infoCol.append(quantity);
    infoCol.append(price);
    row.append(imageCol);
    row.append(infoCol);
    cardBody.append(row);
    card.append(cardBody);

    return card;
}

// Function to get the user's cart ID
function getUserId() {
    const token = sessionStorage.getItem("jwtToken");
    if (!token) {
        alert("User is not authenticated. Please log in.");
        window.location.href = "login.html"; // Redirect to the login page        
        return null;
    }

    // Decode the JWT token to get user data
    const tokenPayload = token.split(".")[1];
    const decodedPayload = atob(tokenPayload);
    const userData = JSON.parse(decodedPayload);

    // Extract the userId from the decoded user data
    const userId = userData.userId;
    const username = userData.username;

    return userId, username;
}

// Load the shopping cart when the page is ready
$(document).ready(function () {
    loadShoppingCart();
});


// function getAuthHeaders() {
//     const token = sessionStorage.getItem("jwtToken");
//     if (!token) {
//     alert("Not authenticated!");
//       window.location.href = "login.html"; // Redirect to login or other appropriate page
//     return {};
//     }
//     return {
//     Authorization: "Bearer " + token,
//     };
// }



// // Function to fetch and display shopping cart items
// function fetchShoppingCart(userId) {
//     fetch(`/shoppingCart/${userId}`, {
//         headers: getAuthHeaders(), // Include the Authorization header
//     })
//         .then(handleResponse)
//         .then(data => {
//             const cartItems = data.cartItems; // Assuming "cartItems" is the key for items in the shopping cart

//             const cartItemsContainer = document.getElementById("cartItems");

//             // Loop through the cart items and create product cards
//             cartItems.forEach(item => {
//                 const productCard = createProductCard(item);
//                 cartItemsContainer.appendChild(productCard);
//             });
//         })
//         .catch(error => console.error("Error fetching shopping cart: " + error));
// }

// // Function to create a product card
// function createProductCard(product) {
//     const card = document.createElement("div");
//     card.classList.add("col-lg-4", "col-md-6", "col-sm-6", "mt-3");

//     const cardInner = document.createElement("div");
//     cardInner.classList.add("card", "mx-auto", "mb-2");
//     cardInner.style.backgroundColor = "transparent";

//     const image = document.createElement("img");
//     image.src = product.imageURL;
//     image.alt = product.name;
//     image.classList.add("img-fluid", "rounded-start", "rounded-end", "mb-3");

//     const cardBody = document.createElement("div");
//     cardBody.classList.add("card-body");

//     const id = document.createElement("p");
//     id.classList.add("card-text");
//     id.textContent = `ID: ${product.id}`;

//     const productName = document.createElement("p");
//     productName.classList.add("card-text");
//     productName.textContent = `Produktname: ${product.name}`;

//     const price = document.createElement("p");
//     price.classList.add("card-text");
//     price.textContent = `Preis: ${product.price}`;

//     const quantity = document.createElement("p");
//     quantity.classList.add("card-text");
//     quantity.textContent = `Menge: ${product.quantity}`;

//     const imageFile = document.createElement("p");
//     imageFile.classList.add("card-text");
//     imageFile.textContent = `Bild: ${product.imageURL}`;

//     // Append elements to the card
//     cardBody.appendChild(id);
//     cardBody.appendChild(productName);
//     cardBody.appendChild(price);
//     cardBody.appendChild(quantity);
//     cardBody.appendChild(imageFile);

//     cardInner.appendChild(image);
//     cardInner.appendChild(cardBody);

//     card.appendChild(cardInner);

//     return card;
// }

// // Function to retrieve the user ID from the token in session storage
// function getUserIdFromToken() {
//     const token = sessionStorage.getItem("jwtToken");
//     if (!token) {
//         alert("Not authenticated!");
//         window.location.href = "login.html"; // Redirect to login or other appropriate page
//         return null;
//     }
    
//     // Decode the token (assuming it's a JWT) to get the user ID
//     const tokenData = JSON.parse(atob(token.split('.')[1])); // Assuming the user ID is in the token payload

//     return tokenData.userId; // Replace 'userId' with the actual key used in the token
// }

// // Replace '123' with the actual user ID fetched from the token
// const userId = getUserIdFromToken();
// if (userId) {
//     fetchShoppingCart(userId);
// }