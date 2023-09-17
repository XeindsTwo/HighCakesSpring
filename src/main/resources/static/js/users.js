const modalDelete = document.getElementById('delete_user');
const body = document.body;
let curr_user_id = -1;

function deleteUser() {
    let user_id = $(event.target).closest("button").attr("data-user-id");
    let user_username = $(event.target).closest("button").attr("data-user-username");
    body.classList.toggle('body--active');
    modalDelete.classList.toggle('modal--active');
    curr_user_id = user_id;
    $("#username-delete").html(user_username);
}

function modal_DeleteUserClose() {
    body.classList.remove('body--active');
    modalDelete.classList.remove('modal--active');
    curr_user_id = -1;
}

function deleteUserSend() {
    window.location.href = `users/delete/${curr_user_id}`;
}