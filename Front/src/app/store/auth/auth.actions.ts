import {LoginRequest, RegisterRequest, User} from '../../models/user.model';

export class GetMe {
  static readonly type: string = '[Auth] GetMe';
}

export class Login {
  static readonly type = '[Auth] Login';
  constructor(public payload: LoginRequest) {}
}

export class Register {
  static readonly type = '[Auth] Register';
  constructor(public payload: RegisterRequest) {}
}

export class UpdateProfile {
  static readonly type = '[Auth] Update Profile';
  constructor(public payload: Partial<User>) {}
}

export class Logout {
  static readonly type = '[Auth] Logout';
}

export class LoadUserFromToken {
  static readonly type = '[Auth] Load User From Token';
}

export class RefreshToken {
  static readonly type = '[Auth] Refresh Token';
}
