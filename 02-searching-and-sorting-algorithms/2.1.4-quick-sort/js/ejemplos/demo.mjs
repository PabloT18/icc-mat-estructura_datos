function sort(a) {
    function partition(lo,hi) {
        const pivot = a[hi]; let i = lo;
        for (let j = lo; j < hi; j++) if (a[j] <= pivot) {
            [a[i],a[j]] = [a[j],a[i]]; i++;
        }
        [a[i],a[hi]] = [a[hi],a[i]]; return i;
    }
    function quick(lo,hi) {
        if (lo >= hi) return;
        const p = partition(lo,hi); quick(lo,p-1); quick(p+1,hi);
    }
    quick(0,a.length-1);
}

for (const data of [[5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]]) {
    const a = [...data]; sort(a);
    const expected = [...data].sort((x,y) => x-y);
    if (JSON.stringify(a) !== JSON.stringify(expected)) throw new Error("Orden incorrecto");
}
const a = [5,3,4,1,2]; sort(a);
console.log(`[${a.join(", ")}]`);
