$(document).ready(function () {
  let width = 320;
  let height = 0;

  let streaming = false;

  let video = document.querySelector('#video')
  let canvas = document.querySelector('#canvas');

  const startup = () => {
    navigator.mediaDevices
      .getUserMedia({ video: true, audio: false })
      .then(function (stream) {
        video.srcObject = stream;
        video.play();
      })
      .catch(function (err) {
        console.log("An error occurred: " + err);
      });

    video.addEventListener(
      "canplay",
      function (ev) {
        if (!streaming) {
          height = video.videoHeight / (video.videoWidth / width);

          if (isNaN(height)) {
            height = width / (4 / 3);
          }

          video.setAttribute("width", width);
          video.setAttribute("height", height);
          canvas.setAttribute("width", width);
          canvas.setAttribute("height", height);
          streaming = true;
        }
      },
      false
    );
    clearphoto();
  }

  const clearphoto = () => {
    let context = canvas.getContext("2d");
    context.fillStyle = "#AAA";
    context.fillRect(0, 0, canvas.width, canvas.height);
  }

  const takepicture= () => {
    let context = canvas.getContext("2d");
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    } else {
      clearphoto();
    }
  }

  $("#webcam").on("show.bs.modal", function () {
    startup();
  });

  $("#webcam").on("hidden.bs.modal", function () {
    const video = document.querySelector("video");
    const mediaStream = video.srcObject;
    const tracks = mediaStream.getTracks();
    tracks[0].stop();
    tracks.forEach((track) => track.stop());
  });

  $("#takePhoto").click(function () {
    takepicture();
  });

  $('#btnOk').click(function(){
    const previewImage = $('#previewImage')
    const data = canvas.toDataURL('image/png');
    previewImage.attr('src', data);
  })
});
