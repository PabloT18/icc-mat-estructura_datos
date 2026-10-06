def fibonacci(n, cache):
    if not isinstance(n,int) or not 0 <= n <= 30:
        raise ValueError('n fuera de rango')
    def memo(k):
        if k in cache:
            return cache[k]
        value = k if k < 2 else memo(k-1)+memo(k-2)
        cache[k] = value
        return value
    return memo(n)

if __name__ == '__main__':
    cache = {}
    for n in (10,10,5):
        value = fibonacci(n,cache)
        assert value == (5 if n == 5 else 55)
        print(f'{n}:{value}:{len(cache)}')
    assert fibonacci(0,{}) == 0
    try:
        fibonacci(-1,cache)
        raise AssertionError('Dominio')
    except ValueError:
        pass
