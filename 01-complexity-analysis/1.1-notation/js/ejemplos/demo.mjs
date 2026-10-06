function visits(values) {
    let count = 0;
    for (const value of values) count++;
    return count;
}
for (const n of [0, 4, 8]) {
    const count = visits(Array(n).fill(0));
    if (count !== n) throw new Error("Conteo incorrecto");
    console.log(`${n}:${count}`);
}
