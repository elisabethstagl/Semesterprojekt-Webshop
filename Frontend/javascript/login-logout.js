 // JavaScript code for performing login and displaying error modal
function performLogin() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Client-side validation (add more checks as needed)
    if (!username || !password) {
        alert("Bitte geben Sie Benutzername und Passwort ein.");
        return;
    }

    fetch("http://localhost:8080/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error("Authentifizierung fehlgeschlagen");
            }
        })
        .then(data => {
            // Speichern des JWT-Tokens sicher, z.B. in einem Cookie oder Local Storage
            // In diesem Beispiel speichern wir es aus Vereinfachungsgründen im Local Storage
            sessionStorage.setItem("jwtToken", data.token);

            // Weiterleitung des Benutzers zur Hauptanwendungs- oder Dashboard-Seite
            window.location.href = '/Frontend/index.html';
        })
        .catch(error => {
            console.error("Fehler beim Login:", error);

            // Anzeigen der Fehlermeldung im Modal
            const errorModal = document.getElementById("errorModal");
            const errorMessage = document.getElementById("loginErrorMessage");
            errorMessage.textContent = "Login fehlgeschlagen. Bitte überprüfen Sie Ihre Anmeldedaten.";
            $(errorModal).modal('show');
        });
}


// Function to retrieve the JWT token from session storage
function getToken() {
    return sessionStorage.getItem("jwtToken");
}

// Function to check if the user is logged in
function isLoggedIn() {
    const token = sessionStorage.getItem("jwtToken");
    return !!token; // Return true if a token exists, false otherwise
}

// Function to update the UI based on login status
function updateUI() {
    const logoutContainer = document.getElementById("logout-icon");
    if (isLoggedIn()) {
        // User is logged in, so show the logout button container
        logoutContainer.style.display = "inline-block";
    } else {
        // User is not logged in, so hide the logout button container
        logoutContainer.style.display = "none";
    }
}

// // Call the updateUI function when the page loads
// window.addEventListener("DOMContentLoaded", () => {
//     updateUI();
// });

function logout() {
    // Remove the JWT token from session storage
    sessionStorage.removeItem("jwtToken");

    // Redirect the user to the login page
    window.location.href = '/Frontend/login.html';
}


// TOKEN EXPIRES - aber Zeit wann er abläuft ist falsch und Token wird nicht richtig gelöscht ? nochmal checken
//  // JavaScript code for performing login and displaying error modal
//  function performLogin() {
//     const username = document.getElementById("username").value;
//     const password = document.getElementById("password").value;

//     // Client-side validation (add more checks as needed)
//     if (!username || !password) {
//         alert("Bitte geben Sie Benutzername und Passwort ein.");
//         return;
//     }

//     fetch("http://localhost:8080/users/login", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify({ username, password })
//     })
//         .then(response => {
//             if (response.status === 200) {
//                 return response.json();
//             } else {
//                 throw new Error("Authentifizierung fehlgeschlagen");
//             }
//         })
//         .then(data => {
//             // Speichern des JWT-Tokens sicher, z.B. in einem Cookie oder Local Storage
//             // In diesem Beispiel speichern wir es aus Vereinfachungsgründen im Local Storage
//             sessionStorage.setItem("jwtToken", data.token);

//             // Weiterleitung des Benutzers zur Hauptanwendungs- oder Dashboard-Seite
//             window.location.href = '/Frontend/index.html';
//         })
//         .catch(error => {
//             console.error("Fehler beim Login:", error);

//             // Anzeigen der Fehlermeldung im Modal
//             const errorModal = document.getElementById("errorModal");
//             const errorMessage = document.getElementById("loginErrorMessage");
//             errorMessage.textContent = "Login fehlgeschlagen. Bitte überprüfen Sie Ihre Anmeldedaten.";
//             $(errorModal).modal('show');
//         });
// }


// // Function to retrieve the JWT token from session storage
// function getToken() {
//     return sessionStorage.getItem("jwtToken");
// }

// // Function to check if the user is logged in
// function isLoggedIn() {
//     const token = sessionStorage.getItem("jwtToken");
//     return !!token; // Return true if a token exists, false otherwise
// }

// function isTokenExpired() {
//     const token = getToken();
//     if (!token) {
//         // No token found, consider the user as not logged in
//         return true;
//     }
    
//     const tokenData = JSON.parse(atob(token.split('.')[1]));
//     const tokenExpiration = new Date(tokenData.exp * 1000); // Convert to milliseconds

//     return tokenExpiration <= new Date();
// }

// // Function to update the UI based on login status
// function updateUI() {
//     const logoutContainer = document.getElementById("logout-icon");
//     if (isLoggedIn() && !isTokenExpired()) {
//         // User is logged in, so show the logout button container
//         logoutContainer.style.display = "inline-block";
//     } else {
//         // User is not logged in, so hide the logout button container
//         logoutContainer.style.display = "none";
//     }
// }

// // Periodically check token expiration status (e.g., every minute)
// setInterval(function () {
//     if (isTokenExpired()) {
//         sessionStorage.removeItem("jwtToken"); // Remove the JWT token
//         alert("Your session has expired. Please log in again."); // Display an alert
//         window.location.href = '/Frontend/login.html'; // Redirect to the login page
//     }
// }, 60000); // 60000 milliseconds = 1 minute


// function logout() {
//     // Remove the JWT token from session storage
//     sessionStorage.removeItem("jwtToken");

//     // Redirect the user to the login page
//     window.location.href = '/Frontend/login.html';
// }

