const modalEditReview = document.getElementById('modal-add_review');
const modalDeleteReview = document.getElementById('modal-delete_review');
const body = document.querySelector('.body');
let curr_review_id = -1;

function saveReview() {
    let formData = new FormData();
    formData.append('text', $("#description").val());

    let isValid = true;
    let description = $("#description").val();
    if (description.trim() === "" || description.length < 30 || description.length > 800) {
        $("#description").next(".error").addClass("error--active");
        isValid = false;
    } else {
        $("#description").next(".error").removeClass("error--active");
    }

    if (isValid) {
        $.ajax({
            method: "POST",
            url: `/reviews/save`,
            data: formData,
            processData: false,
            contentType: false
        }).done(function () {
            reviewClose();
            window.location.reload();
        });
    }
}

function reviewOpen() {
    modalEditReview.classList.toggle('modal--active');
    document.body.classList.toggle('body--active');
}

function reviewClose() {
    modalEditReview.classList.remove('modal--active');
    document.body.classList.remove('body--active');
    modalEditReview.scrollTop = 0;
}

function deleteReviewOpen() {
    let review_id = $(event.target).closest("button").attr("data-review-id");
    let review_name = $(event.target).closest("button").attr("data-review-name");
    body.classList.toggle('body--active');
    modalDeleteReview.classList.toggle('modal--active');
    curr_review_id = review_id;
    $("#review_user-name").html(review_name);
}

function deleteReview() {
    window.location.href = `reviews/delete/${curr_review_id}`;
}

function deleteReviewClose() {
    body.classList.remove('body--active');
    modalDeleteReview.classList.remove('modal--active');
    curr_review_id = -1;
}