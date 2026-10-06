function frequencies(tokens) {
    const counts=new Map();
    for (const token of tokens) counts.set(token,(counts.get(token) ?? 0)+1);
    return counts;
}
const counts=frequencies(['sol','luna','sol']);
if (counts.get('sol')!==2 || counts.get('luna')!==1 || counts.has('mar') || frequencies([]).size!==0)
    throw new Error('Frecuencias');
for (const key of [...counts.keys()].sort()) console.log(`${key}:${counts.get(key)}`);
