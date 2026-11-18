/* Restricciones de interacción y ocultación durante la impresión.
   No impide descargar el HTML ni realizar capturas externas. */
(() => {
  const area = document.querySelector('[data-protected="true"]');

  if (!area) return;

  area.classList.add('protected');
  document.body.classList.add('print-protected');

  // Aviso que solo se mostrará al imprimir.
  let overlay = document.getElementById('securityOverlay');

  if (!overlay) {
    overlay = document.createElement('div');
    overlay.id = 'securityOverlay';
    overlay.className = 'security-overlay';

    const box = document.createElement('div');
    box.className = 'security-box';

    const title = document.createElement('h2');
    title.textContent = 'Impresión no disponible';

    const message = document.createElement('p');
    message.textContent =
      'Este material está disponible para consulta en línea.';

    box.append(title, message);
    overlay.append(box);
    document.body.append(overlay);
  }

  const editable = target =>
    target instanceof Element &&
    Boolean(
      target.closest(
        'input, textarea, select, [contenteditable="true"]'
      )
    );

  for (const eventName of [
    'contextmenu',
    'selectstart',
    'copy',
    'cut',
    'dragstart'
  ]) {
    area.addEventListener(eventName, event => {
      if (!editable(event.target)) {
        event.preventDefault();
      }
    });
  }

  document.addEventListener('keydown', event => {
    if (editable(event.target)) return;

    const key = event.key.toLowerCase();
    const modifier = event.ctrlKey || event.metaKey;

    if (
      (modifier && ['c', 's', 'u', 'a'].includes(key)) ||
      key === 'f12' ||
      (modifier && event.shiftKey && ['i', 'j', 'c'].includes(key)) ||
      (event.metaKey && event.altKey && key === 'i')
    ) {
      event.preventDefault();
    }
  });

  // No se oculta el contenido al perder el foco.
  // Ctrl+P / Cmd+P abre la impresión, donde actúa el CSS.
})();