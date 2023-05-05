// This is a React functional component for purchasing a license plate
// It uses React hooks (useState) to manage component state and axios to make API calls
// It also uses the useLocation hook from react-router-dom to retrieve query parameters

import React, { useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

function PurchaseComponent() {
    const query = useQuery();
    const plateNumber = query.get('plateNumber');
    const price = query.get('price');

    const [buyerName, setBuyerName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [vehicleMake, setVehicleMake] = useState('');
    const [vehicleModel, setVehicleModel] = useState('');
    const [vehicleType, setVehicleType] = useState('');
    const [errors, setErrors] = useState({});

    const handleSubmit = (event) => {
        event.preventDefault();

        // Validate input fields
        let errorsFound = {};
        if (plateNumber.length < 2 || plateNumber.length > 7) {
            errorsFound.plateNumber = 'Plate number must be between 2 and 7 characters long.';
        }

        if (buyerName.trim() === '') {
            errorsFound.buyerName = 'Buyer name is required.';
        }

        if (Object.keys(errorsFound).length > 0) {
            setErrors(errorsFound);
            return;
        }

        // Post request to purchase the license plate
        axios.post('http://localhost:8080/purchase', {
            plateNumber: plateNumber.toUpperCase(),
            buyerName: buyerName,
            lastName: lastName,
            email: email,
            phone: phone,
            vehicleMake: vehicleMake,
            vehicleModel: vehicleModel,
            vehicleType: vehicleType,
            price: price
        })
            .then((response) => {
                alert(response.data);
                setBuyerName('');
                setLastName('');
                setEmail('');
                setPhone('');
                setVehicleMake('');
                setVehicleModel('');
                setVehicleType('');
                setErrors({});
            })
            .catch((error) => {
                alert(error.response.data);
            });
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="plateNumber">Plate Number:</label>
                    <input type="text" id="plateNumber" name="plateNumber" value={plateNumber} readOnly />
                    {errors.plateNumber && <span className="error">{errors.plateNumber}</span>}
                </div>
                <div>
                    <label htmlFor="price">Price:</label>
                    <input type="number" id="price" name="price" min="1" step="1" value={price} readOnly />
                    {errors.price && <span className="error">{errors.price}</span>}
                </div>
                <div>
                    <label htmlFor="buyerName">First Name:</label>
                    <input type="text" id="buyerName" name="buyerName" value={buyerName}
                           onChange={event => setBuyerName(event.target.value)}/>
                    {errors.buyerName && <span className="error">{errors.buyerName}</span>}
                </div>

                <div>
                    <label htmlFor="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value={lastName}
                           onChange={event => setLastName(event.target.value)}/>
                    {errors.lastName && <span className="error">{errors.lastName}</span>}
                </div>

                <div>
                    <label htmlFor="email">Email:</label>
                    <input type="email" id="email" name="email" value={email}
                           onChange={event => setEmail(event.target.value)}/>
                    {errors.email && <span className="error">{errors.email}</span>}
                </div>

                <div>
                    <label htmlFor="phone">Phone:</label>
                    <input type="tel" id="phone" name="phone" value={phone}
                           onChange={event => setPhone(event.target.value)}/>
                    {errors.phone && <span className="error">{errors.phone}</span>}
                </div>

                <div>
                    <label htmlFor="vehicleMake">Vehicle Make:</label>
                    <input type="text" id="vehicleMake" name="vehicleMake" value={vehicleMake}
                           onChange={event => setVehicleMake(event.target.value)}/>
                    {errors.vehicleMake && <span className="error">{errors.vehicleMake}</span>}
                </div>

                <div>
                    <label htmlFor="vehicleModel">Vehicle Model:</label>
                    <input type="text" id="vehicleModel" name="vehicleModel" value={vehicleModel}
                           onChange={event => setVehicleModel(event.target.value)}/>
                    {errors.vehicleModel && <span className="error">{errors.vehicleModel}</span>}
                </div>

                <div>
                    <label htmlFor="vehicleType">Vehicle Type:</label>
                    <input type="text" id="vehicleType" name="vehicleType" value={vehicleType}
                           onChange={event => setVehicleType(event.target.value)}/>
                    {errors.vehicleType && <span className="error">{errors.vehicleType}</span>}
                </div>

                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default PurchaseComponent;