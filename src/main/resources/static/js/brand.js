const schema = {
  imageUrl: {
    type: 'string',
    required: true,
    label: 'image'
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

const fields = Object.keys(schema).concat(['id', 'image'])
const requiredFields = Object.keys(schema).filter(field => schema[field].required)

function verifyBrand () {
  return verifyForm(schema, requiredFields)
}

function resetDataBrand () {
  resetData(fields)
}

function fillDataBrand (data) {
  fillData(fields, data, 'brands')
}