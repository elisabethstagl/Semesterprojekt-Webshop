// check if admin, user or none and show different icons
const authenticationCheckURL = "http://localhost:8080/users/current-user";

function getAuthHeaders() {
    const token = localStorage.getItem("jwtToken");
    if (token) {
        return {
            Authorization: "Bearer " + token,
        };
    } else {
        return null;
    }
}

// Diese Funktion überprüft, ob der Benutzer eingeloggt ist
function checkAuthentication() {
    const personIcon = document.getElementById("person-icon");
    const adminUserIcon = document.getElementById("admin-user-icon"); // Wähle das Icon-Element aus
    const adminProductIcon = document.getElementById("admin-product-icon");
    const cartIcon = document.getElementById("cart-icon");

    let authHeaders = getAuthHeaders();
    if (authHeaders == null) {
        // Wenn der Benutzer nicht eingeloggt ist:
        personIcon.style.display = "inline-block";
        adminUserIcon.style.display = "none";
        adminProductIcon.style.display = "none";
        cartIcon.style.display = "none";
        return;
    }

fetch(authenticationCheckURL, {
    method: "GET",
    headers: authHeaders,
})
.then((response) => {
    if (response.status === 200) {
        return response.json();
    }
})
.then((currentUser) => {
    if (currentUser) {
      // Wenn der Benutzer eingeloggt ist - ADMIN
        if (currentUser.role === "ROLE_ADMIN") {
        personIcon.style.display = "none";
        adminUserIcon.style.display = "inline-block";
        adminProductIcon.style.display = "inline-block";
        cartIcon.style.display = "inline-block";
        }
        
        if (currentUser.role === "ROLE_USER") {
      // Wenn der Benutzer eingeloggt ist - USER
        personIcon.style.display = "none";
        adminUserIcon.style.display = "none";
        adminProductIcon.style.display = "none";
        cartIcon.style.display = "inline-block";
    }
    } else {
        // Wenn man nicht eingeloggt ist
        personIcon.style.display = "inline-block";
        adminUserIcon.style.display = "none";
        adminProductIcon.style.display = "none";
        cartIcon.style.display = "none";
    }
});
}

// Rufe die Funktion zur Überprüfung der Authentifizierung auf
checkAuthentication();

//adds an "active" class to the link that is clicked
function activateNavbarLink() {
    // Find all navbar links
    var navLinks = document.querySelectorAll(".navbar-nav a.nav-link");

    // Loop through each link and compare its href with the current window location
    navLinks.forEach(function (link) {
        if (link.href === window.location.href) {
            // If there's a match, add the 'active' class to the link
            link.classList.add("active");
        }
    });
}

// Call the function when the DOM is ready
document.addEventListener("DOMContentLoaded", activateNavbarLink);

