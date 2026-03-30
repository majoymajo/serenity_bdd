# language: es
Característica: Flujo completo de compra en OpenCart
  Como cliente
  Quiero comprar productos en OpenCart
  Para verificar que el flujo de compra funciona correctamente

  Escenario: Compra e2e desde selección de productos hasta confirmación
    Dado que el usuario está en la página de inicio de OpenCart
    Cuando el usuario agrega 2 productos al carrito
    Y el usuario visualiza el carrito de compras
    Entonces el carrito debe contener 2 productos
    Y el carrito debe mostrar los nombres de los productos
    Y el carrito debe mostrar los precios de los productos
    Cuando el usuario procede a checkout
    Y el usuario selecciona Guest Checkout
    Y el usuario completa los datos de envío
    Y el usuario selecciona un método de envío
    Y el usuario selecciona un método de pago
    Entonces el usuario debe ver el mensaje de confirmación "Your order has been placed!"
    Y el usuario debe ser redirigido a la página de confirmación
