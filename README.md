# 📋 PRUEBAS E2E AUTOMATIZADAS - OPENCART CON SERENITY BDD

## 📝 DESCRIPCIÓN

Este proyecto contiene pruebas funcionales E2E (End-to-End) automatizadas para validar el flujo completo de compra en OpenCart utilizando **Serenity BDD** como framework de automatización.

**Estado:** ✅ **FUNCIONAL Y LISTO PARA USAR** - Todas las pruebas pasan con `mvn clean test`

**Navegador:** Microsoft Edge (driver offline cacheado - sin depender de descargas de internet)  
**Sitio de Prueba:** http://opencart.abstracta.us/ (OpenCart demo público)  
**Éscenarios:** Flujo E2E completo de compra (Home → Productos → Carrito → Checkout → Confirmación)

---

## ✅ REQUISITOS PREVIOS

### 1. Java Development Kit (JDK)
- **Versión:** 17 o superior (proyecto configurado para Java 17)
- **Descargar de:** https://adoptium.net/
- **Verificar instalación:** `java -version`

### 2. Apache Maven
- **Versión:** 3.8.0 o superior
- **Descargar de:** https://maven.apache.org/download.cgi
- **Verificar instalación:** `mvn -version`

### 3. Microsoft Edge
- **IMPORTANTE:** El proyecto está configurado para usar **Microsoft Edge** (no Chrome)
- **Versión:** 146 o superior (o compatible con el driver cacheado)
- **WebDriver:** Ya está cacheado en `C:\Users\usuario\.cache\selenium\msedgedriver\` (descarga manual, NO automática)
- **Nota:** No requiere WebDriverManager; usa driver provisto offline

### 4. Git (Opcional)
- **Descargar de:** https://git-scm.com/downloads
- **Verificar instalación:** `git --version`

### 5. IDE Recomendado
- **IntelliJ IDEA** Community o Ultimate
- **VSCode:** Instalar extensiones Cucumber y Java Test Runner

---

## 🚀 PASOS DE INSTALACIÓN Y EJECUCIÓN

### PASO 1: Clonar el repositorio
```bash
git clone https://github.com/sofka/opencart-serenity-e2e.git
cd opencart-serenity-e2e
```

### PASO 2: Verificar la estructura del proyecto
```
opencart-serenity-e2e/
├── src/
│   └── test/
│       ├── java/com/sofka/opencart/
│       │   ├── models/
│       │   │   ├── Product.java
│       │   │   └── GuestCheckout.java
│       │   ├── pages/
│       │   │   ├── HomePage.java
│       │   │   ├── CartPage.java
│       │   │   └── CheckoutPage.java
│       │   ├── stepdefinitions/
│       │   │   └── PurchaseFlowSteps.java
│       │   └── runners/
│       │       └── PurchaseFlowTestRunner.java
│       └── resources/
│           ├── features/
│           │   └── purchase_flow.feature
│           └── serenity.properties
├── pom.xml
├── README.md
└── CONCLUSIONES.md
```

### PASO 3: Descargar dependencias de Maven
```bash
mvn clean install
```

Esto descargará todas las dependencias definidas en `pom.xml`:
- **Serenity BDD Core:** 5.3.10
- **Serenity Cucumber:** 5.3.10 (incluye Selenium y Cucumber transitivamente)
- **Selenium WebDriver:** 4.x (gestionado por Serenity)
- **Cucumber:** 7.x (gestionado por Serenity transitivamente)
- **JUnit:** 4.13.2
- **Lombok:** 1.18.30
- **Java:** 17

#### ⚠️ NOTA IMPORTANTE: Configuración de Driver
Este proyecto está configurado para usar **Microsoft Edge con driver provisto (offline)**:
- ✅ **NO** utiliza WebDriverManager para descargas automáticas
- ✅ El driver EdgeDriver está pre-cacheado en: `C:\Users\usuario\.cache\selenium\msedgedriver\win64\146.0.3856.62\msedgedriver.exe`
- ✅ Implementa `LocalEdgeDriverSource.java` para instanciar EdgeDriver explícitamente
- ✅ Configurable en `pom.xml` con propiedades de sistema:
  - `webdriver.driver=provided`
  - `webdriver.provided.type=localedge`
  - `webdriver.provided.localedge=com.sofka.opencart.webdriver.LocalEdgeDriverSource`
  - `webdriver.edge.driver=${user.home}\.cache\selenium\msedgedriver\win64\146.0.3856.62\msedgedriver.exe`

**Ventajas:**
- ✓ Funciona **offline** (sin descargas de internet)
- ✓ Compatible con entornos restrictivos (firewall/DNS bloqueado)
- ✓ Control explícito sobre versión y ubicación del driver
- ✓ No causa errores de casteo (ChromeOptions → EdgeOptions)

### PASO 4: Ejecutar las pruebas

#### Opción A: Ejecutar todas las pruebas (recomendado)
```bash
mvn clean test
```

Esta es la forma estándar; genera reportes en `target/site/serenity/index.html`

#### Opción B: Ejecutar con verbose output (troubleshooting)
```bash
mvn test -e -DtrimStackTrace=false
```

#### Opción C: Ejecutar con maven en quiet mode
```bash
mvn -q clean test
```

### PASO 5: Ver los reportes generados

Después de ejecutar `mvn clean test`, los reportes de Serenity estarán disponibles en:

**Ruta:** `target/site/serenity/index.html`

Abre este archivo en un navegador web para ver:
- ✓ Resumen de ejecución
- ✓ Detalle de cada escenario (paso a paso)
- ✓ Screenshots de cada paso (capturados automáticamente por Serenity)
- ✓ Logs de ejecución detallados
- ✓ Tiempos de ejecución
- ✓ Navegador utilizado (Edge)
- ✓ Información de errores (si los hay)

**Nota:** El reporte se genera automáticamente; abre el HTML en cualquier navegador.

---

## 📁 ESTRUCTURA DE CARPETAS

```
src/main/java/com/sofka/opencart/
├── models/          → Modelos de datos (Product, GuestCheckout)
└── pages/           → Page Objects (HomePage, CartPage, CheckoutPage)

