#language: es
Característica: Loguin Saucedemo

  Como usuario
  Quiero autenticarme en la pagina de Saucedemo
  Para verificar el correcto funcionamiento


  @LoguinExitoso
  Escenario: Login Exitoso
    Dado que stiven castro quiere ingresar a la pagina de saucedemo
    Cuando el se autentica con credenciales validas
    Entonces el verifica el mensaje de login exitoso

  @LoguinFallido
  Escenario: Login fallido
    Dado que stiven castro quiere ingresar a la pagina de saucedemo
    Cuando el se autentica con credenciales invalidas
    Entonces el verifica el mensaje de login fallido
