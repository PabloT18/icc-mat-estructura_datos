def visits(values):
    count = 0
    for value in values:
        count += 1
    return count

if __name__ == "__main__":
    assert visits([]) == 0
    for n in (0, 4, 8):
        assert visits([0] * n) == n
        print(f"{n}:{visits([0] * n)}")
