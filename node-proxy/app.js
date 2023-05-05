const express = require('express');
const cors = require('cors');
const app = express();
const port = 3001;

//TO TRY OUR NODE API RUN "http://localhost:3001/license-plates/search/S*1*A" IN URL.

// Enable CORS
app.use(cors());

const licensePlateRegex = /[A-Z]{2}[0-9]{2}[A-Z]{3}|[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}/;

app.get('/license-plates/search/:plateNumber', (req, res) => {
    const input = req.params.plateNumber.toUpperCase();

    // Generate random license plates based on the input
    const response = generateRandomLicensePlates(input);

    res.send(response);
});

// Generate random license plates based on the search pattern
function generateRandomLicensePlates(input) {
    const alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numbers = '0123456789';
    let response = [];

    if (!input.includes('*')) {
        // No wildcard characters, generate plates as before
        for (let i = 4; i <= 7; i++) {
            const numRandomChars = i - input.length > 0 ? i - input.length : 1;

            for (let j = 0; j < 3; j++) {
                let plate = input;

                // Add random characters
                for (let k = 0; k < numRandomChars; k++) {
                    const randomChar = Math.random() < 0.5 ? alphabet.charAt(Math.floor(Math.random() * alphabet.length)) : numbers.charAt(Math.floor(Math.random() * numbers.length));
                    const randomIndex = Math.floor(Math.random() * plate.length);
                    plate = plate.slice(0, randomIndex) + randomChar + plate.slice(randomIndex);
                }

                // Add remaining characters
                for (let k = plate.length; k < i; k++) {
                    const randomChar = Math.random() < 0.5 ? alphabet.charAt(Math.floor(Math.random() * alphabet.length)) : numbers.charAt(Math.floor(Math.random() * numbers.length));
                    plate += randomChar;
                }

                response.push(plate);
            }
        }
    } else {
        // Wildcard characters found, generate plates accordingly
        const inputLength = input.length;
        const numRandomChars = input.split('*').length - 1;
        const minLength = inputLength - numRandomChars;
        const maxLength = inputLength;

        for (let i = minLength; i <= maxLength; i++) {
            for (let j = 0; j < 3; j++) {
                let plate = '';

                for (let k = 0; k < inputLength; k++) {
                    if (input.charAt(k) === '*') {
                        // Add random character
                        const randomChar = Math.random() < 0.5 ? alphabet.charAt(Math.floor(Math.random() * alphabet.length)) : numbers.charAt(Math.floor(Math.random() * numbers.length));
                        plate += randomChar;
                    } else {
                        // Add fixed character
                        plate += input.charAt(k);
                    }
                }

                response.push(plate);
            }
        }
    }

    return response;
}


app.listen(port, () => {
    console.log(`Listening at http://localhost:${port}`)
});
