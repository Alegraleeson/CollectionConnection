const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const submitForm = document.getElementById("item-form")
const itemContainer = document.getElementById("item-container")
// const name = document.getElementById('collectionName-input')
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
const currentLocation = document.getElementById('itemCurrentValue-input')
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
    const response = await fetch(`${baseUrlItems}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers

    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getItems(userId);
    }
}


async function getItems(collectionId){
    await fetch(`${baseUrlItems}collection/${collectionId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
}


async function getItemById(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleItemEdit(itemId){
    let bodyObj = {
        id: itemId,
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
        notes: notes.value    }

    await fetch(baseUrlItems, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getItems(collecctionId);
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



async function handleDeleteItem(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getItems(userId);
}


const createNoteCards = (array) => {
    itemContainer.innerHTML = ''
    array.forEach(obj => {
        let itemCard = document.createElement("div")
        itemCard.classList.add("m-2")
        itemCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                                    <p class="card-text">${obj.name}</p>
                                    <p class="card-text">${obj.brand}</p>
                                    <p class="card-text">${obj.stock_photo}</p>
                                    <p class="card-text">${obj.original_price}</p>
                                    <p class="card-text">${obj.user_photo}</p>
                                    <p class="card-text">${obj.amount_paid}</p>
                                    <p class="card-text">${obj.date_acquired}</p>
                                    <p class="card-text">${obj.current_value}</p>
                                    <p class="card-text">${obj.current_location}</p>
                                    <p class="card-text">${obj.keywords}</p>
                                    <p class="card-text">${obj.notes}</p>
                                    <div class="d-flex justify-content-between">
                                        <button class="btn btn-danger" onclick="handleDeleteItem(${obj.id})">Delete</button>
                                        <button onclick="getItemById(${obj.id})" type="button" class="btn btn-primary"
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
    collectionName.innerText = ''
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

    collectionName.innerText = obj.body
    itemName.innerText = obj.body
    itemBrand.innerText = obj.body
    itemStockPhoto.innerText = obj.body
    itemOriginalPrice.innerText = obj.body
    itemUserPhoto.innerText = obj.body
    itemBoughtPrice.innerText = obj.body
    itemDateAcquired.innerText = obj.body
    itemCurrentValue.innerText = obj.body
    itemKeywords.innerText = obj.body
    itemNotes.innerText = obj.body
    updateItemBtn.setAttribute('data-item-id', obj.id)
}


getItems(userId);

submitForm.addEventListener("submit", handleSubmitItem)

updateItemBtn.addEventListener("click", (e) =>{
    let itemId = e.target.getAttribute('data-item-id')
    handleItemEdit(itemId);
})