src/test/java/com/sofka/opencart/
├── stepdefinitions/ → Implementación de pasos de Gherkin
└── runners/         → Test Runners configurados

src/test/resources/
├── features/        → Archivos .feature con escenarios en Gherkin
└── serenity.properties → Configuración de Serenity BDD (webdriver.driver=provided, etc.)

src/test/java/com/sofka/opencart/webdriver/
└── LocalEdgeDriverSource.java → Implementación de driver provisto para Edge

target/
├── site/serenity/   → Reportes HTML generados (abre index.html)
├── cucumber-reports/→ Reportes de Cucumber en JSON
├── surefire-reports/→ Reportes de Maven Surefire
└── test-classes/    → Clases compiladas
```

---

## ⚙️ ARCHIVOS DE CONFIGURACIÓN

### pom.xml
Define todas las dependencias del proyecto y la configuración de Maven.

**Principales plugins:**
- `maven-compiler-plugin` → Compilación Java 11
- `maven-surefire-plugin` → Ejecución de pruebas
- `serenity-maven-plugin` → Generación de reportes

### serenity.properties
Configuración específica de Serenity:
- **Browser:** Chrome (headless por defecto)
- **Base URL:** http://opencart.abstracta.us/
- **Screenshots:** Tomadas en cada acción
- **Reporting:** Genera reportes HTML
- **Logging:** Debug activado

---

## 🎬 FLUJO DE PRUEBA (ESCENARIOS)

### ESCENARIO 1: Compra E2E Completa

**Archivo:** `src/test/resources/features/purchase_flow.feature`

**Pasos:**
1. El usuario accede a la página de inicio de OpenCart
2. El usuario agrega 2 productos al carrito
3. El usuario visualiza el carrito
4. Verifica que contiene 2 productos con nombres y precios
5. El usuario procede a checkout
6. Selecciona Guest Checkout
7. Completa datos de envío (nombre, email, dirección, etc.)
8. Selecciona método de envío
9. Selecciona método de pago
10. Confirma la orden
11. Verifica mensaje "Your order has been placed!"

---

## 🏗️ PATRONES Y BUENAS PRÁCTICAS IMPLEMENTADAS

### 1. PAGE OBJECT MODEL (POM)
✓ Encapsulación de elementos y acciones en clases de página  
✓ Localizadores centralizados  
✓ Métodos reutilizables  
✓ Mantenimiento simplificado

### 2. CUCUMBER/GHERKIN
✓ Escenarios en lenguaje natural (español)  
✓ Fácil de entender por stakeholders  
✓ Trazabilidad entre requisitos y pruebas

### 3. STEP DEFINITIONS
✓ Mapeo claro entre pasos Gherkin y código Java  
✓ Assertions explícitas  
✓ Manejo de errores robusto

### 4. MANEJO DE DATOS
✓ Modelos reutilizables (Product, GuestCheckout)  
✓ Datos de prueba centralizados  
✓ Fácil de modificar para diferentes escenarios

### 5. SERENITY BDD
✓ Reportes detallados con screenshots  
✓ Evidencia visual de cada paso  
✓ Logs automáticos  
✓ Integración con CI/CD

### 6. SINCRONIZACIÓN
✓ Esperas implícitas y explícitas  
✓ WebDriverWait para elementos críticos  
✓ Scroll automático  
✓ Manejo de timeouts

---

## 🛠️ TROUBLESHOOTING (SOLUCIÓN DE PROBLEMAS)

### ❌ "Type [unknown] not present" o "ClassNotFoundException" al ejecutar `mvn test`

**CAUSA:** Conflicto de versiones de dependencias transitivas.   
**SÍNTOMA:** Compilación exitosa pero fallos en tiempo de ejecución en el proceso forked de Surefire.

**Solución:**
1. NO declarar explícitamente `selenium-java` o `selenium-chrome-driver` en pom.xml
2. NO declarar explícitamente `cucumber-java` o `cucumber-junit` versiones incompatibles
3. Dejar que **serenity-cucumber** maneje todas las versiones transitivas
4. El pom.xml debe incluir SOLO:
   - serenity-core
   - serenity-cucumber
   - webdrivermanager
   - junit
   - lombok
   - logging (sf4j/log4j)

---

## 🔧 TROUBLESHOOTING Y SOLUCIONES

### ✅ Verificar la configuración del driver Edge

**Para confirmar que todo está correcto:**

```bash
# 1. Verificar que el driver está en caché
ls C:\Users\%USERNAME%\.cache\selenium\msedgedriver\win64\

