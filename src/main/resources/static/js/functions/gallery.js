const swiperGallery = new Swiper('.gallery__swiper', {
    loop: true,
    breakpoints: {
        700: {
            speed: 700,
            slidesPerView: 2,
            slidesPerGroup: 1,
            spaceBetween: 40,
        }
    }
});