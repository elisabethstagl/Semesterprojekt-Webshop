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

//TOKEN EXPIRED - must test it first
// // Function to retrieve the JWT token from sessionStorage
// function getToken() {
//     return sessionStorage.getItem("jwtToken");
// }

// // Function to check if the user is logged in and the token is not expired
// function isLoggedIn() {
//     const token = sessionStorage.getItem("jwtToken");
//     if (!token) {
//         return false; // No token, user is not logged in
//     }

//     // Parse the JWT token payload to extract expiration date
//     const tokenData = JSON.parse(atob(token.split('.')[1]));
//     const expirationDate = new Date(tokenData.exp * 1000); // Convert to milliseconds

//     // Check if the token is expired
//     const isTokenExpired = expirationDate <= new Date();

//     return !isTokenExpired;
// }

// // Function to update the UI based on login status
// function updateUI() {
//     const logoutContainer = document.getElementById("logoutContainer");
//     if (isLoggedIn()) {
//         // User is logged in and the token is not expired
//         logoutContainer.style.display = "block";
//         loggedInMessage.style.display = "none";
//     } else {
//         // User is not logged in or the token is expired, so hide the logout button container
//         logoutContainer.style.display = "none";
//     }
// }

// // Call the updateUI function when the page loads
// window.addEventListener("DOMContentLoaded", () => {
//     updateUI();
// });

// // Rest of your code, including the logout function
// function logout() {
//     // Remove the JWT token from session storage
//     sessionStorage.removeItem("jwtToken");

//     // Redirect the user to the login page
//     window.location.href = '/Frontend/login.html';
// }


// function logout() {
// // Remove the JWT token from session storage
// sessionStorage.removeItem("jwtToken");

// // Optionally, you can perform other cleanup tasks here
// // For example, redirect the user to the login page
// window.location.href = '/Frontend/login.html';
// }