# 2. Verificar que la propiedad en pom.xml apunta a la ruta correcta
type pom.xml | findstr "webdriver.edge.driver"

# 3. Ejecutar las pruebas con verbose
mvn test -e -DtrimStackTrace=false
```

### ❌ "java.net.UnknownHostException" al descargar drivers

**CAUSA:** El sistema intenta descargar drivers desde internet pero está bloqueado  
**Solución:** Este proyecto ya está configurado para usar driver **offline/provisto**. Verificar que:

1. El archivo `src/test/java/com/sofka/opencart/webdriver/LocalEdgeDriverSource.java` existe
2. El driver Edge está en caché: `C:\Users\usuario\.cache\selenium\msedgedriver\win64\146.0.3856.62\msedgedriver.exe`
3. `pom.xml` tiene las propiedades de sistema configuradas correctamente

```xml
<systemPropertyVariables>
    <webdriver.driver>provided</webdriver.driver>
    <webdriver.provided.type>localedge</webdriver.provided.type>
    <webdriver.provided.localedge>com.sofka.opencart.webdriver.LocalEdgeDriverSource</webdriver.provided.localedge>
    <webdriver.edge.driver>${user.home}\.cache\selenium\msedgedriver\win64\146.0.3856.62\msedgedriver.exe</webdriver.edge.driver>
</systemPropertyVariables>
```

### ❌ "ClassCastException: ChromeOptions cannot be cast to EdgeOptions"

**CAUSA:** Serenity internamente creaba ChromeOptions aunque se configure Edge  
**Solución:** Ya está resuelta en este proyecto gracias a `LocalEdgeDriverSource.java`  
**Verificar:** Si sigues teniendo el error, revisar que `LocalEdgeDriverSource` esté siendo usado:

```bash
grep -r "LocalEdgeDriverSource" src/
# Debería devolver referencias en pom.xml y serenity.properties
```

### ❌ "TimeoutException esperando elemento"

**Causas comunes:**
- Elemento está en un panel colapsado (no visible)
- Wait no es suficiente para AJAX
- Locator no es preciso

**Soluciones:**
1. Las esperas explícitas ya están implementadas en `CheckoutPage.java`
2. Si falla, aumentar duración en `serenity.properties`:
   ```properties
   webdriver.timeouts.implicitly.wait = 25
   webdriver.wait.for.timeout = 30
   ```
3. Verificar el locator usando Developer Tools (F12) en Edge

### ❌ "Los reportes no se generan"

**Solución:** 
```bash
mvn clean test  # Genera reportes automáticamente en target/site/serenity/
```

Si aún no aparecen:
1. Verificar que `target/site/serenity/index.html` existe
2. Abrir en un navegador (no doble-click; usar `http://localhost:8000/...`)
3. Limpiar caché: `mvn clean -DskipTests`

### ❌ "Microsoft Edge no está instalado"

**Solución:** Descargar e instalar Edge desde:  
👉 https://www.microsoft.com/en-us/edge/download

El proyecto está específicamente configurado para Edge; cambiar a Chrome requeriría actualizar `LocalEdgeDriverSource.java` y las propiedades de `pom.xml`.

---

## 🔄 INTEGRACIÓN CONTÍNUA (CI/CD)

Para integrar este proyecto con GitHub Actions, crear archivo `.github/workflows/tests.yml`:

```yaml
name: E2E Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 11
      - run: mvn clean verify
      - uses: actions/upload-artifact@v2
        if: always()
        with:
          name: serenity-reports
          path: target/site/serenity/
```

---

**Última actualización:** 2026

---
