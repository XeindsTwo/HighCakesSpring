const modalEditCake = document.getElementById('modal-edit_cake');
const modalDeleteCake = document.getElementById('modal-delete_cake');
const body = document.querySelector('.body');
let curr_cake_id = -1;

function modal_addCake() {
    modal_CakeOpen("Создание тортика");
    $("#name").val("")
    $("#price").val("")
    $("#description").val("")
    $("#composition").val("")
    $("#calories").val("")
    $("#weight").val("")
    $("#protein").val("")
    $("#fats").val("")
    $("#photo").val("")
    $("#carbs").val("")
    $("#shelfLife").val("")
    $("#file").text("")
    curr_cake_id = -1;
}

function modal_saveCake() {
    let formData = new FormData();
    formData.append('id', curr_cake_id);
    formData.append('name', $("#name").val());
    formData.append('price', $("#price").val());
    formData.append('description', $("#description").val());
    formData.append('composition', $("#composition").val());
    formData.append('calories', $("#calories").val());
    formData.append('weight', $("#weight").val());
    formData.append('protein', $("#protein").val());
    formData.append('fats', $("#fats").val());
    formData.append('filename', $("#photo").val());
    formData.append('photo', $("#photo")[0].files[0]);
    formData.append('carbs', $("#carbs").val());
    formData.append('shelfLife', $("#shelfLife").val());

    if (curr_cake_id === -1) {
        formData.delete('id');
    }

    $.ajax({
        method: "POST",
        url: curr_cake_id === -1 ? "/save/cake" : `/edit/cake?id=${curr_cake_id}`,
        data: formData,
        processData: false,
        contentType: false
    }).done(function () {
        modal_cakeClose();
        window.location.reload();
    });
}

function modal_EditCake(event) {
    modal_CakeOpen("Редактирование тортика");
    let cake_id = $(event.target).closest("button").attr('data-cake-id');
    let cake_name = $(event.target).closest("button").attr('data-cake-name');
    let cake_price = $(event.target).closest("button").attr('data-cake-price');
    let cake_description = $(event.target).closest("button").attr('data-cake-description');
    let cake_composition = $(event.target).closest("button").attr('data-cake-composition');
    let cake_calories = $(event.target).closest("button").attr('data-cake-calories');
    let cake_weight = $(event.target).closest("button").attr('data-cake-weight');
    let cake_protein = $(event.target).closest("button").attr('data-cake-protein');
    let cake_fat = $(event.target).closest("button").attr('data-cake-fat');
    let cake_carbohydrates = $(event.target).closest("button").attr('data-cake-carbohydrates');
    let cake_shelfLife = $(event.target).closest("button").attr('data-cake-shelfLife');
    let cake_filename = $(event.target).closest("button").attr('data-cake-filename');

    $("#name").val(cake_name);
    $("#price").val(cake_price);
    $("#description").val(cake_description);
    $("#composition").val(cake_composition);
    $("#calories").val(cake_calories);
    $("#weight").val(cake_weight);
    $("#protein").val(cake_protein);
    $("#fats").val(cake_fat);
    $("#carbs").val(cake_carbohydrates);
    $("#shelfLife").val(cake_shelfLife);
    $("#file-upload-label").text(cake_filename);

    let formData = new FormData();
    formData.append('id', cake_id);
    formData.append('name', $("#name").val());
    formData.append('price', $("#price").val());
    formData.append('description', $("#description").val());
    formData.append('composition', $("#composition").val());
    formData.append('calories', $("#calories").val());
    formData.append('weight', $("#weight").val());
    formData.append('protein', $("#protein").val());
    formData.append('fats', $("#fats").val());
    formData.append('carbs', $("#carbs").val());
    formData.append('shelfLife', $("#shelfLife").val());

    // Проверяем, выбран ли новый файл
    let fileInput = $("#photo")[0];
    if (fileInput.files.length > 0) {
        formData.append('photo', fileInput.files[0]);
    } else {
        formData.append('filename', cake_filename);
    }

    curr_cake_id = cake_id;
}

function modal_CakeOpen(titleText) {
    $("#edit_modal__title").html(titleText);
    body.classList.toggle('body--active');
    modalEditCake.classList.toggle('modal--active');
    $()
    $("#modal_group_error").removeClass("input-error--active");
}

function modal_cakeClose() {
    body.classList.remove('body--active');
    modalEditCake.classList.remove('modal--active');
    curr_cake_id = -1;
    $("#name").val("");
    $("#price").val("");
    $("#description").val("");
    $("#composition").val("");
    $("#calories").val("");
    $("#weight").val("");
    $("#protein").val("");
    $("#fats").val("");
    $("#photo").val("");
    $("#carbs").val("");
    $("#shelfLife").val("");
    $("#file").text("");
    $(".modal").scrollTop(0);
}

function modal_DeleteCake() {
    console.log("open")
    let cake_id = $(event.target).closest("button").attr("data-cake-id");
    let cake_name = $(event.target).closest("button").attr("data-cake-name");
    body.classList.toggle('body--active');
    modalDeleteCake.classList.toggle('modal--active');
    curr_cake_id = cake_id;
    $("#cake_name-delete").html(cake_name);
}

function modal_DeleteCakeClose() {
    console.log("close")
    body.classList.remove('body--active');
    modalDeleteCake.classList.remove('modal--active');
    curr_cake_id = -1;
}

function deleteCake() {
    window.location.href = `/delete/${curr_cake_id}`;
}

$('#search-cake').on('input', function () {
    var searchText = $(this).val();
    $.ajax({
        url: '/catalog',
        type: 'GET',
        data: {param: searchText},
        success: function (data) {
            var catalogList = $('.catalog__list');
            var newContent = $(data).find('.catalog__list').html();

            if (searchText.trim() === '') {
                catalogList.replaceWith('<ul class="catalog__list">' + newContent + '</ul>');
            } else {
                catalogList.empty();
                if (newContent.trim() === '') {
                    catalogList.append('<p class="catalog__not-found">Ничего не найдено пупс :)</p>');
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