FROM node:19-alpine

WORKDIR /usr/src/app

COPY /react/package*.json ./
RUN npm install

COPY /react .

CMD [ "npm", "run", "dev" ]