export function initializeTabs() {
    const tabButtons = document.querySelectorAll('.profile__tabs-btn');
    const tabContents = document.querySelectorAll('.profile__tabs-content');

    tabContents.forEach((content, index) => {
        if (index !== 0) {
            content.style.display = 'none';
        }
    });

    tabButtons.forEach((button, index) => {
        button.addEventListener('click', () => {
            tabButtons.forEach((btn) => {
                btn.classList.remove('profile__tabs-btn--active');
            });

            tabContents.forEach((content) => {
                content.style.display = 'none';
            });

            button.classList.add('profile__tabs-btn--active');
            const targetTabId = button.getAttribute('data-tabs-path');
            const targetTab = document.querySelector(`[data-tabs-target="${targetTabId}"]`);
            if (targetTab) {
                targetTab.style.display = 'block';
            }
        });

        if (index === 0) {
            button.click();
        }
    });
}