
// Funktion zum Laden und Anzeigen der Produkte
function loadUsers() {
    $.ajax({
        url: "http://localhost:8080/users",
        type: "GET",
        cors: true,
        success: function (users) {
            // Produkte erfolgreich abgerufen, jetzt anzeigen
            displayUsers(users);
        },
        error: function (error) {
            console.error("Fehler beim Laden der User:", error);
        }
    });
}

// Funktion zum Anzeigen der Produkte auf der Seite
function displayUsers(users) {

    const usersContainer = $('#usersContainer');

    usersContainer.empty(); // Leere den Container, um sicherzustellen, dass keine alten Produkte angezeigt werden

    for (const user of users) {
        // Erstelle eine Karte für jedes Produkt
        const card = createUserCard(user);
        usersContainer.append(card);
    }
}

// Funktion zum Erstellen einer Produktkarte
// Funktion zum Erstellen einer vertikalen Produktkarte
// Funktion zum Erstellen einer vertikalen Produktkarte
function createUserCard(user) {
        // Erstelle die HTML-Struktur für eine Benutzerkarte
        const card = $('<div class="col-lg-3 col-md-4 col-sm-6 mb-4"></div>'); // 4 Karten auf Desktops, 3 auf Tablets, 2 auf Handys
        const cardInner = $('<div class="card mx-auto mb-5 card-products" style="background-color:transparent;"></div>');
        const cardBody = $('<div class="card-body"></div>');
        
        // Benutzerinformationen in die Karte einfügen
        const username = $(`<h5 class="card-title">Username: ${user.username}</h5>`);
        const fullName = $(`<p class="card-text">Name: ${user.firstName} ${user.lastName}</p>`);
        const sex = $(`<p class="card-text">Geschlecht: ${user.sex}</p>`);
        const address = $(`<p class="card-text">Adresse: ${user.address} ${user.doornumber}, ${user.postalCode} ${user.city}</p>`);
        const email = $(`<p class="card-text">Email: ${user.email}</p>`);
        const role = $(`<p class="card-text">Email: ${user.role}</p>`);
        const profilePicture = $(`<p class="card-text">Email: ${user.profilePicture}</p>`);
        // Fügen Sie hier weitere Benutzerinformationen hinzu, wie Sie möchten.
    
        // Füge die HTML-Elemente zur Benutzerkarte hinzu
        cardBody.append(username);
        cardBody.append(fullName);
        cardBody.append(sex);
        cardBody.append(address);
        cardBody.append(email);
        cardBody.append(role);
        // Fügen Sie weitere Informationen hinzu, wie oben beschrieben.
    
        cardInner.append(cardBody);
        card.append(cardInner);
    
        return card;
}

// Rufe die Funktion zum Laden der Produkte auf, wenn die Seite geladen ist
$(document).ready(function () {
    
        loadUsers();
});
