const modal = document.getElementById('modal-edit_review');
const modalDelete = document.getElementById('modal-delete_review');
const saveButton = document.getElementById('save-button');
const descriptionInput = $("#description");
const body = document.querySelector('.body');
let currentReviewId;
let originalReviewText;

saveButton.classList.add('disabled');
descriptionInput.on("input", function () {
    const editedReviewText = $(this).val();

    if (editedReviewText !== originalReviewText) {
        saveButton.classList.remove('disabled');
    } else {
        saveButton.classList.add('disabled');
    }
});

function reviewOpenEdit(event) {
    currentReviewId = $(event.target).attr("data-review-id");
    originalReviewText = $(event.target).attr("data-review-text");
    const review_text = originalReviewText;

    $("#description").val(review_text);
    modal.classList.add('modal--active');
    body.classList.add('body--active');
}

function reviewOpenDelete(event) {
    currentReviewId = $(event.target).attr("data-review-id");
    modalDelete.classList.add('modal--active');
    body.classList.add('body--active');
}

function reviewCloseEdit() {
    modal.classList.remove('modal--active');
    body.classList.remove('body--active');
}

function reviewCloseDelete() {
    modalDelete.classList.remove('modal--active');
    body.classList.remove('body--active');
}

function saveEditReview() {
    const editedReviewText = $("#description").val();
    let review_id = currentReviewId;

    if (!validateDescription(editedReviewText)) {
        return;
    }

    $.ajax({
        type: "POST",
        url: `/reviews/edit/${review_id}`,
        data: {
            text: editedReviewText
        },
        success: function (response) {
            console.log("Отзыв успешно отредактирован", response);
            const reviewTextElement = $(`#review-text-${review_id}`);
            reviewTextElement.text(response.text);
            $(`#button-open-edit_review[data-review-id='${review_id}']`).attr("data-review-text", response.text);

            reviewCloseEdit();
            currentReviewId = review_id;
        },
        error: function (error) {
            console.log(review_id);
            console.error("Произошла ошибка при редактировании отзыва", error);
        }
    });
}

function deleteProfileReview() {
    $.ajax({
        type: "GET",
        url: `/reviews/delete/${currentReviewId}`,
        success: function () {
            location.reload();
            const tabButton = document.querySelector('[data-tabs-path="reviews"]');
            if (tabButton) {
                tabButton.click();
            }
        },
        error: function (error) {
            console.error("Произошла ошибка при удалении отзыва", error);
        }
    });
}

function validateDescription(description) {
    const minChars = 60;
    const maxChars = 800;
    const descriptionError = $("#description-error");

    if (description.length < minChars || description.length > maxChars) {
        descriptionError.addClass('error--active');
        return false;
    } else {
        descriptionError.removeClass('error--active');
        return true;
    }
}