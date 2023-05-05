const express = require('express');
const app = express();
const port = 3000;

app.get('/license-plates/search/:plateNumber', (req, res) => {
    const input = req.params.plateNumber.toUpperCase();

    // Get the search pattern based on the input
    const searchPattern = getSearchPattern(input);

    // Generate random license plates based on the search pattern
    const response = generateRandomLicensePlates(searchPattern);

    res.send(response);
});

// Get the search pattern based on the input
function getSearchPattern(input) {
    let searchPattern = '';

    // If input starts with "S" or "s"
    if (input.startsWith('S')) {
        searchPattern = `SS${input.substring(1, 3)}${input.substring(3, 6)}`;
    }
    // If input starts with digits
    else if (/^\d+$/.test(input)) {
        searchPattern = `XX${input.substring(0, 2)}XXX`;
    }
    // If input starts with letters
    else if (/^[A-Z]+$/.test(input)) {
        searchPattern = `${input.substring(0, 2)}${input.substring(2, 4)}XXX`;
    }
    // If input starts with letters and digits
    else if (/^[A-Z]+\d+$/.test(input)) {
        searchPattern = `${input.substring(0, 2)}${input.substring(2, 4)}${input.substring(4)}`
    }

    return searchPattern;
}

// Generate random license plates based on the search pattern
function generateRandomLicensePlates(searchPattern) {
    const alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const numbers = '0123456789';

    let response = [];

    // Generate random license plates based on the search pattern
    let numGenerated = 0;
    while (numGenerated < 10) {
        let randomString = '';
        for (let i = 0; i < searchPattern.length; i++) {
            let char = searchPattern.charAt(i);
            if (char === 'X') {
                if (i < 2 || i > 3) {
                    char = alphabet.charAt(Math.floor(Math.random() * alphabet.length));
                } else {
                    char = numbers.charAt(Math.floor(Math.random() * numbers.length));
                }
            }
            randomString += char;
        }
        response.push(randomString);
        numGenerated++;
    }

    return response;
}

app.listen(port, () => {
    console.log(`Listening at http://localhost:${port}`)
})
