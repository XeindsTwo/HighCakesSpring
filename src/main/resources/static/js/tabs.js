document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".about-company__tab");
    const sections = document.querySelectorAll(".about-company__section");
    const imageElement = document.getElementById("about-company__img");
    let isAnimating = false;

    tabs.forEach(function (tab, index) {
        tab.addEventListener("click", function () {
            if (isAnimating) return;
            if (tab.classList.contains("about-company__tab--active")) return;

            isAnimating = true;

            tabs.forEach(function (tab) {
                tab.classList.remove("about-company__tab--active");
            });
            sections.forEach(function (section) {
                section.classList.remove("about-company__section--active");
                section.style.maxHeight = 0;
            });

            this.classList.add("about-company__tab--active");
            sections[index].classList.add("about-company__section--active");
            expandSection(sections[index], function () {
                isAnimating = false;
            });

            const imageSrc = `images/about/${index + 1}.jpg`;

            if (imageElement) {
                fadeOutImage(imageElement, function () {
                    imageElement.src = imageSrc;
                    fadeInImage(imageElement);
                });
            }
        });

        if (index === 0) {
            tab.classList.add("about-company__tab--active");
            sections[0].classList.add("about-company__section--active");
            sections[0].style.maxHeight = sections[0].scrollHeight + "px";
        }
    });
});

function expandSection(element, callback) {
    element.style.maxHeight = 0;
    element.style.transition = "max-height .7s";

    setTimeout(function () {
        element.style.maxHeight = element.scrollHeight + "px";
    }, 10);

    setTimeout(function () {
        element.style.transition = "";
        if (callback) callback();
    }, 300);
}

function fadeOutImage(element, callback) {
    element.style.opacity = 1;

    let opacity = 1;
    const timer = setInterval(function () {
        if (opacity <= 0.1) {
            clearInterval(timer);
            element.style.opacity = 0;
            callback();
        }

        element.style.opacity = opacity;
        opacity -= opacity * 0.1;
    }, 20);
}

function fadeInImage(element) {
    element.style.opacity = 0;

    let opacity = 0;
    const timer = setInterval(function () {
        if (opacity >= 0.9) {
            clearInterval(timer);
            element.style.opacity = 1;
        }

        element.style.opacity = opacity;
        opacity += 0.1;
    }, 20);
}