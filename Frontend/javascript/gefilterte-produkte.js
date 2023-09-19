function loadFilteredProducts() {
    const urlParams = new URLSearchParams(window.location.search);
    const category = urlParams.get('category');

    $.ajax({
        url: `http://localhost:8080/products/filter?category=` + encodeURIComponent(category),
        type: "GET",
        cors: true,
        success: function (products) {
            // Produkte erfolgreich abgerufen, jetzt anzeigen#
            displayProducts(products);
        },
        error: function (error) {
            console.error("Fehler beim Laden der Produkte:", error);
        }
    });
}

function displayProducts(products) {

    const productsContainer = $('#filteredProductsContainer');

    productsContainer.empty(); // Leere den Container, um sicherzustellen, dass keine alten Produkte angezeigt werden


        for (const product of products) {
            // Erstelle eine Karte für jedes Produkt
            const card = createProductCard(product);
            productsContainer.append(card);
        }

}

// Funktion zum Erstellen einer Produktkarte
function createProductCard(product) {
    // Erstelle die HTML-Struktur für eine Produktkarte
    const card = $('<div class="card mb-5 mx-auto" style="max-width: 740px; background-color:transparent;"></div>');
    const row = $('<div class="row g-0"></div>');
    const imageCol = $('<div class="col-md-4"></div>');
    const image = $(`<img src="${product.imageURL}" alt="${product.name}" class="img-fluid rounded-start rounded-end card-image">`);
    const infoCol = $('<div class="col-md-8"></div>');
    const cardBody = $('<div class="card-body"></div>');
    const title = $(`<h4 class="card-title">${product.name}</h4>`);
    // const description = $(`<p class="card-text" style="color: rgb(184, 107, 82);">${product.description}</p>`);
    const price = $(`<h5 class="card-price">${product.price}€</h5>`);
    const detailsLink = $(`<a href="produkt-details.html?id=${product.id}" style="color: rgb(184, 107, 82);"><button type="button" class="btn btn-light" style="color: rgb(184, 107, 82); margin-top: 30px;">Details</button></a>`);

    // Füge die HTML-Elemente zur Karte hinzu
    imageCol.append(image);
    infoCol.append(cardBody);
    cardBody.append(title);
    // cardBody.append(description);
    cardBody.append(price);
    cardBody.append(detailsLink);
    row.append(imageCol);
    row.append(infoCol);
    card.append(row);

    return card;
}

// Rufe die Funktion zum Laden der gefilterten Produkte auf, wenn die Seite geladen ist
$(document).ready(function () {
    loadFilteredProducts();
});





// function loadFilteredProducts() {
//     const productCategory = getProductsByCategory();

//     $.ajax({
//         url: "http://localhost:8080/products/${product.category}",
//         type: "GET",
//         cors: true,
//         success: function (products) {
//             // Produkte erfolgreich abgerufen, jetzt anzeigen
//             displayProducts(products);
//         },
//         error: function (error) {
//             console.error("Fehler beim Laden der Produkte:", error);
//         }
//     });
// }


// const queryString = window.location.search;
// //console.log(queryString);
// const urlParams = new URLSearchParams(queryString);
// const category = urlParams.get('category')
// //console.log(category);

// let url;
// if(category){
//    url = "http://localhost:8080/products/category/" + category;
  
// } else {
//     url = "http://localhost:8080/products";
// }
// console.log("URL: " + url);








// $(document).ready(function () {
//     // Funktion zum Anzeigen der Produkte auf der Seite
//     function displayProducts(products) {
//         const productsContainer = $('#productsContainer');
//         productsContainer.empty(); // Leere den Container, um sicherzustellen, dass keine alten Produkte angezeigt werden

//         for (const product of products) {
//             // Erstelle eine Karte für jedes Produkt
//             const card = createProductCard(product);
//             productsContainer.append(card);
//         }
//     }

//     // Klick-Ereignisse für Kategorien in der Navigation hinzufügen
//     $("#pflanzen").click(function () {
//         loadProductsByCategory("pflanzen");
//     });

//     $("#toepfe").click(function () {
//         loadProductsByCategory("toepfe");
//     });

//     $("#pflanzenfreunde").click(function () {
//         loadProductsByCategory("pflanzenfreunde");
//     });

//     // Funktion zum Laden und Anzeigen der Produkte basierend auf der ausgewählten Kategorie
//     function loadProductsByCategory(category) {
//         $.ajax({
//             url: "http://localhost:8080/products?category=" + category,
//             type: "GET",
//             cors: true,
//             success: function (products) {
//                 // Produkte erfolgreich abgerufen, jetzt anzeigen
//                 displayProducts(products);
//             },
//             error: function (error) {
//                 console.error("Fehler beim Laden der Produkte:", error);
//             }
//         });
//     }

//     // Rufe die Funktion zum Laden der Produkte auf, wenn die Seite geladen ist
//     loadProducts();
// });
