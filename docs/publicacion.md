# Consulta y publicación

El proyecto se entrega sin publicar y no crea un repositorio remoto automáticamente.

## Consulta local

Abre index.html tras descomprimir. Para servirlo por HTTP:

```bash
python3 -m http.server 8000
```

## GitHub Pages, cuando el docente decida publicar

1. Crea un repositorio propio y sube el contenido de esta carpeta.
2. Configura Pages para usar GitHub Actions.
3. Revisa `.github/workflows/pages.yml`, que construye, verifica enlaces y prepara la carpeta de publicación.
4. Ejecuta el workflow o sube un commit a main. Consulta la URL que GitHub devuelva; esta entrega no presupone una URL publicada.

```bash
npm run build:site
npm run check:links
npm run stage:pages
```

`scripts/stage-pages.mjs` prepara HTML, assets, MD, PPTX, XML, programa y documentación. Excluye compilados, cachés, node_modules y Git. No requiere dependencias de aplicación.

## Restricciones superficiales de PL

`assets/catalogo.json` contiene `protection: true`. Cámbialo a false y regenera para desactivar globalmente. También puedes abrir una PL con `?protection=off`. Los controles de entrada y la navegación por teclado permanecen disponibles. La restricción no impide capturas, lectura del HTML ni uso de IA. La evaluación se apoya en evidencias y defensa.
