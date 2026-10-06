# Demostraciones

Los ejecutables se encuentran junto a cada material, en `java/ejemplos`, `python/ejemplos` y `js/ejemplos`. Se evita duplicarlos en esta carpeta. Cada versión usa los mismos datos y salida esperada del contenido.

Desde la raíz: `python3 scripts/verify-examples.py`. El verificador ejecuta todas las versiones, comprueba salida exacta y registra versión, código de salida y errores en docs/verificacion/ejecuciones.json. Requiere java, python3 y node. Los archivos fuente son el origen del bloque del MD; `scripts/sync-code.py` los sincroniza.
