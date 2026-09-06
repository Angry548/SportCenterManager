# Sistema de Gestión de Gimnasio

Sistema web para la administración integral de un gimnasio, desarrollado como parte de un proyecto académico con metodología ágil (Scrum), gestionado a través de Jira.

## Descripción

El sistema permite administrar clientes, entrenadores, membresías, pagos, asistencias, evaluaciones físicas y rutinas de entrenamiento desde una única plataforma.

Está diseñado para tres roles de usuario:

- Administrador
- Entrenador
- Cliente

Cada rol cuenta con acceso únicamente a las funcionalidades correspondientes mediante Spring Security.

## Funcionalidades principales

- **Gestión de usuarios y roles**: registro, autenticación mediante correo electrónico y contraseña, almacenamiento seguro de contraseñas con BCrypt y control de acceso por rol.
- **Gestión de clientes y entrenadores**: registro, edición, activación/desactivación de cuentas y consulta de información.
- **Membresías**: administración del catálogo de planes, asignación de membresías a clientes y consulta de su vigencia.
- **Pagos**: registro de pagos asociados a membresías y generación de comprobantes en formato PDF.
- **Asistencias**: control de ingreso mediante código QR, con registro de accesos permitidos y rechazados.
- **Entrenamiento**: asignación de clientes a entrenadores, evaluaciones físicas, catálogo de ejercicios y creación de rutinas personalizadas.
- **Dashboard**: visualización resumida de la información principal del sistema según el rol autenticado.

## Modelo de datos

El modelo fue diseñado de forma iterativa, buscando evitar redundancias y mantener la integridad de los datos.

Entre las principales decisiones de diseño se encuentran:

- Uso de `Usuario` como entidad encargada de la autenticación, roles y estado de las cuentas, relacionada con los perfiles de `Cliente` y `Entrenador`.
- Registro histórico de las membresías asignadas a los clientes.
- Separación de catálogos administrables como `MetodoPago` y `GrupoMuscular` de valores fijos de negocio implementados mediante enumeraciones.
- Registro de intentos de acceso permitidos y rechazados para mantener el historial de asistencias.

El modelo está compuesto por **14 entidades** y **4 enumeraciones**.

## Diagrama de clases

![Diagrama de Clases](src/diagramas/DiagramaClases.png)

## Diagrama de base de datos

![Diagrama de Base de Datos](src/diagramas/DiagramaBaseDatos.png)

## Tecnologías utilizadas

- **Backend:** Java 21
- **Framework:** Spring Boot
- **Persistencia:** Spring Data JPA / Hibernate
- **Seguridad:** Spring Security
- **Autenticación:** BCrypt
- **Frontend:** Thymeleaf, Bootstrap y JavaScript
- **Base de datos:** Microsoft SQL Server
- **Validaciones:** Jakarta Bean Validation
- **Gestión de proyecto:** Jira utilizando Scrum
- **Generación de PDF:** OpenPDF

---

# Acceso al sistema

Para efectos de demostración y evaluación, el proyecto dispone de cuentas de prueba para cada uno de los roles principales.

## Administrador

**Correo:** `admin@gym.com`  
**Contraseña:** `1234567`

El administrador tiene acceso a los módulos administrativos del sistema.

---

## Entrenador

**Correo:** `valeria@entrenadores.com`  
**Contraseña:** `1234567`

El entrenador puede acceder a rutinas, ejercicios, evaluaciones físicas y al control de acceso mediante código QR, según los permisos configurados.

---

## Cliente

**Correo:** `gabriel@cliente.com`  
**Contraseña:** `1234567`

El cliente puede consultar su información personal, membresía, código QR y asistencias.

> Estas credenciales corresponden únicamente a cuentas de demostración creadas para la evaluación del proyecto.
