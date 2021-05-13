//window.alert("Testing");

//Getting DOM elements
const loginForm = document.getElementById('form-login');
const userName = document.getElementById("username");
const password = document.getElementById("password");

const errorElement = document.getElementById('error');

loginForm.addEventListener('submit',(e)=>{
    let messages = [];
	//alert("Testing submit");
    let userNameVal = username.value;
    if(userNameVal === '' || userNameVal == null){
        messages.push("User name is required")
    }
    
    //checking password
    let passwordVal = password.value;
    if(passwordVal ==='' || password == null){
        messages.push("Password is required!");
    }

    if(messages.length > 0){
        e.preventDefault();
        errorElement.innerText = messages.join(', ');
        errorElement.style.color="#ff0000";
        errorElement.style.fontWeight="bold";
    }
});

