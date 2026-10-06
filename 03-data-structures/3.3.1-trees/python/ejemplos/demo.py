class Node:
    def __init__(self, key):
        self.key, self.left, self.right = key, None, None

def insert(node, key):
    if node is None:
        return Node(key)
    if key < node.key:
        node.left = insert(node.left, key)
    elif key > node.key:
        node.right = insert(node.right, key)
    return node

def contains(node, key):
    while node is not None:
        if key == node.key:
            return True
        node = node.left if key < node.key else node.right
    return False

def inorder(node, out):
    if node is not None:
        inorder(node.left, out)
        out.append(node.key)
        inorder(node.right, out)

if __name__ == '__main__':
    root = None
    for key in (8,3,10,1,6,3):
        root = insert(root, key)
    out = []
    inorder(root, out)
    assert out == [1,3,6,8,10]
    assert contains(root,6) and not contains(root,7) and not contains(None,1)
    print(out)
