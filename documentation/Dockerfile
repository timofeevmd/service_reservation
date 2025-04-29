FROM node:18-alpine
WORKDIR /app

COPY package*.json ./documentation/
RUN cd documentation && npm install --include=dev  && npm install -g vitepress
ENV PATH="/app/node_modules/.bin:$PATH"

COPY . .

EXPOSE 5173

CMD ["vitepress", "dev", "--host", "0.0.0.0"]