import React, { useState } from 'react';
import axios from 'axios';

function SearchComponent() {
    const [input, setInput] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [error, setError] = useState('');

    const handleInputChange = (event) => {
        setInput(event.target.value);
    };

    const handleSearch = () => {
        // Validate input
        if (input === '') {
            setError('Input cannot be empty');
            return;
        }

        axios.get(`http://localhost:8080/license-plates/search/${input}`)
            .then(response => {
                setSearchResults(response.data);
            })
            .catch(error => {
                setError(error.response.data);
            });
    };

    return (
        <div>
            <h1>Search for License Plates</h1>
            <input type="text" value={input} onChange={handleInputChange} />
            <button onClick={handleSearch}>Search</button>
            {error && <p>{error}</p>}
            {searchResults.length > 0 &&
                <ul>
                    {searchResults.map(plate => <li key={plate}>{plate}</li>)}
                </ul>
            }
        </div>
    );
}

export default SearchComponent;
