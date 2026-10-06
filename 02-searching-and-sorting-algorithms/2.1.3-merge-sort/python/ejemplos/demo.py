def sort(a):
    buffer = [0] * len(a)
    def merge_sort(lo, hi):
        if hi - lo <= 1:
            return
        mid = lo + (hi - lo) // 2
        merge_sort(lo, mid)
        merge_sort(mid, hi)
        i, j, k = lo, mid, lo
        while i < mid and j < hi:
            if a[i] <= a[j]:
                buffer[k] = a[i]
                i += 1
            else:
                buffer[k] = a[j]
                j += 1
            k += 1
        while i < mid:
            buffer[k] = a[i]
            i += 1
            k += 1
        while j < hi:
            buffer[k] = a[j]
            j += 1
            k += 1
        for k in range(lo, hi):
            a[k] = buffer[k]
    merge_sort(0, len(a))

if __name__ == "__main__":
    for data in ([5,3,4,1,2], [], [1], [2,2,1], [-1,5,0], [1,2,3], [3,2,1]):
        a = data.copy()
        sort(a)
        assert a == sorted(data)
    a = [5,3,4,1,2]
    sort(a)
    print(a)
