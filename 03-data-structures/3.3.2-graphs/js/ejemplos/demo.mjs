function bfs(graph,start) {
    if (!graph.has(start)) throw new RangeError('origen');
    const distance=new Map([...graph.keys()].map(v=>[v,-1]));
    const queue=[start]; let head=0; distance.set(start,0);
    while (head<queue.length) {
        const u=queue[head++];
        for (const v of graph.get(u)) if (distance.get(v)===-1) {
            distance.set(v,distance.get(u)+1); queue.push(v);
        }
    }
    return distance;
}
const graph=new Map([['A',['B','C']],['B',['A','D']],['C',['A','D']],['D',['B','C']],['E',[]]]);
const d=bfs(graph,'A');
if (d.get('D')!==2 || d.get('E')!==-1) throw new Error('BFS');
for (const [key,value] of d) console.log(`${key}:${value}`);
let rejected=false;
try { bfs(graph,'X'); } catch (e) { rejected=e instanceof RangeError; }
if (!rejected) throw new Error('Origen');
