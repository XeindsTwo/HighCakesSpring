const modalDelete = document.getElementById('modal-delete_offer');
const body = document.body;
let curr_offer_id = -1;

function modal_DeleteOffer() {
    console.log("open")
    let offer_id = $(event.target).closest("button").attr("data-offer-id");
    let offer_name = $(event.target).closest("button").attr("data-offer-name");
    let cake_name = $(event.target).closest("button").attr("data-offer-cake");
    body.classList.toggle('body--active');
    modalDelete.classList.toggle('modal--active');
    curr_offer_id = offer_id;
    $("#offer_name-delete").html(offer_name);
    $("#offer_cake-delete").html(cake_name);
}

function modal_DeleteOfferClose() {
    console.log("close")
    body.classList.remove('body--active');
    modalDelete.classList.remove('modal--active');
    curr_offer_id = -1;
}

function deleteOffer() {
    window.location.href = `offer/delete/${curr_offer_id}`;
}