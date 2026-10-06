import fs from 'node:fs/promises';
import path from 'node:path';
import {fileURLToPath} from 'node:url';
const root=path.resolve(path.dirname(fileURLToPath(import.meta.url)),'..');
const target=path.join(root,'build/pages');
await fs.rm(target,{recursive:true,force:true});
await fs.mkdir(target,{recursive:true});
const excluded=new Set(['node_modules','.git','.gradle','build','out','__pycache__','.github','scripts','package.json','package-lock.json','MANIFEST.sha256','.gitignore','.gitattributes']);
for(const e of await fs.readdir(root,{withFileTypes:true})) {
 if(excluded.has(e.name)) continue;
 await fs.cp(path.join(root,e.name),path.join(target,e.name),{recursive:true,filter:p=>!path.relative(root,p).split(path.sep).some(v=>excluded.has(v))&&!/\.(?:class|pyc)$/.test(p)});
}
console.log('Contenido preparado en build/pages para GitHub Pages.');
