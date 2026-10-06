function unique(values) {
    const seen=new Set(), out=[];
    for (const value of values) if (!seen.has(value)) { seen.add(value); out.push(value); }
    return out;
}
const out=unique(['B','A','B','C','A']);
if (out.join(',')!=='B,A,C' || unique([]).length!==0) throw new Error('Unicidad');
console.log(out.join(','));
