def balanced(text):
    stack = []
    pairs = {')':'(', ']':'[', '}':'{'}
    for c in text:
        if c in '([{':
            stack.append(c)
        elif c in pairs:
            if not stack or stack.pop() != pairs[c]:
                return False
    return not stack

if __name__ == "__main__":
    for text, expected in [('([])',True), ('([)]',False), ('',True), ('(',False)]:
        assert balanced(text) == expected
        print('valido' if balanced(text) else 'invalido')
