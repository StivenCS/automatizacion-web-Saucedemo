#language: es

  Característica: Compra de Productos Saucedemo

    Como usuario autenticado
    Quiero seleccionar varios productos y finalizar la compra
    Para verificar el correcto funcionamiento del flujo de checkout

  Antecedentes:
    Dado que stiven castro quiere ingresar a la pagina de saucedemo
    Cuando el se autentica con credenciales validas

  @CompraProductos
  Esquema del escenario: Compra seleccionando entre 3 y 5 productos
    Cuando el selecciona "<cantidad>" productos y los agrega al carrito
    Entonces se verifica que se agregaron "<cantidad>" productos al carrito
    Y el finaliza la compra con sus datos personales
    Entonces el verifica el mensaje de compra exitosa

    Ejemplos:
      | cantidad |
      | 3        |
      | 5        |
