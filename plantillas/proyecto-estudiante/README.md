# Entrega Java

Copia esta carpeta e identifica PC o PL, autor y contrato. Implementa el enunciado correspondiente.

Desde esta carpeta, con un JDK instalado:

```bash
mkdir -p out
javac -d out src/app/Main.java
java -cp out app.Main
```

Al agregar varios archivos, en Linux/macOS:

```bash
find src -name '*.java' > sources.txt
javac -d out @sources.txt
java -cp out app.Main
```

En PowerShell: `Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName | Set-Content sources.txt` y los mismos comandos javac y java.

No entregues out ni sources.txt. Conserva evidencias por actividad y comandos de prueba propios. El esqueleto no incluye soluciones.
