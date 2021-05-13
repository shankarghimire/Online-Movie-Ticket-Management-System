//window.alert("Testing");

//Getting DOM elements
const confirmTicketForm = document.getElementById('confirm-ticket-form');
const seatNumber = document.getElementById('seat-number');
const ticketCategory = document.getElementById('ticket-category');
const price = document.getElementById('price');

const errorElement = document.getElementById('error');

confirmTicketForm.addEventListener('submit',(e)=>{
    let messages = [];


    if(seatNumber.selectedIndex == 0){
         messages.push("Please, select one Seat!");
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


ticketCategory.addEventListener('change',()=>{
 
    
    if(ticketCategory.selectedIndex == 1){
    	amt = 15.0 ;
    }
    else if(ticketCategory.selectedIndex == 2){
    	amt = 10.0 ;
    }
    else if(ticketCategory.selectedIndex == 3){
    	amt = 8.0 ;
    }
    else if(ticketCategory.selectedIndex == 4){
    	amt = 5.0  ;
    }
    else if(ticketCategory.selectedIndex == 5){
    	amt = 5.0  ;
    }
    price.value = amt;
    price.readOnly = true;
});



