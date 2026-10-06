def sequential(a, key):
    for i, value in enumerate(a):
        if value == key:
            return i
    return -1

def binary_first(a, key):
    lo, hi = 0, len(a)
    while lo < hi:
        mid = lo + (hi-lo)//2
        if a[mid] < key:
            lo = mid+1
        else:
            hi = mid
    return lo if lo < len(a) and a[lo] == key else -1

if __name__ == "__main__":
    a = [1,3,3,7,9]
    for key in (3,8,1,9):
        assert binary_first(a,key) == sequential(a,key)
        print(f"{key}:{binary_first(a,key)}")
    assert binary_first([],3) == -1
