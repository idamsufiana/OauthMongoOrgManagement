export const postLoginToken = async idToken => {
    const API_URL = process.env.REACT_APP_API_URL;
    const path = '/v1/oauth/login';
  
    try {
      const response = await fetch(`${API_URL}${path}`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(idToken), 
      });
      if (!response.ok) throw new Error('bad server condition');
      return true;
    } catch (e) {
      console.error('postLoginToken Error: ', e.message);
      return false;
    }
  };
  