export function initializeAvatar() {
    const avatarInput = document.getElementById('filename');
    const uploadAvatarBtn = document.querySelector('.profile__main-btn');
    const userId = uploadAvatarBtn.getAttribute('data-user-id');
    const avatarImage = document.getElementById('avatarImage');

    avatarInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('id', userId);
            formData.append('filename', file);

            fetch('/profile/updateAvatar', {
                method: 'POST',
                body: formData,
            })
                .then(response => {
                    if (response.ok) {
                        avatarImage.src = URL.createObjectURL(file);
                        document.getElementById('successMessage').style.display = 'block';
                        setTimeout(() => {
                            document.getElementById('successMessage').style.display = 'none';
                        }, 3000);
                    } else {
                        console.error('Ошибка при отправке изображения на сервер');
                    }
                })
                .catch(error => {
                    console.error('Ошибка при выполнении запроса:', error);
                });
        }
    });
}