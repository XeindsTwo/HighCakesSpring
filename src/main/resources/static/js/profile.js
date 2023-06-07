const btnEdit = document.querySelector('.profile__btn');
const modalEditProfile = document.getElementById('modal-edit_profile');

btnEdit.addEventListener('click', function () {
    modalEditProfile.classList.toggle('modal--active');
    document.body.classList.toggle('body--active');
});

function profileClose() {
    modalEditProfile.classList.remove('modal--active');
    document.body.classList.remove('body--active');
    modalEditProfile.scrollTop = 0;
    resetFormFields();
}

function resetFormFields() {
    let form = document.querySelector('.modal__form');
    let inputs = form.querySelectorAll('.input');

    inputs.forEach(function (input) {
        input.value = '';
    });
}