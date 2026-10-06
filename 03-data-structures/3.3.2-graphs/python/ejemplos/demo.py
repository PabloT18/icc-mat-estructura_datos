from collections import deque

def bfs(graph, start):
    if start not in graph:
        raise ValueError('origen')
    distance = dict.fromkeys(graph, -1)
    distance[start] = 0
    queue = deque([start])
    while queue:
        u = queue.popleft()
        for v in graph[u]:
            if distance[v] == -1:
                distance[v] = distance[u] + 1
                queue.append(v)
    return distance

if __name__ == '__main__':
    graph = {'A':['B','C'], 'B':['A','D'], 'C':['A','D'], 'D':['B','C'], 'E':[]}
    d = bfs(graph,'A')
    assert d['D'] == 2 and d['E'] == -1
    for key,value in d.items():
        print(f'{key}:{value}')
    try:
        bfs(graph,'X')
        raise AssertionError('Origen')
    except ValueError:
        pass
