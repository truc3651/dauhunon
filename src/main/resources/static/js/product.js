const schema = {
  id: {
    type: 'integer'
  },
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

function takeProduct(data) {
  const products = JSON.parse(localStorage.getItem("products")) || [];
  const product =
  products.filter((product) => product?.id === data.id)[0] || null;

  if (!product) products.push(data);
  else product.total += 1
  localStorage.setItem("products", JSON.stringify(products));

  products.forEach(product => {
      console.log(product?.total)
  });
}

function cleanLocal() {
console.log("hello from clear")
  localStorage.removeItem("products");
  localStorage.removeItem("totalProducts");


}