# Verificación de la entrega

## Código

Se ejecutaron las 54 demostraciones: 18 Java mediante modo de archivo fuente, 18 Python y 18 JavaScript. Todas terminaron con código cero y salida idéntica al archivo salida-esperada.txt. Las comprobaciones internas cubren casos normales, vacíos, duplicados y errores de dominio según el algoritmo. Las versiones reales y salidas se registran en [ejecuciones.json](verificacion/ejecuciones.json).

Los bloques del MD se sincronizan desde las fuentes mediante scripts/sync-code.py. Las PC y PL son enunciados sin solución suministrada y no se presentan como implementaciones ejecutadas.

## Moodle y rúbricas

Los 18 XML se analizaron con un parser XML. Se verificaron categoría, ID, tipos de preguntas y fracciones de respuesta. Cada una de las siete rúbricas de PL suma 10. El banco contiene 108 preguntas: 36 multichoice, 36 truefalse, 18 numerical y 18 essay. Las preguntas essay requieren calificación docente.

No se importaron los XML en una instancia Moodle. Comprobación pendiente: importar un banco en el curso de destino y verificar categorías, representación de caracteres y configuración local de preguntas de desarrollo.

## Sitio

La reconstrucción desde una copia limpia produjo los mismos 80 HTML y el mismo catálogo JavaScript. Se comprobaron 1.036 destinos locales en HTML, sin archivos faltantes. El script de preparación de GitHub Pages se ejecutó correctamente; no realiza la publicación por sí mismo.

La lógica del índice, filtro de 4 unidades y 3 lenguajes, rutas de visor, listado de PL y regreso se ejecutó en Node con un DOM simulado. Eso no equivale a una prueba en navegador. Los scripts JavaScript pasaron revisión de sintaxis.

El comprobador incluido revisa existencia de destinos locales href/src en HTML y excluye enlaces externos. No valida anclas, contenidos MD ni disponibilidad de URLs externas; esas limitaciones son explícitas. La revisión adicional de entrega comprueba anclas explícitas de doc.md y consistencia del catálogo.

No fue posible iniciar Chromium porque su ejecutable no está instalado. Queda por verificar en un navegador real: ajuste responsive, desplazamiento de tablas, foco visible, descargas y visor con archivos locales. Los media queries y estilos responsive de la base se conservaron.

## Presentaciones

Los 25 PPTX contienen 251 diapositivas y 50 tablas nativas editables, con texto y código editables, fuente DejaVu Sans y formato 16:9. Todos pasaron comprobaciones de integridad de paquete, geometría, tipografía e importación técnica. Los archivos finales se renderizaron con LibreOffice y se revisaron visualmente mediante hojas de contacto y ampliaciones de diapositivas. El inventario está en [presentaciones.json](verificacion/presentaciones.json). No se han abierto los archivos en Microsoft PowerPoint ni Google Slides.

## Alcance de las fuentes y versiones

El programa y los archivos suministrados se revisaron localmente. La URL del repositorio anterior no pudo recuperarse mediante la consulta externa. El nuevo paquete se construye con el ZIP y las referencias adjuntas.

Las versiones instaladas para las pruebas son diferentes de las últimas referencias consultadas. Consulta [versiones.md](versiones.md). No se declara ejecución en versiones ausentes del entorno. No se publicó el sitio ni se creó un repositorio remoto.
