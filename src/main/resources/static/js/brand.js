const schema = {
  imageUrl: {
    type: 'string',
    required: true,
    text: 'image'
  },
  name: {
    type: 'string',
    required: true
  },
  totalProducts: {
    type: 'integer',
    required: true
  },
  thumbnail: {
    type: 'string',
    required: true
  }
}

const fields = Object.keys(schema).concat(['id', 'imageUrl'])
const requiredFields = Object.keys(schema).filter(field => schema[field].required)

function dirtyField (nameField) {
  if (!$(`#${nameField}`).val()) {
    const sentenceCase = nameField.replace( /([A-Z])/g, " $1" );
    const fieldError = sentenceCase.charAt(0).toUpperCase() + sentenceCase.slice(1).toLowerCase()

    $(`#${nameField}Error`).text(`${fieldError} is required.`)
  } else {
    $(`#${nameField}Error`).text('')
  }
}

function verifyBrand () {
  let isValid = true

  requiredFields.forEach(field => {
    dirtyField(field)
    if ($(`#${field}Error`).text()) isValid = false
  })

  return isValid
}

function resetDataBrand () {
  fields.forEach(field => {
    $(`#${field}`).val('')
    $(`#${field}Error`).text('')
  })
  $('#previewImage').attr('src', '/images/previewImage.png')
  $('#previewImage').attr('height', '60')
}

function fillDataBrand (data) {
  fields.forEach(field => {
    $(`#${field}`).val(data[field])
    $(`#${field}Error`).text('')
  })
  $('#previewImage').attr('src', '/photos/brands/' + $('#imageUrl').val())
}