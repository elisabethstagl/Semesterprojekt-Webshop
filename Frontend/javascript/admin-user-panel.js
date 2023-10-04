function getAuthHeaders() {
  const token = localStorage.getItem("jwtToken");
  if (!token) {
    alert("Not authenticated!");
    window.location.href = "login.html"; // Redirect to login or other appropriate page
    return {};
  }
  return {
    Authorization: "Bearer " + token,
  };
}

function handleResponse(response) {
  if (!response.ok) {
    throw new Error("Network response was not ok: " + response.statusText);
  }
  return response.json();
}

// Function to create a user card
function createUserCard(user) {
  const card = $('<div class="col-lg-3 col-md-4 col-sm-6 mb-2 mt-3"></div>');
  const cardInner = $(
    '<div class="card mx-auto mb-1" style="background-color:transparent;"></div>'
  );
  const cardHeader = $(
    `<div class="card-header" data-bs-toggle="collapse" href="#collapse${user.id}" role="button" aria-expanded="false"></div>`
  );

  const username = $(`<h5 class="card-title">Username: ${user.username}</h5>`);
  const email = $(`<p class="card-text">Email: ${user.email}</p>`);
  const role = $(`<p class="card-text">Role: ${user.role}</p>`);

  cardHeader.append(username, email, role);

  const cardBody = $(
    '<div class="card-body collapse" id="collapse' + user.id + '"></div>'
  );
  const id = $(`<p class="card-text">Email: ${user.id}</p>`);
  const sex = $(`<p class="card-text">Sex: ${user.sex}</p>`);
  const firstName = $(
    `<p class="card-text">First Name: ${user.firstName} </p>`
  );
  const lastName = $(`<p class="card-text">Last Name: ${user.lastName}</p>`);
  const password = $(`<p class="card-text">Passwort: ${user.password}</p>`);

  const address = $(`<p class="card-text">Adress: ${user.address}</p>`);
  const doornumber = $(
    `<p class="card-text">Door Number: ${user.doornumber}</p>`
  );
  const postalCode = $(
    `<p class="card-text">Postal Code: ${user.postalCode}</p>`
  );
  const city = $(`<p class="card-text">City: ${user.city}</p>`);

  const editButton = $('<button class="btn btn-primary me-3">Edit</button>');
  const deleteButton = $('<button class="btn btn-primary">Delete</button>');

  editButton.on("click", (e) => {
    e.stopPropagation(); // Prevents the card from toggling when the button is clicked
    console.log("Edit button clicked for user:", user.id);

    fetch(`http://localhost:8080/admin/users/${user.id}`, {
    method: "GET",
    headers: getAuthHeaders(),
  })
    .then(handleResponse)
    .then((user) => {

      $("#editUsername").val(user.username);
      $("#editPassword").val(user.password);
      $("#editFirstName").val(user.firstName);
      $("#editLastName").val(user.lastName);
      $("#editEmail").val(user.email);
      $("#editAddress").val(user.address);
      $("#editCity").val(user.city);
      $("#editDoornumber").val(user.doornumber);
      $("#editPostalCode").val(user.postalCode);
      $("#editRole").val(user.role);

    
      // Store user ID for later usage
      $("#editUserModal").data("userId", user.id);
    });
    // Display modal
    const editModal = new bootstrap.Modal(
      document.getElementById("editUserModal")
    );
    editModal.show();
  });

  deleteButton.on("click", (e) => {
    e.stopPropagation(); // Prevents the card from toggling when the button is clicked
    const confirmed = confirm(
      `Are you sure you want to delete user: ${user.username}?`
    );
    if (confirmed) {
      deleteUser(user.id);
    }
  });

  cardBody.append(
    id,
    sex,
    password,
    firstName,
    lastName,
    address,
    doornumber,
    postalCode,
    city,
    editButton,
    deleteButton
  );
  cardInner.append(cardHeader, cardBody);
  card.append(cardInner);

  return card;
}

