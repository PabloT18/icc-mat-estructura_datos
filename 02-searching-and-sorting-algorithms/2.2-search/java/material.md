# Estructura de Datos

![UPS · Carrera de Computación](../../../assets/logos/ups-computacion.png)

## 2.2 Búsqueda secuencial y binaria · Java

**Autor:** Ing. Pablo Torres, Mgtr.  
**Unidad 2:** Ordenamiento, búsqueda y recursividad  
**Lenguaje:** Java 17 o posterior

[Índice del curso](../../../index.html)

### Índice de contenidos

- [1. Conceptos y ejemplos](#conceptos)
  - [1.1. Qué significa buscar](#contrato)
  - [1.2. Búsqueda secuencial en arreglos](#secuencial)
  - [1.3. Casos especiales y duplicados](#duplicados)
  - [1.4. Búsqueda secuencial en objetos y listas](#objetos)
  - [1.5. Precondición de búsqueda binaria](#precondicion)
  - [1.6. Búsqueda binaria de una coincidencia](#binaria)
  - [1.7. Intervalos e invariantes](#invariante)
  - [1.8. Primera coincidencia: lower bound](#lower)
  - [1.9. Última posición exclusiva: upper bound](#upper)
  - [1.10. Rango de duplicados](#rango)
  - [1.11. Trazas de ejecución](#trazas)
  - [1.12. Complejidad temporal y espacial](#complejidad)
  - [1.13. Preparación: ordenar antes de buscar](#preparacion)
  - [1.14. Herramientas de la API de Java](#api)
  - [1.15. Búsqueda de objetos con Comparator](#comparator)
  - [1.16. Errores frecuentes](#errores)
- [2. Demostración guiada](#demostracion)
- [3. Práctica y PC](#pc)
- [Lo esencial](#esencial)
- [Referencias](#referencias)

Este material estudia cómo localizar valores y registros mediante recorrido secuencial y reducción binaria del intervalo. Se distinguen contratos de búsqueda, precondiciones, primeras coincidencias, rangos de duplicados, costo de preparación y uso de la API de Java. Los ejemplos de objetos conectan el tema con `Comparator` y `Comparable`.

<a id="conceptos"></a>
## 1. Conceptos y ejemplos

**Prerrequisitos:** arreglos, listas, clases, métodos, bucles, condiciones, índices y notación asintótica. Se utilizan índices desde cero. Los fragmentos de código se interpretan dentro de métodos o clases con las importaciones necesarias; la sección 2 incluye un programa ejecutable completo.

<a id="contrato"></a>
### 1.1. Qué significa buscar

Buscar consiste en determinar si existe un elemento cuya clave satisface el criterio solicitado y, cuando corresponde, devolver su posición. Conviene definir qué significa una coincidencia y qué sucede con los valores repetidos **antes** de escribir el algoritmo.

| Contrato | Entrada | Salida |
|---|---|---|
| Existencia | Secuencia y clave | `true` o `false` |
| Posición cualquiera | Secuencia y clave | Un índice válido o `-1` |
| Primera posición | Secuencia y clave | Menor índice coincidente o `-1` |
| Rango de coincidencias | Secuencia ordenada y clave | Intervalo `[inicio, fin)` o `[-1, -1]` |

En este material, `sequentialFirst` y `binaryFirst` devuelven la **primera posición**. Cuando se solicita el rango, `fin` es exclusivo: la cantidad de coincidencias es `fin - inicio`. Los algoritmos no modifican la entrada. Un arreglo vacío es válido y devuelve ausencia. Un arreglo `null` se rechaza expresamente con `IllegalArgumentException` en la demostración.

**Ejemplo:** `int[] a = {7, 4, 7, 2};`. Buscar `7` con contrato de primera coincidencia devuelve `0`, no `2`; buscar `9` devuelve `-1`. La secuencia no necesita estar ordenada para el recorrido secuencial.

<a id="secuencial"></a>
### 1.2. Búsqueda secuencial en arreglos

La búsqueda secuencial visita las posiciones desde el principio y termina cuando encuentra una coincidencia. No requiere ordenar los datos.

```java
static int sequentialFirst(int[] datos, int clave) {
    for (int i = 0; i < datos.length; i++) {
        if (datos[i] == clave) {
            return i;
        }
    }
    return -1;
}
```

```java
int[] datos = {8, 3, 5, 3, 1};
System.out.println(sequentialFirst(datos, 3)); // 1
System.out.println(sequentialFirst(datos, 9)); // -1
```

La condición `datos[i] == clave` compara valores enteros, no posiciones. El `return` dentro del bucle garantiza que se conserve la primera coincidencia. No se debe ordenar el arreglo antes de ejecutar este ejemplo: cambiaría la posición solicitada.

**Recorrido paso a paso para clave 5:**

| Índice `i` | `datos[i]` | ¿Coincide? | Acción |
|---:|---:|---|---|
| 0 | 8 | No | Continuar |
| 1 | 3 | No | Continuar |
| 2 | 5 | Sí | Devolver `2` |

<a id="duplicados"></a>
### 1.3. Casos especiales y duplicados

| Entrada | Clave | Resultado de primera posición | Comparaciones de igualdad |
|---|---:|---:|---:|
| `[4, 4, 9]` | 4 | 0 | 1 |
| `[4, 4, 9]` | 9 | 2 | 3 |
| `[4, 4, 9]` | 7 | -1 | 3 |
| `[]` | 4 | -1 | 0 |
| `[5]` | 5 | 0 | 1 |

Si se necesita **todas las posiciones** de un arreglo no ordenado, un recorrido completo puede agregarlas a una lista:

```java
static List<Integer> sequentialAll(int[] datos, int clave) {
    List<Integer> posiciones = new ArrayList<>();
    for (int i = 0; i < datos.length; i++) {
        if (datos[i] == clave) posiciones.add(i);
    }
    return posiciones;
}
// sequentialAll(new int[]{4, 2, 4, 7, 4}, 4) → [0, 2, 4]
```

El costo de recorrer todas las posiciones es `Θ(n)` incluso cuando la primera coincide. La memoria del resultado depende del número `k` de coincidencias: `Θ(k)` sin contar la entrada.

<a id="objetos"></a>
### 1.4. Búsqueda secuencial en objetos y listas

En objetos, la coincidencia debe establecerse mediante un atributo o criterio explícito. `==` entre referencias pregunta si son la **misma instancia**; `equals` consulta la igualdad definida por la clase.

```java
record Estudiante(String codigo, String nombre, double nota) {}

static int buscarCodigo(List<Estudiante> estudiantes, String codigo) {
    for (int i = 0; i < estudiantes.size(); i++) {
        if (estudiantes.get(i).codigo().equals(codigo)) return i;
    }
    return -1;
}
```

```java
List<Estudiante> lista = List.of(
    new Estudiante("E03", "Marta", 8.5),
    new Estudiante("E01", "Luis", 6.2),
    new Estudiante("E02", "Ana", 8.5)
);
System.out.println(buscarCodigo(lista, "E01")); // 1
```

Aquí la igualdad se decide por código. Dos estudiantes con idéntica nota no son necesariamente el mismo registro. En una `ArrayList`, `get(i)` es de tiempo constante; en una lista enlazada, acceder repetidamente por índice es más costoso. Para una búsqueda secuencial en cualquier `List`, un bucle `for (Estudiante e : lista)` evita esos accesos indexados si solo se necesita el objeto y no el índice.

<a id="precondicion"></a>
### 1.5. Precondición de búsqueda binaria

La búsqueda binaria necesita **acceso indexado eficiente** y una secuencia ordenada por el **mismo criterio** utilizado para buscar. Con números ascendentes, toda posición anterior a una dada contiene un valor menor o igual; esta propiedad permite descartar regiones enteras.

```java
int[] ordenado = {1, 3, 3, 7, 9}; // válido para comparación ASC
int[] desordenado = {3, 1, 9, 3, 7}; // no cumple precondición
```

Un algoritmo binario no ordena automáticamente. Aplicarlo al segundo arreglo puede devolver un índice incorrecto o una ausencia falsa. Si se usa orden descendente, también debe adaptarse el sentido de la comparación. Si se ordena por nota pero se busca por código, el orden de las notas no justifica una búsqueda binaria por código.

<a id="binaria"></a>
### 1.6. Búsqueda binaria de una coincidencia

La variante habitual busca cualquier coincidencia y retorna inmediatamente cuando `datos[mid] == clave`. **No garantiza devolver la primera** si existen duplicados.

```java
static int binaryAny(int[] datos, int clave) {
    int lo = 0;
    int hi = datos.length - 1; // extremo incluido
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (datos[mid] == clave) return mid;
        if (datos[mid] < clave) lo = mid + 1;
        else hi = mid - 1;
    }
    return -1;
}
```

Para `[1, 3, 3, 3, 8]` y clave `3`, esta implementación puede devolver `2`. Es correcto para el contrato de **cualquier coincidencia**, pero no para el contrato de **primera coincidencia**. El cálculo `lo + (hi - lo) / 2` evita el desbordamiento que podría provocar `(lo + hi) / 2` en índices enteros grandes y no negativos.

<a id="invariante"></a>
### 1.7. Intervalos e invariantes

Un **invariante** es una propiedad que se mantiene antes y después de cada iteración. Para buscar la primera posición cuyo valor es mayor o igual que la clave, usamos `[lo, hi)`: `lo` incluido y `hi` excluido.

Al inicio `lo = 0` y `hi = n`. En cada iteración se mantiene que:

1. Los índices anteriores a `lo` contienen valores menores que la clave.
2. Los índices desde `hi` hasta `n - 1` contienen valores mayores o iguales que la clave.
3. La primera posición mayor o igual, o `n` si no existe, sigue estando en el intervalo candidato `[lo, hi]` en términos de sus fronteras.

Si `datos[mid] < clave`, se descartan los índices hasta `mid`, incluido: `lo = mid + 1`. Si `datos[mid] >= clave`, `mid` todavía puede ser la primera posición válida: `hi = mid`. Ambas actualizaciones reducen estrictamente `hi - lo` cuando `lo < hi`. El algoritmo termina cuando `lo == hi`.

<a id="lower"></a>
### 1.8. Primera coincidencia: `lower bound`

`lowerBound` devuelve la primera posición cuyo valor es **mayor o igual** que la clave. Puede devolver `n`. Por sí solo no afirma que la clave exista.

```java
static int lowerBound(int[] datos, int clave) {
    int lo = 0, hi = datos.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (datos[mid] < clave) lo = mid + 1;
        else hi = mid;
    }
    return lo;
}

static int binaryFirst(int[] datos, int clave) {
    int pos = lowerBound(datos, clave);
    return pos < datos.length && datos[pos] == clave ? pos : -1;
}
```

| Datos | Clave | `lowerBound` | `binaryFirst` |
|---|---:|---:|---:|
| `[1, 3, 3, 7, 9]` | 3 | 1 | 1 |
| `[1, 3, 3, 7, 9]` | 4 | 3 | -1 |
| `[1, 3, 3, 7, 9]` | 10 | 5 | -1 |
| `[]` | 2 | 0 | -1 |

La condición final es necesaria: la primera posición con valor mayor o igual puede contener un valor **mayor**, no igual. El acceso `datos[pos]` solo se realiza si `pos < datos.length` gracias al cortocircuito de `&&`.

<a id="upper"></a>
### 1.9. Última posición exclusiva: `upper bound`

`upperBound` devuelve la primera posición cuyo valor es **estrictamente mayor** que la clave. Para claves repetidas, es la frontera posterior a todas sus apariciones.

```java
static int upperBound(int[] datos, int clave) {
    int lo = 0, hi = datos.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (datos[mid] <= clave) lo = mid + 1;
        else hi = mid;
    }
    return lo;
}
```

La diferencia esencial frente a `lowerBound` es `<=` en lugar de `<`. En `[1, 3, 3, 3, 8]`, `lowerBound(3)` devuelve `1` y `upperBound(3)` devuelve `4`.

<a id="rango"></a>
### 1.10. Rango de duplicados

En una secuencia ordenada, todas las coincidencias de una clave forman un bloque contiguo. Por ello, dos búsquedas binarias localizan las fronteras **sin recorrer linealmente los duplicados**.

```java
static int[] binaryRange(int[] datos, int clave) {
    int inicio = lowerBound(datos, clave);
    if (inicio == datos.length || datos[inicio] != clave) {
        return new int[]{-1, -1};
    }
    return new int[]{inicio, upperBound(datos, clave)};
}
```

| Datos | Clave | Rango `[inicio, fin)` | Cantidad |
|---|---:|---|---:|
| `[1, 3, 3, 3, 8]` | 3 | `[1, 4)` | 3 |
| `[1, 3, 3, 3, 8]` | 1 | `[0, 1)` | 1 |
| `[1, 3, 3, 3, 8]` | 8 | `[4, 5)` | 1 |
| `[1, 3]` | 2 | `[-1, -1]` | 0 |
| `[]` | 1 | `[-1, -1]` | 0 |

La PC de la sección 3 solicita precisamente este comportamiento. Los límites de salida se muestran como dos enteros en Java: `new int[]{inicio, fin}`.

<a id="trazas"></a>
### 1.11. Trazas de ejecución

**Traza de `lowerBound`**, arreglo `[1, 3, 3, 7, 9]`, clave `3`:

| Paso | `lo` | `hi` | `mid` | `datos[mid]` | Actualización |
|---:|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | `hi = 2` |
| 2 | 0 | 2 | 1 | 3 | `hi = 1` |
| 3 | 0 | 1 | 0 | 1 | `lo = 1` |
| Fin | 1 | 1 | — | — | Primera coincidencia: `1` |

**Traza de `upperBound`**, arreglo `[1, 3, 3, 3, 8]`, clave `3`:

| Paso | `lo` | `hi` | `mid` | `datos[mid]` | Actualización |
|---:|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | `lo = 3` |
| 2 | 3 | 5 | 4 | 8 | `hi = 4` |
| 3 | 3 | 4 | 3 | 3 | `lo = 4` |
| Fin | 4 | 4 | — | — | Primera posición mayor que 3: `4` |

La traza ilustra una ejecución particular. La justificación general requiere mostrar conservación del invariante, reducción del intervalo y corrección de la comprobación final.

<a id="complejidad"></a>
### 1.12. Complejidad temporal y espacial

Sea `n` la cantidad de elementos y supongamos comparación y acceso indexado `O(1)`.

| Operación | Mejor caso | Peor caso | Memoria auxiliar |
|---|---|---|---|
| Secuencial primera coincidencia | `Θ(1)` si `n > 0` y coincide al inicio | `Θ(n)` | `Θ(1)` |
| Secuencial todas las posiciones | `Θ(n)` para recorrer `n` elementos | `Θ(n)` | `Θ(k)` para `k` resultados |
| Binaria con retorno inmediato (`binaryAny`) | `Θ(1)` cuando el punto medio coincide | `Θ(log(n + 1))` | `Θ(1)` |
| `lowerBound` / `upperBound` | `Θ(log(n + 1))` para `n > 1` | `Θ(log(n + 1))` | `Θ(1)` |
| Rango con dos límites | `Θ(log(n + 1))` para `n > 1` | `Θ(log(n + 1))` | `Θ(1)` |
| Validar el orden de todo el arreglo | `Θ(n)` si se inspeccionan todas las parejas | `Θ(n)` | `Θ(1)` |

Para `n = 0` las búsquedas binarias finalizan en tiempo constante. El uso de `log(n + 1)` evita la expresión `log 0` y describe la escala asintótica; no representa un número exacto de iteraciones.

**Por qué la binaria es logarítmica:** tras `t` divisiones queda un intervalo de tamaño aproximadamente `n / 2^t`. El procedimiento termina cuando alcanza un tamaño constante, lo que ocurre para `t` proporcional a `log₂ n`. Dos búsquedas binarias consecutivas cuestan `O(log n) + O(log n) = O(log n)`, no `O(log² n)`.

**Costo de comparar:** si comparar claves cuesta `O(m)`, una búsqueda secuencial tiene cota `O(nm)` y una búsqueda binaria `O(m log(n + 1))`, suponiendo que acceder a la posición media sigue siendo `O(1)`. Los `String` largos pueden requerir revisar múltiples caracteres.

**No confundir preparación y consulta:** comprobar que todo un arreglo está ordenado mediante un recorrido introduce costo `Θ(n)` adicional. Si se realiza en **cada** búsqueda, la operación completa ya no tiene cota puramente logarítmica. En una lista enlazada, calcular el punto medio por desplazamientos cambia el análisis y suele anular la ventaja práctica de la búsqueda binaria.

<a id="preparacion"></a>
### 1.13. Preparación: ordenar antes de buscar

Si los datos no están ordenados, una alternativa consiste en copiar y ordenar una sola vez, para luego realizar muchas consultas.

| Estrategia para `q` consultas | Costo típico o cota |
|---|---|
| `q` búsquedas secuenciales | `O(qn)` |
| Copiar + ordenar + `q` búsquedas binarias | `O(n log n + q log(n + 1))` |
| Validar orden en cada búsqueda y luego buscar | `O(qn + q log(n + 1))` |

Estas expresiones suponen claves de comparación constantes y un ordenamiento de `O(n log n)`. Para **una sola consulta** sobre datos desordenados, ordenar primero suele ser un trabajo innecesario. Para **muchas consultas** sobre datos que cambian poco, el costo de preparación puede amortizarse. No se deduce un umbral universal solo con notación asintótica.

**Importante:** ordenar altera las posiciones originales. Si una aplicación necesita devolver el índice de llegada, debe conservar la relación entre la posición original y el elemento ordenado, por ejemplo mediante registros que almacenen `indiceOriginal`.

<a id="api"></a>
### 1.14. Herramientas de la API de Java

`Arrays.binarySearch` y `Collections.binarySearch` permiten localizar una coincidencia en datos ordenados; **no garantizan la primera posición** de duplicados.

```java
int[] valores = {1, 3, 3, 7, 9};
int indice = Arrays.binarySearch(valores, 7); // 3
int ausente = Arrays.binarySearch(valores, 4); // -4
```

Cuando no hay coincidencia, la API devuelve `-(insertionPoint) - 1`, donde `insertionPoint` es la posición en que se insertaría la clave para conservar el orden. Para clave `4`, el punto de inserción es `3`, por eso el resultado es `-4`. No debe confundirse con el contrato didáctico que devuelve exactamente `-1` para cualquier ausencia.

```java
int resultado = Arrays.binarySearch(valores, 4);
int insercion = resultado >= 0 ? resultado : -resultado - 1; // 3
```

```java
List<Integer> nums = List.of(1, 3, 3, 7, 9);
int posicion = Collections.binarySearch(nums, 7); // 3
```

`Collections.binarySearch` también tiene una versión que recibe un `Comparator`. En listas sin acceso aleatorio eficiente, su implementación puede utilizar una estrategia de recorrido diferente y aumentar el costo de desplazamientos. Para el contrato de primera coincidencia y rango de duplicados, los algoritmos explícitos `lowerBound` y `upperBound` son más apropiados para el estudio.

<a id="comparator"></a>
### 1.15. Búsqueda de objetos con `Comparator`

El criterio de búsqueda debe ser **coherente con el orden usado para preparar los objetos**. Si los estudiantes se ordenan por código, se comparan códigos durante la búsqueda. Se utiliza el mismo comparador para ordenar y para determinar las fronteras.

```java
record Producto(String codigo, double precio) {}

Comparator<Producto> porCodigo =
    Comparator.comparing(Producto::codigo);

List<Producto> productos = new ArrayList<>(List.of(
    new Producto("P03", 10),
    new Producto("P01", 20),
    new Producto("P02", 15)
));
productos.sort(porCodigo);

Producto buscado = new Producto("P02", 0);
int pos = Collections.binarySearch(productos, buscado, porCodigo);
// pos == 1; el precio no interviene en este criterio
```

**Precondición:** `porCodigo` compara únicamente códigos no nulos. El objeto usado como clave solo necesita un código válido porque el comparador ignora el precio. Si se cambia el criterio a precio sin reordenar la lista, la precondición deja de cumplirse.

**Orden natural:** una clase que implementa `Comparable<Producto>` puede permitir `Collections.binarySearch(productos, clave)` sin comparador explícito, siempre que la lista esté ordenada por ese mismo `compareTo`. Tener `Comparable` no hace que los datos se ordenen automáticamente.

**Duplicados:** el resultado de `Collections.binarySearch` puede ser cualquiera de los registros con igual código según el comparador. Si el contrato exige la primera posición, hay que implementar una búsqueda de frontera con ese mismo criterio.

<a id="errores"></a>
### 1.16. Errores frecuentes

| Error | Causa y corrección |
|---|---|
| Usar binaria en datos desordenados | No se puede descartar media región; ordenar antes o usar secuencial |
| Ordenar por nota y buscar binariamente por nombre | Criterios incompatibles; usar el mismo orden y comparación |
| Retornar `mid` al encontrar igualdad y afirmar que es la primera | Un duplicado anterior puede existir; usar `lowerBound` |
| Usar `lo <= hi` junto con límites y actualizaciones de `[lo, hi)` sin adaptación | Mezcla dos convenciones; mantener condiciones y extremos coherentes |
| Escribir `hi = mid - 1` en `lowerBound` | Puede descartar la primera posición válida; usar `hi = mid` |
| Escribir `lo = mid` cuando `lo < hi` y `mid == lo` | Puede no reducir el intervalo; usar `lo = mid + 1` cuando se descarta `mid` |
| Acceder a `datos[lo]` sin comprobar `lo < n` | Riesgo de `ArrayIndexOutOfBoundsException`; validar antes |
| Tratar un resultado negativo de `Arrays.binarySearch` como `-1` universal | La API codifica el punto de inserción; usar su contrato documentado |
| Confundir `==` con igualdad por atributo en objetos | Comparar el atributo requerido o usar un `equals` apropiado |
| Validar orden en cada búsqueda y seguir afirmando `Θ(log n)` para toda la operación | La validación completa cuesta `Θ(n)` |
| Creer que el índice de un arreglo ordenado es el índice de llegada | El ordenamiento cambia posiciones; conservar índices originales si se requieren |
| Emplear resta para comparar enteros extremos | Riesgo de desbordamiento; usar `Integer.compare` o un `Comparator` correcto |

<a id="demostracion"></a>
## 2. Demostración guiada

### 2.1. Problema y contrato

Comparar una búsqueda secuencial con una binaria de primera coincidencia y calcular fronteras de duplicados. El arreglo binario se recibe ordenado ascendentemente; la secuencial acepta cualquier orden. Se admite un arreglo vacío, pero no uno nulo. Los métodos no modifican la entrada.

### 2.2. Antes de ejecutar

1. Predecir la posición de las claves `3`, `8`, `1` y `9` en `[1, 3, 3, 7, 9]`.
2. Predecir `lowerBound` y `upperBound` de `3` en `[1, 3, 3, 3, 8]`.
3. Explicar por qué `binaryAny` puede devolver un duplicado distinto del primero.
4. Identificar cuándo un método devuelve `-1` y cuándo puede devolver `n`.
5. Comprobar si algún método cambia el orden de entrada.

### 2.3. Código completo

Guardar como `ejemplos/DemoBusqueda.java`. No se requieren paquetes ni dependencias externas.

<!-- DEMO:START -->
```java
import java.util.Arrays;

public class DemoBusqueda {
    static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    static void validar(int[] datos) {
        if (datos == null) throw new IllegalArgumentException("Arreglo nulo");
    }

    static int sequentialFirst(int[] datos, int clave) {
        validar(datos);
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] == clave) return i;
        }
        return -1;
    }

    // Precondición: datos está ordenado ascendentemente.
    static int lowerBound(int[] datos, int clave) {
        validar(datos);
        int lo = 0, hi = datos.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (datos[mid] < clave) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // Precondición: datos está ordenado ascendentemente.
    static int upperBound(int[] datos, int clave) {
        validar(datos);
        int lo = 0, hi = datos.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (datos[mid] <= clave) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    static int binaryFirst(int[] datos, int clave) {
        int pos = lowerBound(datos, clave);
        return pos < datos.length && datos[pos] == clave ? pos : -1;
    }

    static int[] binaryRange(int[] datos, int clave) {
        int inicio = lowerBound(datos, clave);
        if (inicio == datos.length || datos[inicio] != clave) {
            return new int[]{-1, -1};
        }
        return new int[]{inicio, upperBound(datos, clave)};
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 3, 7, 9};
        for (int clave : new int[]{3, 8, 1, 9}) {
            int secuencial = sequentialFirst(a, clave);
            int binaria = binaryFirst(a, clave);
            comprobar(secuencial == binaria, "Diferencia para " + clave);
            System.out.println(clave + ":" + binaria);
        }

        int[] repetidos = {1, 3, 3, 3, 8};
        int[] rango = binaryRange(repetidos, 3);
        comprobar(Arrays.equals(rango, new int[]{1, 4}), "Rango incorrecto");
        System.out.println("Rango 3: " + Arrays.toString(rango));
        System.out.println("Limites 3: " + lowerBound(repetidos, 3)
            + "," + upperBound(repetidos, 3));

        comprobar(Arrays.equals(binaryRange(new int[]{1, 3}, 2),
            new int[]{-1, -1}), "Ausencia incorrecta");
        comprobar(Arrays.equals(binaryRange(new int[0], 1),
            new int[]{-1, -1}), "Vacío incorrecto");
        comprobar(Arrays.equals(a, new int[]{1, 3, 3, 7, 9}),
            "Se modificó el arreglo");
        System.out.println("Verificación: correcta.");
    }
}
```
<!-- DEMO:END -->

### 2.4. Ejecución

Desde la carpeta `2.2-search/java`:

```bash
java ejemplos/DemoBusqueda.java
```

O mediante compilación explícita desde esa misma carpeta:

```bash
javac -encoding UTF-8 ejemplos/DemoBusqueda.java
java -cp ejemplos DemoBusqueda
```

### 2.5. Salida esperada

```text
3:1
8:-1
1:0
9:4
Rango 3: [1, 4]
Limites 3: 1,4
Verificación: correcta.
```

### 2.6. Interpretación

La secuencial y `binaryFirst` cumplen el mismo contrato de primera coincidencia cuando la entrada está ordenada. `lowerBound` y `upperBound` no representan exactamente la misma operación: localizan fronteras diferentes. El arreglo `a` conserva su contenido y el caso vacío se resuelve sin accesos fuera de rango. Las comprobaciones son independientes de las opciones de ejecución de `assert`.

<a id="pc"></a>
## 3. Práctica y PC

### 3.1. Indicaciones comunes

Los ejercicios se presentan sin solución. Justificar el contrato, la precondición, la salida esperada y el costo temporal y espacial. Los ejemplos con arreglos ordenados usan orden ascendente, salvo indicación contraria.

### 3.2. Nivel 1: conceptos y recorridos

| N.º | Actividad |
|---:|---|
| 1 | Buscar la primera posición de `4` y de `7` en `{4, 2, 4, 9}` mediante recorrido secuencial. |
| 2 | Modificar una secuencial para devolver un booleano de existencia sin crear arreglos adicionales. |
| 3 | Implementar la búsqueda de todos los índices de `5` en `{5, 1, 5, 5}` y justificar memoria de salida. |
| 4 | Buscar por código en una `List<Estudiante>` sin comparar referencias con `==`. |
| 5 | Explicar por qué una búsqueda secuencial no exige ordenar previamente. |
| 6 | Construir un caso en el que `binaryAny` devuelva un duplicado distinto del primero. |

### 3.3. Nivel 2: binaria, límites y trazas

| N.º | Actividad |
|---:|---|
| 7 | Trazar `lowerBound` en `[2, 2, 4, 6, 6]` para claves `2`, `5` y `8`. |
| 8 | Trazar `upperBound` en `[1, 1, 1, 4, 7]` para clave `1`. |
| 9 | Identificar el error de un `lowerBound` que utiliza `hi = mid - 1`. |
| 10 | Explicar por qué `lo = mid` puede impedir terminar en un intervalo de dos elementos. |
| 11 | Obtener rango y cantidad de coincidencias de `6` en `[1, 6, 6, 6, 9]`. |
| 12 | Adaptar los límites para una secuencia descendente y documentar las desigualdades modificadas. |

### 3.4. Nivel 3: API, objetos y análisis

| N.º | Actividad |
|---:|---|
| 13 | Interpretar un resultado de `Arrays.binarySearch` igual a `-5` como punto de inserción. |
| 14 | Ordenar productos por código con `Comparator` y buscar un producto por esa clave. |
| 15 | Explicar por qué buscar por precio sobre una lista ordenada por código incumple la precondición. |
| 16 | Comparar el costo de una consulta secuencial con la estrategia de ordenar y realizar `q` consultas binarias. |
| 17 | Diferenciar el índice en un arreglo ordenado de la posición original de un registro. |
| 18 | Analizar la complejidad de una búsqueda binaria si comparar cadenas cuesta `O(m)`. |

### 3.5. PC integradora · Intervalo de coincidencias

**Contexto:** una aplicación almacena un conjunto de números enteros ordenados y necesita conocer el bloque completo de posiciones que contienen una clave. No basta con localizar cualquier aparición.

**Contrato de entrada:** arreglo no nulo de enteros ordenados ascendentemente, posiblemente vacío, y clave `int`. Si el arreglo es `null`, rechazarlo con `IllegalArgumentException`. El algoritmo puede asumir que se cumple la precondición de orden y no necesita validarla en cada consulta.

**Trabajo solicitado:**

1. Implementar dos búsquedas binarias independientes para obtener la frontera inferior y la frontera superior exclusiva.
2. Construir un método que devuelva `[inicio, fin]` como arreglo de dos enteros; utilizar `[-1, -1]` si la clave no existe.
3. No recorrer linealmente el bloque de duplicados para descubrir su extremo final.
4. Mantener la entrada sin modificaciones y separar lógica, pruebas y presentación.
5. Incorporar comprobaciones ejecutables de valores repetidos, ausencia, arreglo vacío, extremos y valor único.
6. Explicar por qué la búsqueda del rango es `O(log n)` y ocupa `O(1)` memoria auxiliar con arreglo de salida de tamaño fijo.

**La demostración de la sección 2 funciona como referencia**, pero la entrega debe ser una implementación propia y documentada, con casos adicionales y justificación de invariantes; no copiar el código de demostración como única solución.

### 3.6. Casos de aceptación

| Entrada o escenario | Resultado exigido |
|---|---|
| `[1, 3, 3, 3, 8]`, clave `3` | `[1, 4]` |
| `[1, 3]`, clave `2` | `[-1, -1]` |
| `[]`, clave `1` | `[-1, -1]` |
| `[2, 2, 2]`, clave `2` | `[0, 3]` |
| `[1, 2, 4]`, clave `1` | `[0, 1]` |
| `[1, 2, 4]`, clave `4` | `[2, 3]` |
| `[1, 2, 4]`, clave `9` | `[-1, -1]` |
| `[5]`, clave `5` | `[0, 1]` |
| Arreglo `null` | Rechazo con causa mediante `IllegalArgumentException` |

### 3.7. Entrega y evidencia

La PC puede implementarse en **una versión** a elección entre Java, Python y JavaScript, conforme al contrato original del tema. Las PL se entregan exclusivamente en Java. Para esta guía, los ejemplos y la demostración se mantienen en Java.

Registrar entrada, resultado esperado, resultado obtenido, comprobaciones ejecutadas y justificación del invariante en `evidencias/PC2.2.md`. Si la solución es Java, documentar el comando de ejecución desde la carpeta de entrega. Crear commits relacionados con implementación, pruebas y documentación; verificar el hash con `git rev-parse HEAD`.

<a id="esencial"></a>
## Lo esencial

- La secuencial acepta datos desordenados; la binaria necesita datos ordenados por el mismo criterio.
- `binaryAny` puede retornar cualquier duplicado; `binaryFirst` utiliza la frontera inferior.
- `lowerBound` busca el primer valor mayor o igual; `upperBound`, el primer valor estrictamente mayor.
- Dos límites determinan `[inicio, fin)` sin recorrer duplicados; si no hay coincidencia se devuelve `[-1, -1]`.
- Los intervalos y sus actualizaciones deben usar una convención consistente; el invariante permite justificar corrección y terminación.
- Una búsqueda secuencial requiere `O(n)` en el peor caso; una binaria sobre arreglo ordenado, `O(log n)` con comparaciones constantes.
- Ordenar, validar y conservar índices originales son costos y responsabilidades distintas de buscar.
- `Arrays.binarySearch` no garantiza la primera coincidencia y codifica de modo especial las ausencias.

<a id="referencias"></a>
## Referencias y lectura de apoyo

1. Oracle. [Java SE 25 · Arrays](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Arrays.html). `sort` y `binarySearch`.
2. Oracle. [Java SE 25 · Collections](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collections.html). `binarySearch` con orden natural o comparador.
3. Oracle. [Java SE 25 · Comparator](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Comparator.html). Criterios externos coherentes con el ordenamiento.
4. Oracle. [Java SE 25 · Comparable](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Comparable.html). Orden natural.
5. Programa analítico del curso y material previo de `Comparator` y `Comparable`. Correspondencia con ordenamiento en la Unidad 2.

Los ejemplos, trazas y ejercicios son elaboración didáctica orientada a Java 17 o posterior. La API se documenta con referencias de Java SE 25.

[Volver al índice del curso](../../../index.html)
