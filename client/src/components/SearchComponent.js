// This is a React functional component for a license plate search feature
// It uses React hooks (useState) to manage component state and axios to make API calls
// It also uses the react-router-dom package to create links for purchasing license plates

import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function SearchComponent() {
// Define state variables using useState
    const [input, setInput] = useState('');
    const [searchResults, setSearchResults] = useState(null);
    const [error, setError] = useState('');
    // Define a function to handle changes to the input field
    const handleInputChange = (event) => {
        setInput(event.target.value);
    };


    const handleSearch = () => {
        // Remove spaces from the input string
        const cleanedInput = input.replace(/\s+/g, '');

        // Validate input format
        if (!/^[A-Z]{2}\d{2}[A-Z]{3}|[A-Z]{1,3}\d{1,4}[A-Z]{1,3}$/.test(cleanedInput.toUpperCase())) {
            setError('Input should be in the format: L1K, L1K2J, L1K2J3 or LL12JK3. Only letters and numbers allowed.');
            return;
        }

        // Convert the cleaned input to uppercase
        const uppercaseInput = cleanedInput.toUpperCase();

        // Make an API call to fetch license plate data
        axios.get(`http://localhost:8080/license-plates/${uppercaseInput}`)
            .then(response => {
                setSearchResults(response.data);
            })
            .catch((error) => {
                setError(error && error.response && error.response.data ? error.response.data : 'An error occurred while fetching data');
            });
    };

// Render the component
    return (
        <div>
            <h1>Search for License Plates</h1>
            <input type="text" value={input} onChange={handleInputChange}/>
            <button onClick={handleSearch}>Search</button>
            {error && <p>{error}</p>}
            {searchResults !== null && (
                <div>
                    <p>Plate: {searchResults.plateID || searchResults.plateNumber}</p>
                    <p>Status: {searchResults.available === false || searchResults.status === 'unavailable' ? 'Unavailable' : 'Available'}</p>
                    {searchResults.available === false ? (
                        <p>Owner: {searchResults.firstName} {searchResults.lastName}</p>
                    ) : (
                        <p>Price: {searchResults.price}</p>
                    )}
                    <Link
                        to={{
                            pathname: `/purchase/${searchResults.plateID || searchResults.plateNumber}`,
                            search: `?plateNumber=${searchResults.plateID || searchResults.plateNumber}&price=${searchResults.price}`,
                        }}
                    >
                        <button
                            disabled={searchResults.available === false || searchResults.status !== 'available'}>Purchase
                        </button>
                    </Link>

                </div>
            )}
        </div>
    );
}

export default SearchComponent;