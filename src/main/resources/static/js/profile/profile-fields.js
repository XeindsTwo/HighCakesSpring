function showError(errorElement, inputField, editBtn) {
    errorElement.classList.add('error--active');
    inputField.classList.add('error-input');
    editBtn.style.opacity = '0.5';
    editBtn.style.pointerEvents = 'none';
    editBtn.disabled = true;
}

function hideError(errorElement, inputField, editBtn, isEditing, regex) {
    if (inputField) {
        inputField.classList.remove('error-input');
    }
    if (errorElement) {
        errorElement.classList.remove('error--active');
    }
    if (editBtn) {
        editBtn.style.opacity = '';
        editBtn.style.pointerEvents = '';
        editBtn.disabled = false;
        if (!isEditing && !regex.test(inputField.value.trim())) {
            editBtn.textContent = 'Изм.';
        }
    }
}

export function initializeEditField(inputField, editBtn, regex, errorElement, updateFunction) {
    inputField.setAttribute('readonly', true);
    let isEditing = false;
    const userId = editBtn.getAttribute('data-user-id');

    function toggleEdit() {
        isEditing = !isEditing;
        if (isEditing) {
            inputField.removeAttribute('readonly');
            inputField.classList.add('editable');
            inputField.focus();
            editBtn.textContent = 'Сохранить';
            inputField.setAttribute('data-user-id', userId);
        } else {
            inputField.setAttribute('readonly', true);
            inputField.classList.remove('editable');
            editBtn.textContent = 'Изм.';
            inputField.blur();
        }
    }

    editBtn.addEventListener('click', (event) => {
        event.preventDefault();
        toggleEdit();
        if (!isEditing) {
            const newValue = inputField.value.trim();
            if (regex.test(newValue)) {
                updateFunction(userId, newValue);
            }
        }
    });

    inputField.addEventListener('input', () => {
        if (isEditing && !regex.test(inputField.value.trim())) {
            showError(errorElement, inputField, editBtn);
        } else {
            hideError(errorElement, inputField, editBtn, isEditing, regex);
        }
    });

    inputField.addEventListener('blur', () => {
        if (!isEditing && !regex.test(inputField.value.trim())) {
            editBtn.textContent = 'Изм.';
        }
    });
}