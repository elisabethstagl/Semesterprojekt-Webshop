
$(document).ready(function(){
    $("#navbar").load("../Frontend/navbar.html .navbar", function() {
        $.getScript("javascript/navbar-admin-user.js",function(){
            checkAuthentication(); activateNavbarLink();
        },
        $.getScript("javascript/login-logout.js", function() {
            updateUI(); 
        })
        );
    });
    $("#footer").load("../Frontend/footer.html #footerLoad");
});

