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
const collectionSelect = document.getElementById('collectionName-select')

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


async function getItems(userId){
    await fetch(`${baseUrlItems}user/${userId}`, {
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
        body: itemBody.value
    }

    await fetch(baseUrlItems, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getItems(userId);
}

async function handleItemnEdit(itemId){
    let bodyObj = {
        id: itemId,
        body: itemBody.value
    }

    await fetch(baseUrlItems, {
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

async function handleDeleteItem(itemId){
    await fetch(baseUrlItems + itemId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getItems(userId);
}

const createNoteCards = (array) => {
    collectionContainer.innerHTML = ''
    array.forEach(obj => {
        let itemCard = document.createElement("div")
        itemCard.classList.add("m-2")
        itemCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                                    <p class="card-text">${obj.body}</p>
                                    <div class="d-flex justify-content-between">
                                        <button class="btn btn-danger" onclick="handleDeleteItem(${obj.id})">Delete</button>
                                        <button onclick="getCollectionById(${obj.id})" type="button" class="btn btn-primary"
                                        data-bs-toggle="modal" data-bs-target="#item-edit-modal">
                                        Edit
                                        </button>
                                    </div>
                                </div>
                            </div>
             `




        collectionContainer.append(itemCard);
    })
}

const populateModal = (obj) => {
    itemBody.innerText = ''
    itemBody.innerText = obj.body
    updateItemBtn.setAttribute('data-item-id', obj.id)
}



getItems(userId);

submitForm.addEventListener("submit", handleSubmit)

updateItemBtn.addEventListener("click", (e) =>{
    let itemId = e.target.getAttribute('data-item-id')
    handleItemEdit(itemId);
})
