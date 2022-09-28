const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const submitForm = document.getElementById("collection-form")
const collectionContainer = document.getElementById("collection-container")
const name = document.getElementById('collectionName-input')

let itemBody = document.getElementsByClassName('item-body')
let collectionBody = document.getElementById('collection-name')
let updateCollectionBtn = document.getElementById('update-collection-button')

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



const handleSubmitCollection = async (e) => {
    e.preventDefault()
    let bodyObj = {
        name: name.value


    }

    await addCollection(bodyObj);
    name.value = ''


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


async function getCollections(userId){
    await fetch(`${baseUrlCollections}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
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


async function handleCollectionEdit(collectionId){
    let bodyObj = {
        id: collectionId,
        name: collectionBody.value
    }
    console.log(collectionId)
    console.log(collectionBody)

    await fetch(baseUrlCollections, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getCollections(userId);
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
                                    <p class="card-text">${obj.name}</p>
                                    <div class="d-flex justify-content-between">
                                        <button class="btn btn-danger" onclick="handleDeleteCollection(${obj.id})">Delete</button>
                                         <button id="{${obj.id}" class="btn btn-primary" onclick="test(${obj.id})" type="button" >
                                        Select
                                        </button>
                                        <button onclick="getCollectionById(${obj.id})" type="button" class="btn btn-primary"
                                        data-bs-toggle="modal" data-bs-target="#collection-edit-modal">
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
    collectionBody.innerText = ''
    collectionBody.innerText = obj.body
    updateCollectionBtn.setAttribute('data-collection-id', obj.id)
}

getCollections(userId).then(console.log("get collections success"));

submitForm.addEventListener("submit", handleSubmitCollection)

updateCollectionBtn.addEventListener("click", (e) =>{
    let collectionId = e.target.getAttribute('data-collection-id')
    handleCollectionEdit(collectionId);
})

async function getCollection(collectionId) {
    const response = await fetch(baseUrlCollections + collectionId, {
        method: "GET",
        headers: headers
    })
    if (response.status === 200) {
        document.cookie = `collectionId=${responseArr[1]}`
        window.location.replace(responseArr[0])
    }
}



// collectionCard.addEventListener("submit",getCollection())
let collectionId = document.getElementById("")

async function test(id) {
    await fetch(baseUrlCollections + collectionId, {
        method: "GET",
        headers: headers
    })
        .then (res => res.json())
        .then (data => one(data))
        .catch (err => console.error(err.message));


    }

const one = (obj) =>{
    collectionId.setAttribute("collectionId", obj);

}
collectionId.addEventListener("submit",(e) => {
    let collId = e.target.getAttribute("collectionId");
    collectionSubmit(collId)
})



const collectionSubmit = async (evt) =>{
    evt.preventDefault();
    console.log("HandleSubmit")
    let bodyObj = {
        id: collectionId.value,
    }
    const response = await fetch(`${baseUrlCollection}/{collectionId}`,{
        method :"POST",
        body:JSON.stringify(bodyObj),
        headers:headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()
    console.log("get collection HandleSubmit after response" + responseArr)
    console.log(responseArr[1])
    if(response.status === 200){
        document.cookie = `collectionId=${responseArr[1]}`
        window.location.replace(responseArr[0])
    }
}
collectionCard.addEventListener("submit",collectionSubmit)