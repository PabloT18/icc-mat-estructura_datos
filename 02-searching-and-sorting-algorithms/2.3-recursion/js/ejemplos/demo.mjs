function validate(n) {
    if (!Number.isInteger(n) || n<0 || n>20) throw new RangeError("n fuera de rango");
}
function factorial(n) {
    validate(n);
    function recursive(k) { return k===0 ? 1n : BigInt(k)*recursive(k-1); }
    return recursive(n);
}
function iterative(n) {
    validate(n); let value=1n;
    for (let i=1;i<=n;i++) value*=BigInt(i);
    return value;
}
for (const n of [0,3,5,20]) {
    if (factorial(n)!==iterative(n)) throw new Error("Factorial");
    console.log(`${n}:${factorial(n)}`);
}
for (const n of [-1,21]) {
    let rejected=false;
    try { factorial(n); } catch (e) { rejected=e instanceof RangeError; }
    if (!rejected) throw new Error("Dominio");
}
