def frequencies(tokens):
    counts = {}
    for token in tokens:
        counts[token] = counts.get(token, 0) + 1
    return counts

if __name__ == '__main__':
    counts = frequencies(['sol','luna','sol'])
    assert counts == {'sol':2,'luna':1} and 'mar' not in counts
    assert frequencies([]) == {}
    for key in sorted(counts):
        print(f'{key}:{counts[key]}')
