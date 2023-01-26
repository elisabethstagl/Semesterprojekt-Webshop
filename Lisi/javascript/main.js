var navbar = document.querySelector("#navbar");
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
  if (this.readyState == 4 && this.status == 200) {
    navbar.innerHTML = this.responseText;
  }
};
xhttp.open("GET", "navbar.html", true);
xhttp.send();

// var pathname = window.location.pathname;
// var activeLink = document.querySelector("#navbar a[href='" + pathname + "']");
// if (activeLink) {
//   activeLink.classList.add("active");
// } active funktioniert nicht, wieso?


var footer = document.querySelector("#footer");
   var xhttp = new XMLHttpRequest();
   xhttp.onreadystatechange = function() {
     if (this.readyState == 4 && this.status == 200) {
       footer.innerHTML = this.responseText;
     }
   };
   xhttp.open("GET", "footer.html", true);
   xhttp.send();
