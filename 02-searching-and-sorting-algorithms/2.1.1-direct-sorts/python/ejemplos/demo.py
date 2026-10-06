def bubble(a):
    for end in range(len(a) - 1, 0, -1):
        changed = False
        for j in range(end):
            if a[j] > a[j + 1]:
                a[j], a[j + 1] = a[j + 1], a[j]
                changed = True
        if not changed:
            break

def selection(a):
    for i in range(len(a) - 1):
        minimum = i
        for j in range(i + 1, len(a)):
            if a[j] < a[minimum]:
                minimum = j
        a[i], a[minimum] = a[minimum], a[i]

def insertion(a):
    for i in range(1, len(a)):
        key, j = a[i], i - 1
        while j >= 0 and a[j] > key:
            a[j + 1] = a[j]
            j -= 1
        a[j + 1] = key

def sort(a):
    b, c = a.copy(), a.copy()
    bubble(b)
    selection(c)
    insertion(a)
    assert a == b == c

if __name__ == "__main__":
    for data in ([5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]):
        a = data.copy()
        sort(a)
        assert a == sorted(data)
    a = [5,3,4,1,2]
    sort(a)
    print(a)
