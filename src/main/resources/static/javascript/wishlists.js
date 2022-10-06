const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const submitForm = document.getElementById("wishlist-form")
const wishlistContainer = document.getElementById("wishlist-container")
const name = document.getElementById('wishlistName-input')
const image = document.getElementById('wishlistImage-input')

let itemBody = document.getElementsByClassName('item-body')
let wishlistName = document.getElementById('wishlist-name')
let wishlistImage = document.getElementById('wishlist-image')
let updateWishlistBtn = document.getElementById('update-wishlist-button')

const headers = {
    'Content-Type': 'application/json'

}

const baseUrlWishlists = "http://localhost:8080/api/v1/wishlists/"
const baseUrlItems = "http://localhost:8080/api/v1/items/"

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }

}



const handleSubmitWishlist = async (e) => {
    e.preventDefault()
    let bodyObj = {
        name: name.value,
        image: image.value


    }

    await addWishlist(bodyObj);
    name.value = '',
        image.value = ''


}

async function addWishlist(obj) {
    const response = await fetch(`${baseUrlWishlists}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers

    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getWishlists(userId);
    }
}


async function getWishlists(userId){
    await fetch(`${baseUrlWishlists}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
}

async function getWishlistById(wishlistId){
    await fetch(baseUrlWishlists + wishlistId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}


async function handleWishlistEdit(wishlistId){
    let bodyObj = {
        id: wishlistId,
        name: wishlistBody.value,
        image: wishlistImage.value
    }
    console.log(wishlistId)
    console.log(wishlistBody)
    console.log(wishlistImage.value)

    await fetch(baseUrlWishlists, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getWishlists(userId);
}

async function handleDeleteWishlist(wishlistId){
    await fetch(baseUrlWishlists + wishlistId, {
        method: "DELETE",
        headers: headers
    })

        .catch(err => console.error(err))

    return getWishlists(userId);
}

const createNoteCards = (array) => {
    wishlistContainer.innerHTML = ''
    array.forEach(obj => {
        let wishlistCard = document.createElement("div")
        wishlistCard.classList.add("m-2")
        wishlistCard.innerHTML = `
            <div class="card d-flex" style="width: 18rem; height: 18rem;">
                                <div class="card-body d-flex flex-column justify-content-between" style="height: available">
                                    <p id="card-name" class="card-text">${obj.name}</p>
                                    <img class="card-img-top" src="${obj.image}" alt="Card image">
                                    <div id="wishlist-btns" class="d-flex justify-content-between">
                                        <button id="item-delete-button" class="btn btn-danger" onclick="handleDeleteWishlist(${obj.id})">Delete</button>
                                         <button id="{${obj.id}" class="btn btn-primary bold" onclick="wishlistSubmit(${obj.id})" type="button" >
                                        Select
                                        </button>
                                        <button onclick="getWishlistById(${obj.id})" type="button" id="item-edit-button" class="btn btn-primary"
                                        data-bs-toggle="modal" data-bs-target="#wishlist-edit-modal">
                                        Edit
                                        </button>
                                    </div>
                                </div>
                            </div>
             `
        wishlistContainer.append(wishlistCard);
    })
}

const populateModal = (obj) => {
    wishlistName.innerText = ''
    wishlistImage.innerText = ''

    wishlistName.innerText = obj.name
    wishlistImage.innerText = obj.image
    updateWishlistBtn.setAttribute('data-wishlist-id', obj.id)
}

getWishlists(userId).then(console.log("get wishlists success"));

submitForm.addEventListener("submit", handleSubmitWishlist)

updateWishlistBtn.addEventListener("click", (e) =>{
    let wishlistId = e.target.getAttribute('data-wishlist-id')
    handleWishlistEdit(wishlistId);
})

async function getWishlist(wishlistId) {
    const response = await fetch(baseUrlWishlists + wishlistId, {
        method: "GET",
        headers: headers
    })
    if (response.status === 200) {
        document.cookie = `wishlistId=${responseArr[1]}`
        window.location.replace(responseArr[0])
    }
}

async function wishlistSubmit(id) {
    document.cookie = `wishlistId= ${id}`
    window.location.replace("http://localhost:8080/wishItems.html")

}