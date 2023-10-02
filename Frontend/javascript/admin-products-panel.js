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

// Function to create a product card
function createProductCard(product) {
  const card = $('<div class="col-lg-4 col-md-6 col-sm-6 mt-3"></div>');
  const cardInner = $(
    '<div class="card mx-auto mb-2" style="background-color:transparent;"></div>'
  );
  const cardBody = $('<div class="card-body"></div>');

  const id = $(`<p class="card-text">ID: ${product.id}</p>`);
  const productName = $(
    `<p class="card-text">Produktname: ${product.name}</p>`
  );
  const price = $(`<p class="card-text">Preis: ${product.price} </p>`);
  const description = $(
    `<p class="card-text">Beschreibung: ${product.description}</p>`
  );
  const quantity = $(`<p class="card-text">Menge: ${product.quantity}</p>`);
  const category = $(`<p class="card-text">Kategorie: ${product.category}</p>`);
  const imgElement = $("<img class='card-img-top' alt='Product Image'>");
  imgElement.attr("src", "data:image/jpeg;base64," + product.product_img);
  cardInner.prepend(imgElement); // Display it at the top of your product card.

  const editButton = $('<button class="btn btn-primary me-3">Edit</button>');
  const deleteButton = $('<button class="btn btn-primary">Delete</button>');

  editButton.on("click", (e) => {
    e.stopPropagation();

    // Populate the modal fields with current product details
    $("#productName").val(product.name);
    $("#productPrice").val(product.price);
    $("#productDescription").val(product.description);
    $("#productQuantity").val(product.quantity);
    $("#productCategory").val(product.category);
    // Handle image URL separately

    // Show the modal and modify the submit button's event to update the product
    $("#addProductModal").modal("show");
    $("#addProductSubmit")
      .off("click")
      .on("click", function () {
        const updatedProduct = {
          name: $("#productName").val(),
          price: $("#productPrice").val(),
          description: $("#productDescription").val(),
          quantity: $("#productQuantity").val(),
          category: $("#productCategory").val(),
          // Handle image URL separately
        };

        const productImageFile = document.getElementById("productURL").files[0];
        const formData = new FormData();
        formData.append("productImage", productImageFile);
        formData.append("product", JSON.stringify(updatedProduct));

        fetch(`http://localhost:8080/admin/products/${product.id}`, {
          method: "PUT",
          headers: { ...getAuthHeaders() },
          body: formData,
        })
          .then(handleResponse)
          .then((response) => {
            loadAllProducts();
          })

          .catch((error) => {
            console.error("Error updating the product:", error);
          });
      });
  });

  deleteButton.on("click", (e) => {
    e.stopPropagation();

    if (confirm("Are you sure you want to delete this product?")) {
      fetch(`http://localhost:8080/admin/products/${product.id}`, {
        method: "DELETE",
        headers: getAuthHeaders(),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Error deleting the product");
          }
          // Remove the card from the page or reload the products
          card.remove();
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  });

  // Function to display the modal when the button is clicked
  $("#new-product-button").on("click", function () {
    $("#addProductModal").modal("show");
  });

  // closes modal
  $("#modal-close-button").on("click", function () {
    $("#addProductModal").modal("hide");
    $("#addProductForm")[0].reset();
  });

  cardBody.append(
    id,
    productName,
    price,
    description,
    quantity,
    category,
    editButton,
    deleteButton
  );
  cardInner.append(cardBody);
  card.append(cardInner);

  return card;
}

function loadAllProducts() {
  $("#productsCardContainer").empty(); // Empty the container

  fetch("http://localhost:8080/users/current-user", {
    method: "GET",
    headers: getAuthHeaders(),
  })
    .then(handleResponse)
    .then((currentUser) => {
      if (currentUser.role === "ROLE_ADMIN") {
        return fetch("http://localhost:8080/admin/products", {
          headers: getAuthHeaders(),
        });
      } else {
        throw new Error("User is not an admin");
      }
    })
    .then(handleResponse)
    .then((products) => {
      const productCardContainer = $("#productsCardContainer");
      products.forEach((product) => {
        const productCard = createProductCard(product);
        productCardContainer.append(productCard);
      });

      // Once data is populated, display the content
      document.getElementById("productsCardContainer").style.display = "flex";
      document.getElementById("admin-header").style.display = "block";
      document.getElementById("new-product-button").style.display = "block";
    })
    .catch((error) => {
      console.error("Error:", error.message);
      document.getElementById("error-message").style.display = "block";
    });
}

// Load all products when the page is accessed
loadAllProducts();
