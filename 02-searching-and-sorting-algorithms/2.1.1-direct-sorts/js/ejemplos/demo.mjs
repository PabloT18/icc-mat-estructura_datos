function bubble(a) {
    for (let end = a.length - 1; end > 0; end--) {
        let changed = false;
        for (let j = 0; j < end; j++) {
            if (a[j] > a[j+1]) {
                [a[j], a[j+1]] = [a[j+1], a[j]]; changed = true;
            }
        }
        if (!changed) break;
    }
}
function selection(a) {
    for (let i = 0; i < a.length - 1; i++) {
        let min = i;
        for (let j = i+1; j < a.length; j++) if (a[j] < a[min]) min = j;
        [a[i],a[min]] = [a[min],a[i]];
    }
}
function insertion(a) {
    for (let i = 1; i < a.length; i++) {
        const key = a[i]; let j = i-1;
        while (j >= 0 && a[j] > key) { a[j+1] = a[j]; j--; }
        a[j+1] = key;
    }
}
function sort(a) {
    const b = [...a], c = [...a]; bubble(b); selection(c); insertion(a);
    if (JSON.stringify(a) !== JSON.stringify(b) || JSON.stringify(a) !== JSON.stringify(c))
        throw new Error("Métodos diferentes");
}

for (const data of [[5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]]) {
    const a = [...data]; sort(a);
    const expected = [...data].sort((x,y) => x-y);
    if (JSON.stringify(a) !== JSON.stringify(expected)) throw new Error("Orden incorrecto");
}
const a = [5,3,4,1,2]; sort(a);
console.log(`[${a.join(", ")}]`);
