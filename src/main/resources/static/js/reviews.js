const modalEditReview = document.getElementById('modal-add_review');
const body = document.querySelector('.body');

function modal_SaveReview() {
    let formData = new FormData();
    formData.append('text', $("#description").val());
    $.ajax({
        method: "POST",
        url: `/reviews/save`,
        data: formData,
        processData: false,
        contentType: false
    }).done(function () {
        profileClose();
        window.location.reload();
    });
}

function modalReviewOpen() {
    modalEditReview.classList.toggle('modal--active');
    document.body.classList.toggle('body--active');
}

function modalReviewClose() {
    modalEditReview.classList.remove('modal--active');
    document.body.classList.remove('body--active');
    modalEditReview.scrollTop = 0;
}