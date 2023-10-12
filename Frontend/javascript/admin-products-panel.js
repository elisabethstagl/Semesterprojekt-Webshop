function getAuthHeaders() {
  const token = sessionStorage.getItem("jwtToken");
  if (!token) {
    alert("Not authenticated!");
    window.location.href = "login.html"; // Redirect to login or other appropriate page
    return {};
  }
  return {
    Authorization: "Bearer " + token,
  };
}

//Produkt hinzufügen
$(document).ready(function () {
  $('#addProductSubmit').click(function (e) {
    e.preventDefault();

    var imageFile = $('#productImage').prop('files')[0];

    var formData = new FormData();

    // Create product object
    var product = {
      name: $('#productName').val(),
      price: $('#productPrice').val(),
      description: $('#productDescription').val(),
      quantity: $('#productQuantity').val(),
      category: $('#productCategory').val()
    };

    // Append stringified product object
    formData.append("product", JSON.stringify(product));

    // Append image file
    formData.append("productImage", imageFile);

    fetch("http://localhost:8080/admin/products/add", {
      method: "POST",
      // No 'Content-Type' header—fetch sets it automatically due to FormData
      headers: getAuthHeaders(),
      body: formData,
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        alert("Product added successfully!");
      })
      .catch((error) => {
        console.error('Error:', error);
        alert("Error adding product!");
      });
  });
});

function handleResponse(response) {
  if (!response.ok) {
    throw new Error("Network response was not ok: " + response.statusText);
  }
  return response.json();
}

// Function to create a product card
function createProductCard(product) {
  const card = $('<div class="col-lg-4 col-md-6 col-sm-6 mt-3"></div>');
  const cardInner = $('<div class="card mx-auto mb-2 " style="background-color:transparent;"></div>');
  const image = $(`<img src="${product.imageURL}" alt="${product.name}" class="img-fluid rounded-start rounded-end mb-3">`);
  const cardBody = $('<div class="card-body"></div>');

  const id = $(`<p class="card-text">ID: ${product.id}</p>`);
  const productName = $(`<p class="card-text">Produktname: ${product.name}</p>`);
  const price = $(`<p class="card-text">Preis: ${product.price} </p>`);
  const description = $(`<p class="card-text">Beschreibung: ${product.description}</p>`);
  const quantity = $(`<p class="card-text">Menge: ${product.quantity}</p>`);
  const category = $(`<p class="card-text">Kategorie: ${product.category}</p>`);
  const imageFile = $(`<p class="card-text">Bild: ${product.imageURL}</p>`);
  const editProductButton = $('<button class="btn btn-primary me-3">Edit</button>');
  const deleteProductButton = $('<button class="btn btn-primary">Delete Product</button>');



  // Produkt bearbeiten
  editProductButton.on("click", (e) => {
    e.stopPropagation(); // Prevents the card from toggling when the button is clicked
    console.log("Edit button clicked for product:", product.id);
  
    fetch(`http://localhost:8080/admin/products/${product.id}`, {
      method: "GET",
      headers: getAuthHeaders(),
    })
      .then(handleResponse)
      .then((productData) => {
        // Filling form fields with product data
        $("#editProductID").val(productData.id);
        $("#editProductName").val(productData.name);
        $("#editProductPrice").val(productData.price);
        $("#editProductDescription").val(productData.description);
        $("#editProductQuantity").val(productData.quantity);
        $("#editProductCategory").val(productData.category);
        //console.log("image url: " + productData.imageURL);
        //$("#editProductURL").val(productData.imageURL);
        $("#editProductURL").data("url", productData.imageURL);
        // Displaying the current product image
        //$("#editProductURL").attr("src", `Frontend/images/${productData.imageURL}`);
  
        // Store product ID for later usage
        $("#editProductModal").data("productId", productData.id);
        console.log($("#editProductModal").data("productId"));
      });
  
    // Display modal
    const editProductModal = new bootstrap.Modal(
      document.getElementById("editProductModal")
    );
    editProductModal.show();
  });

  // Produkt löschen
  deleteProductButton.on("click", (e) => {
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

  //reset form after adding product
  $("#addProductSubmit").on("click", function () {
    $("#addProductForm")[0].reset();
  });

  // closes modal
  $("#modal-close-button").on("click", function () {
    $("#addProductModal").modal("hide");
    $("#addProductForm")[0].reset();
  });

  $("#edit-modal-close-button").on("click", function () {
    $("#editProductModal").modal("hide");
    $("#editProductForm")[0].reset();
  });


  cardInner.append(image);
  cardBody.append(
    id,
    productName,
    price,
    description,
    quantity,
    category,
    imageFile,
    editProductButton,
    deleteProductButton
  );
  cardInner.append(cardBody);
  card.append(cardInner);

  return card;
}

$("#editProductSave").click(function (e) {
  e.preventDefault();
  var productId = $("#editProductModal").data("productId");

  // Check if an image file is selected
  var imageFile = $("#editProductURL").prop("files")[0];
  var imageUrl = $("#editProductURL").data("url");
  
  var formData = new FormData();

  // Create the updated product object
  var updatedProduct = {
    id: $("#editProductID").val(),
    name: $("#editProductName").val(),
    price: $("#editProductPrice").val(),
    description: $("#editProductDescription").val(),
    quantity: $("#editProductQuantity").val(),
    category: $("#editProductCategory").val(),
  };

  // Append the product object to formData
  formData.append("product", JSON.stringify(updatedProduct));

  // Append the image file if it exists
  if (imageFile) {
    formData.append("productImage", imageFile);
  }

  fetch(`http://localhost:8080/admin/products/edit/${productId}`, {
    method: "PUT",
    headers: getAuthHeaders(),
    body: formData,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      alert("Product updated successfully!");
      $("#editProductModal").modal("hide");
      loadAllProducts();
    })
    .catch((error) => {
      console.error("Error:", error);
      alert("Error updating product!");
    });
});

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
