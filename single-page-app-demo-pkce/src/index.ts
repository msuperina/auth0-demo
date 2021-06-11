import { load, login, logout } from './auth';
import { loginButton, logoutButton, updateUser, updateMessages } from './dom';
import { getMessages } from './messages';

window.onload = async function () { 
  const { isAuthenticated, accessToken, user } = await load(); 
  updateUser({ isAuthenticated, user });
  const messages = await getMessages(accessToken);
  updateMessages({ messages });
};
loginButton.addEventListener('click', async function () { await login(); });
logoutButton.addEventListener('click', async function () { await logout(); });
