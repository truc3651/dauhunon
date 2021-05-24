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
  total: {
    type: 'integer',
    required: true
  },
  price: {
    type: 'integer',
    required: true
  },
  discount: {
    type: 'integer'
  },
  thumbnail: {
    type: 'string',
    required: true
  },
  slug: {
    type: 'string'
  }
}

const fields = Object.keys(schema).concat(['id', 'image'])
const requiredFields = Object.keys(schema).filter(field => schema[field].required)

function verifyProduct () {
  return verifyForm(schema, requiredFields)
}

function resetDataProduct () {
  resetData(fields)
}

function fillDataProduct (data) {
  fillData(fields, data, 'products')
}