function deleteUser(userId) {
  fetch(`http://localhost:8080/admin/users/${userId}`, {
    method: "DELETE",
    headers: getAuthHeaders(),
  })
    .then(handleResponse)
    .then(() => {
      console.log("User deleted:", userId);
      
      // Remove the user card from the DOM
      $(`#collapse${userId}`).parent().parent().remove();
    })
    .catch((error) => {
      console.error("Error:", error.message);
      alert("Error deleting user!");
    });
}

function handleResponse(response) {
  if (!response.ok) {
    throw new Error("Network response was not ok: " + response.statusText);
  }
  return response.text().then(text => text ? JSON.parse(text) : {});
}

$("#saveChanges").on("click", () => {
  const userId = $("#editUserModal").data("userId");

  // Collect updated data
  const updatedUser = {
    username: $("#editUsername").val(),
    password: $("#editPassword").val(),
    firstName: $("#editFirstName").val(),
    lastName: $("#editLastName").val(),
    email: $("#editEmail").val(),
    address: $("#editAddress").val(),
    city: $("#editCity").val(),
    doornumber: $("#editDoornumber").val(),
    postalCode: $("#editPostalCode").val(),
    role: $("#editRole").val()
  };
  // Send a PUT request with the updated user data
  fetch(`http://localhost:8080/admin/users/${userId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      ...getAuthHeaders(),
    },
    body: JSON.stringify(updatedUser),
  })
    .then(handleResponse)
    .then((updatedUser) => {
      console.log("User updated:", updatedUser);

      // Find the existing user card using its collapse element
      const userCard = $(`#collapse${updatedUser.id}`).parent();

      // Update the fields inside the card with the new data
      userCard.find(".card-title").text(`Username: ${updatedUser.username}`);
      userCard
        .find('.card-text:contains("Email:")')
        .text(`Email: ${updatedUser.email}`);
      userCard
        .find('.card-text:contains("Role:")')
        .text(`Role: ${updatedUser.role}`);
      userCard
        .find('.card-text:contains("Sex:")')
        .text(`Sex: ${updatedUser.sex}`);
      userCard
        .find('.card-text:contains("First Name:")')
        .text(`First Name: ${updatedUser.firstName}`);
      userCard
        .find('.card-text:contains("Passwort:")')
        .text(`Passwort: ${updatedUser.password}`);
      userCard
        .find('.card-text:contains("Last Name:")')
        .text(`Last Name: ${updatedUser.lastName}`);
      userCard
        .find('.card-text:contains("Adress:")')
        .text(`Adress: ${updatedUser.address}`);
      userCard
        .find('.card-text:contains("Door Number:")')
        .text(`Door Number: ${updatedUser.doornumber}`);
      userCard
        .find('.card-text:contains("Postal Code:")')
        .text(`Postal Code: ${updatedUser.postalCode}`);
      userCard
        .find('.card-text:contains("City:")')
        .text(`City: ${updatedUser.city}`);

      // Hide the modal
      const editModal = bootstrap.Modal.getInstance(
        document.getElementById("editUserModal"));
      editModal.hide();
    })


    .catch((error) => {
      console.error("Error:", error.message);
      alert("Error updating user!");
    });

});

// First, fetch the current user details
fetch("http://localhost:8080/users/current-user", {
  method: "GET",
  headers: getAuthHeaders(),
})
  .then(handleResponse)
  .then((currentUser) => {
    if (currentUser.role === "ROLE_ADMIN") {
      return fetch("http://localhost:8080/admin/users", {
        headers: getAuthHeaders(),
      });
    } else {
      throw new Error("User is not an admin");
    }
  })
  .then(handleResponse)
  .then((users) => {
    const userCardContainer = $("#userCardContainer"); // Add a container in your HTML where user cards will be displayed
    users.forEach((user) => {
      const userCard = createUserCard(user);
      userCardContainer.append(userCard);

      // Once data is populated, display the content
      document.getElementById("userCardContainer").style.display = "flex";
      document.getElementById("admin-header").style.display = "block";
    });
  })
  .catch((error) => {
    console.error("Error:", error.message);
    document.getElementById("error-message").style.display = "block";
  });
