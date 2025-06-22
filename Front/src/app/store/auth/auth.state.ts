import { Injectable } from '@angular/core';
import { State, Action, StateContext, Selector } from '@ngxs/store';
import { tap, catchError } from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import {Login, Register, Logout, LoadUserFromToken, RefreshToken, UpdateProfile, GetMe} from './auth.actions';

export interface AuthStateModel {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

@State<AuthStateModel>({
  name: 'auth',
  defaults: {
    user: null,
    token: null,
    isAuthenticated: false,
    loading: false,
    error: null
  }
})
@Injectable()
export class AuthState {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  @Selector()
  static isAuthenticated(state: AuthStateModel): boolean {
    return state.isAuthenticated;
  }

  @Selector()
  static user(state: AuthStateModel): User | null {
    return state.user;
  }

  @Selector()
  static isDriver(state: AuthStateModel): boolean {
    return state.user?.role === 'driver';
  }

  @Selector()
  static isCustomer(state: AuthStateModel): boolean {
    return state.user?.role === 'customer';
  }

  @Selector()
  static loading(state: AuthStateModel): boolean {
    return state.loading;
  }

  @Selector()
  static error(state: AuthStateModel): string | null {
    return state.error;
  }

  @Action(GetMe)
  getMe(ctx: StateContext<AuthStateModel>): Observable<User> {
    return this.authService.getMe().pipe(
      tap(response => {
        console.warn(response);
        ctx.patchState({
          user: response,
        })
      }),
      catchError((error) => {
        return throwError(() => error);
      })
    )
  }

  @Action(Login)
  login(ctx: StateContext<AuthStateModel>, action: Login) {
    ctx.patchState({ loading: true, error: null });

    return this.authService.login(action.payload).pipe(
      tap((response) => {
        console.warn("response Auth: ");
        console.warn(response);
        this.authService.setToken(response.token);
        ctx.patchState({
          user: response.user,
          token: response.token,
          isAuthenticated: true,
          loading: false
        });
        this.router.navigate(['/rides']);
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Login failed'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(Register)
  register(ctx: StateContext<AuthStateModel>, action: Register) {
    ctx.patchState({ loading: true, error: null });

    return this.authService.register(action.payload).pipe(
      tap((response) => {
        this.authService.setToken(response.token);
        ctx.patchState({
          user: response.user,
          token: response.token,
          isAuthenticated: true,
          loading: false
        });
        this.router.navigate(['/rides']);
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Registration failed'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(UpdateProfile)
  updateProfile(ctx: StateContext<AuthStateModel>, action: UpdateProfile) {
    ctx.patchState({ loading: true, error: null });

    return this.authService.updateProfile(action.payload).pipe(
      tap((updatedUser) => {
        ctx.patchState({
          user: updatedUser,
          loading: false
        });
      }),
      catchError((error) => {
        ctx.patchState({
          loading: false,
          error: error.error?.message || 'Failed to update profile'
        });
        return throwError(() => error);
      })
    );
  }

  @Action(Logout)
  logout(ctx: StateContext<AuthStateModel>) {
    this.authService.logout();
    ctx.setState({
      user: null,
      token: null,
      isAuthenticated: false,
      loading: false,
      error: null
    });
    this.router.navigate(['/auth']);
  }

  @Action(LoadUserFromToken)
  loadUserFromToken(ctx: StateContext<AuthStateModel>) {
    const token = this.authService.getToken();
    if (token && this.authService.isTokenValid(token)) {
      const user = this.authService.getUserFromToken(token);
      ctx.patchState({
        user,
        token,
        isAuthenticated: true
      });
    } else {
      this.authService.logout();
    }
  }

  @Action(RefreshToken)
  refreshToken(ctx: StateContext<AuthStateModel>) {
    return this.authService.refreshToken().pipe(
      tap((response) => {
        this.authService.setToken(response.token);
        ctx.patchState({
          user: response.user,
          token: response.token,
          isAuthenticated: true
        });
      }),
      catchError((error) => {
        ctx.dispatch(new Logout());
        return throwError(() => error);
      })
    );
  }
}
