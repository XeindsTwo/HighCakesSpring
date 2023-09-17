function setupSearchFromSearchJS(inputSelector, resultContainerSelector, endpoint) {
    $(inputSelector).on('input', function () {
        let searchText = $(this).val();
        $.ajax({
            url: endpoint,
            type: 'GET',
            data: { param: searchText },
            success: function (data) {
                let resultContainer = $(resultContainerSelector);
                let newContent = $(data).find(resultContainerSelector).html();

                if (searchText.trim() === '') {
                    resultContainer.replaceWith('<ul class="' + resultContainerSelector.substring(1) + '">' + newContent + '</ul>');
                } else {
                    resultContainer.empty();
                    if (newContent.trim() === '') {
                        resultContainer.append('<p class="' + resultContainerSelector.substring(1) + '-not-found">Ничего не найдено пупс :)</p>');
                    } else {
                        resultContainer.append(newContent);
                    }
                }
            },
            error: function () {
                console.log('Ошибка при выполнении AJAX-запроса');
            }
        });
    });
}