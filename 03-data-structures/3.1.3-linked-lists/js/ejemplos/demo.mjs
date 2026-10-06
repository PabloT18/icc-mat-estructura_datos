class Node { constructor(value) { this.value=value; this.next=null; } }
class Chain {
    constructor() { this.head=null; this.tail=null; this.size=0; }
    add(value) {
        const node=new Node(value);
        if (this.tail===null) this.head=node; else this.tail.next=node;
        this.tail=node; this.size++;
    }
    remove(value) {
        let prev=null, cur=this.head;
        while (cur!==null && cur.value!==value) { prev=cur; cur=cur.next; }
        if (cur===null) return false;
        if (prev===null) this.head=cur.next; else prev.next=cur.next;
        if (cur===this.tail) this.tail=prev;
        this.size--; return true;
    }
    values() {
        const out=[];
        for (let cur=this.head;cur!==null;cur=cur.next) out.push(cur.value);
        return out;
    }
}
const c=new Chain(); [10,20,30].forEach(x=>c.add(x));
if (!c.remove(20) || c.remove(99)) throw new Error('Eliminación');
if (JSON.stringify(c.values())!=='[10,30]') throw new Error('Contenido');
console.log(`[${c.values().join(', ')}]`);
if (!c.remove(10) || !c.remove(30) || c.head!==null || c.tail!==null || c.size!==0)
    throw new Error('Vacío inconsistente');
console.log(c.size);
