// This is a React functional component for searching for partial license plates
// It uses React hooks (useState) to manage component state and axios to make API calls
// It also uses the useNavigate hook from react-router-dom to navigate to a new page with state data

import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function PartialSearchComponent() {
    const [input, setInput] = useState('');
    const [searchResults, setSearchResults] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleInputChange = (event) => {
        const inputString = event.target.value;
        const sanitizedInput = inputString.replace(/[^a-zA-Z0-9*]/g, '');
        setInput(sanitizedInput);
    };

    const handleSearch = () => {
        // Remove spaces from the input string
        const cleanedInput = input.replace(/\s+/g, '');

        // Validate input format
        const numericRegex = /\d/;
        if (cleanedInput.length < 3 || !numericRegex.test(cleanedInput)) {
            setError('!!You must provide at least 3 values in input, including at least one numeric value.!!');
            return;
        }

        // Convert the cleaned input to uppercase
        const uppercaseInput = cleanedInput.toUpperCase();

        axios
            .get(`http://localhost:8080/license-plates/search/${uppercaseInput}`)
            .then((response) => {
                setSearchResults(response.data);
                console.log(response.data);
            })
            .catch((error) => {
                setError(
                    error && error.response && error.response.data
                        ? error.response.data
                        : 'An error occurred while fetching data'
                );
            });
    };


    return (
        <div>
            <h1>Search for Partial License Plates</h1>
            <input type="text" value={input} onChange={handleInputChange} />
            <button onClick={handleSearch}>Search</button>
            {error && <p>{error}</p>}
            {Array.isArray(searchResults) &&
                searchResults.map((result, index) => (
                    <div key={index}>
                        <p>Plate: {result.plateNumber}</p>
                        <p>
                            Status:{' '}
                            {result.available === false || result.status === 'unavailable'
                                ? 'Unavailable'
                                : 'Available'}
                        </p>
                        {result.available === false ? (
                            <p>
                                Owner: {result.firstName} {result.lastName}
                            </p>
                        ) : (
                            <p>Price: {result.price}</p>
                        )}
                        <Link
                            to={{
                                pathname: `/purchase/${result.plateID || result.plateNumber}`,
                                search: `?plateNumber=${result.plateID || result.plateNumber}&price=${result.price}`,
                            }}
                            disabled={result.available === false || result.status !== 'available'}
                        >
                            <button>
                                Purchase
                            </button>
                        </Link>
                    </div>
                ))}
            <p>To use wild card search use * in your input example S1*Y or L*K1**J.</p>
            <p>Try something like S*2*** even *23*A.</p>
        </div>
    );
}

export default PartialSearchComponent;
