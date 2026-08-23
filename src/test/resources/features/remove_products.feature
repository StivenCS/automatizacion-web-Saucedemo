#language: es

  Característica: Remover Productos del Carrito Saucedemo

    Como usuario autenticado
    Quiero remover un producto del carrito de compras
    Para poder ajustar mi seleccion antes de finalizar la compra

  Antecedentes:
    Dado que stiven castro quiere ingresar a la pagina de saucedemo
    Cuando el se autentica con credenciales validas

  @RemoverProducto
  Escenario: Remover un producto del carrito
    Cuando el selecciona "3" productos y los agrega al carrito
    Y el remueve un producto del carrito
    Entonces se verifica que quedan "2" productos en el carrito
