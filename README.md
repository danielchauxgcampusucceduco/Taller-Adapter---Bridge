# Taller: Patrón Adapter y Bridge

Aplicación de escritorio en Java 17 que resuelve un caso realista de una empresa de logística. El sistema permite cotizar y registrar envíos con proveedores cuyas APIs son incompatibles, y enviar actualizaciones de envío a través de distintos canales.

## Caso de estudio

**Envíos Conectados** integra dos empresas de mensajería. Una usa un sistema heredado que recibe gramos y una bandera de urgencia; la otra expone una pasarela con un nivel de servicio como texto. La aplicación necesita tratarlas de forma uniforme. Además, cada cambio de estado puede comunicarse por correo, SMS o panel interno sin que las alertas dependan de un canal específico.

## Patrones aplicados

### Adapter

El objetivo común es `ShippingProvider`. Los adaptadores convierten el modelo de dominio `ShipmentRequest` a los contratos de cada proveedor y devuelven un único `ShipmentQuote`.

```text
Interfaz de la aplicación        Sistemas externos incompatibles
ShippingProvider                 LegacyCourierSystem
       ▲                                  ▲
       │                                  │
LegacyCourierAdapter ─────────────────────┘

InternationalCarrierAdapter ─────────────► InternationalCarrierGateway
```

Esto permite añadir un nuevo transportador sin modificar la interfaz gráfica ni el servicio de logística: basta con crear otro adaptador que implemente `ShippingProvider`.

### Bridge

La abstracción `ShipmentNotification` conoce el propósito del aviso, mientras que `NotificationChannel` conoce su entrega. Ambas jerarquías evolucionan de forma independiente.

```text
Tipos de notificación                 Canales de entrega
ShipmentNotification                  NotificationChannel
 ├─ StatusNotification                 ├─ EmailChannel
 └─ DeliveryNotification               ├─ SmsChannel
                                        └─ DashboardChannel
```

Por ejemplo, una alerta de estado se puede enviar simultáneamente por correo y SMS, sin crear clases como `EmailStatusNotification` o `SmsDeliveryNotification`.

## Estructura

```text
src/main/java/edu/ucc/patterns
├── adapter/   Adaptadores y simulaciones de proveedores externos
├── bridge/    Abstracciones, implementadores y resultados de entrega
├── model/     Entidades y reglas de validación del dominio
├── service/   Caso de uso de cotización, registro y seguimiento
└── ui/        Frontend Swing y modelo de tabla
```

## Requisitos y ejecución

- JDK 17 o superior.
- Maven 3.9 o superior (recomendado).

```bash
mvn clean compile
mvn exec:java
```

También puede compilarse sin Maven desde la raíz:

```bash
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
java -cp out edu.ucc.patterns.App
```

## Verificación

La verificación ejecutable cubre ambos adaptadores y la combinación de una alerta con dos canales del Bridge:

```bash
javac -d out (Get-ChildItem -Recurse src/main/java,src/test/java -Filter *.java).FullName
java -cp out edu.ucc.patterns.PatternVerification
```

## Decisiones de calidad

- Separación explícita entre interfaz, dominio, infraestructura simulada y presentación.
- Objetos inmutables para solicitudes y cotizaciones; validación en el modelo de dominio.
- Dependencias inyectadas por constructor y repositorio desacoplado para facilitar pruebas.
- Interfaz de escritorio funcional: cotiza, registra, consulta el historial, cambia estado y selecciona múltiples canales de aviso.
