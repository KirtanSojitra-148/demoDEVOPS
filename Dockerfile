FROM nginx:alpine
COPY reg.html /usr/share/nginx/html/index.html
EXPOSE 80