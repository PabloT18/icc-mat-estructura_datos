# Git y evidencias

Cada PC o PL puede vivir en una carpeta o repositorio independiente. No es obligatorio acumular un solo proyecto durante el curso.

```bash
git init
git add .
git commit -m "chore: crear estructura de la actividad"
```

Después de una etapa funcional, ejecuta pruebas y registra el cambio:

```bash
git add src evidencias README.md
git commit -m "feat(pl3.1): atender turnos sin alterar el orden"
git log --oneline --all
git rev-parse HEAD
git show HASH
```

`HASH` se sustituye por un hash real del historial. `git diff HASH1 HASH2` muestra diferencias entre etapas. No se proporcionan hashes simulados. Registra en la evidencia el comando ejecutado, entrada, salida esperada, salida observada y commit. Capturas sin comandos o sin datos no bastan para reproducir un resultado.

El estudiante explica las herramientas que utilizó y verifica personalmente el código. La defensa usa el commit entregado y un cambio pequeño solicitado por el docente. No se publican soluciones de las PC o PL en el material del curso.
