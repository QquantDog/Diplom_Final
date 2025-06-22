import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngxs/store';
import { Observable, Subject } from 'rxjs';
import { Login, Register } from '../../store/auth/auth.actions';
import { AuthState } from '../../store/auth/auth.state';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
  standalone: false
})
export class AuthComponent implements OnInit, OnDestroy {
  isLoginMode = true;
  authForm: FormGroup;
  loading$: Observable<boolean> | null = null;
  error$: Observable<string | null> | null = null;

  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private store: Store
  ) {
    this.authForm = this.createForm();
  }

  ngOnInit(): void {
    this.setupFormValidation();
    this.loading$ = this.store.select(AuthState.loading);
    this.error$ = this.store.select(AuthState.error);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  createForm(): FormGroup {
    return this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      name: [''],
      surname: [''],
      phone_number: [''],
      role: ['customer']
    });
  }

  setupFormValidation(): void {
    this.updateValidators();
  }

  toggleMode(): void {
    this.isLoginMode = !this.isLoginMode;
    this.authForm = this.createForm();
    this.updateValidators();
  }

  private updateValidators(): void {
    const nameControl = this.authForm.get('name');
    const surnameControl = this.authForm.get('surname');
    const phoneControl = this.authForm.get('phone_number');

    if (!this.isLoginMode) {
      nameControl?.setValidators([Validators.required]);
      surnameControl?.setValidators([Validators.required]);
      phoneControl?.setValidators([Validators.required]);
    } else {
      nameControl?.clearValidators();
      surnameControl?.clearValidators();
      phoneControl?.clearValidators();
    }

    nameControl?.updateValueAndValidity();
    surnameControl?.updateValueAndValidity();
    phoneControl?.updateValueAndValidity();
  }

  onSubmit(): void {
    if (this.authForm.valid) {
      if (this.isLoginMode) {
        const { email, password } = this.authForm.value;
        this.store.dispatch(new Login({ email, password }));
      } else {
        this.store.dispatch(new Register(this.authForm.value));
      }
    }
  }

  getErrorMessage(fieldName: string): string {
    const field = this.authForm.get(fieldName);
    if (field?.hasError('required')) {
      return `${this.capitalizeFirstLetter(fieldName)} is required`;
    }
    if (field?.hasError('email')) {
      return 'Please enter a valid email';
    }
    if (field?.hasError('minlength')) {
      return `${this.capitalizeFirstLetter(fieldName)} must be at least ${field.errors?.['minlength'].requiredLength} characters`;
    }
    return '';
  }

  private capitalizeFirstLetter(str: string): string {
    if (str.length === 0) return "";
    return str.charAt(0).toUpperCase() + str.slice(1);
  }
}
