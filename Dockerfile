FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY ./conf/nginx.conf /etc/nginx/nginx.conf
COPY ./certificates /etc/letsencrypt
EXPOSE 80
EXPOSE 443
CMD ["nginx", "-g", "daemon off;"]