def pairs(n):
    if n < 0:
        raise ValueError("n negativo")
    count = 0
    for i in range(n):
        for j in range(i + 1, n):
            count += 1
    return count

if __name__ == "__main__":
    assert pairs(0) == 0
    for n in (4, 8, 16):
        assert pairs(n) == n * (n - 1) // 2
        print(f"{n}:{pairs(n)}")
    try:
        pairs(-1)
        raise AssertionError("Debió rechazar n")
    except ValueError:
        pass
