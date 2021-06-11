const MESSAGES_API_URL = 'http://localhost:3001/api/messages';

export async function getMessages(accessToken: string) {
  try {
    const response = await fetch(MESSAGES_API_URL, {
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json',
      },
    });
    const messages = await response.json() as { value: string }[];
    return messages;
  } catch (error) {
    console.error(error);
    return [];
  }
}
