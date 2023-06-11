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