const modalAddUser = document.getElementById('add_user');
const modalEditUser = document.getElementById('edit_user');
const modalDelete = document.getElementById('delete_user');
const body = document.body;
let curr_user_id = -1;

function addUser() {
    body.classList.toggle('body--active');
    modalAddUser.classList.toggle('modal--active');
}

function editUser() {
    body.classList.toggle('body--active');
    modalEditUser.classList.toggle('modal--active');
}

function modal_EditUser() {
    let user_id = $(event.target).closest("button").attr('data-user-id');
    let user_name = $(event.target).closest("button").attr('data-user-name');
    let user_username = $(event.target).closest("button").attr('data-user-username');
    let user_mail = $(event.target).closest("button").attr('data-user-mail');
    let user_number = $(event.target).closest("button").attr('data-user-number');
    let user_role = $(event.target).closest("button").attr('data-user-role');

    $("#edit_name").val(user_name);
    $("#edit_username").val(user_username);
    $("#edit_email").val(user_mail);
    $("#edit_phone").val(user_number);
    $("#edit_role").val(user_role);

    let formData = new FormData();
    formData.append('id', user_id);
    formData.append('name', $("#edit_name").val);
    formData.append('username', $("#edit_username").val);
    formData.append('mail', $("#edit_email").val);
    formData.append('number', $("#edit_phone").val);
    formData.append('role', $("#edit_role").val);

    $.ajax({
        method: "POST",
        url: `/users/edit?id=${user_id}`,
        data: formData,
        processData: false,
        contentType: false
    }).done(function () {
        modal_AddUserClose();
        window.location.reload();
    });
}

function modal_EditUserClose() {
    body.classList.toggle('body--active');
    modalEditUser.classList.toggle('modal--active');
    $(".modal").scrollTop(0);
}

function modal_AddUser() {
    let formData = new FormData();
    formData.append('name', $("#add_name").val());
    formData.append('username', $("#add_username").val());
    formData.append('mail', $("#add_email").val());
    formData.append('number', $("#add_phone").val());
    formData.append('password', $("#add_password").val());
    formData.append('role', $("#add_role").val());

    $.ajax({
        method: "POST",
        url: "/users/save",
        data: formData,
        processData: false,
        contentType: false
    }).done(function () {
        modal_AddUserClose();
        window.location.reload();
    });
}

function modal_AddUserClose() {
    body.classList.toggle('body--active');
    modalAddUser.classList.toggle('modal--active');
    $("#add_name").val("");
    $("#add_username").val("");
    $("#add_email").val("");
    $("#add_phone").val("");
    $("#add_password").val("");
    $("#add_role").val($("#add_role option:first").val());
    $(".modal").scrollTop(0);
}

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

$('#search-users').on('input', function () {
    let searchText = $(this).val();
    $.ajax({
        url: '/users',
        type: 'GET',
        data: {param: searchText},
        success: function (data) {
            let catalogList = $('.users__list');
            let newContent = $(data).find('.users__list').html();

            if (searchText.trim() === '') {
                catalogList.replaceWith('<ul class="users__list">' + newContent + '</ul>');
            } else {
                catalogList.empty();
                if (newContent.trim() === '') {
                    catalogList.append('<p class="offer__not-found">Ничего не найдено :)</p>');
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