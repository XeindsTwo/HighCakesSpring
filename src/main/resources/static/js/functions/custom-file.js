updateFileName();

function updateFileName() {
    let input = document.getElementById('photo');
    let fileName = input.files[0].name;
    let fileLabel = document.getElementById('file-upload-label');
    fileLabel.innerHTML = fileName;
}