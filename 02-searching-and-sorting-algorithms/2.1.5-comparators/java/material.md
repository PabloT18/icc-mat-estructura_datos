# Estructura de Datos

![UPS · Carrera de Computación](../../../assets/logos/ups-computacion.png)

## Ordenamiento con la API de Java: `sort`, `Comparator` y `Comparable`

**Autor:** Ing. Pablo Torres, Mgtr.  
**Unidad 2:** Ordenamiento, búsqueda y recursividad  
**Lenguaje:** Java · Ejemplos compatibles con Java 17 o posterior

[Índice del curso](../../../index.html)

### Índice de contenidos

- [1. Conceptos y ejemplos](#conceptos)
  - [1.1. Ordenar mediante una biblioteca](#biblioteca)
  - [1.2. Orden ascendente de primitivos](#primitivos)
  - [1.3. Objetos con orden natural](#natural)
  - [1.4. Cómo funciona sort por detrás](#interno)
  - [1.5. Complejidad y costo de comparar](#complejidad)
  - [1.6. Clase de trabajo](#estudiante)
  - [1.7. Qué es un comparador](#comparadores)
  - [1.8. Orden simple por un atributo](#atributo)
  - [1.9. Funciones lambda y referencias a métodos](#lambda)
  - [1.10. Varios criterios y orden descendente](#criterios)
  - [1.11. Comparadores manuales](#manuales)
  - [1.12. Estabilidad y empates](#estabilidad)
  - [1.13. Comparable: el orden natural de una clase](#comparable)
  - [1.14. Comparadores definidos dentro de la clase](#comparadores-clase)
  - [1.15. Modificación, copias y restricciones](#copias)
  - [1.16. Errores frecuentes](#errores)
- [2. Demostración guiada](#demostracion)
- [3. Práctica y PC](#pc)
- [Lo esencial](#esencial)
- [Referencias](#referencias)

Este material desarrolla el uso de las herramientas de ordenamiento de Java. El recorrido comienza con arreglos de primitivos, continúa con objetos y comparadores externos, y termina con el orden natural definido por la propia clase. Se estudian el criterio de comparación, la estabilidad, los efectos sobre la entrada y el costo temporal y espacial. La explicación interna permite interpretar el comportamiento de la biblioteca sin implementar sus algoritmos.

<a id="conceptos"></a>
## 1. Conceptos y ejemplos

**Prerrequisitos:** arreglos, listas, clases, objetos, métodos, interfaces, genéricos básicos y notación asintótica. Se usan índices desde cero. Los fragmentos parciales se integran en un método `main` y utilizan las clases e importaciones que se presentan más adelante. La sección 2 contiene un programa completo independiente.

<a id="biblioteca"></a>
### 1.1. Ordenar mediante una biblioteca

Ordenar reorganiza los elementos de una secuencia de acuerdo con una relación de orden. El resultado debe cumplir dos condiciones: respetar el criterio elegido y conservar todos los elementos con sus multiplicidades. Una biblioteca proporciona el mecanismo para reorganizarlos; el programador selecciona los datos y define la regla que determina su posición.

| Herramienta | Uso |
|---|---|
| `Arrays.sort(primitivos)` | Orden ascendente de un arreglo primitivo |
| `Arrays.sort(objetos)` | Orden natural de los objetos |
| `Arrays.sort(objetos, comparador)` | Orden de un arreglo de objetos según un criterio explícito |
| `lista.sort(comparador)` | Orden de una lista según un criterio explícito |
| `lista.sort(null)` | Orden natural de los elementos de una lista |
| `Collections.sort(lista)` | Orden natural de una lista |
| `Collections.sort(lista, comparador)` | Orden de una lista con comparador |

Estas operaciones devuelven `void` y modifican el contenedor recibido. Para conservar su disposición original, se ordena una copia. El resultado no es otra lista devuelta por `sort` [1, 2, 7].

**Tres responsabilidades diferentes:**

1. El contenedor almacena los elementos.
2. El criterio determina cuándo un elemento precede a otro.
3. La implementación del ordenamiento realiza las comparaciones y movimientos necesarios.

No se debe suponer que un método ejecuta la misma secuencia de comparaciones en todos los JDK. El programa depende del contrato de la API, no de una traza interna específica.

<a id="primitivos"></a>
### 1.2. Orden ascendente de primitivos

```java
import java.util.Arrays;

int[] numeros = {8, 3, 5, 1, 9, 3};
Arrays.sort(numeros);
System.out.println(Arrays.toString(numeros));
// [1, 3, 3, 5, 8, 9]
```

Los valores repetidos permanecen. Un arreglo vacío o con un solo elemento es válido y ya está ordenado.

```java
double[] notas = {8.5, 6.2, 9.1, 7.0};
Arrays.sort(notas);
// [6.2, 7.0, 8.5, 9.1]
```

La API tiene sobrecargas para `byte`, `short`, `int`, `long`, `char`, `float` y `double`. No tiene `Arrays.sort(boolean[])`. Las versiones de primitivos no aceptan un `Comparator` [1].

Para ordenar solamente una región se indica el inicio inclusivo y el final exclusivo:

```java
int[] datos = {90, 4, 1, 3, 80};
Arrays.sort(datos, 1, 4);
// [90, 1, 3, 4, 80]
```

El tamaño del problema es `r = toIndex - fromIndex`. Los elementos externos al rango conservan sus posiciones. Los límites deben satisfacer `0 <= fromIndex <= toIndex <= datos.length`.

**Primitivos y objetos no son intercambiables.** `int[]` almacena valores primitivos; `Integer[]` almacena referencias a objetos. Para solicitar orden descendente con un comparador se puede utilizar `Integer[]`:

```java
Integer[] numeros = {8, 3, 5, 1, 9};
Arrays.sort(numeros, Comparator.reverseOrder());
// [9, 8, 5, 3, 1]
```

Para mantener `int[]`, una alternativa es ordenar ascendentemente y después invertir sus posiciones con un recorrido. Esa inversión agrega tiempo lineal. Convertir a `Integer[]` introduce objetos y referencias; conviene elegir el tipo por las necesidades del programa.

<a id="natural"></a>
### 1.3. Objetos con orden natural

El **orden natural** es la regla definida por una clase mediante `Comparable`. Clases como `Integer` y `String` ya proporcionan esa regla [4, 6].

```java
List<Integer> valores = new ArrayList<>(List.of(8, 3, 5, 1));
valores.sort(Comparator.naturalOrder());
// [1, 3, 5, 8]

List<String> nombres = new ArrayList<>(List.of("Marta", "Ana", "Luis"));
nombres.sort(null);
// [Ana, Luis, Marta]
```

En `lista.sort(null)`, `null` significa que no se aporta un comparador externo y se solicita el orden natural. No significa que la lista pueda contener elementos nulos sin una política específica.

Los genéricos utilizan tipos de referencia: se escribe `List<Integer>`, no `List<int>`.

El orden natural de `String` es lexicográfico según unidades UTF-16. No garantiza el orden lingüístico del español ni ignora automáticamente mayúsculas o tildes. Si el problema exige un orden de un idioma, debe seleccionarse una regla de colación apropiada.

<a id="interno"></a>
### 1.4. Cómo funciona `sort` por detrás

**Contrato e implementación.** El contrato establece el resultado y las garantías observables. La implementación es el código interno utilizado para cumplirlo y puede cambiar entre versiones [1]. Para los detalles siguientes se toma como referencia la documentación de Java SE 25; los ejemplos públicos de esta lectura funcionan desde Java 17.

| Caso | Comportamiento interno documentado |
|---|---|
| `Arrays.sort(int[])` | Variante optimizada de Dual-Pivot Quicksort |
| `Arrays.sort` para objetos | Ordenamiento estable y adaptativo de la familia TimSort |
| Implementación predeterminada de `List.sort` | Obtiene un arreglo, lo ordena y escribe las referencias de vuelta en la lista |
| `ArrayList.sort` en OpenJDK | Ordena el tramo ocupado de su arreglo interno; no necesita reconstruirlo como la implementación predeterminada de `List` |

TimSort reconoce segmentos ya ordenados, llamados *runs*, y aprovecha esa estructura. Los segmentos cortos se procesan con estrategias apropiadas para tamaños pequeños y los segmentos ordenados se combinan. Esto permite reducir trabajo en entradas favorables. El nombre `sort` no implica que todos los tipos, tamaños y contenedores recorran exactamente el mismo procedimiento [1, 2, 8].

No se necesita reconstruir esas estrategias para usar la API. Sí se necesita comprender que el criterio puede ser consultado muchas veces, que no existe una cantidad universal de llamadas al comparador y que el ordenamiento de objetos debe conservar estabilidad.

**Modelo conceptual para objetos:** cuando necesita comparar dos elementos, la implementación consulta `comparador.compare(a, b)`. Un valor negativo indica que `a` precede a `b`; cero indica equivalencia según el criterio; un valor positivo indica que `a` sigue a `b`. Si se solicita orden natural, la comparación utiliza `compareTo`.

La implementación decide qué parejas comparar. No se garantiza que sean siempre vecinas ni que cada objeto se compare una sola vez. Tampoco se garantiza que las claves extraídas se almacenen en caché.

**Referencias e identidad.** Al ordenar estudiantes se cambian de posición sus referencias. El nombre, código y nota de cada estudiante siguen perteneciendo al mismo objeto. No se ordenan las notas por separado para volver a asociarlas con otros nombres.

<a id="complejidad"></a>
### 1.5. Complejidad y costo de comparar

El tamaño de entrada es `n = cantidad de elementos`. La notación `O` expresa una cota superior asintótica; `Θ` expresa una cota ajustada. Ninguna de ellas significa por sí sola «peor caso» o «mejor caso».

| Operación o escenario | Tiempo | Memoria adicional |
|---|---|---|
| TimSort para objetos, entrada favorable ya ordenada | `Θ(n)` para el reconocimiento y recorrido | No requiere el máximo espacio de mezcla en todos los casos |
| TimSort para objetos, peor caso | `O(n log n)`; existen familias de costo `Θ(n log n)` | `O(n)` referencias auxiliares |
| `Arrays.sort(int[])`, según la nota de implementación de Java SE 25 | `O(n log n)` en todos los conjuntos de datos | No se atribuye aquí una cota universal a todas las sobrecargas primitivas |
| Crear una copia de un arreglo o `ArrayList` | `Θ(n)` | `Θ(n)` |
| Copiar y ordenar objetos, peor caso | `O(n log n)` | `O(n)` |

Las cotas de TimSort suponen comparaciones de costo constante. La adaptabilidad explica que no sea correcto describir todas las entradas con un tiempo ajustado `Θ(n log n)`. Las garantías internas deben asociarse a la versión y al contenedor estudiados [1, 2].

**Justificación del crecimiento.** La combinación equilibrada de segmentos ordenados permite distribuir el trabajo en una cantidad de niveles proporcional a `log n`; en cada nivel, el trabajo total puede ser proporcional a `n`. El producto explica la escala `n log n` del peor caso. En una entrada que ya constituye un segmento ordenado, reconocerla requiere recorrerla: su costo sigue siendo lineal, no constante.

Para ordenar mediante comparaciones y con claves distintas, un límite inferior clásico del peor caso se obtiene de las `n!` permutaciones posibles: distinguirlas requiere una altura de decisión de al menos `log₂(n!)`, que crece como `Θ(n log n)`. Esta es una justificación matemática del modelo de comparaciones, no una afirmación de que todas las entradas necesiten ese trabajo.

**El comparador también cuesta.** Si `q(n)` es el número de comparaciones y `C` una cota de costo por comparación, el costo de comparar se puede acotar mediante `O(q(n) · C)`. Hay que agregar además movimientos de referencias y preparación de datos.

- Comparar notas `double` de tamaño fijo cuesta `O(1)`.
- Comparar dos cadenas puede recorrer hasta `m` unidades de texto: costo `O(m)`.
- Para un ordenamiento con `O(n log n)` comparaciones de cadenas, una cota es `O(n log n · m)`.
- Con `k` criterios de costo constante, una comparación puede costar `O(k)` si se llega hasta el último desempate. Si `k` es fijo, el orden global sigue siendo `O(n log n)`.

**Ejemplo de escala, no de tiempo exacto:** duplicar `n` multiplica `n log₂ n` por `2 · (1 + 1/log₂ n)`. Para tamaños grandes el factor se aproxima a 2; no se aproxima a 4 como en una función cuadrática. No equivale a predecir exactamente milisegundos.

**Memoria.** Modificar la entrada no implica memoria auxiliar constante. TimSort puede reservar almacenamiento temporal de referencias. Una copia superficial adicional cuesta espacio lineal, aunque no duplique los objetos.

**Medición.** Si se instrumenta un comparador, se cuentan sus invocaciones reales. El conteo depende de la entrada y la implementación. No se debe afirmar que siempre se realizan exactamente `n log₂ n` comparaciones ni extrapolar complejidad desde una sola ejecución.

<a id="estudiante"></a>
### 1.6. Clase de trabajo

Los ejemplos siguientes comienzan con una clase sin orden natural. Por eso se proporciona un comparador externo. En la sección 1.13 se incorpora `Comparable`.

```java
class Estudiante {
    private final String codigo;
    private final String nombre;
    private final double nota;

    public Estudiante(String codigo, String nombre, double nota) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nota = nota;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " - " + nota;
    }
}
```

Se presupone que códigos y nombres no son nulos y que las notas son finitas. La demostración completa valida estas condiciones. Cada ejercicio de ordenamiento parte de una copia nueva de la siguiente lista:

```java
List<Estudiante> original = List.of(
    new Estudiante("E03", "Marta", 8.5),
    new Estudiante("E01", "Luis", 6.2),
    new Estudiante("E02", "Ana", 8.5),
    new Estudiante("E04", "Pedro", 9.1)
);

List<Estudiante> estudiantes = new ArrayList<>(original);
```

Importaciones utilizadas en los fragmentos:

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;
```

<a id="comparadores"></a>
### 1.7. Qué es un comparador

`Comparator<T>` representa una regla externa para comparar dos objetos de tipo `T`. Su método funcional es `int compare(T a, T b)` [3].

| Resultado | Significado dentro del orden elegido |
|---|---|
| Menor que cero | `a` debe aparecer antes que `b` |
| Igual a cero | `a` y `b` son equivalentes para ese criterio |
| Mayor que cero | `a` debe aparecer después que `b` |

No se exige devolver exactamente -1, 0 y 1. Se interpreta el signo. Un resultado cero no significa que `a == b` ni que `a.equals(b)`; puede indicar solamente que tienen la misma nota.

**Separación de responsabilidades.** El comparador responde una pregunta sobre dos elementos. `sort` usa esas respuestas para ordenar toda la secuencia. Crear el comparador no ordena los datos.

Un comparador válido debe cumplir antisimetría de signo, transitividad y consistencia de las equivalencias. Si `a` y `b` se consideran equivalentes, deben producir el mismo signo al compararse con un tercer elemento. Además, comparar un elemento consigo mismo debe dar cero. La implementación puede detectar ciertos incumplimientos y lanzar `IllegalArgumentException`; no está obligada a descubrir todos los comparadores incorrectos [3].

<a id="atributo"></a>
### 1.8. Orden simple por un atributo

**Nota ascendente:**

```java
Comparator<Estudiante> porNota =
    Comparator.comparingDouble(e -> e.getNota());

estudiantes.sort(porNota);
```

| Posición final | Código | Nombre | Nota |
|---:|---|---|---:|
| 0 | E01 | Luis | 6.2 |
| 1 | E03 | Marta | 8.5 |
| 2 | E02 | Ana | 8.5 |
| 3 | E04 | Pedro | 9.1 |

Marta permanece antes que Ana porque el comparador solo considera la nota y el ordenamiento es estable.

**Nombre ascendente:**

```java
Comparator<Estudiante> porNombre =
    Comparator.comparing(Estudiante::getNombre);

estudiantes.sort(porNombre);
// Códigos: E02, E01, E03, E04
```

Los constructores especializados evitan convertir una clave primitiva en su envolvente para compararla: `comparingInt`, `comparingLong` y `comparingDouble`. `comparing` sirve para claves de referencia que tengan un orden natural o un comparador propio [3].

<a id="lambda"></a>
### 1.9. Funciones lambda y referencias a métodos

En este ejemplo, la expresión con flecha es una **función extractora de clave**:

```java
e -> e.getNota()
```

| Parte | Función |
|---|---|
| `e` | Parámetro que representa un estudiante |
| `->` | Separa los parámetros del cuerpo de la función |
| `e.getNota()` | Expresión cuyo resultado se devuelve |

El nombre `e` puede reemplazarse por otro nombre válido:

```java
estudiante -> estudiante.getNota()
```

En ese contexto, Java deduce el tipo `Estudiante`. La función recibe **un objeto** y devuelve **un `double`**. No compara dos estudiantes. El cuerpo de una sola expresión devuelve su resultado implícitamente:

```java
e -> e.getNota()
```

Equivale a:

```java
e -> {
    return e.getNota();
}
```

Se puede almacenar y ejecutar esa función de forma independiente. `ToDoubleFunction<Estudiante>` es la interfaz funcional que representa una operación que recibe un estudiante y produce un `double` [5].

```java
ToDoubleFunction<Estudiante> extraerNota = e -> e.getNota();

double notaDeMarta = extraerNota.applyAsDouble(original.get(0));
// notaDeMarta vale 8.5

Comparator<Estudiante> porNota =
    Comparator.comparingDouble(extraerNota);

estudiantes.sort(porNota);
```

| Expresión | Qué produce |
|---|---|
| `e -> e.getNota()` | Una función extractora compatible con el tipo funcional esperado |
| `extraerNota.applyAsDouble(estudiante)` | Un valor `double` concreto |
| `Comparator.comparingDouble(extraerNota)` | Un comparador de estudiantes |
| `porNota.compare(a, b)` | Un entero que expresa el orden entre dos estudiantes |
| `estudiantes.sort(porNota)` | Reorganiza la lista; devuelve `void` |

**Modelo de comparación.** Si la biblioteca necesita comparar a Marta y Luis, el comparador obtiene 8.5 y 6.2 y los compara numéricamente. El resultado es positivo, por lo que Marta sigue a Luis en orden ascendente. Esta explicación describe una comparación posible, no una promesa sobre el orden real de llamadas.

La lambda anterior es equivalente, en este contexto, a una referencia a método:

```java
Comparator.comparingDouble(e -> e.getNota())
Comparator.comparingDouble(Estudiante::getNota)
```

También es posible escribir directamente una función de dos argumentos:

```java
Comparator<Estudiante> porNota =
    (a, b) -> Double.compare(a.getNota(), b.getNota());
```

Aquí la lambda recibe **dos estudiantes** y devuelve **un `int` de comparación**. Es distinta de la extractora, aunque el comparador resultante establezca el mismo orden.

**Nombre de la herramienta.** En estos ejemplos se usa `Comparator.comparingDouble`, del paquete `java.util`. `Collectors`, del paquete `java.util.stream`, agrupa operaciones de recolección y reducción de flujos; no es la clase que construye este comparador.

<a id="criterios"></a>
### 1.10. Varios criterios y orden descendente

**Regla del problema:** nota descendente; a igualdad de nota, nombre ascendente; a igualdad de ambos, código ascendente.

```java
Comparator<Estudiante> porNotaNombreCodigo =
    Comparator.comparingDouble(Estudiante::getNota)
              .reversed()
              .thenComparing(Estudiante::getNombre)
              .thenComparing(Estudiante::getCodigo);

estudiantes.sort(porNotaNombreCodigo);
// Códigos: E04, E02, E03, E01
```

Los criterios se consultan en prioridad. `thenComparing` aplica el siguiente cuando el comparador anterior devuelve cero [3]. No ordena cada atributo de forma independiente.

| Comparación | Decisión |
|---|---|
| Pedro, 9.1 frente a Ana, 8.5 | Pedro precede por nota descendente |
| Marta, 8.5 frente a Ana, 8.5 | Ana precede por nombre ascendente |
| Dos estudiantes con la misma nota y nombre | Se comparan sus códigos |

**La posición de `reversed()` cambia la regla:**

```java
// Nota descendente y nombre ascendente.
Comparator<Estudiante> primero =
    Comparator.comparingDouble(Estudiante::getNota)
              .reversed()
              .thenComparing(Estudiante::getNombre);

// Nota descendente y nombre descendente.
Comparator<Estudiante> segundo =
    Comparator.comparingDouble(Estudiante::getNota)
              .thenComparing(Estudiante::getNombre)
              .reversed();
```

En el segundo caso se invierte el comparador compuesto completo. Si los criterios contienen reglas para nulos, invertir todo también invierte esa ubicación.

<a id="manuales"></a>
### 1.11. Comparadores manuales

Un comparador manual resulta útil cuando la regla combina decisiones que no se expresan cómodamente mediante una cadena de constructores. Puede declararse como variable, lambda o clase.

**Como variable con clase anónima:**

```java
Comparator<Estudiante> porNotaNombre = new Comparator<Estudiante>() {
    @Override
    public int compare(Estudiante a, Estudiante b) {
        int resultado = Double.compare(b.getNota(), a.getNota());
        if (resultado != 0) {
            return resultado;
        }
        return a.getNombre().compareTo(b.getNombre());
    }
};

estudiantes.sort(porNotaNombre);
```

`Double.compare(b.getNota(), a.getNota())` invierte los argumentos para obtener nota descendente. El nombre conserva el sentido ascendente. Un empate en nota permite continuar hacia el segundo criterio.

**Con lambda:**

```java
Comparator<Estudiante> porNotaNombre = (a, b) -> {
    int resultado = Double.compare(b.getNota(), a.getNota());
    if (resultado != 0) {
        return resultado;
    }
    return a.getNombre().compareTo(b.getNombre());
};
```

Estas declaraciones son alternativas; no se colocan con el mismo nombre en el mismo ámbito.

**Como clase reutilizable:**

```java
class ComparadorPorNota implements Comparator<Estudiante> {
    @Override
    public int compare(Estudiante a, Estudiante b) {
        return Double.compare(a.getNota(), b.getNota());
    }
}

// Dentro de main:
estudiantes.sort(new ComparadorPorNota());
```

**Con condiciones explícitas, bajo la precondición de notas finitas:**

```java
Comparator<Estudiante> porNota = (a, b) -> {
    if (a.getNota() < b.getNota()) {
        return -1;
    }
    if (a.getNota() > b.getNota()) {
        return 1;
    }
    return 0;
};
```

Esta última versión permite explicar el signo. Para una comparación general de `double`, se prefiere `Double.compare`, que define el tratamiento de `NaN` y de los ceros con signo. Las comparaciones `<` y `>` por sí solas no establecen ese mismo orden total [1].

**No comparar mediante una resta convertida a entero:**

```java
// Incorrecto para notas decimales:
return (int) (a.getNota() - b.getNota());
```

Para 8.5 y 8.2, la conversión produce cero y declara un empate falso. Con enteros, una resta también puede desbordarse. Se emplean `Integer.compare`, `Long.compare` o `Double.compare` según el tipo.

<a id="estabilidad"></a>
### 1.12. Estabilidad y empates

Un ordenamiento estable conserva el orden relativo **previo a esa llamada** de los elementos que el criterio considera equivalentes. `List.sort` y `Arrays.sort` para objetos garantizan estabilidad [1, 2].

| Entrada | Criterio | Salida |
|---|---|---|
| Marta 8.5, Luis 6.2, Ana 8.5 | Solo nota ascendente | Luis, Marta, Ana |
| Marta 8.5, Luis 6.2, Ana 8.5 | Nota ascendente y nombre ascendente | Luis, Ana, Marta |

En la segunda fila, Marta y Ana no empatan en el comparador completo. El cambio no viola estabilidad.

Si primero se ordena por nombre y después solo por nota, los empates de nota conservan el orden que dejó la primera llamada. La estabilidad no reconstruye automáticamente un orden de llegada perdido. Para una prioridad compuesta, una sola cadena de comparadores suele comunicar mejor la regla.

Un resultado cero no elimina registros de una lista. En estructuras como `TreeSet` y en las claves de `TreeMap`, la equivalencia según el orden sí influye en la identificación de elementos. Un comparador solo por nota puede considerar equivalentes a dos estudiantes diferentes. Esa consecuencia debe evaluarse antes de reutilizarlo en esas estructuras [3, 4].

<a id="comparable"></a>
### 1.13. `Comparable`: el orden natural de una clase

`Comparable<T>` permite que la clase establezca su propio orden natural mediante `int compareTo(T otro)`. A diferencia de un comparador externo, cada objeto se compara con otro del mismo dominio [4].

Para que el orden natural de `Estudiante` sea por código ascendente, se cambia la declaración:

```java
class Estudiante implements Comparable<Estudiante> {
    // Campos, constructor, getters y toString de la sección 1.6.

    @Override
    public int compareTo(Estudiante otro) {
        return this.codigo.compareTo(otro.codigo);
    }
}
```

El comentario indica miembros que deben conservarse; este fragmento no es una segunda clase completa para copiar junto a la anterior.

Uso:

```java
List<Estudiante> porCodigo = new ArrayList<>(original);
porCodigo.sort(null);
// E01, E02, E03, E04

// Forma explícita equivalente:
porCodigo.sort(Comparator.naturalOrder());

// Orden natural inverso:
porCodigo.sort(Comparator.reverseOrder());
```

Aunque exista un orden natural por código, se puede proporcionar un comparador por nota cuando el caso de uso lo requiera. La clase no necesita cambiar cada vez que una pantalla solicita una prioridad diferente.

**Consistencia con `equals`.** Se recomienda que `a.compareTo(b) == 0` coincida con `a.equals(b)`. En este modelo se considera que el código identifica al estudiante; por eso la demostración define también `equals` y `hashCode` por código. La clase mínima de la sección 1.6 heredaba igualdad por identidad y debe completarse si se desea esa consistencia. La recomendación no obliga a convertir todos los comparadores alternativos en criterios de identidad [4].

| Aspecto | `Comparable<Estudiante>` | `Comparator<Estudiante>` |
|---|---|---|
| Método | `compareTo(otro)` | `compare(a, b)` |
| Lugar habitual de la regla | Clase `Estudiante` | Objeto comparador externo o miembro estático |
| Finalidad | Un orden natural de la clase | Órdenes alternativos según el contexto |
| ¿Requiere modificar la clase del elemento? | Sí | No |
| Ejemplo | Código ascendente | Nota descendente y nombre ascendente |

<a id="comparadores-clase"></a>
### 1.14. Comparadores definidos dentro de la clase

Una clase también puede publicar criterios reutilizables sin convertirlos en su orden natural:

```java
// Miembros dentro de Estudiante:
public static final Comparator<Estudiante> POR_NOTA =
    Comparator.comparingDouble(Estudiante::getNota);

public static final Comparator<Estudiante> POR_NOMBRE =
    Comparator.comparing(Estudiante::getNombre);
```

Uso:

```java
estudiantes.sort(Estudiante.POR_NOTA);
estudiantes.sort(Estudiante.POR_NOMBRE);
```

`Comparable` define cómo se compara una instancia con otra. Estas constantes almacenan objetos `Comparator` accesibles desde la clase. Pueden coexistir: orden natural por código y criterios publicados por nota y nombre. Una variable local es suficiente cuando la regla solo se usa en un método; una constante o clase independiente resulta útil cuando tiene varios consumidores.

<a id="copias"></a>
### 1.15. Modificación, copias y restricciones

**Conservar el original:**

```java
List<Estudiante> ordenados = new ArrayList<>(original);
ordenados.sort(Estudiante.POR_NOTA); // Tras incorporar la sección 1.14.
```

La copia es superficial: ambas listas apuntan a los mismos estudiantes. Reordenar la copia no altera las posiciones del original. Si los objetos fueran mutables, cambiar un campo compartido sí sería observable desde ambos contenedores. Los campos finales del ejemplo evitan reasignarlos después de construir el objeto.

**Lista modificable:** `List.of` crea una lista no modificable. Llamar a su `sort` lanza `UnsupportedOperationException`. Un `ArrayList` construido con sus elementos permite ordenar. Una lista de tamaño fijo como `Arrays.asList` puede permitir reemplazar posiciones y ordenar aunque no permita agregar o quitar elementos [2].

**Nulos:** si el dominio admite referencias nulas, debe definirse su ubicación:

```java
Comparator<Estudiante> porNotaConNulosAlFinal =
    Comparator.nullsLast(
        Comparator.comparingDouble(Estudiante::getNota)
    );
```

Este criterio tolera estudiantes nulos, pero no soluciona atributos nulos dentro de un estudiante. Para un nombre nulo se configura el comparador de esa clave:

```java
Comparator<Estudiante> porNombreConNulos =
    Comparator.comparing(
        Estudiante::getNombre,
        Comparator.nullsLast(Comparator.naturalOrder())
    );
```

En los ejercicios principales se rechazan nombres nulos para mantener un contrato único.

**Orden posterior:** una lista no se mantiene ordenada automáticamente por haber ejecutado `sort`. Agregar elementos o modificar claves puede invalidar el orden. Un comparador debe observar valores coherentes durante la operación y no modificar los objetos que compara.

<a id="errores"></a>
### 1.16. Errores frecuentes

| Error | Causa y corrección |
|---|---|
| `List<int>` | Los genéricos requieren tipos de referencia; usar `List<Integer>` |
| `Arrays.sort(intArray, comparador)` | No existe esa sobrecarga; elegir objetos o procesar el arreglo primitivo apropiadamente |
| `List<Estudiante> ordenados = estudiantes.sort(c)` | `sort` devuelve `void`; crear la copia antes de ordenar |
| Orden natural sin `Comparable` | Falta una regla natural; implementar la interfaz o pasar un comparador |
| Restar notas y convertir a `int` | Puede declarar empates falsos; usar `Double.compare` |
| `compare(a, a)` devuelve 1 | Incumple el contrato; devolver cero cuando corresponda |
| Usar datos aleatorios o modificar notas dentro de `compare` | La regla cambia durante el ordenamiento; comparar claves estables |
| Poner `reversed()` al final sin revisar | Se invierte toda la cadena; ubicarlo según la prioridad requerida |
| Agregar código como desempate y afirmar que se conservó llegada | El desempate impone otra prioridad; comparar solo la clave si se quiere preservar el orden previo |
| Suponer memoria `O(1)` porque se modifica la entrada | La implementación puede reservar almacenamiento auxiliar |
| Ordenar directamente una lista de `List.of` | Es no modificable; construir un `ArrayList` |
| Llamar `Collectors` al constructor del comparador | La clase usada es `Comparator`; revisar paquete y método |

<a id="demostracion"></a>
## 2. Demostración guiada

### 2.1. Problema y contrato

Ordenar valores primitivos y estudiantes mediante la API de Java. Cada vista recibe una copia independiente. Mostrar orden por nota, por varios criterios, con comparador manual y mediante el orden natural de `Estudiante`.

**Entrada válida:** códigos y nombres no vacíos, notas finitas entre 0 y 10. Los objetos de la demostración son inmutables y sus códigos son distintos. La clase considera identidad por código y orden natural por ese mismo código. El orden natural de códigos es lexicográfico.

**Salida:** el arreglo primitivo se ordena sobre sí mismo. La lista original conserva su disposición; las copias contienen las mismas referencias. La demostración no presenta una solución de la PC integradora.

### 2.2. Antes de ejecutar

1. Predecir los códigos que aparecerán en cada vista.
2. Explicar por qué Marta precede a Ana si solo se compara la nota ascendente.
3. Distinguir la extractora `e -> e.getNota()` del comparador `(a, b) -> ...`.
4. Identificar qué regla aplica `sort(null)`.
5. Explicar por qué la lista original puede ser no modificable y las copias sí se pueden ordenar.

### 2.3. Código completo

Guardar exclusivamente el siguiente bloque como `DemoSortApi.java`. La clase `Estudiante` está anidada para mantener todo el ejemplo en un archivo. No se necesitan dependencias externas ni declaración de paquete.

<!-- DEMO:START -->
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

public class DemoSortApi {
    static final class Estudiante implements Comparable<Estudiante> {
        private final String codigo;
        private final String nombre;
        private final double nota;

        static final Comparator<Estudiante> POR_NOTA =
            Comparator.comparingDouble(Estudiante::getNota);

        Estudiante(String codigo, String nombre, double nota) {
            if (codigo == null || codigo.isBlank()
                    || nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("Código y nombre obligatorios");
            }
            if (!Double.isFinite(nota) || nota < 0 || nota > 10) {
                throw new IllegalArgumentException("Nota inválida");
            }
            this.codigo = codigo;
            this.nombre = nombre;
            this.nota = nota;
        }

        String getCodigo() { return codigo; }
        String getNombre() { return nombre; }
        double getNota() { return nota; }

        @Override
        public int compareTo(Estudiante otro) {
            return codigo.compareTo(otro.codigo);
        }

        @Override
        public boolean equals(Object otro) {
            return otro instanceof Estudiante
                && codigo.equals(((Estudiante) otro).codigo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigo);
        }

        @Override
        public String toString() {
            return codigo + " - " + nombre + " - " + nota;
        }
    }

    static List<String> codigos(List<Estudiante> datos) {
        List<String> resultado = new ArrayList<>();
        for (Estudiante e : datos) {
            resultado.add(e.getCodigo());
        }
        return resultado;
    }

    static void mostrar(String titulo, List<Estudiante> datos) {
        System.out.println(titulo + ": " + codigos(datos));
    }

    static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    public static void main(String[] args) {
        int[] numeros = {8, 3, 5, 1, 9, 3};
        Arrays.sort(numeros);
        comprobar(Arrays.equals(numeros, new int[]{1, 3, 3, 5, 8, 9}),
            "Orden primitivo incorrecto");
        System.out.println("Primitivos: " + Arrays.toString(numeros));

        List<Estudiante> original = List.of(
            new Estudiante("E03", "Marta", 8.5),
            new Estudiante("E01", "Luis", 6.2),
            new Estudiante("E02", "Ana", 8.5),
            new Estudiante("E04", "Pedro", 9.1)
        );

        ToDoubleFunction<Estudiante> extraerNota = e -> e.getNota();
        System.out.println("Nota extraída: "
            + extraerNota.applyAsDouble(original.get(0)));

        List<Estudiante> porNota = new ArrayList<>(original);
        porNota.sort(Comparator.comparingDouble(extraerNota));
        comprobar(codigos(porNota).equals(List.of("E01", "E03", "E02", "E04")),
            "Orden o estabilidad incorrectos");
        comprobar(porNota.get(1) == original.get(0)
                && porNota.get(2) == original.get(2),
            "No se conservaron identidades en el empate");
        mostrar("Nota ascendente estable", porNota);

        Comparator<Estudiante> variosCriterios =
            Comparator.comparingDouble(Estudiante::getNota)
                      .reversed()
                      .thenComparing(Estudiante::getNombre)
                      .thenComparing(Estudiante::getCodigo);

        List<Estudiante> compuesta = new ArrayList<>(original);
        compuesta.sort(variosCriterios);
        comprobar(codigos(compuesta).equals(List.of("E04", "E02", "E03", "E01")),
            "Criterios compuestos incorrectos");
        mostrar("Nota descendente y nombre ascendente", compuesta);

        Comparator<Estudiante> manual = new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante a, Estudiante b) {
                int resultado = Double.compare(b.getNota(), a.getNota());
                if (resultado != 0) {
                    return resultado;
                }
                resultado = a.getNombre().compareTo(b.getNombre());
                if (resultado != 0) {
                    return resultado;
                }
                return a.getCodigo().compareTo(b.getCodigo());
            }
        };

        List<Estudiante> conManual = new ArrayList<>(original);
        conManual.sort(manual);
        comprobar(codigos(conManual).equals(codigos(compuesta)),
            "El comparador manual no coincide");
        mostrar("Comparador manual", conManual);

        List<Estudiante> natural = new ArrayList<>(original);
        natural.sort(null);
        comprobar(codigos(natural).equals(List.of("E01", "E02", "E03", "E04")),
            "Orden natural incorrecto");
        mostrar("Comparable por código", natural);

        Estudiante[] arreglo = original.toArray(new Estudiante[0]);
        Arrays.sort(arreglo, Estudiante.POR_NOTA);
        comprobar(Arrays.asList(arreglo).equals(porNota),
            "Orden del arreglo incorrecto");
        mostrar("Arrays.sort por nota", Arrays.asList(arreglo));

        comprobar(codigos(original).equals(List.of("E03", "E01", "E02", "E04")),
            "Se modificó el original");
        mostrar("Original conservado", original);
        System.out.println("Verificación: correcta.");
    }
}
```
<!-- DEMO:END -->

### 2.4. Ejecución

Desde la carpeta donde se guardó el archivo:

```bash
java DemoSortApi.java
```

O mediante compilación explícita:

```bash
javac -encoding UTF-8 DemoSortApi.java
java DemoSortApi
```

La demostración utiliza comprobaciones explícitas y no requiere `-ea`. Si falla una condición, se lanza `AssertionError` y no debe interpretarse la ejecución como correcta.

### 2.5. Salida esperada

```text
Primitivos: [1, 3, 3, 5, 8, 9]
Nota extraída: 8.5
Nota ascendente estable: [E01, E03, E02, E04]
Nota descendente y nombre ascendente: [E04, E02, E03, E01]
Comparador manual: [E04, E02, E03, E01]
Comparable por código: [E01, E02, E03, E04]
Arrays.sort por nota: [E01, E03, E02, E04]
Original conservado: [E03, E01, E02, E04]
Verificación: correcta.
```

### 2.6. Interpretación de la demostración

Cada vista se construye desde `original`, por lo que un ordenamiento no recibe accidentalmente el resultado del anterior. El criterio manual y la cadena de comparadores expresan las mismas tres prioridades. `compareTo` define otra regla: código ascendente.

Las comprobaciones de identidad con `==` muestran que los dos objetos de nota 8.5 siguen siendo los mismos y mantienen su precedencia al ordenar solo por nota. Esto complementa la comprobación por códigos, que por sí sola no demostraría identidad si se permitieran códigos repetidos.

Crear un número fijo de copias de tamaño `n` ocupa memoria total `O(n)`. Ejecutar un número fijo de ordenamientos de peor caso `O(n log n)` suma costos de esa misma clase asintótica; no los multiplica. El programa también valida constructores, construye listas para mostrar códigos e imprime resultados: esas tareas deben distinguirse del núcleo de ordenamiento.

<a id="pc"></a>
## 3. Práctica y PC

### 3.1. Indicaciones comunes

Los ejercicios se presentan sin solución. Para cada criterio indicar atributo, sentido y desempates. Para cada análisis indicar tamaño de entrada, costo de comparar, modificación del original y memoria adicional. No se exige reproducir las parejas internas que consulta TimSort.

### 3.2. Nivel 1: uso y conceptos

| N.º | Actividad | Evidencia solicitada |
|---:|---|---|
| 1 | Ordenar `int[] {4, -2, 4, 0}` y conservar una copia de la entrada. | Original, salida y explicación de multiplicidades. |
| 2 | Ordenar solo los índices 1 a 3 de `{99, 7, 2, 5, 88}`. | Argumentos de `Arrays.sort` y salida completa. |
| 3 | Ordenar un `Integer[]` de mayor a menor. | Explicar por qué el mismo comparador no puede pasarse a `int[]`. |
| 4 | Ordenar estudiantes solo por nombre. | Regla explícita y códigos de salida. |
| 5 | Declarar `ToDoubleFunction<Estudiante>` para extraer notas y ejecutarla con un estudiante. | Separar el valor extraído del comparador construido. |
| 6 | Escribir un comparador por nota como clase anónima y como lambda de dos parámetros. | Justificar la equivalencia del signo devuelto. |

### 3.3. Nivel 2: prioridades y estabilidad

| N.º | Problema |
|---:|---|
| 7 | Ordenar por nota descendente y código ascendente. Agregar dos estudiantes con la misma nota. |
| 8 | Ordenar por longitud de nombre ascendente, nombre ascendente y código ascendente. |
| 9 | Construir un ejemplo donde poner `reversed()` al final produzca un resultado distinto de invertir solo el primer criterio. |
| 10 | Usar tres estudiantes con nota igual. Ordenar solo por nota y comprobar la estabilidad mediante referencias. |
| 11 | Ordenar primero por nombre y después solo por nota. Explicar qué orden conserva el segundo ordenamiento entre empates. |
| 12 | Declarar un comparador estático dentro de `Estudiante` y utilizarlo desde otra clase. |

### 3.4. Nivel 3: contratos y complejidad

| N.º | Problema |
|---:|---|
| 13 | Incorporar `Comparable` por código y explicar qué igualdad necesita el modelo si el código es la identidad. |
| 14 | Demostrar con notas concretas por qué `(int)(a.nota - b.nota)` no es un comparador válido. |
| 15 | Instrumentar un comparador con un contador de invocaciones. Comparar entradas ordenadas, inversas y desordenadas de tamaño igual. Identificar JDK y contenedor; no generalizar desde una sola medición. |
| 16 | Si una comparación consulta una lista de hasta `m` elementos con un recorrido lineal, expresar una cota temporal del ordenamiento. Proponer cómo precalcular la clave. |
| 17 | Distinguir la memoria de `new ArrayList<>(original)` de la memoria auxiliar utilizada por el ordenamiento. |
| 18 | Explicar el efecto de usar un comparador solo por nota en un `TreeSet`. Compararlo con el resultado en un `ArrayList`. |

### 3.5. PC integradora · Vistas ordenadas de un registro académico

**Contexto.** Una aplicación recibe estudiantes en orden de registro y necesita ofrecer varias vistas sin modificar la lista original. Cada estudiante tiene código, nombre y nota. Se requiere utilizar la API de ordenamiento de Java.

**Contrato de entrada.** Lista no nula, que puede estar vacía. Cada elemento debe ser no nulo, contener código y nombre no vacíos y una nota finita entre 0 y 10. Los códigos deben ser únicos y se comparan exactamente, distinguiendo mayúsculas de minúsculas. Rechazar toda la operación con una causa si hay un dato inválido. No recortar ni transformar silenciosamente los códigos.

**Trabajo solicitado:**

1. Crear la clase `Estudiante` con campos inmutables y orden natural por código. Definir `equals` y `hashCode` de forma consistente con esa identidad.
2. Validar toda la lista antes de generar las vistas.
3. Construir una copia superficial para cada vista: nota ascendente estable; nota descendente, nombre y código ascendentes; código ascendente mediante `Comparable`.
4. Implementar la regla compuesta de dos formas: cadena de `Comparator` y comparador manual. Ambas deben producir la misma secuencia de referencias.
5. Publicar al menos un comparador como constante de clase.
6. Mostrar la lista original y los códigos de cada salida. Comprobar que el original conserva sus posiciones y que ninguna vista pierde elementos.
7. Instrumentar el comparador de la vista por nota para contar invocaciones. Reportar el JDK utilizado. No exigir un conteo universal de la biblioteca.
8. Analizar por separado validación, copia, comparación, ordenamiento y presentación. Si se usa un `HashSet` para códigos, declarar las suposiciones de costo esperado; si se buscan duplicados con dos bucles, incorporar su costo cuadrático.

**Regla de estabilidad:** la vista por nota ascendente compara únicamente la nota. No agregar código ni posición de llegada como desempates para aparentar que se conserva la estabilidad.

### 3.6. Casos de aceptación

Se abrevia cada estudiante como `(código, nombre, nota)`. La columna compuesta usa nota descendente, nombre ascendente y código ascendente.

| Entrada o escenario | Solo nota ascendente | Orden compuesto | Orden natural |
|---|---|---|---|
| E03/Marta/8.5; E01/Luis/6.2; E02/Ana/8.5; E04/Pedro/9.1 | E01, E03, E02, E04 | E04, E02, E03, E01 | E01, E02, E03, E04 |
| Lista vacía | Vacía | Vacía | Vacía |
| E02/Ana/8.5 | E02 | E02 | E02 |
| E02/Zoe/8; E01/Ana/8 | E02, E01 | E01, E02 | E01, E02 |
| E02/Ana/8; E01/Ana/8 | E02, E01 | E01, E02 | E01, E02 |
| E02/Zoe/0; E01/Ana/10 | E02, E01 | E01, E02 | E01, E02 |
| Código duplicado, nulo o vacío | Rechazo con causa; no generar vistas | Igual | Igual |
| Nombre nulo o vacío, elemento nulo o lista nula | Rechazo con causa; no generar vistas | Igual | Igual |
| Nota negativa, mayor a 10, NaN o infinita | Rechazo con causa; no generar vistas | Igual | Igual |

En todas las entradas válidas, la lista original mantiene su disposición. Agregar un caso propio con al menos seis estudiantes y dos grupos de empate. No entregar solamente capturas: incluir comprobaciones ejecutables y una explicación del criterio.

### 3.7. Entrega y evidencia

Entregar código Java y un archivo `evidencias/ordenamiento-api-java.md` con contrato, reglas, comandos, entradas, resultados esperados y reales, comprobación de estabilidad e identidad, conteos observados y análisis de complejidad. La PC debe poder ejecutarse desde su carpeta con el comando documentado por el estudiante. No se proporciona su solución completa.

<a id="esencial"></a>
## Lo esencial

- `sort` reorganiza el contenedor y devuelve `void`; una copia permite conservar el orden original.
- Los primitivos se ordenan mediante sobrecargas específicas de `Arrays.sort`.
- `Comparator` expresa una regla externa y `Comparable` define el orden natural de una clase.
- `e -> e.getNota()` recibe un estudiante y devuelve una clave; no compara dos estudiantes.
- `thenComparing` incorpora desempates y `reversed` invierte el comparador al que se aplica.
- La estabilidad preserva la precedencia previa entre elementos equivalentes según el criterio completo.
- Para objetos, el costo de peor caso del ordenamiento estudiado es `O(n log n)` si comparar cuesta `O(1)`; la memoria auxiliar puede ser `O(n)`.
- El comportamiento público y la implementación interna deben distinguirse. Los conteos observados no son garantías universales de la API.

<a id="referencias"></a>
## Referencias y lectura de apoyo

1. Oracle. [Java SE 25 · Arrays](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Arrays.html). Sobrecargas, estabilidad e implementaciones documentadas.
2. Oracle. [Java SE 25 · List.sort](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/List.html#sort(java.util.Comparator)). Contrato e implementación predeterminada.
3. Oracle. [Java SE 25 · Comparator](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Comparator.html). Contrato, composición, inversión y políticas para nulos.
4. Oracle. [Java SE 25 · Comparable](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Comparable.html). Orden natural y consistencia con igualdad.
5. Oracle. [Java SE 25 · ToDoubleFunction](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/function/ToDoubleFunction.html). Interfaz funcional para extraer un `double`.
6. Oracle. [The Java Tutorials · Object Ordering](https://docs.oracle.com/javase/tutorial/collections/interfaces/order.html). Distinción conceptual entre orden natural y comparadores. Tutorial de JDK 8; se usa para conceptos estables.
7. Oracle. [Java SE 25 · Collections.sort](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Collections.html#sort(java.util.List)). Ordenamiento de listas mediante los auxiliares estáticos.
8. OpenJDK. [JDK 25 · ArrayList.java](https://github.com/openjdk/jdk/blob/jdk-25-ga/src/java.base/share/classes/java/util/ArrayList.java). Implementación de la sobreescritura de `sort`.
9. OpenJDK. [JDK 25 · TimSort.java](https://github.com/openjdk/jdk/blob/jdk-25-ga/src/java.base/share/classes/java/util/TimSort.java). Comentarios de implementación sobre adaptación, estabilidad y recursos.

Los ejemplos, salidas y ejercicios son elaboración didáctica propia. Los análisis de copias, costo de claves, composición de costos y límite inferior por permutaciones son derivaciones explicadas en el texto. Las notas sobre algoritmos internos se refieren a las fuentes y versiones indicadas.

[Volver al índice del curso](../../../index.html)
