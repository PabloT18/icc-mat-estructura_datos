function fibonacci(n,cache) {
    if (!Number.isInteger(n) || n<0 || n>30) throw new RangeError('n fuera de rango');
    function memo(k) {
        if (cache.has(k)) return cache.get(k);
        const value=k<2?k:memo(k-1)+memo(k-2);
        cache.set(k,value); return value;
    }
    return memo(n);
}
const cache=new Map();
for (const n of [10,10,5]) {
    const value=fibonacci(n,cache);
    if (value!==(n===5?5:55)) throw new Error('Fibonacci');
    console.log(`${n}:${value}:${cache.size}`);
}
if (fibonacci(0,new Map())!==0) throw new Error('Cero válido');
let rejected=false;
try { fibonacci(-1,cache); } catch (e) { rejected=e instanceof RangeError; }
if (!rejected) throw new Error('Dominio');
