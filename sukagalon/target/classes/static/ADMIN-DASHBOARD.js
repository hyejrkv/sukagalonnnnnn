// Assuming the user's email is stored in a variable, you can replace this with your actual login system logic
var userEmail = "admin@sukagalon.com"; // This should come from the logged-in user's session

function toggleProfileMenu() {
    var dropdown = document.getElementById("profileDropdown");
    dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";

    // Set the user's email dynamically
    document.getElementById("userEmail").textContent = userEmail;
}

function logout() {
    alert("Logging out...");
    window.location.href = "login.html"; // Replace with your actual logout URL
}
