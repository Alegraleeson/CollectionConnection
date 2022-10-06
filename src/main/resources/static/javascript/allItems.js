const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const searchContainer = document.getElementById("search-container")

const searchButton = document.getElementById("search-button")
const searchInput = document.getElementById("search-input")

let updateItemBtn = document.getElementById('update-item-button')

let itemName = document.getElementById("item-name" )
let itemBrand = document.getElementById("item-brand" )
let itemStockPhoto = document.getElementById("item-stockPhoto")
let itemOriginalPrice = document.getElementById("item-originalPrice")
let itemUserPhoto = document.getElementById("item-userPhoto")
let itemBoughtPrice = document.getElementById("item-boughtPrice")
let itemDateAcquired = document.getElementById("item-dateAcquired")
let itemCurrentValue = document.getElementById("item-currentValue")
let itemCurrentLocation = document.getElementById("item-currentLocation")
let itemKeywords = document.getElementById("item-keywords")
let itemNotes = document.getElementById("item-notes")

const headers = {
    'Content-Type': 'application/json'

}

const baseUrlItems = "http://localhost:8080/api/v1/items/"

async function searchItems(e){
    e.preventDefault()
    console.log("inside search item")
    await fetch(`${baseUrlItems}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => searchByKeywordOrItemName(data))
        .catch(err => console.error(err.message))
}

function searchByKeywordOrItemName(data){
    searchContainer.innerHTML = ''
    data.forEach(item => {
        if(item.name.includes(searchInput.value) || item.keywords.includes(searchInput.value)){

            createNoteCards(item)

        }
    })

}

searchButton.addEventListener('click', searchItems )

async function getItems(){
    console.log("inside getItems")
    await fetch(`${baseUrlItems}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => getAllItems(data))
        .catch(err => console.error(err))
}

function getAllItems(data){
    searchContainer.innerHTML = ''
    data.forEach(item => {

            createNoteCards(item)

    })

}



getItems();



const createNoteCards = (obj) => {
        let itemCard = document.createElement("div")
        itemCard.classList.add("m-2")
        const itemDate = new Date(obj.date_acquired).toLocaleDateString('en-US', { timeZone: 'UTC' })

        itemCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: fit-content;">
                                <div class="card-body d-flex flex-column justify-content-between" style="height: available">                                
                                    <p id="card-name" class="card-text">${obj.name}</p>                                  
                                    <img class="card-img-top" src="${obj.stock_photo}" alt="stock image">
                                    <p class="card-text">Brand: ${obj.brand}</p>
                                    <p class="card-text">Original Price: ${obj.original_price}</p>
                                    <img class="card-img-top" src="${obj.user_photo}" alt="stock image">
                                    <p class="card-text">Amount Paid: ${obj.amount_paid}</p>
                                    <p class="card-text">Date Acquired: ${itemDate}</p>
                                    <p class="card-text">Current Value: ${obj.current_value}</p>
                                    <p class="card-text">Current Location: ${obj.current_location}</p>
                                    <p class="card-text">Keywords: ${obj.keywords}</p>
                                    <p class="card-text">Notes: ${obj.notes}</p>
                                    <div class="d-flex justify-content-between">
                                        <button id="item-delete-button" class="btn btn-danger" onclick="handleDeleteItem(${obj.id})">Delete</button>
                                        <button onclick="getItemById(${obj.id})" type="button" id="item-edit-button" class="btn btn-primary"
                                        data-bs-toggle="modal" data-bs-target="#item-edit-modal">
                                        Edit
                                        </button>
                                    </div>
                                </div>
                            </div>
             `
        searchContainer.append(itemCard);

}

async function getItemById(itemId){
    console.log("getItemById" + itemId)
    await fetch(baseUrlItems + itemId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleItemEdit(itemId){
    console.log("handleItemEdit" + itemId)
    let bodyObj = {
        id: itemId,
        name: itemName.value,
        brand: itemBrand.value,
        stock_photo: itemStockPhoto.value,
        original_price: itemOriginalPrice.value,
        user_photo: itemUserPhoto.value,
        amount_paid: itemBoughtPrice.value,
        date_acquired: itemDateAcquired.value,
        current_location: itemCurrentLocation.value,
        current_value: itemCurrentValue.value,
        keywords: itemKeywords.value,
        notes: itemNotes.value    }

    await fetch(baseUrlItems, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getItems();

}



async function handleDeleteItem(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getItems();
}

const populateModal = (obj) => {
    console.log("populateModal"+ obj)
    console.log("populateModalId"+ obj.id)
    itemName.innerText = ''
    itemBrand.innerText = ''
    itemStockPhoto.innerText = ''
    itemOriginalPrice.innerText = ''
    itemUserPhoto.innerText = ''
    itemBoughtPrice.innerText = ''
    itemDateAcquired.innerText = ''
    itemCurrentValue.innerText = ''
    itemKeywords.innerText = ''
    itemNotes.innerText = ''

    itemName.innerText = obj.name
    itemBrand.innerText = obj.brand
    itemStockPhoto.innerText = obj.stock_photo
    itemOriginalPrice.innerText = obj.original_price
    itemUserPhoto.innerText = obj.user_photo
    itemBoughtPrice.innerText = obj.amount_paid
    itemDateAcquired.innerText = obj.date_acquired
    itemCurrentValue.innerText = obj.current_value
    itemKeywords.innerText = obj.keywords
    itemNotes.innerText = obj.notes
    updateItemBtn.setAttribute('data-item-id', obj.id)
    console.log("populateModalBtn"+ updateItemBtn.getAttribute('data-item-id'))
}

updateItemBtn.addEventListener("click", (e) =>{
    let itemId = e.target.getAttribute('data-item-id')
    console.log("updateItemBtn" + itemId)
    handleItemEdit(itemId);
})

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
