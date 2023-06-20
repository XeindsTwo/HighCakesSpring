const modalDelete = document.getElementById('modal-delete_unique');
const body = document.body;
let curr_unique_id = -1;

function modal_DeleteUnique() {
    let unique_id = $(event.target).closest("button").attr("data-unique-id");
    let unique_name = $(event.target).closest("button").attr("data-unique-name");
    body.classList.toggle('body--active');
    modalDelete.classList.toggle('modal--active');
    curr_unique_id = unique_id;
    $("#unique_name-delete").html(unique_name);
}

function modal_DeleteUniqueClose() {
    body.classList.remove('body--active');
    modalDelete.classList.remove('modal--active');
    curr_unique_id = -1;
}

function deleteUnique() {
    window.location.href = `unique/delete/${curr_unique_id}`;
}

function validateOfferForm() {
    const nameField = $("#name");
    const phoneField = $("#phone");
    const emailField = $("#email");
    const weightField = $("#weight");
    const descriptionField = $("#description");

    const nameError = nameField.next(".error");
    const phoneError = phoneField.next(".error");
    const emailError = emailField.next(".error");
    const weightError = weightField.next(".error");
    const descriptionError = descriptionField.next(".error");

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

    const weightValue = parseFloat(weightField.val());
    const minWeight = 300;
    const maxWeight = 30000;
    if (isNaN(weightValue) || weightValue < minWeight || weightValue > maxWeight) {
        weightError.addClass("error--active");
        isValid = false;
    } else {
        weightError.removeClass("error--active");
    }

    if (descriptionField.val().trim().length < 100) {
        descriptionError.addClass("error--active");
        isValid = false;
    } else {
        descriptionError.removeClass("error--active");
    }

    return isValid;
}

$("#offerForm").on("submit", function(event) {
    if (!validateOfferForm()) {
        event.preventDefault();
    }
});

$('#search-offer').on('input', function () {
    let searchText = $(this).val();
    $.ajax({
        url: '/unique',
        type: 'GET',
        data: {param: searchText},
        success: function (data) {
            let catalogList = $('.offer__admin-list');
            let newContent = $(data).find('.offer__admin-list').html();

            if (searchText.trim() === '') {
                catalogList.replaceWith('<ul class="offer__admin-list">' + newContent + '</ul>');
            } else {
                catalogList.empty();
                if (newContent.trim() === '') {
                    catalogList.append('<p class="offer__not-found">Ничего не найдено пупс :)</p>');
                } else {
                    catalogList.append(newContent);
                }
            }
        },
        error: function () {
            console.log('Ошибка при выполнении AJAX-запроса');
        }
    });
});