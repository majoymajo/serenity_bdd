# 📋 PRUEBAS E2E AUTOMATIZADAS - OPENCART CON SERENITY BDD

## 📝 DESCRIPCIÓN

Este proyecto contiene pruebas funcionales E2E (End-to-End) automatizadas para validar el flujo completo de compra en OpenCart utilizando **Serenity BDD** como framework de automatización.

---

## ✅ REQUISITOS PREVIOS

### 1. Java Development Kit (JDK)
- **Versión:** 11 o superior
- **Descargar de:** https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
- **Verificar instalación:** `java -version`

### 2. Apache Maven
- **Versión:** 3.6.0 o superior
- **Descargar de:** https://maven.apache.org/download.cgi
- **Verificar instalación:** `mvn -version`

### 3. Git
- **Descargar de:** https://git-scm.com/downloads
- **Verificar instalación:** `git --version`

### 4. Google Chrome
- El proyecto utiliza ChromeDriver para controlar el navegador
- **Versión:** Compatible con Selenium 4.14.0

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
│   ├── main/java/com/sofka/opencart/
│   │   ├── models/
│   │   │   ├── Product.java
│   │   │   └── GuestCheckout.java
│   │   └── pages/
│   │       ├── HomePage.java
│   │       ├── CartPage.java
│   │       └── CheckoutPage.java
│   └── test/
│       ├── java/com/sofka/opencart/
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
- **Serenity BDD:** 3.9.0
- **Selenium WebDriver:** 4.14.0
- **Cucumber:** 7.14.0
- **JUnit:** 4.13.2
- **WebDriverManager:** 5.6.2
- **Lombok:** 1.18.30
- Y otras dependencias de soporte

### PASO 4: Ejecutar las pruebas

#### Opción A: Ejecutar todas las pruebas
```bash
mvn clean verify
```

#### Opción B: Ejecutar solo las pruebas (sin reportes)
```bash
mvn test
```

#### Opción C: Ejecutar con etiquetas específicas
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

#### Opción D: Ejecutar un escenario específico
```bash
mvn test -Dcucumber.filter.tags="@purchase"
```

### PASO 5: Ver los reportes generados

Después de ejecutar las pruebas, los reportes estarán disponibles en:

**Ruta:** `target/site/serenity/index.html`

Abre este archivo en un navegador web para ver:
- ✓ Resumen de ejecución
- ✓ Detalle de cada escenario
- ✓ Screenshots de cada paso
- ✓ Logs de ejecución
- ✓ Tiempos de ejecución

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
└── serenity.properties → Configuración de Serenity BDD

target/
├── site/serenity/   → Reportes HTML generados
├── cucumber-reports/→ Reportes de Cucumber
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

### ❌ "ChromeDriver no encontrado"
**Solución:** El proyecto usa WebDriverManager que descarga automáticamente. Si persiste, establecer `CHROME_DRIVER_PATH` en variables de entorno.

### ❌ "Timeout esperando elemento"
**Solución:** Aumentar `webdriver.timeouts.implicitly.wait` en `serenity.properties`

### ❌ "Elemento no interactuable"
**Solución:** Agregar scroll:
```javascript
getDriver().executeScript("arguments[0].scrollIntoView();")
```

### ❌ "Las pruebas se ejecutan en headless y no veo nada"
**Solución:** Comentar `chrome.options.headless=old` en `serenity.properties` para abrira ventana de navegador visible.

### ❌ "Los reportes no se generan"
**Solución:** Ejecutar `mvn verify` (en lugar de solo `mvn test`). Verificar que `target/site/serenity/` exista.

### ❌ "Falla el checkout - país/estado no se selecciona"
**Solución:** Los selectores pueden variar según la versión de OpenCart. Usar Chrome DevTools (F12) para actualizar los selectores CSS/XPath.

### ❌ "Falta una dependencia o no descarga correctamente"
**Solución:**
```bash
mv n dependency:purge-local-repository -DreResolve=false
mvn clean install -U
```

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

## 📈 PRÓXIMOS PASOS / MEJORAS FUTURAS

- [ ] Agregar más escenarios de prueba (errores, validaciones, etc.)
- [ ] Implementar Page Factory y @FindBy para localizadores
- [ ] Agregar timeout dinámicos según tipo de elemento
- [ ] Implementar logging con SLF4J/Log4j2
- [ ] Integración con herramientas de reporte (Allure, ExtentReports)
- [ ] Paralelización de pruebas
- [ ] Integración con Jira para trazabilidad
- [ ] Datos dinámicos desde Excel/CSV
- [ ] Video recording de ejecuciones fallidas
- [ ] Implementar Screenplay Pattern (Actor/Ability/Interaction)

---

## 📞 CONTACTO Y SOPORTE

| Campo | Valor |
|-------|-------|
| **Proyecto** | OpenCart Serenity E2E Tests |
| **Versión** | 1.0.0 |
| **Framework** | Serenity BDD 3.9.0 |
| **Selenium** | 4.14.0 |
| **Java** | 11+ |

Para reportar issues o sugerencias:
👉 https://github.com/sofka/opencart-serenity-e2e/issues

---

## 📄 LICENCIA Y TÉRMINOS

Este proyecto es para fines educativos y de prueba.  
OpenCart es una plataforma de e-commerce de código abierto.  
Para más información: https://www.opencart.com/

---

**Última actualización:** 2024

---
