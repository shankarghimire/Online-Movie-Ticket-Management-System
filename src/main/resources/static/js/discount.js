//window.alert("Testing");

//Getting DOM elements
const discountTicketForm = document.getElementById('discount-ticket-form');
const showDate = document.getElementById('showdate');
const movieName = document.getElementById('movie-name');
const theaterName = document.getElementById('theater-name');
const seatNumber = document.getElementById('seat-number');
const showTime = document.getElementById('showtime');
const ticketCategory = document.getElementById('ticket-category');
const price = document.getElementById('price');
const btnConfirmTicket = document.getElementById('btn-confirm-ticket');

const errorElement = document.getElementById('error');

discountTicketForm.addEventListener('submit',(e)=>{
    let messages = [];
    //alert("Testing submit");
    //Testing show-date input

    let showDateVal = showDate.value;

    if(showDateVal === ""){
        messages.push("Show date canot be blank!");
    }

    if(movieName.selectedIndex == 0){
        messages.push("Please, select one Movie!");
    }

    if(theaterName.selectedIndex == 0){
        messages.push("Please, select one Theater name!");
    }

    if(seatNumber.selectedIndex == 0){
        messages.push("Please, select one Seat!");
    }

    let showTimeVal = showTime.value ;
    if(showTimeVal === "" || showTimeVal == null){
        messages.push("Please, fill up the show time!");
    }

    if(ticketCategory.selectedIndex == 0){
        messages.push("Please, select at least one ticket category!");
    }

    let priceVal = price.value;
    if(priceVal === "" || priceVal == null){
        messages.push("Please, fill up price field!");
    }

    if(messages.length > 0){
        e.preventDefault();
        errorElement.innerText = messages.join(', ');
        errorElement.style.color="#ff0000";
        errorElement.style.fontWeight="bold";
    }
});


movieName.addEventListener("change",()=>{

    let selectedMovie = movieName.value;
    console.log(`Movie name ${selectedMovie}`);
    //alert(selectedMovie);
    if(selectedMovie =="Titanic"){
        showTime.value = "8:30";
    }
    else if(selectedMovie =="Avatar"){
        showTime.value = "12:30";
    }
    else if(selectedMovie == "Parba"){
        showTime.value = "5:30";
    }
    showTime.readOnly = true;
});

ticketCategory.addEventListener('change',()=>{
    let category = ticketCategory.value;
    let amt = 0.0;
    let discount = 0.2;
    /*if(category == "General Admission"){
        amt = 15.0 - 15 * discount ;
    }
    else if(category == "Sheridan College Student"){
        amt= 10.0 - 10 * discount;
    }
    else if(category == "PROG 32758 Students"){
        amt = 8.0 - 8 * discount;
    }
    else if(category == "Senior Citizen"){
        amt = 5.0 - 5 * discount;
    }
    else if(category =="Children"){
        amt = 5.0 - 5 * discount;
    }*/
    if(ticketCategory.selectedIndex == 1){
    	amt = 15.0 - 15 * discount ;
    }
    else if(ticketCategory.selectedIndex == 2){
    	amt = 10.0 - 10 * discount ;
    }
    else if(ticketCategory.selectedIndex == 3){
    	amt = 8.0 - 8 * discount ;
    }
    else if(ticketCategory.selectedIndex == 4){
    	amt = 5.0 - 5 * discount ;
    }
    else if(ticketCategory.selectedIndex == 5){
    	amt = 5.0 - 5 * discount ;
    }
    price.value = amt;
    price.readOnly = true;
});



