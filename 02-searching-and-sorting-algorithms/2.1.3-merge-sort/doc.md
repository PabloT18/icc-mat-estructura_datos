# Documento docente · 2.1.3 Merge Sort

## Fuente y correspondencia

[Programa analítico](../../programa-analitico.pdf), página 2. Asignatura E-CT-ICO-167, nivel 2. Unidad oficial: **UNIDAD 1 - Ordenamiento y búsqueda**. Horas oficiales de esa unidad: **40**. Código oficial cubierto: **1.1.5**. Código editorial del recurso: **2.1.3**.

La numeración editorial sigue la reorganización solicitada; no sustituye la numeración del documento institucional. Las horas pertenecen a la unidad completa, no a este segmento.

## Resultados de aprendizaje

- Relaciona las técnicas de ordenamiento y analiza los tipos de búsqueda.
- Desarrolla módulos que emplean técnicas de ordenamiento y tipos de búsquedas.

## Indicadores de logro

- Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda.
- Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda

## Correspondencia por apartado

| Apartado exacto del MD | Aporte al resultado | Indicador | Evidencia observable |
| --- | --- | --- | --- |
| [Dividir y combinar](java/material.md#concepto-1) | Aporte conceptual parcial: dividir y combinar. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica dividir y combinar y lo relaciona con un estado concreto de la traza. |
| [Mezcla de dos secuencias ordenadas](java/material.md#concepto-2) | Aporte conceptual parcial: mezcla de dos secuencias ordenadas. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica mezcla de dos secuencias ordenadas y lo relaciona con un estado concreto de la traza. |
| [Recurrencia y memoria](java/material.md#concepto-3) | Aporte conceptual parcial: recurrencia y memoria. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica recurrencia y memoria y lo relaciona con un estado concreto de la traza. |
| [Casos de borde](java/material.md#concepto-4) | Aporte conceptual parcial: casos de borde. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica casos de borde y lo relaciona con un estado concreto de la traza. |
| [Traza](java/material.md#traza) | Análisis del comportamiento de una ejecución. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Reproduce transiciones y detecta una violación del invariante. |
| [Demostración](java/material.md#demostracion) | Lectura y comprobación de una implementación de referencia. No acredita por sí sola desarrollo autónomo. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Ejecuta y explica salidas; identifica el caso base o condición de parada. |
| [PC](java/material.md#pc) | Aplicación autónoma del contenido. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Implementa mezcla estable de registros por fecha y añade un contador de comparaciones entre claves. Usa un buffer compartido. Explica el tamaño máximo simultáneo de la pila y compara con la cantidad total de llamadas. |

Las versiones [Python](python/material.md) y [JavaScript](js/material.md) conservan las mismas anclas y casos de la PC. La tabla usa Java como enlace principal. No se han creado códigos oficiales RA/IL.

## Secuencia y evaluación

Implementa mezcla estable de registros por fecha y añade un contador de comparaciones entre claves. Usa un buffer compartido. Explica el tamaño máximo simultáneo de la pila y compara con la cantidad total de llamadas.

La demostración es apoyo formativo. La evidencia de implementación se obtiene en la PC y en el laboratorio correspondiente de la unidad. El estudiante entrega una versión en la PC y Java en las PL. Prerrequisitos pedagógicos: programación básica; bucles y arreglos. El programa suministrado no especifica una asignatura prerrequisito formal.

Se introduce el caso base y la disminución de intervalos de forma breve en Merge Sort y Quick Sort. El desarrollo sistemático de la recursividad se mantiene al final de la unidad por petición del docente.
