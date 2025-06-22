export interface JwtPayload {
  id: string;
  name: string;
  surname: string;
  email: string;
  phone_number: string;
  role: 'customer' | 'driver';
  exp: number;
  iat: number;
}
