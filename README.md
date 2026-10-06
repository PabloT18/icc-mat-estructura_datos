# Estructura de Datos

Material universitario de **Ing. Pablo Torres, Mgtr.**, Universidad Politécnica Salesiana, Computación, Cuenca.

Asignatura **E-CT-ICO-167**, nivel 2. El programa fija 120 horas: 48 de docencia, 48 de prácticas de aplicación y experimentación y 24 de trabajo autónomo. El documento no enumera un prerrequisito formal; para estudiar este material se presupone programación básica.

## Inicio rápido

1. Descomprime el paquete completo.
2. Abre `index.html` en un navegador. El sitio incluye HTML ya generado y funciona sin conexión, salvo enlaces bibliográficos externos.
3. Selecciona unidad, lenguaje y contenido. Lee la demostración, ejecuta sus fuentes y resuelve la PC.
4. Abre la pestaña de laboratorios para las guías y presentaciones de las PL.

Si un navegador restringe archivos locales dentro del visor, desde esta carpeta ejecuta `python3 -m http.server 8000` y abre `http://localhost:8000`.

## Contenido de la entrega

- 4 unidades y 18 contenidos editoriales.
- 54 lecturas completas: Java, Python y JavaScript por contenido, con una PC equivalente en cada versión.
- 54 demostraciones ejecutables con comprobaciones internas y salidas esperadas.
- 18 documentos docentes, 18 síntesis visuales SVG y 18 presentaciones editables de contenido en Java.
- 7 PL independientes en Java con guías HTML, rúbricas de 10 puntos y 7 presentaciones editables.
- 18 bancos Moodle XML con 108 preguntas: 36 de opción múltiple, 36 verdadero/falso, 18 numéricas y 18 de desarrollo que requieren calificación docente.

No hay proyecto acumulativo. El proyecto final de la Unidad 4 es una actividad integradora autónoma. Cada PC se entrega en un lenguaje; las PL solo en Java. No se incluyen soluciones de PC ni PL. Las demostraciones resueltas tienen objetivos distintos.

## Unidades

| Unidad editorial | Contenidos | Horas de la unidad oficial |
|---|---|---:|
| 1. Teoría de la Complejidad | Notación, clases de crecimiento y análisis | 15 |
| 2. Ordenamiento, búsqueda y recursividad | Directos, Shell, Merge Sort, Quick Sort, búsquedas y recursividad | 40 |
| 3. Estructura de Datos y Grafos | Pilas, colas, listas, colecciones, diccionarios, árboles y grafos | 50 |
| 4. Programación Dinámica | Comparación con recursión, caché y memoización | 15 |

Las horas se reproducen del programa por unidad y no se suman a las estimaciones de las prácticas. Recursividad 2.3 es un complemento sin horas oficiales adicionales. La correspondencia exacta se encuentra en [mapa curricular](docs/mapa-curricular.md).

## Ejemplos

Desde la carpeta de la versión de un contenido:

```bash
java ejemplos/Demo.java
python3 ejemplos/demo.py
node ejemplos/demo.mjs
```

Ejecuta el comando del lenguaje seleccionado. Java requiere **JDK**, no solamente JRE. No se utilizan Maven, Gradle, pnpm ni dependencias de aplicación. Consulta [versiones y entorno](docs/versiones.md).

## Mantenimiento

`material.md` es la fuente del cuerpo HTML. El código entre marcadores se sincroniza desde el archivo de `ejemplos/` para evitar diferencias. `assets/catalogo.json` contiene títulos, rutas, orden y datos de las PL. `assets/js/course-data.js` es generado y no se edita manualmente. Las presentaciones son archivos editables independientes y no se regeneran al convertir MD.

```bash
python3 scripts/sync-code.py
node scripts/build-site.mjs
node scripts/check-links.mjs
python3 scripts/verify-examples.py
```

Equivalentes: `npm run build:site`, `npm run check:links`, `npm run check:examples` y `npm run sync:code`. El build requiere Node >=22 y usa el parser incluido con su licencia. No requiere `npm install`. Los HTML se sobrescriben al regenerar. Un cambio de CSS se aplica directamente; un contenido nuevo también debe registrarse en el catálogo.

## Presentaciones y fuente

Instala `assets/fonts/DejaVuSans.ttf` y `assets/fonts/DejaVuSans-Bold.ttf` antes de editar los PPTX. La licencia acompaña los archivos. El código, los textos y las tablas permanecen editables. Formato 16:9. El logo aparece en carátulas. No se solicitaron PDF de las presentaciones.

## Moodle

Importa cada `evaluaciones/tema.xml` desde Banco de preguntas → Importar → Formato Moodle XML. Las categorías siguen la unidad y numeración editorial. Las preguntas de desarrollo exigen revisión docente. La entrega verifica estructura XML y puntajes, pero no se ha realizado importación en una instancia Moodle.

## Documentación

- [Análisis de fuentes](docs/analisis-referencias.md)
- [Mapa curricular](docs/mapa-curricular.md)
- [Git y evidencias](docs/guia-git.md)
- [Trabajo del estudiante](docs/proyecto-estudiante.md)
- [Publicación](docs/publicacion.md)
- [Verificación y límites](docs/verificacion.md)
- [Árbol real de archivos](docs/arbol.txt)

`MANIFEST.sha256` contiene hashes de todos los archivos salvo el propio manifiesto. Los ejemplos y materiales se organizan por unidades y códigos; `plantillas/` permite iniciar una entrega independiente.
# icc-mat-estructura_datos
