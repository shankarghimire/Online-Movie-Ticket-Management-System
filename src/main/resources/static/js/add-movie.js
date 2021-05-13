//window.alert("Testing");

//Getting DOM elements
const addMovieForm  = document.getElementById('add-movie-form');
const movieName = document.getElementById("movieName");
const movieGenre = document.getElementById("movieGenre");
const language = document.getElementById('language');
const showTime = document.getElementById('showTime');
const lastName = document.getElementById('lastName');



const errorElement = document.getElementById('error');

addMovieForm.addEventListener('submit',(e)=>{
    let messages = [];
    //alert("Testing submit");
    
    //Checking Movie Name
    let movieNameVal = movieName.value;
    if(movieNameVal === '' || movieNameVal == null){
        messages.push("Movie name is required")
    }


    //Checking Movie Genre
    let movieGenreVal = movieGenre.value;
    if(movieGenreVal ==='' || movieGenreVal == null){
        messages.push("Movie Genre cannot be empty!");
    }


    //Checking Language
    let languageVal = language.value;
    //let result = 0;
    //result =  passwordVal.localCompare(confirmPasswordVal);
    if(languageVal ==='' || language == null){
    	//alert(passwordVal);
    	//alert(confirmPasswordVal);
        messages.push("Language is required!");
    }


    
    if(messages.length > 0){
        e.preventDefault();
        errorElement.innerText = messages.join(', ');
        errorElement.style.color="#ff0000";
        errorElement.style.fontWeight="bold";
    }
   
});

