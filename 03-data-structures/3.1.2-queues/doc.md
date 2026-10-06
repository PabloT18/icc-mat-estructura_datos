# Documento docente · 3.1.2 Colas

## Fuente y correspondencia

[Programa analítico](../../programa-analitico.pdf), página 2. Asignatura E-CT-ICO-167, nivel 2. Unidad oficial: **UNIDAD 2 - Estructura de Datos y Grafos**. Horas oficiales de esa unidad: **50**. Código oficial cubierto: **2.1.2**. Código editorial del recurso: **3.1.2**.

La numeración editorial sigue la reorganización solicitada; no sustituye la numeración del documento institucional. Las horas pertenecen a la unidad completa, no a este segmento.

## Resultados de aprendizaje

- Identifica las características y generalidades de las técnicas para realizar programación dinámica.

## Indicadores de logro

- Conoce estructuras de datos lineales, no lineales y avanzadas
- Desarrolla aplicaciones que usen estructuras de datos lineales, no lineales y avanzadas

## Correspondencia por apartado

| Apartado exacto del MD | Aporte al resultado | Indicador | Evidencia observable |
| --- | --- | --- | --- |
| [Tipo abstracto FIFO](java/material.md#concepto-1) | Aporte conceptual parcial: tipo abstracto fifo. | Conoce estructuras de datos lineales, no lineales y avanzadas | Explica tipo abstracto fifo y lo relaciona con un estado concreto de la traza. |
| [Arreglo circular acotado](java/material.md#concepto-2) | Aporte conceptual parcial: arreglo circular acotado. | Conoce estructuras de datos lineales, no lineales y avanzadas | Explica arreglo circular acotado y lo relaciona con un estado concreto de la traza. |
| [Invariantes y referencias](java/material.md#concepto-3) | Aporte conceptual parcial: invariantes y referencias. | Conoce estructuras de datos lineales, no lineales y avanzadas | Explica invariantes y referencias y lo relaciona con un estado concreto de la traza. |
| [Alternativas de biblioteca](java/material.md#concepto-4) | Aporte conceptual parcial: alternativas de biblioteca. | Conoce estructuras de datos lineales, no lineales y avanzadas | Explica alternativas de biblioteca y lo relaciona con un estado concreto de la traza. |
| [Traza](java/material.md#traza) | Análisis del comportamiento de una ejecución. | Conoce estructuras de datos lineales, no lineales y avanzadas | Reproduce transiciones y detecta una violación del invariante. |
| [Demostración](java/material.md#demostracion) | Lectura y comprobación de una implementación de referencia. No acredita por sí sola desarrollo autónomo. | Desarrolla aplicaciones que usen estructuras de datos lineales, no lineales y avanzadas | Ejecuta y explica salidas; identifica el caso base o condición de parada. |
| [PC](java/material.md#pc) | Aplicación autónoma del contenido. | Desarrolla aplicaciones que usen estructuras de datos lineales, no lineales y avanzadas | Construye un simulador de turnos con cola circular de capacidad configurable. Incluye agregar, atender, consultar siguiente y listar en orden lógico. Rechaza capacidad cero y atiende correctamente después de dar varias vueltas al buffer. |

Las versiones [Python](python/material.md) y [JavaScript](js/material.md) conservan las mismas anclas y casos de la PC. La tabla usa Java como enlace principal. No se han creado códigos oficiales RA/IL.

## Secuencia y evaluación

Construye un simulador de turnos con cola circular de capacidad configurable. Incluye agregar, atender, consultar siguiente y listar en orden lógico. Rechaza capacidad cero y atiende correctamente después de dar varias vueltas al buffer.

La demostración es apoyo formativo. La evidencia de implementación se obtiene en la PC y en el laboratorio correspondiente de la unidad. El estudiante entrega una versión en la PC y Java en las PL. Prerrequisitos pedagógicos: programación básica; pilas, colas, diccionarios y recursividad. El programa suministrado no especifica una asignatura prerrequisito formal.

La estimación de tiempo de las PL es orientativa y no modifica la carga oficial.
