#language: es

  Característica: Detalle de Productos Saucedemo

    Como usuario autenticado
    Quiero validar el precio y la descripcion de un producto
    Para asegurarme de que la informacion se mantiene consistente entre el inventario y el carrito

  Antecedentes:
    Dado que stiven castro quiere ingresar a la pagina de saucedemo
    Cuando el se autentica con credenciales validas

  @ValidarProducto
  Escenario: Validar precio y descripcion de un producto al agregarlo al carrito
    Cuando el consulta el precio y la descripcion del primer producto del inventario
    Y el agrega ese producto al carrito
    Entonces el precio y la descripcion en el carrito coinciden con los del inventario
