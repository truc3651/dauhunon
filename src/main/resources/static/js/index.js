$(function(){
  $('#image').change(function(e){
    const url = URL.createObjectURL(e.target.files[0])
    $('#previewImage').attr('src', url)
  })

  if ($('#image').val()) {
    $('#previewImage').attr('src', '/photos/brands/' + $('#image').val())
  }
});