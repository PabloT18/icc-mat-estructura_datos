class Queue:
    def __init__(self, capacity):
        if capacity <= 0:
            raise ValueError('capacidad')
        self.data = [None] * capacity
        self.head = self.size = 0
    def add(self, value):
        if self.size == len(self.data):
            raise OverflowError('llena')
        self.data[(self.head+self.size) % len(self.data)] = value
        self.size += 1
    def remove(self):
        if self.size == 0:
            raise IndexError('vacía')
        value = self.data[self.head]
        self.data[self.head] = None
        self.head = (self.head+1) % len(self.data)
        self.size -= 1
        return value

if __name__ == '__main__':
    q = Queue(3)
    for value in (10,20,30):
        q.add(value)
    try:
        q.add(99)
        raise AssertionError('Llena')
    except OverflowError:
        pass
    assert q.size == 3
    print(q.remove())
    q.add(40)
    for expected in (20,30,40):
        value = q.remove()
        assert value == expected
        print(value)
    try:
        q.remove()
        raise AssertionError('Vacía')
    except IndexError:
        pass
    try:
        Queue(0)
        raise AssertionError('Capacidad')
    except ValueError:
        pass
