import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/styles/shared-styles.css?inline';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/login/theme/lumo/vaadin-login-form.js';
import '@vaadin/email-field/theme/lumo/vaadin-email-field.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/app-layout/theme/lumo/vaadin-app-layout.js';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '334c0e5e76564b91587550659a4222104f4c6a37983d38b662de007715f3afd7') {
    pending.push(import('./chunks/chunk-c7fd6b9ed6637c23468c9186d3c9230c89289cc0c16cac31a1df56f9a26cd86c.js'));
  }
  if (key === '05035bfcd8bd49387d9aaf34e262db3f8db24b4f86689e79e96d94acc6e2dadc') {
    pending.push(import('./chunks/chunk-52999b432f891c7ae815b8837aa281c511c91b185291f31cca3e0b24f8a5ce4a.js'));
  }
  if (key === '0e0d47324887042461199ef16936291796d4d6e196c984fb9056c8827864ceb7') {
    pending.push(import('./chunks/chunk-306a8662ee296c70f4c1b08fef6c0673a4f21512a0f0dc6d1bea939d91078eb3.js'));
  }
  if (key === 'b97b14722e4bab53e66dcc16d63ff1cdf0216a334a47b2818e555d82dc90ab5a') {
    pending.push(import('./chunks/chunk-619d33b3979963f986b0ce30178d42a520d1ce6f3c048f93770d6d348e0f2fe4.js'));
  }
  if (key === '865699b05750169416e362fc46f69c47cdcf51c4bba99da2411de5b8265d9c00') {
    pending.push(import('./chunks/chunk-619d33b3979963f986b0ce30178d42a520d1ce6f3c048f93770d6d348e0f2fe4.js'));
  }
  if (key === '969ac257565fae3ef7f90fb667a4edce818fd0320162916646c11062b706e23a') {
    pending.push(import('./chunks/chunk-b044d4758703594b87971cb7cfa271d47f13a282d29c47a9fc450e34fee7d258.js'));
  }
  if (key === '1d4d3d9ad2cd50dd22de4c744e69bb4bcc083d22d5f591301980e7cde23140ec') {
    pending.push(import('./chunks/chunk-086e51f05777efe021bdbef2b43637fc0519e0880e8718d4f75ac03a1c2df02d.js'));
  }
  if (key === 'ff16a40eb5333c527050cf140e270ede86016d47c5c34eb27bf2c07300fe3c89') {
    pending.push(import('./chunks/chunk-4b9445e38a7a74c5d1ab548d7a031e2a34bd31bace527dca053369f223798c53.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}