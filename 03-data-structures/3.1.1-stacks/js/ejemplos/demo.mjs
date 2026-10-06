function balanced(text) {
    const stack=[], pairs=new Map([[")","("],["]","["],["}","{"]]);
    for (const c of text) {
        if ("([{ ".trim().includes(c)) stack.push(c);
        else if (pairs.has(c)) {
            if (stack.length===0 || stack.pop()!==pairs.get(c)) return false;
        }
    }
    return stack.length===0;
}
for (const [text,expected] of [["([])",true],["([)]",false],["",true],["(",false]]) {
    if (balanced(text)!==expected) throw new Error("Balance");
    console.log(balanced(text)?"valido":"invalido");
}
