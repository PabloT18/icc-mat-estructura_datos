from pathlib import Path
import json,subprocess,sys,platform,shutil,os
root=Path(__file__).resolve().parents[1]
data=json.loads((root/'assets/catalogo.json').read_text())
commands={'java':['java','ejemplos/Demo.java'],'python':[sys.executable,'ejemplos/demo.py'],'js':['node','ejemplos/demo.mjs']}
results=[]
for t in data['topics']:
 folder=root/t['path'];cmd=commands[t['language']];expected=(folder/'ejemplos/salida-esperada.txt').read_text()
 try:
  p=subprocess.run(cmd,cwd=folder,capture_output=True,text=True,timeout=30)
  result=dict(id=t['id'],command=['python3' if x==sys.executable else x for x in cmd],exit_code=p.returncode,stdout=p.stdout,stderr=p.stderr,expected=expected,passed=p.returncode==0 and p.stdout.replace('\r\n','\n')==expected)
 except (subprocess.TimeoutExpired,FileNotFoundError) as e:
  result=dict(id=t['id'],passed=False,error=type(e).__name__)
 results.append(result)
versions={}
for name,cmd in {'java':['java','-version'],'python':[sys.executable,'--version'],'node':['node','--version']}.items():
 p=subprocess.run(cmd,capture_output=True,text=True); versions[name]=(p.stdout+p.stderr).strip()
folder=root/'docs/verificacion';folder.mkdir(parents=True,exist_ok=True)
(folder/'ejecuciones.json').write_text(json.dumps(dict(versions=versions,results=results),ensure_ascii=False,indent=2))
failed=[r['id'] for r in results if not r['passed']]
print(f'{len(results)-len(failed)}/{len(results)} demostraciones verificadas')
if failed: print('Fallos: '+', '.join(failed))
sys.exit(bool(failed))
