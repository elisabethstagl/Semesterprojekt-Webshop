
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
    const role = $(`<p class="card-text">Rolle: ${user.role}</p>`);
    
    // Überprüfe, ob ein Profilbild vorhanden ist, und füge es zur Karte hinzu, falls verfügbar
    if (user.profilePicture) {
        const profilePicture = $(`<img src="${user.profilePicture}" alt="Profilbild" class="card-img-top">`);
        cardBody.append(profilePicture);
    }
    
    // Fügen Sie die restlichen Informationen zur Karte hinzu
    cardBody.append(username);
    cardBody.append(fullName);
    cardBody.append(sex);
    cardBody.append(address);
    cardBody.append(email);
    cardBody.append(role);
    
    cardInner.append(cardBody);
    card.append(cardInner);

    return card;
}

// Rufe die Funktion zum Laden der Produkte auf, wenn die Seite geladen ist
$(document).ready(function () {
    
        loadUsers();
});


// CHECKEN IF ADMIN
// success: function (users) {
//     // Überprüfe, ob der Benutzer die Rolle "admin" hat
//     if (users && users.length > 0 && users[0].role === 'admin') {
//         // Wenn ja, zeige die Benutzerdaten an
//         displayUsers(users);
//     } else {
//         // Andernfalls, zeige eine Fehlermeldung
//         displayErrorMessage('Nur Admins dürfen diese Seite sehen.');
//     }
// },
// error: function (error) {
//     console.error("Fehler beim Laden der User:", error);
//     // Zeige auch hier eine Fehlermeldung an
//     displayErrorMessage('Fehler beim Laden der Benutzerdaten.');
// }