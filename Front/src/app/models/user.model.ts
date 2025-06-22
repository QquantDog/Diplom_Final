export interface User {
  user_id: string;
  name: string;
  surname: string;
  email: string;
  phone_number: string;
  role: 'customer' | 'driver';
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  email: string;
  password: string;
  role: 'customer' | 'driver';
  phone_number: string;
}

export interface AuthResponse {
  user: User;
  token: string;
}
