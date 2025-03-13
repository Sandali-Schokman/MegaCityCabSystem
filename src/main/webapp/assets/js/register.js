function validateForm() {
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirm_password").value;
    let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$/;

    if (!passwordRegex.test(password)) {
        alert("Password must be at least 8 characters, include one uppercase, one lowercase, one number, and one special character.");
        return false;
    }
    else if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }


    return true;
}
