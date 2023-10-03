export function updateFieldOnServer(userId, fieldId, newValue) {
    const xhr = new XMLHttpRequest();
    const capitalizedFieldId = fieldId.charAt(0).toUpperCase() + fieldId.slice(1);
    xhr.open('POST', `/profile/update${capitalizedFieldId}`, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                if (response) {
                    document.getElementById('successMessage').style.display = 'block';
                } else {
                    alert("Ошибка при обновлении");
                }
            } else {
                alert("Ошибка при отправке запроса на сервер");
            }
        }
    };

    const data = JSON.stringify({id: userId, [fieldId]: newValue});
    xhr.send(data);
}