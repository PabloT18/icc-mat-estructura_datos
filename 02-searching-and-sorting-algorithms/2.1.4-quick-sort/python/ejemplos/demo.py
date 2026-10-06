def sort(a):
    def partition(lo, hi):
        pivot, i = a[hi], lo
        for j in range(lo, hi):
            if a[j] <= pivot:
                a[i], a[j] = a[j], a[i]
                i += 1
        a[i], a[hi] = a[hi], a[i]
        return i
    def quick(lo, hi):
        if lo >= hi:
            return
        p = partition(lo, hi)
        quick(lo, p - 1)
        quick(p + 1, hi)
    quick(0, len(a) - 1)

if __name__ == "__main__":
    for data in ([5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]):
        a = data.copy()
        sort(a)
        assert a == sorted(data)
    a = [5,3,4,1,2]
    sort(a)
    print(a)
