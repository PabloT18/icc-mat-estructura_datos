function sort(a) {
    const buffer = Array(a.length);
    function mergeSort(lo, hi) {
        if (hi-lo <= 1) return;
        const mid = lo + Math.floor((hi-lo)/2);
        mergeSort(lo,mid); mergeSort(mid,hi);
        let i = lo, j = mid, k = lo;
        while (i < mid && j < hi) buffer[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i < mid) buffer[k++] = a[i++];
        while (j < hi) buffer[k++] = a[j++];
        for (k = lo; k < hi; k++) a[k] = buffer[k];
    }
    mergeSort(0,a.length);
}

for (const data of [[5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]]) {
    const a = [...data]; sort(a);
    const expected = [...data].sort((x,y) => x-y);
    if (JSON.stringify(a) !== JSON.stringify(expected)) throw new Error("Orden incorrecto");
}
const a = [5,3,4,1,2]; sort(a);
console.log(`[${a.join(", ")}]`);
