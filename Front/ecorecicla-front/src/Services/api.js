import axios from 'axios';

// Aqui configuramos o endereço base do seu Spring Boot
export const api = axios.create({
    baseURL: 'http://localhost:8080/residuos' 
});
