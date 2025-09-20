# MailSender API - Deployment Guide

## Quick Deployment Options

### Option 1: Railway (Recommended - Free Tier)
1. **Sign up** at [railway.app](https://railway.app)
2. **Connect GitHub** and select your MailSender repository
3. **Add Environment Variables:**
   - `SPRING_MAIL_USERNAME` = your-gmail@gmail.com
   - `SPRING_MAIL_PASSWORD` = your-app-password
4. **Deploy** - Railway will automatically build and deploy
5. **Get your URL** - Railway provides a public URL like `https://your-app.railway.app`

### Option 2: Render (Free Tier)
1. **Sign up** at [render.com](https://render.com)
2. **Create New Web Service**
3. **Connect GitHub** repository
4. **Build Command:** `mvn clean package`
5. **Start Command:** `java -jar target/MailSender-0.0.1-SNAPSHOT.jar`
6. **Add Environment Variables:**
   - `SPRING_MAIL_USERNAME` = your-gmail@gmail.com
   - `SPRING_MAIL_PASSWORD` = your-app-password

### Option 3: Heroku (Free Tier - Limited)
1. **Install Heroku CLI**
2. **Login:** `heroku login`
3. **Create app:** `heroku create your-mail-sender-api`
4. **Set environment variables:**
   ```bash
   heroku config:set SPRING_MAIL_USERNAME=your-gmail@gmail.com
   heroku config:set SPRING_MAIL_PASSWORD=your-app-password
   ```
5. **Deploy:** `git push heroku main`

## Environment Variables Setup

### For Production Deployment
Update your `application.properties` to use environment variables:

```properties
spring.application.name=MailSender

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${SPRING_MAIL_USERNAME:seanasb52058@gmail.com}
spring.mail.password=${SPRING_MAIL_PASSWORD:trzf swmc ptxh syav}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Production settings
server.port=${PORT:8080}
```

### Gmail App Password Setup
1. **Enable 2-Factor Authentication** on your Google account
2. **Generate App Password:**
   - Go to Google Account settings
   - Security → 2-Step Verification → App passwords
   - Generate password for "Mail"
3. **Use the generated password** (not your regular Gmail password)

## Local Testing with Production URL

Once deployed, update your frontend code to use the production URL:

```javascript
// Replace localhost with your deployed URL
const API_BASE_URL = 'https://your-app.railway.app'; // or your deployed URL

const sendEmail = async (toEmail, subject, body) => {
  const response = await fetch(`${API_BASE_URL}/api/email/send`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ toEmail, subject, body })
  });
  return await response.json();
};
```

## Docker Deployment (Optional)

### Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/MailSender-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Build and Run
```bash
mvn clean package
docker build -t mail-sender-api .
docker run -p 8080:8080 -e SPRING_MAIL_USERNAME=your-email -e SPRING_MAIL_PASSWORD=your-password mail-sender-api
```

## Troubleshooting

### Common Issues:
1. **CORS Errors:** Make sure your frontend URL is in the CORS config
2. **Email Not Sending:** Check Gmail app password and 2FA settings
3. **Port Issues:** Some platforms use different ports - check environment variables

### Health Check:
Always test your deployed API:
```bash
curl https://your-deployed-url.com/api/email/health
```

## Cost Considerations
- **Railway:** Free tier includes 500 hours/month
- **Render:** Free tier with sleep after 15 minutes of inactivity
- **Heroku:** No longer has free tier
- **DigitalOcean:** $5/month minimum

## Security Notes
- Never commit your email credentials to Git
- Use environment variables for sensitive data
- Consider adding basic authentication for production use
- Monitor your API usage to avoid hitting email limits
