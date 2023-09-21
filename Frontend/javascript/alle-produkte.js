
// Funktion zum Laden und Anzeigen der Produkte
function loadProducts() {
    $.ajax({
        url: "http://localhost:8080/products",
        type: "GET",
        cors: true,
        success: function (products) {
            // Produkte erfolgreich abgerufen, jetzt anzeigen
            displayProducts(products);
        },
        error: function (error) {
            console.error("Fehler beim Laden der Produkte:", error);
        }
    });
}

// Funktion zum Anzeigen der Produkte auf der Seite
function displayProducts(products) {

    const productsContainer = $('#productsContainer');

    productsContainer.empty(); // Leere den Container, um sicherzustellen, dass keine alten Produkte angezeigt werden

    for (const product of products) {
        // Erstelle eine Karte für jedes Produkt
        const card = createProductCard(product);
        productsContainer.append(card);
    }
}

// Funktion zum Erstellen einer Produktkarte
// Funktion zum Erstellen einer vertikalen Produktkarte
// Funktion zum Erstellen einer vertikalen Produktkarte
function createProductCard(product) {
    // Erstelle die HTML-Struktur für eine vertikale Produktkarte
    const card = $('<div class="col-lg-3 col-md-4 col-sm-6 mb-4"></div>'); // 4 Karten auf Desktops, 3 auf Tablets, 2 auf Handys
    const cardInner = $('<div class="card mx-auto mb-5 card-products" style="background-color:transparent;"></div>');
    const image = $(`<img src="${product.imageURL}" alt="${product.name}" class="img-fluid rounded-start rounded-end">`);
    const cardBody = $('<div class="card-body"></div>');
    const title = $(`<h5 class="card-title">${product.name}</h5>`);
    // const description = $(`<p class="card-text" style="color: rgb(184, 107, 82);">${product.description}</p>`);
    const price = $(`<h5 class="card-price">${product.price}€</h5>`);
    const detailsLink = $(`<a href="produkt-details.html?id=${product.id}" style="color: rgb(184, 107, 82);"><button type="button" class="btn btn-light" style="color: rgb(184, 107, 82); margin-top: 30px;">Details</button></a>`);

    // Füge die HTML-Elemente zur vertikalen Karte hinzu
    cardInner.append(image);
    cardBody.append(title);
    // cardBody.append(description);
    cardBody.append(price);
    cardBody.append(detailsLink);
    cardInner.append(cardBody);
    card.append(cardInner);

    return card;
}

// Rufe die Funktion zum Laden der Produkte auf, wenn die Seite geladen ist
$(document).ready(function () {
    
        loadProducts();
});
