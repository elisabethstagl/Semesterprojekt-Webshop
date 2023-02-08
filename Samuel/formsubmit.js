document.querySelector("submit").addEventListener("submit", function(event) {
    event.preventDefault();
  
    let formData = new FormData(this);
  
    fetch("/test/form-submit", {
      method: "POST",
      body: formData
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then(data => {
        console.log("Test");
        // Do something with the response data
      })
      .catch(error => {
        console.error("There was a problem with the fetch operation:", error);
      });
  });
  