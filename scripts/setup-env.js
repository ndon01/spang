const fs = require('fs');
const path = require('path');

// Since we are in /scripts, move up one directory and then down into the resource directory
const ENV_DIR = path.join(__dirname, '..', 'api', 'src', 'main', 'resources');
const ENV_FILE = '.env';
const ENV_EXAMPLE_FILE = '.env.example';

const ENV_PATH = path.join(ENV_DIR, ENV_FILE);
const ENV_EXAMPLE_PATH = path.join(ENV_DIR, ENV_EXAMPLE_FILE);

// Check if directory exists
if (!fs.existsSync(ENV_DIR)) {
    console.error(`Directory ${ENV_DIR} does not exist. Please check your project structure.`);
    process.exit(1);
}

// Check if .env.example file exists
if (!fs.existsSync(ENV_EXAMPLE_PATH)) {
    console.error(`File ${ENV_EXAMPLE_PATH} not found. Please ensure the .env.example file is present.`);
    process.exit(1);
}

// Check if .env file already exists
if (fs.existsSync(ENV_PATH)) {
    console.error(`File ${ENV_PATH} already exists. Please remove it before running this script.`);
    process.exit(1);
}

// Copy .env.example to .env
fs.copyFile(ENV_EXAMPLE_PATH, ENV_PATH, (err) => {
    if (err) {
        console.error(`Failed to create ${ENV_PATH}. Error: ${err.message}`);
        process.exit(1);
    }
    console.log(`.env file created successfully at ${ENV_PATH}`);
});
