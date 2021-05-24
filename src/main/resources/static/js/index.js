$(function(){
  $('#image').change(function(e){
    const url = URL.createObjectURL(e.target.files[0])
    $('#previewImage').attr('src', url)
    $('#imageUrl').val('ok')
    $('#imageUrlError').text('')
  })

  $('.hierarchy-select').hierarchySelect({
    hierarchy: false,
    width: 'auto'
  });
});

function dirtyField (nameField, label) {
  label ||= nameField
  if (!$(`#${nameField}`).val()) {
    const sentenceCase = label.replace( /([A-Z])/g, " $1" );
    const fieldError = sentenceCase.charAt(0).toUpperCase() + sentenceCase.slice(1).toLowerCase()

    $(`#${nameField}Error`).text(`${fieldError} is required.`)
  } else {
    $(`#${nameField}Error`).text('')
  }
}

function verifyForm (schema, requiredFields) {
  let isValid = true

  requiredFields.forEach(field => {
    let label = field
    if (schema[field].label) label = schema[field].label
    dirtyField(field, label)

    if ($(`#${field}Error`).text()) isValid = false
  })

  return isValid
}

function resetData (fields) {
  fields.forEach(field => {
    $(`#${field}`).val('')
    $(`#${field}Error`).text('')
  })
  $('#previewImage').attr('src', '/images/previewImage.png')
  $('#previewImage').attr('width', '120')
  $('#action').text('Create')
}

function fillData (fields, data, photoDir) {
  fields.forEach(field => {
    $(`#${field}`).val(data[field])
    $(`#${field}Error`).text('')
  })
  $('#previewImage').attr('src', `/photos/${photoDir}/` + $('#imageUrl').val())
  $('#published').attr('checked', data.published)
  $('#action').text('Edit')
}