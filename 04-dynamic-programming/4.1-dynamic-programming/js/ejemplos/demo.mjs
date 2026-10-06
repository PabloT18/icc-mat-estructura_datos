function recursive(n,counter) {
    counter.calls++;
    return n<2?n:recursive(n-1,counter)+recursive(n-2,counter);
}
function tabulated(n) {
    const dp=Array(n+2).fill(0); dp[1]=1;
    for (let i=2;i<=n;i++) dp[i]=dp[i-1]+dp[i-2];
    return dp[n];
}
function validate(n) {
    if (!Number.isInteger(n) || n<0 || n>30) throw new RangeError('n fuera de rango');
}
for (const n of [0,1,5,10]) {
    validate(n); const counter={calls:0}; const value=recursive(n,counter);
    if (value!==tabulated(n)) throw new Error('Fibonacci');
    console.log(`${n}:${value}:${counter.calls}`);
}
let rejected=false;
try { validate(-1); } catch (e) { rejected=e instanceof RangeError; }
if (!rejected) throw new Error('Dominio');
