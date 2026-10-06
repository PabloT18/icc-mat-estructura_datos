from pathlib import Path
import json,re
root=Path(__file__).resolve().parents[1]
data=json.loads((root/'assets/catalogo.json').read_text())
names={'java':('Demo.java','java'),'python':('demo.py','python'),'js':('demo.mjs','javascript')}
for t in data['topics']:
 filename,language=names[t['language']]; folder=root/t['path']; code=(folder/'ejemplos'/filename).read_text().rstrip()
 md=folder/'material.md'; text=md.read_text()
 block='<!-- DEMO:START -->\n```'+language+'\n'+code+'\n```\n<!-- DEMO:END -->'
 updated,count=re.subn(r'<!-- DEMO:START -->.*?<!-- DEMO:END -->',lambda _:block,text,flags=re.S)
 if count!=1: raise ValueError('Bloque de demostración inválido: '+t['path'])
 md.write_text(updated)
print('54 bloques sincronizados desde sus fuentes ejecutables.')
