function pairs(n) {
    if (!Number.isSafeInteger(n) || n < 0) throw new RangeError("n inválido");
    let count = 0;
    for (let i = 0; i < n; i++)
        for (let j = i + 1; j < n; j++) count++;
    return count;
}
if (pairs(0) !== 0) throw new Error("Vacío");
for (const n of [4, 8, 16]) {
    if (pairs(n) !== n * (n - 1) / 2) throw new Error("Conteo");
    console.log(`${n}:${pairs(n)}`);
}
let rejected = false;
try { pairs(-1); } catch (e) { rejected = e instanceof RangeError; }
if (!rejected) throw new Error("Debió rechazar n");
