import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthState } from './store/auth/auth.state';
import { LoadUserFromToken, Logout } from './store/auth/auth.actions';
import {User} from './models/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: false
})
export class AppComponent implements OnInit {
  title = 'Taxi Service';
  isAuthenticated$: Observable<boolean> | null = null;
  // user$: Observable<User | null> | null = null;
  // isDriver$: Observable<boolean> | null = null;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(new LoadUserFromToken());

    this.isAuthenticated$ = this.store.select(AuthState.isAuthenticated);
    // this.user$ = this.store.select(AuthState.user);
    // this.isDriver$ = this.store.select(AuthState.isDriver);
  }

  onLogout(): void {
    this.store.dispatch(new Logout());
  }
}
