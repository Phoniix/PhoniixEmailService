# MailSender API - Personal Use

## Base URL
- **Local:** `http://localhost:8080`
- **Production:** `[Your deployed URL]`

## Endpoints

### 1. Health Check
- **URL:** `GET /api/email/health`
- **Description:** Check if the API is running
- **Response:**
```json
{
  "status": "UP",
  "service": "MailSender API",
  "timestamp": "2025-09-20T11:16:54.185201200"
}
```

### 2. Send Email
- **URL:** `POST /api/email/send`
- **Description:** Send an email
- **Request Body:**
```json
{
  "toEmail": "recipient@example.com",
  "subject": "Your Subject",
  "body": "Your message here"
}
```
- **Success Response:**
```json
{
  "success": true,
  "message": "Email sent successfully to recipient@example.com",
  "timestamp": "2025-09-20T11:17:16.191764400"
}
```
- **Error Response:**
```json
{
  "success": false,
  "message": "Failed to send email: [error details]",
  "timestamp": "2025-09-20T11:17:16.191764400"
}
```

## Frontend Integration Examples

### JavaScript/Fetch
```javascript
const sendEmail = async (toEmail, subject, body) => {
  const response = await fetch('http://localhost:8080/api/email/send', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ toEmail, subject, body })
  });
  return await response.json();
};
```

### React Hook
```jsx
const useEmailSender = () => {
  const sendEmail = async (toEmail, subject, body) => {
    try {
      const response = await fetch('http://localhost:8080/api/email/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ toEmail, subject, body })
      });
      return await response.json();
    } catch (error) {
      return { success: false, message: error.message };
    }
  };
  
  return { sendEmail };
};
```

### React Component Example
```jsx
import React, { useState } from 'react';

const EmailForm = () => {
  const [email, setEmail] = useState('');
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      const response = await fetch('http://localhost:8080/api/email/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ toEmail: email, subject, body })
      });
      
      const result = await response.json();
      setResult(result);
      
      if (result.success) {
        setEmail('');
        setSubject('');
        setBody('');
      }
    } catch (error) {
      setResult({ success: false, message: error.message });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input 
          type="email" 
          placeholder="To Email" 
          value={email} 
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input 
          type="text" 
          placeholder="Subject" 
          value={subject} 
          onChange={(e) => setSubject(e.target.value)}
          required
        />
        <textarea 
          placeholder="Body" 
          value={body} 
          onChange={(e) => setBody(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Sending...' : 'Send Email'}
        </button>
      </form>
      
      {result && (
        <div style={{ color: result.success ? 'green' : 'red' }}>
          {result.message}
        </div>
      )}
    </div>
  );
};

export default EmailForm;
```

### Vue.js Example
```vue
<template>
  <form @submit.prevent="sendEmail">
    <input v-model="email" type="email" placeholder="To Email" required />
    <input v-model="subject" type="text" placeholder="Subject" required />
    <textarea v-model="body" placeholder="Body" required></textarea>
    <button type="submit" :disabled="loading">
      {{ loading ? 'Sending...' : 'Send Email' }}
    </button>
  </form>
  
  <div v-if="result" :style="{ color: result.success ? 'green' : 'red' }">
    {{ result.message }}
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      subject: '',
      body: '',
      loading: false,
      result: null
    };
  },
  methods: {
    async sendEmail() {
      this.loading = true;
      try {
        const response = await fetch('http://localhost:8080/api/email/send', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            toEmail: this.email,
            subject: this.subject,
            body: this.body
          })
        });
        this.result = await response.json();
        
        if (this.result.success) {
          this.email = '';
          this.subject = '';
          this.body = '';
        }
      } catch (error) {
        this.result = { success: false, message: error.message };
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>
```

## Testing Commands

### PowerShell
```powershell
# Health Check
Invoke-RestMethod -Uri "http://localhost:8080/api/email/health" -Method GET

# Send Email
Invoke-RestMethod -Uri "http://localhost:8080/api/email/send" -Method POST -ContentType "application/json" -Body '{"toEmail":"test@example.com","subject":"Test Email","body":"Hello from API!"}'
```

### Command Prompt (cmd)
```cmd
# Health Check
curl http://localhost:8080/api/email/health

# Send Email
curl -X POST http://localhost:8080/api/email/send -H "Content-Type: application/json" -d "{\"toEmail\":\"test@example.com\",\"subject\":\"Test Email\",\"body\":\"Hello from API!\"}"
```

## Running the API
```bash
mvn spring-boot:run
```

## Configuration
Update `src/main/resources/application.properties` with your email credentials.

## CORS Support
The API supports CORS for the following origins:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)
- `http://localhost:5173` (Vite)
- `http://localhost:8080` (Vue)
- `http://127.0.0.1:3000`
- `http://127.0.0.1:4200`

## Deployment Options
- **Railway** (free tier)
- **Render** (free tier)
- **Heroku** (free tier)
- **DigitalOcean App Platform**
