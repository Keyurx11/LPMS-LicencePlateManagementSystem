import React, { useState } from 'react';
import axios from 'axios';

function SearchPartialComponent() {
    const [inputValue, setInputValue] = useState('');
    const [results, setResults] = useState([]);
    const [error, setError] = useState('');

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
        setError('');
    };

    const handleFormSubmit = (event) => {
        event.preventDefault();
        if (inputValue.trim()) {
            // Validate input - must contain at least one letter or wildcard character
            if (!/^[A-Za-z*]+$/.test(inputValue)) {
                setError('Invalid input. Please use only letters or wildcard characters (*).');
                return;
            }
            axios.get(`http://localhost:8080/license-plates/search/${inputValue}`)
                .then(response => {
                    setResults(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    };

    return (
        <div>
            <h1>Search for License Plates</h1>
            <form onSubmit={handleFormSubmit}>
                <input type="text" value={inputValue} onChange={handleInputChange} />
                <button type="submit">Search</button>
            </form>
            {error && <p className="error">{error}</p>}
            {results.length > 0 && (
                <ul>
                    {results.map(result => (
                        <li key={result}>{result}</li>
                    ))}
                </ul>
            )}
            <p>Use * as a wildcard character to match any letter.</p>
        </div>
    );
}

export default SearchPartialComponent;
