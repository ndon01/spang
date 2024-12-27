const { execSync } = require('child_process');
const http = require('http');
const path = require('path');

(async () => {
    const isWin = process.platform === 'win32';
    const mvnCommand = isWin ? 'mvnw.cmd' : './mvnw';

    // Define directories
    const rootDir = path.join(__dirname, '..');
    const apiDir = path.join(rootDir, 'api');

    // URL to check if the API is running
    const apiCheckUrl = 'http://localhost:8080/swagger-ui/index.html';

    async function checkIfApiRunning(url) {
        return new Promise(resolve => {
            http.get(url, res => {
                if (res.statusCode === 200) {
                    resolve(true);
                } else {
                    resolve(false);
                }
            }).on('error', () => {
                resolve(false);
            });
        });
    }

    async function waitForApi(url) {
        return new Promise(resolve => {
            const check = () => {
                http.get(url, res => {
                    if (res.statusCode === 200) {
                        resolve();
                    } else {
                        setTimeout(check, 1000);
                    }
                }).on('error', () => {
                    setTimeout(check, 1000);
                });
            };
            check();
        });
    }

    // Check if the server is already running
    const alreadyRunning = await checkIfApiRunning(apiCheckUrl);

    let startedServer = false;
    if (!alreadyRunning) {
        console.log('Starting the Spring Boot application...');
        // Start the Spring Boot application in the background
        // This requires the spring-boot-maven-plugin in pom.xml
        // Make sure pom.xml has the plugin and `spring-boot:start` / `spring-boot:stop` goals available
        execSync(`${mvnCommand} spring-boot:start`, { cwd: apiDir, stdio: 'inherit' });
        startedServer = true;

        // Wait for the API to be ready
        await waitForApi(apiCheckUrl);
    } else {
        console.log('API is already running');
    }

    // Run generation commands
    execSync('npm run spring:generate-openapi-docs', { stdio: 'inherit', cwd: rootDir });
    execSync('npm run angular:generate-types', { stdio: 'inherit', cwd: rootDir });
    execSync('npm run react-native:generate-types', { stdio: 'inherit', cwd: rootDir });

    // Stop the API only if we started it
    if (startedServer) {
        execSync(`${mvnCommand} spring-boot:stop`, { cwd: apiDir, stdio: 'inherit' });
    }

    process.exit(0);
})();
