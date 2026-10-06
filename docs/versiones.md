# Versiones y entorno

Fecha de consulta: 2026-10-01.

| Tecnología | Versión de referencia consultada | Evidencia oficial |
|---|---|---|
| Java | JDK 27, lanzamiento de septiembre de 2026 | [Notas Oracle](https://www.oracle.com/java/technologies/javase/27-relnote-issues.html) |
| Python | 3.14.8, publicada el 30 de septiembre de 2026 | [Lanzamiento](https://www.python.org/downloads/release/python-3148/) |
| JavaScript | Lenguaje ECMAScript en Node.js 26.10.0 Current, publicado el 22 de septiembre de 2026 | [Lanzamiento](https://nodejs.org/en/blog/release/v26.10.0) |

Se fijan estas versiones consultadas para instalación. La referencia recuperada de Node confirma esa versión Current; no se asegura que el índice del buscador cubra una publicación posterior. JavaScript no se trata como un producto con la misma numeración que Node.

## Entorno realmente ejecutado

- OpenJDK 17.0.20, compilación y ejecución mediante modo de archivo fuente.
- Python 3.12.14.
- Node.js 24.19.0.

Los ejemplos usan un subconjunto compatible y no requieren características nuevas ni experimentales. Las 54 demostraciones se ejecutaron en las versiones anteriores, no en JDK 27/Python 3.14.8/Node 26.10.0. Esta diferencia se declara para no confundir compatibilidad prevista con prueba realizada. La salida de versiones completa está en `verificacion/ejecuciones.json`.

El generador HTML usa Node >=22 y marked incluido con su licencia. No requiere instalación de paquetes. La tipografía DejaVu Sans acompaña la entrega con licencia. No hay base de datos, hardware, Maven, Gradle ni frameworks de aplicación.
