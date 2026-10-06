function validate(n) {
    if (!Number.isSafeInteger(n) || n < 0 || n > 1000000)
        throw new RangeError("fuera de rango");
}
function sumLoop(n) {
    validate(n);
    let total = 0;
    for (let i = 1; i <= n; i++) total += i;
    return total;
}
function sumFormula(n) { validate(n); return n * (n + 1) / 2; }
for (const n of [0, 5, 10, 1000]) {
    if (sumLoop(n) !== sumFormula(n)) throw new Error("Resultado");
    console.log(`${n}:${sumLoop(n)}`);
}
let rejected = false;
try { sumLoop(-1); } catch (e) { rejected = e instanceof RangeError; }
if (!rejected) throw new Error("Dominio");
