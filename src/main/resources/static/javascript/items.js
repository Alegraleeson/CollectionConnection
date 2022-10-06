
const userId = getCookie("userId")
const collectionId = getCookie("collectionId")

const submitForm = document.getElementById("item-form")
const itemContainer = document.getElementById("item-container")
const name = document.getElementById('itemItemName-input')
const brand = document.getElementById('itemItemBrand-input')
const stockPhoto = document.getElementById('itemItemStockPhoto-input')
const originalPrice = document.getElementById('itemItemOriginalPrice-input')
const userPhoto = document.getElementById('itemUserPhoto-input')
const boughtPrice = document.getElementById('itemBoughtPrice-input')
const dateAcquired = document.getElementById('itemDateAcquired-input')
const currentValue = document.getElementById('itemCurrentValue-input')
const keywords = document.getElementById('itemItemKeywords-input')
const notes = document.getElementById('itemItemNotes-input')
const currentLocation = document.getElementById('itemCurrentLocation-input')
const itemSelect = document.getElementById('itemName-select')


let collectionName = document.getElementById("collection-name")

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


let updateItemBtn = document.getElementById('update-item-button')

const headers = {
    'Content-Type': 'application/json'

}

const baseUrlCollections = "http://localhost:8080/api/v1/collections/"
const baseUrlItems = "http://localhost:8080/api/v1/items/"

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }

}

const handleSubmitItem = async (e) => {
    e.preventDefault()
    let bodyObj = {
        user_id: userId,
        collection_id: collectionId,
        name: name.value,
        brand: brand.value,
        stock_photo: stockPhoto.value,
        original_price: originalPrice.value,
        user_photo: userPhoto.value,
        amount_paid: boughtPrice.value,
        date_acquired: dateAcquired.value,
        current_location: currentLocation.value,
        current_value: currentValue.value,
        keywords: keywords.value,
        notes: notes.value

    }
    await addItem(bodyObj);
    name.value = ''
    brand.value = ''
    stockPhoto.value = ''
    originalPrice.value = ''
    userPhoto.value = ''
    boughtPrice.value = ''
    dateAcquired.value = ''
    currentLocation.value = ''
    currentValue.value = ''
    keywords.value = ''
    notes.value = ''

}

async function addItem(obj) {
    const response = await fetch(`${baseUrlItems}collections/${collectionId}/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers

    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getItems(collectionId);
    }

}


async function getItems(collectionId){
    await fetch(`${baseUrlItems}from/${collectionId}`, {
    method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
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

    return getItems(collectionId);

}



async function handleDeleteItem(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getItems(collectionId);
}


const createNoteCards = (array) => {
    itemContainer.innerHTML = ''

    array.forEach(obj => {
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
                                        <button class="btn btn-danger bold" onclick="handleDeleteItem(${obj.id})">Delete</button>
                                        <button onclick="getItemById(${obj.id})" type="button" class="btn btn-primary bold"
                                        data-bs-toggle="modal" data-bs-target="#item-edit-modal">
                                        Edit
                                        </button>
                                    </div>
                                </div>
                            </div>
             `
        itemContainer.append(itemCard);
    })
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


getItems(collectionId);

submitForm.addEventListener("submit", handleSubmitItem)

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



