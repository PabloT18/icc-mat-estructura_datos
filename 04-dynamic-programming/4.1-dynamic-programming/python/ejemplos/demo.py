def recursive(n, counter):
    counter[0] += 1
    return n if n < 2 else recursive(n-1,counter)+recursive(n-2,counter)

def tabulated(n):
    dp = [0] * (n+2)
    dp[1] = 1
    for i in range(2,n+1):
        dp[i] = dp[i-1]+dp[i-2]
    return dp[n]

def validate(n):
    if not isinstance(n,int) or not 0 <= n <= 30:
        raise ValueError('n fuera de rango')

if __name__ == '__main__':
    for n in (0,1,5,10):
        validate(n)
        counter = [0]
        value = recursive(n,counter)
        assert value == tabulated(n)
        print(f'{n}:{value}:{counter[0]}')
    try:
        validate(-1)
        raise AssertionError('Dominio')
    except ValueError:
        pass
