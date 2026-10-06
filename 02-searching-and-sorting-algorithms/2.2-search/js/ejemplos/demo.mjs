function sequential(a,key) {
    for (let i=0; i<a.length; i++) if (a[i]===key) return i;
    return -1;
}
function binaryFirst(a,key) {
    let lo=0, hi=a.length;
    while (lo<hi) {
        const mid=lo+Math.floor((hi-lo)/2);
        if (a[mid]<key) lo=mid+1; else hi=mid;
    }
    return lo<a.length && a[lo]===key ? lo : -1;
}
const a=[1,3,3,7,9];
for (const key of [3,8,1,9]) {
    if (binaryFirst(a,key)!==sequential(a,key)) throw new Error("Búsqueda");
    console.log(`${key}:${binaryFirst(a,key)}`);
}
if (binaryFirst([],3)!==-1) throw new Error("Vacío");
