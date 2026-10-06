# Documento docente · 2.3 Recursividad

## Fuente y correspondencia

[Programa analítico](../../programa-analitico.pdf), página 2. Asignatura E-CT-ICO-167, nivel 2. Unidad oficial: **UNIDAD 1 - Ordenamiento y búsqueda**. Horas oficiales de esa unidad: **40**. Código oficial cubierto: **complementario**. Código editorial del recurso: **2.3**.

Recursividad es un complemento solicitado. No figura como tema independiente de la Unidad 1 oficial. Prepara el contenido oficial 3.2, programación dinámica vs programación recursiva, de la página 2. La vinculación siguiente es una propuesta docente y no agrega horas ni resultados oficiales.

## Vinculación docente complementaria

El resultado de la unidad oficial 3 es: “Identifica las características y generalidades de las técnicas para realizar programación dinámica.” Su indicador es: “Conoce las técnicas para realizar algoritmos basados en programación dinámica”. La recursividad prepara esa comparación. La relación con ordenamiento y búsqueda se limita a interpretar algoritmos recursivos, no a atribuir una competencia oficial nueva.

## Resultados de aprendizaje de la unidad de ubicación

- Relaciona las técnicas de ordenamiento y analiza los tipos de búsqueda.
- Desarrolla módulos que emplean técnicas de ordenamiento y tipos de búsquedas.

## Indicadores de logro

- Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda.
- Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda

## Correspondencia por apartado

| Apartado exacto del MD | Aporte al resultado | Indicador | Evidencia observable |
| --- | --- | --- | --- |
| [Definición y contrato](java/material.md#concepto-1) | Aporte conceptual parcial: definición y contrato. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica definición y contrato y lo relaciona con un estado concreto de la traza. |
| [Caso base y terminación](java/material.md#concepto-2) | Aporte conceptual parcial: caso base y terminación. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica caso base y terminación y lo relaciona con un estado concreto de la traza. |
| [Pila de llamadas y retorno](java/material.md#concepto-3) | Aporte conceptual parcial: pila de llamadas y retorno. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica pila de llamadas y retorno y lo relaciona con un estado concreto de la traza. |
| [Comparación con iteración](java/material.md#concepto-4) | Aporte conceptual parcial: comparación con iteración. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica comparación con iteración y lo relaciona con un estado concreto de la traza. |
| [Recursión ramificada](java/material.md#concepto-5) | Aporte conceptual parcial: recursión ramificada. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Explica recursión ramificada y lo relaciona con un estado concreto de la traza. |
| [Traza](java/material.md#traza) | Análisis del comportamiento de una ejecución. | Identifica la estructura y funcionamiento de los diferentes algoritmos de ordenación y búsqueda. | Reproduce transiciones y detecta una violación del invariante. |
| [Demostración](java/material.md#demostracion) | Lectura y comprobación de una implementación de referencia. No acredita por sí sola desarrollo autónomo. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Ejecuta y explica salidas; identifica el caso base o condición de parada. |
| [PC](java/material.md#pc) | Aplicación autónoma del contenido. | Crea aplicaciones utilizando técnicas de ordenamiento y búsqueda | Implementa suma de dígitos para un entero no negativo mediante recursividad, su versión iterativa y una traza de llamadas y retornos. Define el contrato para cero y rechaza negativos. Justifica costo respecto del número de dígitos. |

Las versiones [Python](python/material.md) y [JavaScript](js/material.md) conservan las mismas anclas y casos de la PC. La tabla usa Java como enlace principal. No se han creado códigos oficiales RA/IL.

## Secuencia y evaluación

Implementa suma de dígitos para un entero no negativo mediante recursividad, su versión iterativa y una traza de llamadas y retornos. Define el contrato para cero y rechaza negativos. Justifica costo respecto del número de dígitos.

La demostración es apoyo formativo. La evidencia de implementación se obtiene en la PC y en el laboratorio correspondiente de la unidad. El estudiante entrega una versión en la PC y Java en las PL. Prerrequisitos pedagógicos: programación básica; bucles y arreglos. El programa suministrado no especifica una asignatura prerrequisito formal.

Se introduce el caso base y la disminución de intervalos de forma breve en Merge Sort y Quick Sort. El desarrollo sistemático de la recursividad se mantiene al final de la unidad por petición del docente.
