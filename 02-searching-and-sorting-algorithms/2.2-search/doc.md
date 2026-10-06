# Documento docente · 2.2 Búsqueda secuencial y binaria

## Fuente y correspondencia

[Programa analítico](../../programa-analitico.pdf), página 2. Asignatura E-CT-ICO-167, nivel 2. Unidad oficial: **UNIDAD 1 - Ordenamiento y búsqueda**. Horas oficiales de esa unidad: **40**. Código oficial cubierto: **1.2.1–1.2.2**. Código editorial del recurso: **2.2**.

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
| [Contrato de búsqueda](java/material.md#concepto-1) | Aporte conceptual parcial: contrato de búsqueda. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica contrato de búsqueda y lo relaciona con un estado concreto de la traza. |
| [Recorrido secuencial](java/material.md#concepto-2) | Aporte conceptual parcial: recorrido secuencial. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica recorrido secuencial y lo relaciona con un estado concreto de la traza. |
| [Intervalo binario e invariante](java/material.md#concepto-3) | Aporte conceptual parcial: intervalo binario e invariante. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica intervalo binario e invariante y lo relaciona con un estado concreto de la traza. |
| [Costo y precondición](java/material.md#concepto-4) | Aporte conceptual parcial: costo y precondición. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica costo y precondición y lo relaciona con un estado concreto de la traza. |
| [Preparación frente a consultas](java/material.md#concepto-5) | Aporte conceptual parcial: preparación frente a consultas. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica preparación frente a consultas y lo relaciona con un estado concreto de la traza. |
| [Traza](java/material.md#traza) | Análisis del comportamiento de una ejecución. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Reproduce transiciones y detecta una violación del invariante. |
| [Demostración](java/material.md#demostracion) | Lectura y comprobación de una implementación de referencia. No acredita por sí sola desarrollo autónomo. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Ejecuta y explica salidas; identifica el caso base o condición de parada. |
| [PC](java/material.md#pc) | Aplicación autónoma del contenido. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Implementa una búsqueda que devuelva el intervalo semiabierto de todas las coincidencias con dos búsquedas binarias. No recorras linealmente el bloque de duplicados. Si no hay coincidencias devuelve [-1,-1]. |

Las versiones [Python](python/material.md) y [JavaScript](js/material.md) conservan las mismas anclas y casos de la PC. La tabla usa Java como enlace principal. No se han creado códigos oficiales RA/IL.

## Secuencia y evaluación

Implementa una búsqueda que devuelva el intervalo semiabierto de todas las coincidencias con dos búsquedas binarias. No recorras linealmente el bloque de duplicados. Si no hay coincidencias devuelve [-1,-1].

La demostración es apoyo formativo. La evidencia de implementación se obtiene en la PC y en el laboratorio correspondiente de la unidad. El estudiante entrega una versión en la PC y Java en las PL. Prerrequisitos pedagógicos: programación básica; bucles y arreglos. El programa suministrado no especifica una asignatura prerrequisito formal.

Se introduce el caso base y la disminución de intervalos de forma breve en Merge Sort y Quick Sort. El desarrollo sistemático de la recursividad se mantiene al final de la unidad por petición del docente.
