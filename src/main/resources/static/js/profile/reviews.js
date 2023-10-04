const modal = document.getElementById('modal-edit_review');
const body = document.querySelector('.body');

function reviewOpenEdit(event) {
    const review_text = $(event.target).attr("data-review-text");
    window.currentReviewId = $(event.target).attr("data-review-id");

    $("#description").html(review_text);
    modal.classList.add('modal--active');
    body.classList.add('body--active');
}

function reviewCloseEdit() {
    modal.classList.remove('modal--active');
    body.classList.remove('body--active');
}

function saveEditReview() {
    const editedReviewText = $("#description").val();
    const review_id = $("#button-open-edit_review").attr("data-review-id");

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

            reviewCloseEdit();
        },
        error: function (error) {
            console.log(review_id);
            console.error("Произошла ошибка при редактировании отзыва", error);
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