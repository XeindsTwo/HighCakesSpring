const modalDelete = document.getElementById('modal-delete_offer');
const body = document.body;
let curr_offer_id = -1;

function modal_DeleteOffer() {
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
    body.classList.remove('body--active');
    modalDelete.classList.remove('modal--active');
    curr_offer_id = -1;
}

function deleteOffer() {
    window.location.href = `offer/delete/${curr_offer_id}`;
}

function validateOfferForm() {
    const nameField = $("#name");
    const phoneField = $("#phone");
    const emailField = $("#email");

    const nameError = nameField.next(".error");
    const phoneError = phoneField.next(".error");
    const emailError = emailField.next(".error");

    let isValid = true;

    const nameRegex = /^[A-Za-zА-Яа-я]+$/;
    if (!nameRegex.test(nameField.val())) {
        nameError.addClass("error--active");
        isValid = false;
    } else {
        nameError.removeClass("error--active");
    }

    if (phoneField.val().trim() === "") {
        phoneError.addClass("error--active");
        isValid = false;
    } else {
        phoneError.removeClass("error--active");
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(emailField.val())) {
        emailError.addClass("error--active");
        isValid = false;
    } else {
        emailError.removeClass("error--active");
    }

    return isValid;
}

$("#offerForm").on("submit", function(event) {
    if (!validateOfferForm()) {
        event.preventDefault();
    }
});