def sort(a):
    h = len(a) // 2
    while h > 0:
        for i in range(h, len(a)):
            key, j = a[i], i
            while j >= h and a[j-h] > key:
                a[j] = a[j-h]
                j -= h
            a[j] = key
        h //= 2

if __name__ == "__main__":
    for data in ([5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]):
        a = data.copy()
        sort(a)
        assert a == sorted(data)
    a = [5,3,4,1,2]
    sort(a)
    print(a)
