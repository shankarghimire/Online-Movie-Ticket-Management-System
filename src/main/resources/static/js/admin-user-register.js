//window.alert("Testing");

//Getting DOM elements
const registerForm = document.getElementById('registerForm');
const userName = document.getElementById("userName");
const password = document.getElementById("password");
const confirmPassword = document.getElementById('confirmPassword');
const firstName = document.getElementById('firstName');
const lastName = document.getElementById('lastName');
const userRole = document.getElementById('userRole');
const agreement = document.getElementById('agreement');
const enabled = document.getElementById('enabled');

const errorElement = document.getElementById('error');

registerForm.addEventListener('submit',(e)=>{
    let messages = [];
	//alert("Testing submit");
    let userNameVal = userName.value;
    if(userNameVal === '' || userNameVal == null){
        messages.push("User name is required")
    }
    else if(userNameVal.length < 3 ){
        messages.push("User name must be at least 3 characters long")
    }

    //checking password
    let passwordVal = password.value;
    if(passwordVal ==='' || password == null){
        messages.push("Password cannot be empty!");
    }
    else if(passwordVal.length < 3){
        messages.push("Password must be at least 3 charactes long!");
    }

    //Checking confirm password
    let confirmPasswordVal = confirmPassword.value;
    //let result = 0;
    //result =  passwordVal.localCompare(confirmPasswordVal);
    if(passwordVal != confirmPasswordVal){
    	//alert(passwordVal);
    	//alert(confirmPasswordVal);
        messages.push("Confirm password does not match!");
    }

    //Checking first name
    let firstNameVal = firstName.value;
    if(firstNameVal === '' || firstNameVal == null){
        messages.push("First name cannot be empty!");
    } else if(firstNameVal.length < 2){
        messages.push("Frist name must be at least 2 characters long!");
    }

    //checking last name 

    let lastNameVal = lastName.value;
    if(lastNameVal === '' || lastNameVal == null){
        messages.push("Last name cannot be empty!");
    } else if(lastNameVal.length < 2){
        messages.push("Last name must be at least 2 characters long!");
    }

    //Checking the User Role 
    if(userRole.selectedIndex == 0){
        messages.push("You should select at least one User Role for the uesr.");
    }

    //Checking agreement
    if(!agreement.checked ){
        messages.push("You should agree to the Terms and Condigiton!");
    }


    if(messages.length > 0){
        e.preventDefault();
        errorElement.innerText = messages.join(', ');
        errorElement.style.color="#ff0000";
        errorElement.style.fontWeight="bold";
    }
    else{
     	errorElement.style.color="#00ff00";
        errorElement.style.fontWeight="bold";
        errorElement.innerText = "Registration successful";
    }
  
});

