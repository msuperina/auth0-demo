import createAuth0Client, { Auth0Client } from '@auth0/auth0-spa-js';

const AUTH0_DOMAIN = 'dev-flidg5q0.eu.auth0.com';
const CLIENT_ID = 'eNbPNTavMWbzWwGp9txUmL1IrHPSXHPK';
const REDIRECT_URL = 'http://localhost:3003';

let auth0Client: Auth0Client;

export async function load() {
  auth0Client = await createAuth0Client({
    domain: AUTH0_DOMAIN,
    client_id: CLIENT_ID,
    redirect_uri: REDIRECT_URL,
    advancedOptions: { defaultScope: null },
    scope: 'email read:messages',
    audience: 'http://localhost:3001/api',
  });

  const query = window.location.search;
  if (query.includes("code=") && query.includes("state=")) {
    await auth0Client.handleRedirectCallback();
    window.history.replaceState({}, document.title, "/");
  }

  const isAuthenticated = await auth0Client.isAuthenticated();
  let accessToken;
  let user;

  if (isAuthenticated) {
    accessToken = await auth0Client.getTokenSilently();
    user = await auth0Client.getUser();
  }

  return { isAuthenticated, accessToken, user };
}

export async function login() {
  await auth0Client.loginWithRedirect();
}

const returnTo = window.location.origin;
export async function logout() {
  await auth0Client.logout({ returnTo });
}
