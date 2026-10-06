def validate(n):
    if not isinstance(n, int) or not 0 <= n <= 1_000_000:
        raise ValueError("fuera de rango")

def sum_loop(n):
    validate(n)
    total = 0
    for i in range(1, n + 1):
        total += i
    return total

def sum_formula(n):
    validate(n)
    return n * (n + 1) // 2

if __name__ == "__main__":
    for n in (0, 5, 10, 1000):
        assert sum_loop(n) == sum_formula(n)
        print(f"{n}:{sum_loop(n)}")
    try:
        sum_loop(-1)
        raise AssertionError("Dominio")
    except ValueError:
        pass
