class Node:
    def __init__(self, value):
        self.value, self.next = value, None

class Chain:
    def __init__(self):
        self.head = self.tail = None
        self.size = 0
    def add(self, value):
        node = Node(value)
        if self.tail is None:
            self.head = node
        else:
            self.tail.next = node
        self.tail = node
        self.size += 1
    def remove(self, value):
        prev, cur = None, self.head
        while cur is not None and cur.value != value:
            prev, cur = cur, cur.next
        if cur is None:
            return False
        if prev is None:
            self.head = cur.next
        else:
            prev.next = cur.next
        if cur is self.tail:
            self.tail = prev
        self.size -= 1
        return True
    def values(self):
        out, cur = [], self.head
        while cur is not None:
            out.append(cur.value)
            cur = cur.next
        return out

if __name__ == '__main__':
    c = Chain()
    for value in (10,20,30):
        c.add(value)
    assert c.remove(20) and not c.remove(99)
    assert c.values() == [10,30]
    print(c.values())
    assert c.remove(10) and c.remove(30)
    assert c.head is None and c.tail is None and c.size == 0
    print(c.size)
