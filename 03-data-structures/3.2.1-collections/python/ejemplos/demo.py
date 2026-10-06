def unique(values):
    seen, out = set(), []
    for value in values:
        if value not in seen:
            seen.add(value)
            out.append(value)
    return out

if __name__ == '__main__':
    out = unique(['B','A','B','C','A'])
    assert out == ['B','A','C'] and unique([]) == []
    print(','.join(out))
