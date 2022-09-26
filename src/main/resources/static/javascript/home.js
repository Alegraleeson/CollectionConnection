const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const submitForm = document.getElementById("collection-form")
const collectionContainer = document.getElementById("collection-container")
const name = document.getElementById('collectionName-input')
const itemName = document.getElementById('collectionItemName-input')
const brand = document.getElementById('collectionItemBrand-input')
const stockPhoto = document.getElementById('collectionItemStockPhoto-input')
const originalPrice = document.getElementById('collectionItemOriginalPrice-input')
const userPhoto = document.getElementById('collectionUserPhoto-input')
const boughtPrice = document.getElementById('collectionBoughtPrice-input')
const dateAcquired = document.getElementById('collectionDateAcquired-input')
const currentValue = document.getElementById('collectionCurrentValue-input')
const keywords = document.getElementById('collectionItemKeywords-input')
const notes = document.getElementById('collectionItemNotes-input')
const currentLocation = document.getElementById('collectionCurrentValue-input')

let itemBody = document.getElementsByClassName('item-body')
let collectionBody = document.getElementsByClassName('collection-body')
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
        keywords: keywords.value,
        notes: notes.value

    }
    await addItem(bodyObj);
        name.value = ''
        brand.value = ''
        stockPhoto.value = ''
        originalPrice.value = ''
        keywords.value = ''
        notes.value = ''

}

const handleSubmitCollection = async (e) => {
    e.preventDefault()
    let bodyObj = {
        name: name.value,
        user_photo: userPhoto.value,
        amount_paid: boughtPrice.value,
        date_acquired: dateAcquired.value,
        current_location: currentLocation.value,
        current_value: currentValue.value,

    }

    await addCollection(bodyObj);
    name.value = ''
    userPhoto.value = ''
    boughtPrice.value = ''
    dateAcquired.value = ''
    currentLocation.value = ''
    currentValue.value = ''

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

async function addCollection(obj) {
    const response = await fetch(`${baseUrlCollections}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers

    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getCollections(userId);
    }
}

async function getItems(userId){
    await fetch(`${baseUrlItems}user/${userId}`, {
    method: "GET",
    headers: headers
    })
    .then(response => response.json())
    .then(data => createNoteCards(data))
    .catch(err => console.error(err))
}

async function getCollections(userId){
    await fetch(`${baseUrlCollections}user/${userId}`, {
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

async function getCollectionById(collectionId){
    await fetch(baseUrlCollections + collectionId, {
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
        body: itemBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
    .catch(err => console.error(err))

    return getItems(userId);
}

async function handleCollectionEdit(collectionId){
    let bodyObj = {
        id: collectionId,
        body: collectionBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getCollections(userId);
}

async function handleDeleteItem(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "DELETE",
        headers: headers
    })

    .catch(err => console.error(err))

    return getItems(userId);
}

async function handleDeleteCollection(collectionId){
    await fetch(baseUrlCollections + collectionId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getCollections(userId);
}

const createNoteCards = (array) => {
    collectionContainer.innerHTML = ''
    array.forEach(obj => {
        let collectionCard = document.createElement("div")
        collectionCard.classList.add("m-2")
        collectionCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                                    <p class="card-text">${obj.body}</p>
                                    <div class="d-flex justify-content-between">
                                        <button class="btn btn-danger" onclick="handleDeleteCollection(${obj.id})">Delete</button>
                                        <button onclick="getCollectionById(${obj.id})" type="button" class="btn btn-primary"
                                        data-bs-toggle="modal" data-bs-target="#item-edit-modal">
                                        Edit
                                        </button>
                                    </div>
                                </div>
                            </div>
             `




        collectionContainer.append(collectionCard);
    })
}

const populateModal = (obj) => {
    itemBody.innerText = ''
    itemBody.innerText = obj.body
    updateItemBtn.setAttribute('data-item-id', obj.id)
}

getCollections(userId);

submitForm.addEventListener("submit", handleSubmitCollection)

updateItemBtn.addEventListener("click", (e) =>{
    let itemId = e.target.getAttribute('data-item-id')
    handleItemEdit(itemId);
})
