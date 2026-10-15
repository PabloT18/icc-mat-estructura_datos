# Estructura de Datos

![UPS · Carrera de Computación](../../../assets/logos/ups-computacion.png)

## 2.1.1 Ordenamiento directo · Java

**Autor:** Ing. Pablo Torres, Mgtr.
**Unidad 2:** Ordenamiento, búsqueda y recursividad

[Índice del curso](../../../index.html) · [Presentación Java](../presentacion.pptx) · [Evaluación Moodle](../evaluaciones/tema.xml)

[Java](../java/material.md) · [Python](../python/material.md) · [JavaScript](../js/material.md)

### Índice de contenidos

- [1. Conceptos y ejemplos](#conceptos)
- [Burbuja](#concepto-2)
- [Selección](#concepto-3)
- [Inserción y traza](#concepto-4)
- [Comparación de complejidades](#concepto-5)
- [2. Demostración guiada](#demostracion)
- [3. Práctica y PC](#pc)
- [Lo esencial](#esencial)

Este material explica cómo funcionan los ordenamientos directos, por qué producen una salida correcta y cómo justificar su costo. Cada método se estudia mediante concepto, procedimiento, traza, invariante, complejidad y errores frecuentes. Las tres versiones mantienen los mismos problemas, entradas y casos de aceptación.

<a id="conceptos"></a>
## 1. Conceptos y ejemplos

**Prerrequisitos:** variables, comparaciones, condiciones, bucles, funciones y acceso a una secuencia mediante índices. Se utilizan índices desde cero. La notación de complejidad se explica antes de aplicarla a los algoritmos.

<a id="concepto-1"></a>
### 1.1. Qué significa ordenar

Ordenar consiste en reorganizar elementos según una **clave** y un **criterio de comparación**. Si cada elemento es un número, el propio número puede ser la clave. Si cada elemento es un registro de estudiante, la clave podría ser su nota. Ordenar por nota no significa ordenar alfabéticamente por nombre: primero se debe precisar qué información decide la posición.

En orden ascendente se exige que cada elemento sea menor o igual que el siguiente. La igualdad permite conservar valores repetidos. Para una secuencia `a` de tamaño `n`, la condición es `a[k] <= a[k + 1]` para todo índice `k` entre `0` y `n - 2`. El orden descendente invierte la comparación: `a[k] >= a[k + 1]`.

| Entrada | Criterio | Salida válida |
|---|---|---|
| `[5, 3, 4, 1, 2]` | Numérico ascendente | `[1, 2, 3, 4, 5]` |
| `[5, 3, 4, 1, 2]` | Numérico descendente | `[5, 4, 3, 2, 1]` |
| `[2, 1, 2]` | Numérico ascendente | `[1, 2, 2]` |
| `[]` | Numérico ascendente | `[]` |

**Dos condiciones de corrección.** La salida debe estar ordenada y conservar exactamente los elementos de la entrada, incluidas sus repeticiones. Convertir `[2, 1, 2]` en `[1, 2]` produce una secuencia ordenada, pero no resuelve el problema: ha eliminado un elemento. Decimos que la salida debe ser una **permutación** de la entrada.

Vacío y un elemento ya están ordenados porque no existe ningún par vecino que incumpla el criterio. El algoritmo debe aceptarlos sin intentar acceder a posiciones inexistentes.

**Error que evitar.** Confundir ordenar con filtrar, eliminar duplicados o construir una secuencia nueva de números consecutivos. El procedimiento debe funcionar con negativos, huecos entre valores y repeticiones.

### 1.2. Estabilidad, modificación y copia

Un algoritmo es **estable** si conserva el orden relativo de los registros que tienen la misma clave. La estabilidad solo se puede observar cuando se distinguen las identidades de esos registros.

| Entrada por nota | Salida estable ascendente | Salida ordenada pero inestable |
|---|---|---|
| `[(A,8), (B,5), (C,8)]` | `[(B,5), (A,8), (C,8)]` | `[(B,5), (C,8), (A,8)]` |

A y C tienen la misma nota. Ambas salidas están ordenadas por nota, pero solo la primera respeta que A llegó antes que C. Con números aislados como `[8, 5, 8]` no se puede observar cuál de los dos ochos cambió de lugar.

Ordenar **sobre la misma secuencia** significa modificar sus posiciones. En las implementaciones de este tema, cada algoritmo usa únicamente unas pocas variables adicionales: su memoria auxiliar es constante. Esto no significa que la entrada ocupe memoria constante; almacenar `n` elementos requiere espacio proporcional a `n`.

Una copia independiente del contenedor permite conservar el arreglo original. Cuando sus elementos son objetos, una copia superficial copia referencias: reordenar la copia no cambia las posiciones del original, pero modificar un objeto compartido puede ser visible desde ambos contenedores.

**Ejemplo de aplicación.** Si se comparan tres algoritmos, cada uno debe recibir una copia del mismo arreglo inicial. Ejecutar selección sobre la salida de burbuja haría que el segundo algoritmo recibiera datos ya ordenados, alterando el experimento.

### 1.3. Cómo analizar el costo

El tamaño de entrada es **n = cantidad de elementos**, no el valor del mayor número. Ordenar `[1000000, -5, 7]` tiene `n = 3`. Bajo el modelo utilizado aquí, acceder a un índice, comparar dos enteros de tamaño acotado y asignar una referencia o un entero cuestan tiempo constante.

| Operación contada | Qué significa | Qué no incluye |
|---|---|---|
| Comparación de claves | Evaluar `a[j] > clave` o comparar dos elementos | Comprobar un límite como `j >= 0` |
| Intercambio | Permutar dos posiciones distintas | Actualizar el índice del mínimo |
| Desplazamiento | Copiar un elemento una posición a la derecha en inserción | Escribir la clave guardada en su posición final |
| Pasada | Una iteración del bucle exterior | Cada comparación del bucle interior |

Los conteos exactos dependen de la implementación y de la convención. Un intercambio suele implicar una variable temporal y dos escrituras en la secuencia; no equivale a una sola asignación. Aquí contamos intercambios como operaciones completas, sin confundirlos con desplazamientos.

**Mejor caso:** entradas que requieren menos trabajo entre las de tamaño `n`. **Peor caso:** entradas que requieren más trabajo con ese tamaño. **Caso promedio:** costo esperado bajo una distribución de entradas explícita. En las comparaciones de este tema se supone, para el promedio, una permutación uniformemente aleatoria de `n` claves distintas.

| Notación | Significado |
|---|---|
| `O(g(n))` | Cota superior asintótica |
| `Ω(g(n))` | Cota inferior asintótica |
| `Θ(g(n))` | Cota ajustada: el crecimiento queda acotado por múltiplos positivos de `g(n)` |

Estas notaciones no son sinónimos de peor, mejor y promedio. Se puede describir el **mejor caso** de burbuja optimizada como `Θ(n)` y el **peor caso** como `Θ(n²)`. Decir únicamente `O(n²)` es una cota válida, pero no expresa la mejora que aprovecha en datos ordenados.

Para justificar un costo no basta con contar cuántos bucles aparecen escritos. Dos bucles consecutivos de `n` iteraciones suman trabajo lineal; dos bucles anidados pueden acumular trabajo cuadrático, pero hay que examinar sus límites y condiciones de terminación.

**Límite del modelo.** Si las claves fueran cadenas de longitud máxima `m`, una comparación podría costar hasta `O(m)`. Los conteos de comparaciones seguirían siendo útiles, pero no se podría tratarlas automáticamente como operaciones constantes. En Python también debe distinguirse el número de elementos del tamaño en bits de enteros arbitrariamente grandes.

### 1.4. Invariantes: qué permanece correcto

Un **invariante de bucle** es una propiedad que se cumple antes de empezar una iteración y se conserva al terminarla. Sirve para explicar por qué el algoritmo funciona, además de mostrar que produjo una salida correcta en un ejemplo.

La justificación tiene tres partes: indicar por qué la propiedad es verdadera al inicio, explicar cómo la iteración la conserva y relacionarla con el resultado cuando termina el bucle. También hay que justificar la terminación: un límite disminuye, un índice avanza o se cumple una condición de salida.

| Método | Región cuya corrección se acumula |
|---|---|
| Burbuja ascendente | Sufijo con los mayores elementos en sus posiciones finales |
| Selección del mínimo | Prefijo con los menores elementos en sus posiciones finales |
| Inserción | Prefijo ordenado que contiene los elementos ya procesados |

El prefijo de inserción está ordenado, pero sus elementos todavía pueden cambiar de posición al procesar claves posteriores. Por ejemplo, el `5` de `[3, 5]` se desplazará al insertar `4`. No se debe atribuir a ese prefijo la misma propiedad de posiciones definitivas que tiene selección.

<a id="concepto-2"></a>
### 1.5. Burbuja con detección de cambios

**Concepto.** Burbuja compara elementos vecinos. Si están en el orden incorrecto, los intercambia. En un recorrido de izquierda a derecha, el mayor elemento del tramo activo va avanzando hasta su extremo derecho.

**Procedimiento.** Se inicia `end = n - 1`. Se comparan las parejas de índices `(0,1)`, `(1,2)` y así hasta `(end - 1,end)`. Al terminar, la posición `end` ya contiene el máximo del tramo; la siguiente pasada reduce `end` en uno. Una bandera `changed` comienza en falso en cada pasada y cambia a verdadero si ocurre un intercambio.

**Ejemplo completo por pasadas**, con entrada `[5, 3, 4, 1, 2]`:

| Pasada | Tramo activo antes de recorrer | Estado al terminar | Comparaciones | Intercambios |
|---|---|---|---:|---:|
| 1 | Índices 0 a 4 | `[3, 4, 1, 2, 5]` | 4 | 4 |
| 2 | Índices 0 a 3 | `[3, 1, 2, 4, 5]` | 3 | 2 |
| 3 | Índices 0 a 2 | `[1, 2, 3, 4, 5]` | 2 | 2 |
| 4 | Índices 0 a 1 | `[1, 2, 3, 4, 5]` | 1 | 0 |
| **Total** | | | **10** | **8** |

En la primera pasada se realizan los intercambios `5↔3`, `5↔4`, `5↔1` y `5↔2`. El `5` queda fijado. La última pasada no intercambia: la bandera permite terminar. Que la secuencia ya esté ordenada después de la tercera pasada no significa que el programa lo sepa sin comprobarlo.

**Invariante y terminación.** Antes de una pasada, el sufijo posterior a `end` ya está fijado. El recorrido coloca el máximo restante en `end`, ampliando ese sufijo. Si no hubo cambios, todas las parejas vecinas del tramo activo están ordenadas; junto con el sufijo fijado, toda la secuencia está ordenada. El límite decrece hasta que no quedan parejas por revisar.

**Estabilidad.** Con comparación estricta `>`, dos claves iguales no se intercambian. Como los movimientos son entre vecinos, dos registros iguales no pueden cruzarse sin intercambiarse directamente. Por eso esta variante es estable. Usar `>=` eliminaría esa garantía y produciría intercambios innecesarios entre iguales.

**Complejidad paso a paso.** En el peor caso se completan todas las pasadas:

| Paso | Expresión | Justificación |
|---|---|---|
| Conteo | `C(n) = (n - 1) + (n - 2) + ... + 1` | El tramo pierde una posición por pasada |
| Suma | `C(n) = n(n - 1)/2` | Suma de los primeros `n - 1` enteros positivos |
| Expansión | `C(n) = (n² - n)/2` | Distribución del producto |
| Crecimiento | `C(n) ∈ Θ(n²)` | Domina el término cuadrático |

En orden inverso y con claves distintas, todas estas comparaciones provocan intercambios. En cambio, para una entrada ya ordenada de tamaño `n >= 2`, la primera pasada realiza `n - 1` comparaciones y cero intercambios; la bandera detiene el algoritmo. Por tanto, el mejor tiempo es `Θ(n)` y el peor `Θ(n²)`. Sin la salida por bandera, esta estructura de bucles seguiría haciendo trabajo cuadrático incluso con la entrada ordenada.

**Error que evitar.** Comparar `a[i]` con todos los `a[j]` posteriores e intercambiar inmediatamente no es la burbuja adyacente descrita aquí. Tampoco se debe reiniciar la bandera dentro del bucle interior: se perdería la información de los intercambios anteriores de esa pasada.

<a id="concepto-3"></a>
### 1.6. Selección del mínimo

**Concepto.** Selección busca el menor elemento del tramo pendiente y lo coloca en la primera posición de ese tramo. Durante la búsqueda solo actualiza un índice; realiza el intercambio después de terminar de buscar.

**Procedimiento.** Para cada posición `i`, se supone inicialmente que el mínimo está en `i`. Se recorren las posiciones `i + 1` a `n - 1`. Si aparece una clave menor, se actualiza `minimum`. Al terminar, se intercambian `i` y `minimum` solamente si son posiciones distintas.

| Posición i | Mínimo encontrado | Estado después de colocarlo | Comparaciones | Intercambios |
|---:|---:|---|---:|---:|
| 0 | 1 | `[1, 3, 4, 5, 2]` | 4 | 1 |
| 1 | 2 | `[1, 2, 4, 5, 3]` | 3 | 1 |
| 2 | 3 | `[1, 2, 3, 5, 4]` | 2 | 1 |
| 3 | 4 | `[1, 2, 3, 4, 5]` | 1 | 1 |
| **Total** | | | **10** | **4** |

**Invariante y terminación.** Al comenzar la iteración `i`, las posiciones anteriores contienen los `i` menores elementos en orden. Elegir el mínimo del sufijo coloca el siguiente elemento correcto en `i`. El prefijo crece una posición por iteración. Tras fijar `n - 1` posiciones, la última queda determinada.

**Complejidad paso a paso.** La búsqueda del mínimo siempre recorre todo el sufijo. Aunque no encuentre un valor menor, debe comprobarlo:

`C(n) = Σ desde i = 0 hasta n - 2 de (n - i - 1) = n(n - 1)/2`.

La entrada ordenada y la entrada inversa producen el mismo número de comparaciones. El mejor, promedio y peor tiempo son `Θ(n²)`. El número de intercambios es como máximo `n - 1`, porque hay como máximo uno por iteración exterior. Con la condición `minimum != i`, una entrada ordenada realiza cero intercambios, pero mantiene las comparaciones cuadráticas.

**Contraejemplo de estabilidad.** En `[2A, 2B, 1C]`, las letras identifican registros y solo los números se comparan. El mínimo `1C` se intercambia con `2A`, dejando `[1C, 2B, 2A]`. Los registros de clave 2 invierten su orden. Por tanto, esta selección con intercambio no es estable, incluso si se usa comparación estricta para buscar el mínimo.

**Utilidad y límite.** Puede interesar cuando reducir escrituras importa más que reducir comparaciones. Esto no implica que sea siempre más rápida: el costo de comparar y escribir, el tamaño y el entorno también influyen.

**Error que evitar.** Intercambiar cada vez que aparece un candidato menor cambia la implementación y su cantidad de intercambios. Actualizar `minimum` no modifica el arreglo. Las versiones de los tres lenguajes de esta lectura evitan también el intercambio de una posición consigo misma.

<a id="concepto-4"></a>
### 1.7. Inserción por desplazamiento

**Concepto.** Inserción mantiene ordenados los elementos que ya procesó. Toma la siguiente clave, abre un espacio desplazando a la derecha los elementos mayores y guarda la clave en ese espacio. Es comparable a insertar una carta en una mano que ya está ordenada.

**Procedimiento.** El primer elemento forma un prefijo ordenado de longitud uno. Para cada `i` desde 1, se guarda `key = a[i]` y se inicia `j = i - 1`. Mientras `j` sea válido y `a[j] > key`, se copia `a[j]` en `a[j + 1]` y se decrementa `j`. Al terminar se escribe `key` en `a[j + 1]`.

Guardar la clave antes de desplazar es indispensable: la primera copia puede sobrescribir su posición original. Durante el desplazamiento pueden verse valores duplicados temporalmente en el arreglo; el valor guardado en `key` completa el estado lógico de los datos. La conservación de todos los elementos se recupera al terminar la inserción.

<a id="traza"></a>
**Traza completa por inserciones**, contando solo comparaciones entre claves:

| i | Clave | Comparaciones efectuadas, en orden | Estado al terminar | Desplazamientos |
|---:|---:|---|---|---:|
| 1 | 3 | `5 > 3`: V | `[3, 5, 4, 1, 2]` | 1 |
| 2 | 4 | `5 > 4`: V; `3 > 4`: F | `[3, 4, 5, 1, 2]` | 1 |
| 3 | 1 | `5 > 1`: V; `4 > 1`: V; `3 > 1`: V | `[1, 3, 4, 5, 2]` | 3 |
| 4 | 2 | `5 > 2`: V; `4 > 2`: V; `3 > 2`: V; `1 > 2`: F | `[1, 2, 3, 4, 5]` | 3 |
| **Total** | | **10 comparaciones de claves** | | **8 desplazamientos** |

En las inserciones de 3 y 1, el bucle termina al llegar `j` a -1; no existe una comparación adicional con `a[-1]`. En las otras dos inserciones se evalúa una comparación falsa entre claves antes de detenerse. La escritura final de `key` ocurre una vez por inserción, pero no se cuenta como desplazamiento.

**Invariante y terminación.** Antes de procesar `i`, el prefijo `a[0..i-1]` está ordenado. Desplazar únicamente las claves mayores conserva el orden de ese grupo y abre la posición de `key`. Al insertarla queda ordenado `a[0..i]`. El índice exterior avanza; el interior solo retrocede hasta detenerse o llegar al inicio.

**Estabilidad.** La condición estricta `a[j] > key` no desplaza claves iguales. La clave nueva se coloca después de las iguales que llegaron antes. Cambiar `>` por `>=` puede invertir empates. Para comprobar estabilidad, la comparación debe utilizar solo la clave de orden y el registro completo debe desplazarse como una unidad.

**Mejor caso.** Si la entrada está ordenada, cada una de las `n - 1` claves encuentra inmediatamente un elemento menor o igual. Hay `n - 1` comparaciones y cero desplazamientos. Las escrituras finales y el bucle exterior siguen ejecutándose: el tiempo es `Θ(n)`, no `Θ(1)`.

**Peor caso.** Con claves distintas en orden inverso, la clave de la iteración `i` es menor que las `i` anteriores. Se necesitan `i` comparaciones y `i` desplazamientos:

`D(n) = 1 + 2 + ... + (n - 1) = n(n - 1)/2`.

La cantidad de comparaciones de claves tiene ese mismo valor en este caso. El tiempo total es `Θ(n²)`. Que la implementación use `while` en vez de un segundo `for` no cambia este resultado: importa cuántas veces se ejecuta su cuerpo.

**Inversiones y adaptación.** Una inversión es un par de posiciones `p < q` cuyas claves cumplen `a[p] > a[q]`. Cada desplazamiento de inserción corrige una de esas inversiones. Si la entrada tiene `I` inversiones, se realizan exactamente `I` desplazamientos. El trabajo restante es lineal y el tiempo es `Θ(n + I)` para `n >= 1`. Esta expresión explica por qué se adapta a datos casi ordenados: si `I` es pequeño, hace poco trabajo adicional al recorrido básico.

**Error que evitar.** Comprobar primero `a[j] > key` y después `j >= 0`. La condición de índice debe proteger el acceso por cortocircuito. En Java un índice negativo causa una excepción; en Python puede leer desde el final; en JavaScript el acceso ordinario `a[-1]` no representa el último elemento. Ninguno expresa el recorrido buscado.

### 1.8. Caso promedio y comparación global

Con una permutación aleatoria de claves distintas, cada par tiene probabilidad 1/2 de estar invertido. Hay `n(n - 1)/2` pares; por tanto, el número esperado de inversiones es `n(n - 1)/4`.

Inserción debe corregir esas inversiones mediante desplazamientos y burbuja mediante intercambios adyacentes. Como el número esperado de esas operaciones es cuadrático y ambas implementaciones tienen una cota superior cuadrática, su tiempo promedio es `Θ(n²)`. Selección ya realiza un número cuadrático fijo de comparaciones en cualquier orden. Este argumento no supone que todos los conjuntos de datos del mundo real sean aleatorios.

<a id="concepto-5"></a>
| Método de esta lectura | Mejor tiempo | Tiempo promedio* | Peor tiempo | Memoria auxiliar** | Estable |
|---|---|---|---|---|---|
| Burbuja con bandera | Θ(n) | Θ(n²) | Θ(n²) | Θ(1) | Sí, con `>` |
| Selección con intercambio | Θ(n²) | Θ(n²) | Θ(n²) | Θ(1) | No |
| Inserción por desplazamiento | Θ(n) | Θ(n²) | Θ(n²) | Θ(1) | Sí, con `>` |

*Promedio sobre permutaciones uniformes de claves distintas. **Memoria adicional del algoritmo, sin contar la entrada, las copias de prueba ni la impresión. Las cotas describen el crecimiento con `n`; vacío y un elemento requieren trabajo constante.

**Ejemplo numérico.** Para `n = 1000`, selección realiza `1000 × 999 / 2 = 499500` comparaciones, aunque los datos estén ordenados. Burbuja con bandera e inserción realizan 999 comparaciones en una entrada ordenada. En sus peores casos, las tres alcanzan 499500 comparaciones con las implementaciones mostradas.

Tener la misma clase `Θ(n²)` no implica ejecutar exactamente las mismas operaciones ni tardar lo mismo. Tampoco unos pocos tiempos medidos demuestran una cota asintótica. Para comparar tiempos se deben usar los mismos datos originales, separar copia e impresión del intervalo medido y repetir las mediciones. La complejidad se justifica mediante el crecimiento del trabajo.

**Elección razonada.** Inserción suele ser una opción didáctica y práctica para secuencias pequeñas o con pocas inversiones; selección permite estudiar el intercambio entre comparaciones y escrituras; burbuja permite observar movimientos locales y terminación anticipada. Para grandes volúmenes generales se estudian métodos con mejores garantías, sin atribuirles aquí propiedades que aún no se han analizado.

**Error que evitar.** Concluir que todo arreglo «casi ordenado» hace lineal a burbuja. En `[2, 3, ..., n, 1]` hay solo `n - 1` inversiones, pero el 1 avanza una posición hacia la izquierda por pasada. Esta burbuja realiza trabajo cuadrático, mientras que inserción resuelve esa familia en tiempo lineal.

### 1.9. Particularidades de Java

Java utiliza aquí `int[]`: cada elemento es un entero con signo de 32 bits. Los índices también son enteros. La comparación directa con `>` evita calcular diferencias que podrían desbordarse al comparar valores extremos. Para un contador de operaciones potencialmente cuadrático conviene `long`; al calcular una fórmula, promover antes del producto: `(long) n * (n - 1) / 2`.

`a.clone()` crea un arreglo independiente. En un `int[]`, se copian valores primitivos; en un arreglo de objetos se copian referencias. `Arrays.equals(a, b)` comprueba el contenido de dos arreglos de enteros; `a == b` solo indica si ambas referencias apuntan al mismo objeto. `Arrays.toString` permite imprimir sus elementos.

`&&` evalúa primero la condición izquierda y no evalúa la derecha si la primera es falsa. Esto protege `a[j]` cuando `j` llega a -1. Los algoritmos son métodos `void`: modifican el arreglo recibido y no devuelven uno nuevo. La demostración usa una comprobación explícita que lanza `AssertionError`, por lo que no depende de activar `-ea`. Se ejecuta con un JDK compatible con el modo de archivo fuente, disponible desde Java 11, sin Maven ni Gradle.

<a id="demostracion"></a>
## 2. Demostración guiada

### 2.1. Problema y contrato

Ordenar `[5, 3, 4, 1, 2]` con burbuja, selección e inserción. Cada método recibe una copia independiente de la misma entrada. Mostrar las tres salidas y verificar cada implementación con diez casos compartidos entre lenguajes.

**Entrada válida:** una secuencia no nula de enteros dentro del rango de 32 bits con signo. Puede estar vacía, contener un solo elemento, valores negativos o repeticiones. Las funciones del núcleo presuponen esta entrada válida; no incluyen validación de tipos ni lectura de teclado. En Python se usan enteros, no booleanos; en JavaScript se usa un arreglo denso de números enteros, no cadenas ni valores ausentes.

**Efecto:** cada algoritmo modifica la secuencia que recibe en orden ascendente y conserva las multiplicidades. El original del programa permanece intacto porque se entregan copias. No se promete estabilidad para selección. Las pruebas numéricas no comprueban identidades de registros: esa comprobación forma parte de la PC.

**Verificación:** la ordenación de biblioteca se utiliza solamente para obtener el resultado esperado. Cada método se compara por separado con ese resultado; que tres implementaciones coincidan entre sí no bastaría si compartieran el mismo error.

### 2.2. Antes de ejecutar

1. Reproducir la primera pasada de cada algoritmo sobre la entrada principal.
2. Identificar qué posición o región queda ordenada y si sus posiciones ya son definitivas.
3. Predecir los resultados de vacío, un elemento, duplicados y entrada inversa.
4. Localizar las copias y distinguirlas del núcleo que modifica sus elementos.
5. Explicar por qué `minimum` se actualiza durante la búsqueda, pero el intercambio ocurre después.

### 2.3. Código completo

Guardar el bloque en `ejemplos/Demo.java` dentro de la carpeta de esta versión. El código está incluido íntegramente para poder copiarlo y ejecutarlo sin dependencias externas. Si el repositorio sincroniza estos bloques desde los archivos de ejemplos, actualizar también ese archivo fuente antes de ejecutar su sincronizador.

<!-- DEMO:START -->
```java
import java.util.Arrays;

public class Demo {
    static void swap(int[] a, int i, int j) {
        int temporary = a[i];
        a[i] = a[j];
        a[j] = temporary;
    }

    static void bubble(int[] a) {
        for (int end = a.length - 1; end > 0; end--) {
            boolean changed = false;
            for (int j = 0; j < end; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }
    }

    static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minimum = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minimum]) {
                    minimum = j;
                }
            }
            if (minimum != i) {
                swap(a, i, minimum);
            }
        }
    }

    static void insertion(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void verifyCases() {
        int[][] cases = {
            {5, 3, 4, 1, 2}, {}, {1}, {2, 2, 1}, {-1, 5, 0},
            {1, 2, 3}, {3, 2, 1}, {7, 7, 7}, {2, 1},
            {Integer.MAX_VALUE, 0, Integer.MIN_VALUE}
        };
        for (int[] input : cases) {
            int[] original = input.clone();
            int[] expected = input.clone();
            Arrays.sort(expected); // Solo oráculo de comprobación.
            int[] b = input.clone();
            int[] s = input.clone();
            int[] i = input.clone();
            bubble(b);
            selection(s);
            insertion(i);
            check(Arrays.equals(b, expected), "Fallo en burbuja");
            check(Arrays.equals(s, expected), "Fallo en selección");
            check(Arrays.equals(i, expected), "Fallo en inserción");
            check(Arrays.equals(input, original), "Se modificó el original");
        }
    }

    public static void main(String[] args) {
        verifyCases();
        int[] original = {5, 3, 4, 1, 2};
        int[] b = original.clone();
        int[] s = original.clone();
        int[] i = original.clone();
        bubble(b);
        selection(s);
        insertion(i);
        System.out.println("Original: " + Arrays.toString(original));
        System.out.println("Burbuja: " + Arrays.toString(b));
        System.out.println("Selección: " + Arrays.toString(s));
        System.out.println("Inserción: " + Arrays.toString(i));
        System.out.println("Verificación: 10 casos correctos por algoritmo.");
    }
}
```
<!-- DEMO:END -->

### 2.4. Ejecución

En la terminal integrada de VS Code, situarse en `02-searching-and-sorting-algorithms/2.1.1-direct-sorts/java` y ejecutar:

```bash
java ejemplos/Demo.java
```

También se puede compilar con `javac ejemplos/Demo.java` y ejecutar con `java -cp ejemplos Demo`. No se utiliza declaración de paquete en este ejemplo.

### 2.5. Salida esperada

```text
Original: [5, 3, 4, 1, 2]
Burbuja: [1, 2, 3, 4, 5]
Selección: [1, 2, 3, 4, 5]
Inserción: [1, 2, 3, 4, 5]
Verificación: 10 casos correctos por algoritmo.
```

Las comprobaciones se ejecutan antes de imprimir. Una excepción indica que una comparación con el resultado esperado o la conservación del original ha fallado. La última línea solo aparece si se superan todas las comprobaciones.

| Caso de prueba | Resultado esperado |
|---|---|
| `[5, 3, 4, 1, 2]` | `[1, 2, 3, 4, 5]` |
| `[]` | `[]` |
| `[1]` | `[1]` |
| `[2, 2, 1]` | `[1, 2, 2]` |
| `[-1, 5, 0]` | `[-1, 0, 5]` |
| `[1, 2, 3]` | `[1, 2, 3]` |
| `[3, 2, 1]` | `[1, 2, 3]` |
| `[7, 7, 7]` | `[7, 7, 7]` |
| `[2, 1]` | `[1, 2]` |
| `[2147483647, 0, -2147483648]` | `[-2147483648, 0, 2147483647]` |

Son pruebas de la demostración numérica, no los casos de aceptación de la PC de registros. El caso de valores extremos permite detectar errores si se sustituye una comparación directa por una resta que desborda en Java.

### 2.6. Interpretación de costos

Para la entrada principal, las trazas desarrolladas en la sección 1 producen:

| Método | Comparaciones de claves | Intercambios | Desplazamientos |
|---|---:|---:|---:|
| Burbuja | 10 | 8 | No utiliza este movimiento |
| Selección | 10 | 4 | No utiliza este movimiento |
| Inserción | 10 | No utiliza intercambios | 8 |

Los conteos de esta tabla se deducen de las trazas; el programa mostrado verifica salidas y **no imprime contadores**. En inserción se agregan cuatro escrituras finales de la clave, una por iteración exterior, que no están incluidas en sus ocho desplazamientos. La PC pide instrumentar el algoritmo con una convención explícita.

Cada núcleo conserva memoria auxiliar Θ(1). La demostración necesita Θ(n) memoria adicional por las copias de tamaño `n`. Un número constante de copias sigue ocupando espacio lineal: no debe concluirse que el programa completo usa memoria constante porque sus algoritmos individuales sí lo hacen.

Ejecutar los tres métodos consecutivamente suma sus costos. Como selección siempre es cuadrática, la ejecución conjunta de los tres núcleos tiene tiempo Θ(n²), incluso si los otros dos encuentran datos ordenados. La biblioteca de verificación, las copias y la impresión son tareas adicionales y deben distinguirse de las operaciones estudiadas. La comprobación con diez entradas fijas es una prueba funcional; no mide cómo crece el tiempo con entradas arbitrariamente grandes.

<a id="pc"></a>
## 3. Práctica

### 3.1. Indicaciones comunes

Los ejercicios se presentan **sin solución**. Resolver primero las trazas y los conteos a mano; después contrastar con una ejecución. Una salida ordenada no sustituye la explicación del procedimiento ni demuestra por sí sola estabilidad o complejidad.

En cada traza indicar la entrada, el criterio de orden, las variables relevantes, el estado después de cada pasada y el invariante. Al contar, separar comparaciones de claves, intercambios y desplazamientos según la sección 1.3. No contar comprobaciones de índices como comparaciones de claves.

Los ejercicios 1 a 24 preparan la PC integradora. Se mantiene una sola PC para este contenido; no son 24 proyectos independientes ni requieren una rúbrica individual.

### 3.2. Nivel 1: conceptos y trazas

| N.º | Actividad | Evidencia solicitada |
|---:|---|---|
| 1 | Para `[4, -2, 4, 0]`, escribir una salida ascendente y una descendente. | Explicar cómo se conservan las repeticiones. |
| 2 | Ordenar manualmente `[(A,7),(B,3),(C,7),(D,3)]` por nota. | Distinguir una salida estable de otra que solo esté ordenada. |
| 3 | Ejecutar burbuja sobre `[4, 1, 3, 2]`. | Estado después de cada intercambio y valor de la bandera al final de cada pasada. |
| 4 | Ejecutar selección sobre `[4, 1, 3, 2]`. | Valores sucesivos de `minimum` y estado después de cada intercambio final. |
| 5 | Ejecutar inserción sobre `[4, 1, 3, 2]`. | Clave guardada, desplazamientos y posición de inserción. |
| 6 | Ejecutar los tres métodos sobre `[2, 2, 2, 2]`. | Comparaciones de claves y movimientos de cada método. |
| 7 | Revisar los bucles con `[]` y `[9]`. | Explicar por qué no acceden a posiciones inválidas. |
| 8 | Trazar inserción sobre `[3, 5, 2]`. | Identificar cuándo el arreglo contiene temporalmente un duplicado y dónde se conserva la clave. |

### 3.3. Nivel 2: conteos y complejidad

Para cada respuesta indicar el tamaño `n`, la operación contada, el caso de entrada y la justificación. No responder solamente «O(n²) porque tiene dos bucles».

| N.º | Problema |
|---:|---|
| 9 | Calcular las comparaciones de selección para `n = 8` y `n = 100`. Derivar la suma antes de sustituir. |
| 10 | Comparar los conteos de burbuja para `[1,2,3,4,5,6]` y `[6,5,4,3,2,1]`. Indicar la función de la bandera. |
| 11 | Derivar comparaciones y desplazamientos de inserción sobre una secuencia ordenada de tamaño `n >= 2`. |
| 12 | Derivar comparaciones y desplazamientos de inserción sobre una secuencia inversa de claves distintas. |
| 13 | Enumerar las inversiones de `[3,1,4,2]` y contrastarlas con los desplazamientos de inserción. |
| 14 | Analizar la familia `[2,3,...,n,1]` con burbuja e inserción. Justificar sus diferencias sin usar tiempos medidos. |
| 15 | Calcular el cociente `C(2n)/C(n)` para selección, con `n >= 2`, y explicar su tendencia cuando crece `n`. |
| 16 | Si comparar dos claves de texto cuesta hasta O(m), expresar una cota superior temporal de selección en función de `n` y `m`. Distinguir número de comparaciones de costo total. |

### 3.4. Nivel 3: detectar y corregir errores

Para cada afirmación, explicar si es válida. Si es falsa, aportar un contraejemplo o corregir la condición necesaria.

| N.º | Afirmación a revisar |
|---:|---|
| 17 | «Selección es estable porque nunca compara usando `<=`». |
| 18 | «Burbuja siempre cuesta Θ(n²), incluso con bandera y entrada ordenada». |
| 19 | «Inserción tiene memoria auxiliar Θ(n) porque la lista de entrada almacena n elementos». |
| 20 | «Tres ordenamientos Θ(n²) ejecutados uno después del otro producen Θ(n⁶)». |
| 21 | «Si la salida está ordenada, el algoritmo es correcto aunque haya eliminado un valor repetido». |
| 22 | «Cambiar `>` por `>=` en inserción solo modifica la velocidad; nunca cambia la estabilidad». |
| 23 | «Medir cada algoritmo sobre el mismo arreglo ya modificado garantiza una comparación justa». |
| 24 | «Si dos registros tienen la misma nota, agregar el código como desempate equivale a preservar siempre su orden de llegada». |

### 3.5. PC2.1.1 · Ordenamiento estable de calificaciones

**Contexto.** Una lista registra estudiantes en orden de llegada. Cada registro contiene un código, una nota y su posición original. Se requiere ordenarlos por nota ascendente sin cambiar el orden de llegada entre estudiantes con la misma nota.

**Contrato de entrada.** Una secuencia de registros con `codigo` no vacío, `nota` entera entre 0 y 10 y `posicionOriginal` igual a su índice de llegada desde cero. Los códigos deben ser únicos. No se aceptan registros incompletos, notas textuales, booleanos como notas ni valores fuera del rango. La lista vacía es válida. Validar toda la entrada antes de ordenarla; ante un error, informar su causa y conservarla sin modificaciones.

**Contrato de salida.** Una copia ordenada por nota que contenga los mismos registros completos. La secuencia original mantiene su orden. Los registros con notas iguales conservan su orden de llegada. `posicionOriginal` se utiliza para comprobar ese requisito; no se modifica ni se usa como una segunda clave para ocultar un algoritmo inestable.

**Trabajo solicitado:**

1. Implementar inserción estable por desplazamiento para registros completos, comparando únicamente `nota`.
2. Separar validación, ordenamiento y presentación de resultados. No usar ordenación de biblioteca en la implementación de la PC.
3. Mantener intacta la secuencia original mediante una copia superficial; no modificar los campos de los registros.
4. Contar **comparaciones de notas**: cada evaluación efectiva de «nota anterior > nota de la clave», incluida la falsa que detiene la búsqueda cuando se evalúa.
5. Contar **desplazamientos**: cada copia de un registro una posición a la derecha. No incluir la colocación final de la clave, la copia inicial ni la validación.
6. Mostrar original, resultado, posiciones originales, comparaciones y desplazamientos. Explicar por qué los empates conservan su orden.
7. Añadir un modo de traza para observar el prefijo ordenado después de cada inserción. La impresión se estudia separadamente del costo del núcleo.
8. Justificar el mejor y peor tiempo del ordenamiento y distinguir su memoria auxiliar de la memoria de la copia y de la validación elegida.

La demostración anterior ordena enteros. Esta PC requiere adaptar la comparación a una clave, conservar registros e identidades, instrumentar operaciones y validar el contrato. No se proporciona la solución completa.

### 3.6. Casos de aceptación de la PC

En la tabla se abrevia cada registro como `(código, nota)`; su posición original se obtiene del orden de entrada. Los conteos corresponden exclusivamente al núcleo de inserción con comparación estricta.

| Entrada o escenario | Códigos en la salida | Comparaciones | Desplazamientos | Condición adicional |
|---|---|---:|---:|---|
| `[(A,8),(B,5),(C,8)]` | B, A, C | 2 | 1 | A conserva precedencia sobre C |
| `[]` | Lista vacía | 0 | 0 | Sin accesos inválidos |
| `[(A,7)]` | A | 0 | 0 | Registro conservado |
| `[(A,8),(B,8)]` | A, B | 1 | 0 | Empate sin invertir |
| `[(A,1),(B,5),(C,9)]` | A, B, C | 2 | 0 | Entrada ya ordenada |
| `[(A,9),(B,7),(C,5)]` | C, B, A | 3 | 3 | Orden inverso |
| `[(A,8),(B,5),(C,8),(D,6)]` | B, D, A, C | 5 | 3 | Posiciones originales: 1, 3, 0, 2 |
| `[(A,0),(B,10)]` | A, B | 1 | 0 | Límites válidos |
| Nota -1, 11, `"8"` o booleana | Rechazo con causa | No iniciar | No iniciar | Entrada intacta |
| Código vacío, repetido o campo ausente | Rechazo con causa | No iniciar | No iniciar | Entrada intacta |
| Posición original incoherente con la llegada | Rechazo con causa | No iniciar | No iniciar | Entrada intacta |

Añadir un caso propio de al menos cinco registros y un control del invariante después de cada inserción. Justificar por qué la validación de códigos únicos podría agregar costo: buscar duplicados con dos bucles, por ejemplo, puede hacer cuadrática la validación aunque la entrada ya esté ordenada por nota. Reportar por separado el núcleo de ordenamiento y el programa completo.

### 3.7. Entrega, ejecución y evidencia

Implementar la PC en **una versión**, a elección entre Java, Python y JavaScript. Las PL de la asignatura se entregan exclusivamente en Java. La actividad no requiere un proyecto acumulativo ni dependencias externas.

Desde la carpeta de la entrega personal, ejecutar:

```bash
java Main.java
```

Si se usan paquetes de Java, ajustar la compilación y ejecución a esa estructura; el comando anterior presupone un archivo `Main.java` sin declaración de paquete.

Entregar código fuente y `evidencias/PC2.1.1.md` en el repositorio personal. La evidencia debe contener: contrato, traza manual, comandos, entrada, resultado esperado, resultado real, conteos, comprobación de estabilidad, análisis temporal y espacial, y explicación de cualquier diferencia. Los resultados deben proceder de ejecuciones reales.

Crear commits por avances funcionales, adaptando los mensajes al cambio realizado:

```bash
git add .
git commit -m "feat(pc2.1.1): ordenar registros por nota"
# Después de implementar y comprobar nuevos casos:
git add .
git commit -m "test(pc2.1.1): verificar estabilidad y conteos"
# Después de documentar el análisis:
git add .
git commit -m "docs(pc2.1.1): justificar complejidad"
git rev-parse HEAD
```

Para revisar una etapa, ejecutar `git show HASH` con el hash real correspondiente. Para comparar dos avances, usar `git diff HASH_ANTERIOR HASH_ACTUAL`. No inventar identificadores ni sustituir los resultados por capturas sin datos reproducibles.

<a id="esencial"></a>
## Lo esencial

- Ordenar exige cumplir el criterio y conservar todos los elementos, incluidas sus repeticiones.
- La estabilidad conserva el orden relativo de registros con la misma clave; no se observa comparando únicamente números repetidos.
- Burbuja fija un sufijo; selección fija un prefijo; inserción mantiene ordenado el prefijo procesado.
- La bandera hace lineal el mejor caso de esta burbuja. Selección mantiene comparaciones cuadráticas en todos los casos.
- Inserción realiza un desplazamiento por inversión y tiene tiempo Θ(n + I) bajo el modelo indicado.
- El conteo exacto requiere definir qué operación se cuenta y qué implementación se analiza.
- Los tres núcleos usan memoria auxiliar constante; las copias de la demostración o de la PC requieren memoria lineal.
- La corrección se justifica mediante invariantes y conservación; las pruebas detectan errores en casos concretos.

## Referencias y lectura de apoyo

- Oracle. [Arrays: equals, sort y toString](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Arrays.html). Consulta sobre los auxiliares de comprobación; no define los algoritmos implementados aquí.
- El análisis de complejidad de esta lectura se deriva de los límites y condiciones de sus implementaciones. Las cotas no se atribuyen a los métodos de ordenación de biblioteca utilizados como oráculo.
- [Bibliografía y análisis de fuentes del curso](../../../docs/analisis-referencias.md).

[Volver al índice del curso](../../../index.html)
