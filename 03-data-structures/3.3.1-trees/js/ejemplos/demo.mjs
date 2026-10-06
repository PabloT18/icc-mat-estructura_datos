class Node { constructor(key) {this.key=key; this.left=null; this.right=null;} }
function insert(node,key) {
    if (node===null) return new Node(key);
    if (key<node.key) node.left=insert(node.left,key);
    else if (key>node.key) node.right=insert(node.right,key);
    return node;
}
function contains(node,key) {
    while (node!==null) {
        if (key===node.key) return true;
        node=key<node.key ? node.left : node.right;
    }
    return false;
}
function inorder(node,out) {
    if (node===null) return;
    inorder(node.left,out); out.push(node.key); inorder(node.right,out);
}
let root=null;
for (const key of [8,3,10,1,6,3]) root=insert(root,key);
const out=[]; inorder(root,out);
if (out.join(',')!=='1,3,6,8,10' || !contains(root,6) || contains(root,7) || contains(null,1))
    throw new Error('BST');
console.log(`[${out.join(', ')}]`);
