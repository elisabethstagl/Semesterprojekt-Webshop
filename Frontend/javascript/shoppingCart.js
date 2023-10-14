
// // Funktion zum Laden und Anzeigen der Produkte im Warenkorb
// function loadPositions() {
//     const userId = getUserIdFromURL();
//     const shoppingCartUrl = `http://localhost:8080/shoppingCart/${userId}`;

//     $.ajax({
//         url: shoppingCartUrl,
//         type: "GET",
//         cors: true,
//         success: function (positions) {
//             // Produktdaten erfolgreich abgerufen, jetzt anzeigen
//             displayPositions(positions);
//         },
//         error: function (error) {
//             console.error("Fehler beim Laden der Produktdaten:", error);
//         }
//     });
// }

// // Funktion zum Auslesen der User-ID aus der URL
// function getUserIdFromURL() {
//     const params = new URLSearchParams(window.location.search);
//     return params.get("id");
// }

// Funktion zum Laden und Anzeigen der Produktdaten
function loadProductDetails() {
    const productId = getProductIdFromURL();
    const productUrl = `http://localhost:8080/products/${productId}`;

    $.ajax({
        url: productUrl,
        type: "GET",
        cors: true,
        success: function (product) {
            // Produktdaten erfolgreich abgerufen, jetzt anzeigen
            displayProductDetails(product);
        },
        error: function (error) {
            console.error("Fehler beim Laden der Produktdaten:", error);
        }
    });
}

// Funktion zum Anzeigen der Produktdetails in Container
function displayProductDetails(product) {
    const productsContainer = $('#productDetailsContainer');

    productsContainer.empty(); // Leere den Container, um sicherzustellen, dass keine alten Produkte angezeigt werden

    
    // Erstellt HTML Struktur für das Produkt
    const productRow = createProductRow(product);
    productsContainer.append(productRow);
    
}

function createProductRow(product) {
    // Erstelle die HTML-Struktur für eine Zeile mit Produktinformationen
    const row = $('<div class="row  mx-auto" style="max-width: 800px;"></div>');
    const imageCol = $('<div class="col-md-4" style="margin-right: 40px; margin-bottom: 40px;"></div>');
    const image = $(`<img src="${product.imageURL}" alt="${product.name}" class="img-fluid rounded-start rounded-end">`);
    const infoCol = $('<div class="col-md-6"></div>');
    const title = $(`<h1>${product.name}</h1>`);
    const description = $(`<p>${product.description}</p>`);
    const price = $(`<h2>${product.price}€</h2>`);
    const warenkorbButton = $(`<button type="button" class="btn btn-light" style="color: rgb(184, 107, 82); margin-top: 20px; margin-bottom: 40px;">In den Warenkorb</button></button>`);

    // Füge die HTML-Elemente zur Zeile hinzu
    imageCol.append(image);
    infoCol.append(title);
    infoCol.append(description);
    infoCol.append(price);
    infoCol.append(warenkorbButton);
    row.append(imageCol);
    row.append(infoCol);

    return row;
}

// Funktion zum Auslesen der Produkt-ID aus der URL
function getProductIdFromURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Rufe die Funktion zum Laden der Produktdaten auf, wenn die Seite geladen ist
$(document).ready(function () {
    loadProductDetails();
});
