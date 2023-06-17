const modalEditProfile = document.getElementById('modal-edit_profile');
const body = document.querySelector('.body');
let curr_profile_id = 0;

function modal_EditData() {
    modal_EditOpen();
    let profile_id = $(event.target).closest("button").attr('data-user-id');
    let profile_name = $(event.target).closest("button").attr('data-user-name');
    let profile_number = $(event.target).closest("button").attr('data-user-number');
    let profile_mail = $(event.target).closest("button").attr('data-user-mail');
    let profile_filename = $(event.target).closest("button").attr('data-user-filename');

    $("#name").val(profile_name);
    $("#number").val(profile_number);
    $("#email").val(profile_mail);
    $("#file-upload-label").text(profile_filename);

    let formData = new FormData();
    formData.append('id', profile_id);
    formData.append('name', profile_name);
    formData.append('mail', profile_mail);
    formData.append('number', profile_number);
    let fileInput = $("#photo")[0];
    if (fileInput.files.length > 0) {
        formData.append('photo', fileInput.files[0]);
    } else {
        formData.append('filename', profile_filename);
    }

    curr_profile_id = profile_id;
}

function modal_SaveData() {
    let formData = new FormData();
    formData.append('id', curr_profile_id);
    formData.append('name', $("#name").val());
    formData.append('mail', $("#email").val());
    formData.append('number', $("#number").val());
    formData.append('photo', $("#photo")[0].files[0]);
    formData.append('filename', $("#photo").val());
    $.ajax({
        method: "POST",
        url: `/profile/edit?id=${curr_profile_id}`,
        data: formData,
        processData: false,
        contentType: false
    }).done(function () {
        profileClose();
        window.location.reload();
    });
}

function modal_EditOpen() {
    modalEditProfile.classList.toggle('modal--active');
    document.body.classList.toggle('body--active');
}

function profileClose() {
    modalEditProfile.classList.remove('modal--active');
    document.body.classList.remove('body--active');
    modalEditProfile.scrollTop = 0;
}