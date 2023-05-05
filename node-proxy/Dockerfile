# Use the official Node.js image as the base image
FROM node:14

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the application source code
COPY . .

# Expose the port the app will run on
EXPOSE 3001

# Start the server
CMD ["npm", "start"]
