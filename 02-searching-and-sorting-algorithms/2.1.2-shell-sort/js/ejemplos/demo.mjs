function sort(a) {
    for (let h = Math.floor(a.length/2); h > 0; h = Math.floor(h/2)) {
        for (let i = h; i < a.length; i++) {
            const key = a[i]; let j = i;
            while (j >= h && a[j-h] > key) { a[j] = a[j-h]; j -= h; }
            a[j] = key;
        }
    }
}

for (const data of [[5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]]) {
    const a = [...data]; sort(a);
    const expected = [...data].sort((x,y) => x-y);
    if (JSON.stringify(a) !== JSON.stringify(expected)) throw new Error("Orden incorrecto");
}
const a = [5,3,4,1,2]; sort(a);
console.log(`[${a.join(", ")}]`);
