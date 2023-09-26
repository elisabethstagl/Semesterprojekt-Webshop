
// Function to retrieve the JWT token from localStorage
        function getToken() {
            return localStorage.getItem("jwtToken");
        }

        // Function to check if the user is logged in
        function isLoggedIn() {
            const token = localStorage.getItem("jwtToken");
            return !!token; // Return true if a token exists, false otherwise
        }

        // Function to update the UI based on login status
        function updateUI() {
            const logoutContainer = document.getElementById("logoutContainer");
            if (isLoggedIn()) {
                // User is logged in, so show the logout button container
                logoutContainer.style.display = "block";
                loggedInMessage.style.display = "none";
            } else {
                // User is not logged in, so hide the logout button container
                logoutContainer.style.display = "none";
            }
        }

        // Call the updateUI function when the page loads
        window.addEventListener("DOMContentLoaded", () => {
            updateUI();
        });

        // Rest of your code, including the logout function
        function logout() {
            // Remove the JWT token from local storage
            localStorage.removeItem("jwtToken");

            // Redirect the user to the login page
            window.location.href = '/Frontend/login.html';
        }

function logout() {
    // Remove the JWT token from local storage
    localStorage.removeItem("jwtToken");

    // Optionally, you can perform other cleanup tasks here
    // For example, redirect the user to the login page
    window.location.href = '/Frontend/login.html';
}
