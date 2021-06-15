import { User } from "@auth0/auth0-spa-js";

const userComponent = document.getElementById('user-component') as HTMLDivElement;
const userContainer = document.getElementById('user') as HTMLDivElement;
const messagesComponent = document.getElementById('messages-component') as HTMLDivElement;
const messagesContainer = document.getElementById('messages') as HTMLDivElement;
export const loginButton = document.getElementById('login') as HTMLButtonElement;
export const logoutButton = document.getElementById('logout') as HTMLButtonElement;

function updateIsAuthenticated(isAuthenticated: boolean) {
  loginButton.disabled = isAuthenticated;
  logoutButton.disabled = !isAuthenticated;
}

export function updateUser(params: { 
  isAuthenticated: boolean, 
  user: User,
}) {
  const { isAuthenticated, user } = params;
  updateIsAuthenticated(isAuthenticated);
  userComponent.style.display = user ? 'block' : 'none';
  userContainer.textContent = user ? JSON.stringify(user) : '';
}

export function updateMessages(params: { 
  messages: { value: string }[] 
}) {
  const { messages } = params;
  if (messages.length > 0) {
    messagesComponent.style.display = 'block';
    messagesContainer.innerHTML = messages
      .map(message => `<li>${message.value}</li>`)
      .join('');
  } else {
    messagesComponent.style.display = 'none';
    messagesContainer.innerHTML = '';
  }
}
