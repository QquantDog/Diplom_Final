import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthState } from '../../../store/auth/auth.state';
import { Logout } from '../../../store/auth/auth.actions';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: false
})
export class HeaderComponent implements OnInit {
  user$!: Observable<User | null>;
  isDriver$!: Observable<boolean>;
  isCustomer$!: Observable<boolean>;

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.user$ = this.store.select(AuthState.user);
    this.isDriver$ = this.store.select(AuthState.isDriver);
    this.isCustomer$ = this.store.select(AuthState.isCustomer);
  }

  onLogout(): void {
    this.store.dispatch(new Logout());
  }

  getGreeting(user: User | null): string {
    if (!user) return 'Hello';
    return `Hello, ${user.name || user.email}`;
  }
}
