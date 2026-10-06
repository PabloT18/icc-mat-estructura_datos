def validate(n):
    if not isinstance(n, int) or not 0 <= n <= 20:
        raise ValueError("n debe estar entre 0 y 20")

def factorial(n):
    validate(n)
    def recursive(k):
        return 1 if k == 0 else k * recursive(k-1)
    return recursive(n)

def iterative(n):
    validate(n)
    value = 1
    for i in range(1,n+1):
        value *= i
    return value

if __name__ == "__main__":
    for n in (0,3,5,20):
        assert factorial(n) == iterative(n)
        print(f"{n}:{factorial(n)}")
    for n in (-1,21):
        try:
            factorial(n)
            raise AssertionError("Dominio")
        except ValueError:
            pass
