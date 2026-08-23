# Saucedemo - Automatización

Proyecto de automatización de pruebas para la plataforma **SauceDemo (Swag Labs)**, disponible públicamente en `https://www.saucedemo.com`. Cubre exclusivamente la parte **frontend** de la plataforma; SauceDemo no expone una API/backend propia y documentada, por lo que la automatización de servicios queda fuera del alcance de este proyecto.

## Descripción del proyecto

### Historia de usuario

> Yo como cliente de SwagLabs
> Necesito explorar la página y los artículos
> Para poder realizar una compra

**Criterios de aceptación:**
1. Poder seleccionar artículos y terminar una compra.
2. Validar el precio y descripción de al menos uno de los objetos.
3. Poder remover artículos del carrito de compra.

### Cobertura actual por criterio de aceptación

| Criterio | Estado | Evidencia |
|---|---|---|
| 1. Seleccionar artículos y terminar una compra | ✅ Cubierto | `purchase_products.feature` (`@CompraProductos`) |
| 2. Validar precio y descripción de al menos un objeto | ✅ Cubierto | `product_details.feature` (`@ValidarProducto`) |
| 3. Remover artículos del carrito | ✅ Cubierto | `remove_products.feature` (`@RemoverProducto`) |

El **3er caso requerido** (seleccionar mínimo 3 y máximo 5 artículos y completar la compra) también está implementado y es ejecutable — ver [Casos automatizados en este repositorio](#casos-automatizados-en-este-repositorio).

El proyecto también automatiza, con **Screenplay Pattern + Serenity BDD + Cucumber**, un flujo de **autenticación** (login exitoso y login fallido) que no forma parte de los criterios de aceptación de arriba, pero sirve como precondición (`Antecedentes`) del flujo de compra.

Además, como parte del diseño de pruebas (técnicas de caja negra) de una iteración anterior, se documentan hallazgos y bugs detectados sobre el sitio real durante una fase exploratoria manual, que no necesariamente están cubiertos por la suite automatizada actual (ver [Reporte de bugs](#reporte-de-bugs)).

---

## Arquitectura

El proyecto sigue el **Screenplay Pattern** sobre Serenity BDD: un `Actor` interpreta `Task`s (acciones de negocio) compuestas por `Interaction`s (`Click`, `Enter`) sobre elementos de UI (`Target`), y responde `Question`s para hacer aserciones. Los steps de Cucumber orquestan Actors/Tasks/Questions; no contienen lógica de Selenium directamente.

```
src/main/java/saucedemo/
├── userinterface/   Page Objects como Target (locators): LoginPage, HomePage,
│                    InventoryPage, CartPage, CheckoutPage
├── tasks/           Acciones de negocio (Screenplay Task): Login, SelectProducts,
│                    FinalizePurchase, RemoveProduct
├── questions/       Consultas para aserciones (Screenplay Question): VerifyMessage
├── models/          DTOs de datos de dominio: CheckoutInfo
└── utils/           ConfigReader (lee config.properties), Constants (URL base y
                     duraciones de espera), DataGenerator (datos aleatorios con Datafaker)

src/test/java/saucedemo/
├── runners/         Runners JUnit + Cucumber + Serenity: LoginSaucedemoRunner,
│                    PurchaseProductsRunner, ProductDetailsRunner, RemoveProductRunner
└── stepdefinitions/ Traducen Gherkin → Tasks/Questions: LoginSaucedemoStepDefinitions,
                     PurchaseProductsStepDefinitions, ProductDetailsStepDefinitions,
                     RemoveProductStepDefinitions, ParameterDefinitions (Actor/Stage)

src/test/resources/
├── features/        Escenarios en Gherkin (español): login_saucedemo.feature,
│                    purchase_products.feature, product_details.feature,
│                    remove_products.feature
└── config.properties  Credenciales de prueba y mensajes esperados
```

**Flujo de una prueba:**

1. El `.feature` describe el escenario en Gherkin (`#language: es`).
2. Cucumber mapea cada paso a un método en `stepdefinitions/`.
3. El step definition invoca un `Task` (p. ej. `Login.withCredentials(...)`) sobre el `Actor` activo (`OnStage.theActorInTheSpotlight()`).
4. El `Task` encadena `Interaction`s de Serenity Screenplay contra los `Target` definidos en `userinterface/`.
5. Las aserciones se hacen con `Question`s (`VerifyMessage`, `Text.of(...)`) comparadas contra los mensajes esperados en `config.properties`.
6. `runners/` ejecuta la suite vía JUnit + `CucumberWithSerenity`, y Serenity genera el reporte HTML con la narrativa y evidencias de cada paso.

**Datos y configuración externalizados:** credenciales, mensajes esperados y URL base no están hardcodeados en el código — viven en `config.properties` / `Constants.java`, y los datos de checkout (nombre, apellido, código postal) se generan dinámicamente con **Datafaker** en cada ejecución (`DataGenerator`), evitando dependencias entre corridas.

---

## Requisitos previos

- Java 17 (JDK)
- Gradle 8+ (incluido el wrapper `gradlew`, no requiere instalación aparte)
- Google Chrome instalado (navegador configurado por defecto en `serenity.properties` vía `webdriver.driver=chrome`; para usar otro navegador ver [Configuración del navegador](#configuración-del-navegador))

Herramientas del stack (gestionadas por Gradle, no requieren instalación manual):
- Serenity BDD 4.2.1
- Serenity Screenplay
- Selenium WebDriver (vía Serenity + WebDriverManager)
- Cucumber (Gherkin en español)

---

## Instalación y configuración

**Clonar el repositorio:**
```bash
git clone <URL_DEL_REPOSITORIO>
cd automatizacion-web-Saucedemo
```

**Configurar credenciales y mensajes esperados (opcional):**
Editar `src/test/resources/config.properties` si se necesita apuntar a otro usuario o ajustar los textos esperados:
```properties
saucedemo.credentials.valid.username=standard_user
saucedemo.credentials.valid.password=secret_sauce
saucedemo.credentials.invalid.password=<password_invalida>

saucedemo.messages.login.success=Swag Labs
saucedemo.messages.login.error=Epic sadface: Username and password do not match any user in this service
saucedemo.messages.purchase.success=Thank you for your order!
```

La URL base (`https://www.saucedemo.com/`) está en `src/main/java/saucedemo/utils/Constants.java`.

### Configuración del navegador

`serenity.properties` controla el driver y las opciones de Chrome (headless, incógnito, tamaño de ventana, etc.). Para cambiar de navegador, ajustar `webdriver.driver` (por defecto `chrome`).

---

## Comandos clave de ejecución

Todos los comandos se ejecutan desde la raíz del proyecto con el wrapper de Gradle (`./gradlew` en Linux/macOS/Git Bash, `gradlew.bat` en PowerShell/CMD).

| Comando | Qué hace |
|---|---|
| `./gradlew clean test` | Limpia, compila y ejecuta **toda** la suite (login + compra) y genera el reporte Serenity |
| `./gradlew test` | Ejecuta la suite sin limpiar el build anterior |
| `./gradlew test --tests "saucedemo.runners.LoginSaucedemoRunner"` | Ejecuta solo los escenarios de login (exitoso y fallido) |
| `./gradlew test --tests "saucedemo.runners.PurchaseProductsRunner"` | Ejecuta solo los escenarios de compra (3 y 5 productos) |
| `./gradlew test --tests "saucedemo.runners.ProductDetailsRunner"` | Ejecuta solo el escenario de validación de precio/descripción |
| `./gradlew test --tests "saucedemo.runners.RemoveProductRunner"` | Ejecuta solo el escenario de remover producto del carrito |
| `./gradlew clean build` | Compila, ejecuta pruebas y arma el proyecto (ciclo de vida completo de Gradle) |

Tras cualquier ejecución, Gradle dispara automáticamente la tarea `aggregate` de Serenity (`test.finalizedBy(aggregate)` en `build.gradle`), que genera el reporte HTML en:

```
target/site/serenity/index.html
```

Ese reporte incluye la narrativa Gherkin de cada escenario, el resultado paso a paso y las evidencias (capturas en caso de fallo, según `serenity.take.screenshots = FOR_FAILURES`).

**Filtrar por tag de Cucumber:** login usa `@LoguinExitoso` / `@LoguinFallido`, compra usa `@CompraProductos`, validación de producto usa `@ValidarProducto`, y remover del carrito usa `@RemoverProducto`. Para filtrar por tag al ejecutar el runner de login (que por defecto corre ambos), descomentar y ajustar la línea `tags` en `LoginSaucedemoRunner.java`.

---

## Casos de prueba en Gherkin (diseño)

Ejemplos de casos diseñados en distintos niveles de prueba (algunos aún no automatizados en este repositorio; los bugs referenciados están detallados en [Reporte de bugs](#reporte-de-bugs)):

### Nivel unitario
```gherkin
Scenario: Verificar obligatoriedad del campo First Name
  Given Que ingreso a la web de Saucedemo
  And diligencio credenciales correctas
  When agrego un producto al carrito y voy al checkout
  And doy click en el campo de texto "First Name"
  And doy click fuera del campo de texto sin diligenciarlo
  And doy click en "Continue"
  Then debe aparecer un mensaje indicando "First Name is required"
```

### Nivel de integración
```gherkin
Scenario: Verificar bloqueo de cuenta por estado inactivo
  Given Que ingreso a la web de Saucedemo
  When me autentico con el usuario "locked_out_user" y la contraseña "secret_sauce"
  Then debe aparecer un mensaje indicando "Sorry, this user has been locked out."
  And no debo ser redirigido al inventario
```

### E2E (implementado en este repositorio)
```gherkin
Scenario: Compra exitosa de productos
  Given Que ingreso a la web de Saucedemo
  And diligencio credenciales correctas
  When selecciono 3 productos y los agrego al carrito
  And diligencio todos los campos obligatorios del checkout
  And doy click en el botón "Finish"
  Then debe aparecer el mensaje de confirmación "Thank you for your order!"
```

## Casos automatizados en este repositorio

| # | Caso | Feature | Tag |
|---|---|---|---|
| 1 | Login exitoso | `login_saucedemo.feature` | `@LoguinExitoso` |
| 2 | Login fallido | `login_saucedemo.feature` | `@LoguinFallido` |
| 3 | Compra seleccionando 3 y 5 productos (Esquema del escenario) | `purchase_products.feature` | `@CompraProductos` |
| 4 | Validar precio y descripción de un producto (catálogo vs. carrito) | `product_details.feature` | `@ValidarProducto` |
| 5 | Remover un producto del carrito | `remove_products.feature` | `@RemoverProducto` |

- El caso 3 incluye una verificación explícita de que la cantidad de productos agregados al carrito coincide con la cantidad solicitada (lectura del badge del carrito), además del mensaje final de compra exitosa.
- El caso 4 captura nombre, descripción y precio del primer producto del inventario (`actor.remember(...)`), agrega ese mismo producto al carrito y verifica que el nombre, descripción y precio mostrados en el carrito coincidan (`actor.recall(...)`).
- El caso 5 agrega 3 productos, remueve uno y verifica que el badge del carrito quede en 2 — reutiliza el mismo step de verificación de cantidad que el caso 3.

Con los casos 3, 4 y 5 quedan cubiertos los 3 criterios de aceptación de la historia de usuario (ver tabla de cobertura al inicio).

---

## Reporte de bugs

Bugs de comportamiento funcional detectados sobre el sitio  durante la fase de diseño/exploración de pruebas.
---

**Título:** El ordenamiento "Name (Z to A)" no reordena la lista de productos
**ID del Bug:** BUG-01
**Componente:** Inventario - Ordenamiento de productos
**Severidad:** Alta (la función de ordenar queda completamente inoperante).
**Prioridad:** Media (no bloquea completar una compra, pero afecta la exploración del catálogo).

**Entorno:**
- Navegador: Chromium 
- OS: Windows 11 64-bit
- Usuario: `problem_user`

**Pasos para Reproducir:**
1. Inicia sesión con `problem_user` / `secret_sauce`.
2. En el inventario, despliega el dropdown de orden.
3. Selecciona la opción "Name (Z to A)".

**Comportamiento Esperado:**
La lista de productos debería invertirse alfabéticamente (Z a A).

**Comportamiento Actual:**
El valor seleccionado en el dropdown cambia, pero el orden de los productos en pantalla no cambia (permanece A-Z). Reproducido en el 100% de las ejecuciones.

---

**Título:** El campo "Last Name" no retiene el valor tecleado en checkout
**ID del Bug:** BUG-02
**Componente:** Checkout - Paso 1 (Información del comprador)
**Severidad:** Alta (pérdida de datos ingresados por el usuario).
**Prioridad:** Alta (afecta directamente el flujo de compra).

**Entorno:**
- Navegador: Chromium 
- OS: Windows 11 64-bit
- Usuario: `error_user`

**Pasos para Reproducir:**
1. Inicia sesión con `error_user` / `secret_sauce`.
2. Agrega un producto al carrito y dirígete a "Checkout".
3. Diligencia "First Name" (ej. "Ana").
4. Diligencia "Last Name" (ej. "Lopez").
5. Diligencia "Postal Code" con un valor válido.

**Comportamiento Esperado:**
El campo "Last Name" debería conservar el texto ingresado ("Lopez").

**Comportamiento Actual:**
El campo queda vacío pese a haber sido tecleado, sin ningún mensaje de error visible en ese momento. Reproducido en 2/2 ejecuciones.

---

**Título:** El campo "First Name" acepta espacios en blanco como un valor válido
**ID del Bug:** BUG-03
**Componente:** Checkout - Paso 1 (Información del comprador)
**Severidad:** Media (permite avanzar con datos de envío vacíos en la práctica, sin corromper otros módulos).
**Prioridad:** Media (permite entrega en próximo despliegue).

**Entorno:**
- Navegador: Chromium 
- OS: Windows 11 64-bit
- Usuario: `standard_user`

**Pasos para Reproducir:**
1. Inicia sesión con `standard_user` / `secret_sauce`.
2. Agrega un producto al carrito y dirígete a "Checkout".
3. En el campo "First Name" ingresa únicamente espacios en blanco (`"   "`).
4. Completa "Last Name" y "Postal Code" con datos válidos.
5. Haz clic en "Continue".

**Comportamiento Esperado:**
El sistema debería tratar un valor compuesto solo por espacios como equivalente a un campo vacío y bloquear el avance con el mensaje "First Name is required".

**Comportamiento Actual:**
El formulario acepta el valor y avanza al paso 2 del checkout como si "First Name" tuviera un dato válido.
