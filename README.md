# Spang
This is just boilerplate for a Springboot & Angular project

# Table of Contents
1. [Dependencies](#dependencies)
2. [Setup](#setup)

# DEPENDENCIES
There are a couple prerequisites required to get this application running for local development.
The following are the required dependencies:
- Docker
- Java 21
- Node 20+
- IntelliJ (Optional but very helpful)

# SETUP
1. Clone the repository
```bash
git clone https://github.com/ndon01/spang.git
cd spang
```

2. Setup environment variables
- Run the run configuration `setup api .env` or run the following commands in the terminal at the root of the project:
```bash
npm run sprint:setup-env
```
- Create a file named `.env` in the '/api/src/main/resources' directory of the project.
- There is a `.env.example` file in the same directory that you can copy and paste into the new `.env` file.


3. Install node dependencies
- (IntelliJ) Run the `angular - install` script
- (Terminal) Change directory to '/angular' and run:
```bash
npm install
```

4. Running the Docker Containers
- Make sure docker is open and running
- (IntelliJ) Run the `Containers` run configuration
- (Terminal) Change directory to '/' and run:
```bash
docker-compose up -d
```

5. Generate Types Script:

First time running, you will probably need to install the openapi-generator-cli globally:
```bash
npm install -g @openapitools/openapi-generator-cli
```

Next you will need to generate the types for the frontend. This can be done by running the following command:
- (IntelliJ) Run the `all - generate types` run configuration
- (Terminal) Change directory to '/' and run:
```bash
npm run all:generate-types
```

6. Start the API
- (IntelliJ) Run the API through the `API` run configuration in IntelliJ
- (Terminal) Change directory to '/api' and run:
```bash
./mvnw spring-boot:run
```

7. Building the Frontend:
- (IntelliJ) Run the run configuration `angular - watch`
- (Terminal) Change directory to '/angular' and run:
```bash
npm run watch
```

The application should now be running on `localhost:8080`, you may need to hard refresh to see the changes.

Default credentials:
- Username: admin
- Password: changeme