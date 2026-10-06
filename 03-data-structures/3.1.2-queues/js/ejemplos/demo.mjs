class Queue {
    constructor(capacity) {
        if (!Number.isInteger(capacity) || capacity<=0) throw new RangeError('capacidad');
        this.data=Array(capacity).fill(null); this.head=0; this.size=0;
    }
    add(value) {
        if (this.size===this.data.length) throw new RangeError('llena');
        this.data[(this.head+this.size)%this.data.length]=value; this.size++;
    }
    remove() {
        if (this.size===0) throw new RangeError('vacía');
        const value=this.data[this.head]; this.data[this.head]=null;
        this.head=(this.head+1)%this.data.length; this.size--; return value;
    }
}
function rejects(fn) {
    let rejected=false; try { fn(); } catch (e) { rejected=e instanceof RangeError; }
    if (!rejected) throw new Error('Se esperaba error');
}
const q=new Queue(3); [10,20,30].forEach(x=>q.add(x));
rejects(()=>q.add(99));
if (q.size!==3) throw new Error('Tamaño');
console.log(q.remove()); q.add(40);
for (const expected of [20,30,40]) {
    const value=q.remove(); if (value!==expected) throw new Error('FIFO'); console.log(value);
}
rejects(()=>q.remove()); rejects(()=>new Queue(0));
