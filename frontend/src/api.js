import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL;
const AUTH_STORAGE_KEY = import.meta.env.VITE_AUTH_STORAGE_KEY || "auth_token";
const getAuthToken = () => localStorage.getItem(AUTH_STORAGE_KEY);


const api = axios.create({
    baseURL: API_URL,
    timeout: 5000,
});

api.interceptors.request.use(
    (config) => {
        const token = getAuthToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            console.warn("⚠️ Ошибка 401: Неавторизованный запрос");
            localStorage.removeItem(AUTH_STORAGE_KEY); // Удаляем токен

            // Показываем уведомление или просто логируем
            console.log("Пользователь не авторизован. Требуется вход в систему.");

        }
        return Promise.reject(error);
    }
);

export default api;
